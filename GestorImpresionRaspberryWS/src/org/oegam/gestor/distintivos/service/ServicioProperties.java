package org.oegam.gestor.distintivos.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.oegam.gestor.distintivos.integracion.dto.PropertiesDto;

public interface ServicioProperties extends Serializable{

	List<PropertiesDto> getlistaPropertiesProceso();

	HashMap<String, String> refrescarProperties();

}
