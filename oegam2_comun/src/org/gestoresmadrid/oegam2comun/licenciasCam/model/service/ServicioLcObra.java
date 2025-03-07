package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcObraDto;

public interface ServicioLcObra extends Serializable {

	ResultadoLicenciasBean guardarOActualizarObra(LcObraDto obraDto);

	void validarDatosObra(LcObraDto obra, ResultadoLicenciasBean resultado);
}
