package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaAltaDto;

public interface ServicioLcDatosPlantaAlta extends Serializable{

	ResultadoLicenciasBean getDatosPlantaAlta(long identificador);

	List<LcDatosPlantaAltaDto> getPlantasAlta(long id);

	ResultadoLicenciasBean guardarOActualizarDatosPlantaAlta(LcDatosPlantaAltaDto datosPlantaAltaDto);

	ResultadoLicenciasBean validarDatosPlantaAlta(LcDatosPlantaAltaDto datosPlantaAltaDto);

	ResultadoLicenciasBean borrarDatosPlantaAlta(long id);


}
