package org.gestoresmadrid.oegam2comun.conductor.component.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.conductor.model.dao.ConsultaConductorDao;
import org.gestoresmadrid.core.conductor.model.vo.ConsultaConductorVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.conductor.model.service.ServicioConsultaConductor;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ConsultaConductorBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConductorPaginationImpl")
@Transactional(readOnly = true)
public class ModeloConductorPaginationImpl extends AbstractModelPagination {

	@Resource
	private ConsultaConductorDao consultaConductorDao;

	@Autowired
	private ServicioConsultaConductor servicioConsultaConductor;

	@Autowired
	Conversor conversor;

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {

		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener número total de resultados según los criterios de busqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		List<ConsultaConductorBean> lista = null;
		// Comprobar si hay resultados
		List<?> list;
		if (numElementosTotales > 0) {
			// Calcular primer y último resultado
			int firstResult = 0;
			int maxResults = resPag;
			if (resPag <= 0 || page <= 0) {
				maxResults = numElementosTotales;
			} else {
				firstResult = resPag * (page - 1);
				if (firstResult >= numElementosTotales) {
					if (numElementosTotales % resPag == 0) {
						page = (numElementosTotales / resPag);
					} else {
						page = (numElementosTotales / resPag) + 1;
					}
					firstResult = resPag * (page - 1);
				}
			}
			list = (List<ConsultaConductorBean>) getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath,
					listInitializedAnyPath, alias);

			if (list != null && !list.isEmpty()) {
				// En este caso la conversión que se realiza es poner la
				// descripción de los estados
				lista = servicioConsultaConductor.convertirListaEnBeanPantalla((List<ConsultaConductorVO>) list);
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

	public ConsultaConductorDao getConsultaConductorDao() {
		return consultaConductorDao;
	}

	public void setConsultaConductorDao(ConsultaConductorDao consultaConductorDao) {
		this.consultaConductorDao = consultaConductorDao;
	}

	@Override
	protected GenericDao<?> getDao() {
		return consultaConductorDao;
	}

}