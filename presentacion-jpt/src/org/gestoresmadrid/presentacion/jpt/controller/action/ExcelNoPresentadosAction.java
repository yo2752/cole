package org.gestoresmadrid.presentacion.jpt.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import general.acciones.ActionBase;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ExcelNoPresentadosAction extends ActionBase {

	private static final long serialVersionUID = 1L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ExcelNoPresentadosAction.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	private Fecha fechaNoPresentados;
	private String fileName;
	private InputStream inputStream;

	public String inicio() {
		fechaNoPresentados = utilesFecha.getFechaActual();
		return SUCCESS;
	}

	public String recuperarExcel() {
		try {
			String nombreFichero = "NoPresentadosJpt_" + fechaNoPresentados.getAnio() + "_" +  fechaNoPresentados.getMes() + "_" + fechaNoPresentados.getDia();
			File fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.NO_PRESENTADOS_JEFATURA,null,
			fechaNoPresentados, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLS).getFile();
			inputStream = new FileInputStream(fichero);
			setFileName(nombreFichero + ".xls");
		} catch (NullPointerException | FileNotFoundException e) {
			log.error("La recuperación de la excel de expedientes no presentados en JPT ha lanzado la siguiente excepción: ", e);
			addActionError("Fichero no encontrado para la fecha seleccionada");
			return SUCCESS;
		} catch (OegamExcepcion e) {
			log.error("ExcelNoPresentadosAction ha lanzado la siguiente excepción: ", e);
			return SUCCESS;
		}
		return "descargaExcel";
	}

	public Fecha getFechaNoPresentados() {
		return fechaNoPresentados;
	}

	public void setFechaNoPresentados(Fecha fechaNoPresentados) {
		this.fechaNoPresentados = fechaNoPresentados;
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

}