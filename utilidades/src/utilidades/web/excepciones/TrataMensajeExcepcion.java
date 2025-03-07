package utilidades.web.excepciones;

import utilidades.web.OegamExcepcion;

public class TrataMensajeExcepcion extends OegamExcepcion {

	private static final long serialVersionUID = -1949394832231907724L;

	public TrataMensajeExcepcion(String causa) {
		super(causa);
	}

}
