package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

public class DozerBigDecimalToStringConverter extends DozerConverter<BigDecimal, String>{

	public DozerBigDecimalToStringConverter(Class<BigDecimal> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}
	
	public DozerBigDecimalToStringConverter() {
		super(BigDecimal.class, String.class);
	}

	@Override
	public String convertTo(BigDecimal source, String destination) {
		if(source == null){
			return null;
		}
		return source.toString();
	}

	@Override
	public BigDecimal convertFrom(String source, BigDecimal destination) {
		if(source == null || source.isEmpty()){
			return null;
		}
		if(source.contains(",")){
			source = source.replace(",",".");
		}
		
		return new BigDecimal(source);
	}

}
