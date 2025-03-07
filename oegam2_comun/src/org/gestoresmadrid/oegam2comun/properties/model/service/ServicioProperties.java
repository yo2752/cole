package org.gestoresmadrid.oegam2comun.properties.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.properties.view.dto.PropertiesDto;

import escrituras.beans.ResultBean;

public interface ServicioProperties extends Serializable {

	List<PropertiesDto> getlistaProperties();

	void actualizarProperties(String nombre, String valor, String entorno);

	ResultBean refrescarProperties();

	ResultBean actualizarPropertiePantalla(String id, String valorNuevo);

}
