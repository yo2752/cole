package org.gestoresmadrid.oegamImpresion.solicitudes.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;

public interface ServicioSolicitudesDPC extends Serializable {

	ResultadoImpresionBean imprimirSolicitudDPC(List<BigDecimal> listaExpediente);
}
