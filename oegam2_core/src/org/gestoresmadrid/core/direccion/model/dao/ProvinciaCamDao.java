package org.gestoresmadrid.core.direccion.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.ProvinciaCamVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ProvinciaCamDao  extends GenericDao<ProvinciaCamVO>, Serializable{

	List<ProvinciaCamVO> getLista();

	ProvinciaCamVO getProvincia(String idProvincia);

}
