package org.gestoresmadrid.oegamComun.evolucionUsuario.component.impl;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.evolucionUsuario.model.dao.EvolucionUsuarioDao;
import org.gestoresmadrid.core.evolucionUsuario.model.vo.EvolucionUsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegamComun.evolucionUsuario.service.ServicioEvolucionUsuario;
import org.gestoresmadrid.oegamComun.evolucionUsuario.view.bean.EvolucionUsuarioBean;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloEvolucionUsuarioPaginatedImpl")
@Transactional(readOnly=true)
public class ModeloEvolucionUsuarioPaginatedImpl extends AbstractModelPagination{

	@Resource
	EvolucionUsuarioDao evolucionUsuarioDao;
	
	@Autowired
	ServicioEvolucionUsuario servicioEvolucionUsuario;
	
	@Override
	protected GenericDao<?> getDao() {
		return evolucionUsuarioDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener numero total de resultados seg�n los criterios de busqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
		List<EvolucionUsuarioBean> lista = null;
		// Comprobar si hay resultados
		if (numElementosTotales > 0) {
			
			// Calcular primer y ultimo resultado
			int firstResult = 0;
			int maxResults = resPag;
			if (resPag <= 0 || page <= 0) {
				maxResults = numElementosTotales;
			} else {
				firstResult = resPag * (page - 1) ;
				if (firstResult>=numElementosTotales){
					if (numElementosTotales % resPag == 0) {
						page = (numElementosTotales / resPag);
					} else {
						page = (numElementosTotales / resPag)+1;
					}
					firstResult = resPag * (page - 1) ;
				}
			}
			// Llamar al dao para obtener el listado
			List<?> list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);
			if(list != null && !list.isEmpty()){
					lista = servicioEvolucionUsuario.convertirListaEnBeanPantalla((List<EvolucionUsuarioVO>)list);
			}
		} else {
			// Si no hay resultados, lista vacia
			lista = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag,page,dir, sort, numElementosTotales, lista);
	}

}
