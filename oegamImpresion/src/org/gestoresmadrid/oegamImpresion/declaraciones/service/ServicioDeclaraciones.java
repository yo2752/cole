package org.gestoresmadrid.oegamImpresion.declaraciones.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioDeclaraciones extends Serializable {

	ResultadoImpresionBean imprimirDeclaracion(Long idImpresion);
}
