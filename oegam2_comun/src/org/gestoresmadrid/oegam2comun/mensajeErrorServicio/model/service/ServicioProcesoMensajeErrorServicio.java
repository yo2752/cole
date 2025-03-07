package org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service;

import java.io.File;

import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;

import escrituras.beans.ResultBean;

/**
 * @author ext_fjcl
 *
 */
public interface ServicioProcesoMensajeErrorServicio {
	
	ResultadoMensajeErrorServicio generarExcel();
	
	ResultBean enviarCorreoFicheroErrorServicio(File ficheroErrorServicio);
	
}
