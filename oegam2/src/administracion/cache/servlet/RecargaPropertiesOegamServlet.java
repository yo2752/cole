package administracion.cache.servlet;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;

import administracion.utiles.PropertiesUtils;
import general.utiles.Constantes;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.utiles.UtilesExcepciones;

public class RecargaPropertiesOegamServlet extends RecargaCacheServlet {

	private static final long serialVersionUID = -8788646504338446135L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(RecargaPropertiesOegamServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info("Recibo una peticion de recargar de properties de oegam por POST desde la IP: "+req.getRemoteAddr());
		log.info("IP detras del proxy: "+req.getHeader("X-FORWARDED-FOR"));
		String ipFronta = obtenerIp();
		try {
			ActionContext.getContext().getApplication().put(Constantes.PROPIEDADES, new PropertiesUtils().refresh());
			log.info("Properties refrescadas en la maquina: " + ipFronta);
		} catch (Throwable ex) {
			log.error("ha sucedido un error a la hora de refrescar las properties en la maquina: " + ipFronta + ", error: ", ex);
			log.error(UtilesExcepciones.stackTraceAcadena(ex, 3));
		}
	}

	private String obtenerIp() {
		String ipFrontal = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			String ipAccesso = address.getHostAddress();
			int num = address.toString().indexOf('/');
			if (num > 0) {
				ipFrontal = address.toString().substring(num + 1);
			}
		} catch (Throwable e1) {
			log.error("Error UnknownHostException: ", e1);
		}
		return ipFrontal;
	}
}