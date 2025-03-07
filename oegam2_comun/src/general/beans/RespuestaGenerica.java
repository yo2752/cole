package general.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;

public class RespuestaGenerica {

	private HashMap<String,Object> mapaParametros;
	private List<Object> listaCursor;
	private BeanPQGeneral beanPQGeneral; 	
	
	public RespuestaGenerica() {
		mapaParametros = new HashMap<String,Object>();
		listaCursor = new ArrayList<Object>();
		//super();

	}

	/**
	 * Nos devuelve un parámetro concreto de la respuesta del procedimiento, hay que hacerle el casting luego
	 * @param respuestaProcedimiento
	 * @param nombreParametro
	 * @return
	 */
	public Object getParametro(String nombreParametro){
		return mapaParametros.get(nombreParametro);
	}
	public HashMap<String, Object> getMapaParametros() {
		return mapaParametros;
	}
	public void setMapaParametros(HashMap<String, Object> mapaParametros) {
		this.mapaParametros = mapaParametros;
	}

	/**
	 * Nos devuelve la List<Object> de la respuesta del procedimiento almacenado
	 * @param respuestaProcedimiento
	 * @return
	 */
	public List<Object> getListaCursor() {
		return listaCursor;
	}
	public void setListaCursor(List<Object> listaCursor) {
		this.listaCursor = listaCursor;
	}
	public BeanPQGeneral getBeanPQGeneral() {
		return beanPQGeneral;
	}
	public void setBeanPQGeneral(BeanPQGeneral beanPQGeneral) {
		this.beanPQGeneral = beanPQGeneral;
	}	
}