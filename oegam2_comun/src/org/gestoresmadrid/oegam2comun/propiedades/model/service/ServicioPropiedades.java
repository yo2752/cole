package org.gestoresmadrid.oegam2comun.propiedades.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.properties.view.dto.PropertiesDto;

public interface ServicioPropiedades extends Serializable{

	List<PropertiesDto> getlistaPropiedades();

	void actualizarProperties(String nombre, String valor, String entorno);

}
