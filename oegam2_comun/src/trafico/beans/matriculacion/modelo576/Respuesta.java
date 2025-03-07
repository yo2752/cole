package trafico.beans.matriculacion.modelo576;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Respuesta {

	@JsonProperty(value = "correcta")
	private Correcta correcta;
	@JsonProperty(value = "errores")
	private List<String> errores;

	public Respuesta() {
	}

	public Correcta getCorrecta() {
		return correcta;
	}

	public void setCorrecta(Correcta correcta) {
		this.correcta = correcta;
	}

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

}