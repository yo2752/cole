package com.matriculasIvtmWS.integracion.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.matriculasIvtmWS.integracion.model.dto.PropertiesDto;

public interface ServicioProperties extends Serializable{

	List<PropertiesDto> getlistaPropertiesProceso();

	HashMap<String, String> refrescarProperties();

}
