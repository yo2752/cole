package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioExportacionTramiteRegistro extends Serializable {
	
	public static final String BYTESXML = "BytesXML";

	public ResultBean exportarEscritura(String[] codSeleccionados) throws Exception, OegamExcepcion;

}
