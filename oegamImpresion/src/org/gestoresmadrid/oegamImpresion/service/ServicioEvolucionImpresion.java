package org.gestoresmadrid.oegamImpresion.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface ServicioEvolucionImpresion extends Serializable {

	void guardarEvolucion(Long idImpresion, String tipoActuacion, String nombreDocumento, List<BigDecimal> listaTramitesErroneos, Long idUsuario);
}
