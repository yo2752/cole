package colas.procesos;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public interface ProcesoDummy {
	
	public static ILoggerOegam log = LoggerOegam.getLogger(ProcesoDummy.class);
	public static final String PROCESO_BASE = "base";
	public static final String ERROR_SERVICIO = "ERROR SERVICIO";
	public static final String FINALIZADO_CON_ERROR = "FINALIZADO CON ERROR";
	 
	/**
	 * Comprueba la validez del usuario dummy 
	 * @param numExpediente
	 * @param proceso
	 * @return
	 */
	public boolean checkDummyUser(Long numExpediente, String proceso);
	
	/**
	 * Obtiene el dummy message para el proceso en cuestión
	 * @param proceso
	 * @return
	 */
	public String getDummyMessage(String proceso);
	
	/**
	 * Obtiene el tipo de finalización para un proceso dummy
	 * @param proceso
	 * @return
	 */
	public String getTipoFinalizacion(String proceso);

}
