package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class ExpedienteSinSolicitudesExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = 3963480773503150636L;

	public ExpedienteSinSolicitudesExcepcion(String causa) {
		super(causa);
	}

}
