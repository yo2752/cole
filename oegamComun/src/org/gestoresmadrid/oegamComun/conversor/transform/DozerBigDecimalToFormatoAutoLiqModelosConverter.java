package org.gestoresmadrid.oegamComun.conversor.transform;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.dozer.DozerConverter;

public class DozerBigDecimalToFormatoAutoLiqModelosConverter extends DozerConverter<BigDecimal, String> {

	public DozerBigDecimalToFormatoAutoLiqModelosConverter(){
		super(BigDecimal.class, String.class);
	}
	public DozerBigDecimalToFormatoAutoLiqModelosConverter(Class<BigDecimal> prototypeA, Class<String> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public String convertTo(BigDecimal source, String destination) {
		if (source==null){
			return "";
		}
		DecimalFormat df = null;
		if (source.compareTo(new BigDecimal(1000)) == 0 || source.compareTo(new BigDecimal(1000)) == 1){
			df = new DecimalFormat("0,000.00");
		}else{
			df = new DecimalFormat("0.00");
		}
		return df.format(source.floatValue());
	}

	@Override
	public BigDecimal convertFrom(String source, BigDecimal destination) {
		if (source==null|| source.isEmpty()){
			return null; 
		}
		return new BigDecimal(source.replace(",",""));
	}

}
