package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoLocalDto;

public interface ServicioLcInfoLocal extends Serializable {

	ResultadoLicenciasBean guardarOActualizarInfoLocal(LcInfoLocalDto infoLocalDto);

	void validarInfoLocal(LcInfoLocalDto infoLocal, ResultadoLicenciasBean resultado);
}
