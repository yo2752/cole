package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

public class DozerBigDecimalToStringRegistradoresConverter extends DozerConverter<BigDecimal, String>{

	public DozerBigDecimalToStringRegistradoresConverter(Class<BigDecimal> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}
	
	public DozerBigDecimalToStringRegistradoresConverter() {
		super(BigDecimal.class, String.class);
	}

	@Override
	public String convertTo(BigDecimal source, String destination) {
		if(source == null){
			return null;
		}
		if(source.toString().contains(".")){
			destination = source.toString().replace(".",",");
		}else{
			destination = source.toString();
		}
		return destination;
	}

	@Override
	public BigDecimal convertFrom(String source, BigDecimal destination) {
		if(source == null || source.isEmpty()){
			return null;
		}
		if(source.contains(".")){
			source = source.replace(".",",");
		}
		
		return new BigDecimal(source);
	}

}
