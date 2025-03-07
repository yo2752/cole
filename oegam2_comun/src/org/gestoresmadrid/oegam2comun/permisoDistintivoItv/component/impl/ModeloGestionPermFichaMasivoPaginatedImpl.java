package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.component.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoBajaDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDuplicadosDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoTransDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionPermisosFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermFichaMasivoFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisosFichasDgtBean;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloGestionPermFichaMasivoPaginated")
@Transactional(readOnly = true)
public class ModeloGestionPermFichaMasivoPaginatedImpl extends AbstractModelPagination {

	@Resource
	TramiteTraficoDao tramiteTraficoDao;
	
	@Resource
	TramiteTraficoMatrDao tramiteTraficoMatrDao;
	
	@Resource
	TramiteTraficoTransDao tramiteTraficoTransDao;
	
	@Resource
	TramiteTraficoBajaDao tramiteTraficoBajaDao;
	
	@Resource
	TramiteTraficoDuplicadosDao tramiteTraficoDuplicadoDao;
	
	@Autowired
	ServicioGestionPermisosFichasDgt servicioGestionPermisosFichasDgt;
	
	@Override
	protected GenericDao<?> getDao() {
		return tramiteTraficoDao;
	}

	
	protected GenericDao<?> getDaoMatr() {
		return tramiteTraficoMatrDao;
	}

	protected GenericDao<?> getDaoCtit() {
		return tramiteTraficoTransDao;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener numero total de resultados según los criterios de busqueda
		int numElementosTotales = 0;
		GestionPermFichaMasivoFilterBean beanFilterCriterio = (GestionPermFichaMasivoFilterBean) beanCriterios;
		if(beanFilterCriterio.getTipoTramite() != null && !beanFilterCriterio.getTipoTramite().isEmpty()){
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
				numElementosTotales = tramiteTraficoMatrDao.getNumElementosMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
			} else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
				numElementosTotales = tramiteTraficoTransDao.getNumElementosMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
			}else if(TipoTramiteTrafico.Baja.getValorEnum().equals(beanFilterCriterio.getTipoTramite())) {
				numElementosTotales = tramiteTraficoBajaDao.getNumElementosMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
			}else if(TipoTramiteTrafico.Duplicado.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
				numElementosTotales = tramiteTraficoDuplicadoDao.getNumElementosMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
			}
		} else {
			numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		}
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
			if(beanFilterCriterio.getTipoTramite() != null && !beanFilterCriterio.getTipoTramite().isEmpty()){
				if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
					list = tramiteTraficoMatrDao.getTramitesMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
				} else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
					list = tramiteTraficoTransDao.getTramitesMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
				} else if(TipoTramiteTrafico.Baja.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
					list = tramiteTraficoBajaDao.getTramitesMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
				} else if(TipoTramiteTrafico.Duplicado.getValorEnum().equals(beanFilterCriterio.getTipoTramite())){
					list = tramiteTraficoDuplicadoDao.getTramitesMasivos(beanFilterCriterio.getIdContrato(), beanFilterCriterio.getFechaPresentacion().getFechaInicio());
				}
			} else {
				list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath,
						alias);
			}
			if (list != null && !list.isEmpty()) {
				lista = servicioGestionPermisosFichasDgt.convertirListaEnBeanPantallaMasivo((List<TramiteTraficoVO>) list);
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
