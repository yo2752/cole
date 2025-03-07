package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoValidacionDireccionBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;

public interface ServicioLicBDCRestWS extends Serializable {

	ResultadoValidacionDireccionBean validarDireccionRest(LcDireccionDto direccion);

	ResultadoValidacionDireccionBean validarDireccionCompletaRest(LcDireccionDto direccion);
}
