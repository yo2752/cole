package org.gestoresmadrid.oegamComun.stock.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;

public interface ServicioPersistenciaMaterialStock extends Serializable {

	MaterialStockVO getMaterialPorTipoYJefatura(String tipoDocumento, String jefatura);

	void descontarStock(String jefatura, String tipo, Long cantidadExpediente);
}
