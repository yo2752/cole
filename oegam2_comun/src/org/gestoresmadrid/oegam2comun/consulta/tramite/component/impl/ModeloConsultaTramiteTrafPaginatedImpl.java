package org.gestoresmadrid.oegam2comun.consulta.tramite.component.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.ConsultaTramiteTraficoFilterBean;
import org.gestoresmadrid.oegam2comun.consulta.tramite.view.bean.ConsultaTramiteTraficoResultBean;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaTramiteTrafPaginated")
@Transactional(readOnly = true)
public class ModeloConsultaTramiteTrafPaginatedImpl extends AbstractModelPagination {

	@Resource
	TramiteTraficoDao tramiteTraficoDao;

	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		int numElementosTotales = 0;
		ConsultaTramiteTraficoFilterBean filter = (ConsultaTramiteTraficoFilterBean) beanCriterios;

		try {
			numElementosTotales = tramiteTraficoDao.numeroElementosConsultaTramite(filter.getIdContrato(), filter.getEstado(), filter.getFechaAlta().getFechaInicio(), filter.getFechaAlta()
					.getFechaFin(), filter.getFechaPresentacion().getFechaInicio(), filter.getFechaPresentacion().getFechaFin(), filter.getTipoTramite(), filter.getRefPropia(), filter
							.getNumExpediente(), filter.getNifTitular(), filter.getNifFacturacion(), filter.getBastidor(), filter.getMatricula(), filter.getTipoTasa(), filter.getNive(), filter
									.getPresentadoJPT(), filter.getConNive());
		} catch (Exception e) {
			Log.error("Error al obtener el numero total de tramites en la consulta tramite trafico: ", e);
		}

		List<ConsultaTramiteTraficoResultBean> lista = null;
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
				list = tramiteTraficoDao.buscarConsultaTramite(filter.getIdContrato(), filter.getEstado(), filter.getFechaAlta().getFechaInicio(), filter.getFechaAlta().getFechaFin(), filter
						.getFechaPresentacion().getFechaInicio(), filter.getFechaPresentacion().getFechaFin(), filter.getTipoTramite(), filter.getRefPropia(), filter.getNumExpediente(), filter
								.getNifTitular(), filter.getNifFacturacion(), filter.getBastidor(), filter.getMatricula(), filter.getTipoTasa(), filter.getNive(), filter.getPresentadoJPT(), filter
										.getConNive(), firstResult, maxResults, dir, sort);
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

	private List<ConsultaTramiteTraficoResultBean> convertirListaEnBeanPantallaConsulta(List<Object[]> lista) {
		List<ConsultaTramiteTraficoResultBean> listaConsulta = new ArrayList<ConsultaTramiteTraficoResultBean>();
		for (Object[] tramite : lista) {
			ConsultaTramiteTraficoResultBean bean = new ConsultaTramiteTraficoResultBean();

			bean.setNumExpediente((BigDecimal) tramite[0]);
			if (tramite[1] != null) {
				bean.setRefPropia((String) tramite[1]);
			}

			if (tramite[2] != null) {
				bean.setBastidor((String) tramite[2]);
			}

			if (tramite[3] != null) {
				bean.setMatricula((String) tramite[3]);
			}

			if (tramite[4] != null) {
				bean.setTipoTasa((String) tramite[4]);
			}

			if (tramite[5] != null) {
				bean.setCodigoTasa((String) tramite[5]);
			}
			
			if (tramite[6] != null) {
				bean.setTipoTramite(TipoTramiteTrafico.convertirTexto((String) tramite[6]));
			}

			if (tramite[7] != null) {
				BigDecimal estado = (BigDecimal) tramite[7];
				bean.setEstado(EstadoTramiteTrafico.convertirTexto(estado.toString()));
			}

			if (tramite[8] != null) {
				if (EstadoPresentacionJPT.No_Presentado.getValorEnum().equals(tramite[8])){
					bean.setPresentadoJPT("NO");
				} else {
					bean.setPresentadoJPT("SI");
				}
			} else {
				bean.setPresentadoJPT("NO");
			}
			if (tramite[9] != null) {
				bean.setRespuesta((String)tramite[9]);
			}
			listaConsulta.add(bean);
		}
		return listaConsulta;
	}
}
