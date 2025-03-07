package org.gestoresmadrid.oegamImpresion.pdf417.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioPdf417 extends Serializable {

	ResultadoImpresionBean imprimirPdf417(Long idImpresion);
}
