package org.oegam.gestor.distintivos.service;

import java.io.Serializable;
import java.util.List;

import org.oegam.gestor.distintivos.integracion.dto.PropertiesDto;

public interface ServicioPropiedades extends Serializable{

	List<PropertiesDto> getlistaPropiedades();

	void actualizarProperties(String nombre, String valor, String entorno);

}
