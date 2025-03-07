package org.gestoresmadrid.oegam2comun.imputaciones.component.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.imputaciones.model.dao.ImputacionHorasDao;
import org.gestoresmadrid.core.imputaciones.model.vo.TipoImputacionVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloImputacionHorasPaginated")
@Transactional(readOnly = true)
public class ModeloImputacionHorasPaginatedImpl extends AbstractModelPagination {

	@Resource
	private ImputacionHorasDao imputacionHorasDaoImpl;

	private BeanResultTransformPaginatedList beanTransform;

	@Override
	protected GenericDao<?> getDao() {
		return imputacionHorasDaoImpl;
	}

	@Override
	protected BeanResultTransformPaginatedList crearTransformer() {
		return beanTransform;
	}

	@Override
	protected List<AliasQueryBean> crearListaAlias() {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		AliasQueryBean alias = new AliasQueryBean(UsuarioVO.class, "usuario", "usuario");

		listaAlias.add(alias);

		alias = new AliasQueryBean(TipoImputacionVO.class, "tipoImputacionVO", "tipoImputacion");

		listaAlias.add(alias);

		return listaAlias;
	}

}