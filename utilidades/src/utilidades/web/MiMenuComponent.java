package utilidades.web;

import java.util.List;

import net.sf.navigator.menu.MenuComponent;

public class MiMenuComponent extends MenuComponent{


	private static final long serialVersionUID = 1L;

	private List<Funcion> _listaFunciones=null;

	public List<Funcion> getListaFunciones() {
		return _listaFunciones;
	}

	public void setListaFunciones(List<Funcion> listaFunciones) {
		_listaFunciones = listaFunciones;
	}
	
	
}
