package com.matriculasIvtmWS.integracion.service;

import java.io.Serializable;
import java.util.List;

import com.matriculasIvtmWS.integracion.model.dto.PropertiesDto;

public interface ServicioPropiedades extends Serializable{

	List<PropertiesDto> getlistaPropiedades();

	void actualizarProperties(String nombre, String valor, String entorno);

}
