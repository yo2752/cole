package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.enumerados.EstadoImpr;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.impr.model.dao.ImprDao;
import org.gestoresmadrid.core.impr.model.vo.EvolucionImprVO;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvolucionGestionarImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaGestionImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaGestionImprImpl implements ServicioPersistenciaGestionImpr {

	private static final long serialVersionUID = 8803104168235386698L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaGestionImprImpl.class);

	@Autowired
	ImprDao imprDao;

	@Autowired
	ServicioEvolucionGestionarImpr servicioEvolucionGestionarImpr;

	@Autowired
	ServicioComunCola servicioCola;

	@Override
	@Transactional(readOnly = true)
	public ImprVO getImprVO(Long id) {
		try {
			return imprDao.getImpr(id);
		} catch (Exception e) {
			log.error("Error al recuperar trámite, error: ", e);
		}	
		return null;
	}

	@Override
	@Transactional
	public void actualiarEstado(Long id, String estadoNuevo,Long idUsuario) {
		ImprVO imprVO = imprDao.getImpr(id);
		String estadoAnterior= imprVO.getEstadoImpr();
		imprVO.setEstadoImpr(estadoNuevo);
		imprDao.actualizar(imprVO);
		servicioEvolucionGestionarImpr.guardarEvolucion(rellenarEvolucion(imprVO,idUsuario,OperacionPrmDstvFicha.CAMBIO_ESTADO.getValorEnum(),
				estadoAnterior,estadoNuevo,null));
	}

	private EvolucionImprVO rellenarEvolucion(ImprVO imprVO, Long idUsuario, String tipoOperacion, String estadoAnterior,
			String estadoNuevo, Long docImpr) {
		EvolucionImprVO evolucionImprVO = new EvolucionImprVO();
		evolucionImprVO.setIdImpr(imprVO.getId());
		evolucionImprVO.setEstadoAnterior(estadoAnterior);
		evolucionImprVO.setEstadoNuevo(estadoNuevo);
		evolucionImprVO.setIdUsuario(idUsuario);
		evolucionImprVO.setTipoOperacion(tipoOperacion);
		evolucionImprVO.setTipoImpr(imprVO.getTipoImpr());
		evolucionImprVO.setIdDocImpr(docImpr);
		return evolucionImprVO;
	}

	@Override
	@Transactional
	public void modificarCarpeta(Long id, String carpetaNueva, Long idUsuario) {
		ImprVO imprVO = imprDao.getImpr(id);
		String estadoAnterior = imprVO.getEstadoImpr();
		imprVO.setCarpeta(carpetaNueva);
		imprDao.actualizar(imprVO);
		servicioEvolucionGestionarImpr.guardarEvolucion(rellenarEvolucion(imprVO,idUsuario,OperacionPrmDstvFicha.MODIFICACION_CARPETA.getValorEnum(),
				estadoAnterior,imprVO.getEstadoImpr(),null));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImprVO> getListaImprPorIds(List<Long> listaIds) {
		return imprDao.getListaImprPorIds(listaIds);
	}

	@Override
	@Transactional
	public void actualizarGeneracionImpr(Long id, Long idUsuario) {
		ImprVO imprVO = imprDao.getImpr(id);
		imprVO.setEstadoImpr(EstadoImpr.Finalizado.getValorEnum());
		imprDao.actualizar(imprVO);
		servicioEvolucionGestionarImpr.guardarEvolucion(rellenarEvolucion(imprVO,idUsuario,OperacionPrmDstvFicha.FINALIZADO_IMPR.getValorEnum(),
				EstadoImpr.Tramitado_Telematicamente.getValorEnum(), EstadoImpr.Finalizado.getValorEnum(),null));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ImprVO> getListaExpedientesPorListNumExp(Long[] ids) {
		return imprDao.getListaImprPorIds(ids);
	}

	@Override
	@Transactional
	public ResultadoDocImprBean generarDocImprNocturno(List<Long> listaIdsImpr, Long docId, Long idUsuario, String tipoSolicitud, Boolean esEntornoAm, Long idContrato) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			for (Long idImpr : listaIdsImpr) {
				ImprVO imprVO = imprDao.getImpr(idImpr);
				imprVO.setEstadoSolicitud(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
				imprVO.setEstadoImpresion(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				imprVO.setDocId(docId);
				imprVO.setEstadoImpr(EstadoImpr.Finalizado.getValorEnum());
				imprDao.actualizar(imprVO);
				servicioEvolucionGestionarImpr.guardarEvolucion(rellenarEvolucionImpr(imprVO, idUsuario, 
						OperacionPrmDstvFicha.SOLICITUD.getValorEnum(), null, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(), docId));
			}

			ResultadoBean resultBean = servicioCola.crearSolicitud(esEntornoAm ? ProcesosAmEnum.IMPR_NOCTURNO.getValorEnum() : ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), null, tipoSolicitud,
					new BigDecimal(docId), idUsuario, new BigDecimal(idContrato));
			
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de cambiar los estados y generar la cola para los IMPR_NOCTURNOS del contrato: " +idContrato + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar los estados y generar la cola para los IMPR_NOCTURNOS del contrato: " +idContrato );
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private EvolucionImprVO rellenarEvolucionImpr(ImprVO impr, Long idUsuario,String tipoOperacion, String estadoAnt,
			String estadoNuevo, Long docImpr) {
		EvolucionImprVO evolucionImprVO = new EvolucionImprVO();
		evolucionImprVO.setIdImpr(impr.getId());
		evolucionImprVO.setEstadoAnterior(estadoAnt);
		evolucionImprVO.setEstadoNuevo(estadoNuevo);
		evolucionImprVO.setIdUsuario(idUsuario);
		evolucionImprVO.setTipoOperacion(tipoOperacion);
		evolucionImprVO.setTipoImpr(impr.getTipoImpr());
		evolucionImprVO.setIdDocImpr(docImpr);
		return evolucionImprVO;
	}

	@Override
	@Transactional
	public void desasignarDocumento(Long id, Long idUsuario) {
		ImprVO imprVO = imprDao.getImpr(id);
		String estadoAnterior = imprVO.getEstadoImpr();
		Long docImpr = imprVO.getDocId();
		imprVO.setDocId(null);
		imprVO.setEstadoSolicitud(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
		imprVO.setEstadoImpresion(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
		imprDao.actualizar(imprVO);
		servicioEvolucionGestionarImpr.guardarEvolucion(rellenarEvolucion(imprVO,idUsuario,OperacionPrmDstvFicha.DESASIGNAR.getValorEnum(),
				estadoAnterior,imprVO.getEstadoImpr(),docImpr));
	}

}