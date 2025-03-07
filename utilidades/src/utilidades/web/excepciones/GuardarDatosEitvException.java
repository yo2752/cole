package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class GuardarDatosEitvException extends OegamExcepcion {

	private static final long serialVersionUID = -7324136011455998596L;

	public GuardarDatosEitvException(String causa) {
		super(causa);
	}

}
