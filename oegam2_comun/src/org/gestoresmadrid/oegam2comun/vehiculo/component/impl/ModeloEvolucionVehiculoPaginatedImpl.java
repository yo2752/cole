package org.gestoresmadrid.oegam2comun.vehiculo.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.vehiculo.model.dao.EvolucionVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionVehiculoPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionVehiculoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private EvolucionVehiculoDao evolucionVehiculoDao;

	@Autowired
	private Conversor conversor;

	@Override
	protected GenericDao<?> getDao() {
		return evolucionVehiculoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<EvolucionVehiculoDto> listaFinal = new ArrayList<>();
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
				int posicion = 0;
				while (it.hasNext()) {
					EvolucionVehiculoVO evolucionVehiculoVO = (EvolucionVehiculoVO) it.next();
					EvolucionVehiculoDto evolucionVehiculoDto = conversor.transform(evolucionVehiculoVO, EvolucionVehiculoDto.class);
					evolucionVehiculoDto.setIndice(posicion);
					listaFinal.add(evolucionVehiculoDto);
					posicion++;
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
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public EvolucionVehiculoDao getEvolucionVehiculoDao() {
		return evolucionVehiculoDao;
	}

	public void setEvolucionVehiculoDao(EvolucionVehiculoDao evolucionVehiculoDao) {
		this.evolucionVehiculoDao = evolucionVehiculoDao;
	}
}