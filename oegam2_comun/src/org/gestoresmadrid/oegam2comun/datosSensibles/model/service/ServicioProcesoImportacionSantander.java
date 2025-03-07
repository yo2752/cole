package org.gestoresmadrid.oegam2comun.datosSensibles.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoImportacionSantanderBean;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import utilidades.web.OegamExcepcion;

public interface ServicioProcesoImportacionSantander extends Serializable{

	public static final int MAX_LINEAS_FICHERO_IMPORTAR = 5000;
	
	ResultadoImportacionSantanderBean procesarImportacionSantander();
	
	public List<String> listarFicherosSantander() throws Exception;
	
	public FicheroBean descargar(String path) throws OegamExcepcion;

	void borrar(String nombreYExtension);

}
