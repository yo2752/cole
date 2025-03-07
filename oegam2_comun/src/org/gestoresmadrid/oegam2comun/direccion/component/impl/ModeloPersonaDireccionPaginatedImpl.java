package org.gestoresmadrid.oegam2comun.direccion.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.personas.model.dao.PersonaDireccionDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloPersonaDireccionPaginated")
@Transactional(readOnly = true)
public class ModeloPersonaDireccionPaginatedImpl extends AbstractModelPagination {

	@Resource
	private PersonaDireccionDao personaDireccionDao;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Override
	protected GenericDao<?> getDao() {
		return personaDireccionDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		int numElementosTotales = getDao().numeroElementos(beanCriterios);
		List<?> list;
		List<PersonaDireccionVO> results = new ArrayList<>();
		if (numElementosTotales > 0) {
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
			BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
			ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

			List<AliasQueryBean> alias = crearListaAlias();
			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

			if (list != null && !list.isEmpty()) {
				Iterator it = (Iterator) list.iterator();
				while (it.hasNext()) {
					PersonaDireccionVO personaDireccion = (PersonaDireccionVO) it.next();
					if (personaDireccion.getDireccion() != null) {
						personaDireccion.getDireccion().setNombreMunicipio(
								servicioDireccion.obtenerNombreMunicipio(personaDireccion.getDireccion().getIdMunicipio(), personaDireccion.getDireccion().getIdProvincia()));
						personaDireccion.getDireccion().setNombreProvincia(servicioDireccion.obtenerNombreProvincia(personaDireccion.getDireccion().getIdProvincia()));
						personaDireccion.getDireccion().setTipoViaDescripcion(servicioDireccion.obtenerNombreTipoVia(personaDireccion.getDireccion().getIdTipoVia()));
					}
					results.add(personaDireccion);
				}
			}
		} else {
			list = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, results);
	}

	public ServicioDireccion getServicioDireccion() {
		return servicioDireccion;
	}

	public void setServicioDireccion(ServicioDireccion servicioDireccion) {
		this.servicioDireccion = servicioDireccion;
	}
}