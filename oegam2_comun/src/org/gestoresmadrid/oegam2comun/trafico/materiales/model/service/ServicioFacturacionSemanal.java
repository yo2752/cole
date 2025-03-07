package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import escrituras.beans.ResultBean;

public interface ServicioFacturacionSemanal extends Serializable {
	static final String TIPO_INCIDENCIA_DISTINTIVO = "INDS";
	static final String TIPO_INCIDENCIA_PERMISO    = "INPC";
	static final String TIPO_INCIDENCIA_FICHA      = "INFC";
	
	HashMap<String, HashMap<String, Long>> obtenerFacturacionSemanalStock(String jefaturaProvincial, Long tipoDocumento);
	HashMap<String, HashMap<String, HashMap<String, Long>>> obenerFacturacionSemanalImpresion(String jefaturaProvincial,
			Long tipoDocumento, Date fecDesde, Date fecHasta);
	Boolean[] establecerDocumentosAMostrar(Long tipoDocumento);
	ResultBean executeStockSemanal();
}
