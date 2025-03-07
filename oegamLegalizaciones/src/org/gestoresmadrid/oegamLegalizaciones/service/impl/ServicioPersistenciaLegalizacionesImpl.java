package org.gestoresmadrid.oegamLegalizaciones.service.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.legalizacion.model.dao.LegalizacionDao;
import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioPersistenciaLegalizaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaLegalizacionesImpl implements ServicioPersistenciaLegalizaciones {

	private static final long serialVersionUID = -6911755972162581967L;

	@Autowired
	private LegalizacionDao legalizacionDao;

	@Override
	@Transactional(readOnly = true)
	public LegalizacionCitaVO obtenerLegalizacionPorId(int idPeticion) {
		return legalizacionDao.getLegalizacionId(idPeticion);
	}

	@Override
	@Transactional
	public LegalizacionCitaVO guardarOActualizar(LegalizacionCitaVO legalizacionCitaVO) {
		return legalizacionDao.guardarOActualizar(legalizacionCitaVO);
	}

	@Override
	@Transactional
	public boolean esPosiblePeticion(String numColegiado) {
		return legalizacionDao.esPosiblePeticion(numColegiado);
	}

	@Override
	@Transactional
	public List<LegalizacionCitaVO> listadoDiario(String numColegiado, Date fechaLegalizacion) {
		return legalizacionDao.listadoDiario(numColegiado, fechaLegalizacion);
	}

}
