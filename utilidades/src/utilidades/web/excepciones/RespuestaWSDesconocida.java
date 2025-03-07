package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class RespuestaWSDesconocida extends OegamExcepcion {

	private static final long serialVersionUID = -1981913171280582820L;

	public RespuestaWSDesconocida(String causa) {
		super(causa);
	}

}