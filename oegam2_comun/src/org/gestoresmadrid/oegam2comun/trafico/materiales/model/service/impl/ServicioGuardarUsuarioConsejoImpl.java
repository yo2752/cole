package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.UsuarioColegioDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarUsuarioConsejo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarUsuarioConsejoImpl implements ServicioGuardarUsuarioConsejo {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2923595694390586140L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarPedidoImpl.class);

	@Resource UsuarioColegioDao usuarioColegioDao;
	
	@Override
	@Transactional
	public ResultBean guardarUsuarioConsejo(UsuarioColegioVO usuarioConsejoVO) {
		ResultBean result = new ResultBean(Boolean.FALSE);
		
		try {
			@SuppressWarnings("unused")
			UsuarioColegioVO usuarioColegioVO = usuarioColegioDao.guardarOActualizar(usuarioConsejoVO);
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error(ex.getMessage());
		}
		return result;
	}

}
