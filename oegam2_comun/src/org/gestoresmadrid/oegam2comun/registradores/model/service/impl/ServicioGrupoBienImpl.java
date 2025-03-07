package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.GrupoBienDao;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioGrupoBien;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.GrupoBienDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioGrupoBienImpl implements ServicioGrupoBien {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5436935660580481260L;

	@Autowired
	private GrupoBienDao grupoBienDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional(readOnly=true)
	public List<GrupoBienDto> getListaGrupoBien() {
		return conversor.transform(grupoBienDao.buscar(null),GrupoBienDto.class);
	}


}
