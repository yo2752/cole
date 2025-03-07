package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import escrituras.beans.ResultBean;

public interface ServicioAvpoAtex5 extends Serializable {

	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";
	public final String COBRAR_CREDITOS = "cobrar.properties.atex5";

	ResultBean avpo(BigDecimal numExpediente, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, int numeroSolicitudes);

}
