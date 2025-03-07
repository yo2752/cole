package org.gestoresmadrid.oegam2comun.trafico.liquidacion620.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.dao.EmpresaTelematicaDao;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.vo.EmpresaTelematicaVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;
import org.hibernate.criterion.ProjectionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.Fecha;

@Service(value = "modeloConsultaEmpresaTelematica")
@Transactional(readOnly = true)
public class ModeloConsultaEmpresaTelematicaPaginatedImpl extends AbstractModelPagination {

	@Autowired
	EmpresaTelematicaDao empresaDao;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Override
	@Transactional(readOnly=true)
	public PaginatedList buscarPag(Object beanCriterios, int resPag, int page, String dir, String sort, String[] listInitializedOnePath, String[] listInitializedAnyPath) {

		List<EmpresaTelematicaDto> listaFinal = new ArrayList<EmpresaTelematicaDto>();
		// Obtener BeanResultTransformPaginatedList y lista de proyecciones
		BeanResultTransformPaginatedList transformadorResultados = crearTransformer();
		ProjectionList listaProyecciones = crearListaProyecciones(transformadorResultados);

		List<AliasQueryBean> alias = crearListaAlias();

		// Obtener número total de resultados según los criterios de búsqueda
		int numElementosTotales = getDao().numeroElementos(beanCriterios, listInitializedOnePath, alias);

		// Comprobar si hay resultados
		List<?> list;
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

			// Llamar al DAO para obtener el listado
			list = getDao().buscarPorCriteria(firstResult, maxResults, dir, sort, beanCriterios, listaProyecciones, transformadorResultados, listInitializedOnePath, listInitializedAnyPath, alias);

			if (list != null && list.size() > 0) {
				Iterator it = (Iterator) list.iterator();
				while (it.hasNext()) {
					EmpresaTelematicaVO empresa = (EmpresaTelematicaVO) it.next();
					EmpresaTelematicaDto elemento = new EmpresaTelematicaDto();
					elemento.setId(empresa.getId());
					elemento.setNumColegiado(empresa.getNumColegiado());
					elemento.setEstado(empresa.getEstado().toString());
					elemento.setProvincia(empresa.getProvinciaVO().getNombre());
					elemento.setMunicipio(empresa.getMunicipioVO().getNombre());
					elemento.setCifEmpresa(empresa.getCifEmpresa());
					elemento.setCodigoPostal(empresa.getCodigoPostal());
					elemento.setEmpresa(empresa.getEmpresa());
					elemento.setFechaAlta(new Fecha(empresa.getFechaAlta()));
					elemento.setNombreContrato(empresa.getNumColegiado() +" - " + empresa.getContratoVO().getProvincia().getNombre() +" - " + empresa.getContratoVO().getVia());
					if(null!=empresa.getFechaBaja()){
						elemento.setFechaBaja(new Fecha(empresa.getFechaBaja()));
					}
					listaFinal.add(elemento);
				}
				list=listaFinal;
				if(listaFinal == null || listaFinal.isEmpty()){
					listaFinal = Collections.emptyList();
				}
			}
		} else {
			// Si no hay resultados, lista vacía
			list = Collections.emptyList();
		}

		// Devuelve una instancia de PaginatedList con el listado encontrado
		return new PaginatedListImpl(resPag,page,dir, sort, numElementosTotales, list);
	}

	@Override
	protected GenericDao<?> getDao() {
		return empresaDao;
	}

	public EmpresaTelematicaDao getEmpresaDao() {
		return empresaDao;
	}

	public void setEmpresaDao(EmpresaTelematicaDao empresaDao) {
		this.empresaDao = empresaDao;
	}

}