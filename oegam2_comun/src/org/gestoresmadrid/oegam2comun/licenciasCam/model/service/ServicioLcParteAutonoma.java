package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcParteAutonomaDto;

public interface ServicioLcParteAutonoma extends Serializable {

	ResultadoLicenciasBean getParteAutonoma(long identificador);

	ResultadoLicenciasBean guardarOActualizarParteAutonoma(LcParteAutonomaDto parteAutonomaDto);

	ResultadoLicenciasBean borrarParteAutonoma(long id);
}
