package org.gestoresmadrid.oegam2.jnlp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.oegam2comun.presentacion05.service.ServicioPresentacion05;
import org.gestoresmadrid.oegam2comun.presentacion05.view.bean.Presentacion05Bean;
import org.gestoresmadrid.oegam2comun.presentacion05.view.bean.ResultadoPresentacion05Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class JnlpServlet05 extends HttpServlet {

	private static final long serialVersionUID = 330471947268877018L;

	@Autowired
	private ServicioPresentacion05 servicioPresentacion05;

	public JnlpServlet05() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String numExpediente = (String) request.getParameter("numExpediente");
			if (StringUtils.isNotBlank(numExpediente)) {
				ResultadoPresentacion05Bean resultado = servicioPresentacion05.presentacionJnlp(new BigDecimal(numExpediente));
				String jnlp = null;
				if (resultado != null && !resultado.getError()) {
					jnlp = getJnlp(resultado.getPresentacion05Bean(), getUrl(request));
				} else {
					jnlp = getJnlpError(resultado, getUrl(request));
				}
				if (jnlp != null) {
					response.setContentType("application/x-java-jnlp-file");
					out.print(jnlp);
				}
			}
		} finally {
			out.close();
		}
	}

	private String getJnlp(Presentacion05Bean presentacionBean, String url) {
		String jnlp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" + "<jnlp spec=\"1.0+\" codebase=\"" + url + "\"> \n" + "<information> \n" + "<title>Jnlp Oegam 05</title> \n"
				+ "</information> \n" + "<security> \n" + "<all-permissions/> \n" + "</security> \n" + "<resources> \n" + "<j2se version=\"1.6+\" /> \n" + "<jar href=\"oegam05.jar\"/> \n"
				+ "</resources> \n" + "<application-desc main-class=\"org.gestoresmadrid.oegam05.main.Main\"> \n";

		if (StringUtils.isNotBlank(presentacionBean.getBastidor())) {
			jnlp += "<argument>" + presentacionBean.getBastidor() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getNifGestor())) {
			jnlp += "<argument>" + presentacionBean.getNifGestor() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getApellidosNombreGestor())) {
			jnlp += "<argument>" + presentacionBean.getApellidosNombreGestor() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getEjercicioServicio())) {
			jnlp += "<argument>" + presentacionBean.getEjercicioServicio() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getUsado())) {
			jnlp += "<argument>" + presentacionBean.getUsado() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getProcedencia())) {
			jnlp += "<argument>" + presentacionBean.getProcedencia() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getKmUso())) {
			jnlp += "<argument>" + presentacionBean.getKmUso() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getHorasUso())) {
			jnlp += "<argument>" + presentacionBean.getHorasUso() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getFechaPrimMatriculacion())) {
			jnlp += "<argument>" + presentacionBean.getFechaPrimMatriculacion() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getFechaMatriculacion())) {
			jnlp += "<argument>" + presentacionBean.getFechaMatriculacion() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getCombustible())) {
			jnlp += "<argument>" + presentacionBean.getCombustible() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getMarca())) {
			jnlp += "<argument>" + presentacionBean.getMarca() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getModelo())) {
			jnlp += "<argument>" + presentacionBean.getModelo() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getObservaciones())) {
			jnlp += "<argument>" + presentacionBean.getObservaciones() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getCodigoITV())) {
			jnlp += "<argument>" + presentacionBean.getCodigoITV() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getTipoITV())) {
			jnlp += "<argument>" + presentacionBean.getTipoITV() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getTipoTarjetaITV())) {
			jnlp += "<argument>" + presentacionBean.getTipoTarjetaITV() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getCilindrada())) {
			jnlp += "<argument>" + presentacionBean.getCilindrada() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getCriterioConstruccion())) {
			jnlp += "<argument>" + presentacionBean.getCriterioConstruccion() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getCriterioUtilizacion())) {
			jnlp += "<argument>" + presentacionBean.getCriterioUtilizacion() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getClasificacion())) {
			jnlp += "<argument>" + presentacionBean.getClasificacion() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getCo2())) {
			jnlp += "<argument>" + presentacionBean.getCo2() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getNifIntegrador())) {
			jnlp += "<argument>" + presentacionBean.getNifIntegrador() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getApellidosNombreIntegrador())) {
			jnlp += "<argument>" + presentacionBean.getApellidosNombreIntegrador() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getReduccion05())) {
			jnlp += "<argument>" + presentacionBean.getReduccion05() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		if (StringUtils.isNotBlank(presentacionBean.getReduccion05())) {
			jnlp += "<argument>" + presentacionBean.getReduccion05() + "</argument>  \n";
		} else {
			jnlp += "<argument>NA</argument>  \n";
		}

		jnlp += "</application-desc> \n" + "</jnlp>";

		return jnlp;
	}

	private String getJnlpError(ResultadoPresentacion05Bean resultado, String url) {
		String jnlp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" + "<jnlp spec=\"1.0+\" codebase=\"" + url + "\"> \n" + "<information> \n" + "<title>Jnlp Oegam 05</title> \n"
				+ "</information> \n" + "<security> \n" + "<all-permissions/> \n" + "</security> \n" + "<resources> \n" + "<j2se version=\"1.6+\" /> \n" + "<jar href=\"oegam05.jar\"/> \n"
				+ "</resources> \n" + "<application-desc main-class=\"org.gestoresmadrid.oegam05.main.Main\"> \n";

		if (StringUtils.isNotBlank(resultado.getMensaje())) {
			jnlp += "<argument>" + resultado.getMensaje() + "</argument>  \n";
		} else if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
			String mensaje = "";
			for (String m : resultado.getListaMensajes()) {
				mensaje += m + ". ";
			}
			jnlp += "<argument>" + mensaje + "</argument>  \n";
		} else {
			jnlp += "<argument>Error al presentar 05. Pongase en contacto con el Colegio.</argument>  \n";
		}

		jnlp += "</application-desc> \n" + "</jnlp>";

		return jnlp;
	}

	private String getUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	}
}
