package org.gestoresmadrid.oegam2comun.materiales.component.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionStockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionStockMaterialesVO;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioEvolucionStockMateriales;
import org.gestoresmadrid.oegam2comun.materiales.view.bean.EvolucionStockMaterialBean;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionMaterialStockPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionMaterialStockPaginatedImpl extends AbstractModelPagination {

	@Resource
	EvolucionStockMaterialDao evolucionStockMaterialDao;

	@Autowired
	ServicioEvolucionStockMateriales servicioEvolucionStockMateriales;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionStockMaterialDao;
	}

	public EvolucionStockMaterialDao getEvolucionStockMaterialDao() {
		return evolucionStockMaterialDao;
	}

	public void setEvolucionStockMaterialDao(EvolucionStockMaterialDao evolucionStockMaterialDao) {
		this.evolucionStockMaterialDao = evolucionStockMaterialDao;
	}

	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort,
			String[] listInitializedOnePath, String[] listInitializedAnyPath) {

		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener número total de resultados según los criterios de búsqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);

		// Comprobar si hay resultados
		List<?> list;

		List<EvolucionStockMaterialBean> lista = null;
		if (numElementosTotales > 0) {
			// Calcular primer último resultado
			int firstResult = 0;
			int maxResults = resPag;
			if (resPag <= 0 || page <= 0) {
				maxResults = numElementosTotales;
			} else {
				firstResult = resPag * (page - 1) ;
				if (firstResult>=numElementosTotales) {
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
			if (list != null && !list.isEmpty()) {
				lista = servicioEvolucionStockMateriales.convertirEvolucionEnBeanPantalla((List<EvolucionStockMaterialesVO>) list);
				if (lista == null || lista.isEmpty()) {
					lista = Collections.emptyList();
				}
			} else { // Si no hay resultados, lista vacía
				lista = Collections.emptyList();
			}
		} else { // Si no hay resultados, lista vacía
			list = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag,page,dir, sort, numElementosTotales, lista);
	}
}