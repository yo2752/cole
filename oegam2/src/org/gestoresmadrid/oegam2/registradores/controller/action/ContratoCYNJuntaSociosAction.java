package org.gestoresmadrid.oegam2.registradores.controller.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.utiles.ConstantesPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class ContratoCYNJuntaSociosAction extends ActionBase {

	private static final long serialVersionUID = -6349047434597090432L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	private static final String TRAMITE_REGISTRO_MODELO_4 = "tramiteRegistroModelo4";
	private static final String VER_RM = "verRm";
	private static final String ID_CONTRATO_REGISTRO = "TramiteRegistroMd4";
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private TramiteRegistroDto tramiteRegistro;

	private BigDecimal numExpediente;

	private boolean rmFirmar;

	private String tipoContratoRegistro;

	private String idRegistroFueraSecuencia;

	private String estadoAcusePendiente;

	// FICHEROS
	private InputStream inputStream;
	private String ficheroDescarga;
	private String fileUploadFileName;
	private File fileUpload;
	private String idFicheroEliminar;
	private String idFicheroDescargar;
	private String nombreDoc;
	private String tipoDoc;

	public String inicio() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO4_INICIO + "inicio.");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		if (tramiteRegistro == null) {
			tramiteRegistro = new TramiteRegistroDto();
		}
		tramiteRegistro.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		tramiteRegistro.setTipoTramite(TipoTramiteRegistro.MODELO_4.getValorEnum());
		tramiteRegistro.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		tramiteRegistro.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String guardar() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);

		ResultBean result = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());

		ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(EstadoTramiteRegistro.Iniciado
				.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (respuestaBean != null && respuestaBean.getError()) {
			log.error(Claves.CLAVE_LOG_MODELO_REGISTRADORES_ERROR + "Ha ocurrido el siguiente error actualizando a 'Iniciado' el tramite con identificador: " + tramiteRegistro.getIdTramiteRegistro()
					+ " : " + respuestaBean.getMensaje());
		} else {
			tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		}

		if (result.getError() && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
			for (String mensaje : result.getListaMensajes())
				addActionError(mensaje);

			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));

			ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
				tramiteRegistro.setFicherosSubidos(ficherosSubidos);
			}

		} else {
			addActionMessage("Trámite guardado correctamente");
			recuperar();
		}

		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String recuperar() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO4_INICIO + "inicio.");
		if (null == numExpediente)
			numExpediente = tramiteRegistro.getIdTramiteRegistro();

		if (null == numExpediente) {
			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
			return TRAMITE_REGISTRO_MODELO_4;
		}
		tramiteRegistro = servicioTramiteRegistro.getTramite(numExpediente);
		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String construirRm() throws Throwable {
		ResultRegistro resultado;
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		try {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO4_INICIO + "construirRm.");

			recuperar();

			resultado = servicioTramiteRegistro.construirRm(tramiteRegistro, rmFirmar, utilesColegiado.getAlias());

			if (!resultado.isError()) {
				if (!rmFirmar) {
					return VER_RM;
				}
				addActionMessage(resultado.getMensaje());
			} else {
				if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
					for (String validacion : resultado.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultado.getMensaje());
				}
			}

		} catch (Exception ex) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO4_ERROR + "construirRm()" + ex + "\n" + UtilesExcepciones.stackTraceAcadena(ex, 3));
			addActionError("Error en la construcción del documento rm (registro mercantil) ");
		}
		recuperar();
		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String validarTramite() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		ResultRegistro resultado = new ResultRegistro();
		try {

			ResultBean result = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (null != result && result.getError() && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}

			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());

			resultado = servicioTramiteRegistro.validarRegistro(tramiteRegistro);

			if (!resultado.isError()) {
				ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(
						EstadoTramiteRegistro.Validado.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuestaBean != null && respuestaBean.getError()) {
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO2_ERROR + "Ha ocurrido el siguiente error actualizando a 'Validado' el tramite con identificador: " + tramiteRegistro
							.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
				} else {
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()));
				}
				servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
				addActionMessage("Trámite validado");
			} else {
				if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
					for (String validacion : resultado.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultado.getMensaje());
				}
			}
		} catch (Exception e) {
			addActionError("Error al validar el trámite.");
		}
		recuperar();
		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String construirDpr() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO4_INICIO + "construirDpr.");
		ResultRegistro resultRegistro;

		// String entorno = gestorPropiedades.valorPropertie("Entorno");

		/*
		 * if (entorno != null && ("PREPRODUCCION".equals(entorno) || "TEST".equals(entorno))) { addActionError("No se puede enviar un trámite de registro desde los entornos de PRE o TEST"); return TRAMITE_REGISTRO_MODELO_4; }
		 */

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
		tramiteRegistro.setFicherosSubidos(ficherosSubidos);
		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			addActionError("No se ha adjuntado ninguna documentación");
			recuperar();
			return TRAMITE_REGISTRO_MODELO_4;
		}

		resultRegistro = servicioTramiteRegRbm.construirDpr(getTramiteRegistro().getIdTramiteRegistro().toString(), utilesColegiado.getAlias());

		if (resultRegistro.isError()) {
			addActionError(resultRegistro.getMensaje());
		} else {
			addActionMessage(resultRegistro.getMensaje());
		}

		recuperar();
		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String construirFirmarAcuse() {
		log.info("TRAMITE_REGISTRO_MODELO_4 firmar acuse.");

		ResultRegistro resultRegistro;

		resultRegistro = servicioTramiteRegistro.construirAcuse(getTramiteRegistro().getIdTramiteRegistro().toString(), utilesColegiado.getAlias(), estadoAcusePendiente, idRegistroFueraSecuencia);

		if (resultRegistro.isError()) {
			addActionError(resultRegistro.getMensaje());
		} else {
			addActionMessage(resultRegistro.getMensaje());
		}

		recuperar();
		return TRAMITE_REGISTRO_MODELO_4;
	}

	// ---------------------------------------------Bloque Documentos ------------------------------------------------------------------------

	// Para añadir ficheros a la lista desde el botón de la pestaña documentación
	public String incluirFichero() throws Exception, OegamExcepcion {
		String cadenaIncluirFichero = "incluirFichero.";

		// Previamente guardamos el contrato
		ResultBean result = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (null != result && result.getError() && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
			for (String mensaje : result.getListaMensajes())
				addActionError(mensaje);
		}

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + cadenaIncluirFichero);
		String returnStruts = "";
		try {
			returnStruts = TRAMITE_REGISTRO_MODELO_4;
			ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (ficherosSubidos == null) {
				ficherosSubidos = new ArrayList<FicheroInfo>();
			}
			if (fileUpload != null) {
				// Verifica que no se ha alcanzado el máximo de ficheros permitidos:
				int maxFicheros = Integer.parseInt(gestorPropiedades.valorPropertie("envioEscrituras.numero.maximo.ficheros"));
				if (ficherosSubidos.size() == maxFicheros) {
					addActionError("Alcanzado el máximo de ficheros a incluir en el envío de documentación: " + maxFicheros);
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
					return returnStruts;
				}
				// Verifica que el nombre del fichero no contenga caracteres especiales
				if (!utiles.esNombreFicheroValido(fileUploadFileName)) {
					addActionError("El fichero añadido debe tener un nombre correcto");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
					return returnStruts;
				}
				// Verifica que el fichero subido tiene un formato soportado
				String extension = utiles.dameExtension(fileUploadFileName, false);
				if (extension == null) {
					addActionError("El fichero añadido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .xls, .xml, .jpg, .jpeg o .zip");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
					return returnStruts;
				}
				// PCKS7, PDF , RTF, DOC, TIF, ASC, XLS, XML,JPG, JPEG y ZIP
				if (extension.equalsIgnoreCase("pkcs7") || extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("rtf") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase(
						"tif") || extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xml") || extension.equalsIgnoreCase("jpg") || extension
								.equalsIgnoreCase("jpeg")) {
					try {
						File fichero = guardarFichero(extension);
						FicheroInfo ficheroInfo = new FicheroInfo(fichero, ficherosSubidos.size());
						ficherosSubidos.add(ficheroInfo);
						addActionMessage("Fichero añadido correctamente");
						tramiteRegistro.setFicherosSubidos(ficherosSubidos);
						tramiteRegistro.setExtensionFicheroEnviado(ficheroInfo.getExtension());
						if (tramiteRegistro.getFicheroSubido() == null) {
							tramiteRegistro.setFicheroSubido("si");
							servicioTramiteRegistro.actualizarFicheroSubido(tramiteRegistro.getIdTramiteRegistro());
						}
						log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + cadenaIncluirFichero);
					} catch (Exception ex) {
						log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + ex);
						addActionError("Se ha lanzado una excepción Oegam relacionada con el fichero de propiedades");
						log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					}
				} else {
					addActionError("El fichero añadido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .asc, .xls, .xml, .jpg, .jpeg o .zip");
					log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
				}
			} else {
				addActionError("Utilice el botón 'Examinar' para seleccionar los ficheros a adjuntar en el envío.");
				log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
			}
		} catch (Exception ex) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + ex);
			addActionError("Se ha lanzado una excepción Oegam relacionada con el fichero de propiedades");
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
		}

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + cadenaIncluirFichero);

		recuperar();

		return returnStruts;
	}

	private File guardarFichero(String extension) throws Exception, OegamExcepcion {
		Long idFichero = new Date().getTime();
		FileInputStream fis = new FileInputStream(fileUpload);
		byte[] array = IOUtils.toByteArray(fis);
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.REGISTRADORES);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION_ENVIADA);
		ficheroBean.setExtension("." + extension);
		ficheroBean.setFicheroByte(array);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteRegistro.getIdTramiteRegistro()));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento("fichero_registro_" + idFichero + "_" + tramiteRegistro.getIdTramiteRegistro());
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}

	public String eliminarFichero() {
		String returnStruts = "";

		// Previamente guardamos el contrato
		ResultBean result = servicioTramiteRegistro.guardarTramiteRegistro(tramiteRegistro, null, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (null != result && result.getError() && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
			for (String mensaje : result.getListaMensajes())
				addActionError(mensaje);
		}

		try {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminarFichero.");
			returnStruts = TRAMITE_REGISTRO_MODELO_4;
			if (tramiteRegistro == null) {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " No se ha recuperado tramite en curso de la sesion.");
				return returnStruts;
			}

			// Si está 'finalizado con error' lo pone en 'iniciado':
			ArrayList<FicheroInfo> ficheros = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(getIdFicheroEliminar())) {
						// Borra fisicamente el fichero del disco:
						tmp.getFichero().delete();
						it.remove();
						tramiteRegistro.setFicherosSubidos(ficheros);
						if (ficheros.isEmpty()) {
							tramiteRegistro.setFicheroSubido("no");
							servicioTramiteRegistro.actualizarFicheroSubido(tramiteRegistro.getIdTramiteRegistro());
						}
						addActionMessage("Fichero eliminado correctamente");
						recuperar();
						return returnStruts;
					}
				}
			} else {
				addActionError("No existe fichero para eliminar");
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "eliminarFichero.");
		} catch (Exception ex) {
			log.error(ex);
			log.error(UtilesExcepciones.stackTraceAcadena(ex, 3));
			addActionError(ex.getMessage());
		}

		recuperar();

		return returnStruts;
	}

	public String descargarDocumentacion() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + DESCARGAR_DOCUMENTO);
		ArrayList<FicheroInfo> ficheros = null;
		try {

			if (tipoDoc.equals("recibida")) {
				ficheros = servicioTramiteRegistro.recuperarDocumentosRecibidos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			} else {
				ficheros = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
			}

			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(nombreDoc)) {
						File fichero = tmp.getFichero();
						inputStream = new FileInputStream(fichero);
						ficheroDescarga = tmp.getNombre();
						return DESCARGAR_DOCUMENTO;
					}
				}
			} else {
				addActionError("No existe fichero para descargar");
			}
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + DESCARGAR_DOCUMENTO);
		} catch (FileNotFoundException ex) {
			addActionError("No se ha podido recuperar el fichero");
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + DESCARGAR_DOCUMENTO);
		} catch (Exception ex) {
			log.error(ex);
			log.error(UtilesExcepciones.stackTraceAcadena(ex, 3));
			addActionError(ex.toString());
			addActionError(UtilesExcepciones.stackTraceAcadena(ex, 3));
		}

		recuperar();
		return TRAMITE_REGISTRO_MODELO_4;
	}

	public String descargarAdjuntos() throws OegamExcepcion, IOException {

		boolean descargar = false;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(baos);

		FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades
				.transformExpedienteFecha(tramiteRegistro.getIdTramiteRegistro().toString().trim()), tramiteRegistro.getIdTramiteRegistro().toString().trim());

		if (null != ficheros.getFiles() && !ficheros.getFiles().isEmpty()) {
			for (File temporal : ficheros.getFiles()) {
				descargar = true;
				FileInputStream fis = new FileInputStream(temporal);
				ZipEntry entrada = new ZipEntry(temporal.getName());
				out.putNextEntry(entrada);
				byte[] buffer = new byte[1024];
				int leido = 0;
				while (0 < (leido = fis.read(buffer))) {
					out.write(buffer, 0, leido);
				}
				fis.close();
				out.closeEntry();
			}
		}
		out.close();
		if (descargar) {
			ByteArrayInputStream stream = new ByteArrayInputStream(baos.toByteArray());
			ficheroDescarga = tramiteRegistro.getIdTramiteRegistro().toString() + ConstantesPDF.EXTENSION_ZIP;
			inputStream = stream;
			return DESCARGAR_DOCUMENTO;
		}

		recuperar();
		addActionError("No existe documentación para descargar");
		return TRAMITE_REGISTRO_MODELO_4;
	}
	// ---------------------------------------------Fin Bloque Documentos ------------------------------------------------------------------------------

	public TramiteRegistroDto getTramiteRegistro() {
		return tramiteRegistro;
	}

	public void setTramiteRegistro(TramiteRegistroDto tramiteRegistro) {
		this.tramiteRegistro = tramiteRegistro;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public boolean getRmFirmar() {
		return rmFirmar;
	}

	public void setRmFirmar(boolean rmFirmar) {
		this.rmFirmar = rmFirmar;
	}

	public String getTipoContratoRegistro() {
		return tipoContratoRegistro;
	}

	public void setTipoContratoRegistro(String tipoContratoRegistro) {
		this.tipoContratoRegistro = tipoContratoRegistro;
	}

	public String getIdRegistroFueraSecuencia() {
		return idRegistroFueraSecuencia;
	}

	public void setIdRegistroFueraSecuencia(String idRegistroFueraSecuencia) {
		this.idRegistroFueraSecuencia = idRegistroFueraSecuencia;
	}

	public String getEstadoAcusePendiente() {
		return estadoAcusePendiente;
	}

	public void setEstadoAcusePendiente(String estadoAcusePendiente) {
		this.estadoAcusePendiente = estadoAcusePendiente;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFicheroDescarga() {
		return ficheroDescarga;
	}

	public void setFicheroDescarga(String ficheroDescarga) {
		this.ficheroDescarga = ficheroDescarga;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getIdFicheroEliminar() {
		return idFicheroEliminar;
	}

	public void setIdFicheroEliminar(String idFicheroEliminar) {
		this.idFicheroEliminar = idFicheroEliminar;
	}

	public String getIdFicheroDescargar() {
		return idFicheroDescargar;
	}

	public void setIdFicheroDescargar(String idFicheroDescargar) {
		this.idFicheroDescargar = idFicheroDescargar;
	}

	public String getNombreDoc() {
		return nombreDoc;
	}

	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

}