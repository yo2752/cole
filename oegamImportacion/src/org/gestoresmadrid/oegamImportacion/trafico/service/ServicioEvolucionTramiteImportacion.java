package org.gestoresmadrid.oegamImportacion.trafico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public interface ServicioEvolucionTramiteImportacion extends Serializable{

	void guardarEvolucion(BigDecimal numExpediente, BigDecimal estado, Long idUsuario, Date fechaAlta);

}
