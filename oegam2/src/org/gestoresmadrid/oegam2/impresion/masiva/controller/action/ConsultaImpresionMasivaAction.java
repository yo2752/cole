package org.gestoresmadrid.oegam2.impresion.masiva.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.enumerados.EstadoImpresion;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.impresion.masiva.model.service.ServicioImpresionMasiva;
import org.gestoresmadrid.oegam2comun.impresion.masiva.view.beans.ImpresionMasivaBean;
import org.gestoresmadrid.oegam2comun.impresion.masiva.view.dto.ImpresionMasivaDto;
import org.gestoresmadrid.oegam2comun.impresion.util.BorrarFicherosImpresionesMasivasThread;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ConsultaImpresionMasivaAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2621888710831123463L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaImpresionMasivaAction.class);

	private String nombreFicheroDescargar;

	@Resource
	private ModelPagination modeloImpresionMasivaPaginated;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioImpresionMasiva servicioImpresionMasiva;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private ImpresionMasivaBean impresionMasivaBean;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	public String imprimir() throws OegamExcepcion, ParseException {
		ImpresionMasivaDto impresionMasivaDto = servicioImpresionMasiva.getImpresionMasivaPorNombreFichero(nombreFicheroDescargar);

		if (impresionMasivaDto != null) {
			String[] separador = impresionMasivaDto.getNombreFichero().split("\\.");
			File fichero = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPRESION_MASIVA,
					impresionMasivaDto.getTipoDocumento(), impresionMasivaDto.getFechaAltaBBDD(), separador[0],
					ConstantesGestorFicheros.EXTENSION_PDF);

			try {
				if (fichero != null) {
					inputStream = new FileInputStream(fichero);
					fileName = nombreFicheroDescargar;

					if (EstadoImpresion.SIN_DESCARGAR.getValorEnum().equals(impresionMasivaDto.getEstadoImpresion())) {
						borrarFichero(fichero.getAbsolutePath());
					}

					servicioImpresionMasiva.cambiarEstadoImpresion(impresionMasivaDto);

					return "descargarPDF";
				} else
					addActionError("No existe el documento");
			} catch (FileNotFoundException e) {
				log.error(e);
				addActionError("No existe el documento");
			}
		}
		return buscar();
	}

	private void borrarFichero(String rutaFichero) {
		// A los 5 minutos borramos el fichero tanto del GESTOR DOCUMENTOS y de la tabla
		BorrarFicherosImpresionesMasivasThread hiloBorrar = new BorrarFicherosImpresionesMasivasThread(nombreFicheroDescargar, rutaFichero);
		hiloBorrar.start();
		log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin()) {
			if (impresionMasivaBean == null) {
				impresionMasivaBean = new ImpresionMasivaBean();
			}
			impresionMasivaBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloImpresionMasivaPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return impresionMasivaBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.impresionMasivaBean = (ImpresionMasivaBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		impresionMasivaBean = new ImpresionMasivaBean();
		if (!utilesColegiado.tienePermisoAdmin()) {
			impresionMasivaBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
		impresionMasivaBean.setEstadoImpresion(EstadoImpresion.SIN_DESCARGAR.getValorEnum());
		impresionMasivaBean.setFechaFiltradoEnviadoProceso(utilesFecha.getFechaFracionadaActual());
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

	public ServicioImpresionMasiva getServicioImpresionMasiva() {
		return servicioImpresionMasiva;
	}

	public void setServicioImpresionMasiva(ServicioImpresionMasiva servicioImpresionMasiva) {
		this.servicioImpresionMasiva = servicioImpresionMasiva;
	}

	public ImpresionMasivaBean getImpresionMasivaBean() {
		return impresionMasivaBean;
	}

	public void setImpresionMasivaBean(ImpresionMasivaBean impresionMasivaBean) {
		this.impresionMasivaBean = impresionMasivaBean;
	}
}