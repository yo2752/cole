package org.gestoresmadrid.oegamImpresion.pdfTelematico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioPdfTelematicoCtit extends Serializable {

	ResultadoImpresionBean imprimirPdfTelematico(List<BigDecimal> listaExpediente);
}
