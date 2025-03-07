package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaIncidencias;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class IncidenciaAjaxAction extends ActionBase {

	private static final long serialVersionUID = -4450960067950692287L;

	private static final String SUCCESS = "success";

	@SuppressWarnings("unused")
	private static final ILoggerOegam log = LoggerOegam.getLogger(IncidenciaAjaxAction.class);

	@Autowired ServicioConsultaIncidencias servicioConsultaIncidencias;

	private String	descripcion;
	private Long	incidencia;

	public String obtener() {
		IncidenciaDto incidenciaDto = servicioConsultaIncidencias.getIncidencia(incidencia);
		this.setDescripcion(incidenciaDto.getObservaciones());
		return SUCCESS;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Long incidencia) {
		this.incidencia = incidencia;
	}

	// Getters & Setters

}