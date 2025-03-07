package org.gestoresmadrid.oegam2comun.estadisticas.impresion.service;

import java.util.Date;

import escrituras.beans.ResultBean;

public interface ServicioEstadisticaImpresion {

	ResultBean generarExcel(Date fechaInicio, Date fechaFin, String jefatura, String tipoDocumento);

}