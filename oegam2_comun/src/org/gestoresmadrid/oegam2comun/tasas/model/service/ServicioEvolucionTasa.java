package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionTasa extends Serializable {

	String DESASIGNAR = "DESASIGNACION";
	String ASIGNAR = "ASIGNACION";
	String CREAR = "CREACION";
	String CAMBIOFORMATO = "CAMBIO DE FORMATO";
	String BLOQUEO = "BLOQUEO";
	String DESBLOQUEO = "DESBLOQUEO";

	public ResultBean insertarEvolucionTasa(TasaVO tasa, String Accion);

	ResultBean insertarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, String accion, String motivo, Date fecha, BigDecimal idUsuario);
}
