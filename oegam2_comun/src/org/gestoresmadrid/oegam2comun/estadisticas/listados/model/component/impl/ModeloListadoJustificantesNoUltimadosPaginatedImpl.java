package org.gestoresmadrid.oegam2comun.estadisticas.listados.model.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.dao.JustificanteProfDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloListadoJustificantesNoUltimadosPaginated")
@Transactional(readOnly = true)
public class ModeloListadoJustificantesNoUltimadosPaginatedImpl extends AbstractModelPagination {

	@Resource
	private JustificanteProfDao justificanteProfDao;

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(TramiteTraficoVO.class, "tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "tramiteTrafico.vehiculo", "tramiteTraficoVehiculo", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<AliasQueryBean> alias = crearListaAlias();

		ProjectionList listaProyecciones = Projections.projectionList();
		listaProyecciones.add(Projections.alias(Projections.groupProperty("tramiteTraficoVehiculo.matricula"), "matricula"));
		listaProyecciones.add(Projections.alias(Projections.rowCount(), "tramiteTraficoVehiculo.matricula"), "cantidad");

		// Obtener numero total de resultados según los criterios de busqueda
		int numElementosTotales = servicioJustificanteProfesional.numeroElementosListadoJustificantesNoUltimados(beanCriterios, listInitializedOnePath, alias, listaProyecciones);

		if (numElementosTotales > 0) {
			int firstResult = 0;
			int maxResults = resPag;

			firstResult = resPag * (page - 1);
			if (firstResult >= numElementosTotales) {
				if (numElementosTotales % resPag == 0) {
					page = (numElementosTotales / resPag);
				} else {
					page = (numElementosTotales / resPag) + 1;
				}
				firstResult = resPag * (page - 1);
			}

			BeanResultTransformPaginatedList transformadorResultados = crearTransformer();

			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

		} else {
			// Si no hay resultados, lista vacía
			list = Collections.emptyList();
		}

		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, list);
	}

	@Override
	protected GenericDao<?> getDao() {
		return justificanteProfDao;
	}
}
