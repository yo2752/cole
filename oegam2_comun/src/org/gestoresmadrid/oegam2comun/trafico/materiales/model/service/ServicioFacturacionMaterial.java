package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.FacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.ResultadoFacturacionStockBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionStockDto;

public interface ServicioFacturacionMaterial extends Serializable {
	
	FacturacionStockDto obtenerInformacionColegiado(Long contrato, FacturacionStockDto facturacionStockDto);
	ResultadoFacturacionStockBean generarExcelFacturacion(FacturacionStockBean facturacionStock);
	ResultadoFacturacionStockBean descargarExcelFacturacion(FacturacionStockBean facturacionStock);
}
