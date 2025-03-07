package org.gestoresmadrid.oegam2comun.contratos.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegam2comun.model.service.ServicioTipoTramite;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TipoTramiteDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ContratosAjax extends ActionBase implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 4123214301695047067L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ContratosAjax.class);

	private static final String TOKEN_SEPARADOR = "||";

	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;

	@Autowired
	private ServicioTipoTramite servicioTipoTramite;

	private HttpServletRequest getServletRequest() {
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

	public void buscarTipoTramitePorAplicacion() throws IOException {
		log.debug("buscarTipoTramite");

		String codigoAplicacion = getServletRequest().getParameter("codigoAplicacion");

		List<TipoTramiteDto> listTipoTramiteConsulta = servicioTipoTramite.getTipoTramitePorAplicacion(codigoAplicacion);

		String resultado = "";
		if (listTipoTramiteConsulta != null && !listTipoTramiteConsulta.isEmpty()) {
			StringBuilder respuesta = new StringBuilder();
			for (TipoTramiteDto tipoTramite : listTipoTramiteConsulta) {
				respuesta.append(tipoTramite.getDescripcion() + ";" + tipoTramite.getTipoTramite() + TOKEN_SEPARADOR);
			}
			resultado = respuesta.substring(0, respuesta.length() - 2);
		}
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(resultado);
	}
}