package org.gestoresmadrid.oegam2comun.correo.model.service;

import java.io.IOException;
import java.io.Serializable;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioCorreo extends Serializable{
	
	ResultBean enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;
	
	ResultBean enviarNotificacion(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;

	ResultBean enviarCorreoDuplicados(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;
	
	ResultBean enviarCorreoBajas(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;

	ResultBean enviarCorreoNotificacionesEni(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;

	ResultBean enviarCorreoInciJptm(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException;

}
