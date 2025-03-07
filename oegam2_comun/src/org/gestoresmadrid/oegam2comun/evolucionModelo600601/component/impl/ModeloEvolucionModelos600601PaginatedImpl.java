package org.gestoresmadrid.oegam2comun.evolucionModelo600601.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.evolucionModelo600601.model.dao.EvolucionModelo600601Dao;
import org.gestoresmadrid.core.evolucionModelo600601.model.vo.EvolucionModelo600_601VO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.model.service.ServicioEvolucionModelo600_601;
import org.gestoresmadrid.oegam2comun.evolucionModelo600601.view.bean.EvolucionModelo600_601Bean;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloEvolucionModelos600601PaginatedImpl")
@Transactional(readOnly=true)
public class ModeloEvolucionModelos600601PaginatedImpl extends AbstractModelPagination{

	@Autowired
	private EvolucionModelo600601Dao evolucionModelo600601Dao;

	@Autowired
	private ServicioEvolucionModelo600_601 servicioEvolucionModelo600_601;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionModelo600601Dao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> alias = new ArrayList<>();
		alias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return alias;
	}

	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener número total de resultados según los criterios de búsqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		List<EvolucionModelo600_601Bean> lista = null;
		// Comprobar si hay resultados
		if (numElementosTotales > 0) {
			// Calcular primer y último resultado
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
			List<?> list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);
			if (list != null && !list.isEmpty()) {
				lista = servicioEvolucionModelo600_601.convertirListaEnBeanPantalla((List<EvolucionModelo600_601VO>) list);
				if (lista == null || lista.isEmpty()) {
					lista = Collections.emptyList();
				}
			}
		} else {
			// Si no hay resultados, lista vacía
			lista = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, lista);
	}

}