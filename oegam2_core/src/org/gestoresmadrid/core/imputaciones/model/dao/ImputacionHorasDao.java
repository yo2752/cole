package org.gestoresmadrid.core.imputaciones.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.imputaciones.model.vo.ImputacionHorasVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ImputacionHorasDao extends GenericDao<ImputacionHorasVO> ,Serializable{

	public ImputacionHorasVO guardarImputacion(ImputacionHorasVO imputacionHorasVO);

	public List<ImputacionHorasVO> getImputacionesPorFechaUsuario(ImputacionHorasVO imputacionHorasVO);

}
