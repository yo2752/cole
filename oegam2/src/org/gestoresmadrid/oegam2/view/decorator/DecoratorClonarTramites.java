package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramitesDto;

public class DecoratorClonarTramites extends TableDecorator {

	public String getEstado() {
		ClonarTramitesDto fila = (ClonarTramitesDto) getCurrentRowObject();
		return EstadoTramiteTrafico.convertirTexto(fila.getEstado());
	}

	public String getTipoTramite() {
		ClonarTramitesDto fila = (ClonarTramitesDto) getCurrentRowObject();
		return TipoTramiteTrafico.convertirTexto(fila.getTipoTramite());
	}

}