package org.gestoresmadrid.oegam2comun.estadisticas.service;

import java.io.Serializable;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioEstadisticasCorreoMate extends Serializable {

	public static final String JEFATURA_PROVINCIAL_MADRID = "M";
	public static final String JEFATURA_PROVINCIAL_ALCORCON = "M1";
	public static final String JEFATURA_PROVINCIAL_ALCALA = "M2";
	public static final String JEFATURA_PROVINCIAL_SEGOVIA = "SG";
	public static final String JEFATURA_PROVINCIAL_AVILA = "AV";
	public static final String JEFATURA_PROVINCIAL_CUENCA = "CU";
	public static final String JEFATURA_PROVINCIAL_CIUDAD_REAL = "CR";
	public static final String JEFATURA_PROVINCIAL_GUADALAJARA = "GU";

	public static final String TOTALES = "TOTALES";
	public static final String TOTAL_DELEGACIONES = "TODASDELEGACIONES";

	public static final String PROVINCIA_AVILA = "05";

	public static final String FICHEROHTML = "FicheroHTML";

	ResultBean enviarMailEstadisticasMate() throws OegamExcepcion, Exception;

	ResultBean enviarMailFirstMate() throws OegamExcepcion, Exception;

	ResultBean enviarMailUltMate() throws OegamExcepcion, Exception;
}