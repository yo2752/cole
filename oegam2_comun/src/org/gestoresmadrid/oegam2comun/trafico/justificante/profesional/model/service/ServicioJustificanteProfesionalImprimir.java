package org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;

import es.globaltms.gestorDocumentos.bean.ByteArrayInputStreamBean;

public interface ServicioJustificanteProfesionalImprimir extends Serializable {

	ByteArrayInputStreamBean imprimirJustificantes(Long[] listaIdJustificantesInternos);

	ByteArrayInputStreamBean imprimirJustificantesNumExpedientes(String[] listaNumExpedientes);

	String generarNombre(JustificanteProfVO justificante);

	String generarNombre(BigDecimal numExpediente, BigDecimal numJustificante);

	/**
	 * Guarda el PDF con el justificante profesional obtenido
	 * @param justificanteProfDto
	 * @param numJustificante
	 * @param documento
	 * @return
	 */
	boolean guardarDocumento(JustificanteProfDto justificanteProfDto, String numJustificante, byte[] documento);
	
	boolean guardarDocumento(JustificanteProfVO justificanteProfVO, String numJustificante, byte[] documento);

	File obtenerFileJustificantes(JustificanteProfVO justificante);

	void addZipEntryFromFile(ZipOutputStream zip, File file);
}
