package org.gestoresmadrid.oegam2comun.trafico.liquidacion620.model.service;

import java.io.Serializable;
import java.util.Date;

import escrituras.beans.ResultBean;

public interface ServicioLiquidacion620 extends Serializable {

	public static final String PROPERTIE_CORREO_ASUNTO = "Proceso EXCEL Liquidacion 620";
	public static final String PROPERTIE_CORREO_PARA = "transmision.620.correo.para";
	public static final String PROPERTIE_CORREO_COPIA = "transmision.620.correo.copia";

	ResultBean getExcelLiquidacion620Hoy();

	ResultBean getExcelLiquidacion620DiaAnterior();

	ResultBean getExcelLiquidacion620PorFecha(Date fechaIni, Date fechaFin, String numColegiado);
}
