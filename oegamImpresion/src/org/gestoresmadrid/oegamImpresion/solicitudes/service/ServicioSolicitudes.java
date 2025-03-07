package org.gestoresmadrid.oegamImpresion.solicitudes.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioSolicitudes extends Serializable {

	ResultadoImpresionBean imprimirSolicitud(Long idImpresion);
}
