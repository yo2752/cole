package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class RespuestaDesconocidaWS extends OegamExcepcion {

	private static final long serialVersionUID = -4259875179650235555L;

	public RespuestaDesconocidaWS(String causa) {
		super(causa);
	}

}
