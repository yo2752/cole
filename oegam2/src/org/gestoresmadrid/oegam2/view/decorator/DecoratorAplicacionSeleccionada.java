package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;

public class DecoratorAplicacionSeleccionada extends TableDecorator {

	public DecoratorAplicacionSeleccionada() {}

	@Override
	public String addRowId() {
		FuncionDto row = (FuncionDto) getCurrentRowObject();
		return row.getCodigoFuncion();
	}

	public String addRowClass() {
		FuncionDto row = (FuncionDto) getCurrentRowObject();
		String salida = "";
		if (row.getCodFuncionPadre() != null) {
			salida = "hijo " + row.getCodFuncionPadre();
		}
		return salida;
	}
}