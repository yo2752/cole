package org.gestoresmadrid.oegam2.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioPresentacion576;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

import com.opensymphony.xwork2.ActionSupport;

public class Justificante576Action extends ActionSupport {

	private static final long serialVersionUID = -6785294639447556443L;

	private static ILoggerOegam log = LoggerOegam.getLogger(Justificante576Action.class);

	private static final String RESULT_DOWNLOAD_PDF = "peticionPDF";

	private String csv;

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir

	@Autowired
	private ServicioPresentacion576 servicioPresentacion576;

	public String execute() {
		return inicio();
	}

	public String inicio() {
		if (log.isDebugEnabled()) {
			log.debug("inicio Justificante576Action");
		}
		return SUCCESS;
	}

	public String descarga() {
		if (log.isDebugEnabled()) {
			log.debug("descarga Justificante576Action");
		}
		if (csv == null || csv.isEmpty()) {
			addActionError("Introduzca un código seguro de verificación válido.");
		} else {
			byte[] pdf = servicioPresentacion576.descargarPdf(csv);
			if (pdf != null) {
				inputStream = new ByteArrayInputStream(pdf);
				fileName = csv + ".pdf";
				fileSize = pdf.length + "";
				if (log.isDebugEnabled()) {
					log.debug(new String(pdf));
				}
				return RESULT_DOWNLOAD_PDF;
			} else {
				addActionError("No se pudo acceder a la AEAT para descargar el PDF con el justificante de la presentación.");
			}
		}
		return SUCCESS;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
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

	public ServicioPresentacion576 getServicioPresentacion576() {
		return servicioPresentacion576;
	}

	public void setServicioPresentacion576(ServicioPresentacion576 servicioPresentacion576) {
		this.servicioPresentacion576 = servicioPresentacion576;
	}
}
