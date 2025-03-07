package org.gestoresmadrid.oegam2.duplicadoPermiso.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDocDuplicadosPermisos;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.service.ServicioDuplicadoPermCond;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.ResultadoDuplPermCondBean;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;
import org.gestoresmadrid.oegamImpresion.utiles.BorrarFicherosImpresionThread;
import org.gestoresmadrid.oegamInterga.service.ServicioInterga;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaDuplicadoPermisoConducirAction extends ActionBase {

	private static final long serialVersionUID = 3775284251922376297L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaDuplicadoPermisoConducirAction.class);

	private static final String ALTA_DUPLICADO_PERMISO_COND = "altaDuplicadoPermisoConducir";
	private static final String DESCARGAR_MANDATO = "descargarMandato";
	private static final String IMPRIMIR_DPC = "imprimirDPC";

	DuplicadoPermisoConducirDto duplicadoPermisoConducirDto;

	BigDecimal numExpediente;

	InputStream inputStream;
	String fileName;

	File fichero;
	String ficheroFileName;
	String ficheroContentType;
	String ficheroFileSize;

	String tipoDocumento;

	String numExpedienteSel;
	String nombreFicheroSel;
	String tipoDocumentoSel;

	@Autowired
	ServicioDuplicadoPermCond servicioDuplicadoPermCond;

	@Autowired
	ServicioInterga servicioInterga;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		duplicadoPermisoConducirDto = new DuplicadoPermisoConducirDto();
		duplicadoPermisoConducirDto.setIdContrato(utilesColegiado.getIdContratoSession());
		duplicadoPermisoConducirDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		duplicadoPermisoConducirDto.setJefatura(utilesColegiado.getIdJefaturaSesion());
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String detalle() {
		if (numExpediente != null) {
			ResultadoDuplPermCondBean resultado = servicioDuplicadoPermCond.getDuplPermCondPorNumExpediente(numExpediente);
			if (!resultado.getError()) {
				duplicadoPermisoConducirDto = resultado.getDuplicadoPermisoConducirDto();
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("No se le ha indicado ningún numero de expediente para obtener su detalle");
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String guardar() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplicadoPermCond.guardar(duplicadoPermisoConducirDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (!resultado.getError()) {
				addActionMessage("Trámite guardado");
				if (duplicadoPermisoConducirDto.getIdDuplicadoPermCond() == null) {
					Long idDuplicadoPermisoCond = resultado.getIdDuplicadoPermisoCond();
					duplicadoPermisoConducirDto.setIdDuplicadoPermCond(idDuplicadoPermisoCond);
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
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		} catch (Exception e) {
			log.error("Error al guardar el duplicado permiso conducir", e);
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String firmarDeclYSolicitud() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplicadoPermCond.firmarDeclYSolicitud(duplicadoPermisoConducirDto, utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError(resultado.getMensaje());
			}
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		} catch (Exception e) {
			log.error("Error al firmar la declaración y la solicitud del duplicado permiso conducir", e);
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String validar() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplicadoPermCond.validar(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage("Trámite validado");
			} else {
				addActionError("Trámite no validado");
				if (StringUtils.isNotBlank(resultado.getMensaje())) {
					addActionError(resultado.getMensaje());
				}
			}
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		} catch (Exception e) {
			log.error("Error al validar el duplicado permiso conducir", e);
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String tramitar() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplicadoPermCond.tramitar(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError("No se ha podido tramitar.");
				if (StringUtils.isNotBlank(resultado.getMensaje())) {
					addActionError(resultado.getMensaje());
				}
			}
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		} catch (Exception e) {
			log.error("Error al enviar la solicitud del duplicado permiso conducir", e);
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String enviarDocumentacion() {
		ResultadoDuplPermCondBean resultado = servicioDuplicadoPermCond.enviarDocumentacion(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), utilesColegiado.getIdUsuarioSession());
		if (resultado.getError()) {
			addActionError("Errores en el envío de la documentación, inténtelo de nuevo más tarde.");
			if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
				for (String mensaje : resultado.getListaMensajes()) {
					addActionError(mensaje);
				}
			}
		} else {
			addActionMessage("Documentación enviada a DGT correctamente");
		}
		duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String generarMandato() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplicadoPermCond.generarMandato(duplicadoPermisoConducirDto.getNumExpediente(), duplicadoPermisoConducirDto.getIdContrato(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError(resultado.getMensaje());
			}
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		} catch (Exception e) {
			log.error("Error al generar el mandato", e);
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String descargarMandato() {
		try {
			ResultadoDuplPermCondBean resultado = servicioDuplicadoPermCond.descargarMandato(duplicadoPermisoConducirDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession());
			if (resultado != null && !resultado.getError() && resultado.getFichero() != null) {
				inputStream = new FileInputStream(resultado.getFichero());
				fileName = resultado.getFichero().getName();
				borrarFichero(servicioInterga.obtenerNombreDocumentoMandato(duplicadoPermisoConducirDto.getNumExpediente(), TipoTramiteTrafico.DuplicadoPermisoConducir.getValorEnum()), resultado
						.getFichero().getAbsolutePath());
				return DESCARGAR_MANDATO;
			} else {
				addActionError("No existe el documento");
			}
		} catch (FileNotFoundException e) {
			log.error("Error al descargar el mandato del duplicado permiso conducir", e);
			addActionError("No existe el documento");
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	private void borrarFichero(String nombreFicheroDescargar, String rutaFichero) {
		// A los 5 minutos borramos el fichero tanto del GESTOR DOCUMENTOS y de la tabla
		BorrarFicherosImpresionThread hiloBorrar = new BorrarFicherosImpresionThread(nombreFicheroDescargar, rutaFichero);
		hiloBorrar.start();
		log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
	}

	public String descargarDocumentacion() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (numExpedienteSel != null && !numExpedienteSel.isEmpty()) {
			if (TiposDocDuplicadosPermisos.DECLARACION.getNombreEnum().equals(tipoDocumentoSel) || TiposDocDuplicadosPermisos.SOLICITUD.getNombreEnum().equals(tipoDocumentoSel)) {
				resultado = servicioDuplicadoPermCond.descargarFichero(nombreFicheroSel, utilesColegiado.getIdUsuarioSession());
			} else {
				resultado = servicioDuplicadoPermCond.descargarDocAportada(nombreFicheroSel, new BigDecimal(numExpedienteSel));
			}

			if (resultado != null && resultado.getError() && resultado.getFichero() != null) {
				addActionError(resultado.getMensaje());
			} else {
				try {
					inputStream = new FileInputStream(resultado.getFichero());
					fileName = resultado.getFichero().getName();
					return IMPRIMIR_DPC;
				} catch (FileNotFoundException e) {
					log.error("No existe el fichero a descargar, error:", e);
					addActionError("No existe el fichero a descargar.");
				}
			}
		} else {
			addActionError("Debe de indicar un tramite para la descarga de su documentación aportada.");
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String eliminarDocAportada() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		if (numExpedienteSel != null && !numExpedienteSel.isEmpty()) {
			resultado = servicioDuplicadoPermCond.eliminarDocAportada(nombreFicheroSel, tipoDocumentoSel, new BigDecimal(numExpedienteSel));
			if (resultado != null && !resultado.getError()) {
				addActionMessage("Fichero eliminado");
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("Debe de indicar un tramite para la descarga de su documentación aportada.");
		}
		duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String eliminarMandato() {
		try {
			ResultadoDuplPermCondBean resultado = servicioDuplicadoPermCond.eliminarMandato(duplicadoPermisoConducirDto.getNumExpediente());
			if (resultado != null && !resultado.getError()) {
				addActionMessage("Mandato eliminado");
			} else {
				addActionError(resultado.getMensaje());
			}
			duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		} catch (Exception e) {
			log.error("Error al eliminar el mandato del duplicado permiso conducir", e);
			addActionError("Error al eliminar el mandato");
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String subirDocAportada() {
		if(!utiles.esNombreFicheroValido(ficheroFileName)) {
			addActionError("El nombre del fichero no es válido");
		}else {
			ResultadoDuplPermCondBean resultado = servicioDuplicadoPermCond.subirDocAportada(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), tipoDocumento, fichero, ficheroFileName, utilesColegiado
					.getIdUsuarioSession());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Documentación subida correctamente");
			}
		}
		duplicadoPermisoConducirDto = servicioDuplicadoPermCond.getDuplicadoPermisoConducir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond());
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String imprimir() {
		ResultadoDuplPermCondBean resultado = new ResultadoDuplPermCondBean(Boolean.FALSE);
		try {
			resultado = servicioDuplicadoPermCond.imprimir(duplicadoPermisoConducirDto.getIdDuplicadoPermCond(), utilesColegiado.getIdUsuarioSession());
			if (!resultado.getError()) {
				try {
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = (String) resultado.getNombreFichero();
					return IMPRIMIR_DPC;
				} catch (FileNotFoundException e) {
					log.error("No existe el fichero a imprimir, error:", e);
					addActionError("No existe el fichero a imprimir.");
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud del duplicado permiso conducir", e);
		}
		return ALTA_DUPLICADO_PERMISO_COND;
	}

	public String getNumExpedienteSel() {
		return numExpedienteSel;
	}

	public void setNumExpedienteSel(String numExpedienteSel) {
		this.numExpedienteSel = numExpedienteSel;
	}

	public DuplicadoPermisoConducirDto getDuplicadoPermisoConducirDto() {
		return duplicadoPermisoConducirDto;
	}

	public void setDuplicadoPermisoConducirDto(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		this.duplicadoPermisoConducirDto = duplicadoPermisoConducirDto;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ServicioDuplicadoPermCond getServicioDuplicadoPermCond() {
		return servicioDuplicadoPermCond;
	}

	public void setServicioDuplicadoPermCond(ServicioDuplicadoPermCond servicioDuplicadoPermCond) {
		this.servicioDuplicadoPermCond = servicioDuplicadoPermCond;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNombreFicheroSel() {
		return nombreFicheroSel;
	}

	public void setNombreFicheroSel(String nombreFicheroSel) {
		this.nombreFicheroSel = nombreFicheroSel;
	}

	public String getTipoDocumentoSel() {
		return tipoDocumentoSel;
	}

	public void setTipoDocumentoSel(String tipoDocumentoSel) {
		this.tipoDocumentoSel = tipoDocumentoSel;
	}
}
