package org.gestoresmadrid.oegam2comun.estadisticas.listados.model.component.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TipoIntervinienteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoGeneralYPersonalizadoBean;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoVehiculosEstadisticasAgrupadasBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.hibernate.Hibernate;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import estadisticas.utiles.enumerados.Agrupacion;
import estadisticas.utiles.enumerados.ConvierteCodigoALiteral;

@Service(value = "modeloListadoGeneralYPersonalizadoPaginated")
@Transactional(readOnly = true)
public class ModeloListadoGeneralYPersonalizadoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TramiteTraficoDao tramiteTraficoDao;

	@Resource
	private TramiteTraficoTransDao tramiteTraficoTransDao;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {

		List<ListadoVehiculosEstadisticasAgrupadasBean> listaFinal = new ArrayList<ListadoVehiculosEstadisticasAgrupadasBean>();

		String agrupacion = ((ListadoGeneralYPersonalizadoBean) beanCriterios).getAgrupacion();

		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();

		List<AliasQueryBean> alias = crearListaAlias(agrupacion);

		ProjectionList listaProyecciones = decidirProyeccionPorAgrupacion(agrupacion);

		// Obtener numero total de resultados según los criterios de busqueda
		int numElementosTotales = servicioTramiteTrafico.numeroElementosListadosEstadisticasConListaProyeccion(beanCriterios, listInitializedOnePath, alias, listaProyecciones, agrupacion);

		// Comprobar si hay resultados
		List<Object[]> listaAgrupada;
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

			listaAgrupada = (List<Object[]>) getDao(agrupacion).buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath,
					listInitializedAnyPath, alias);

			if (listaAgrupada != null && !listaAgrupada.isEmpty()) {
				ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
				for (Object[] elemen : (List<Object[]>) listaAgrupada) {

					ListadoVehiculosEstadisticasAgrupadasBean listadoVehiculosEstadisticasAgrupadasBean = new ListadoVehiculosEstadisticasAgrupadasBean();

					if (elemen[0] != null) {
						listadoVehiculosEstadisticasAgrupadasBean.setCampoAgrupacion(convierteCodigoALiteral.getLiteralAgrupacion(elemen[0].toString(), new BigDecimal(agrupacion)));
					} else {
						listadoVehiculosEstadisticasAgrupadasBean.setCampoAgrupacion("Vacío");
					}

					listadoVehiculosEstadisticasAgrupadasBean.setNumRegistros((Integer) elemen[1]);
					listaFinal.add(listadoVehiculosEstadisticasAgrupadasBean);
				}
			}

		} else {
			// Si no hay resultados, lista vacía
			listaFinal = Collections.emptyList();
		}

		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}

	private ProjectionList decidirProyeccionPorAgrupacion(String agrupacion) {

		ProjectionList listaProyecciones = Projections.projectionList();

		switch (agrupacion) {
			case "1":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("numColegiado"), "campo"));
				break;
			case "2":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("contratoProvincia.nombre"), "campo"));
				break;
			case "3":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("contratoJefaturaTrafico.descripcion"), "campo"));
				break;
			case "4":
				listaProyecciones.add(Projections.alias(Projections.sqlGroupProjection("TO_CHAR(fecha_presentacion,'HH24') as campo", "TO_CHAR(fecha_presentacion,'HH24')", new String[] { "campo" },
						new Type[] { Hibernate.STRING }), "campo"));
				break;
			case "5":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("vehiculo.idCarburante"), "campo"));
				break;
			case "6":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("tipoTransferencia"), "campo"));
				break;
			case "7":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("intervinienteTraficosDireccion.idProvMun"), "campo"));
				break;
			case "8":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("exentoCem"), "campo"));
				break;
			case "9":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("vehiculo.idServicio"), "campo"));
				break;
			case "10":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("tipoTramite"), "campo"));
				break;
			case "11":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("estado"), "campo"));
				break;
			case "12":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("vehiculo.codigoMarca"), "campo"));
				break;
			case "13":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("vehiculoTipoVehiculoVO.descripcion"), "campo"));
				break;
			case "14":
				listaProyecciones.add(Projections.alias(Projections.groupProperty("idTipoCreacion"), "campo"));
				break;
		}

		listaProyecciones.add(Projections.alias(Projections.rowCount(), "cantidad"));

		return listaProyecciones;

	}

	private List<AliasQueryBean> crearListaAlias(String agrupacion) {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(ContratoVO.class, "contrato", "contrato", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(ProvinciaVO.class, "contrato.provincia", "contratoProvincia", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(JefaturaTraficoVO.class, "contrato.jefaturaTrafico", "contratoJefaturaTrafico", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(VehiculoVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(TipoVehiculoVO.class, "vehiculo.tipoVehiculoVO", "vehiculoTipoVehiculoVO", CriteriaSpecification.LEFT_JOIN));
		if (Agrupacion.Municipio_Titular.getValorEnum().equals(agrupacion)) {
			listaAlias.add(new AliasQueryBean(IntervinienteTraficoVO.class, "intervinienteTraficos", "intervinienteTraficos", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(DireccionVO.class, "intervinienteTraficos.direccion", "intervinienteTraficosDireccion", CriteriaSpecification.LEFT_JOIN));
			listaAlias.add(new AliasQueryBean(TipoIntervinienteVO.class, "intervinienteTraficos.tipoIntervinienteVO", "intervinienteTraficosTipoIntervinienteVO", CriteriaSpecification.LEFT_JOIN));
		}
		return listaAlias;

	}

	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}

	protected GenericDao<?> getDao(String agrupacion) {
		if (agrupacion.equals(Agrupacion.Tipo_Transferencia.getValorEnum())) {
			return tramiteTraficoTransDao;
		} else {
			return tramiteTraficoDao;
		}
	}
}
