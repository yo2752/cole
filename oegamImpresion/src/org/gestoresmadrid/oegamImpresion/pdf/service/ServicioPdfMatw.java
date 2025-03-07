package org.gestoresmadrid.oegamImpresion.pdf.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioPdfMatw extends Serializable {

	ResultadoImpresionBean imprimirPdf(List<BigDecimal> listaExpediente, boolean borrador);
}
