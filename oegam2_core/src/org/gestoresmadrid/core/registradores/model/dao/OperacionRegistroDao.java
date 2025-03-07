package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.OperacionRegistroVO;

public interface OperacionRegistroDao extends GenericDao<OperacionRegistroVO>, Serializable {

	OperacionRegistroVO getOperacionRegistro(String codigo, String tipoTramite);

	List<OperacionRegistroVO> getOperacionesRegistro();
}
