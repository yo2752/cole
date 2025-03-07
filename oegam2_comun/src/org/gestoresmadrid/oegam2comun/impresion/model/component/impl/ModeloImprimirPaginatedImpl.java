package org.gestoresmadrid.oegam2comun.impresion.model.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.tasas.Tasa;
import hibernate.entities.trafico.Vehiculo;

@Service(value = "modeloImprimirPaginated")
@Transactional(readOnly = true)
public class ModeloImprimirPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	private Conversor conversor;

	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		listaAlias.add(new AliasQueryBean(Vehiculo.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(Tasa.class, "tasa", "tasa", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<TramiteTrafDto> listaFinal = new ArrayList<TramiteTrafDto>();
		// Obtener numero total de resultados según los criterios de busqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios);

		// Comprobar si hay resultados
		List<?> list;
		if (numElementosTotales > 0) {

			// Calcular primer y ultimo resultado
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

			// Obtener BeanResultTransformPaginatedList y lista de proyecciones
			BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
			ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

			List<AliasQueryBean> alias = crearListaAlias();

			// Llamar al dao para obtener el listado
			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

			if (list != null && list.size() > 0) {
				Iterator<TramiteTraficoVO> it = (Iterator) list.iterator();
				while (it.hasNext()) {
					TramiteTraficoVO tramiteTraficoVO = (TramiteTraficoVO) it.next();
					TramiteTrafDto tramiteTrafDto = conversor.transform(tramiteTraficoVO, TramiteTrafDto.class);
					if (tramiteTraficoVO.getIntervinienteTraficos() != null) {
						for (IntervinienteTraficoVO interviniente : tramiteTraficoVO.getIntervinienteTraficosAsList()) {
							if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Compraventa.getValorEnum()) || interviniente.getId().getTipoInterviniente().equals(
									TipoInterviniente.Adquiriente.getValorEnum()) || interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
								tramiteTrafDto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							}
						}
					}
					listaFinal.add(tramiteTrafDto);
				}
			}

		} else {
			// Si no hay resultados, lista vacia
			list = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}
}
