package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcActuacionDto;

public interface ServicioLcActuacion extends Serializable {

	ResultadoLicenciasBean guardarOActualizarActuacion(LcActuacionDto actuacionDto);

	void validarDatosActuacion(LcActuacionDto actuacion, ResultadoLicenciasBean resultado);
}
