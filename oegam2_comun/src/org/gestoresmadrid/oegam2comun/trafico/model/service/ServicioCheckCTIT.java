package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.math.BigDecimal;
import java.util.Map;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;

import trafico.utiles.enumerados.TipoTransferencia;
import escrituras.beans.ResultBean;

public interface ServicioCheckCTIT {

	/**
	 * Crea la solicitud del proceso checkCtit
	 * @param numExpediente
	 * @param nombreXml
	 * @param idUsuario
	 * @param idContrato 
	 * @throws TransactionalException
	 * @return
	 */
	ResultBean crearSolicitud(BigDecimal numExpediente, String nombreXml, BigDecimal idUsuario, BigDecimal idContrato);

	/**
	 * 
	 * @param numColegiado
	 * @param numExpediente
	 * @param tipoTransferencia
	 * @param matricula
	 * @param nifAdquiriente
	 * @param nifTransmitente
	 * @param nifPrimerCotitular
	 * @param nifSegundoCotitular
	 * @return
	 */
	Map<String, Object> generarXMLCheckCTIT(String numColegiado, BigDecimal numExpediente, TipoTransferencia tipoTransferencia, String matricula, String nifAdquiriente, String nifTransmitente,
			String nifPrimerCotitular, String nifSegundoCotitular);


}
