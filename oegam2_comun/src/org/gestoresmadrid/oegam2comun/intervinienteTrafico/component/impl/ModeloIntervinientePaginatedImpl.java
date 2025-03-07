package org.gestoresmadrid.oegam2comun.intervinienteTrafico.component.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.intervinienteTrafico.model.dao.IntervinienteTraficoDao;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioConsultaInterviniente;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloIntervinientePaginated")
@Transactional(readOnly = true)
public class ModeloIntervinientePaginatedImpl extends AbstractModelPagination {

	@Resource
	IntervinienteTraficoDao intervinienteTraficoDao;

	@Autowired
	ServicioConsultaInterviniente servicioConsultaInterviniente;

	@Override
	protected GenericDao<?> getDao() {
		return intervinienteTraficoDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);
		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener número total de resultados según los criterios de búsqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);

		// Comprobar si hay resultados
		List<?> list;
		if (numElementosTotales > 0) {
			// Calcular primer y ultimo resultado
			int firstResult = 0;
			int maxResults = resPag;
			if (resPag <= 0 || page <= 0) {
				maxResults = numElementosTotales;
			} else {
				firstResult = resPag * (page - 1) ;
				if (firstResult>=numElementosTotales){
					if (numElementosTotales % resPag == 0) {
						page = (numElementosTotales / resPag);
					} else {
						page = (numElementosTotales / resPag)+1;
					}
					firstResult = resPag * (page - 1) ;
				}
			}

			// Llamar al dao para obtener el listado
			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);
			list = servicioConsultaInterviniente.convertirListaEnBeanPantalla((List<IntervinienteTraficoVO>)list);
		} else {
			// Si no hay resultados, lista vacía
			list = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag,page,dir, sort, numElementosTotales, list);
	}

	private String traducirParametroBusqueda(String sort) {
		HashMap<String, String> hashMapSort = hashMapBusqueda();
		return hashMapSort.get(sort);
	}

	private HashMap<String, String> hashMapBusqueda() {
		HashMap<String, String> hashMapSort = new HashMap<>();
		hashMapSort.put("numColegiado", "id.numColegiado");
		hashMapSort.put("numExpediente", "id.numExpediente");
		hashMapSort.put("nif", "id.nif");
		hashMapSort.put("descTipoInterviniente", "id.tipoInterviniente");
		hashMapSort.put("descMotivo", "idMotivoTutela");
		return hashMapSort;
	}

}