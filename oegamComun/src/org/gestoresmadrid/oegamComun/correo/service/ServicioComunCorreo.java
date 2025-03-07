package org.gestoresmadrid.oegamComun.correo.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public interface ServicioComunCorreo extends Serializable {

	ResultadoBean enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos);

	ResultadoBean enviarNotificacion(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos);
}
