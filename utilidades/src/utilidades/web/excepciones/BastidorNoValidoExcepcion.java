package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class BastidorNoValidoExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = -8352459569187678803L;

	public BastidorNoValidoExcepcion(String causa) {
		super(causa);
	}

}
