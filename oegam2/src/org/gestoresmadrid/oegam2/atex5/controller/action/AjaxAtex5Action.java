package org.gestoresmadrid.oegam2.atex5.controller.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AjaxAtex5Action extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = -913812036374886728L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AjaxAtex5Action.class);

	private HttpServletRequest servletRequest; // Para AJAX
	private HttpServletResponse servletResponse; // Para AJAX

	private static final String TOKEN_SEPARADOR = "||";

	private BigDecimal numExpediente;

	private String idContratoSeleccionado;

	@Autowired
	ServicioPersonaAtex5 servicioPersonaAtex5;

	@Autowired
	ServicioVehiculoAtex5 servicioVehiculoAtex5;

	@Autowired
	ServicioTasa servicioTasa;

	public void recuperarTasa() throws Throwable {
		String resultado = "";
		if (idContratoSeleccionado != null && !idContratoSeleccionado.isEmpty()) {
			List<TasaDto> tasas = servicioTasa.obtenerTasasContrato(Long.valueOf(idContratoSeleccionado), TipoTasa.CuatroUno.getValorEnum());
			if (tasas != null && !tasas.isEmpty()) {
				StringBuilder respuesta = new StringBuilder();
				for (TasaDto ts : tasas) {
					respuesta.append(ts.getCodigoTasa() + ";" + ts.getCodigoTasa() + TOKEN_SEPARADOR);
				}
				resultado = respuesta.substring(0, respuesta.length() - 2);
			}
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		log.debug(resultado);
		out.print(resultado);
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getIdContratoSeleccionado() {
		return idContratoSeleccionado;
	}

	public void setIdContratoSeleccionado(String idContratoSeleccionado) {
		this.idContratoSeleccionado = idContratoSeleccionado;
	}
}