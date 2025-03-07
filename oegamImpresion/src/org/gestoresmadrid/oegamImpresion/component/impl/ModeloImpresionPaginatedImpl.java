package org.gestoresmadrid.oegamImpresion.component.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.impresion.model.dao.ImpresionDao;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.view.bean.ConsultaImpresionBean;
import org.gestoresmadrid.oegamImpresion.view.bean.ImpresionFilterBean;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloImpresionPaginated")
@Transactional(readOnly = true)
public class ModeloImpresionPaginatedImpl extends AbstractModelPagination {

	@Resource
	ImpresionDao impresionDao;

	@Autowired
	ServicioImpresionDocumentos servicioImpresionDocumentos;

	@Override
	protected GenericDao<?> getDao() {
		return impresionDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		ImpresionFilterBean beanFilterCriterio = (ImpresionFilterBean) beanCriterios;
		int numElementosTotales = 0;
		List<ConsultaImpresionBean> lista = null;
		try {
			numElementosTotales = impresionDao.numeroElementos(beanFilterCriterio.getNombreDocumento(), beanFilterCriterio.getTipoDocumento(), beanFilterCriterio.getEstado(), beanFilterCriterio
					.getIdContrato(), beanFilterCriterio.getFechaFiltrado().getFechaInicio(), beanFilterCriterio.getFechaFiltrado().getFechaFin(), beanFilterCriterio.getTipoTramite(),
					beanFilterCriterio.getNumExpediente());
		} catch (Exception e) {
			Log.error("Error al obtener las impresiones: ", e);
			List<AliasQueryBean> alias = crearListaAlias();
			numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		}
		List<?> list;
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
			try {
				list = impresionDao.buscar(beanFilterCriterio.getNombreDocumento(), beanFilterCriterio.getTipoDocumento(), beanFilterCriterio.getEstado(), beanFilterCriterio.getIdContrato(),
						beanFilterCriterio.getFechaFiltrado().getFechaInicio(), beanFilterCriterio.getFechaFiltrado().getFechaFin(), beanFilterCriterio.getTipoTramite(), beanFilterCriterio
								.getNumExpediente(), firstResult, maxResults);
				if (list != null && !list.isEmpty()) {
					lista = servicioImpresionDocumentos.convertirListaEnBeanPantallaConsulta((List<ImpresionVO>) list);
					if (lista == null || lista.isEmpty()) {
						lista = Collections.emptyList();
					}
				}
			} catch (Exception e) {
				Log.error("Error al obtener las impresiones: ", e);
				lista = Collections.emptyList();
			}
		} else {
			lista = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, lista);
	}
}
