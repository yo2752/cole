package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcResumenEdificacionDto;

public interface ServicioLcResumenEdificacion extends Serializable {

	ResultadoLicenciasBean guardarOActualizarResumenEdificacion(LcResumenEdificacionDto resumenEdificacionDto);
}
