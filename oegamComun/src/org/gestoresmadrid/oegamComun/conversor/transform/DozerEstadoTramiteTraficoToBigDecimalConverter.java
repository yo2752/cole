package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

public class DozerEstadoTramiteTraficoToBigDecimalConverter extends DozerConverter<EstadoTramiteTrafico, BigDecimal> {

	public DozerEstadoTramiteTraficoToBigDecimalConverter() {
		super(EstadoTramiteTrafico.class, BigDecimal.class);
	}

	public DozerEstadoTramiteTraficoToBigDecimalConverter(Class<EstadoTramiteTrafico> prototypeA, Class<BigDecimal> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public BigDecimal convertTo(EstadoTramiteTrafico source, BigDecimal destination) {
		if (source == null) {
			return null;
		}
		return new BigDecimal(source.getValorEnum());
	}

	@Override
	public EstadoTramiteTrafico convertFrom(BigDecimal source, EstadoTramiteTrafico destination) {
		if (source == null) {
			return null;
		}
		return EstadoTramiteTrafico.convertir(source.toString());
	}

}
