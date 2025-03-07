package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;

public interface ServicioModelos extends Serializable{

	ModeloDto getModeloDtoPorModelo(Modelo modelo);

}
