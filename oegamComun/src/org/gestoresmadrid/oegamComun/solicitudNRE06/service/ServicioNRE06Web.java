package org.gestoresmadrid.oegamComun.solicitudNRE06.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.SolicitudNRE06Bean;

public interface ServicioNRE06Web extends Serializable{
	ResultadoSolicitudNRE06Bean navegacionWeb(SolicitudNRE06Bean solicitudNRE06Bean, ResultadoSolicitudNRE06Bean resultado);

}
