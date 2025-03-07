package org.gestoresmadrid.core.proceso.model.dao;

import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.proceso.model.vo.UsuarioGestProcesosVO;

public interface UsuarioGestProcesosDao extends GenericDao<UsuarioGestProcesosVO> {

	List<UsuarioGestProcesosVO> getUsuarioGestProcesosByIdUsuario(Long idUsuario);

	List<UsuarioGestProcesosVO> getUsuarioGestProcesosByUsername(String username);

	List<UsuarioGestProcesosVO> getUserProcesosByIdUsuarioAndUser(Long idUsuario, String username);
}
