package org.gestoresmadrid.oegam2.sanciones.controller.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamSanciones.service.ServicioPersistenciaSanciones;
import org.gestoresmadrid.oegamSanciones.service.ServicioSanciones;
import org.gestoresmadrid.oegamSanciones.service.ServicioValidacionSanciones;
import org.gestoresmadrid.oegamSanciones.view.beans.ResultadoSancionesBean;
import org.gestoresmadrid.oegamSanciones.view.beans.SancionesFilterBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaSancionesAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 5872988807640449424L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaSancionesAction.class);

	@Resource
	ModelPagination modeloSancionesPaginated;

	@Autowired
	ServicioSanciones servicioSanciones;

	@Autowired
	ServicioPersistenciaSanciones servicioPersistencia;

	@Autowired
	ServicioValidacionSanciones servicioValidacion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	SancionesFilterBean sancionesFilterBean;
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String cambioEstado;
	private String idSanciones;
	private String idSancion;
	
	@Autowired
	private Utiles utiles;
	
	@Override
	protected String getResultadoPorDefecto() {
		if(utiles.esUsuarioOegam3(utilesColegiado.getNumColegiadoSession())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloSancionesPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {

		if (!utilesColegiado.tienePermisoAdmin()) {
			sancionesFilterBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());

			// sanciones que no estén en baja lógica
			sancionesFilterBean.setEstado(1);
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (sancionesFilterBean == null) {
			sancionesFilterBean = new SancionesFilterBean();
		}
		sancionesFilterBean.setFechaPresentacion(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return sancionesFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		sancionesFilterBean = (SancionesFilterBean) object;
	}

	@Autowired
	UtilesFecha utilesFecha;

	public String cambiarEstado() {
		if (utilesColegiado.tienePermisoAdmin()) {
			String[] idsSacion = idSanciones.split(",");
			ResultadoSancionesBean resultadoCambioEstados = servicioSanciones.cambiarEstados(idsSacion, cambioEstado);
			super.setActionErrors(resultadoCambioEstados.getListaMensajesError());
			super.setActionMessages(resultadoCambioEstados.getListaMensajesAvisos());
		}

		return super.navegar();
	}

	public String borrar() {
		String[] codSeleccionados = getIdSancion().split("-");

		ResultadoSancionesBean resultado = servicioSanciones.borrarSancion(codSeleccionados);
		super.setActionErrors(resultado.getListaMensajesError());
		super.setActionMessages(resultado.getListaMensajesAvisos());

		return super.navegar();
	}

	public String listado() {
		try {
			ResultadoSancionesBean resultado = servicioSanciones.generarInforme(getIdSancion().split("-"));
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
				return super.navegar();
			}
			ByteArrayInputStream stream = new ByteArrayInputStream(resultado.getByteFinal());
			setInputStream(stream);
			setFileName("InformeSanciones_" + resultado.getFechaPresentacion().toString() + ConstantesGestorFicheros.EXTENSION_PDF);
		} catch (Exception e) {
			addActionError("Se ha producido un error al intentar generar el listado de sanciones");
			log.error("Se ha producido un error al intentar generar el listado de sanciones: ", e);
			return super.navegar();
		}

		return "descargarPDF";
	}

	public SancionesFilterBean getSancionesFilterBean() {
		return sancionesFilterBean;
	}

	public void setSancionesFilterBean(SancionesFilterBean sancionesFilterBean) {
		this.sancionesFilterBean = sancionesFilterBean;
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

	public String getCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(String cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public String getIdSanciones() {
		return idSanciones;
	}

	public void setIdSanciones(String idSanciones) {
		this.idSanciones = idSanciones;
	}

	public String getIdSancion() {
		return idSancion;
	}

	public void setIdSancion(String idSancion) {
		this.idSancion = idSancion;
	}

}
