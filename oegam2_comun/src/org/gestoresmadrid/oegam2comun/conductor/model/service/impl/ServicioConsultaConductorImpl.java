package org.gestoresmadrid.oegam2comun.conductor.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.conductor.model.dao.ConductorDao;
import org.gestoresmadrid.core.conductor.model.vo.ConductorVO;
import org.gestoresmadrid.core.conductor.model.vo.ConsultaConductorVO;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConductor;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConsultaConductor;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaConductorImpl implements ServicioConsultaConductor {

	private static final long serialVersionUID = 6664874147211628014L;

	@Autowired
	Conversor conversor;

	@Autowired
	ConductorDao consultaConductorDao;

	ConductorVO consultaConductorVO;

	@Autowired
	ServicioConductor servicioConductor;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaConductorImpl.class);

	private static final String DEBE_SELECCIONAR_CONSULTA = "Debe seleccionar alguna consulta para realizar su consulta.";

	@Override
	public List<ConsultaConductorBean> convertirListaEnBeanPantalla(List<ConsultaConductorVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<ConsultaConductorBean> listaBean = new ArrayList<>();
			for (ConsultaConductorVO consultaConductorVO : lista) {
				ConsultaConductorBean conductorBean = conversor.transform(consultaConductorVO,
						ConsultaConductorBean.class);
				conductorBean.setEstadotxt(EstadoCaycEnum.convertirEstadoBigDecimal(consultaConductorVO.getEstado()));

				conductorBean.setConsentimientotxt(consultaConductorVO.getConsentimiento() ? "Existe" : "No existe");

				conductorBean.setOperaciontxt(TipoOperacionCaycEnum.convertirTexto(consultaConductorVO.getOperacion()));
				listaBean.add(conductorBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultConsultaConductorBean cambiarEstado(String codSeleccionados, String estadoNuevo,
			BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaConductorBean resultCambioEstado = servicioConductor.cambiarEstado(
						new BigDecimal(numExpediente), idUsuario, new BigDecimal(estadoNuevo), Boolean.TRUE);
				if (resultCambioEstado.getError()) {
					resultado.addError();
					resultado.addListaError(resultCambioEstado.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultCambioEstado.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultado;
	}

	@Override
	public ResultConsultaConductorBean eliminar(String codSeleccionados, BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(false);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaConductorBean resultCambioEstado = servicioConductor.cambiarEstado(
						new BigDecimal(numExpediente), idUsuario, new BigDecimal(EstadoCaycEnum.Anulado.getValorEnum()),
						Boolean.TRUE);
				if (resultCambioEstado.getError()) {
					resultado.addError();
					resultado.addListaError(resultCambioEstado.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(resultCambioEstado.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para eliminar.");
		}
		return resultado;
	}

	@Override
	public ResultConsultaConductorBean consultar(String codSeleccionados, BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaConductorBean resultGetConsulta = servicioConductor
						.consultarConductor(new BigDecimal(numExpediente), idUsuario);
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk(
							"Petición encolada correctamente para el número de expediente:" + numExpediente);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(DEBE_SELECCIONAR_CONSULTA);
		}
		return resultado;
	}

	@Override
	public ResultConsultaConductorBean validar(String codSeleccionados, BigDecimal idUsuario) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaConductorBean resultGetConsulta = servicioConductor
						.getConductorDto(new BigDecimal(numExpediente));
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					resultGetConsulta = servicioConductor.validarConductor(resultGetConsulta.getConductorDto(),
							idUsuario);
					if (resultGetConsulta.getError()) {
						resultado.addError();
						resultado.addListaError(resultGetConsulta.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk("Validado correctamente para el número de expediente:" + numExpediente);
					}
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(DEBE_SELECCIONAR_CONSULTA);
		}
		return resultado;
	}

	@Override
	public ResultConsultaConductorBean modificarFechas(String codSeleccionados, BigDecimal idUsuario, Fecha fechaIni,
			Fecha fechaFin) {
		ResultConsultaConductorBean resultado = new ResultConsultaConductorBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaConductorBean resultGetConsulta = null;
				try {
					resultGetConsulta = servicioConductor.modificarConductor(new BigDecimal(numExpediente), idUsuario,
							fechaIni.getDate(), fechaFin.getDate());
				} catch (ParseException e) {
					e.printStackTrace();
					resultado.addError();
					resultado.addListaError("Error al tratar las fechas.");
				}
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk("Petición de modificación encolada correctamente para el número de expediente: "
									+ resultGetConsulta.getNumExpediente().toString());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(DEBE_SELECCIONAR_CONSULTA);
		}
		return resultado;
	}

}