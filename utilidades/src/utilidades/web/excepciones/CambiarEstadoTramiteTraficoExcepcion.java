package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class CambiarEstadoTramiteTraficoExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = 8013095750506280928L;

	public CambiarEstadoTramiteTraficoExcepcion(String causa) {
		super(causa);
	}

}
