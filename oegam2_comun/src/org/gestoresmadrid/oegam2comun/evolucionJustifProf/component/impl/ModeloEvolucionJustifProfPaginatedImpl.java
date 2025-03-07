package org.gestoresmadrid.oegam2comun.evolucionJustifProf.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.dao.EvolucionJustifProfDao;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="modeloEvolucionJustifProfPaginated")
@Transactional(readOnly=true)
public class ModeloEvolucionJustifProfPaginatedImpl extends AbstractModelPagination{

	@Resource
	private EvolucionJustifProfDao evolucionJustifProfDaoImpl;
	
	private BeanResultTransformPaginatedList beanTransform;
	
	@Override
	protected GenericDao<?> getDao() {
		return evolucionJustifProfDaoImpl;
	}
	
	@Override
	protected BeanResultTransformPaginatedList crearTransformer() {
		return beanTransform;
	}
	
	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<AliasQueryBean>();
		AliasQueryBean alias = new AliasQueryBean(UsuarioVO.class, "usuario","usuario");
		
		listaAlias.add(alias);
		
		return listaAlias;
	}

}
