package org.gestoresmadrid.oegam2comun.usoRustico.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.TipoUsoRustico;
import org.gestoresmadrid.oegam2comun.usoRustico.view.dto.UsoRusticoDto;

public interface ServicioUsoRustico extends Serializable{

	List<UsoRusticoDto> getListaUsoRusticoPorTipo(TipoUsoRustico tipo);

}
