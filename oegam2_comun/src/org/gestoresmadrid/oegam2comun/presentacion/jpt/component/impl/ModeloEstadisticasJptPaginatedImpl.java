package org.gestoresmadrid.oegam2comun.presentacion.jpt.component.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.presentacion.jpt.model.dao.EstadisticasJPTDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloEstadisticasJptHorasPaginated")
@Transactional(readOnly=true)
public class ModeloEstadisticasJptPaginatedImpl extends AbstractModelPagination{

	@Resource
	private EstadisticasJPTDao estadisticasJPTDaoImpl;
	
	private BeanResultTransformPaginatedList beanTransform;
	
	@Override
	protected GenericDao<?> getDao() {
		return estadisticasJPTDaoImpl;
	}
	
	@Override
	protected BeanResultTransformPaginatedList crearTransformer() {
		return beanTransform;
	}
}
