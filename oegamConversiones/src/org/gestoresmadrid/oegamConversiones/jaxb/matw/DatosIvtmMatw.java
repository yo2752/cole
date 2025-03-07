package org.gestoresmadrid.oegamConversiones.jaxb.matw;
//TODO MPC. Cambio IVTM. Clase añadida.
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.FORMATOGA.MATRICULACION.DATOSIMPUESTOS.DATOSIMVTM;
import org.jfree.util.Log;

public class DatosIvtmMatw extends DATOSIMVTM implements DatosIvtm {

	private static final String NO_SE_HAN_PODIDO_TRANSFORMAR_DATOS_IVTM = "No se han podido transformar los datos del IVTM.";

	public DatosIvtmMatw (){
	}

	public DatosIvtmMatw (DATOSIMVTM d){
		try {
			PropertyUtils.copyProperties(this, d);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			Log.error(NO_SE_HAN_PODIDO_TRANSFORMAR_DATOS_IVTM);
		}
	}

}