package org.gestoresmadrid.oegam2comun.circular.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.circular.model.enumerados.OperacionCirculares;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;

public interface ServicioEvolucionCircular extends Serializable {

	ResultadoCircularBean guardarEvolucion(Long idCircular, BigDecimal idUsuario, Date fecha, String estadoAnt,
			String estadoNuevo, OperacionCirculares operacion);
}