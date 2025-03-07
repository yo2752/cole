package org.gestoresmadrid.oegam2comun.estadisticas.service;

import java.io.Serializable;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioEstadisticasCorreoCtit extends Serializable {

	public static final String TOTALES = "TOTALES";

	public static final String PROVINCIA_AVILA = "05";

	public static final String FICHEROHTML = "FicheroHTML";

	public static final String NO_TELEMATICAS = "NO TELEMÁTICAS";
	public static final String TELEMATICAS = "TELEMÁTICAS";

	public static final String CADENA_A_SUSTITUIR = "cadena a sustituir";

	ResultBean enviarMailEstadisticasCTIT() throws OegamExcepcion, Exception;

	ResultBean enviarMailEstadisticasCTITDelegaciones() throws OegamExcepcion, Exception;
}