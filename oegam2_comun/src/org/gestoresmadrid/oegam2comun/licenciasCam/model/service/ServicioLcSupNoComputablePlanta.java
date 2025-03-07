package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcSupNoComputablePlantaDto;

public interface ServicioLcSupNoComputablePlanta extends Serializable {

	ResultadoLicenciasBean getSupNoComputablePlanta(long identificador);

	List<LcSupNoComputablePlantaDto> getSupNoComputablesPlanta(long id);

	ResultadoLicenciasBean borrarSupNoComputablePlanta(long id);

	ResultadoLicenciasBean guardarOActualizarSupNoComputablesPlanta(LcSupNoComputablePlantaDto supNoComputablePlantaDto);

	ResultadoLicenciasBean validarSupNoComputablePlantaDto(LcSupNoComputablePlantaDto supNoComputablePlantaDto);
}
