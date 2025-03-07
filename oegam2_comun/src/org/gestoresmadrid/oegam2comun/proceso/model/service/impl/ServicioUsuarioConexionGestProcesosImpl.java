package org.gestoresmadrid.oegam2comun.proceso.model.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.proceso.model.dao.UsuarioConexionGestProcesosDao;
import org.gestoresmadrid.core.proceso.model.vo.UsuarioConexionGestProcesosVO;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioConexionGestProcesos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioUsuarioConexionGestProcesosImpl implements ServicioUsuarioConexionGestProcesos {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioUsuarioConexionGestProcesosImpl.class);

	private static final long serialVersionUID = -6326918892194054172L;

	@Autowired
	UsuarioConexionGestProcesosDao usuarioConexionGestProcesosDao;

	@Override
	@Transactional
	public void guardarUltimaConexion(Long idGestProcesos, String ipAcceso, String frontal, String tipoAcceso) {
		try {
			UsuarioConexionGestProcesosVO usuarioConexionGestProcesosVO = new UsuarioConexionGestProcesosVO();
			usuarioConexionGestProcesosVO.setIdGestProcesos(idGestProcesos);
			usuarioConexionGestProcesosVO.setFechaConexion(new Date());
			usuarioConexionGestProcesosVO.setIpAcceso(ipAcceso);
			usuarioConexionGestProcesosVO.setMaquinaProceso(frontal);
			usuarioConexionGestProcesosVO.setTipoOperacion(tipoAcceso);

			usuarioConexionGestProcesosDao.guardar(usuarioConexionGestProcesosVO);
		} catch (Exception e) {
			log.error("Error al guardar la ultima conexion al acceder a los procesos", e);
		}
	}

	@Override
	@Transactional
	public void registrarOperacion(Long idGestProcesos, String tipoOperacion, String procesoPatron, Long idUsuario) {
		try {
			UsuarioConexionGestProcesosVO usuarioConexionGestProcesosVO = new UsuarioConexionGestProcesosVO();
			usuarioConexionGestProcesosVO.setIdGestProcesos(idGestProcesos);
			usuarioConexionGestProcesosVO.setTipoOperacion(tipoOperacion);
			usuarioConexionGestProcesosVO.setProceso(procesoPatron);
			usuarioConexionGestProcesosVO.setFechaConexion(new Date());

			usuarioConexionGestProcesosDao.guardar(usuarioConexionGestProcesosVO);
		} catch (Exception e) {
			log.error("Error al guardar la operación de un usuario en la gestión de procesos", e);
		}
	}
}
