package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class NiveNoValidoExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = -2937223661082140501L;

	public NiveNoValidoExcepcion(String causa) {
		super(causa);
	}

}
