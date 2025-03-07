package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;


public interface ServicioImpNochePrmDstvFicha extends Serializable{
	
	public static final String SUBJECT_PERMISO = "subject.generar.permiso";
	public static final String SUBJECT_DISTINTIVO = "subject.generar.distintivo";
	public static final String SUBJECT_EITV = "subject.generar.eitv";
	public static final String PERMISO_DSTV_B = "OT11M";
	public static final String PERMISO_DSTV_C = "OT12M";
	public static final String PERMISO_DSTV_MOTOS = "OT15M";
	public static final String PERMISO_FICHAS = "OT13M";
	public static final String PERMISO_PERM = "OT14M";

	ResultadoDistintivoDgtBean imprimirDocumentos();

}
