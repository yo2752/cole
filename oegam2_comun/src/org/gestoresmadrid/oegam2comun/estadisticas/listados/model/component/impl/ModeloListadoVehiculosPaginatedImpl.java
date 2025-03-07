package org.gestoresmadrid.oegam2comun.estadisticas.listados.model.component.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoVehiculosBean;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoVehiculosEstadisticasAgrupadasBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.hibernate.Hibernate;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estadisticas.utiles.enumerados.AgrupacionVehiculos;

@Service(value = "modeloListadoVehiculosPaginated")
@Transactional(readOnly = true)
public class ModeloListadoVehiculosPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {

		List<ListadoVehiculosEstadisticasAgrupadasBean> listFinal = new ArrayList<ListadoVehiculosEstadisticasAgrupadasBean>();

		String agrupacion = ((ListadoVehiculosBean) beanCriterios).getAgrupacionVehiculos();

		ListadoVehiculosEstadisticasAgrupadasBean listadoEstadisticasAgrupadasBean = null;
		List<AliasQueryBean> alias = crearListaAlias();

		ProjectionList listaProyecciones = decidirProyeccionPorAgrupacion(agrupacion);

		// Obtener numero total de resultados según los criterios de busqueda
		int numElementosTotales = servicioTramiteTrafico.numeroElementosListadosEstadisticasConListaProyeccion(beanCriterios, listInitializedOnePath, alias, listaProyecciones, agrupacion);

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

			List listaAgrupada = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath,
					listInitializedAnyPath, alias);

			((ListadoVehiculosBean) beanCriterios).setFechaPrimMatri(Boolean.TRUE);

			List listaAgrupadaConFechaPrimMatric = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath,
					listInitializedAnyPath, alias);

			// Obtenida la lista se vuelve a setear la fecha primera matrícula a false
			((ListadoVehiculosBean) beanCriterios).setFechaPrimMatri(Boolean.FALSE);

			String campoAgrupacion;

			for (Object[] arrayDeObjeto : (List<Object[]>) listaAgrupada) {

				if (AgrupacionVehiculos.Estado.getValorEnum().equals(((ListadoVehiculosBean) beanCriterios).getAgrupacionVehiculos())) {
					campoAgrupacion = EstadoTramiteTrafico.convertirTexto(((BigDecimal) arrayDeObjeto[0]).toString());
				} else if (AgrupacionVehiculos.Tipo_Tramite.getValorEnum().equals(((ListadoVehiculosBean) beanCriterios).getAgrupacionVehiculos())) {
					campoAgrupacion = TipoTramiteTrafico.convertirTexto(((String) arrayDeObjeto[0]));
				} else {
					campoAgrupacion = (String) arrayDeObjeto[0];
				}

				listadoEstadisticasAgrupadasBean = new ListadoVehiculosEstadisticasAgrupadasBean();
				listadoEstadisticasAgrupadasBean.setNumRegistros((Integer) arrayDeObjeto[1]);
				listadoEstadisticasAgrupadasBean.setCampoAgrupacion(campoAgrupacion);
				listadoEstadisticasAgrupadasBean.setConfechaPrimMatriculacion(0);

				String tipo;

				for (Object element : listaAgrupadaConFechaPrimMatric) {
					if (element instanceof Object[]) {
						Object[] arrayDeLista = (Object[]) element;

						if (AgrupacionVehiculos.Estado.getValorEnum().equals(((ListadoVehiculosBean) beanCriterios).getAgrupacionVehiculos())) {
							tipo = EstadoTramiteTrafico.convertirTexto(((BigDecimal) arrayDeLista[0]).toString());
						} else if (AgrupacionVehiculos.Tipo_Tramite.getValorEnum().equals(((ListadoVehiculosBean) beanCriterios).getAgrupacionVehiculos())) {
							tipo = TipoTramiteTrafico.convertirTexto(((String) arrayDeLista[0]));
						} else {
							tipo = (String) arrayDeLista[0];
						}

						// Se setea la cantidad de registros con fecha de primera matrícula
						if (tipo.equalsIgnoreCase(campoAgrupacion)) {
							listadoEstadisticasAgrupadasBean.setConfechaPrimMatriculacion((Integer) arrayDeLista[1]);
							break;
						}
					}

				}

				listFinal.add(listadoEstadisticasAgrupadasBean);

			}

		} else {
			// Si no hay resultados, lista vacía
			listFinal = Collections.emptyList();
		}

		dir = GenericDaoImplHibernate.ordenDes;
		sort = "campoAgrupacion";
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listFinal);
	}

	private ProjectionList decidirProyeccionPorAgrupacion(String agrupacion) {

		ProjectionList listaProyecciones = Projections.projectionList();

		switch (agrupacion) {
			case "1":
				listaProyecciones.add(Projections.sqlGroupProjection("TO_CHAR(FECHA_PRESENTACION,'YYYY') as campo", "TO_CHAR(FECHA_PRESENTACION,'YYYY')", new String[] { "campo" }, new Type[] {
						Hibernate.STRING }));
				break;
			case "2":
				listaProyecciones.add(Projections.sqlGroupProjection("TO_CHAR(FECHA_PRESENTACION,'MM/YYYY') as campo", "TO_CHAR(FECHA_PRESENTACION,'MM/YYYY')", new String[] { "campo" }, new Type[] {
						Hibernate.STRING }));
				break;
			case "3":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("estado"), "campo"));
				break;
			case "4":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("tipoTramite"), "campo"));
				break;
		}

		listaProyecciones.add(Projections.alias(Projections.rowCount(), "cantidad"));

		return listaProyecciones;

	}

	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}
}
