package org.gestoresmadrid.oegamImpresion.mandatoGenerico.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioMandatoGenerico extends Serializable {

	ResultadoImpresionBean imprimirMandatoGenerico(Long idImpresion);
}
