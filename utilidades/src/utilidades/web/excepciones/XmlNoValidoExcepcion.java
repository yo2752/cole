package utilidades.web.excepciones;

import utilidades.web.CrearSolicitudExcepcion;

public class XmlNoValidoExcepcion extends CrearSolicitudExcepcion {

	private static final long serialVersionUID = -8600901564790137774L;

	public XmlNoValidoExcepcion(String causa) {
		super(causa);
	}

}