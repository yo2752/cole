package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

public class DozerBigDecimalTagVacioConverter extends DozerConverter<BigDecimal, String> {

	public DozerBigDecimalTagVacioConverter(){
		super(BigDecimal.class, String.class);
	}
	public DozerBigDecimalTagVacioConverter(Class<BigDecimal> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(BigDecimal source, String destination) {
		if (source==null){
			return "";
		}
		return source.toString();
	}

	@Override
	public BigDecimal convertFrom(String source, BigDecimal destination) {
		if (source == null){
			return null; 
		}
		return new BigDecimal(source);
	}

}
