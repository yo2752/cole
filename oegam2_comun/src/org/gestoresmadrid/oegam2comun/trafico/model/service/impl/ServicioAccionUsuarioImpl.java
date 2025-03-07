package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.util.Date;

import org.gestoresmadrid.core.trafico.model.dao.AccionUsuarioDao;
import org.gestoresmadrid.core.trafico.model.vo.UsuarioAccionVO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioAccionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultadoAccionUsuarioBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioAccionUsuarioImpl implements ServicioAccionUsuario{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioAccionUsuarioImpl.class);
	private static final long serialVersionUID = -8373298177973631875L;
	
	@Autowired
	AccionUsuarioDao accionUsuarioDao;

	@Override
	@Transactional
	public void guardarActionBorrarDatos(String numExpediente, String accion, ResultadoAccionUsuarioBean resultado, String ipAccesso) {
		try {
			UsuarioAccionVO usuarioAccionVO = new UsuarioAccionVO();
			usuarioAccionVO.setNumExpediente(numExpediente);
			usuarioAccionVO.setFecha(new Date());
			usuarioAccionVO.setIpAcceso(ipAccesso);
			usuarioAccionVO.setAccion(accion);
			if(resultado.getRespuesta() != null) {
				usuarioAccionVO.setDato(resultado.getRespuesta());
			}else if(resultado.getKilometros() != null) {
				usuarioAccionVO.setDato(resultado.getKilometros());
			}else if(resultado.getMensaje() != null) {
				usuarioAccionVO.setDato(resultado.getMensaje());
			}
			accionUsuarioDao.guardar(usuarioAccionVO);
		} catch (Exception e) {
			log.error("Error al guardar la acción realizada por el usuario.", e);
		}
	}
}
