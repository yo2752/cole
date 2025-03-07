package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.PaisRegistroDao;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPaisRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PaisRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPaisRegistroImpl implements ServicioPaisRegistro {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6270463331147127146L;

	@Autowired
	private PaisRegistroDao paisRegistroDao;
	
	@Autowired
	private Conversor conversor;	

	public ServicioPaisRegistroImpl() {
	}

	@Override
	@Transactional
	public List<PaisRegistroDto> getPaisesRegistro(){
		return conversor.transform(paisRegistroDao.buscar(null), PaisRegistroDto.class);
	}
}
