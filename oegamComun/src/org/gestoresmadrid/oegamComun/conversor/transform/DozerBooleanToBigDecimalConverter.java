package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

public class DozerBooleanToBigDecimalConverter extends DozerConverter<Boolean, BigDecimal>{

	public DozerBooleanToBigDecimalConverter() {
		super(Boolean.class, BigDecimal.class);
	}
	
	public DozerBooleanToBigDecimalConverter(Class<Boolean> prototypeA,	Class<BigDecimal> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public BigDecimal convertTo(Boolean source, BigDecimal destination) {
		if (source==null){
			return null;
		}
		if(source){
			return BigDecimal.ONE;
		}else {
			return BigDecimal.ZERO;
		}
		
	}

	@Override
	public Boolean convertFrom(BigDecimal source, Boolean destination) {
		if(source == null){
			return null;
		}
		if(source.equals(BigDecimal.ZERO)){
			return false;
		}else{
			return true;
		}
	}

}
