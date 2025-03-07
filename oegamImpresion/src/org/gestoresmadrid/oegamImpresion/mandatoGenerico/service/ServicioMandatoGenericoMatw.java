package org.gestoresmadrid.oegamImpresion.mandatoGenerico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioMandatoGenericoMatw extends Serializable {

	ResultadoImpresionBean imprimirMandatoGenerico(List<BigDecimal> listaExpediente);
}
