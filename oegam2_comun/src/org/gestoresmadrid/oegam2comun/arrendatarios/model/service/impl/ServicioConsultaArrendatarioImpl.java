package org.gestoresmadrid.oegam2comun.arrendatarios.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.dao.ArrendatarioDao;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.model.service.ServicioConsultaArrendatario;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaArrendatarioImpl implements ServicioConsultaArrendatario {
	private static final long serialVersionUID = 8814053410607035682L;
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioArrendatario servicioArrendatario;
	@Autowired
	ArrendatarioDao consultaArrendatarioDao;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaArrendatarioImpl.class);

	public List<ConsultaArrendatarioBean> convertirListaEnBeanPantalla(List<ArrendatarioVO> lista) {

		if (lista != null && !lista.isEmpty()) {
			List<ConsultaArrendatarioBean> listaBean = new ArrayList<ConsultaArrendatarioBean>();
			for (ArrendatarioVO arrendatarioVO : lista) {
				ConsultaArrendatarioBean arrendararioBean = conversor.transform(arrendatarioVO, ConsultaArrendatarioBean.class);
				arrendararioBean.setEstadotxt(EstadoCaycEnum.convertirEstadoBigDecimal(arrendatarioVO.getEstado()));
				arrendararioBean.setOperaciontxt(TipoOperacionCaycEnum.convertirTexto(arrendatarioVO.getOperacion()));
				listaBean.add(arrendararioBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultConsultaArrendatarioBean eliminar(String codSeleccionados, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(false);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaArrendatarioBean resultCambioEstado = servicioArrendatario.cambiarEstado(new BigDecimal(numExpediente), idUsuario, new BigDecimal(EstadoCaycEnum.Anulado.getValorEnum()),
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
	public ResultConsultaArrendatarioBean cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaArrendatarioBean resultCambioEstado = servicioArrendatario.cambiarEstado(new BigDecimal(numExpediente), idUsuario, new BigDecimal(estadoNuevo), Boolean.TRUE);
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
	public ResultConsultaArrendatarioBean consultar(String codSeleccionados, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaArrendatarioBean resultGetConsulta = servicioArrendatario.consultarArrendatario(new BigDecimal(numExpediente), idUsuario);
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk("Peticion encolada correctamente para el numero de expediente:" + numExpediente);
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para realizar su consulta.");
		}
		return resultado;
	}

	@Override
	public ResultConsultaArrendatarioBean modificar(String codSeleccionados, BigDecimal idUsuario, Date fechaIni, Date fechaFin) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaArrendatarioBean resultGetConsulta = servicioArrendatario.modificarArrendatario(new BigDecimal(numExpediente), idUsuario, fechaIni, fechaFin);
				if (resultGetConsulta.getError()) {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				} else {
					resultado.addOk();
					resultado.addListaOk("Peticion de modificacion encolada correctamente para el numero de expediente:" + resultGetConsulta.getNumExpediente().toString());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para realizar su consulta.");
		}
		return resultado;
	}

	@Override
	public ResultConsultaArrendatarioBean validar(String codSeleccionados, BigDecimal idUsuario) {
		ResultConsultaArrendatarioBean resultado = new ResultConsultaArrendatarioBean(Boolean.FALSE);
		if (codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] sNumExpedientes = codSeleccionados.split("-");
			for (String numExpediente : sNumExpedientes) {
				ResultConsultaArrendatarioBean resultGetConsulta = servicioArrendatario.getArrendatarioDto(new BigDecimal(numExpediente));
				if (!resultGetConsulta.getError()) {
					resultGetConsulta = servicioArrendatario.validarArrendatario(resultGetConsulta.getArrendatarioDto(), idUsuario);
					if (resultGetConsulta.getError()) {
						resultado.addError();
						resultado.addListaError(resultGetConsulta.getMensaje());
					} else {
						resultado.addOk();
						resultado.addListaOk("Validado correctamente para el numero de expediente: " + numExpediente);
					}
				} else {
					resultado.addError();
					resultado.addListaError(resultGetConsulta.getMensaje());
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar alguna consulta para realizar su consulta.");
		}
		return resultado;
	}
}
