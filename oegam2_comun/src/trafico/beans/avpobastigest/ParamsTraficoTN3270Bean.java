package trafico.beans.avpobastigest;


import java.util.HashMap;

import trafico.utiles.enumerados.AplicacionTraficoTN3270;

/**
 * Parámetros de invocación de una consulta de datos contra las aplicaciones de tráfico 
 * a través de un emulador TN3270.
 *
 */
public class ParamsTraficoTN3270Bean {

	private AplicacionTraficoTN3270 codigoAplicacion;
	private HashMap<String, String> listaParametros;
	
	/**
	 * Inicializa el ParamsTraficoTN3270Bean con un código de aplicación.
	 * 
	 * @param codigoAplicación Indica la aplicación a invocar.
	 */
	public ParamsTraficoTN3270Bean(AplicacionTraficoTN3270 codigoAplicacion){
		this.codigoAplicacion = codigoAplicacion;
	}
	
	/**
	 * Inicializa el ResultTraficoTN3270Bean con un código de aplicación y una lista de parámetros.
	 * 
	 * @param codigoAplicación Indica la aplicación a invocar.
	 * @param listaParametros Parámetros necesarios para la invocación de la aplicación indicada en codigoAplicación.
	 */
	public ParamsTraficoTN3270Bean(AplicacionTraficoTN3270 codigoAplicacion, HashMap<String, String> listaParametros){
		this.codigoAplicacion = codigoAplicacion;
		this.listaParametros = listaParametros;
	}

	/**
	 * Obtiene el código que indica la aplicación de tráfico que se va a invocar.
	 * @return Código de aplicación
	 */
	public AplicacionTraficoTN3270 getCodigoAplicacion() {
		return codigoAplicacion;
	}

	/**
	 * Establece el código que indica la aplicación de tráfico que se va a invocar.
	 * @param codigoAplicacion Código de aplicación
	 */
	public void setCodigoAplicación(AplicacionTraficoTN3270 codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	/**
	 * Obtiene los parámetros que se usarán para la invocación de la aplicación indicada en codigoAplicación
	 * @return Listado de parámetros
	 */
	public HashMap<String, String> getListaParametros() {
		return listaParametros;
	}

	/**
	 * Establece los parámetros que se usarán para la invocación de la aplicación indicada en codigoAplicación
	 * @param listaParametros Listado de parámetros
	 */
	public void setListaParametros(HashMap<String, String> listaParametros) {
		this.listaParametros = listaParametros;
	}
	
	
	
	
	
}
