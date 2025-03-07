package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class ValidacionJustificantePorFechaExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = 7386199599884114642L;

	public ValidacionJustificantePorFechaExcepcion(String causa) {
		super(causa);
	}

}