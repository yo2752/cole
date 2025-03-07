package org.gestoresmadrid.oegam2comun.colegiado.component.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloContratoColegiadoPaginated")
@Transactional(readOnly = true)
public class ModeloContratoColegiadoPaginatedImpl extends AbstractModelPagination {

	@Resource
	private ContratoDao contratoDao;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		List<?> list;
		List<ContratoDto> listaFinal = new ArrayList<>();
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
					ContratoVO contratoVO = (ContratoVO) it.next();
					ContratoDto contratoDto = conversor.transform(contratoVO, ContratoDto.class);
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(contratoDto.getIdProvincia());
					contratoDto.setContratoProvincia(contratoDto.getIdContrato() + "_" + nombreProvincia);
					listaFinal.add(contratoDto);
				}
			}
		} else {
			list = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, listaFinal);
	}

	@Override
	protected GenericDao<?> getDao() {
		return contratoDao;
	}

	public ContratoDao getContratoDao() {
		return contratoDao;
	}

	public void setContratoDao(ContratoDao contratoDao) {
		this.contratoDao = contratoDao;
	}
}