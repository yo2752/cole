package org.gestoresmadrid.oegam2comun.grupo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.view.dto.GruposDto;

public interface ServicioGrupo extends Serializable {

	GruposDto getGrupo(String idGrupo);

	List<GruposDto> getGrupos();

	List<DatoMaestroBean> getComboGrupos();

	String getDescripcionGrupo(String idGrupo);
}
