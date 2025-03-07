package org.gestoresmadrid.oegam2comun.procesos.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;

public interface ServicioWebServiceCheckCtit extends Serializable {

	public static final String NOTIFICACION = "PROCESO CHECK CTIT FINALIZADO";

	ResultadoCtitBean tramitarPeticion(ColaDto solicitud);

	ResultadoCtitBean actualizarTramiteProceso(BigDecimal numExpediente, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario, String respuesta);
}
