package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.DirectivaCeeDto;

import trafico.beans.HomologacionBean;

public interface ServicioDirectivaCee extends Serializable {

	public static String TODOS = "**";

	List<DirectivaCeeDto> listadoDirectivaCee(String criterioConstruccion);

	HomologacionBean getHomologacionBean(String idDirectivaCEE);

}
