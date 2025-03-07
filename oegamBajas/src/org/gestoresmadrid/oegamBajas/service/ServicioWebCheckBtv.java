package org.gestoresmadrid.oegamBajas.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;

public interface ServicioWebCheckBtv extends Serializable{

	ResultadoBajasBean procesarCheckBtv(ColaDto solicitud);

	void cambiarEstadoCheckBtv(ColaDto cola, BigDecimal estadoNuevo, String respuesta, BigDecimal idUsuario);

}
