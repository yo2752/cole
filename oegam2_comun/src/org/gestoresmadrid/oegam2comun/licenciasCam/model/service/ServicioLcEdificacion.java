package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEdificacionDto;

public interface ServicioLcEdificacion extends Serializable {

	ResultadoLicenciasBean guardarOActualizarEdificacion(LcEdificacionDto edificacionDto, boolean tipoAlta);

	void validarEdificacionAlta(LcEdificacionDto edifAlta, ResultadoLicenciasBean resultado);

	void validarEdificacionBaja(LcEdificacionDto edifBaja, ResultadoLicenciasBean resultado);
}
