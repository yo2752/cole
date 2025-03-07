package trafico.beans.avpobastigest;

/**
 * Enumeración de los identificadores de las diferentes listas de valores que se 
 * tratan en la consulta de datos a través de un emulador TN3270. 
 * 
 * @author TB·Solutions
 *
 */
public enum ListaValoresTraficoTN3270 {
	
	/**
	 * Identifica una lista que contiene los valores de una inspección técnica de un vehículo. 
	 * Se usa en AVPO.
	 */
	INSPECCION_TECNICA	,
	
	/**
	 * Identifica una lista que contiene los valores de una transferencia de un vehículo. 
	 * Se usa en AVPO.
	 */
	TRANSFERENCIA	,
	
	/**
	 * Identifica una lista que contiene los valores de una carga de un vehículo. 
	 * Se usa en GEST.
	 */
	CARGA	,
	
	/**
	 * Identifica una lista que contiene los valores de una embargo de un vehículo. 
	 * Se usa en GEST.
	 */
	EMBARGO	;
}
