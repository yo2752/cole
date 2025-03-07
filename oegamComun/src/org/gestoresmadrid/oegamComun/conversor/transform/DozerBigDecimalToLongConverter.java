package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

public class DozerBigDecimalToLongConverter extends DozerConverter<BigDecimal, Long> {

	public DozerBigDecimalToLongConverter(){
		super(BigDecimal.class, Long.class);
	}
	public DozerBigDecimalToLongConverter(Class<BigDecimal> prototypeA, Class<Long> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public Long convertTo(BigDecimal source, Long destination) {
		if (source==null){
			return null;
		}
		return source.longValue();
	}

	@Override
	public BigDecimal convertFrom(Long source, BigDecimal destination) {
		if (source==null){
			return null; 
		}
		return new BigDecimal(source);
	}

}
