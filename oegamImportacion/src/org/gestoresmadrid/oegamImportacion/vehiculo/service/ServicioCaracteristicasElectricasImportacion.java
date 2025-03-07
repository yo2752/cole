package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ServicioCaracteristicasElectricasImportacion extends Serializable {

	int numeroCoincidentes(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor, String categoriaElectrica, BigDecimal consumo,
			BigDecimal autonomiaElectrica);
}
