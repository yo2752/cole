package escrituras.modelo;

import general.modelo.ModeloBaseIF;

import java.util.Map;

import org.displaytag.properties.SortOrderEnum;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.estructuras.ListaRegistros;

public abstract class ModeloBase implements ModeloBaseIF{

	private Map<String, Object> parametrosBusqueda;
	public abstract ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden);

	public ModeloBase() {
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