package org.gestoresmadrid.oegam2.atex5.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaConsultaPersonaAtex5Action extends ActionBase {

	private static final long serialVersionUID = 5106061207092277643L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaConsultaPersonaAtex5Action.class);

	private static final String ALTA_CONSULTA_PERSONA_ATEX5 = "altaPersonaAtex5";
	private static final String DESCARGA_PDF = "descargarPdf";

	private BigDecimal numExpediente;
	private ConsultaPersonaAtex5Dto consultaPersonaAtex5;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	@Autowired
	ServicioPersonaAtex5 servicioPersonaAtex5;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String alta() {
		if (numExpediente != null) {
			ResultadoAtex5Bean resultado = servicioPersonaAtex5.getConsultaPersonaAtex5(numExpediente);
			if (!resultado.getError()) {
				consultaPersonaAtex5 = resultado.getConsultaPersonaAtex5Dto();
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			cargarValoresIniciales();
		}
		return ALTA_CONSULTA_PERSONA_ATEX5;
	}

	public String guardar() {
		ResultadoAtex5Bean resultado = servicioPersonaAtex5.guardarConsultaPersonaAtex5(consultaPersonaAtex5,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConsultaPersonaAtex5Dto(resultado, "Consulta Persona Atex5 guardada.");
		}
		return ALTA_CONSULTA_PERSONA_ATEX5;
	}

	public String consultar() {
		ResultadoAtex5Bean resultado = servicioPersonaAtex5.consultarPersonaAtex5(consultaPersonaAtex5,
				utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getConsultaPersonaAtex5Dto(resultado, "Consulta Persona Atex5 generada.");
		}
		return ALTA_CONSULTA_PERSONA_ATEX5;
	}

	public String imprimirPdf() {
		String pagina = "";
		ResultadoAtex5Bean resultado = servicioPersonaAtex5.imprimirPdf(consultaPersonaAtex5.getNumExpediente());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			pagina = ALTA_CONSULTA_PERSONA_ATEX5;
		} else {
			try {
				inputStream = new FileInputStream(resultado.getFicheroDescarga());
				fileName = resultado.getNombreFichero();
				pagina = DESCARGA_PDF;
			} catch (FileNotFoundException e) {
				log.error("No existe el fichero a descargar,error:", e);
				addActionError("No existe el fichero a descargar");
				pagina = ALTA_CONSULTA_PERSONA_ATEX5;
			}
		}
		return pagina;
	}

	private void cargarValoresIniciales() {
		consultaPersonaAtex5 = new ConsultaPersonaAtex5Dto();
		if (!utilesColegiado.tienePermisoAdmin()) {
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			consultaPersonaAtex5.setContrato(contrato);
			consultaPersonaAtex5.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	private void getConsultaPersonaAtex5Dto(ResultadoAtex5Bean resultado, String mensajeOk) {
		numExpediente = resultado.getNumExpediente();
		List<String> listaMensajesAvisos = null;
		if (resultado.getListaErrores() != null && !resultado.getListaErrores().isEmpty()) {
			listaMensajesAvisos = resultado.getListaErrores();
		}
		resultado = servicioPersonaAtex5.getConsultaPersonaAtex5(numExpediente);
		if (!resultado.getError()) {
			consultaPersonaAtex5 = resultado.getConsultaPersonaAtex5Dto();
			if (mensajeOk != null && !mensajeOk.isEmpty()) {
				addActionMessage(mensajeOk);
			}
			aniadirMensajeAviso(null, listaMensajesAvisos);
		} else {
			addActionError(resultado.getMensaje());
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ConsultaPersonaAtex5Dto getConsultaPersonaAtex5() {
		return consultaPersonaAtex5;
	}

	public void setConsultaPersonaAtex5(ConsultaPersonaAtex5Dto consultaPersonaAtex5) {
		this.consultaPersonaAtex5 = consultaPersonaAtex5;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

}