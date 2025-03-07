package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import escrituras.beans.ResultBean;

public interface ServicioImpresionDuplicados extends Serializable {

	ResultBean imprimirDuplicadosPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario);

	ResultBean imprimirListadoMatriculas(String[] numExpedientes, BigDecimal idUsuario);
}
