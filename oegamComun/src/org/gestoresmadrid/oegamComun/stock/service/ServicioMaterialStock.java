package org.gestoresmadrid.oegamComun.stock.service;

import java.io.Serializable;

public interface ServicioMaterialStock extends Serializable {

	Boolean existeStockMaterial(String tipoDocumento, String jefatura);

	void descontarStock(String jefatura, String tipo, Long cantidadExpediente);
}
