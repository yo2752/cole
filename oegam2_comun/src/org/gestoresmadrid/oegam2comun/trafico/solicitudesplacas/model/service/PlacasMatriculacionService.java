package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoPlacasMatriculasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.EstadisticasPlacasBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.SolicitudPlacaBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.ValidacionPlacasBean;

import hibernate.entities.general.Usuario;
import utiles.correo.PropiedadesEmail;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Interfaz del modelo de solicitud de placas de matriculaci�n
 * @author Santiago Cuenca
 * @date 11/02/2014
 *
 */
public interface PlacasMatriculacionService {
	
	ILoggerOegam log = LoggerOegam.getLogger(PlacasMatriculacionService.class);
	
	/**
	 * Analiza la matr�cula e intenta determinar a que categor�a pertenece (Turismo, Tractor, Ciclomotor...)
	 * @param matricula
	 * @return
	 */
	String analizarMatricula(String matricula);
	
	/**
	 * Devuelve un tipo de placa gen�rica en funci�n del resultado devuelto por <em>analizarMatricula</em>(String <em>matricula</em>)
	 * @param matricula
	 * @return
	 */
	TipoPlacasMatriculasEnum getTipoPlacaPorDefecto(String matricula);
	
	/**
	 * Devuelve un veh�culo con los datos necesarios para crear una solicitud de placas
	 * @param numExpediente
	 * @return
	 */
	VehiculoVO getVehiculoPlacasPorNumExpediente(Long numExpediente);
	
	/**
	 * Valida la informaci�n que contienen un bean de pantalla
	 * @param solicitudBean
	 * @return
	 */
	ValidacionPlacasBean validarSolicitud(SolicitudPlacaBean solicitudBean, EstadoSolicitudPlacasEnum estadoPrevio, int contador);
	
	/**
	 * Valida la informaci�n que contienen los bean de pantalla
	 * @param listaSolicitudes
	 * @return
	 */
	List<ValidacionPlacasBean> validarSolicitudes(List<SolicitudPlacaBean> listaSolicitudes, EstadoSolicitudPlacasEnum estadoPrevio);
	
	/**
	 * Convierte una solicitud de pantalla, en un objeto usable por el DAO
	 * @param solicitudBean
	 * @return
	 */
	SolicitudPlacaVO prepararSolicitud(SolicitudPlacaBean solicitudBean);
	
	/**
	 * Convierte una lista de solicitudes de pantalla, en objetos usables por el DAO
	 * @param listaSolicitudes
	 * @return
	 */
	List<SolicitudPlacaVO> prepararSolicitudes(List<SolicitudPlacaBean> listaSolicitudes);
	
	/**
	 * Obtiene una lista de solicitudes de pantalla, para realizar la solicitud. Debe llamar al menos a <em>prepararSolicitudes</em>(ArrayList<SolicitudPlacaBean> <em>listaSolicitudes</em>) y <em>guardarSolicitudes</em>(ArrayList<SolicitudPlaca> <em>solicitudes</em>) para realizar el proceso.
	 * @param listaSolicitudes
	 * @return
	 */
	List<ValidacionPlacasBean> realizarSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean);
	
	/**
	 * Obtiene una lista de solicitudes de pantalla, para realizar la solicitud (guardar + confirmar). Debe llamar al menos a <em>prepararSolicitudes</em>(ArrayList<SolicitudPlacaBean> <em>listaSolicitudes</em>) y <em>guardarSolicitudes</em>(ArrayList<SolicitudPlaca> <em>solicitudes</em>) para realizar el proceso.
	 * @param listaSolicitudes
	 * @return
	 */
	List<ValidacionPlacasBean> confirmarSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean);
	
	/**
	 * Finaliza una solicitud o lista de solicitudes, enviando las notificaciones pertinentes
	 * @param listaSolicitudes
	 * @return
	 */
	List<ValidacionPlacasBean> finalizarSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean);
	
	/**
	 * Persiste en BBDD la solicitud, llamando al DAO correspondiente
	 * @param solicitud
	 * @return
	 */
	SolicitudPlacaVO guardarSolicitud(SolicitudPlacaVO solicitud, boolean descontarCreditos);
	
	/**
	 * Persiste en BBDD una lista de solicitudes, llamando al DAO correspondiente
	 * @param solicitudes
	 * @return
	 */
	List<ValidacionPlacasBean> guardarSolicitudes(List<SolicitudPlacaVO> solicitudes, boolean descontarCreditos,boolean soloEstado);
	
	/**
	 * Descuenta el n�mero de cr�ditos especificado para una solicitud
	 * @param solicitud
	 * @param numeroCreditos
	 * @return
	 */
	boolean modificarCreditosSolicitudPlacas(SolicitudPlacaVO solicitud, boolean incremental);
	
	/**
	 * Obtiene una solicitud por su idSolicitud
	 * @return
	 */
	SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized);
	
	/**
	 * Obtiene la lista completa de solicitudes de placas de matr�cula
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes();
	
	/**
	 * Obtiene la lista de solicitudes de matr�cula asociada a un n�mero de expediente
	 * @param numExpediente
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(Long numExpediente);
	
	/**
	 * Obtiene la lista de solicitudes de placas de matr�cula asociadas a un usuario
	 * @param idUsuario
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(Integer idUsuario);
	
	/**
	 * Obtiene la lista de solicitudes de placas matr�cula que se encuentran en un estado determinado
	 * @param estado
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(EstadoSolicitudPlacasEnum estado);
	
	/**
	 * Obtiene la lista de solicitudes de placas de matr�cula asociadas a un expediente, usuario y estado
	 * @param numExpediente
	 * @param idUsuario
	 * @param estado
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(Long numExpediente, Integer idUsuario, EstadoSolicitudPlacasEnum estado);
	
	/**
	 * Obtiene una solicitud apta para pantalla, a partir de un identificador de solicitud dado
	 * @param idSolicitud
	 * @return
	 */
	SolicitudPlacaBean getSolicitudPantalla(Integer idSolicitud);
	
	/**
	 * Devuelve una lista de solicitudes aptas para pantalla
	 * @return
	 */
	List<SolicitudPlacaBean> getSolicitudesPantalla(Integer[] idSolicitudes);
	
	/**
	 * Convierte a una solicitud apta para pantalla, una solicitud almacenada en BBDD
	 * @param solicitud
	 * @return
	 */
	SolicitudPlacaBean convertSolicitudPantalla(SolicitudPlacaVO solicitud);
	
	/**
	 * Convierte a una lista de solicitudes aptas para pantalla, una lista de solicitudes almacenadas en BBDD
	 * @param solicitudes
	 * @return
	 */
	List<SolicitudPlacaBean> convertSolicitudesPantalla(List<SolicitudPlacaVO> solicitudes);

	/**
	 * Obtiene una lista de solicitudes de pantalla, a partir de los par�metros de b�squeda
	 * @param 
	 * @return
	 */
	List<SolicitudPlacaBean> buscar(Date fechaInicio, Date fechaFin, String numColegiado, Long numExpediente, String matricula, String tipoMatricula);
	
	/**
	 * Cambia el estado de una lista de solicitudes
	 * @param listaSolicitudes
	 * @param estado
	 * @return
	 */
	boolean cambiarEstadoSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean, String estado);
	
	/**
	 * Trata de comprobar si la solicitud ya existe a trav�s de su clave �nica, y devuelve el id de la solicitud si la encuentra
	 * @param spBean
	 * @return
	 */
	Integer getIdSolicitudPorClaveUnica(SolicitudPlacaBean spBean);
	
	/**
	 * Trata de comprobar si la solicitud ya existe a trav�s de su clave �nica, y devuelve la solicitud completa si la encuentra
	 * @param spBean
	 * @return
	 */
	SolicitudPlacaBean getSolicitudPorClaveUnica(SolicitudPlacaBean solicitudPlacaBean);
	
	/**
	 * Env�a un correo electr�nico y ordena una notificaci�n en Oegam si procede
	 * @param spBean
	 * @param notificarOegam
	 * @return
	 */
	boolean notificar(SolicitudPlacaBean solicitudPlacaBean, boolean notificarOegam);
	
	/**
	 * Env�a un correo electr�nico y ordena una notificaci�n en Oegam si procede
	 * @param listaSolicitudes
	 * @return
	 */
	HashMap<Long, Boolean> notificarVarias(List<SolicitudPlacaBean> lSolicitudPlacaBean, boolean notificarOegam);
	
	/**
	 * Envia un correo de confirmaci�n de finalizaci�n de solicitud
	 * @param spBean
	 * @return
	 */
	boolean sendMail(SolicitudPlacaBean spBean, PropiedadesEmail propiedadesEmail);
	
	/**
	 * L�gica de negocio para la creaci�n de las estad�sticas de placas de matr�cula
	 * @param spBean
	 * @return
	 */
	//byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean);
	
	byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean);
	
	/**
	 * M�todo para recuperar un usuario a trav�s del idUsario
	 * @param idUsuario
	 * @return
	 */
	public Usuario getUsuario(Integer idUsuario);
	
	/**
	 * M�todo para borrar de forma l�gica una solicitud
	 * @param solicitud
	 * @return
	 */
	public boolean borrarSolicitud(SolicitudPlacaVO solicitud);

	List<SolicitudPlacaBean> crearNuevasSolicitudes(List<SolicitudPlacaBean> listaSolicitudesp,
			List<TramiteTraficoVO> tramites, Usuario usuario, Integer idContrato) throws OegamExcepcion;
	
		
	
}
