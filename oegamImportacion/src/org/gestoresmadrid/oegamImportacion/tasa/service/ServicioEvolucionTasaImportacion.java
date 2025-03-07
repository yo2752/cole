package org.gestoresmadrid.oegamImportacion.tasa.service;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ServicioEvolucionTasaImportacion extends Serializable {

	String DESASIGNAR = "DESASIGNACION";
	String ASIGNAR = "ASIGNACION";
	String CREAR = "CREAR";

	void insertarEvolucionTasa(String codigoTasa, BigDecimal numExpediente, String accion, Long idUsuario);
}
