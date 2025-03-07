package org.gestoresmadrid.oegamImpresion.pdf417.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioPdf417Baja extends Serializable {

	ResultadoImpresionBean imprimirPdf417(List<BigDecimal> listaExpediente, boolean borrador);
}
