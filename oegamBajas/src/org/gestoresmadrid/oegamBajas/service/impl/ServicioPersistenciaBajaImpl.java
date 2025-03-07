package org.gestoresmadrid.oegamBajas.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.enumerados.ProcesosEnum;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoPK;
import org.gestoresmadrid.core.trafico.model.vo.EvolucionTramiteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamBajas.service.ServicioPersistenciaBaja;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.cola.service.ServicioComunCola;
import org.gestoresmadrid.oegamComun.credito.service.ServicioComunCredito;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioPersistenciaEvoTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPersistenciaBajaImpl implements ServicioPersistenciaBaja {

	private static final long serialVersionUID = -4824393382637679450L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaBajaImpl.class);
	private static final String COLEGIADO_AM = "2631";

	@Autowired
	TramiteTraficoBajaDao tramiteTraficoBajaDao;

	@Autowired
	ServicioPersistenciaEvoTramiteTrafico servicioPersistenciaEvoTramiteTrafico;

	@Autowired
	ServicioComunCola servicioComunCola;

	@Autowired
	ServicioComunCredito servicioComunCredito;

	@Autowired
	GestorPropiedades gestorProperties;

	@Override
	@Transactional(readOnly=true)
	public TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, Boolean tramiteCompleto) {
		return tramiteTraficoBajaDao.getTramiteBaja(numExpediente, tramiteCompleto);
	}

	@Override
	@Transactional
	public void guardarActualizarTramite(TramiteTrafBajaVO tramiteTrafBajaVO) {
		tramiteTraficoBajaDao.guardarOActualizar(tramiteTrafBajaVO);
	}

	@Override
	@Transactional
	public void guardarActualizarTramiteConEvo(TramiteTrafBajaVO tramiteTrafBajaVO, EvolucionTramiteTraficoVO evolucionTramiteTraficoVO) {
		tramiteTraficoBajaDao.guardarOActualizar(tramiteTrafBajaVO);
		servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
	}

	@Override
	@Transactional
	public void finalizarSolicitudBtv(BigDecimal numExpediente, BigDecimal nuevoEstado, String respuesta, BigDecimal idUsuario) {
		TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBaja(numExpediente, Boolean.FALSE);
		BigDecimal estadoAnt = tramiteTrafBajaVO.getEstado();
		EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
		EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
		id.setEstadoAnterior(estadoAnt);
		id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
		id.setNumExpediente(numExpediente);
		evolucionTramiteTraficoVO.setId(id);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		evolucionTramiteTraficoVO.setUsuario(usuario);
		Date fecha = new Date();
		tramiteTrafBajaVO.setFechaUltModif(fecha);
		tramiteTrafBajaVO.setEstado(nuevoEstado);
		tramiteTrafBajaVO.setRespuesta(respuesta);
		tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
		servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
		if (EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(nuevoEstado.toString())) {
			List<BigDecimal> listaExpedientes = new ArrayList<>();
			listaExpedientes.add(numExpediente);
			String descontarCreditosAM = gestorProperties.valorPropertie("descontar.creditos.Am");

			servicioComunCredito.devolverCredito(
					COLEGIADO_AM.equals(tramiteTrafBajaVO.getNumColegiado()) && "SI".equals(descontarCreditosAM)
							? TipoTramiteTrafico.BAJA_AM.getValorEnum()
							: TipoTramiteTrafico.Baja.getValorEnum(),
					tramiteTrafBajaVO.getContrato().getIdContrato(), idUsuario.longValue(), listaExpedientes);
		}
	}

	@Override
	@Transactional
	public void finalizarSolicitudCheckBtv(BigDecimal numExpediente, BigDecimal estadoNuevo, String respuesta, BigDecimal idUsuario) {
		TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBaja(numExpediente, Boolean.FALSE);
		BigDecimal estadoAnt = tramiteTrafBajaVO.getEstado();
		EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
		EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
		id.setEstadoAnterior(estadoAnt);
		id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
		id.setNumExpediente(numExpediente);
		evolucionTramiteTraficoVO.setId(id);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		evolucionTramiteTraficoVO.setUsuario(usuario);
		Date fecha = new Date();
		tramiteTrafBajaVO.setFechaUltModif(fecha);
		tramiteTrafBajaVO.setEstado(estadoNuevo);
		tramiteTrafBajaVO.setResCheckBtv(respuesta);
		tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
		servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
	}

	@Override
	@Transactional
	public ResultadoBajasBean realizarCheckBtv(BigDecimal numExpediente, String proceso, String nombreXml,
			BigDecimal idUsuario, String hostProceso, Long idContrato) throws OegamExcepcion {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBaja(numExpediente, Boolean.FALSE);
			BigDecimal estadoAnt = tramiteTrafBajaVO.getEstado();
			tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Consulta_BTV.getValorEnum()));
			EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
			EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
			id.setEstadoAnterior(estadoAnt);
			id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
			id.setNumExpediente(numExpediente);
			evolucionTramiteTraficoVO.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			evolucionTramiteTraficoVO.setUsuario(usuario);
			Date fecha = new Date();
			tramiteTrafBajaVO.setFechaUltModif(fecha);
			tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
			servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
			servicioComunCola.crearSolicitud(numExpediente.longValue(),proceso, hostProceso,
					TipoTramiteTrafico.Baja.getValorEnum(), idUsuario, new BigDecimal(idContrato), nombreXml);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de guardar el checkBtv del tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el checkBtv del trámite de baja con número de expediente: " + numExpediente);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBajasBean realizarPdteEnvioExcel(BigDecimal numExpediente, BigDecimal idUsuario, Long idContrato) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBaja(numExpediente, Boolean.FALSE);
			BigDecimal estadoAnt = tramiteTrafBajaVO.getEstado();
			tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio_Excel.getValorEnum()));
			EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
			EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
			id.setEstadoAnterior(estadoAnt);
			id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
			id.setNumExpediente(numExpediente);
			evolucionTramiteTraficoVO.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			evolucionTramiteTraficoVO.setUsuario(usuario);
			List<BigDecimal> listaExpedientesCreditos = new ArrayList<>();
			listaExpedientesCreditos.add(numExpediente);
			ResultadoBean resultCredito = servicioComunCredito.creditosDisponiblesComprobandoPendientes(1L,
					tramiteTrafBajaVO.getContrato().getIdContrato(), ProcesosEnum.BTV.getNombreEnum(),
					TipoTramiteTrafico.Baja.getValorEnum());
			if (!resultCredito.getError()) {
				servicioComunCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), idContrato,
						idUsuario.longValue(), listaExpedientesCreditos);
				tramiteTrafBajaVO.setFechaUltModif(new Date());
				tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
				servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene créditos disponibles.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el pendiente envio excel del tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el pendiente envío excel del trámite de baja con número de expediente: " + numExpediente);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoBajasBean realizarTramitacionBtv(BigDecimal numExpediente, String proceso, String nombreXml,
			BigDecimal idUsuario, String hostProceso, Long idContrato) throws OegamExcepcion {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			TramiteTrafBajaVO tramiteTrafBajaVO = getTramiteBaja(numExpediente, Boolean.FALSE);
			BigDecimal estadoAnt = tramiteTrafBajaVO.getEstado();
			tramiteTrafBajaVO.setEstado(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
			EvolucionTramiteTraficoVO evolucionTramiteTraficoVO = new EvolucionTramiteTraficoVO();
			EvolucionTramiteTraficoPK id = new EvolucionTramiteTraficoPK();
			id.setEstadoAnterior(estadoAnt);
			id.setEstadoNuevo(tramiteTrafBajaVO.getEstado());
			id.setNumExpediente(numExpediente);
			evolucionTramiteTraficoVO.setId(id);
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(idUsuario.longValue());
			evolucionTramiteTraficoVO.setUsuario(usuario);
			List<BigDecimal> listaExpedientesCreditos = new ArrayList<>();
			listaExpedientesCreditos.add(numExpediente);
			String descontarCreditosAM = gestorProperties.valorPropertie("descontar.creditos.Am");
			if (COLEGIADO_AM.equals(tramiteTrafBajaVO.getNumColegiado()) && "SI".equals(descontarCreditosAM)) {
				servicioComunCredito.descontarCreditos(TipoTramiteTrafico.BAJA_AM.getValorEnum(), idContrato,
						idUsuario.longValue(), listaExpedientesCreditos);
				tramiteTrafBajaVO.setFechaUltModif(new Date());
				tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
				servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
				servicioComunCola.crearSolicitud(numExpediente.longValue(), proceso, hostProceso,
				TipoTramiteTrafico.Baja.getValorEnum(), idUsuario, new BigDecimal(idContrato), nombreXml);
			} else {
				ResultadoBean resultCredito = servicioComunCredito.creditosDisponiblesComprobandoPendientes(1L,
						tramiteTrafBajaVO.getContrato().getIdContrato(), ProcesosEnum.BTV.getNombreEnum(), TipoTramiteTrafico.Baja.getValorEnum());
				if (!resultCredito.getError()) {
					servicioComunCredito.descontarCreditos(TipoTramiteTrafico.Baja.getValorEnum(), idContrato, idUsuario.longValue(), listaExpedientesCreditos);
					tramiteTrafBajaVO.setFechaUltModif(new Date());
					tramiteTraficoBajaDao.actualizar(tramiteTrafBajaVO);
					servicioPersistenciaEvoTramiteTrafico.guardar(evolucionTramiteTraficoVO);
					servicioComunCola.crearSolicitud(numExpediente.longValue(),proceso, hostProceso,
						TipoTramiteTrafico.Baja.getValorEnum(), idUsuario, new BigDecimal(idContrato), nombreXml);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No tiene créditos disponibles.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el tramitarBtv del tramite de baja con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el tramitarBtv del trámite de baja con número de expediente: " + numExpediente);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
}