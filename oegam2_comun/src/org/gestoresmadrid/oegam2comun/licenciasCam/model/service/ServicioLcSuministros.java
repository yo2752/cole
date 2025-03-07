package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosSuministrosDto;

public interface ServicioLcSuministros extends Serializable {

	ResultadoLicenciasBean guardarOActualizarSuministro(LcDatosSuministrosDto suministrosDto);
}
