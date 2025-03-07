package org.gestoresmadrid.oegamImportacion.service;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.oegamImportacion.bean.ImportarTramiteBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;

public interface ServicioImportacion extends Serializable {

	ResultadoImportacionBean importarFichero(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, Long idUsuario, Boolean tienePermisoAdmin, Boolean tienePermisoLiberarEEFF, Boolean esSiga);

	ResultadoImportacionBean importarFicheroPegatinas(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, int numContratosPorColegiado, Long idContratoAdmin);
	
	ResultadoImportacionBean importarDuplicadosDistintivos(File fichero, String nombreFichero, ImportarTramiteBean importarTramiteBean, Long idUsuario, Boolean esGestoria);
}
