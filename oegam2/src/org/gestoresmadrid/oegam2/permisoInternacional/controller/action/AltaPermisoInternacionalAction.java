package org.gestoresmadrid.oegam2.permisoInternacional.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamImpresion.utiles.BorrarFicherosImpresionThread;
import org.gestoresmadrid.oegamInterga.service.ServicioInterga;
import org.gestoresmadrid.oegamPermisoInternacional.service.ServicioPermisoInternacional;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.ResultadoPermInterBean;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaPermisoInternacionalAction extends ActionBase {

	private static final long serialVersionUID = 3775284251922376297L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaPermisoInternacionalAction.class);

	private static final String ALTA_PERMISO_INTERNACIONAL = "altaPermisoInternacional";
	private static final String IMPRIMIR_P_INTER = "imprimirPInternacional";
	private static final String DESCARGAR_MANDATO = "descargarMandato";

	PermisoInternacionalDto permisoInternacionalDto;

	String idPermisoInternacional;
	InputStream inputStream;
	String fileName;
	File fichero;
	String ficheroFileName;
	String ficheroContentType;
	String ficheroFileSize;
	BigDecimal numExpediente;

	boolean cambioDomicilio;

	@Autowired
	ServicioPermisoInternacional servicioPermisoInternacional;

	@Autowired
	ServicioInterga servicioInterga;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		permisoInternacionalDto = new PermisoInternacionalDto();
		permisoInternacionalDto.setIdContrato(utilesColegiado.getIdContratoSession());
		permisoInternacionalDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		permisoInternacionalDto.setJefatura(utilesColegiado.getIdJefaturaSesion());
		cambioDomicilio = false;
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String detalle() {
		if (numExpediente != null) {
			ResultadoPermInterBean resultado = servicioPermisoInternacional.getPermisoInternPorNumExpediente(numExpediente);
			if (!resultado.getError()) {
				permisoInternacionalDto = resultado.getPermisoInternacional();
				if (permisoInternacionalDto != null && permisoInternacionalDto.getTitular() != null && permisoInternacionalDto.getTitular().getDireccion() != null) {
					cambioDomicilio = true;
				} else {
					cambioDomicilio = false;
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("No se le ha indicado ningún numero de expediente para obtener su detalle");
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String guardar() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.guardar(permisoInternacionalDto, utilesColegiado.getIdUsuarioSessionBigDecimal(), cambioDomicilio);
			if (!resultado.getError()) {
				addActionMessage("Trámite guardado");
				if (permisoInternacionalDto.getIdPermiso() == null) {
					Long idPermiso = resultado.getIdPermiso();
					permisoInternacionalDto.setIdPermiso(idPermiso);
				}
				if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
			} else {
				addActionError("Error al guardar");
				if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al guardar el permiso internacional", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String firmarDecl() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.firmarDeclaracion(permisoInternacionalDto, utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError(resultado.getMensaje());
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al firmar la declaración del permiso internacional", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String enviarDecl() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.enviarDeclaracion(permisoInternacionalDto.getIdPermiso(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				if (StringUtils.isNotBlank(resultado.getMensaje())) {
					addActionError(resultado.getMensaje());
				}
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al enviar la declaración del permiso internacional", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String validar() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.validar(permisoInternacionalDto.getIdPermiso(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage("Trámite validado");
			} else {
				addActionError("Trámite no validado");
				if (StringUtils.isNotBlank(resultado.getMensaje())) {
					addActionError(resultado.getMensaje());
				}
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al validar el permiso internacional", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String generarMandato() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.generarMandato(permisoInternacionalDto.getNumExpediente(), permisoInternacionalDto.getIdContrato(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError(resultado.getMensaje());
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al generar el mandato", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String tramitar() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.tramitar(permisoInternacionalDto.getIdPermiso(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError("No se ha podido tramitar");
				if (StringUtils.isNotBlank(resultado.getMensaje())) {
					addActionError(resultado.getMensaje());
				}
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al enviar la solicitud del permiso internacional", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String imprimir() {
		ResultadoPermInterBean resultado = new ResultadoPermInterBean(Boolean.FALSE);
		try {
			resultado = servicioPermisoInternacional.imprimir(permisoInternacionalDto.getIdPermiso(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				try {
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = (String) resultado.getNombreFichero();
					return IMPRIMIR_P_INTER;
				} catch (FileNotFoundException e) {
					log.error("No existe el fichero a imprimir, error:", e);
					addActionError("No existe el fichero a imprimir.");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud del permiso internacional", e);
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String eliminarMandato() {
		try {
			ResultadoPermInterBean resultado = servicioPermisoInternacional.eliminarMandato(permisoInternacionalDto.getNumExpediente());
			if (resultado != null && !resultado.getError()) {
				addActionMessage("Mandato eliminado");
			} else {
				addActionError(resultado.getMensaje());
			}
			permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		} catch (Exception e) {
			log.error("Error al eliminar el mandato del permiso internacional", e);
			addActionError("Error al eliminar el mandato");
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String descargarMandato() {
		try {
			ResultadoPermInterBean resultado = servicioPermisoInternacional.descargarMandato(permisoInternacionalDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession());
			if (resultado != null && !resultado.getError() && resultado.getFichero() != null) {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getFichero().getName();
				borrarFichero(servicioInterga.obtenerNombreDocumentoMandato(permisoInternacionalDto.getNumExpediente(), TipoTramiteTrafico.PermisonInternacional.getValorEnum()), resultado.getFichero()
						.getAbsolutePath());
				return DESCARGAR_MANDATO;
			} else {
				addActionError("No existe el documento");
			}
		} catch (FileNotFoundException e) {
			log.error("Error al descargar el mandato del permiso internacional", e);
			addActionError("No existe el documento");
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	private void borrarFichero(String nombreFicheroDescargar, String rutaFichero) {
		// A los 5 minutos borramos el fichero tanto del GESTOR DOCUMENTOS y de la tabla
		BorrarFicherosImpresionThread hiloBorrar = new BorrarFicherosImpresionThread(nombreFicheroDescargar, rutaFichero);
		hiloBorrar.start();
		log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
	}

	public String descargarDocAportada() {
		if (idPermisoInternacional != null && !idPermisoInternacional.isEmpty()) {
			ResultadoPermInterBean resultado = servicioPermisoInternacional.descargarDocAportada(new Long(idPermisoInternacional));
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				try {
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = (String) resultado.getNombreFichero();
					return IMPRIMIR_P_INTER;
				} catch (FileNotFoundException e) {
					log.error("No existe el fichero a descargar, error:", e);
					addActionError("No existe el fichero a descargar.");
				}
			}
		} else {
			addActionError("Debe de indicar un tramite para la descarga de su documentación aportada.");
		}
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public String subirDocAportada() {
		ResultadoPermInterBean resultado = servicioPermisoInternacional.subirDocAportada(permisoInternacionalDto.getIdPermiso(), permisoInternacionalDto.getNumReferencia(), fichero, ficheroFileName,
				utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			addActionMessage("Documentación enviada a DGT correctamente");
		}
		permisoInternacionalDto = servicioPermisoInternacional.getPermisoInternacional(permisoInternacionalDto.getIdPermiso());
		return ALTA_PERMISO_INTERNACIONAL;
	}

	public PermisoInternacionalDto getPermisoInternacionalDto() {
		return permisoInternacionalDto;
	}

	public void setPermisoInternacionalDto(PermisoInternacionalDto permisoInternacionalDto) {
		this.permisoInternacionalDto = permisoInternacionalDto;
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

	public String getIdPermisoInternacional() {
		return idPermisoInternacional;
	}

	public void setIdPermisoInternacional(String idPermisoInternacional) {
		this.idPermisoInternacional = idPermisoInternacional;
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

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public boolean isCambioDomicilio() {
		return cambioDomicilio;
	}

	public void setCambioDomicilio(boolean cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}
}
