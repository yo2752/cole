package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaDireccionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;

public interface ServicioLcPersonaDireccion extends Serializable {

	LcPersonaDireccionVO buscarDireccionExistente(LcDireccionVO direccion, String numColegiado, String numDocumento);

	LcPersonaDireccionVO buscarPersonaDireccion(String numColegiado, String nif);

	void guardarActualizar(LcPersonaDireccionVO lcPersonaDireccionVO);

	LcDireccionDto obtenerDireccionActiva(String numColegiado, String nif);
}
