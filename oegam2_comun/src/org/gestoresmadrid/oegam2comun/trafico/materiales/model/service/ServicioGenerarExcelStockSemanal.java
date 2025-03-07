package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionSemanalBean;

public interface ServicioGenerarExcelStockSemanal {
	static final String EXCEL_TYPE = "XLSX";

	ByteArrayInputStream generarExcelWithData(FacturacionSemanalBean servicioFacturacionSemanalBean,
			String fileName, Date fecInforme);
	
}
