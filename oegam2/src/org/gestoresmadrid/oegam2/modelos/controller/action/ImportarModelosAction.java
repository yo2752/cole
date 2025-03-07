package org.gestoresmadrid.oegam2.modelos.controller.action;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.io.FilenameUtils;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.modelo600_601.model.service.ServicioModelo600_601;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.Provincias;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ImportarModelosAction extends ActionBase {

	private static final long serialVersionUID = 6446143564255694912L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImportarModelosAction.class);

	private Modelo600_601Dto modeloDto;
	private BigDecimal numExpediente;
	private String tipoModelo;
	private String idContrato;

	// Importación desde JSP
	private File fichero; // Fichero a importar desde la página JSP
	private String ficheroFileName; // Nombre del fichero importado
	private String ficheroContentType; // Tipo del fichero importado
	private Boolean menorTamMax; // Indica si se llega al action, ya que si se excede el tamaño máximo, el interceptor devuelve directamente input
	private String tipoFichero; // {600, 601}

	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String ficheroFileSize;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioModelo600_601 servicioModelo600_601;

	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String importarModelo600() {
		if (comprobarFicheroValido(fichero, ficheroFileName)) {
			if (idContrato==null || idContrato.trim().isEmpty()) {
				idContrato = String.valueOf(utilesColegiado.getIdContratoSession());
			}
			ResultBean resultado = servicioModelo600_601.importarModelo600(fichero,idContrato,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado == null || !resultado.getError()) {
				addActionMessage("El/Los modelos se han importado correctamente.");
			} else {
				log.error("Error al importar el fichero, error:" + resultado.getMensaje());
				gestionarMensajesResult(resultado, "", "");
			}
		} else {
			addActionError("El fichero tiene que ser un XML");
		}
		return SUCCESS;
	}

	public String importarModelo601() {
		if (comprobarFicheroValido(fichero, ficheroFileName)) {
			if (idContrato == null || idContrato.trim().isEmpty()) {
				idContrato = String.valueOf(utilesColegiado.getIdContratoSessionBigDecimal());
			}
			ResultBean resultado = servicioModelo600_601.importarModelo601(fichero, idContrato, utilesColegiado.getIdUsuarioSessionBigDecimal());	
			if (resultado == null || !resultado.getError()) {
				addActionMessage("El/Los modelos se han importado correctamente.");
			} else {
				log.error("Error al importar el fichero, error:" + resultado.getMensaje());
				gestionarMensajesResult(resultado, "", "");
			}
		} else {
			addActionError("El fichero tiene que ser un XML");
		}
		return SUCCESS;
	}

	private void gestionarMensajesResult(ResultBean resultBean, String mensajeOk, String mensajeOkErrores) {
		if (resultBean == null || !resultBean.getError()) {
			if (resultBean.getListaMensajes() != null && !resultBean.getListaMensajes().isEmpty()) {
				addActionMessage(mensajeOkErrores);
				for (String mensaje : resultBean.getListaMensajes()) {
					addActionMessage(mensaje);
				}
			} else {
				addActionMessage(mensajeOk);
			}
		} else {
			aniadirMensajeError(resultBean);
		}
	}

	private boolean comprobarFicheroValido(File fichero, String ficheroFileName) {
		boolean esCorrecto = true;
		String ext = FilenameUtils.getExtension(ficheroFileName);
		if (fichero == null || !utiles.esNombreFicheroValido(ficheroFileName)
				|| !"xml".equals(ext) || fichero.length() > 20971520) {
			esCorrecto = false;
		}
		return esCorrecto;
	}

	private void cargarValoresIniciales() {
		if (modeloDto == null) {
			modeloDto = new Modelo600_601Dto();
		}
		// Se carga directo Madrid
		OficinaLiquidadoraDto oficinaLiquidadora = new OficinaLiquidadoraDto();
		oficinaLiquidadora.setIdProvincia(String.valueOf(Provincias.Madrid.getValorEnum()));
		modeloDto.setOficinaLiquidadora(oficinaLiquidadora);
		modeloDto.setModelo(servicioModelo600_601.getModelo(Modelo.convertirTexto(tipoModelo)));
		modeloDto.setPresentador(servicioModelo600_601.getPresentadorContrato(servicioContrato.getContratoDto(utilesColegiado.getIdContratoSessionBigDecimal())));
		modeloDto.setNumColegiado(utilesColegiado.getNumColegiadoCabecera());
		modeloDto.setContrato(servicioContrato.getContratoDto(utilesColegiado.getIdContratoSessionBigDecimal()));
	}

	public Modelo600_601Dto getModeloDto() {
		return modeloDto;
	}

	public void setModeloDto(Modelo600_601Dto modeloDto) {
		this.modeloDto = modeloDto;
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

}