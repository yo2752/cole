package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class DescontarCreditosExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = -812054246140220092L;

	public DescontarCreditosExcepcion(String causa) {
		super(causa);
	}

}
