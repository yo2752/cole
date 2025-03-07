package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.EvolucionTramiteRegistroDto;

public interface ServicioEvolucionTramiteRegistro extends Serializable {

	void guardar(EvolucionTramiteRegistroDto evolucion);

	void guardarEvolucion(BigDecimal idTramiteRegistro, BigDecimal estadoNuevo, BigDecimal estadoAnterior, BigDecimal idUsuario);
}
