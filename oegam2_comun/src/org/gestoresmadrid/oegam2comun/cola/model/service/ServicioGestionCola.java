package org.gestoresmadrid.oegam2comun.cola.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.cola.view.bean.ConsultaGestionColaBean;
import org.gestoresmadrid.oegam2comun.cola.view.bean.ResultadoGestionColaBean;

public interface ServicioGestionCola extends Serializable {

	public static String NOMBRE_HOST_PROC = "nombreHostProceso";

	ResultadoGestionColaBean getListasGestion(ConsultaGestionColaBean consultaGestionCola);

	ResultadoGestionColaBean getListaRecargarUltimasEjecuciones(ConsultaGestionColaBean consultaGestionCola, int page, String sort, String dir, int numPorPage);

	ResultadoGestionColaBean getListaRecargarPeticionesCola(ConsultaGestionColaBean consultaGestionColaBean, int page, String sort, String dir, int numPorPage);

	ResultadoGestionColaBean eliminarColas(String idsEnvios, ConsultaGestionColaBean consultaGestionCola);

	ResultadoGestionColaBean finalizarConErrorServicioEnvios(String idsEnvios, ConsultaGestionColaBean consultaGestionCola);

	ResultadoGestionColaBean finalizarConErrorEnvios(String idsEnvios, ConsultaGestionColaBean consultaGestionCola);

	ResultadoGestionColaBean reactivarEnvios(String idsEnvios, ConsultaGestionColaBean consultaGestionCola);

	ResultadoGestionColaBean maxPrioridadEnvios(String idEnvio, ConsultaGestionColaBean consultaGestionCola);

}
