package org.gestoresmadrid.oegam2comun.embarcaciones.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.embarcaciones.view.dto.EmbarcacionDto;

import escrituras.beans.ResultBean;

public interface ServicioEmbarcaciones extends Serializable{

	ResultBean generarSolicitudNRE06(EmbarcacionDto embarcacionDto, BigDecimal bigDecimal, String numExpediente);

	ResultBean buscarPersona(String nif, String numColegiado);

	ResultBean generarTxt(EmbarcacionDto embarcacion, BigDecimal idContrato, String numExpediente);
}
