package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

@SuppressWarnings("serial")
public class AccionException extends OegamExcepcion {

	public AccionException(String causa) {
		super(causa);
	}

}
