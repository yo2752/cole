package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class ProcesoExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = -8740110528977480724L;

	public ProcesoExcepcion(String causa) {
		super(causa);
	}

}