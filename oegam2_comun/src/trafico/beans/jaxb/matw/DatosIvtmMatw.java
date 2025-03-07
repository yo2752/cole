package trafico.beans.jaxb.matw;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.jfree.util.Log;

import trafico.beans.jaxb.matriculacion.DatosIvtm;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM;;

public class DatosIvtmMatw extends DATOSIMVTM implements DatosIvtm{

	public DatosIvtmMatw (){
	}
	
	public DatosIvtmMatw (DATOSIMVTM d){
		try {
			PropertyUtils.copyProperties(this, d);
		} catch (IllegalAccessException e) {
			Log.error("No se han podido transformar los datos del IVTM.");
		} catch (InvocationTargetException e) {
			Log.error("No se han podido transformar los datos del IVTM.");
		} catch (NoSuchMethodException e) {
			Log.error("No se han podido transformar los datos del IVTM.");
		}
	}
}
