package org.gestoresmadrid.oegamPlacasMatricula.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.oegamPlacasMatricula.view.bean.ValidacionPlacasBean;
import org.gestoresmadrid.oegamPlacasMatricula.view.dto.SolicitudPlacaDto;

import trafico.beans.EstadisticasPlacasBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public interface ServicioPlacasMatricula extends Serializable {

	ILoggerOegam log = LoggerOegam.getLogger(ServicioPlacasMatricula.class);

	/**
	 * Método para invocar al conversor para paso de lista de SolicitudPlacaVO a lista de SolicitudPlacaDto
	 * @param lista
	 * @return
	 */
	public List<SolicitudPlacaDto> convertirListaEnBeanPantallaConsulta(List<SolicitudPlacaVO> lista);

	/**
	 * Obtiene una solicitud por su idSolicitud
	 * @return
	 */
	public SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized);

	/**
	 * Obtiene una solicitud apta para pantalla, a partir de un identificador de solicitud dado
	 * @param idSolicitud
	 * @return
	 */
	public SolicitudPlacaDto getSolicitudPantalla(Integer idSolicitud);

	/**
	 * Trata de comprobar si la solicitud ya existe a través de su clave única, y devuelve la solicitud completa si la encuentra
	 * @param spBean
	 * @return
	 */
	public SolicitudPlacaDto getSolicitudPorClaveUnica(SolicitudPlacaDto spBean);

	/**
	 * Envía un correo electrónico y ordena una notificación en Oegam si procede
	 * @param spBean
	 * @param notificarOegam
	 * @return
	 */
	boolean notificar(SolicitudPlacaDto spBean, boolean notificarOegam);

	/**
	 * Envía un correo electrónico y ordena una notificación en Oegam si procede
	 * @param listaSolicitudes
	 * @return
	 */
	public HashMap<Integer, Boolean> notificarVarias(ArrayList<SolicitudPlacaDto> listaSolicitudes, boolean notificarOegam);

	/**
	 * Lógica de negocio para la creación de las estadísticas de placas de matrícula
	 * @param spBean
	 * @return
	 */
	public byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean);

	/**
	 * Crea nuevas solicitudes para los vehículos pasados por parámetro y las añade a la lista
	 * @param expedientes
	 * @param listaSolicitudes
	 * @param idUsuario
	 * @param idContrato
	 */
	public ArrayList<SolicitudPlacaDto> crearNuevasSolicitudes(List<SolicitudPlacaDto> listaSolicitudes, String[] expedientes, UsuarioVO usuario, Integer idContrato) throws OegamExcepcion;

	public ArrayList<ValidacionPlacasBean> realizarSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes);

	public ArrayList<ValidacionPlacasBean> confirmarSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes);

	public ArrayList<ValidacionPlacasBean> finalizarSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes);

	public boolean cambiarEstadoSolicitud(ArrayList<SolicitudPlacaDto> listaSolicitudes);

	public boolean borrarSolicitud(SolicitudPlacaVO solicitud);

}
