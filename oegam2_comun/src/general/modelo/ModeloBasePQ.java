package general.modelo;

import java.util.Map;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.estructuras.ListaRegistros;

public abstract class ModeloBasePQ extends ModeloGenerico implements ModeloBaseIF {

	private Map<String, Object> parametrosBusqueda;
	public abstract ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden);

	public ModeloBasePQ() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	public Map<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}
	public void setParametrosBusqueda(Map<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

}
