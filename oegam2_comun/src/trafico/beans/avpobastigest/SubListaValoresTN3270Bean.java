package trafico.beans.avpobastigest;

import java.util.HashMap;

/**
 * Contiene los valores de una sub-lista de valores. Con setIdTipoLista() se identifica el tipo de lista y
 * con setListaValores() se almacenan los valores correspondientes a la lista.<br><br> 
 * Se usa cuando se obtiene, en una misma petición, varios valores con un mismo identificador CasillaTraficoTN3270. Por ejemplo, en la aplicación AVPO,
 * hay un listado de inspecciones técnicas, las cuales contienen los mismos campos (un IT_CONCEPTO, IT_FECHA, etc, por cada inspección técnica). 
 * Cada inspección técnica se guardaría como una sub-lista de valores identificada con INSPECCION_TECNICA. De esta forma se pueden guardar un número
 * indeterminado de inspecciones técnicas.
 * 
 */
public class SubListaValoresTN3270Bean {

	private ListaValoresTraficoTN3270 idTipoLista;
	private HashMap<String, String> listaValores;
	
	public SubListaValoresTN3270Bean() {
		
	}
	
	/**
	 * Obtiene el tipo de sub-lista de valores almacenados en este bean en getListaValores(). 
	 * @return Identificador del tipo de lista.
	 */
	public ListaValoresTraficoTN3270 getIdTipoLista() {
		return idTipoLista;
	}
	
	/**
	 * Establece el tipo de sub-lista de valores almacenados en este bean en getListaValores().
	 * @param idTipoLista Identificador del tipo de lista.
	 */
	public void setIdTipoLista(ListaValoresTraficoTN3270 idTipoLista) {
		this.idTipoLista = idTipoLista;
	}
	
	/**
	 * Obtiene la lista de valores de esta sub-lista.
	 * @return Lista de valores.
	 */
	public HashMap<String, String> getListaValores() {
		return listaValores;
	}
	
	/**
	 * Establece la lista de valores de esta sub-lista.
	 * @param listaValores Lista de valores.
	 */
	public void setListaValores(HashMap<String, String> listaValores) {
		this.listaValores = listaValores;
	}
	
	
}
