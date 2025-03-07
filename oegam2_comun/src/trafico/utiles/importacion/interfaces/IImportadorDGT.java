package trafico.utiles.importacion.interfaces;

import escrituras.beans.ResultBean;

/**
 * Interfaz de la logica para la importacion de ficheros que contienen tramites.
 * 
 * @author TB·Solutions
 *
 */
public interface IImportadorDGT {
	
	/**
	 * Metodo que comprueba que la trama de un tramite tiene una longitud correcto y ocupa una
	 * posicion adecuada en el orden de los tramites del fichero.
	 * 
	 * @param lineaTramite Trama del tramite a validar
	 * @param numLinea Posicion que ocupa la linea en el fichero
	 * @return Devuelve un objeto {@link ResultBean} que indica si es correcto o no
	 */
	public ResultBean validaFormato(String lineaTramite, int numLinea);	
	
	/**
	 * Obtiene el bean con las parejas valor-identificador a partir de la cadena que contiene 
	 * el tramite.
	 * 
	 * @param lineaTramite Trama del tramite a importar
	 * @return Devuelve {@link TramitesBean} con los datos del tramite
	 */
	public ResultBean importaValores(String lineaTramite, String colegiadoCabecera);
	
	
	/**
	 * Obtiene el nombre del bean correspondiente al trámite
	 */
	public String getKeyBean();
}
