package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;

import escrituras.beans.ResultBean;

public interface ServicioPermisos extends Serializable {

	boolean tienePermisoElContrato(Long idContrato, String codigoFuncion, String codigoAplicacion);

	boolean tienePermisoElUsuario(Long idUsuario, Long idContrato, String codigoFuncion, String codigoAplicacion);

	boolean usuarioTienePermisoSobreTramites(String[] numExpediente, Long idContrato);

	List<UsuarioFuncionSinAccesoVO> compruebaAcceso(String codigoFuncion);

	ResultBean asignarPermiso(BigDecimal idContrato, String codFuncion, String codAplicacion, String numColegiado, Long idUsuario);

	ResultBean desasignarPermiso(BigDecimal idContrato, String codFuncion, String codAplicacion, String numColegiado, Long idUsuario);

	List<FuncionDto> obtenerPermisosContrato(Long idContrato);

	List<FuncionDto> obtenerPermisosContratoPorAplicacion(Long idContrato, String codigoAplicacion);

	List<FuncionDto> obtenerPermisosUsuario(Long idContrato, Long idUsuario);

	void eliminarPermisosUsuarios(Long idUsuario);
}
