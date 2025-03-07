package org.gestoresmadrid.oegamComun.conversor.transform;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.dozer.DozerConverter;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DozerXmlCalendarConverter extends DozerConverter<XMLGregorianCalendar, Date> {

	private static final ILoggerOegam log = LoggerOegam.getLogger(DozerXmlCalendarConverter.class);
	
	public DozerXmlCalendarConverter(){
		super(XMLGregorianCalendar.class, Date.class);
	}
	public DozerXmlCalendarConverter(Class<XMLGregorianCalendar> prototypeA, Class<Date> prototypeB) {
		super(prototypeA, prototypeB);
	}

	@Override
	public Date convertTo(XMLGregorianCalendar source, Date destination) {
		if (source==null){
			return null;
		}
		return source.toGregorianCalendar().getTime();
	}

	@Override
	public XMLGregorianCalendar convertFrom(Date source, XMLGregorianCalendar destination) {
		if (source == null){
			return null; 
		}
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(source);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (DatatypeConfigurationException e) {
			log.error("Error: ",e);
		}
		return xmlCalendar;
	}

}
