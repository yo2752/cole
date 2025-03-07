package org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioMandatoEspecifico extends Serializable {

	ResultadoImpresionBean imprimirMandatoEspecifico(Long idImpresion);
}
