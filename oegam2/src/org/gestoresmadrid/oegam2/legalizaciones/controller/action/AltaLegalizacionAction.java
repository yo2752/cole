package org.gestoresmadrid.oegam2.legalizaciones.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioLegalizacionesCita;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.ResultadoLegalizacionesBean;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
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
public class AltaLegalizacionAction extends ActionBase {

	private static final long serialVersionUID = -8518866369020619052L;

	private static ILoggerOegam log = LoggerOegam.getLogger(AltaLegalizacionAction.class);

	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String idPeticion;
	private LegalizacionCitaDto legDto;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private static final String COBRAR_CREDITOS = "si";
	private Fecha fechaEntregaDocumentacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioLegalizacionesCita servicioLegalizaciones;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private Utiles utiles;

	public String inicio() {
		if (utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		} else {
			return SUCCESS;
		}
	}

	public String guardar() {
		boolean descontarCredito = Boolean.FALSE;
		try {
			if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA)) {
				addActionMessage("No tiene permiso para realizar una petición.");
				return SUCCESS;
			}

			if (legDto.getIdPeticion() == null) {
				descontarCredito = Boolean.TRUE;
			}

			if (legDto.getIdContrato() == null) {
				legDto.setIdContrato(utilesColegiado.getIdContratoSession().intValue());
				legDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			} else {
				legDto.setNumColegiado(utilesColegiado.getNumColegiadoByIdContrato(new BigDecimal(legDto.getIdContrato())));
			}

			ResultadoLegalizacionesBean resultado = servicioLegalizaciones.guardarPeticion(legDto, fileUpload, fileUploadFileName, utilesColegiado.tienePermisoAdmin());
			if (resultado.getError()) {
				if (resultado.getListaMensajesError() != null && !resultado.getListaMensajesError().isEmpty()) {
					for (String validacion : resultado.getListaMensajesError()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				legDto = resultado.getLegalizacionCitaDto();
				if (descontarCredito && legDto != null && legDto.getIdPeticion() != null && gestorPropiedades.valorPropertie("cobrar.peticiones").equalsIgnoreCase(COBRAR_CREDITOS)) {
					BigDecimal creditosCobrados = BigDecimal.ZERO;
					try {
						creditosCobrados = descontarCreditos(new BigDecimal(legDto.getIdContrato()), utilesColegiado.getIdUsuarioSessionBigDecimal(), legDto.getIdPeticion());
					} catch (DescontarCreditosExcepcion e) {
						addActionError("No dispone de suficientes créditos para poder dar de alta una petición de legalización.");
						log.error("Se ha producido un error al descontar créditos: ", e);
						this.devolverCreditos(legDto.getIdPeticion(), creditosCobrados);
						return SUCCESS;
					}
				} else {
					addActionMessage(resultado.getMensaje());
				}
			}

		} catch (Exception e) {
			log.error("Se ha producido un error al guardar el trámite de Legalización: ", e);
			addActionError(e.getMessage());
		}

		return SUCCESS;
	}

	public String cargarPeticion() {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_LEGALIZACION_DOCUMENTOS_ALTA) || StringUtils.isBlank(idPeticion)) {
			addActionError("No tiene permiso para modificar una petición.");
			return SUCCESS;
		}

		if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoMinisterio()) {
			return "listadoPeticiones";
		}

		legDto = new LegalizacionCitaDto();
		legDto.setIdPeticion(Integer.parseInt(idPeticion));
		try {
			legDto = servicioLegalizaciones.getPeticion(legDto);

			if (legDto.getFechaLegalizacion() != null) {
				fechaEntregaDocumentacion = utilesFecha.getPrimerLaborableAnterior(legDto.getFechaLegalizacion());
			}

			if (!utilesColegiado.tienePermisoAdmin()
						&& !utilesColegiado.getNumColegiadoSession().equals(legDto.getNumColegiado())) {
				legDto = null;
				log.error("El Colegiado " + utilesColegiado.getNumColegiadoSession() + " ha intentado entrar con un trámite de otro colegiado.");
				addActionError("No puede acceder a la petición solicitada");
			}
		} catch (ParseException e) {
			log.error("Error al recuperar la petición: ", e);
		} catch (Throwable e) {
			log.error("Error al recuperar la fecha de la petición: ", e);
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
			setFileName(legDto.getNombre() + ConstantesGestorFicheros.EXTENSION_ZIP);
			log.debug("Enviando el ZIP");

		} catch (ParseException | OegamExcepcion | IOException e) {
			log.error("Error al descargar el documento de Legalización: ", e);
		}
		return "descargarDocumentos";
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
			servicioCredito.devolverCreditos(TipoTramiteTrafico.Legalizacion.getValorEnum(), utilesColegiado.getIdContratoSessionBigDecimal(), numCreditos.intValue(), utilesColegiado
					.getIdUsuarioSessionBigDecimal(), ConceptoCreditoFacturado.DLEG, idPeticion.toString());
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

}