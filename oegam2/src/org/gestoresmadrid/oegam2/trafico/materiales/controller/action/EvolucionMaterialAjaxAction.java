package org.gestoresmadrid.oegam2.trafico.materiales.controller.action;

import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaEvolucionMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.EvolucionMaterialDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class EvolucionMaterialAjaxAction extends ActionBase {

	private static final long serialVersionUID = -4450960067950692287L;

	private static final String SUCCESS = "success";

	private static final ILoggerOegam log = LoggerOegam.getLogger(EvolucionMaterialAjaxAction.class);

	@Autowired ServicioConsultaEvolucionMateriales servicioConsultaEvolucionMateriales;

	private String descripcion;
	private Long evolucion;

	public String obtener() {
		log.info("Obtener descripcion");
		EvolucionMaterialDto evolucionMaterialDto = servicioConsultaEvolucionMateriales.obtenerMaterialForPrimaryKey(evolucion);
		this.setDescripcion(evolucionMaterialDto.getDescripcion());
		return SUCCESS;
	}

	// Getters & Setters

	public Long getEvolucion() {
		return evolucion;
	}

	public void setEvolucion(Long evolucion) {
		this.evolucion = evolucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}