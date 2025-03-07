package org.icogam.sanciones.Utiles;

public class UtilesVistaSancion {
	
	private static UtilesVistaSancion utilesVistaSancion;
	
	private UtilesVistaSancion() {
		super();
	}
	
	public static UtilesVistaSancion getInstance() {
		if (utilesVistaSancion == null) {
			utilesVistaSancion = new UtilesVistaSancion();
		}
		return utilesVistaSancion;
	}
	
	public Motivo[] getMotivos(){
    	return Motivo.values();
    }
	
	public EstadoSancion[] getEstadoSancion(){
    	return EstadoSancion.values();
    }
	
	public TipoImpresion[] getTipoImpresion(){
    	return TipoImpresion.values();
    }
}
