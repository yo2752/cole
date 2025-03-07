package org.gestoresmadrid.oegamImpresion.pdfTelematico.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioPdfTelematico extends Serializable {

	ResultadoImpresionBean imprimirPdfTelematico(Long idImpresion);
}
