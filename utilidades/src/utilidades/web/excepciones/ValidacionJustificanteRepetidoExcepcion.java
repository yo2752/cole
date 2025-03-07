package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class ValidacionJustificanteRepetidoExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = 5874349162765667265L;

	public ValidacionJustificanteRepetidoExcepcion(String causa) {
		super(causa);
	}

}