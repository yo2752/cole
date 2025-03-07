package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionStockDto;

public interface ServicioGenerarExcel extends Serializable {
	ByteArrayInputStream generarExcelFacturacionStock(List<FacturacionStockDto> datosFact, String fileName, Long tipoDocumento);
}
