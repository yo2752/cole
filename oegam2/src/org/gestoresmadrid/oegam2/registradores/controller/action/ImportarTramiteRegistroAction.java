package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioImportacionTramiteRegistro;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import escrituras.beans.ResultBeanImportacion;
import general.acciones.ActionBase;
import utilidades.validaciones.ValidadorXml;

public class ImportarTramiteRegistroAction extends ActionBase {

	private static final long serialVersionUID = -2205877147184837234L;

	private static final String RUTA_ESQUEMA_REGISTRO = "ruta.esquema.registro";

	private static Logger log = Logger.getLogger(ImportarTramiteRegistroAction.class);

	private String idContrato;
	private File fichero; // Fichero a importar desde la pagina JSP
	private String ficheroFileName; // nombre del fichero importado
	private String ficheroContentType; // tipo del fichero importado
	private Boolean menorTamMax; // Indica si se llega al action, ya que si se excede el tamaño máximo, el interceptor devuelve directamente input

	@Autowired
	private ServicioImportacionTramiteRegistro servicioImportacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private String tipoContratoRegistro;

	/*
	 * *****************************************************************
	 * Methods
	 * ***************************************************************** 
	 */

	public String importar() {
		log.debug("Start");
		if(comprobarFicheroValido(fichero, ficheroFileName)){
			if(validaXml(fichero)){
				if(idContrato==null || idContrato.trim().isEmpty()){
					idContrato = String.valueOf(utilesColegiado.getIdContratoSession());
				}
				ResultBeanImportacion resultado = servicioImportacion.importar(fichero,idContrato,utilesColegiado.getIdUsuarioSessionBigDecimal(),tipoContratoRegistro);	
				gestionarMensajesResult(resultado);
			}
		} else {
			addActionError("Debe seleccionar un fichero correcto.");
			return SUCCESS;
		}
		log.debug("Return");
		return SUCCESS;
	}

	private boolean validaXml(File fichero) {
		String rutaXsd = gestorPropiedades.valorPropertie(RUTA_ESQUEMA_REGISTRO);
		try {
			ValidadorXml.validarXML(rutaXsd, fichero);
		} catch (IOException e) {
			addActionError("Error al leer el fichero");
			log.error(e.getMessage());
			return false;
		} catch (SAXException e) {
			addActionError("Error de formato xml");
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private void gestionarMensajesResult(ResultBeanImportacion resultado) {
		if(!resultado.getListaMensajes().isEmpty()){
			for(String mensaje : resultado.getListaMensajes()){
				addActionMessage(mensaje);
			}
		}
		if(!resultado.getListaMensajesError().isEmpty()){
			for(String mensaje : resultado.getListaMensajesError()){
				addActionError(mensaje);
			}
		}

	}

	private boolean comprobarFicheroValido(File fichero, String ficheroFileName) {
		boolean esCorrecto = true;
		if (fichero == null) {
			esCorrecto = false;
		} else {
			if (!utiles.esNombreFicheroValido(ficheroFileName)) {
				esCorrecto = false;
			}
			String ext = FilenameUtils.getExtension(ficheroFileName);
			if (!"xml".equals(ext)) {
				esCorrecto = false;
			}
			//El tamaño máximo de fichero es de 20MB
			if (fichero.length() > 20971520) {
				esCorrecto = false;
			}
		}
		return esCorrecto;
	}

	/*
	 * ***********************************************
	 * Getters & setters
	 * ***********************************************
	 */

	public String getTipoContratoRegistro() {
		return tipoContratoRegistro;
	}

	public void setTipoContratoRegistro(String tipoContratoRegistro) {
		this.tipoContratoRegistro = tipoContratoRegistro;
	}

	/**
	 * @return the fichero
	 */
	public File getFichero() {
		return fichero;
	}

	/**
	 * @param fichero the fichero to set
	 */
	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return the ficheroFileName
	 */
	public String getFicheroFileName() {
		return ficheroFileName;
	}

	/**
	 * @param ficheroFileName the ficheroFileName to set
	 */
	public void setFicheroFileName(String ficheroFileName) {
		this.ficheroFileName = ficheroFileName;
	}

	/**
	 * @return the ficheroContentType
	 */
	public String getFicheroContentType() {
		return ficheroContentType;
	}

	/**
	 * @param ficheroContentType the ficheroContentType to set
	 */
	public void setFicheroContentType(String ficheroContentType) {
		this.ficheroContentType = ficheroContentType;
	}

	/**
	 * @return the menorTamMax
	 */
	public Boolean getMenorTamMax() {
		return menorTamMax;
	}

	/**
	 * @param menorTamMax the menorTamMax to set
	 */
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