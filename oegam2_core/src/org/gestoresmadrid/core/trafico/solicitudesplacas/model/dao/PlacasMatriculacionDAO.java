package org.gestoresmadrid.core.trafico.solicitudesplacas.model.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.hibernate.Transaction;

import hibernate.entities.general.JefaturaTrafico;
import hibernate.entities.general.Usuario;
import hibernate.entities.personas.Persona;

public interface PlacasMatriculacionDAO extends GenericDao<SolicitudPlacaVO>, Serializable {
	
	//ILoggerOegam log = LoggerOegam.getLogger(PlacasMatriculacionDAO.class);
	
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
	 * Ejecuta la transacción pasada por parámetro
	 * @param tx
	 * @return
	 * @throws Exception
	 */
	boolean ejecutarTransacciones (Transaction tx) throws Exception;
	
	/**
	 * Realiza una búsqueda de solicitudes en función de los parámetros pasados
	 * @param fechaInicio
	 * @param fechaFin
	 * @param numColegiado
	 * @param numExpediente
	 * @param matricula
	 * @param tipoMatricula
	 * @param initialized
	 * @return
	 */
	ArrayList<SolicitudPlacaVO> buscar(Date fechaInicio, Date fechaFin, String numColegiado, Long numExpediente, String matricula, String tipoMatricula, String... initialized);
	
	/**
	 * Obtiene el id de una solicitud a través de su clave única (numExpediente + fechaSolicitud)
	 * @param solicitud
	 * @return
	 */
	Integer getIdSolicitudPorClaveUnica(SolicitudPlacaVO solicitud);
	
	/**
	 * Obtiene una solicitud completa a través de su clave única (numExpediente + fechaSolicitud)
	 * @param solicitud
	 * @return
	 */	
	SolicitudPlacaVO getSolicitudPorClaveUnica(SolicitudPlacaVO solicitudPlacaVO);
	
	

	public List<SolicitudPlacaVO> generarEstadisticasSinAgrupacion(Date fechaInicio,Date fechaFin);
	
	@SuppressWarnings("rawtypes")
	public ArrayList<List> generarEstadisticasAgrupadoPorVehiculo(Date fechaInicio,Date fechaFin);
	public HashMap<Integer, Integer> generarEstadisticasAgrupadoPorCreditos(Date fechaInicio,Date fechaFin);
	public HashMap<String, Integer> generarEstadisticasAgrupadoPorNumColegiado(Date fechaInicio,Date fechaFin);
	public HashMap<String, HashMap<Integer, Integer>> generarEstadisticasAgrupadoPorCreditosColegiado(Date fechaInicio,Date fechaFin);
	
	/**
	 * Obtiene la persona del titular por el nif y colegiado
	 * @param nif
	 * @return
	 */
	Persona getPersonaFromNif(String nif, String numColegiado);
	
	/**
	 * Devuelve un registro de la tabla solicitudPlaca buscando por matricula.
	 * Si no existe ningun registro devuelve null 
	 */
	//public SolicitudPlaca getSolicitudPlacaBean(String matricula);
	
	/**
	 * Recupera el objeto Usuario dándole el idUsuario que es PK.
	 * @param idUsuario
	 * @return
	 */
	public Usuario getUsuario(Integer idUsuario);
	
	/**
	 * Devuelve la entidad jefatura provincial pasandole el valor del campo jefatura_provincial
	 * @param jefaturaProv
	 * @return
	 */
	public JefaturaTrafico getJefaturaFromId(String jefaturaProv);
	
	 /**
	  * borra de forma lógica una solicitud placa poniendola con estado 0 
	  */
	public boolean borrarSolicitud (SolicitudPlacaVO solicitud);
	
	
	/**
	 * Obtiene todas las solicitudes
	 */
	public List<SolicitudPlacaVO> obtenerListaSolicitudes();
}
