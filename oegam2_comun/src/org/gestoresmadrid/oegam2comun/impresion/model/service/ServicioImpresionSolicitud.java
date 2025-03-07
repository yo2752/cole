package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import escrituras.beans.ResultBean;

public interface ServicioImpresionSolicitud extends Serializable {

	ResultBean imprimirSolicitudPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario);

}
