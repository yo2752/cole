package org.gestoresmadrid.oegam2comun.datosSensibles.component.impl;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value="modeloDatosSensiblesPaginated")
@Transactional(readOnly=true)
public class ModeloDatosSensiblesPaginatedImpl extends AbstractModelPagination{
	
	
	private BeanResultTransformPaginatedList beanTransform;

	@Override
	protected GenericDao<?> getDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected BeanResultTransformPaginatedList crearTransformer() {
		return beanTransform;
	}

}
