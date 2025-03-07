package org.gestoresmadrid.core.imputaciones.model.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.imputaciones.model.dao.TipoImputacionHorasDao;
import org.gestoresmadrid.core.imputaciones.model.vo.TipoImputacionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.springframework.stereotype.Repository;

@Repository
public class TipoImputacionHorasDaoImpl extends  GenericDaoImplHibernate<TipoImputacionVO> implements TipoImputacionHorasDao, Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public List<TipoImputacionVO> buscarTipoImputaciones(TipoImputacionVO tipoImputacionVO) {
		return buscar(tipoImputacionVO);
	}

}
