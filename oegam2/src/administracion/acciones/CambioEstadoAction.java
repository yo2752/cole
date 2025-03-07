package administracion.acciones;

import general.acciones.ActionBase;

@SuppressWarnings("serial")
public class CambioEstadoAction extends ActionBase {

	public static final String POP_UP = "popUp";

	public String cargarPopUp() {
		return POP_UP;
	}

}