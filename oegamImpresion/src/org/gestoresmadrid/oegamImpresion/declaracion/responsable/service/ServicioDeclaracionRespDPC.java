package org.gestoresmadrid.oegamImpresion.declaracion.responsable.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioDeclaracionRespDPC extends Serializable {

	ResultadoImpresionBean imprimirDeclaracionRespColegiado(List<BigDecimal> listaExpediente);
}
