package escrituras.beans;

import java.io.Serializable;

public class ResultadoAccionUsuarioBean implements Serializable{

	private static final long serialVersionUID = -3194569442026200149L;
	
	private Boolean error;
	private String mensaje;
	private String kilometros;
	private String respuesta;
	
	public ResultadoAccionUsuarioBean(Boolean error) {
		super();
		this.error = error;
	}
	
	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getKilometros() {
		return kilometros;
	}

	public void setKilometros(String kilometros) {
		this.kilometros = kilometros;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}
