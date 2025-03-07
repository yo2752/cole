package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcIntervinienteDto;

public interface ServicioLcInterviniente extends Serializable {

	ResultadoLicenciasBean guardarInterviniente(LcIntervinienteDto interviniente);

	ResultadoLicenciasBean buscarPersona(String nif, String numColegiado);

	ResultadoLicenciasBean borrarInterviniente(Long id);

	LcIntervinienteDto getInterviniente(Long id);

}
