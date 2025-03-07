package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.model.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.dao.PlacasMatriculacionDAO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloSolicitudPlacaPaginated")
@Transactional(readOnly = true)
public class ModeloSolicitudPlacaPaginatedImpl extends AbstractModelPagination {

	@Resource
	private PlacasMatriculacionDAO placasMatriculacionDAO;
	
	
	@Override
	protected GenericDao<?> getDao() {
		return placasMatriculacionDAO;
	}
	
	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		
		
		listaAlias.add(new AliasQueryBean(SolicitudPlacaVO.class, "tramiteTrafico", "tramiteTrafico", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(SolicitudPlacaVO.class, "vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN));
		listaAlias.add(new AliasQueryBean(SolicitudPlacaVO.class, "usuario", "usuario", CriteriaSpecification.INNER_JOIN));
		
		
		return listaAlias;
	}

	public PlacasMatriculacionDAO getSolicitudPlacaDao() {
		return placasMatriculacionDAO;
	}

	public void setSolicitudPlacaDao(PlacasMatriculacionDAO placasMatriculacionDAO) {
		this.placasMatriculacionDAO = placasMatriculacionDAO;
	}
}
