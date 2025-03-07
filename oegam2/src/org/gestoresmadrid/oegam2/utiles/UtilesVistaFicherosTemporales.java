package org.gestoresmadrid.oegam2.utiles;

import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaFicherosTemporales {

	private static UtilesVistaFicherosTemporales utilesVistaFicherosTemporales;

	private UtilesVistaFicherosTemporales() {
		super();
	}

	public static UtilesVistaFicherosTemporales getInstance() {
		if (utilesVistaFicherosTemporales == null) {
			utilesVistaFicherosTemporales = new UtilesVistaFicherosTemporales();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaFicherosTemporales);
		}
		return utilesVistaFicherosTemporales;
	}

	public TipoFicheros[] getComboTipoDocumentos(){
		return TipoFicheros.values();
	}

}