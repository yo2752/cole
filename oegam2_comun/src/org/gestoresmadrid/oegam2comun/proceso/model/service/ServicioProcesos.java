package org.gestoresmadrid.oegam2comun.proceso.model.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.proceso.view.bean.ResultadoProcesosBean;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;

public interface ServicioProcesos {
	
	String getNombreProceso(BigDecimal tipo);

	String getNombreProceso(Integer tipo);

	/**
	 * Devuelve el numero máximos de intentos configurado para el proceso
	 * @param nombreProceso Nombre del proceso
	 * @param nodo Nombre del nodo
	 * @return Número máximo de intentos
	 */
	BigDecimal getIntentosMaximos(String nombreProceso, String nodo);

	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param colaBean
	 * @param resultadoEjecucion
	 */
	void actualizarUltimaEjecucion(ColaBean colaBean, String resultadoEjecucion, String excepcion);

	void actualizarUltimaEjecucionNuevo(ColaDto cola, String resultadoEjecucion, String excepcion);

	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param proceso
	 * @param resultadoEjecucion
	 */
	void actualizarEjecucion(String proceso, String respuesta, String resultadoEjecucion, String numCola);

	void guardarEnvioData(String proceso, String respuesta, String resultadoEjecucion, String numCola);

	/**
	 * Marcar la solicitud con error servicio y anotar el mensaje de error
	 * @param cola
	 * @param respuestaError
	 */
	void marcarSolicitudConErrorServicio(ColaBean cola, String respuestaError);

	/**
	 * Devuelve la siguiente solicitud pendiente para el proceso en el hiloActivo, si existe alguna.
	 * @param nombreProceso Corresponde al ProcesosEnum.getNombreEnum()
	 * @param hiloActivo Corresponde al JobExecutionContext.getMergedJobDataMap().getInt(ConstantesProcesos.INDICE);
	 * @return
	 */
	ColaBean tomarSolicitud(String nombreProceso, Integer hiloActivo);

	ColaDto tomarSolicitudNuevo(String nombreProceso, Integer hiloActivo);

	void borrarSolicitud(Long idEnvio, String resultado, String proceso, String nodo);

	void errorSolicitud(Long idEnvio);

	void errorServicio(Long idEnvio, String respuesta);

	/**
	 * Comprueba si la solicitud ha superado el numero maximos de intentos que tenga configurados, y envía notificacion de correo en caso de que los supere
	 * @param solicitud
	 * @param respuestaError
	 * @return
	 */
	boolean tratarRecuperable(ColaBean solicitud, String respuestaError);

	/**
	 * Realiza el envío de un correo de notificación de que cierta petición ha superado su número máximo de envios con ERROR_RECUPERABLE pasando a estado 9 : ERROR_SERVICIO
	 * @param ColaBean
	 * @param error
	 */
	void notificarErrorServicio(ColaBean cola, String error);

	void notificarErrorServicioNuevo(ColaDto cola, String error);

	/**
	 * Realiza el envío de un correo de notificación de que cierta petición ha superado su número máximo de envios con ERROR_RECUPERABLE pasando a estado 9 : ERROR_SERVICIO
	 * @param proceso
	 * @param error
	 */
	void notificarErrorServicio(String proceso, String error);

	/**
	 * Realiza la busqueda del proceso por nodo y nombre del proceso
	 * @param proceso
	 * @param nodo
	 * @return
	 */
	ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo);

	/**
	 * Metodo que devuelve si existe enviodata del proceso en el dia
	 * @param proceso
	 * @return
	 */
	boolean hayEnvio(String proceso);

	ArrayList<String> getListaEnvio(String proceso);

	Date getUltimaFechaEnvioData(String proceso, String resultadoEjecucion);

	String getNombreProcesoOrdenado(Long tipo);

	ProcesoVO getProceso(String proceso);

	void actualizarProceso(ProcesoDto proceso);

	List<ProcesoDto> getListadoProcesos();

	ResultadoProcesosBean getListadoProcesosPantalla();

	ResultadoProcesosBean getListadoProcesosPorPatron(String patron);

	ResultadoProcesosBean pararProceso(String ordenProceso, String patron, Long idUsuario, String username);

	ResultadoProcesosBean pararProcesoPorPatron(String patron, Long idUsuario);

	ResultadoProcesosBean activarProceso(String ordenProceso, String patron, Long idUsuario, String username);

	ResultadoProcesosBean actualizarGestorColas(String patron);

	ResultadoProcesosBean modificar(String ordenProceso, String intentosMax, String hilosColas, String horaInicio,
			String horaFin, String tiempoCorrecto, String tiempoRecuperable, String tiempoNoRecuperable,
			String tiempoSinDatos, String patron);

	ResultadoProcesosBean activarProcesoPorPatron(String patron, Long idUsuario);

	List<EnvioDataVO> recuperarUltimasEjecuciones(String proceso, String resultadoEjecucion);

	List<EnvioDataVO> recuperarUltimasOk(String proceso, String cola);

	List<EnvioDataVO> ejecucionesUltimasProceso(String proceso, String correcta);

	List<ProcesoVO> getProcesosPatron(String nodo, ArrayList<String> procesosPatron);

}
