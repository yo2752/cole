package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.component.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegam2comun.distintivo.model.service.ServicioDistintivoVehNoMat;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionDocPrmDstvFichas;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermDstvItvFilterBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisoDistintivoItvBean;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloPermDstvItvPaginated")
@Transactional(readOnly = true)
public class ModeloPermDstvItvPaginatedImpl extends AbstractModelPagination {

	@Resource
	DocPermDistItvDao docPermDistItvDao;

	@Autowired
	ServicioGestionDocPrmDstvFichas servicioGestionDocPrmDstvFichas;

	@Autowired
	ServicioDistintivoVehNoMat servicioDistintivoVehNoMat;

	@Override
	protected GenericDao<?> getDao() {
		return docPermDistItvDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);
		List<AliasQueryBean> alias = crearListaAlias();
		// Obtener número total de resultados según los criterios de búsqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		GestionPermDstvItvFilterBean gestionBean = (GestionPermDstvItvFilterBean) beanCriterios;
		if (numElementosTotales <= 0 && TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(gestionBean.getTipoDocumento())
					&& gestionBean != null && gestionBean.getMatricula() != null && !gestionBean.getMatricula().isEmpty()){
			gestionBean.setMatriculaDupDstv(gestionBean.getMatricula());
			gestionBean.setMatriculaDstv(null);
			numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		}
		List<GestionPermisoDistintivoItvBean> lista = null;
		// Comprobar si hay resultados
		if (numElementosTotales > 0) {
			// Calcular primer y último resultado
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
			List<?> list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath,
					alias);
			if (list != null && !list.isEmpty()) {
				lista = servicioGestionDocPrmDstvFichas.convertirListaEnBeanPantalla((List<DocPermDistItvVO>) list);
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