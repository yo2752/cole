package org.gestoresmadrid.oegam2comun.proceso.model.service;

import java.io.Serializable;

public interface ServicioUsuarioConexionGestProcesos extends Serializable {

	public static String ACCESO_PROCESO = "ACCESO a PROCESOS";
	public static String ACCESO_COLA = "ACCESO a COLA";
	public static String ARRANCAR = "ARRANCADO";
	public static String PARAR = "PARADO";
	public static String ARRANCAR_PATRON = "ARRANCADO PATRON";
	public static String PARAR_PATRON = "PARADO PATRON";

	void guardarUltimaConexion(Long idGestProcesos, String ipAcceso, String frontal, String tipoAcceso);

	void registrarOperacion(Long idGestProcesos, String tipoOperacion, String procesoPatron, Long idUsuario);
}
