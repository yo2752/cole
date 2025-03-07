package org.gestoresmadrid.core.imputaciones.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.imputaciones.model.vo.TipoImputacionVO;

public interface TipoImputacionHorasDao extends  Serializable{

	public List<TipoImputacionVO> buscarTipoImputaciones(TipoImputacionVO tipoImputacionVO);

}
