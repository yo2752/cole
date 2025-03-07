package org.gestoresmadrid.oegam2comun.clonarTramites.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramitesDto;

import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioClonarTramitesMatriculacion extends Serializable {

	Boolean comprobarCheckClonacion(ClonarTramitesDto clonarTramitesDto);

	ResultBean clonar(ClonarTramitesDto clonarTramitesDto, BigDecimal idUsuario) throws OegamExcepcion;

	ClonarTramitesDto getPestaniasClonar(ClonarTramitesDto clonarTramitesDto) throws OegamExcepcion;
}
