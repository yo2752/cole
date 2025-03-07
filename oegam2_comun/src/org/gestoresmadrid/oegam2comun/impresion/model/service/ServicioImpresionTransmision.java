package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;

import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.enumerados.TipoImpreso;
import escrituras.beans.ResultBean;

public interface ServicioImpresionTransmision extends Serializable {

	ResultBean imprimirTransmisionesPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, boolean esTelematica, Boolean tienePermisoImpresionPdf417);

	ResultBean imprimirListadoBastidores(String[] numsExpedientes,String numColegiado, BigDecimal idContrato, TipoTramiteTrafico tipoTramite);

	ResultBean imprimirTransmision(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite, TipoImpreso tipoImpreso, Boolean esProceso);

	ResultBean imprimirEnProcesoListaBastidores(String[] numsExpedientes);

}
