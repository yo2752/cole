package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface UsuarioDao extends GenericDao<UsuarioVO>, Serializable {

	UsuarioVO getUsuario(Long idUsuario);

	String getJefaturaProvincialPorUsuario(Long idUsuario);

	List<UsuarioVO> obtenerGrupoUsuarioPorNif(UsuarioVO usuarioVO);

	Integer numUsuariosPorNifEstado(String nif, BigDecimal estado);

	List<UsuarioVO> getUsuariosNumColegiado(String numColegiado);

	List<UsuarioVO> getUsuarioPorNumColegiado(String numColegiado);

	List<UsuarioVO> getUsuariosPorContrato(Long idContrato);

	List<UsuarioVO> getUsuarioPorNifYEstado(String nifUsuario, BigDecimal estadoUsuario);

	List<UsuarioVO> getUsuariosHabilitadosPorNumColegiado(String numColegiado);

	UsuarioVO getUsuarioPorNifYEstadoVO(String nifUsuario, BigDecimal estadoUsuario);

	UsuarioVO getUsuarioPorNifYColegiado(String nif, String numColegiado);
}
