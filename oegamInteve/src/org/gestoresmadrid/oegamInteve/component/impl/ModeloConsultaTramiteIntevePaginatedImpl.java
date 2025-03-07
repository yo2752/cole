package org.gestoresmadrid.oegamInteve.component.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.inteve.model.dao.TramiteTraficoInteveDao;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.oegamInteve.service.ServicioConsultaTramiteInteve;
import org.gestoresmadrid.oegamInteve.view.bean.ConsultaTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.bean.ConsultaTramiteInteveFilterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaTramiteIntevePaginated")
@Transactional(readOnly = true)
public class ModeloConsultaTramiteIntevePaginatedImpl extends AbstractModelPagination implements Serializable{

	private static final long serialVersionUID = -736223146155428527L;
	
	@Resource
	TramiteTraficoInteveDao tramiteTraficoInteveDao; 
	
	@Autowired 
	ServicioConsultaTramiteInteve servicioConsultaTramiteInteve;
	
	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoInteveDao;
	}
	
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		ConsultaTramiteInteveFilterBean filtro = (ConsultaTramiteInteveFilterBean)beanCriterios;
		int numElementosTotales = tramiteTraficoInteveDao.numeroElementos(filtro.getBastidor(),filtro.getCodigoTasa(),filtro.getEstado(),filtro.getFechaAlta(),filtro.getFechaPresentacion(),
				filtro.getIdContrato(), filtro.getMatricula(), filtro.getNive(), filtro.getNumExpediente(), filtro.getTipoInforme(), filtro.getTipoTramite());
		List<ConsultaTramiteInteveBean> lista = null;
		// Comprobar si hay resultados
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
			// Llamar al dao para obtener el listado
			List<TramiteTraficoInteveVO> list = tramiteTraficoInteveDao.buscarPorCriteria(firstResult, maxResults, dir, sort,
					filtro.getBastidor(),filtro.getCodigoTasa(),filtro.getEstado(),filtro.getFechaAlta(),filtro.getFechaPresentacion(),
					filtro.getIdContrato(), filtro.getMatricula(), filtro.getNive(), filtro.getNumExpediente(), filtro.getTipoInforme(), filtro.getTipoTramite());
			if (list != null && !list.isEmpty()) {
				lista = servicioConsultaTramiteInteve.convertirListaEnBeanPantallaConsulta(list);
				if (lista == null || lista.isEmpty()) {
					lista = Collections.emptyList();
				}
			}
		} else {
			// Si no hay resultados, lista vacia
			lista = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag, page, dir, sort, numElementosTotales, lista);
	}

}
