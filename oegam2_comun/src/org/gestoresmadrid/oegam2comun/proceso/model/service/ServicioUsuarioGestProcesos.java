package org.gestoresmadrid.oegam2comun.proceso.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.proceso.model.vo.UsuarioGestProcesosVO;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.UsuarioGestProcesosDto;

import escrituras.beans.ResultBean;

public interface ServicioUsuarioGestProcesos extends Serializable {

	UsuarioGestProcesosDto getUsuarioGestProcesosByIdUsuario(Long idUsuario);

	UsuarioGestProcesosDto getUserProcesosByIdUsuarioAndUser(Long idUsuario, String username);

	UsuarioGestProcesosVO getUsuarioGestProcesosByIdUsuarioVO(Long idUsuario);

	ResultBean cambiarPassword(String username, String passwordNueva);

	void guardarUltimaConexion(Long idGestProcesos, String ipAcceso, String frontal, String tipoAcceso);

	UsuarioGestProcesosVO getUserProcesosByIdUsuarioAndUserVO(Long idUsuario, String username);
}
