package org.gestoresmadrid.oegam2comun.personas.component.impl;

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
import org.gestoresmadrid.core.personas.model.dao.EvolucionPersonaDao;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloEvolucionPersonaPaginated")
@Transactional(readOnly = true)
public class ModeloEvolucionPersonaPaginatedImpl extends AbstractModelPagination {

	@Resource
	private EvolucionPersonaDao evolucionPersonaDao;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<EvolucionPersonaDto> listaFinal = new ArrayList<>();
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
					EvolucionPersonaVO evolucionPersonaVO = (EvolucionPersonaVO) it.next();
					EvolucionPersonaDto evolucionPersonaDto = conversor.transform(evolucionPersonaVO, EvolucionPersonaDto.class);
					evolucionPersonaDto.setIndice(posicion);
					listaFinal.add(evolucionPersonaDto);
					posicion++;
				}
			}
		} else {
			list = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}

	@Override
	protected GenericDao<?> getDao() {
		return evolucionPersonaDao;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario", CriteriaSpecification.LEFT_JOIN));
		return listaAlias;
	}

	public EvolucionPersonaDao getEvolucionPersonaDao() {
		return evolucionPersonaDao;
	}

	public void setEvolucionPersonaDao(EvolucionPersonaDao evolucionPersonaDao) {
		this.evolucionPersonaDao = evolucionPersonaDao;
	}

	public ServicioEvolucionPersona getServicioEvolucionPersona() {
		return servicioEvolucionPersona;
	}

	public void setServicioEvolucionPersona(ServicioEvolucionPersona servicioEvolucionPersona) {
		this.servicioEvolucionPersona = servicioEvolucionPersona;
	}
}