package org.gestoresmadrid.oegam2comun.impresion.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.oegam2comun.impresion.view.dto.ImprimirTramiteTraficoDto;

import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioImpresionProceso extends Serializable {

	ResultBean procesar(ColaBean colaBean, String tipoImpreso, String tipoTramite, String numColegiado, String[] numExpedientes) throws OegamExcepcion;

	File buscarFichero(ColaBean colaBean) throws OegamExcepcion;

	BigDecimal[] convertirNumExp(String[] numExpedientes);

	ResultBean leerFichero(File fichero) throws Exception;

	void borradoRecursivoFichero(File fichero);

	ResultBean devolverCreditos(String tipoTramite, String tipoImpreso, BigDecimal[] numerosExpedientes, BigDecimal idContrato, BigDecimal idUsuario);

	void enviarCorreo(String nombreFichero, ResultBean resultado, ColaBean colaBean, String[] numExpedientes, String tipoImpreso, String tipoTramite, String numColegiado);

	ResultBean tratarPeticionPorProceso(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, ImprimirTramiteTraficoDto imprimirTramiteTrafico, BigDecimal idContrato,
			BigDecimal idUsuario, String numColegiado) throws OegamExcepcion;
}
