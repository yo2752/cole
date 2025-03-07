package org.gestoresmadrid.oegamConversiones.view.dto;

import java.util.List;

import net.sf.navigator.menu.MenuComponent;

public class MiMenuComponentDto extends MenuComponent{


	private static final long serialVersionUID = 1L;

	private List<FuncionDto> _listaFunciones=null;

	public List<FuncionDto> getListaFunciones() {
		return _listaFunciones;
	}

	public void setListaFunciones(List<FuncionDto> listaFunciones) {
		_listaFunciones = listaFunciones;
	}
	
	
}
