package org.gestoresmadrid.oegamSanciones.service.impl;

import java.util.List;

import org.gestoresmadrid.core.sancion.model.dao.SancionDao;
import org.gestoresmadrid.core.sancion.model.vo.SancionVO;
import org.gestoresmadrid.oegamSanciones.service.ServicioPersistenciaSanciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaSancionesImpl implements ServicioPersistenciaSanciones {

	private static final long serialVersionUID = 7541060406736535945L;

	@Autowired
	SancionDao sancionDao;

	@Override
	@Transactional
	public SancionVO guardarOActualizar(SancionVO sancionVO) {
		return sancionDao.guardarOActualizar(sancionVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SancionVO getSancionPorId(Integer idSancion) {
		return sancionDao.getSancionId(idSancion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SancionVO> obtenerListadoPorMotivo(SancionVO sancion) throws Throwable {
		return sancionDao.listado(sancion);
	}

}
