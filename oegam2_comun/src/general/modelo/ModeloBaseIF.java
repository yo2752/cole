package general.modelo;

import java.util.Map;

import org.displaytag.properties.SortOrderEnum;

import utilidades.estructuras.ListaRegistros;

public interface ModeloBaseIF {

	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden);

	public Map<String, Object> getParametrosBusqueda();
	public void setParametrosBusqueda(Map<String, Object> parametrosBusqueda);

}
