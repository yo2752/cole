package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioBajaDto;

public interface ServicioLcInfoEdificioBaja extends Serializable {

	ResultadoLicenciasBean getDatosInfoEdificioBaja(long identificador);

	ResultadoLicenciasBean borrarInfoEdificioBaja(long id);

	ResultadoLicenciasBean guardarOActualizarInfoEdificioBaja(LcInfoEdificioBajaDto infoEdificioBajaDto);

	void validarInfoEdificacionBaja(LcInfoEdificioBajaDto baja, ResultadoLicenciasBean resultado);
}
