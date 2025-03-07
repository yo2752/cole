package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;

import escrituras.beans.ResultBean;

public interface ServicioLcDireccion extends Serializable {

	public static String DIRECCION = "DIRECCION";

	ResultadoLicenciasBean guardarOActualizarDireccion(LcDireccionDto direccionDto);

	ResultBean guardarActualizarPersona(LcDireccionVO direccion, Long idPersona, String nif, String numColegiado);

	void validarDireccion(LcDireccionDto direccion, ResultadoLicenciasBean resultado, String textoValidacion, boolean esInterviniente, boolean validacionRest);

	ResultadoLicenciasBean borrarDireccion(Long id);
}
