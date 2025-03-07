package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.JustificanteProfResult;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;

public interface ServicioWebserviceJustificanteProfesional extends Serializable {

	/**
	 * Metodo que permite emitir un justificante profesional a partir de los datos facilitados en la solicitud de emisión
	 * @param justificanteProfDto
	 * @param tramiteTrafico
	 * @return
	 */
	JustificanteProfResult emisionJustificante(JustificanteProfDto justificanteProfDto, TramiteTrafDto tramiteTrafico);

	/**
	 * Metodo que permite recuperar un justificante profesional anteriormente emitido con el metodo emisionJustificante a partir del número de identificación único de justificante profesional
	 * @param justificanteProfDto
	 * @return
	 */
	JustificanteProfResult descargarJustificante(JustificanteProfDto justificanteProfDto);

	/**
	 * Verifica, a partir del CSV (Código Seguro de Verificación) impreso en un justificante profesional, que éste fue legítimamente emitido en SEA-JP
	 * @param justificanteProfDto
	 * @return
	 */
	JustificanteProfResult verificarJustificante(JustificanteProfDto justificanteProfDto);

}
