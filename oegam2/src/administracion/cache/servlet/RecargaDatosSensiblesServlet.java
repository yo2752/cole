package administracion.cache.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trafico.datosSensibles.utiles.UtilesVistaDatosSensibles;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

public class RecargaDatosSensiblesServlet extends RecargaCacheServlet{

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(RecargaDatosSensiblesServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Recibo una peticion de refresco combos por POST desde la IP: "+request.getRemoteAddr());
		log.info("IP detras del proxy: "+request.getHeader("X-FORWARDED-FOR"));

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (validarAcceso(request)) {
			log.info("IP valida: "+request.getRemoteAddr());
			ResultBean resultBean = UtilesVistaDatosSensibles.getInstance().recargarDatosSensibles();
			if (resultBean.getError()) {
				out.println("error");
				log.error("ha ocurrido un error al refrescar los combos en "+InetAddress.getLocalHost().getHostAddress());
			} else {
				out.println("ok");
				log.error("datos sensibles refrescados en "+InetAddress.getLocalHost().getHostAddress());
			}
		} else {
			out.println("unauthorize");
			log.error("Se ha registrado una peticion no autorizada de refresco de combos desde la ip " + request.getRemoteAddr());
		}

	}

}