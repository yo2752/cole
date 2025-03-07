package org.gestoresmadrid.oegamPlacasMatricula.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;

public interface ServicioPersistenciaPlacasMatricula extends Serializable {

	public boolean borrarSolicitud(SolicitudPlacaVO solicitud);

	public SolicitudPlacaVO guardarOActualizar(SolicitudPlacaVO solicitudPlacaVO);

	public HashMap<String, HashMap<Integer, Integer>> generarEstadisticasAgrupadoPorCreditosColegiado(Date fechaInicio, Date fechaFin);

	public HashMap<String, Integer> generarEstadisticasAgrupadoPorNumColegiado(Date fechaInicio, Date fechaFin);

	public HashMap<Integer, Integer> generarEstadisticasAgrupadoPorCreditos(Date fechaInicio, Date fechaFin);

	public  List<List<HashMap<Integer, String>>> generarEstadisticasAgrupadoPorVehiculo(Date fechaInicio, Date fechaFin);

	public List<SolicitudPlacaVO> generarEstadisticasSinAgrupacion(Date fechaInicio, Date fechaFin);

	public SolicitudPlacaVO getSolicitudPorClaveUnica(SolicitudPlacaVO solicitudPlacaVO);

	public SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized);
}
