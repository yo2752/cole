package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;

public class DecoratorTramiteLicencia extends TableDecorator {

	@Override
	public String addRowClass() {
		LcTramiteVO tramite = (LcTramiteVO) getCurrentRowObject();
		if (EstadoLicenciasCam.Anulado.getValorEnum().equals(tramite.getEstado().toString())) {
			return "enlaceAnulado anulado";
		}

		return null;
	}

}
