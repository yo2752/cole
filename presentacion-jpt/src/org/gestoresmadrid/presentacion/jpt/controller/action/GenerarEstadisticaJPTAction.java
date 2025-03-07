package org.gestoresmadrid.presentacion.jpt.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;

import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEstadisticasJPT;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class GenerarEstadisticaJPTAction extends ActionBase {

	private static final long serialVersionUID = 1549014629076614665L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GenerarEstadisticaJPTAction.class);

	@Autowired
	private ServicioEstadisticasJPT servicioEstadisticasJPT;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private Fecha fechaEstadistica;

	private String jefaturaJpt;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	private static final String DESCARGAR_PDF = "descargarPDF";

	public String inicio() {
		fechaEstadistica = utilesFecha.getFechaActual();
		if (!utilesColegiado.getTipoUsuarioAdminJefaturaJpt()) {
			jefaturaJpt = servicioEstadisticasJPT.getJefaturaProvincialPorUsuario(utilesColegiado.getIdUsuarioSession());
		}
		return SUCCESS;
	}

	public String generar() throws ParseException, Exception, OegamExcepcion {
		ResultBean resultado = servicioEstadisticasJPT.comprobarFechaGeneracionEstadisticas(fechaEstadistica);
		if (resultado == null) {
			byte[] pdfBytes = servicioEstadisticasJPT.generarEstadisticas(fechaEstadistica, jefaturaJpt);
			try {
				if (pdfBytes != null) {
					String fecha = utilesFecha.formatoFecha("ddMMyyyy", fechaEstadistica.getDate());
					String descJefatura = JefaturasJPTEnum.convertirJefatura(jefaturaJpt);
					setFileName("estadisticas_" + descJefatura + "_jpt_" + fecha + ".pdf");
					inputStream = new ByteArrayInputStream(pdfBytes);
					addActionMessage("Fichero de estadísticas generado correctamente");
					return DESCARGAR_PDF;
				} else {
					addActionMessage("No existe fichero de estadística para esa fecha");
				}
			} catch (Exception e) {
				addActionError("Error al generar el fichero de estadísticas");
			}
		} else {
			addActionError(resultado.getListaMensajes().get(0));
		}
		return SUCCESS;
	}

	public ServicioEstadisticasJPT getServicioEstadisticasJPT() {
		return servicioEstadisticasJPT;
	}

	public void setServicioEstadisticasJPT(ServicioEstadisticasJPT servicioEstadisticasJPT) {
		this.servicioEstadisticasJPT = servicioEstadisticasJPT;
	}

	public Fecha getFechaEstadistica() {
		return fechaEstadistica;
	}

	public void setFechaEstadistica(Fecha fechaEstadistica) {
		this.fechaEstadistica = fechaEstadistica;
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

	public String getJefaturaJpt() {
		return jefaturaJpt;
	}

	public void setJefaturaJpt(String jefaturaJpt) {
		this.jefaturaJpt = jefaturaJpt;
	}
}