package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.impr.model.dao.DocImprDao;
import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.impr.service.ServicioDistintivo;
import org.gestoresmadrid.oegamComun.impr.service.ServicioEvoDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaDocImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ServicioPersistenciaDocImprImpl implements ServicioPersistenciaDocImpr {

	private static final long serialVersionUID = -2505649746136342023L;

	private static final Logger log = LoggerFactory.getLogger(ServicioPersistenciaDocImprImpl.class);

	@Autowired
	DocImprDao docImprDao;

	@Autowired
	ServicioEvoDocImpr servicioEvoDocImpr;

	@Autowired
	ServicioComunCola servicioComunCola;

	@Override
	@Transactional
	public void borrarDoc(Long docId) {
		servicioEvoDocImpr.borrarEvolucionImpr(docId);
		DocImprVO docImprVO = docImprDao.getDocImprPorId(docId, Boolean.FALSE);
		docImprDao.borrar(docImprVO);
	}

	@Override
	@Transactional
	public Long guardar(DocImprVO docImpr) {
		return (Long) docImprDao.guardar(docImpr);
	}

	@Override
	@Transactional
	public void guardarDocId(DocImprVO docImpr, Long idUsuario) {
		docImprDao.actualizar(docImpr);
		servicioEvoDocImpr.guardarEvolucion(docImpr.getId(), docImpr.getTipo(), OperacionPrmDstvFicha.CREACION.getValorEnum(), null, docImpr.getEstado(), idUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public DocImprVO getDocImprPorId(Long id, Boolean completo) {
		return docImprDao.getDocImprPorId(id, completo);
	}

	@Override
	@Transactional
	public ResultadoDocImprBean impresionManual(Long id, List<Long> listaIds, Long idUsuario, String jefatura) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = getDocImprPorId(id, Boolean.FALSE);
			String estadoAnt = docImprVO.getEstado();
			docImprVO.setEstado(EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum());
			docImprDao.actualizar(docImprVO);
			servicioEvoDocImpr.guardarEvolucion(docImprVO.getId(), docImprVO.getTipo(), OperacionPrmDstvFicha.IMPRESION.getValorEnum(), estadoAnt, docImprVO.getEstado(), idUsuario);
			if (TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())) {
				impresionDocDstv(docImprVO, listaIds, idUsuario, jefatura);
			} else {
				impresionDocPcFct(docImprVO, listaIds, idUsuario, jefatura);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir manualmente el Id: " + id + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir manualmente el Id: " + id);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void impresionDocPcFct(DocImprVO docImprVO, List<Long> listaIds, Long idUsuario, String jefatura) {
//		if (TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo())) {
		//servicioConsultaStock.descontarStock(docImprVO.getTipo(), jefatura, listaIds.size());
//		}
//		for (Long idImpr : listaIds) {
			//servicioConsultaStock.impresionImpr(idImpr, idUsuario);
//		}
	}

	private void impresionDocDstv(DocImprVO docImprVO, List<Long> listaIds, Long idUsuario, String jefatura) {
		/*servicioPersistenciaGsmMaterialStock.descontarStock(docImprVO.getTipoDstv(), jefaturaUsuarioImpr, listaIds.size());
		Long dstv = 0L;
		Long dupDstv = 0L;
		for (Long idDstv : listaIds) {
			DistintivoVO distintivoVO = servicioPersistenciaDistintivo.getDistintivoPorId(idDstv);
			if (TipoPeticionDistintivo.Duplicado_Dstv.getValorEnum().equals(distintivoVO.getTipo())) {
				if ("NO".equals(distintivoVO.getPrimeraImpresion())) {
					dupDstv++;
				} else {
					dstv++;
				}
			} else {
				dstv++;
			}
			servicioPersistenciaDistintivo.impresionDistintivo(distintivoVO, idUsuario);
		}
		servicioPersistenciaFacturacionDstv.guardarFacturacion(docImprVO.getId(), docImprVO.getTipoDstv(), docImprVO.getIdContrato(), dstv, dupDstv);
	*/
	}

	@Override
	@Transactional
	public ResultadoDocImprBean cambiarEstado(Long id, String estadoNuevo, Long idUsuario, List<Long> listaIds) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = getDocImprPorId(id, Boolean.FALSE);
			String estadoAnt = docImprVO.getEstado();
			docImprVO.setEstado(estadoNuevo);
			docImprDao.actualizar(docImprVO);
			servicioEvoDocImpr.guardarEvolucion(docImprVO.getId(), docImprVO.getTipo(), OperacionPrmDstvFicha.CAMBIO_ESTADO.getValorEnum(), estadoAnt, docImprVO.getEstado(), idUsuario);
			if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(estadoAnt) && TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())) {
				//servicioPersistenciaGsmMaterialStock.añadirStock(docImprVO.getTipoDstv(), docImprVO.getJefatura(), listaIds.size());
				//servicioPersistenciaFacturacionDstv.borrarFacturacion(docImprVO.getDocImpr());
			} else if (EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(estadoAnt) && TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo())) {
				//servicioPersistenciaGsmMaterialStock.añadirStock(docImprVO.getTipo(), docImprVO.getJefatura(), listaIds.size());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del docId: " + id + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoDocImprBean reactivar(Long id, Long idUsuario, Boolean esEntornoAm) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = getDocImprPorId(id, Boolean.FALSE);
			String estadoAnt = docImprVO.getEstado();
			docImprVO.setEstado(EstadoPermisoDistintivoItv.ORDENADO_DOC.getValorEnum());
			docImprDao.actualizar(docImprVO);
			servicioEvoDocImpr.guardarEvolucion(docImprVO.getId(), docImprVO.getTipo(), OperacionPrmDstvFicha.REACTIVAR.getValorEnum(), estadoAnt, docImprVO.getEstado(), idUsuario);
			String proceso = "", datos = null, tipoTramiteSolicitud = null;
			if (TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())) {
				tipoTramiteSolicitud = TipoTramiteTrafico.Solicitud_Distintivo.getValorEnum();
				proceso = ProcesosEnum.IMP_DSTV.getNombreEnum();
				datos = ServicioDistintivo.IMPRESION_PROCESO_DSTV + "_" + docImprVO.getId();
			} else if (TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo())) {
				tipoTramiteSolicitud = TipoTramiteTrafico.Solicitud_Permiso.getValorEnum();
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(docImprVO.getTipoTramite())) {
					if (esEntornoAm || "2631".equals(docImprVO.getContrato().getColegiado().getNumColegiado())) {
						proceso = ProcesosAmEnum.IMP_PRM_MATW.getValorEnum();
					} else {
						proceso = ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
					}
				} else {
					if (esEntornoAm || "2631".equals(docImprVO.getContrato().getColegiado().getNumColegiado())) {
						proceso = ProcesosAmEnum.IMP_PRM_CTIT.getValorEnum();
					} else {
						proceso = ProcesosEnum.IMP_PRM_CTIT.getNombreEnum();
					}
				}
			} else {
				tipoTramiteSolicitud = TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum();
				if (esEntornoAm || "2631".equals(docImprVO.getContrato().getColegiado().getNumColegiado())) {
					proceso = ProcesosAmEnum.IMP_FICHA.getValorEnum();
				} else {
					proceso = ProcesosEnum.IMP_FICHA.getNombreEnum();
				}
			}
			ResultadoBean resultCola = servicioComunCola.crearSolicitud(proceso, datos, tipoTramiteSolicitud, new BigDecimal(id), idUsuario, new BigDecimal(docImprVO.getIdContrato()));
			if (resultCola.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultCola.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reactivar el documento: " + id + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar el documento.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	public ResultadoDocImprBean imprimir(Long id, List<Long> listaIds, Long idUsuario, String jefatura) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = getDocImprPorId(id, Boolean.FALSE);
			String estadoAnt = docImprVO.getEstado();
			docImprVO.setEstado(EstadoPermisoDistintivoItv.IMPRIMIENDO.getValorEnum());
			docImprDao.actualizar(docImprVO);
			servicioEvoDocImpr.guardarEvolucion(docImprVO.getId(), docImprVO.getTipo(), OperacionPrmDstvFicha.IMPRIMIENDO.getValorEnum(), estadoAnt, docImprVO.getEstado(), idUsuario);
			if (TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())) {
				impresionDocDstv(docImprVO, listaIds, idUsuario, jefatura);
			} else {
				impresionDocPcFct(docImprVO, listaIds, idUsuario, jefatura);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el DocId: " + id + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el DocId: " + id);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
}