package org.gestoresmadrid.oegam2comun.impr.component.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.EstadoImpr;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.impr.model.dao.ImprDao;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegamComun.impr.service.ServicioGestionarImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionImprBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionImprFilterBean;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloImprPaginated")
@Transactional(readOnly=true)
public class ModeloImprPaginatedImpl extends AbstractModelPagination implements Serializable{

	private static final long serialVersionUID = -5575557452391986092L;

	@Resource
	ImprDao imprDao;
	
	@Autowired
	ServicioGestionarImpr servicioImpr;
	
	@Override
	protected GenericDao<?> getDao() {
		return imprDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		int numElementosTotales = 0;
		GestionImprFilterBean filter = (GestionImprFilterBean) beanCriterios;

		try {
			numElementosTotales = imprDao.numeroElementosConsultaTramite(filter.getId(), filter.getMatricula(),filter.getBastidor(),filter.getNive(),filter.getTipoImpr(),filter.getTipoTramite(),filter.getNumExpediente(),
					filter.getEstadoSolicitud(),filter.getEstadoImpresion(),filter.getFechaAlta().getFechaInicio(),filter.getFechaAlta().getFechaFin(),
					filter.getJefatura(),filter.getDocId(),filter.getIdContrato(),filter.getCarpeta(),filter.getEstadoImpr());
		} catch (Exception e) {
			Log.error("Error al obtener el numero total de tramites en la consulta tramite trafico: ", e);
		}

		List<GestionImprBean> lista = null;
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
				list = imprDao.buscarConsultaTramite(filter.getId(), filter.getMatricula(),filter.getBastidor(),filter.getNive(),filter.getTipoImpr(),filter.getTipoTramite(),filter.getNumExpediente(),
						filter.getEstadoSolicitud(),filter.getEstadoImpresion(),filter.getFechaAlta().getFechaInicio(),filter.getFechaAlta().getFechaFin(),
						filter.getJefatura(),filter.getDocId(),filter.getIdContrato(),filter.getCarpeta(),filter.getEstadoImpr(),firstResult, maxResults, dir, sort);
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

	private List<GestionImprBean> convertirListaEnBeanPantallaConsulta(List<Object[]> lista) {
		List<GestionImprBean> listaConsulta = new ArrayList<GestionImprBean>();
		for (Object[] tramite : lista) {
			GestionImprBean bean = new GestionImprBean();
			bean.setId((Long)tramite[0]);
			bean.setNumExpediente((BigDecimal) tramite[1]);
			if (tramite[2] != null) {
				bean.setMatricula((String) tramite[2]);
			}

			if (tramite[3] != null) {
				bean.setBastidor((String) tramite[3]);
			}

			if (tramite[4] != null) {
				bean.setCarpeta((String) tramite[4]);
			}

			if (tramite[5] != null) {
				bean.setTipoImpr(TipoImpr.convertirTexto((String) tramite[5]));
			}

			if (tramite[6] != null) {
				bean.setTipoTramite(TipoTramiteTrafico.convertirTexto((String) tramite[6]));
			}
			
			if (tramite[7] != null) {
				bean.setEstadoImpr(EstadoImpr.convertirTexto((String)tramite[7]));
			}
			
			if (tramite[8] != null) {
				bean.setEstadoSolicitud(EstadoPermisoDistintivoItv.convertirTexto((String) tramite[8]));
			}

			if (tramite[9] != null) {
				bean.setDocId((Long) tramite[9]);
			}
			if (tramite[10] != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				bean.setFechaAlta(formatter.format((Date)tramite[10]));
			}
			listaConsulta.add(bean);
		}
		return listaConsulta;
	}


}
