package trafico.beans.matriculacion.modelo576;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Errores {

	@JsonProperty(value = "errores")
	List<String> errores;

	public Errores() {

	}

	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

}
