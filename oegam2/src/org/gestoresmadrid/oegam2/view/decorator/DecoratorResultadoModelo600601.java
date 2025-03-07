package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.modelos.model.enumerados.ErroresWSModelo600601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelo600601Dto;

public class DecoratorResultadoModelo600601 extends TableDecorator {

	public String getCodigoResultado() {
		ResultadoModelo600601Dto resultado = (ResultadoModelo600601Dto) getCurrentRowObject();
		String descError = ErroresWSModelo600601.getNombrePorValor(resultado.getCodigoResultado());
		if (descError != null) {
			return descError;
		}
		return "";
	}

}