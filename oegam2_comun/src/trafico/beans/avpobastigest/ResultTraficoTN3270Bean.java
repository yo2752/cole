package trafico.beans.avpobastigest;

import java.util.HashMap;
import java.util.List;

/**
 * Resultado de una solicitud de datos a una aplicaci�n de tr�fico a trav�s de un emulador TN3270.

 *
 */
public class ResultTraficoTN3270Bean {

	private String codigoRespuesta;
	private HashMap<String, String> listaValores;
	private List<SubListaValoresTN3270Bean> sublistasValores;
	private List<String> listas;

	public ResultTraficoTN3270Bean(){
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un c�digo de respuesta.
	 * 
	 * @param codigoRespuesta Codigo que identifica la respuesta obtenida.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta){
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un c�digo de respuesta y una lista de valores.
	 * 
	 * @param codigoRespuesta C�digo que identifica la respuesta obtenida.
	 * @param listaValores Valores obtenidos depu�s de realizar la invocaci�n a una aplicaci�n de tr�fico.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta, HashMap<String, String> listaValores){
		this.codigoRespuesta = codigoRespuesta;
		this.listaValores = listaValores;
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un c�digo de respuesta y una lista de valores y una sublista de valores.
	 * 
	 * @param codigoRespuesta C�digo que identifica la respuesta obtenida.
	 * @param listaValores Valores obtenidos depu�s de realizar la invocaci�n a una aplicaci�n de tr�fico.
	 * @param sublistasValores Sub-listas de valores obtenidos depu�s de realizar la invocaci�n a una aplicaci�n de tr�fico.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta, 
			HashMap<String, String> listaValores,
			List<SubListaValoresTN3270Bean> sublistasValores){
		this.codigoRespuesta = codigoRespuesta;
		this.listaValores = listaValores;
		this.sublistasValores = sublistasValores;
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un c�digo de respuesta y una lista de valores y una sublista de valores.
	 * 
	 * @param codigoRespuesta C�digo que identifica la respuesta obtenida.
	 * @param listaValores Valores obtenidos depu�s de realizar la invocaci�n a una aplicaci�n de tr�fico.
	 * @param sublistasValores Sub-listas de valores obtenidos depu�s de realizar la invocaci�n a una aplicaci�n de tr�fico.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta,
			List<String> listas, HashMap<String, String> listaValores){
		this.codigoRespuesta = codigoRespuesta;
		this.listaValores = listaValores;
		this.listas = listas;
	}

	/**
	 * Obtiene el c�digo que identifica la respuesta obtenida.
	 * @return C�digo de respuesta
	 */
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	/**
	 * Establece el c�digo que identifica la respuesta obtenida.
	 * @param codigoRespuesta C�digo de respuesta
	 */
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * Obtiene los valores le�dos, obtenidos al producirse la invocaci�n a una aplicaci�n de tr�fico.
	 * @return Lista de valores
	 */
	public HashMap<String, String> getListaValores() {
		return listaValores;
	}

	/**
	 * Establece los valores le�dos, obtenidos al producirse la invocaci�n a una aplicaci�n de tr�fico.
	 * @param listaValores Lista de valores
	 */
	public void setListaValores(HashMap<String, String> listaValores) {
		this.listaValores = listaValores;
	}

	/**
	 * Obtiene las sub-listas de valores le�dos, obtenidos al producirse la invocaci�n a una aplicaci�n de tr�fico.  <br>
	 * Este elemento contiene listas de valores, identificadas con ListaValoresTraficoTN3270 (Ejemplo: INSPECCION_TECNICA).<br><br>
	 * Se usa cuando se obtiene, en una misma petici�n, varios valores con un mismo identificador CasillaTraficoTN3270. Por ejemplo, en la aplicaci�n AVPO,
	 * hay un listado de inspecciones t�cnicas, las cuales contienen los mismos campos (Un IT_CONCEPTO, IT_FECHA, etc, por cada inspecci�n t�cnica). 
	 * Cada inspecci�n t�cnica se guardar�a como una sub-lista de valores identificada con INSPECCION_TECNICA. De esta forma se pueden guardar un n�mero
	 * indeterminado de inspecciones t�cnicas.
	 * @return Sub-listas de valores.
	 */
	public List<SubListaValoresTN3270Bean> getSublistasValores() {
		return sublistasValores;
	}

	/**
	 * Establece las sub-listas de valores le�dos, obtenidos al producirse la invocaci�n a una aplicaci�n de tr�fico.  <br>
	 * Este elemento debe contener listas de valores, identificadas con ListaValoresTraficoTN3270 (Ejemplo: INSPECCION_TECNICA). <br><br>
	 * Se usa cuando se obtiene, en una misma petici�n, varios valores con un mismo identificador CasillaTraficoTN3270. Por ejemplo, en la aplicaci�n AVPO,
	 * hay un listado de inspecciones t�cnicas, las cuales contienen los mismos campos (un IT_CONCEPTO, IT_FECHA, etc, por cada inspecci�n t�cnica). 
	 * Cada inspecci�n t�cnica se guardar�a como una sub-lista de valores identificada con INSPECCION_TECNICA. De esta forma se pueden guardar un n�mero
	 * indeterminado de inspecciones t�cnicas.
	 * 
	 * @param sublistasValores Sub-listas de valores.
	 */
	public void setSublistasValores(List<SubListaValoresTN3270Bean> sublistasValores) {
		this.sublistasValores = sublistasValores;
	}

	/**
	 * Devuelve una lista con todos los listados que se generan al realizar una petici�n AVPO y que no
	 * se leen como campos individuales.
	 * 
	 * @return
	 */
	public List<String> getListas() {
		return listas;
	}

	/**
	 * Establece la lista que contiene los listados que se generan en una consulta AVPO y que no se
	 * leen como campos individuales
	 * 
	 * @param listas
	 */
	public void setListas(List<String> listas) {
		this.listas = listas;
	}

}