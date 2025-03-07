package org.gestoresmadrid.oegamImpresion.borradorPdf417.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioBorradorPdf417 extends Serializable {

	ResultadoImpresionBean imprimirBorradorPdf417(Long idImpresion);
}
