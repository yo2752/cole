package org.gestoresmadrid.oegamComun.solicitudNRE06.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;

import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;

public interface ServicioSolicitudNRE06 extends Serializable{

	ResultadoSolicitudNRE06Bean solicitarNRE06(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoSolicitudNRE06Bean generarResumen() throws ParseException;


}
