package org.gestoresmadrid.oegam2comun.clonarTramites.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramitesDto;

import escrituras.beans.ResultBean;

public interface ServicioClonarTramites extends Serializable {

	ResultBean comprobarPrevioAClonarTramites(String[] codSeleccionados, String estadoTramiteSeleccionado, String tipoTramiteSeleccionado);

	List<ClonarTramitesDto> crearListaExpedienteClonar(ClonarTramitesDto clonarTramitesDto);

	ResultBean comprobacionDatosClonacion(ClonarTramitesDto clonarTramitesDto);

	ResultBean clonarTramites(ClonarTramitesDto clonarTramitesDto, BigDecimal idUsuario);

	ResultBean seleccionarPestaniasClonar(ClonarTramitesDto clonarTramitesDto);
}
