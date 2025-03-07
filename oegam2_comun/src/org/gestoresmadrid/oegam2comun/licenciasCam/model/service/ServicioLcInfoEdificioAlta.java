package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioAltaDto;

public interface ServicioLcInfoEdificioAlta extends Serializable {

	ResultadoLicenciasBean guardarOActualizarInfoEdificioAlta(LcInfoEdificioAltaDto infoEdificioAltaDto);

	ResultadoLicenciasBean getDatosInfoEdificioAlta(long identificador);

	ResultadoLicenciasBean borrarInfoEdificioAlta(long id);

	List<LcInfoEdificioAltaDto> getInfoEdificiosAlta(long id);

	void validarInfoEdificacionAlta(LcInfoEdificioAltaDto alta, ResultadoLicenciasBean resultado);
}
