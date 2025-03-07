package org.gestoresmadrid.oegam2.view.decorator;

import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoAlmacenTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.transformer.ResultTransformerConsultaTasa;

public class DecoratorTasas extends TableDecorator {

	public String getImportadoIcogam() {
		ResultTransformerConsultaTasa tasa = (ResultTransformerConsultaTasa) getCurrentRowObject();
		if (tasa.getImportadoIcogam() != null) {
			return Decision.convertirValor(tasa.getImportadoIcogam().intValueExact());
		}
		return "";
	}

	public String getBloqueada() {
		ResultTransformerConsultaTasa tasa = (ResultTransformerConsultaTasa) getCurrentRowObject();

		return tasa.getBloqueada() != null && EstadoTasaBloqueo.BLOQUEADA.getValorEnum().equals(tasa.getBloqueada())
				? EstadoTasaBloqueo.BLOQUEADA.getNombreEnum()
				: EstadoTasaBloqueo.DESBLOQUEADA.getNombreEnum();
	}

	public String getTipoAlmacen() {
		ResultTransformerConsultaTasa tasa = (ResultTransformerConsultaTasa) getCurrentRowObject();
		return StringUtils.isNotBlank(tasa.getTipoAlmacen()) ? TipoAlmacenTasa.convertirTexto(tasa.getTipoAlmacen()) : "";
	}
}