package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;

public interface ServicioUsuario extends Serializable {

	public static String ID_USUARIO = "idUsuario";

	UsuarioDto getUsuarioDto(BigDecimal idUsuario);

	UsuarioVO getUsuario(BigDecimal idUsuario);

	ResultBean guardarUsuario(UsuarioDto usuario);

	ResultBean guardar(UsuarioVO usuarioVO);

	ResultBean actualizarJefaturaJPTUsuario(BigDecimal idUsuario, String jefatura);

	String getJefaturaProvincial(long idUsuario);

	UsuarioDto getGrupoUsuarioPorNIF(String nif);

	ResultBean deshabilitarUsuario(BigDecimal idUsuario, Date fecha, Boolean salirSesion);

	void eliminarUsuario(BigDecimal idUsuario, Date fecha);

	ResultBean habilitarUsuario(BigDecimal idUsuario, Boolean salirSesion);

	List<UsuarioDto> getUsuarioPorNumColegiado(String numColegiado);

	UsuarioDto usuarionConPermisos(BigDecimal idUsuario, Long idContrato);

	List<UsuarioDto> getUsuariosPorContrato(Long idContrato);

	List<UsuarioDto> getUsuariosHabilitadosPorNumColegiado(String numColegiado);

	List<ContratoUsuarioVO> getContratosAnterioresPorUsuario(String idSesion);

	List<UsuarioVO> getUsuarioPorNif(String nif);
}
