package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioImpresionBaja extends Serializable {

	ResultBean imprimirBajaPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario) throws Throwable;

	ResultBean impresionListadoBastidoresBaja(String[] numExpedientes, BigDecimal idUsuario) throws OegamExcepcion, Throwable;

	ResultBean imprimirBaja(String[] numExpedientes, BigDecimal idUsuario, TipoImpreso tipoImp, Boolean esProceso);
}
