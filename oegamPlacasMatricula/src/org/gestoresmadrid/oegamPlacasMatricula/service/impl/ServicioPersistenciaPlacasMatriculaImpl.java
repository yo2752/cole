package org.gestoresmadrid.oegamPlacasMatricula.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.placasMatricula.model.dao.SolicitudPlacasDAO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioPersistenciaPlacasMatricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaPlacasMatriculaImpl implements ServicioPersistenciaPlacasMatricula {

	private static final long serialVersionUID = -7231602630532743136L;

	@Autowired
	private SolicitudPlacasDAO solicitudPlacasDao;

	@Override
	@Transactional
	public SolicitudPlacaVO guardarOActualizar(SolicitudPlacaVO solicitudPlacaVO) {
		return solicitudPlacasDao.guardarOActualizar(solicitudPlacaVO);
	}

	@Override
	@Transactional
	public boolean borrarSolicitud(SolicitudPlacaVO solicitud) {
		return solicitudPlacasDao.borrarSolicitud(solicitud);
	}

	@Override
	@Transactional
	public HashMap<String, HashMap<Integer, Integer>> generarEstadisticasAgrupadoPorCreditosColegiado(Date fechaInicio, Date fechaFin) {
		return solicitudPlacasDao.generarEstadisticasAgrupadoPorCreditosColegiado(fechaInicio, fechaFin);
	}

	@Override
	@Transactional
	public HashMap<String, Integer> generarEstadisticasAgrupadoPorNumColegiado(Date fechaInicio, Date fechaFin) {
		return solicitudPlacasDao.generarEstadisticasAgrupadoPorNumColegiado(fechaInicio, fechaFin);
	}

	@Override
	@Transactional
	public HashMap<Integer, Integer> generarEstadisticasAgrupadoPorCreditos(Date fechaInicio, Date fechaFin) {
		return solicitudPlacasDao.generarEstadisticasAgrupadoPorCreditos(fechaInicio, fechaFin);
	}

	@Override
	@Transactional
	public List<List<HashMap<Integer, String>>> generarEstadisticasAgrupadoPorVehiculo(Date fechaInicio, Date fechaFin) {
		return solicitudPlacasDao.generarEstadisticasAgrupadoPorVehiculo(fechaInicio, fechaFin);
	}

	@Override
	@Transactional
	public List<SolicitudPlacaVO> generarEstadisticasSinAgrupacion(Date fechaInicio, Date fechaFin) {
		return solicitudPlacasDao.generarEstadisticasSinAgrupacion(fechaInicio, fechaFin);
	}

	@Override
	@Transactional
	public SolicitudPlacaVO getSolicitudPorClaveUnica(SolicitudPlacaVO solicitudPlacaVO) {
		return solicitudPlacasDao.getSolicitudPorClaveUnica(solicitudPlacaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized) {
		return solicitudPlacasDao.getSolicitud(idSolicitud, initialized);
	}

}