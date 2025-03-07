package org.gestoresmadrid.core.bien.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface BienRemesaDao extends GenericDao<BienRemesaVO>,Serializable{

	List<BienRemesaVO> getBienRemesaPorIdRemesa(Long idRemesa);

	BienRemesaVO getBienRemesaPorId(Long idBien, Long idRemesa,Boolean remesaCompleta);

	List<BienRemesaVO> getListaPorIdBien(Long idBien);

}
