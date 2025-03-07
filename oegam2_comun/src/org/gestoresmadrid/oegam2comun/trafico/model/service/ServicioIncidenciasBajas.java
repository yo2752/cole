package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoIncidenciasBajasBean;

public interface ServicioIncidenciasBajas extends Serializable {

	public static final String INCD_BTV = "IncidenciasBajasBTV";
	public static final Integer COL_DESCRIPCION = 2;
	public static final String NGESTORIA = " N� Gestor�a ";
	public static final Integer COL_NGESTORIA = 0;
	public static final String NMATRICULA = " Matr�cula ";
	public static final Integer COL_NMATRICULA = 1;

	ResultadoIncidenciasBajasBean generarExcelIncidencias(JefaturasJPTEnum jefatura);

	String generarExcelIncidenciasPorJefatura();

}
