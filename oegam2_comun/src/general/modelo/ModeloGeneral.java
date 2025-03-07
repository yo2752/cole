package general.modelo;

import java.util.HashMap;

import org.displaytag.properties.SortOrderEnum;

import utilidades.estructuras.ListaRegistros;

public abstract class ModeloGeneral {

	private HashMap<String, Object> parametrosBusqueda;

	public abstract ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden);
	public abstract Object detalle(Integer entero);
	public abstract String alta(Object object);

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}
	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

}