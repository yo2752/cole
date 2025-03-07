package org.gestoresmadrid.oegam2.trafico.consulta.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.ConsultaTramiteTraficoResultBean;

public class DecoradorConsTramiteTrafico extends TableDecorator {

	public DecoradorConsTramiteTrafico() {}

	public String addRowClass() {
		ConsultaTramiteTraficoResultBean row = (ConsultaTramiteTraficoResultBean) getCurrentRowObject();

		String impreso = row.getEstado();

		if (impreso != null && (impreso.equals(EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum()) || impreso.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum())
				|| impreso.equals(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getNombreEnum()) || impreso.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getNombreEnum()))) {
			return "enlaceImpreso impreso";
		} else if (impreso != null && !impreso.isEmpty() && EstadoTramiteTrafico.Anulado.getNombreEnum().equals(impreso)) {
			return "enlaceAnulado anulado";
		}
		return null;
	}
}
