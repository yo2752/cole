package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.SecUsuarioPermisoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface UsuarioPermisoDao extends GenericDao<SecUsuarioPermisoVO>, Serializable{

	List<SecUsuarioPermisoVO> getlistaPermisosPorUsuario(Long idUsuario);

}
