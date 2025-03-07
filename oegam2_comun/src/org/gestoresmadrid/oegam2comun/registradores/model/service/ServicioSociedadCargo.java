package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.SociedadCargoDto;

import escrituras.beans.ResultBean;

public interface ServicioSociedadCargo extends Serializable {

	SociedadCargoDto getSociedadCargo(String cif, String numColegiado, String nifCargo, String codigoCargo);

	ResultBean guardarSociedadCargo(SociedadCargoDto sociedadCargoDto, BigDecimal numExpediente, String numColegiado, String cifSociedad, String tipoTramite, BigDecimal idUsuario);
}
