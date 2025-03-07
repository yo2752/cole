package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.empresaDIRe.model.dao.EmpresaDIReDao;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIReVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresaDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "ModeloEmpresaDIRePaginationImpl")
@Transactional(readOnly = true)
public class ModeloEmpresaDIRePaginationImpl extends AbstractModelPagination {
    
    @Resource
    private EmpresaDIReDao             EmpresaDIReDao;
    
    @Autowired
    private ServicioEmpresaDIRe servicioEmpresaDIRe;
    
    @Autowired
    Conversor                            conversor;
    
    @SuppressWarnings("unchecked")
    @Override
    public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort,
            String[] listInitializedOnePath, String[] listInitializedAnyPath) {
        
        // Obtener BeanResultTransformPaginatedList y lista de proyecciones
        BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
        ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);
        
        List<AliasQueryBean> alias = crearListaAlias();
        
        // Obtener numero total de resultados según los criterios de busqueda
        int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);
        List<ConsultaEmpresaDIReBean> lista = null;
        // Comprobar si hay resultados
        List<?> list = null;
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
            list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones,
                    transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);
            
            if (list != null && !list.isEmpty()) {
                // En este caso la conversion que se realiza es poner la
                // descripcion de los estados
                lista = servicioEmpresaDIRe.convertirListaEnBeanPantalla((List<EmpresaDIReVO>) list);
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
    
 
    
    public EmpresaDIReDao getEmpresaDIReDao() {
		return EmpresaDIReDao;
	}



	public void setEmpresaDIReDao(EmpresaDIReDao empresaDIReDao) {
		EmpresaDIReDao = empresaDIReDao;
	}



	public ServicioEmpresaDIRe getServicioEmpresaDIRe() {
		return servicioEmpresaDIRe;
	}



	public void setServicioEmpresaDIRe(ServicioEmpresaDIRe servicioEmpresaDIRe) {
		this.servicioEmpresaDIRe = servicioEmpresaDIRe;
	}



	@Override
    protected GenericDao<?> getDao() {
        return EmpresaDIReDao;
    }
    
}
