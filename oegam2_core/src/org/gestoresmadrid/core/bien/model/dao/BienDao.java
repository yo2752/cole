package org.gestoresmadrid.core.bien.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface BienDao extends Serializable, GenericDao<BienVO>{

	BienVO getBienPorId(Long idBien);

	List<BienVO> getListaBienPorIdDireccion(Long idDireccion);

	BienVO getBienPorIdufir(Long idufir);

}
