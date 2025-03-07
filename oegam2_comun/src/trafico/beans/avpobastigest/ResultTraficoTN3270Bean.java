package trafico.beans.avpobastigest;

import java.util.HashMap;
import java.util.List;

/**
 * Resultado de una solicitud de datos a una aplicación de tráfico a través de un emulador TN3270.

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
	 * Inicializa el ResultTraficoTN3270Bean con un código de respuesta.
	 * 
	 * @param codigoRespuesta Codigo que identifica la respuesta obtenida.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta){
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un código de respuesta y una lista de valores.
	 * 
	 * @param codigoRespuesta Código que identifica la respuesta obtenida.
	 * @param listaValores Valores obtenidos depués de realizar la invocación a una aplicación de tráfico.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta, HashMap<String, String> listaValores){
		this.codigoRespuesta = codigoRespuesta;
		this.listaValores = listaValores;
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un código de respuesta y una lista de valores y una sublista de valores.
	 * 
	 * @param codigoRespuesta Código que identifica la respuesta obtenida.
	 * @param listaValores Valores obtenidos depués de realizar la invocación a una aplicación de tráfico.
	 * @param sublistasValores Sub-listas de valores obtenidos depués de realizar la invocación a una aplicación de tráfico.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta, 
			HashMap<String, String> listaValores,
			List<SubListaValoresTN3270Bean> sublistasValores){
		this.codigoRespuesta = codigoRespuesta;
		this.listaValores = listaValores;
		this.sublistasValores = sublistasValores;
	}

	/**
	 * Inicializa el ResultTraficoTN3270Bean con un código de respuesta y una lista de valores y una sublista de valores.
	 * 
	 * @param codigoRespuesta Código que identifica la respuesta obtenida.
	 * @param listaValores Valores obtenidos depués de realizar la invocación a una aplicación de tráfico.
	 * @param sublistasValores Sub-listas de valores obtenidos depués de realizar la invocación a una aplicación de tráfico.
	 */
	public ResultTraficoTN3270Bean(String codigoRespuesta,
			List<String> listas, HashMap<String, String> listaValores){
		this.codigoRespuesta = codigoRespuesta;
		this.listaValores = listaValores;
		this.listas = listas;
	}

	/**
	 * Obtiene el código que identifica la respuesta obtenida.
	 * @return Código de respuesta
	 */
	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	/**
	 * Establece el código que identifica la respuesta obtenida.
	 * @param codigoRespuesta Código de respuesta
	 */
	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * Obtiene los valores leídos, obtenidos al producirse la invocación a una aplicación de tráfico.
	 * @return Lista de valores
	 */
	public HashMap<String, String> getListaValores() {
		return listaValores;
	}

	/**
	 * Establece los valores leídos, obtenidos al producirse la invocación a una aplicación de tráfico.
	 * @param listaValores Lista de valores
	 */
	public void setListaValores(HashMap<String, String> listaValores) {
		this.listaValores = listaValores;
	}

	/**
	 * Obtiene las sub-listas de valores leídos, obtenidos al producirse la invocación a una aplicación de tráfico.  <br>
	 * Este elemento contiene listas de valores, identificadas con ListaValoresTraficoTN3270 (Ejemplo: INSPECCION_TECNICA).<br><br>
	 * Se usa cuando se obtiene, en una misma petición, varios valores con un mismo identificador CasillaTraficoTN3270. Por ejemplo, en la aplicación AVPO,
	 * hay un listado de inspecciones técnicas, las cuales contienen los mismos campos (Un IT_CONCEPTO, IT_FECHA, etc, por cada inspección técnica). 
	 * Cada inspección técnica se guardaría como una sub-lista de valores identificada con INSPECCION_TECNICA. De esta forma se pueden guardar un número
	 * indeterminado de inspecciones técnicas.
	 * @return Sub-listas de valores.
	 */
	public List<SubListaValoresTN3270Bean> getSublistasValores() {
		return sublistasValores;
	}

	/**
	 * Establece las sub-listas de valores leídos, obtenidos al producirse la invocación a una aplicación de tráfico.  <br>
	 * Este elemento debe contener listas de valores, identificadas con ListaValoresTraficoTN3270 (Ejemplo: INSPECCION_TECNICA). <br><br>
	 * Se usa cuando se obtiene, en una misma petición, varios valores con un mismo identificador CasillaTraficoTN3270. Por ejemplo, en la aplicación AVPO,
	 * hay un listado de inspecciones técnicas, las cuales contienen los mismos campos (un IT_CONCEPTO, IT_FECHA, etc, por cada inspección técnica). 
	 * Cada inspección técnica se guardaría como una sub-lista de valores identificada con INSPECCION_TECNICA. De esta forma se pueden guardar un número
	 * indeterminado de inspecciones técnicas.
	 * 
	 * @param sublistasValores Sub-listas de valores.
	 */
	public void setSublistasValores(List<SubListaValoresTN3270Bean> sublistasValores) {
		this.sublistasValores = sublistasValores;
	}

	/**
	 * Devuelve una lista con todos los listados que se generan al realizar una petición AVPO y que no
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