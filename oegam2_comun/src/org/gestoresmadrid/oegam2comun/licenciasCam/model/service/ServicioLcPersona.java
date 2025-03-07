package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcPersonaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcPersonaDto;

import escrituras.beans.ResultBean;

public interface ServicioLcPersona extends Serializable {

	public static String PERSONA = "PERSONA";

	ResultBean guardarActualizar(LcPersonaDto persona);

	LcPersonaVO getLcPersona(String nif, String numColegiado);

	LcPersonaDto getLcPersonaDto(String nif, String numColegiado);
}
