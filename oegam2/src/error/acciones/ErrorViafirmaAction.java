package error.acciones;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.viafirma.cliente.exception.CodigoError;

/*
 * Viafirma encapsula sus errores en la enum CodigoError. Desde
 * un objeto de esta enum se accede a la información relativa
 * a las excepciones de Viafirma.
 */

public class ErrorViafirmaAction implements SessionAware {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> sesion;

	public String execute() {
		CodigoError codError = (CodigoError) sesion.get("codError");
		// Coloca en sesión el mensaje del error de Viafirma:
		sesion.put("mensaje", codError.getMensaje());
		return "success";
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sesion = arg0;
	}
}