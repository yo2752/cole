package org.gestoresmadrid.oegam2.impresion.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.impresion.model.enumerados.EstadosImprimir;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.utiles.BorrarFicherosImpresionThread;
import org.gestoresmadrid.oegamImpresion.view.bean.ImpresionFilterBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaImpresionAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2621888710831123463L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaImpresionAction.class);

	private String nombreFicheroDescargar;

	@Resource
	ModelPagination modeloImpresionPaginated;

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	ImpresionFilterBean impresionFilterBean;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	public String imprimir() {
		try {
			File fichero = servicioImpresion.descargarFichero(nombreFicheroDescargar, utilesColegiado.getIdUsuarioSession());
			if (fichero != null) {
				inputStream = new FileInputStream(fichero);
				fileName = fichero.getName();
				if (fileName != null && fileName.contains(".zip")) {
					return "descargarZIP";
				} else if (fileName != null && fileName.contains(".txt")) {
					borrarFichero(fichero.getAbsolutePath());
					return "descargarTXT";
				} else {
					borrarFichero(fichero.getAbsolutePath());
					return "descargarPDF";
				}
			} else {
				addActionError("No existe el documento");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			addActionError("No existe el documento");
		}
		return buscar();
	}

	private void borrarFichero(String rutaFichero) {
		String tiempo = gestorPropiedades.valorPropertie("tiempo.milisegundos.borrado.documentos");
		if (StringUtils.isNotBlank(tiempo)) {
			BorrarFicherosImpresionThread hiloBorrar = new BorrarFicherosImpresionThread(nombreFicheroDescargar, rutaFichero, Long.valueOf(tiempo));
			hiloBorrar.start();
		} else {
			BorrarFicherosImpresionThread hiloBorrar = new BorrarFicherosImpresionThread(nombreFicheroDescargar, rutaFichero);
			hiloBorrar.start();
		}
		log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			impresionFilterBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImpresionPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return impresionFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.impresionFilterBean = (ImpresionFilterBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		impresionFilterBean = new ImpresionFilterBean();
		if (!utilesColegiado.tienePermisoAdmin()) {
			impresionFilterBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
		impresionFilterBean.setEstado(EstadosImprimir.Generado.getValorEnum());
		impresionFilterBean.setFechaFiltrado(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	public Boolean validarFechas(Object object) {
		Boolean esRangoValido = Boolean.FALSE;
		String valorRangoFechas = gestorPropiedades.valorPropertie("valor.rango.busquedas");
		impresionFilterBean = (ImpresionFilterBean) object;
		if (impresionFilterBean.getFechaFiltrado() != null && !impresionFilterBean.getFechaFiltrado().isfechaNula() && impresionFilterBean.getFechaFiltrado().getFechaInicio() != null
				&& impresionFilterBean.getFechaFiltrado().getFechaFin() != null) {
			if (StringUtils.isNotBlank(valorRangoFechas)) {
				esRangoValido = utilesFecha.comprobarRangoFechas(impresionFilterBean.getFechaFiltrado().getFechaInicio(), impresionFilterBean.getFechaFiltrado().getFechaFin(), Integer.parseInt(
						valorRangoFechas));

			}
		}
		if (!esRangoValido) {
			addActionError("Debe indicar un rango de fechas no mayor a " + valorRangoFechas + " dias para poder obtener los datos de los documentos.");
		}
		return esRangoValido;
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

	public String getNombreFicheroDescargar() {
		return nombreFicheroDescargar;
	}

	public void setNombreFicheroDescargar(String nombreFicheroDescargar) {
		this.nombreFicheroDescargar = nombreFicheroDescargar;
	}

	public ImpresionFilterBean getImpresionFilterBean() {
		return impresionFilterBean;
	}

	public void setImpresionFilterBean(ImpresionFilterBean impresionFilterBean) {
		this.impresionFilterBean = impresionFilterBean;
	}
}
