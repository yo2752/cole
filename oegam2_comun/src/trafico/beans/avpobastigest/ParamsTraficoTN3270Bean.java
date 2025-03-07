package trafico.beans.avpobastigest;


import java.util.HashMap;

import trafico.utiles.enumerados.AplicacionTraficoTN3270;

/**
 * Par�metros de invocaci�n de una consulta de datos contra las aplicaciones de tr�fico 
 * a trav�s de un emulador TN3270.
 *
 */
public class ParamsTraficoTN3270Bean {

	private AplicacionTraficoTN3270 codigoAplicacion;
	private HashMap<String, String> listaParametros;
	
	/**
	 * Inicializa el ParamsTraficoTN3270Bean con un c�digo de aplicaci�n.
	 * 
	 * @param codigoAplicaci�n Indica la aplicaci�n a invocar.
	 */
	public ParamsTraficoTN3270Bean(AplicacionTraficoTN3270 codigoAplicacion){
		this.codigoAplicacion = codigoAplicacion;
	}
	
	/**
	 * Inicializa el ResultTraficoTN3270Bean con un c�digo de aplicaci�n y una lista de par�metros.
	 * 
	 * @param codigoAplicaci�n Indica la aplicaci�n a invocar.
	 * @param listaParametros Par�metros necesarios para la invocaci�n de la aplicaci�n indicada en codigoAplicaci�n.
	 */
	public ParamsTraficoTN3270Bean(AplicacionTraficoTN3270 codigoAplicacion, HashMap<String, String> listaParametros){
		this.codigoAplicacion = codigoAplicacion;
		this.listaParametros = listaParametros;
	}

	/**
	 * Obtiene el c�digo que indica la aplicaci�n de tr�fico que se va a invocar.
	 * @return C�digo de aplicaci�n
	 */
	public AplicacionTraficoTN3270 getCodigoAplicacion() {
		return codigoAplicacion;
	}

	/**
	 * Establece el c�digo que indica la aplicaci�n de tr�fico que se va a invocar.
	 * @param codigoAplicacion C�digo de aplicaci�n
	 */
	public void setCodigoAplicaci�n(AplicacionTraficoTN3270 codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	/**
	 * Obtiene los par�metros que se usar�n para la invocaci�n de la aplicaci�n indicada en codigoAplicaci�n
	 * @return Listado de par�metros
	 */
	public HashMap<String, String> getListaParametros() {
		return listaParametros;
	}

	/**
	 * Establece los par�metros que se usar�n para la invocaci�n de la aplicaci�n indicada en codigoAplicaci�n
	 * @param listaParametros Listado de par�metros
	 */
	public void setListaParametros(HashMap<String, String> listaParametros) {
		this.listaParametros = listaParametros;
	}
	
	
	
	
	
}
