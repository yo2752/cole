package org.gestoresmadrid.core.sancion.model.dao;

import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.sancion.model.vo.SancionVO;

public interface SancionDao extends GenericDao<SancionVO> {
	public SancionVO getSancionId(Integer idSancion);

	public List<SancionVO> listado(SancionVO sancion);
}
