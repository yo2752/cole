package org.gestoresmadrid.oegam2comun.impr.component.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.impr.model.dao.DocImprDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegamComun.impr.service.ServicioGestionarDocImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionDocImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionDocImprFilterBean;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloDocImprPaginated")
@Transactional(readOnly=true)
public class ModeloDocImprPaginatedImpl extends AbstractModelPagination implements Serializable {

	private static final long serialVersionUID = -579920592261241821L;

	@Resource
	DocImprDao docImprDao;

	@Autowired
	ServicioGestionarDocImpr servicioDocImpr;

	@Override
	protected GenericDao<?> getDao() {
		return docImprDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		int numElementosTotales = 0;
		GestionDocImprFilterBean filter = (GestionDocImprFilterBean) beanCriterios;

		try {
			numElementosTotales = docImprDao.numeroElementosConsultaTramite(filter.getId(),filter.getDocImpr(),filter.getMatricula(),filter.getTipoImpr(),
					filter.getTipoTramite(),filter.getNumExpediente(),filter.getEstado(),filter.getFechaAlta().getFechaInicio(),
					filter.getFechaAlta().getFechaFin(),filter.getFechaImpresion().getFechaInicio(),filter.getFechaImpresion().getFechaFin(),filter.getJefatura(),filter.getIdContrato(),filter.getCarpeta());
		} catch (Exception e) {
			Log.error("Error al obtener el numero total de tramites en la consulta tramite trafico: ", e);
		}

		List<GestionDocImprBean> lista = null;
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

			List<?> list;
			try {
				list = docImprDao.buscarConsultaTramite(filter.getId(),filter.getDocImpr(),filter.getMatricula(),filter.getTipoImpr(),
						filter.getTipoTramite(),filter.getNumExpediente(),filter.getEstado(),filter.getFechaAlta().getFechaInicio(),
						filter.getFechaAlta().getFechaFin(),filter.getFechaImpresion().getFechaInicio(),filter.getFechaImpresion().getFechaFin(),filter.getJefatura(),filter.getIdContrato(),filter.getCarpeta(),firstResult, maxResults, dir, sort);
				if (list != null && !list.isEmpty()) {
					lista = convertirListaEnBeanPantallaConsulta((List<Object[]>) list);
					if (lista == null || lista.isEmpty()) {
						lista = Collections.emptyList();
					}
				}
			} catch (Exception e) {
				Log.error("Error al obtener los tramites en la consulta tramite trafico: ", e);
			}
		} else {
			lista = Collections.emptyList();
		}
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, lista);
	}

	private List<GestionDocImprBean> convertirListaEnBeanPantallaConsulta(List<Object[]> lista) {
		List<GestionDocImprBean> listaConsulta = new ArrayList<GestionDocImprBean>();
		for (Object[] tramite : lista) {
			GestionDocImprBean bean = new GestionDocImprBean();
			bean.setId((Long)tramite[0]);
			if (tramite[1] != null) {
				bean.setDocImpr((String) tramite[1]);
			}
			if (tramite[2] != null) {
				bean.setTipoImpr(TipoImpr.convertirTexto((String) tramite[2]));
			}
			if (tramite[3] != null) {
				bean.setCarpeta((String) tramite[3]);
			}
			if (tramite[4] != null) {
				bean.setEstado(EstadoPermisoDistintivoItv.convertirTexto((String) tramite[4]));
			}
			if (tramite[5] != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				bean.setFechaImpresion(formatter.format((Date)tramite[5]));
			}
			if (tramite[6] != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				bean.setFechaAlta(formatter.format((Date)tramite[6]));
			}
			if (tramite[7] != null) {
				bean.setTipoTramite(TipoTramiteTrafico.convertirTexto((String) tramite[7]));
			}

			listaConsulta.add(bean);
		}
		return listaConsulta;
	}

}