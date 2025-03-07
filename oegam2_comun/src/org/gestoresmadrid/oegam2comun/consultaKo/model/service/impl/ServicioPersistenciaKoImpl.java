package org.gestoresmadrid.oegam2comun.consultaKo.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.consultaKo.model.dao.ConsultaKoDao;
import org.gestoresmadrid.core.consultaKo.model.enumerados.EstadoKo;
import org.gestoresmadrid.core.consultaKo.model.enumerados.OperacionConsultaKo;
import org.gestoresmadrid.core.consultaKo.model.vo.ConsultaKoVO;
import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioPersistenciaKo;
import org.gestoresmadrid.oegam2comun.consultaKo.view.bean.ResultadoConsultaKoBean;
import org.gestoresmadrid.oegam2comun.evolucionConsultaKo.model.service.ServicioEvolucionConsultaKo;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaKoImpl implements ServicioPersistenciaKo {

	private static final long serialVersionUID = 9051719627525650449L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaKoImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ConsultaKoDao consultaKoDao;

	@Autowired
	ServicioEvolucionConsultaKo servicioEvolucionKo;

	@Autowired
	ServicioComunCola servicioCola;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public void guardarConEvolucion(ConsultaKoVO consultaKoVo, String estadoAnt, Date fecha,
			OperacionConsultaKo operacion) {
		consultaKoDao.guardarOActualizar(consultaKoVo);
		servicioEvolucionKo.guardarEvolucion(consultaKoVo.getId(), consultaKoVo.getMatricula(),
				new BigDecimal(consultaKoVo.getIdUsuario()), consultaKoVo.getTipoTramite(), consultaKoVo.getTipo(),
				OperacionConsultaKo.CREACION, new Date(), estadoAnt, consultaKoVo.getEstado());
	}

	@Override
	@Transactional
	public ResultadoConsultaKoBean solicitarGenExcelDemanda(List<Long> listaIdsConsultaKO, Long idUsuario, Date fecha) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			for (Long id : listaIdsConsultaKO) {
				ConsultaKoVO consultaKoVO = getConsultaKo(id);
				if (consultaKoVO != null) {
					consultaKoVO.setEstado(EstadoKo.Pdte_Generar_KO.getValorEnum());
					servicioEvolucionKo.guardarEvolucion(consultaKoVO.getId(), consultaKoVO.getMatricula(),
							new BigDecimal(idUsuario), consultaKoVO.getTipoTramite(), consultaKoVO.getTipo(),
							OperacionConsultaKo.CREACION, new Date(), EstadoKo.Iniciado.getValorEnum(),
							consultaKoVO.getEstado());
				}
			}
			ResultadoBean resultCola = servicioCola.crearSolicitud(null, ProcesosEnum.GEN_EXCEL_KO_IMPR.getNombreEnum(),
					gestorPropiedades.valorPropertie("nombreHostProceso"), TipoTramiteTrafico.IMPR_KO.getValorEnum(),
					new BigDecimal(idUsuario), null, null);
			if (resultCola.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de generar la solicitud al proceso de generacion excel KO a demanda.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de solicitar a demanda la generacion del excel, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar a demanda la generacion del excel.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsultaKoVO> getListaKoPorEstado(String estadoKo) {
		return consultaKoDao.getListaKoPorEstado(estadoKo);
	}

	@Override
	@Transactional(readOnly = true)
	public ConsultaKoVO getConsultaKo(Long id) {
		return consultaKoDao.getConsultaKo(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsultaKoVO> getListaConsultaKO(Long[] idsConsultaKO) {
		return consultaKoDao.getListaConsultasKOPorIds(idsConsultaKO);
	}

	@Override
	@Transactional
	public void actualizarEstadoGenExcel(Long id, String estadoKO, Long idUsuario, Date fecha,
			OperacionConsultaKo operacion, String nombreFichero, Date fechaGenExcel) {
		ConsultaKoVO consultaKoVO = consultaKoDao.getConsultaKo(id);
		String estadoAntConsultaKo = consultaKoVO.getEstado();
		consultaKoVO.setEstado(estadoKO);
		consultaKoVO.setNombreFichero(nombreFichero);
		consultaKoVO.setFechaGenExcel(fechaGenExcel);
		consultaKoDao.guardarOActualizar(consultaKoVO);
		if (idUsuario == null) {
			idUsuario = consultaKoVO.getIdUsuario();
		}
		servicioEvolucionKo.guardarEvolucion(consultaKoVO.getId(), consultaKoVO.getMatricula(),
				new BigDecimal(idUsuario), consultaKoVO.getTipoTramite(), consultaKoVO.getTipo(), operacion, fecha,
				estadoAntConsultaKo, consultaKoVO.getEstado());
	}

	@Override
	@Transactional
	public ResultadoConsultaKoBean cambiarEstadoConsultaConEvolucion(Long idConsultaKO, String estadoNuevo,
			Long idUsuario) {
		ResultadoConsultaKoBean resultado = new ResultadoConsultaKoBean(Boolean.FALSE);
		try {
			ConsultaKoVO consultaKoVO = getConsultaKo(idConsultaKO);
			if (consultaKoVO != null) {
				String estadoAnt = consultaKoVO.getEstado();
				consultaKoVO.setEstado(estadoNuevo);
				consultaKoDao.actualizar(consultaKoVO);
				servicioEvolucionKo.guardarEvolucion(consultaKoVO.getId(), consultaKoVO.getMatricula(),
						new BigDecimal(idUsuario), consultaKoVO.getTipoTramite(), consultaKoVO.getTipo(),
						OperacionConsultaKo.CAMBIO_ESTADO, new Date(), estadoAnt, consultaKoVO.getEstado());
				resultado.setMensaje(
						"El KO de la matricula: " + consultaKoVO.getMatricula() + " se ha cambiado su estado.");
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para la consultaKO con ID: " + idConsultaKO);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consultaKO con ID: " + idConsultaKO
					+ ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(
					"Ha sucedido un error a la hora de cambiar el estado de la consultaKO con ID: " + idConsultaKO);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConsultaKoVO> getListaPorEstadoDoc(String estadoDocKO) {
		return consultaKoDao.getListaPorEstadoDoc(estadoDocKO);
	}

}