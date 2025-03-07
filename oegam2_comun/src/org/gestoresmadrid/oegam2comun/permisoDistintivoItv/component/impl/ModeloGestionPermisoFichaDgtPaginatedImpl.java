package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.component.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionPermisosFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisoFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisosFichasDgtBean;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloGestionPermisoFichaDgtPaginated")
@Transactional(readOnly = true)
public class ModeloGestionPermisoFichaDgtPaginatedImpl extends AbstractModelPagination {

	@Resource
	TramiteTraficoDao tramiteTraficoDao;
	
	@Resource
	TramiteTraficoTransDao tramiteTraficoTransDao;
	
	@Autowired
	ServicioGestionPermisosFichasDgt servicioGestionPermisosFichasDgt;
	
	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		int numElementosTotales = 0;
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);
		List<AliasQueryBean> alias = crearListaAlias();
		GestionPermisoFilterBean gestionPermisoFilterBean = null;
		if(beanCriterios instanceof GestionPermisoFilterBean){
			gestionPermisoFilterBean = (GestionPermisoFilterBean) beanCriterios;
			if(gestionPermisoFilterBean.getTipoTransferencia() != null && !gestionPermisoFilterBean.getTipoTransferencia().isEmpty()) {
				numElementosTotales = tramiteTraficoTransDao.numeroElementos(beanCriterios, listInitializedOnePath, alias);
			}else {
				numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
			}
		} else {
			numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		}
		// Obtener numero total de resultados según los criterios de busqueda
		//getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		List<GestionPermisosFichasDgtBean> lista = null;
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
			List<?> list = null;
			if(beanCriterios instanceof GestionPermisoFilterBean){
				if(gestionPermisoFilterBean.getTipoTransferencia() != null && !gestionPermisoFilterBean.getTipoTransferencia().isEmpty()) {
					list = tramiteTraficoTransDao.buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath,
							alias);
				}else {
					list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath,
							alias);
				}
			} else {
				list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath,
						alias);
			}
			if (list != null && !list.isEmpty()) {
				if(beanCriterios instanceof GestionPermisoFilterBean){
					lista = servicioGestionPermisosFichasDgt.convertirListaEnBeanPantallaConsultaPermisos((List<TramiteTraficoVO>) list);
				} else {
					lista = servicioGestionPermisosFichasDgt.convertirListaEnBeanPantallaGestionFichas((List<TramiteTraficoVO>) list);
				}
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
