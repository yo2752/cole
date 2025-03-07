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
 * Interfaz del modelo de solicitud de placas de matriculación
 * @author Santiago Cuenca
 * @date 11/02/2014
 *
 */
public interface PlacasMatriculacionService {
	
	ILoggerOegam log = LoggerOegam.getLogger(PlacasMatriculacionService.class);
	
	/**
	 * Analiza la matrícula e intenta determinar a que categoría pertenece (Turismo, Tractor, Ciclomotor...)
	 * @param matricula
	 * @return
	 */
	String analizarMatricula(String matricula);
	
	/**
	 * Devuelve un tipo de placa genérica en función del resultado devuelto por <em>analizarMatricula</em>(String <em>matricula</em>)
	 * @param matricula
	 * @return
	 */
	TipoPlacasMatriculasEnum getTipoPlacaPorDefecto(String matricula);
	
	/**
	 * Devuelve un vehículo con los datos necesarios para crear una solicitud de placas
	 * @param numExpediente
	 * @return
	 */
	VehiculoVO getVehiculoPlacasPorNumExpediente(Long numExpediente);
	
	/**
	 * Valida la información que contienen un bean de pantalla
	 * @param solicitudBean
	 * @return
	 */
	ValidacionPlacasBean validarSolicitud(SolicitudPlacaBean solicitudBean, EstadoSolicitudPlacasEnum estadoPrevio, int contador);
	
	/**
	 * Valida la información que contienen los bean de pantalla
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
	 * Descuenta el número de créditos especificado para una solicitud
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
	 * Obtiene la lista completa de solicitudes de placas de matrícula
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes();
	
	/**
	 * Obtiene la lista de solicitudes de matrícula asociada a un número de expediente
	 * @param numExpediente
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(Long numExpediente);
	
	/**
	 * Obtiene la lista de solicitudes de placas de matrícula asociadas a un usuario
	 * @param idUsuario
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(Integer idUsuario);
	
	/**
	 * Obtiene la lista de solicitudes de placas matrícula que se encuentran en un estado determinado
	 * @param estado
	 * @return
	 */
	List<SolicitudPlacaVO> getSolicitudes(EstadoSolicitudPlacasEnum estado);
	
	/**
	 * Obtiene la lista de solicitudes de placas de matrícula asociadas a un expediente, usuario y estado
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
	 * Obtiene una lista de solicitudes de pantalla, a partir de los parámetros de búsqueda
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
	 * Trata de comprobar si la solicitud ya existe a través de su clave única, y devuelve el id de la solicitud si la encuentra
	 * @param spBean
	 * @return
	 */
	Integer getIdSolicitudPorClaveUnica(SolicitudPlacaBean spBean);
	
	/**
	 * Trata de comprobar si la solicitud ya existe a través de su clave única, y devuelve la solicitud completa si la encuentra
	 * @param spBean
	 * @return
	 */
	SolicitudPlacaBean getSolicitudPorClaveUnica(SolicitudPlacaBean solicitudPlacaBean);
	
	/**
	 * Envía un correo electrónico y ordena una notificación en Oegam si procede
	 * @param spBean
	 * @param notificarOegam
	 * @return
	 */
	boolean notificar(SolicitudPlacaBean solicitudPlacaBean, boolean notificarOegam);
	
	/**
	 * Envía un correo electrónico y ordena una notificación en Oegam si procede
	 * @param listaSolicitudes
	 * @return
	 */
	HashMap<Long, Boolean> notificarVarias(List<SolicitudPlacaBean> lSolicitudPlacaBean, boolean notificarOegam);
	
	/**
	 * Envia un correo de confirmación de finalización de solicitud
	 * @param spBean
	 * @return
	 */
	boolean sendMail(SolicitudPlacaBean spBean, PropiedadesEmail propiedadesEmail);
	
	/**
	 * Lógica de negocio para la creación de las estadísticas de placas de matrícula
	 * @param spBean
	 * @return
	 */
	//byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean);
	
	byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean);
	
	/**
	 * Método para recuperar un usuario a través del idUsario
	 * @param idUsuario
	 * @return
	 */
	public Usuario getUsuario(Integer idUsuario);
	
	/**
	 * Método para borrar de forma lógica una solicitud
	 * @param solicitud
	 * @return
	 */
	public boolean borrarSolicitud(SolicitudPlacaVO solicitud);

	List<SolicitudPlacaBean> crearNuevasSolicitudes(List<SolicitudPlacaBean> listaSolicitudesp,
			List<TramiteTraficoVO> tramites, Usuario usuario, Integer idContrato) throws OegamExcepcion;
	
		
	
}
