package org.gestoresmadrid.oegam2comun.model.service;

import java.util.List;

import org.gestoresmadrid.oegam2comun.modelos.view.dto.ChartDataDto;

import colas.beans.ProcesoMonitorizado;


public interface ServicioMonitor {
	
	List<ProcesoMonitorizado> monitorizaProcesosTrafico();

	List<ProcesoMonitorizado> monitorizaProcesosGDoc();

	/**
	 * 
	 * @param proceso
	 * @return
	 */
	ChartDataDto listarSolicitudes(String proceso);

	/**
	 * 
	 * @param proceso
	 * @return
	 */
	ChartDataDto listarFinalizaciones(String proceso);

	/**
	 * 
	 * @return
	 */
	ChartDataDto peticionesTotales();

}
