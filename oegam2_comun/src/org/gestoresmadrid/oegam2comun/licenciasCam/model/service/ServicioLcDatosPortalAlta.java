package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPortalAltaDto;

public interface ServicioLcDatosPortalAlta extends Serializable {

	ResultadoLicenciasBean guardarOActualizarDatosPortalAlta(LcDatosPortalAltaDto datosPortalAltaDto);

	ResultadoLicenciasBean validarDatosPortalAlta(LcDatosPortalAltaDto datosPortalAltaDto);

	List<LcDatosPortalAltaDto> getPortalesAlta(long id);

	ResultadoLicenciasBean getDatosPortalAlta(long identificador);

	ResultadoLicenciasBean borrarDatosPortalAlta(long id);

}
