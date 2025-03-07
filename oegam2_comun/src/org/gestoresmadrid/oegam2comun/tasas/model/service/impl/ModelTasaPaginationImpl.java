package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.tasas.model.dao.TasaCargadaDao;
import org.gestoresmadrid.oegam2comun.tasas.model.transformer.ResultTransformerConsultaTasa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modelTasaPagination")
@Transactional(readOnly = true)
public class ModelTasaPaginationImpl extends AbstractModelPagination {

	@Autowired
	private TasaCargadaDao tasaCargadaDao;

	@Override
	protected GenericDao<?> getDao() {
		return tasaCargadaDao;
	}

	@Override
	protected BeanResultTransformPaginatedList crearTransformer() {
		return new ResultTransformerConsultaTasa();
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		return Collections.singletonList(new AliasQueryBean(UsuarioVO.class, "usuario", "usuario"));
	}

	public TasaCargadaDao getTasaCargadaDao() {
		return tasaCargadaDao;
	}

	public void setTasaCargadaDao(TasaCargadaDao tasaCargadaDao) {
		this.tasaCargadaDao = tasaCargadaDao;
	}

}