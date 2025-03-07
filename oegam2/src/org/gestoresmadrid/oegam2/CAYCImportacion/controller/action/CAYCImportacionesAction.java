package org.gestoresmadrid.oegam2.CAYCImportacion.controller.action;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.oegam2comun.modeloCAYC.model.service.ServicioModeloCAYC;
import org.gestoresmadrid.oegam2comun.modeloCAYC.view.bean.ResultBeanTotal;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class CAYCImportacionesAction extends ActionBase {

	private static final long serialVersionUID = 6446143564255694912L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(CAYCImportacionesAction.class);

	private BigDecimal numExpediente;
	private String tipoModelo;
	private String idContrato;
	// Importacion desde JSP
	private File fichero;
	private String ficheroFileName;
	private String ficheroContentType;
	private Boolean menorTamMax;
	private String tipoFichero;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String ficheroFileSize;

	// Estas variables estan bien definidas, se podrian unificar todas en un Bean,
	// pero por seguir la filosofia de deja asi
	private Integer guardadosMal;
	private Integer guardadosBien;
	private Integer totalImportados;

	@Autowired
	private ServicioModeloCAYC servicioModeloCAYC;

	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String importar() {
		// Por si se quiere inicializar alguna propiedad

		return SUCCESS;
	}

	public String importar_general(String tipo) {
		String strTipo = "";
		if (fichero == null) {
			addActionError("Debe seleccionar un fichero XML");
			return SUCCESS;
		}
		if (comprobarFicheroValido(fichero, ficheroFileName, tipo)) {
			if (idContrato == null || idContrato.trim().isEmpty()) {
				idContrato = String.valueOf(utilesColegiado.getIdContratoSession());
			}
			ResultBeanTotal resultado = servicioModeloCAYC.importarModeloCAYC(fichero, idContrato,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), tipo);
			if (resultado == null || !resultado.getError()) {

				switch (tipo) {
				case "AA":
					strTipo = " de altas de arrendamientos";
					break;
				case "MA":
					strTipo = " de modificaciones de arrendamientos";
					break;
				case "AC":
					strTipo = " de alta de conductores habituales";
					break;
				case "MC":
					strTipo = " de modificaciones de conductores habituales";
					break;
				}

				this.setGuardadosBien(resultado.getGuardadosBien());
				this.setGuardadosMal(resultado.getGuardadosMal());
				this.setTotalImportados(resultado.getTotalImportados());

				addActionMessage("Importacion correcta del tipo" + strTipo);
			} else {
				log.error("Error al importar el fichero, error:" + resultado.getMensaje());
				gestionarMensajesResult(resultado, "", "");
			}
		} else {
			addActionError("El fichero tiene que ser un xml y tener un nombre válido");
		}

		return SUCCESS;
	}

	private void gestionarMensajesResult(ResultBeanTotal resultado, String mensajeOk, String mensajeOkErrores) {

		if (resultado == null || !resultado.getError()) {
			if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
				addActionMessage(mensajeOkErrores);
				for (String mensaje : resultado.getListaMensajes()) {
					addActionMessage(mensaje);
				}
			} else {
				addActionMessage(mensajeOk);
			}
		} else {
			aniadirMensajeError(resultado);
		}
	}

	protected void aniadirMensajeError(ResultBeanTotal resultado) {
		if (resultado != null && resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
			aniadirListaErrores(resultado.getListaMensajes());
		}
	}

	public String importarAA() {
		return (importar_general("AA"));
	}

	public String importarMA() {
		return (importar_general("MA"));
	}

	public String importarAC() {
		return (importar_general("AC"));
	}

	public String importarMC() {
		return (importar_general("MC"));
	}

	private boolean comprobarFicheroValido(File fichero, String ficheroFileName, String tipo) {
		boolean esCorrecto = true;
		if (fichero == null) {
			esCorrecto = false;
		} else {
			String ext = FilenameUtils.getExtension(ficheroFileName);
			if (!utiles.esNombreFicheroValido(ficheroFileName) || !"xml".equals(ext) 
					|| fichero.length() > 20971520) {
				esCorrecto = false;
			} 
		}
		return esCorrecto;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoModelo() {
		return tipoModelo;
	}

	public void setTipoModelo(String tipoModelo) {
		this.tipoModelo = tipoModelo;
	}

	public String getTipoFichero() {
		return tipoFichero;
	}

	public void setTipoFichero(String tipoFichero) {
		this.tipoFichero = tipoFichero;
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

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getFicheroFileName() {
		return ficheroFileName;
	}

	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}

	public String getFicheroContentType() {
		return ficheroContentType;
	}

	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

	public String getFicheroFileSize() {
		return ficheroFileSize;
	}

	public void setFicheroFileSize(String ficheroFileSize) {
		this.ficheroFileSize = ficheroFileSize;
	}

	public Boolean getMenorTamMax() {
		return menorTamMax;
	}

	public void setMenorTamMax(Boolean menorTamMax) {
		this.menorTamMax = menorTamMax;
	}

	/**
	 * @return the idContrato
	 */
	public String getIdContrato() {
		return idContrato;
	}

	/**
	 * @param idContrato the idContrato to set
	 */
	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public ServicioModeloCAYC getServicioModeloCAYC() {
		return servicioModeloCAYC;
	}

	public void setServicioModeloCAYC(ServicioModeloCAYC servicioModeloCAYC) {
		this.servicioModeloCAYC = servicioModeloCAYC;
	}

	public Integer getGuardadosMal() {
		return guardadosMal;
	}

	public void setGuardadosMal(Integer guardadosMal) {
		this.guardadosMal = guardadosMal;
	}

	public Integer getGuardadosBien() {
		return guardadosBien;
	}

	public void setGuardadosBien(Integer guardadosBien) {
		this.guardadosBien = guardadosBien;
	}

	public Integer getTotalImportados() {
		return totalImportados;
	}

	public void setTotalImportados(Integer totalImportados) {
		this.totalImportados = totalImportados;
	}

}
