package org.gestoresmadrid.oegamImpresion.declaracion.responsable.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioDeclaracionRespPermInter extends Serializable {

	ResultadoImpresionBean imprimirDeclaracionRespColegiado(List<BigDecimal> listaExpediente);
}
