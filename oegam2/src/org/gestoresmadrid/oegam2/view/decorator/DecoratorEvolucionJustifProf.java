package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.EvolucionJustifProfesionalesVO;

public class DecoratorEvolucionJustifProf extends TableDecorator {

	public String getEstadoAnterior() {
		EvolucionJustifProfesionalesVO fila = (EvolucionJustifProfesionalesVO) getCurrentRowObject();
		if (fila.getId().getEstadoAnterior() == null) {
			return "";
		}

		switch (fila.getId().getEstadoAnterior().intValueExact()) {
			case 1:
				return "Iniciado";
			case 2:
				return "Pendiente_DGT";
			case 3:
				return "Ok";
			case 4:
				return "Anulado";
			case 5:
				return "Pendiente autorizacion colegio";
			default:
				return "";
		}
	}

	public String getEstadoNuevo() {
		EvolucionJustifProfesionalesVO fila = (EvolucionJustifProfesionalesVO) getCurrentRowObject();
		if (fila.getId().getEstado() == null) {
			return "";
		}

		switch (fila.getId().getEstado().intValueExact()) {
			case 1:
				return "Iniciado";
			case 2:
				return "Pendiente_DGT";
			case 3:
				return "Ok";
			case 4:
				return "Anulado";
			case 5:
				return "Pendiente autorizacion colegio";
			default:
				return null;
		}
	}

}