package org.gestoresmadrid.oegamComun.tasa.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaPK;
import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioComunTasa;
import org.gestoresmadrid.oegamComun.tasa.service.ServicioPersistenciaTasas;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunTasaImpl implements ServicioComunTasa {

	private static final long serialVersionUID = -1562735488346078294L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunTasaImpl.class);

	@Autowired
	ServicioPersistenciaTasas servicioPersistenciaTasa;

	@Autowired
	GestorPropiedades gestorProperties;

	@Override
	public ResultadoTasaBean gestionarTasaTramite(TasaVO tasa, BigDecimal numExpediente, Long idUsuario, String tipoTasa) {
		ResultadoTasaBean resultado = new ResultadoTasaBean(Boolean.FALSE);
		try {
			TasaVO tasaBBDD = servicioPersistenciaTasa.getTasa(null, numExpediente, tipoTasa);
			if (tasa != null && StringUtils.isNotBlank(tasa.getCodigoTasa())) {
				if (tasaBBDD == null) {
					resultado = servicioPersistenciaTasa.asignarTasasTramite(tasa.getCodigoTasa(), tasa.getTipoTasa(), numExpediente, idUsuario);
				} else if (tasaBBDD != null && !tasa.getCodigoTasa().equals(tasaBBDD.getCodigoTasa())) {
					resultado = servicioPersistenciaTasa.desasignarYAsignarTasasTramite(tasa.getCodigoTasa(), tasa.getTipoTasa(), tasaBBDD, numExpediente, idUsuario);
				}
			} else {
				if (tasaBBDD != null) {
					Date fecha = new Date();
					tasaBBDD.setFechaAsignacion(null);
					tasaBBDD.setNumExpediente(null);
					servicioPersistenciaTasa.guardarActualizarConEvo(tasaBBDD, gestionarEvolucion(tasaBBDD.getCodigoTasa(), null, fecha, DESASIGNAR, idUsuario));
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de gestionar la tasa: " + tasa.getCodigoTasa() + " para el expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de gestionar la tasa: " + tasa.getCodigoTasa() + " para el expediente: " + numExpediente);
		}
		return resultado;
	}

	@Override
	public TasaVO getTasa(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		try {
			return servicioPersistenciaTasa.getTasa(codigoTasa, numExpediente, tipoTasa);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de asignar la tasa: " + codigoTasa + ", al expediente: " + numExpediente + ", error: ", e);
		}
		return null;
	}

	@Override
	public ResultadoBean asignarTasaExpediente(String codigoTasa, BigDecimal numExpediente, Long idContrato, String tipoTasa, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TasaVO tasa = servicioPersistenciaTasa.getTasa(codigoTasa, null, tipoTasa);
			resultado = validarAsignacionTasa(tasa, codigoTasa, idContrato, numExpediente);
			if (!resultado.getError()) {
				Date fecha = new Date();
				tasa.setFechaAsignacion(fecha);
				tasa.setNumExpediente(numExpediente);
				servicioPersistenciaTasa.guardarActualizarConEvo(tasa, gestionarEvolucion(codigoTasa, numExpediente, fecha, ASIGNAR, idUsuario));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de asignar la tasa: " + codigoTasa + ", al expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de asignar la tasa: " + codigoTasa);
		}
		return resultado;
	}
	

	/*@Override
	public void bloquearComboTasa(String codigoTasa, Long idUsuario) {
		try {
			servicioPersistenciaTasa.bloquearComboTasa(codigoTasa,idUsuario);
		}catch (Exception e) {
			log.error("Error al bloquear el combo: " + codigoTasa, e);
		}

		
	}*/

	@Override
	public ResultadoBean desasignarTasaExpediente(String codigoTasa, BigDecimal numExpediente, Long idContrato, String tipoTasa, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TasaVO tasa = servicioPersistenciaTasa.getTasa(codigoTasa, null, tipoTasa);
			resultado = validarDesasignacionTasa(tasa, codigoTasa, idContrato, numExpediente);
			if (!resultado.getError()) {
				Date fecha = new Date();
				tasa.setFechaAsignacion(null);
				tasa.setNumExpediente(null);
				servicioPersistenciaTasa.guardarActualizarConEvo(tasa, gestionarEvolucion(codigoTasa, null, fecha, DESASIGNAR, idUsuario));
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar la tasa: " + codigoTasa + ", al expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar la tasa: " + codigoTasa);
		}
		return resultado;
	}

	private ResultadoBean validarDesasignacionTasa(TasaVO tasa, String codigoTasa, Long idContrato, BigDecimal numExpediente) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (tasa == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa:" + codigoTasa + " no existe en BBDD con ese codigo o tipo de tasa.");
		} else if (tasa.getContrato().getIdContrato() != null && !tasa.getContrato().getIdContrato().equals(idContrato)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa:" + codigoTasa + " no es del mismo contrato que el del tramite.");
		} else if (tasa.getNumExpediente() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa:" + codigoTasa + " no se puede desasignar porque no se encuentra asignada a ningún expediente.");
		} else if (!tasa.getNumExpediente().toString().equals(numExpediente.toString())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa:" + codigoTasa + " no se puede desasignar porque no se encuentra asignada al expediente.");
		}
		return resultado;
	}

	private EvolucionTasaVO gestionarEvolucion(String codigoTasa, BigDecimal numExpediente, Date fecha, String estado, Long idUsuario) {
		EvolucionTasaVO evolucionTasaVO = new EvolucionTasaVO();
		EvolucionTasaPK id = new EvolucionTasaPK();
		id.setCodigoTasa(codigoTasa);
		id.setFechaHora(fecha);
		evolucionTasaVO.setId(id);
		evolucionTasaVO.setAccion(estado);
		evolucionTasaVO.setNumExpediente(numExpediente);
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		evolucionTasaVO.setUsuario(usuario);
		return evolucionTasaVO;
	}

	private ResultadoBean validarAsignacionTasa(TasaVO tasa, String codigoTasa, Long idContrato, BigDecimal numExpediente) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		if (tasa == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa: " + codigoTasa + " no existe en BBDD con ese codigo o tipo de tasa.");
		} else if (tasa.getContrato() != null && !tasa.getContrato().getIdContrato().equals(idContrato)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa: " + codigoTasa + " no es del mismo contrato que el del tramite.");
		} else if (tasa.getNumExpediente() != null && !tasa.getNumExpediente().equals(numExpediente)) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa: " + codigoTasa + " ya esta asignada al expediente: " + tasa.getNumExpediente());
		}
		return resultado;
	}

	@Override
	public List<TasaVO> obtenerTasasContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorProperties.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}
			return servicioPersistenciaTasa.obtenerTasasContrato(idContrato, tipoTasa, maxResult);
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}
	
	@Override
	public List<TasaInteveVO> obtenerTasasInteveContrato(Long idContrato, String tipoTasa) {
		int maxResult = 20;
		try {
			String maxResultProp = gestorProperties.valorPropertie("maximo.resultados.tasa");
			if (StringUtils.isNotBlank(maxResultProp)) {
				maxResult = Integer.parseInt(maxResultProp);
			}
			return servicioPersistenciaTasa.obtenerTasasInteveContrato(idContrato, tipoTasa, maxResult);
		} catch (Exception e) {
			log.error("Error al obtener las tasa para el contrato: " + idContrato, e);
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoBean asignarTasaLibre(BigDecimal numExpediente, String tipoTramite, String tipoTasa, Long idContrato, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			TasaVO tasa = servicioPersistenciaTasa.getTasaLibre(idContrato, tipoTasa);
			if (tasa != null) {
				Date fecha = new Date();
				tasa.setFechaAsignacion(fecha);
				tasa.setNumExpediente(numExpediente);
				resultado.setCodigoTasa(tasa.getCodigoTasa());
				servicioPersistenciaTasa.guardarActualizarConEvo(tasa, gestionarEvolucion(tasa.getCodigoTasa(), numExpediente, fecha, ASIGNAR, idUsuario));
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen tasas libres.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de asignar la tasa libre al expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de asignar la tasa libre.");
		}
		return resultado;
	}

	@Override
	public ResultadoBean desasignarTasaBBDD(String codigoTasa, BigDecimal numExpediente, Long idContrato, String tipoTasa, Long idUsuario) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		resultado = servicioPersistenciaTasa.desasignarTasaBBDD(codigoTasa,numExpediente,tipoTasa,idContrato,idUsuario);
		if(resultado.getError()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(resultado.getMensaje());
		}
		return resultado;
	}

	@Override
	public void bloquearComboTasa(String codigoTasa, Long idUsuario) {
		// TODO Auto-generated method stub
		
	}
}
