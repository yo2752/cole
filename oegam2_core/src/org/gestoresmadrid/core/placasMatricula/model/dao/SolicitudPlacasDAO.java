package org.gestoresmadrid.core.placasMatricula.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;

public interface SolicitudPlacasDAO extends GenericDao<SolicitudPlacaVO>, Serializable {

	/**
	 * Obtiene una solicitud a través de su idSolicitud
	 * @param idSolicitud
	 * @param initialized
	 * @return
	 */
	SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized);

	/**
	 * Inicializa entidades hijas que pudieran ser lazys
	 * @param initialized
	 */
	void initLazys(SolicitudPlacaVO solicitud, String... initialized);

	/**
	 * Obtiene una solicitud completa a través de su clave única (numExpediente + fechaSolicitud)
	 * @param solicitud
	 * @return
	 */
	SolicitudPlacaVO getSolicitudPorClaveUnica(SolicitudPlacaVO solicitudPlacaVO);

	public List<SolicitudPlacaVO> generarEstadisticasSinAgrupacion(Date fechaInicio, Date fechaFin);

	public List<List<HashMap<Integer, String>>> generarEstadisticasAgrupadoPorVehiculo(Date fechaInicio, Date fechaFin);

	public HashMap<Integer, Integer> generarEstadisticasAgrupadoPorCreditos(Date fechaInicio, Date fechaFin);

	public HashMap<String, Integer> generarEstadisticasAgrupadoPorNumColegiado(Date fechaInicio, Date fechaFin);

	public HashMap<String, HashMap<Integer, Integer>> generarEstadisticasAgrupadoPorCreditosColegiado(Date fechaInicio, Date fechaFin);

	/**
	 * borra de forma lógica una solicitud placa poniendola con estado 0
	 */
	public boolean borrarSolicitud(SolicitudPlacaVO solicitud);

}
