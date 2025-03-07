package trafico.beans.matriculacion.modelo576;

import org.codehaus.jackson.annotate.JsonProperty;

public class RespuestaJsonRecibida {

	@JsonProperty(value = "respuesta")
	private Respuesta respuesta;

	public RespuestaJsonRecibida() {

	}

	public Respuesta getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Respuesta respuesta) {
		this.respuesta = respuesta;
	}

}
