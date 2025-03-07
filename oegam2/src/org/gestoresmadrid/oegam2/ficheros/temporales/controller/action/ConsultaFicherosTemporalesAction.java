package org.gestoresmadrid.oegam2.ficheros.temporales.controller.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.enumerados.EstadoImpresion;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.util.BorrarFicherosTemporalesThread;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.FicherosTemporalesDto;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.ResultadoFicherosTemp;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.views.beans.FicherosTemporalesBean;
import org.gestoresmadrid.oegam2comun.trafico.ficheros.temporales.model.service.ServicioFicherosTemporales;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaFicherosTemporalesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 1346598961369228888L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaFicherosTemporalesAction.class);

	private FicherosTemporalesBean ficherosTemporalesBean;
	private String tipoDoc;
	private String idFichero;
	private Boolean flagDisabled;
	private String textoTitulo;
	private String ficheroBorrado;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	@Resource
	private ModelPagination modeloFicherosTemporalesPaginated;

	@Autowired
	private ServicioFicherosTemporales servicioFicherosTemporales;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String imprimir() {
		ResultadoFicherosTemp resultado = null;
		if (idFichero != null && idFichero != "") {
			FicherosTemporalesDto ficheroTemporalDto = servicioFicherosTemporales.getFicheroTemporalDto(Long.parseLong(idFichero));
			if (ficheroTemporalDto != null) {
				resultado = servicioFicherosTemporales.getFicheroImprimir(ficheroTemporalDto);
				if (!resultado.isError()) {
					if (resultado.getFichero() != null) {
						getResultadoFicheroImprimir(resultado.getFichero());
						if (EstadoImpresion.SIN_DESCARGAR.getValorEnum().equals(ficheroTemporalDto.getEstado())) {
							borrarFichero(ficheroTemporalDto.getIdFicheroTemporal());
							ficheroBorrado = "true";
							resultado = servicioFicherosTemporales.actualizarEstadoFichero(ficheroTemporalDto, EstadoImpresion.DESCARGADO.getValorEnum());
						}
						addActionMessage("El fichero se ha descargado correctamente, pasados 5 minutos se borrara de la plataforma.");
						actualizarPaginatedList();
						return "descargarPDF";
					} else {
						addActionError("Ha sucedio un error a la hora de obtener el fichero");
					}
				} else {
					if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
						for (String mensaje : resultado.getListaMensajes()) {
							addActionError(mensaje);
						}
					} else {
						addActionError(resultado.getMensaje());
					}
				}
			} else {
				addActionError("El fichero seleccionado no existe en la aplicacion.");
			}
		} else {
			addActionError("Debe seleccionar un Fichero para imprimir");
		}
		actualizarPaginatedList();
		return SUCCESS;
	}

	public String consulta(){
		if (idFichero != null && !idFichero.isEmpty()) {
			ficherosTemporalesBean = new FicherosTemporalesBean();
			ficherosTemporalesBean.setIdFicheroTemporal(Long.parseLong(idFichero));
			ficherosTemporalesBean.setTipoFichero(tipoDoc);
			setFlagDisabled(true);
			if (!utilesColegiado.tienePermisoAdmin()) {
				ficherosTemporalesBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				ficherosTemporalesBean.setIdContrato(utilesColegiado.getIdContratoSession());
			}
		}
		return buscar();
	}

	private void borrarFichero(Long idFicheroTemporal) {
		BorrarFicherosTemporalesThread hiloBorrar = new BorrarFicherosTemporalesThread(idFicheroTemporal);
		hiloBorrar.start();
		log.info("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
	}

	private void getResultadoFicheroImprimir(FicheroBean fichero) {
		try {
			inputStream = new FileInputStream(fichero.getFichero());
			fileName = fichero.getNombreDocumento() + fichero.getExtension();
		} catch (FileNotFoundException e) {
			log.error("Ha sucedido un error a la hora de descargar el fichero, error: ",e);
			addActionError("Ha sucedido un error a la hora de descargar el fichero");
		}
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloFicherosTemporalesPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			ficherosTemporalesBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			ficherosTemporalesBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (ficherosTemporalesBean == null) {
			ficherosTemporalesBean = new FicherosTemporalesBean();
		}
		ficherosTemporalesBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
		if (tipoDoc != null) {
			ficherosTemporalesBean.setTipoFichero(tipoDoc);
			setFlagDisabled(true);
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			ficherosTemporalesBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			ficherosTemporalesBean.setIdContrato(utilesColegiado.getIdContratoSession());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return ficherosTemporalesBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.ficherosTemporalesBean = (FicherosTemporalesBean) object;
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorConsultaFicherosTemporales";
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getIdFichero() {
		return idFichero;
	}

	public void setIdFichero(String idFichero) {
		this.idFichero = idFichero;
	}


	public Boolean getFlagDisabled() {
		return flagDisabled;
	}

	public void setFlagDisabled(Boolean flagDisabled) {
		this.flagDisabled = flagDisabled;
	}

	public String getTextoTitulo() {
		return textoTitulo;
	}

	public void setTextoTitulo(String textoTitulo) {
		this.textoTitulo = textoTitulo;
	}

	public ServicioFicherosTemporales getServicioFicherosTemporales() {
		return servicioFicherosTemporales;
	}

	public void setServicioFicherosTemporales(
			ServicioFicherosTemporales servicioFicherosTemporales) {
		this.servicioFicherosTemporales = servicioFicherosTemporales;
	}

	public FicherosTemporalesBean getFicherosTemporalesBean() {
		return ficherosTemporalesBean;
	}

	public void setFicherosTemporalesBean(
			FicherosTemporalesBean ficherosTemporalesBean) {
		this.ficherosTemporalesBean = ficherosTemporalesBean;
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

	public String getFicheroBorrado() {
		return ficheroBorrado;
	}

	public void setFicheroBorrado(String ficheroBorrado) {
		this.ficheroBorrado = ficheroBorrado;
	}
}