package org.gestoresmadrid.oegam2comun.trafico.component.impl;

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
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
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

@Service(value = "modeloTramiteTraficoPaginated")
@Transactional(readOnly = true)
public class ModeloTramiteTraficoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	private Conversor conversor;

	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<TramiteTrafDto> listaFinal = new ArrayList<>();
		int numElementosTotales = getDao().numeroElementos(beanCriterios);
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
			ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

			List<AliasQueryBean> alias = crearListaAlias();

			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

			if (list != null && !list.isEmpty()) {
				Iterator it = (Iterator) list.iterator();
				while (it.hasNext()) {
					TramiteTraficoVO tramiteTraficoVO = (TramiteTraficoVO) it.next();
					TramiteTrafDto tramiteTrafDto = conversor.transform(tramiteTraficoVO, TramiteTrafDto.class);
					if (tramiteTraficoVO.getIntervinienteTraficos() != null) {
						for (IntervinienteTraficoVO interviniente : tramiteTraficoVO.getIntervinienteTraficosAsList()) {
							if (interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Compraventa.getValorEnum())
									|| interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Adquiriente.getValorEnum())
									|| interviniente.getId().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())) {
								tramiteTrafDto.setTitular(conversor.transform(interviniente, IntervinienteTraficoDto.class));
							}
						}
					}
					listaFinal.add(tramiteTrafDto);
				}
			}
		} else {
			list = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(PersonaVO.class, "intervinienteTraficos.persona", "intervinienteTraficos.persona", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public TramiteTraficoDao getTramiteTraficoDao() {
		return tramiteTraficoDao;
	}

	public void setTramiteTraficoDao(TramiteTraficoDao tramiteTraficoDao) {
		this.tramiteTraficoDao = tramiteTraficoDao;
	}
}