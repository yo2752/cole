package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;

public class DecoratorConsultaEEFFMatriculacion extends TableDecorator {

	public String getRespuesta() {
		ConsultaEEFFDto resultado = (ConsultaEEFFDto) getCurrentRowObject();
		if (resultado.getRespuesta() == null || resultado.getRespuesta().isEmpty()) {
			return "";
		} else if (resultado.getRespuesta().substring(0, 4).equals("EEFF")) {
			return "ERROR";
		}
		return "";
	}

	public String estado() {
		ConsultaEEFFDto resultado = (ConsultaEEFFDto) getCurrentRowObject();
		if (resultado.getEstado() != null && !resultado.getEstado().isEmpty()) {
			return EstadoConsultaEEFF.convertirTexto(resultado.getEstado());
		}
		return "";
	}

}