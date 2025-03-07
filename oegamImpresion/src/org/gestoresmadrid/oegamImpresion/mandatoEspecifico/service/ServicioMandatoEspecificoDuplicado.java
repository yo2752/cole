package org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioMandatoEspecificoDuplicado extends Serializable {

	ResultadoImpresionBean imprimirMandatoEspecifico(List<BigDecimal> listaExpedienteo);
}
