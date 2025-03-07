package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaBajaDto;

public interface ServicioLcDatosPlantaBaja extends Serializable {

	ResultadoLicenciasBean getDatosPlantaBaja(long identificador);

	ResultadoLicenciasBean borrarDatosPlantaBaja(long id);

	ResultadoLicenciasBean validarDatosPlantaBaja(LcDatosPlantaBajaDto datosPlantaBajaDto);

	ResultadoLicenciasBean guardarOActualizarDatosPlantaBaja(LcDatosPlantaBajaDto datosPlantaBajaDto);

	List<LcDatosPlantaBajaDto> getPlantasBaja(long id);

}
