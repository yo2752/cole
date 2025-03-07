package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEpigrafeDto;

public interface ServicioLcEpigrafe extends Serializable {

	ResultadoLicenciasBean guardarOActualizarEpigrafe(LcEpigrafeDto epigrafeDto);

	ResultadoLicenciasBean borrarEpigrafe(long id);

	ResultadoLicenciasBean getEpigrafe(long identificador);

}
