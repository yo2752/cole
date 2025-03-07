package org.icogam.legalizacion.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.legalizacion.Modelo.ModeloLegalizacionInt;
import org.icogam.legalizacion.ModeloImpl.ModeloLegalizacionImpl;
import org.icogam.legalizacion.dto.LegalizacionCitaDto;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;

/**
 * @author ext_jmbc
 */
public class AltaLegalizacionAction extends ActionBase implements SessionAware {

	private static final long serialVersionUID = -8518866369020619052L;

	private static ILoggerOegam log = LoggerOegam.getLogger(AltaLegalizacionAction.class);

	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String TipoPermisos;
	private String idPeticion;
	private LegalizacionCitaDto legDto;
	private ModeloLegalizacionInt modeloLegalizacion = new ModeloLegalizacionImpl();
	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private final String COBRAR_CREDITOS = "si";
	private Map<String, Object> session;
	private Fecha fechaEntregaDocumentacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private ServicioCredito servicioCredito;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() throws Exception {
		if (utilesColegiado.tienePermisoAdmin()) {
			TipoPermisos = UtilesColegiado.PERMISO_ADMINISTRACION;
		} else {
			TipoPermisos = "GESTOR";
		}
		return SUCCESS;
	}

	public String limpiarCampos() {
		legDto = new LegalizacionCitaDto();
		return SUCCESS;
	}

	public String guardar() {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA)) {
			addActionError("No tiene permiso para realizar una peticion.");
			return SUCCESS;
		}

		if (!validar()) {
			return SUCCESS;
		}

		if (hasErrors()) {
			return ERROR;
		}

		if (legDto.getNumColegiado() == null || legDto.getNumColegiado().equals("")) {
			legDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}

		if (utilesColegiado.tienePermisoAdmin()) {
			if (legDto.getIdPeticion() != null) {
				try {
					legDto = modeloLegalizacion.actualizar(legDto);
					addActionMessage("Petición actualizada");
				} catch (ParseException e) {
					log.error(e);
					addActionError("Petición no actualizada");
				}
			} else {
				try {
					legDto = modeloLegalizacion.guardar(legDto);
					addActionMessage("Nueva Petición guardada");
				} catch (ParseException e) {
					log.error("Error de parseado");
					addActionError("Petición no guardada");
				} catch (OegamExcepcion e) {
					log.error("Error número maximo de peticiones");
					addActionError(e.getMessage());
				}
			}

			if (fileUpload != null) {
				try {
					String extension = "." + fileUploadContentType.split("/")[1];
					modeloLegalizacion.guardarDocumentacion(legDto, fileUpload, extension);
					// Despúes de subir la documentación se envía un e-mail a MAEC informándole.
					modeloLegalizacion.envioMailInformandoMAECDocumentacionSubida(legDto);
				} catch (OegamExcepcion e) {
					log.error(e);
					addActionError("Se ha producido una incidencia al subir el fichero.");
				} catch (ParseException e) {
					log.error(e);
					addActionError("Fichero no subido.");
				}
			}

		} else {
			if (legDto.getIdPeticion() != null) {
				try {
					ResultBean result = modeloLegalizacion.permiteModificarPeticion(legDto.getIdPeticion(), legDto.getFechaLegalizacion());
					if (!result.getError()) {
						modeloLegalizacion.actualizar(legDto);
						addActionMessage("Petición actualizada");
					} else {
						addActionError(result.getMensaje());
					}
				} catch (ParseException e) {
					log.error("Se ha producido una excepcion al modificar una petición de legalización", e);
					addActionError("Petición no actualizada");
				} catch (Exception e) {
					log.error("Se ha producido una excepcion al modificar una petición de legalización", e);
					addActionError("Petición no actualizada");
				}
			} else {
				Integer idPeticion = null;
				BigDecimal creditosCobrados = BigDecimal.ZERO;
				try {
					ResultBean result = modeloLegalizacion.permiteGuardarPeticion(legDto);
					if (!result.getError()) {
						LegalizacionCitaDto resultadoGuardar = modeloLegalizacion.guardar(legDto);

						// Si es un alta nuevo se descuentan creditos.
						if (resultadoGuardar != null && resultadoGuardar.getIdPeticion() != null && gestorPropiedades.valorPropertie("cobrar.peticiones").equals(COBRAR_CREDITOS)) {
							idPeticion = resultadoGuardar.getIdPeticion();
							try {
								creditosCobrados = descontarCreditos(utilesColegiado.getIdContratoSessionBigDecimal(),
										utilesColegiado.getIdUsuarioSessionBigDecimal(), idPeticion);
							} catch (DescontarCreditosExcepcion e) {
								addActionError("No dispone de suficientes créditos para poder dar de alta una petición de legalización.");
								log.error("Se ha producido un error al descontar creditos: ", e);
								return SUCCESS;
							}
						}

						addActionMessage("Nueva Petición guardada");
					} else {
						addActionError(result.getMensaje());
					}
				} catch (ParseException e) {
					log.error("Error de parseado");
					devolverCreditos(idPeticion, creditosCobrados);
					addActionError("Petición no guardada");
				} catch (OegamExcepcion e) {
					log.error("Se ha producido una excepcion al guardar una petición de legalización", e);
					devolverCreditos(idPeticion, creditosCobrados);
					addActionError("Error número maximo de peticiones");
				} catch (Throwable e) {
					log.error("Se ha producido una excepcion al guardar una petición de legalización", e);
					devolverCreditos(idPeticion, creditosCobrados);
					addActionError("Error al dar de altala petición");
				}
			}
		}
		return SUCCESS;
	}

	public String cargarPeticion() {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA)) {
			addActionError("No tiene permiso para modificar una peticion.");
			return SUCCESS;
		}

		if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoMinisterio()) {
			return "listadoPeticiones";
		}

		if (idPeticion == null || idPeticion.equals("")) {
			addActionError("No tiene permiso para modificar una peticion.");
			return SUCCESS;
		}

		legDto = new LegalizacionCitaDto();
		legDto.setIdPeticion(Integer.parseInt(idPeticion));
		try {
			legDto = modeloLegalizacion.getPeticion(legDto);

			if (legDto.getFechaLegalizacion() != null) {
				fechaEntregaDocumentacion = utilesFecha.getPrimerLaborableAnterior(legDto.getFechaLegalizacion());
			}

			if (!utilesColegiado.tienePermisoAdmin()) {

				if (!utilesColegiado.getNumColegiadoSession().equals(legDto.getNumColegiado())) {
					legDto = null;
					log.error("El Colegiado " + utilesColegiado.getNumColegiadoSession() + " a intentado entrar con un tramite de otro colegiado.");
					addActionError("No puede acceder a la peticion solicitada");
				}

			}

		} catch (ParseException e) {
			log.error("Error al recuperar la peticion");
		} catch (Throwable e) {
			log.error("Error al recuperar la fecha de la peticion");
		}
		return SUCCESS;
	}

	public String descargar() {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA)) {
			addActionError("No tiene permiso para modificar una peticion.");
			return SUCCESS;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			List<File> listaFicheros = modeloLegalizacion.getDocumentacion(legDto);

			legDto = modeloLegalizacion.getPeticion(legDto);

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
			setFileName(legDto.getNombre() + ConstantesGestorFicheros.EXTENSION_ZIP);
			log.debug("Enviando el ZIP");

		} catch (ParseException e) {
			log.error(e);
		} catch (OegamExcepcion e) {
			log.error(e);
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return "descargarDocumentos";
	}

	private boolean validar() {
		if (!legDto.getFechaLegalizacion().getAnio().equals("") || !legDto.getFechaLegalizacion().getMes().equals("") || !legDto.getFechaLegalizacion().getDia().equals("")) {
			if (legDto.getFechaLegalizacion().getAnio().equals("") || legDto.getFechaLegalizacion().getMes().equals("") || legDto.getFechaLegalizacion().getDia().equals("")) {
				addActionError("Se debe introducir una fecha completa");
				return false;
			}
		}

		if (legDto.getFechaDocumentacion().getAnio().equals("") || legDto.getFechaDocumentacion().getMes().equals("") || legDto.getFechaDocumentacion().getDia().equals("")) {
			addActionError("Se debe introducir una fecha completa");
			return false;
		}

		if (legDto.getTipoDocumento() == null || legDto.getTipoDocumento().equals("-1")) {
			addActionError("Se debe de elegir un tipo de documento");
			return false;
		}

		if (legDto.getNombre() == null || legDto.getNombre().equals("")) {
			addActionError("No se ha rellenado el nombre.");
			return false;
		}
		
		if (legDto.getClaseDocumento() == null || legDto.getClaseDocumento().equals("-1")) {
			addActionError("Se debe de elegir una clase de documento");
			return false;
		}
		
		if (legDto.getPais() == null || legDto.getPais().equals("")) {
			addActionError("No se ha rellenado el pais.");
			return false;
		}
		return true;
	}

	public BigDecimal descontarCreditos(BigDecimal contrato, BigDecimal idUsuario, Integer idPeticion) throws DescontarCreditosExcepcion {
		BigDecimal numCreditos = BigDecimal.ONE;
		ResultBean result = servicioCredito.descontarCreditos(TipoTramiteTrafico.Legalizacion.getValorEnum(), contrato, numCreditos, idUsuario, ConceptoCreditoFacturado.LEG, idPeticion.toString());

		if (result.getError()) {
			String mensajeError = "Error al descontar créditos de la operación";
			result.setError(true);
			log.error(mensajeError);
			throw new DescontarCreditosExcepcion(result.getMensaje());
		} else {
			return numCreditos;
		}
	}

	private void devolverCreditos(Integer idPeticion, BigDecimal numCreditos) {
		if (numCreditos != null && numCreditos.intValue() > 0 && idPeticion != null) {
			servicioCredito.devolverCreditos(TipoTramiteTrafico.Legalizacion.getValorEnum(), utilesColegiado.getIdContratoSessionBigDecimal(), numCreditos.intValue(), 
					utilesColegiado.getIdUsuarioSessionBigDecimal(),
					ConceptoCreditoFacturado.DLEG, idPeticion.toString());
		}
	}

	public LegalizacionCitaDto getLegDto() {
		return legDto;
	}

	public void setLegDto(LegalizacionCitaDto legDto) {
		this.legDto = legDto;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getTipoPermisos() {
		return TipoPermisos;
	}

	public void setTipoPermisos(String tipoPermisos) {
		TipoPermisos = tipoPermisos;
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

	public Fecha getFechaEntregaDocumentacion() {
		return fechaEntregaDocumentacion;
	}

	public void setFechaEntregaDocumentacion(Fecha fechaEntregaDocumentacion) {
		this.fechaEntregaDocumentacion = fechaEntregaDocumentacion;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
