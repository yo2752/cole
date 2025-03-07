package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

public class DozerBigDecimalToSNConverter extends DozerConverter<BigDecimal, String> {

	public DozerBigDecimalToSNConverter(){
		super(BigDecimal.class, String.class);
	}
	public DozerBigDecimalToSNConverter(Class<BigDecimal> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(BigDecimal source, String destination) {
		if (source==null){
			return null;
		}
		if (BigDecimal.ONE.equals(source)){
			return "S";
		}
		return "N";
	}

	@Override
	public BigDecimal convertFrom(String source, BigDecimal destination) {
		if (source==null|| source.isEmpty()){
			return null; 
		}
		if (source.equals("S")){
			return BigDecimal.ONE;
		}
		return BigDecimal.ZERO;
	}

}
