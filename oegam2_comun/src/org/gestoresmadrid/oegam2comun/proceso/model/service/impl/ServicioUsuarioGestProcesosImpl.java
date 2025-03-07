package org.gestoresmadrid.oegam2comun.proceso.model.service.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.proceso.model.dao.UsuarioGestProcesosDao;
import org.gestoresmadrid.core.proceso.model.vo.UsuarioGestProcesosVO;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioConexionGestProcesos;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioGestProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.UsuarioGestProcesosDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioUsuarioGestProcesosImpl implements ServicioUsuarioGestProcesos {

	private static final long serialVersionUID = 5917222350070467515L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioUsuarioGestProcesosImpl.class);

	@Autowired
	UsuarioGestProcesosDao usuarioGestProcesosDao;

	@Autowired
	ServicioUsuarioConexionGestProcesos servicioUsuarioConexionGestProcesos;

	@Autowired
	Conversor conversor;

	@Override
	@Transactional
	public UsuarioGestProcesosDto getUsuarioGestProcesosByIdUsuario(Long idUsuario) {
		List<UsuarioGestProcesosVO> lista = usuarioGestProcesosDao.getUsuarioGestProcesosByIdUsuario(idUsuario);
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista.get(0), UsuarioGestProcesosDto.class);
		}
		return null;
	}

	@Override
	@Transactional
	public UsuarioGestProcesosDto getUserProcesosByIdUsuarioAndUser(Long idUsuario, String username) {
		List<UsuarioGestProcesosVO> lista = usuarioGestProcesosDao.getUserProcesosByIdUsuarioAndUser(idUsuario, username);
		if (lista != null && !lista.isEmpty()) {
			return conversor.transform(lista.get(0), UsuarioGestProcesosDto.class);
		}
		return null;
	}

	@Override
	@Transactional
	public UsuarioGestProcesosVO getUsuarioGestProcesosByIdUsuarioVO(Long idUsuario) {
		List<UsuarioGestProcesosVO> lista = usuarioGestProcesosDao.getUsuarioGestProcesosByIdUsuario(idUsuario);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional
	public UsuarioGestProcesosVO getUserProcesosByIdUsuarioAndUserVO(Long idUsuario, String username) {
		List<UsuarioGestProcesosVO> lista = usuarioGestProcesosDao.getUserProcesosByIdUsuarioAndUser(idUsuario, username);
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean cambiarPassword(String username, String passwordNueva) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		try {
			List<UsuarioGestProcesosVO> lista = usuarioGestProcesosDao.getUsuarioGestProcesosByUsername(username);
			if (lista != null && !lista.isEmpty()) {
				lista.get(0).setPassword(passwordNueva);
				lista.get(0).setFechaCambioPassword(new Date());
				usuarioGestProcesosDao.actualizar(lista.get(0));
			} else {
				result.setError(Boolean.TRUE);
				result.setMensaje("No se ha encontrado el usuario con username: " + username);
			}
		} catch (Exception e) {
			log.error("Error a la hora de cambiar la contraseña", e);
			result.setError(Boolean.TRUE);
			result.setMensaje("Error a la hora de cambiar la contraseña");
		}
		return result;
	}

	@Override
	public void guardarUltimaConexion(Long idGestProcesos, String ipAcceso, String frontal, String tipoAcceso) {
		servicioUsuarioConexionGestProcesos.guardarUltimaConexion(idGestProcesos, ipAcceso, frontal, tipoAcceso);
	}
}
