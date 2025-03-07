package org.gestoresmadrid.oegam2.legalizaciones.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;

import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioLegalizacionesCita;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.LegalizacionCitasFilterBean;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.ResultadoLegalizacionesBean;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ConsultaLegalizacionAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -8518866369020619052L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaLegalizacionAction.class);

	private static final int ESTADO_FINALIZADO = 4;

	private static final String[] fetchList = { "contrato", "contrato.provincia" };

	private String TipoPermisos;
	private LegalizacionCitaDto legDto;
	private String idPeticion;

	private LegalizacionCitasFilterBean legalizacionCitasFilterBean;

	@Resource
	ModelPagination modeloLegalizacionesPaginated;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private String varFicheroAdjunto;
	private String varSolicitud;
	private String varEstadoPeticion;
	private String cambioEstado;
	private String idPeticiones;

	private String numColegiado;

	private Fecha fechaListado = new Fecha();

	@Autowired
	private ServicioLegalizacionesCita servicioLegalizaciones;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private Utiles utiles;

	public String solicitarDocumentacion() {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		try {
			List<LegalizacionCitaVO> listaLegBean = servicioLegalizaciones.obtenerSeleccionados(getIdPeticion().split("-"));
			resultado = servicioLegalizaciones.solDocumentaciones(listaLegBean);
			setActionErrors(resultado.getListaMensajesError());
			setActionMessages(resultado.getListaMensajesAvisos());
		} catch (Exception e) {
			log.error("Se ha producido un error al solicitar documentación de Legalización: ", e);
			addActionError(e.getMessage());
		}
		return actualizarPaginatedList();
	}

	public String borrar() {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		try {
			resultado = servicioLegalizaciones.borrarPeticiones(getIdPeticion(), utilesColegiado.tienePermisoAdmin());
			setActionErrors(resultado.getListaMensajesError());
			setActionMessages(resultado.getListaMensajesAvisos());
		} catch (Exception e) {
			log.error("Se ha producido un error no esperado al borrar las peticiones del colegiado: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError(e.getMessage());
		}
		return super.inicio();
	}

	public String descargar() {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA)) {
			addActionError("No tiene permiso para modificar una petición.");
			return SUCCESS;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			legDto = servicioLegalizaciones.getPeticion(legDto);
			List<File> listaFicheros = servicioLegalizaciones.getDocumentacion(legDto);

			ZipOutputStream zip = new ZipOutputStream(baos);
			for (File file : listaFicheros) {
				FileInputStream is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				is.close();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				}
			}
			zip.close();

			ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
			setInputStream(stream);
			setFileName(legDto.getNombre().replace(' ', '_') + ConstantesGestorFicheros.EXTENSION_ZIP);
			log.debug("Enviando el ZIP");

		} catch (ParseException | OegamExcepcion | IOException e) {
			addActionError("Se ha producido un error al descargar el fichero. Por favor contacte con el colegio");
			log.error("Se ha producido un error al descargar el fichero: ", e);
		}

		return "descargarDocumentos";
	}

	public String cambiarEstado() {
		if (utilesColegiado.tienePermisoAdmin()) {
			ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
			try {
				String[] idsPeticion = idPeticiones.split(",");
				List<LegalizacionCitaVO> listaLegBean = servicioLegalizaciones.obtenerSeleccionados(idsPeticion);
				resultado = servicioLegalizaciones.cambiarEstados(listaLegBean, cambioEstado);
				setActionErrors(resultado.getListaMensajesError());
				setActionMessages(resultado.getListaMensajesAvisos());
			} catch (Exception e) {
				log.error("Se ha producido un error al cambiar estado en los trámites de Legalización: ", e);
				addActionError(e.getMessage());
			}
		}

		return actualizarPaginatedList();
	}

	/*
	 * Método que limitará la fecha hasta al día anterior al actual independientemente de la fecha que ponga el usuario. Esto se usará cuando el usuario sea del ministerio.
	 */
	private void limitaFechaMinisterio() {
		try {
			Fecha fechaPantalla = new Fecha();
			Fecha fechaAyer = utilesFecha.getDiaMesAnterior();

			// Primero comprobar que la fecha de fin no es nulo.
			if (legalizacionCitasFilterBean.getFechaLegalizacion().getFechaFin() != null) {
				fechaPantalla.setDia(legalizacionCitasFilterBean.getFechaLegalizacion().getDiaFin());
				fechaPantalla.setMes(legalizacionCitasFilterBean.getFechaLegalizacion().getMesFin());
				fechaPantalla.setAnio(legalizacionCitasFilterBean.getFechaLegalizacion().getAnioFin());
				if (utilesFecha.compararFechaMayor(fechaPantalla, fechaAyer) == 1) {
					legalizacionCitasFilterBean.getFechaLegalizacion().setDiaFin(fechaAyer.getDia());
					legalizacionCitasFilterBean.getFechaLegalizacion().setMesFin(fechaAyer.getMes());
					legalizacionCitasFilterBean.getFechaLegalizacion().setAnioFin(fechaAyer.getAnio());
				}
			} else {
				legalizacionCitasFilterBean.getFechaLegalizacion().setDiaFin(fechaAyer.getDia());
				legalizacionCitasFilterBean.getFechaLegalizacion().setMesFin(fechaAyer.getMes());
				legalizacionCitasFilterBean.getFechaLegalizacion().setAnioFin(fechaAyer.getAnio());
			}
			if (legalizacionCitasFilterBean.getFechaLegalizacion().getFechaInicio() != null) {
				fechaPantalla.setDia(legalizacionCitasFilterBean.getFechaLegalizacion().getDiaInicio());
				fechaPantalla.setMes(legalizacionCitasFilterBean.getFechaLegalizacion().getMesInicio());
				fechaPantalla.setAnio(legalizacionCitasFilterBean.getFechaLegalizacion().getAnioInicio());
				if (utilesFecha.compararFechaMayor(fechaPantalla, fechaAyer) == 1) {
					legalizacionCitasFilterBean.getFechaLegalizacion().setDiaInicio(fechaAyer.getDia());
					legalizacionCitasFilterBean.getFechaLegalizacion().setMesInicio(fechaAyer.getMes());
					legalizacionCitasFilterBean.getFechaLegalizacion().setAnioInicio(fechaAyer.getAnio());
				}
			}

		} catch (Throwable e) {
			log.error("Se ha producido un error al limitar la fecha de legalización para el usuario ministerio", e);
		}
	}

	public String confirmarLegalizacion() {

		ResultadoLegalizacionesBean resultado = null;
		try {
			List<LegalizacionCitaVO> listaLegBean = servicioLegalizaciones.obtenerSeleccionados(getIdPeticion().split("-"));
			resultado = servicioLegalizaciones.solLegalizaciones(listaLegBean, legDto.getFechaLegalizacion(), utilesColegiado.tienePermisoAdmin());
			setActionErrors(resultado.getListaMensajesError());
			setActionMessages(resultado.getListaMensajesAvisos());
		} catch (Exception e) {
			log.error("Se ha producido un error no controlado al confirmar legalizaciones: " + utilesColegiado.getNumColegiadoSession(), e);
			addActionError(e.getMessage());
		}

		return actualizarPaginatedList();
	}

	public String getTipoPermisos() {
		return TipoPermisos;
	}

	public void setTipoPermisos(String tipoPermisos) {
		TipoPermisos = tipoPermisos;
	}

	public LegalizacionCitaDto getLegDto() {
		return legDto;
	}

	public void setLegDto(LegalizacionCitaDto legDto) {
		this.legDto = legDto;
	}

	public String getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
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

	public String getVarFicheroAdjunto() {
		return varFicheroAdjunto;
	}

	public void setVarFicheroAdjunto(String varFicheroAdjunto) {
		this.varFicheroAdjunto = varFicheroAdjunto;
	}

	public String getVarSolicitud() {
		return varSolicitud;
	}

	public void setVarSolicitud(String varSolicitud) {
		this.varSolicitud = varSolicitud;
	}

	public String getCambioEstado() {
		return cambioEstado;
	}

	public void setCambioEstado(String cambioEstado) {
		this.cambioEstado = cambioEstado;
	}

	public String getIdPeticiones() {
		return idPeticiones;
	}

	public void setIdPeticiones(String idPeticiones) {
		this.idPeticiones = idPeticiones;
	}

	public String getVarEstadoPeticion() {
		return varEstadoPeticion;
	}

	public void setVarEstadoPeticion(String varEstadoPeticion) {
		this.varEstadoPeticion = varEstadoPeticion;
	}

	public Fecha getFechaListado() {
		return fechaListado;
	}

	public void setFechaListado(Fecha fechaListado) {
		this.fechaListado = fechaListado;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	@Override
	public String getResultadoPorDefecto() {
		if (utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		} else {
			return SUCCESS;
		}
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoMinisterio()) {
			/* Es un colegiado */
			legalizacionCitasFilterBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		} else if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoMinisterio()) {
			/* Es el ministerio */
			legalizacionCitasFilterBean.setEstadoPeticion(ESTADO_FINALIZADO);
			limitaFechaMinisterio();
		}

		if (!utilesColegiado.tienePermisoAdmin()) {
			legalizacionCitasFilterBean.setEstado(1);
		}
	}

	@Override
	public String getDecorator() {
		return "org.gestoresmadrid.oegam2.view.decorator.DecoratorLegalizacion";
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloLegalizacionesPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargarFiltroInicial() {
		if (this.legalizacionCitasFilterBean == null) {
			this.legalizacionCitasFilterBean = new LegalizacionCitasFilterBean();
		}
		this.legalizacionCitasFilterBean.setFechaLegalizacion(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	@Override
	protected Object getBeanCriterios() {
		return legalizacionCitasFilterBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.legalizacionCitasFilterBean = (LegalizacionCitasFilterBean) object;
	}

	public LegalizacionCitasFilterBean getLegalizacionCitasFilterBean() {
		return legalizacionCitasFilterBean;
	}

	public void setLegalizacionCitasFilterBean(LegalizacionCitasFilterBean legalizacionCitasFilterBean) {
		this.legalizacionCitasFilterBean = legalizacionCitasFilterBean;
	}

}