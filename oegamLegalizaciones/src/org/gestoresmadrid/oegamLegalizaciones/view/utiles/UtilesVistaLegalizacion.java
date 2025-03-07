package org.gestoresmadrid.oegamLegalizaciones.view.utiles;


public class UtilesVistaLegalizacion {

	
	public static TipoDocumento[] getTiposDocumentos(){
    	return TipoDocumento.values();
    }
	
	public static TipoSiNo[] getTipoSiNo(){
    	return TipoSiNo.values();
    }
	
	public static EstadoPeticion[] getEstadoPeticion(){
		return EstadoPeticion.values();
	}
	
	public static ClaseDocumento[] getClaseDocumento(){
    	return ClaseDocumento.values();
    }
}
