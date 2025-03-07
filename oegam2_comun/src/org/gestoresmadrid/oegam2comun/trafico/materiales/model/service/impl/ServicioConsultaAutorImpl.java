package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.AutorDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaAutor;
import org.springframework.stereotype.Service;

@Service
public class ServicioConsultaAutorImpl implements ServicioConsultaAutor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2185742810125707207L;
	
	@Resource AutorDao autorDao;
	
	@Override
	public AutorVO getAutorByPrimaryKey(Long autorId) {
		AutorVO autorVO = autorDao.findByPrimaryKey(autorId);
		return autorVO;
	}

}
