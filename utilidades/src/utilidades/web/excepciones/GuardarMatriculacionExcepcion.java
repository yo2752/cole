package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class GuardarMatriculacionExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = 5521751173127969886L;

	public GuardarMatriculacionExcepcion(String causa) {
		super(causa);
	}

}
