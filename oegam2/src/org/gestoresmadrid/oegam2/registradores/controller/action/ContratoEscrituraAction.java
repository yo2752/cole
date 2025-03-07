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
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados.TipoCuentaBancaria;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoFormaPago;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service.ServicioDatosBancariosFavoritos;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFacturaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioInmueble;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FacturaRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
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

public class ContratoEscrituraAction extends ActionBase {

	private static final long serialVersionUID = -6349047434597090432L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	private static final String TRAMITE_REGISTRO_ESCRITURA = "tramiteRegistroModelo6";
	private static final String ID_CONTRATO_REGISTRO = "TramiteRegistroMd6";
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private ServicioInmueble servicioInmueble;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioFacturaRegistro servicioFacturaRegistro;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioDatosBancariosFavoritos servicioDatosBancariosFavoritos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private TramiteRegistroDto tramiteRegistro;

	private TramiteRegistroDto tramiteRegistroJustificante;

	private BigDecimal numExpediente;

	private String nifBusqueda;

	private String tipoContratoRegistro;

	private InmuebleDto inmueble;

	private String nombreDoc;

	private String tipoDoc;

	private Long idInmueble;

	private String idFactura;

	private String idDatosBancarios;

	private String idRegistroFueraSecuencia;

	private String estadoAcusePendiente;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String ficheroDescarga;
	private String fileUploadFileName;
	private File fileUpload;
	private String idFicheroEliminar;
	private String idFicheroDescargar;

	public String inicio() {
		log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INICIO + "inicio.");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		if (tramiteRegistro == null) {
			tramiteRegistro = new TramiteRegistroDto();
		}
		tramiteRegistro.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		tramiteRegistro.setTipoTramite(TipoTramiteRegistro.MODELO_6.getValorEnum());
		tramiteRegistro.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		tramiteRegistro.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public String recuperar() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INICIO + "inicio.");
		if (null == numExpediente)
			numExpediente = tramiteRegistro.getIdTramiteRegistro();

		if (null == numExpediente) {
			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
			return TRAMITE_REGISTRO_ESCRITURA;
		}

		tramiteRegistro = servicioTramiteRegistro.getTramite(numExpediente);

		if (null != tramiteRegistroJustificante && null != tramiteRegistroJustificante.getIdTramiteRegistro()) {
			tramiteRegistroJustificante = servicioTramiteRegistro.getTramite(tramiteRegistroJustificante.getIdTramiteRegistro());
		} else {
			tramiteRegistroJustificante = servicioTramiteRegistro.getTramiteJustificante(String.valueOf(numExpediente));
		}

		return TRAMITE_REGISTRO_ESCRITURA;
	}

	// Boton guardar del formulario
	public String guardar() {
		ResultRegistro guardar;

		guardar = guardarEscritura(true);

		if (!guardar.isError()) {
			addActionMessage("Trámite guardado correctamente");
			recuperar();
		} else if (guardar.getValidaciones() != null && !guardar.getValidaciones().isEmpty()) {
			for (String validacion : guardar.getValidaciones()) {
				addActionError(validacion);
			}
			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
		} else if (guardar.isError()) {
			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
		}

		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public ResultRegistro guardarEscritura(boolean cambiarEstado) {
		ResultRegistro resultado = new ResultRegistro();
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		try {
			log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_INICIO + "guardarEscritura");
			if (cambiarEstado) {
				ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(
						EstadoTramiteRegistro.Iniciado.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuestaBean != null && respuestaBean.getError()) {
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO1_ERROR + "Ha ocurrido el siguiente error actualizando a 'Iniciado' el tramite con identificador: " + tramiteRegistro
							.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
				} else {
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
				}
			}

			ResultBean result = servicioTramiteRegistro.guardarTramiteRegistroEscritura(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());

			if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}

		} catch (Exception ex) {
			log.error(ex);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error: " + ex.getMessage());
		}

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
		if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
			tramiteRegistro.setFicherosSubidos(ficherosSubidos);
		}

		// Incluimos los inmuebles
		servicioTramiteRegistro.incluirInmuebles(tramiteRegistro);

		return resultado;
	}

	public String validarContrato() {
		ResultRegistro resultado;

		try {
			guardarEscritura(false);
			recuperar();
			resultado = servicioTramiteRegistro.validarRegistro(getTramiteRegistro());

			if (resultado != null && resultado.isError()) {
				if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
					for (String validacion : resultado.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(
						EstadoTramiteRegistro.Validado.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuestaBean != null && respuestaBean.getError()) {
					log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_ERROR + "Ha ocurrido el siguiente error actualizando a 'Validado' el tramite con identificador: " + tramiteRegistro
							.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
				} else {
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()));
				}
				guardarEscritura(false);
				addActionMessage("Escritura validada");
			}

		} catch (Exception e) {
			addActionError("Error al validar el contrato.");
		}
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public String consultarPersona() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		if (nifBusqueda != null && !nifBusqueda.equals("")) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nifBusqueda.toUpperCase(), tramiteRegistro.getNumColegiado());
			if (interviniente == null) {
				TramiteRegistroDto tramite = tramiteRegistro;
				recuperar();
				if (null == tramiteRegistro) {
					setTramiteRegistro(tramite);
					tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
				}
				PersonaDto persona = new PersonaDto();
				persona.setNif(nifBusqueda);
				tramiteRegistro.setSociedad(persona);
				addActionError("Cliente o destinatario no encontrado");
			} else {
				recuperar();
				tramiteRegistro.setSociedad(interviniente.getPersona());
				if (interviniente.getDireccion() != null) {
					tramiteRegistro.getSociedad().setDireccionDto(interviniente.getDireccion());
				}
			}
		} else {
			addActionError("Se debe de rellenar el nif");
			recuperar();
		}
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public String construirDpr() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INICIO + "construirDpr.");
		ResultRegistro resultRegistro;

		if (!utilesColegiado.tienePermisoEnvioDprRegistradores()) {
			addActionError("No tiene permiso para enviar a DPR");
			recuperar();
			return TRAMITE_REGISTRO_ESCRITURA;
		}

		// String entorno = gestorPropiedades.valorPropertie("Entorno");

		/*
		 * if (entorno != null && ("PREPRODUCCION".equals(entorno) || "TEST".equals(entorno))) { addActionError("No se puede enviar un trámite de registro desde los entornos de PRE o TEST"); return TRAMITE_REGISTRO_ESCRITURA; }
		 */

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
		tramiteRegistro.setFicherosSubidos(ficherosSubidos);
		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			addActionError("No se ha adjuntado ninguna documentación");
			recuperar();
			return TRAMITE_REGISTRO_ESCRITURA;
		}

		resultRegistro = servicioTramiteRegRbm.construirDpr(getTramiteRegistro().getIdTramiteRegistro().toString(), utilesColegiado.getAlias());

		if (resultRegistro.isError()) {
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
		} else {
			addActionMessage(resultRegistro.getMensaje());
		}

		recuperar();
		return TRAMITE_REGISTRO_ESCRITURA;

	}

	public String construirDprJustificantePago() {

		TramiteRegistroDto tramite = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());

		TramiteRegistroDto tramiteRegistroAux = tramite;
		tramiteRegistroAux.setIdTramiteRegistro(tramiteRegistroJustificante.getIdTramiteRegistro());
		tramiteRegistroAux.setIdTramiteCorpme(tramiteRegistroJustificante.getIdTramiteCorpme());
		tramiteRegistroAux.setIdentificacionCorpme(tramiteRegistroJustificante.getIdentificacionCorpme());
		tramiteRegistroAux.setFechaCreacion(tramiteRegistroJustificante.getFechaCreacion());
		tramiteRegistroAux.setEstado(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()));
		tramiteRegistroAux.setIdFirmaDoc(null);
		tramiteRegistroAux.setFechaEnvio(null);
		tramiteRegistroAux.setRespuesta(null);
		tramiteRegistroAux.setNEnvios(null);
		tramiteRegistroAux.setHoraEntradaReg(null);
		tramiteRegistroAux.setLibroReg(null);
		tramiteRegistroAux.setAnioReg(null);
		tramiteRegistroAux.setNumReg(null);
		tramiteRegistroAux.setLocalizadorReg(null);

		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURA_INICIO + "construirDpr.");
		ResultRegistro resultRegistro;

		if (!utilesColegiado.tienePermisoEnvioDprRegistradores()) {
			addActionError("No tiene permiso para enviar a DPR");
			recuperar();
			return TRAMITE_REGISTRO_ESCRITURA;
		}

		// String entorno = gestorPropiedades.valorPropertie("Entorno");

		/*
		 * if (entorno != null && ("PREPRODUCCION".equals(entorno) || "TEST".equals(entorno))) { addActionError("No se puede enviar un trámite de registro desde los entornos de PRE o TEST"); return TRAMITE_REGISTRO_ESCRITURA; }
		 */

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramite.getIdTramiteRegistro(), tramite.getTipoTramite());
		tramiteRegistroAux.setFicherosSubidos(ficherosSubidos);
		if (tramiteRegistroAux.getFicherosSubidos() == null || tramiteRegistroAux.getFicherosSubidos().isEmpty()) {
			addActionError("No se ha adjuntado ninguna documentación");
			recuperar();
			return TRAMITE_REGISTRO_ESCRITURA;
		}

		resultRegistro = servicioTramiteRegRbm.construirDprFacturaEscritura(tramiteRegistroAux, utilesColegiado.getAlias());

		if (resultRegistro.isError()) {
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
		} else {
			addActionMessage(resultRegistro.getMensaje());
		}

		recuperar();
		return TRAMITE_REGISTRO_ESCRITURA;

	}

	public String construirFirmarAcuse() {
		log.info("Escritura firmar acuse.");

		ResultRegistro resultRegistro;

		resultRegistro = servicioTramiteRegistro.construirAcuse(tramiteRegistro.getIdTramiteRegistro().toString(), utilesColegiado.getAlias(), estadoAcusePendiente, idRegistroFueraSecuencia);

		if (resultRegistro.isError()) {
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
		} else {
			addActionMessage(resultRegistro.getMensaje());
		}

		recuperar();
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	// ----------------------------------------------Pestania inmueble------------------------------------------------------------------------

	public String eliminarInmueble() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminar inmueble.");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		servicioInmueble.eliminarInmueble(idInmueble);

		addActionMessage("Inmueble eliminado");
		tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		ResultBean respuesta = servicioTramiteRegistro.guardarTramiteRegistroEscritura(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (respuesta.getError()) {
			addActionError("No se ha modificado el estado el trámite: " + respuesta.getMensaje());
		} else {
			tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		}
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "eliminar.");
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public String prepararModificacionInmueble() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "modificación inmueble.");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		inmueble = servicioInmueble.getInmuebleDto(idInmueble);
		if (inmueble != null && inmueble.getBien() != null && inmueble.getBien().getTipoInmueble() != null) {
			inmueble.getBien().setTipoBien(inmueble.getBien().getTipoInmueble().getIdTipoBien());
		}
		addActionMessage("Inmueble cargado");
		tramiteRegistro = servicioTramiteRegistro.getTramite(tramiteRegistro.getIdTramiteRegistro());
		tramiteRegistro.setInmueble(inmueble);

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + "modificación nombramiendo.");
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	// ----------------------------------------------Fin Pestania inmueble--------------------------------------------------------------------

	// ---------------------------------------------Bloque Documentos ------------------------------------------------------------------------

	// Para añadir ficheros a la lista desde el botón de la pestaña documentación
	public String incluirFichero() throws Exception, OegamExcepcion {
		String cadenaIncluirFichero = "incluirFichero.";
		guardarEscritura(true);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + cadenaIncluirFichero);
		String returnStruts = "";
		try {
			returnStruts = TRAMITE_REGISTRO_ESCRITURA;
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
						log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_ERROR + ex);
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
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.ESCRITURAS);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.ESCRITURAS_DOCUMENTACION_ENVIADA);
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
		guardarEscritura(true);
		try {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminarFichero.");
			returnStruts = TRAMITE_REGISTRO_ESCRITURA;
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
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public String descargarAdjuntos() throws OegamExcepcion, IOException {

		boolean descargar = false;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(baos);

		FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.ESCRITURAS, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades
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
		return TRAMITE_REGISTRO_ESCRITURA;

	}
	// ---------------------------------------------Fin Bloque Documentos ------------------------------------------------------------------------------

	// ---------------------------------------------Bloque Justificante de pago ------------------------------------------------------------------------

	// Para añadir justificantes de pago a la lista desde el botón de la pestaña documentación
	public String incluirJustificante() throws Exception, OegamExcepcion {
		String cadenaIncluirFichero = "incluirFichero.";

		ResultRegistro resultado = new ResultRegistro();
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);

		if (tramiteRegistroJustificante == null) {
			tramiteRegistroJustificante = new TramiteRegistroDto();
		}
		tramiteRegistroJustificante.setNumColegiado(tramiteRegistro.getNumColegiado());
		tramiteRegistroJustificante.setTipoTramite(TipoTramiteRegistro.MODELO_6.getValorEnum());
		tramiteRegistroJustificante.setIdContrato(tramiteRegistro.getIdContrato());
		tramiteRegistroJustificante.setIdTramiteOrigen(tramiteRegistro.getIdTramiteRegistro());

		try {
			log.info(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_INICIO + "guardarEscritura");

			tramiteRegistroJustificante.setEstado(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()));

			ResultBean result = servicioTramiteRegistro.guardarTramiteRegistroEscritura(tramiteRegistroJustificante, utilesColegiado.getIdUsuarioSessionBigDecimal());

			if (result != null && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				resultado.setError(true);
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}

		} catch (Exception ex) {
			log.error(ex);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error: " + ex.getMessage());
		}

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + cadenaIncluirFichero);
		String returnStruts = "";
		try {
			returnStruts = TRAMITE_REGISTRO_ESCRITURA;
			ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistroJustificante.getIdTramiteRegistro(), tramiteRegistroJustificante.getTipoTramite());
			if (ficherosSubidos == null) {
				ficherosSubidos = new ArrayList<FicheroInfo>();
			}
			if (fileUpload != null) {
				// Verifica que no se ha alcanzado el máximo de ficheros permitidos:
				int maxFicheros = Integer.parseInt(gestorPropiedades.valorPropertie("envioEscrituras.numero.maximo.ficheros"));
				if (ficherosSubidos.size() == maxFicheros) {
					addActionError("Alcanzado el máximo de ficheros a incluir en el envío de documentación: " + maxFicheros);
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					tramiteRegistroJustificante = servicioTramiteRegistro.getTramite(tramiteRegistroJustificante.getIdTramiteRegistro());
					return returnStruts;
				}
				
				// Verifica que el nombre del fichero no contenga caracteres especiales
				if (!utiles.esNombreFicheroValido(fileUploadFileName)) {
					addActionError("El fichero añadido debe tener un nombre correcto");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					tramiteRegistroJustificante = servicioTramiteRegistro.getTramite(tramiteRegistroJustificante.getIdTramiteRegistro());
					return returnStruts;
				}
				
				// Verifica que el fichero subido tiene un formato soportado
				String extension = utiles.dameExtension(fileUploadFileName, false);
				if (extension == null) {
					addActionError("El fichero añadido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .xls, .xml, .jpg, .jpeg o .zip");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					tramiteRegistroJustificante = servicioTramiteRegistro.getTramite(tramiteRegistroJustificante.getIdTramiteRegistro());
					return returnStruts;
				}
				// PCKS7, PDF , RTF, DOC, TIF, ASC, XLS, XML,JPG, JPEG y ZIP
				if (extension.equalsIgnoreCase("pkcs7") || extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("rtf") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase(
						"tif") || extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xml") || extension.equalsIgnoreCase("jpg") || extension
								.equalsIgnoreCase("jpeg")) {
					try {
						File fichero = guardarJustificante(extension);
						FicheroInfo ficheroInfo = new FicheroInfo(fichero, ficherosSubidos.size());
						ficherosSubidos.add(ficheroInfo);
						addActionMessage("Fichero añadido correctamente");
						tramiteRegistroJustificante.setFicherosSubidos(ficherosSubidos);
						tramiteRegistroJustificante.setExtensionFicheroEnviado(ficheroInfo.getExtension());
						if (tramiteRegistroJustificante.getFicheroSubido() == null) {
							tramiteRegistroJustificante.setFicheroSubido("si");
							servicioTramiteRegistro.actualizarFicheroSubido(tramiteRegistroJustificante.getIdTramiteRegistro());
						}
						log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + cadenaIncluirFichero);
					} catch (Exception ex) {
						log.error(Claves.CLAVE_LOG_ENVIO_ESCRITURAS_ERROR + ex);
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

	private File guardarJustificante(String extension) throws Exception, OegamExcepcion {
		Long idFichero = new Date().getTime();
		FileInputStream fis = new FileInputStream(fileUpload);
		byte[] array = IOUtils.toByteArray(fis);
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.ESCRITURAS);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.ESCRITURAS_DOCUMENTACION_ENVIADA);
		ficheroBean.setExtension("." + extension);
		ficheroBean.setFicheroByte(array);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteRegistroJustificante.getIdTramiteRegistro()));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento("fichero_registro_" + idFichero + "_" + tramiteRegistroJustificante.getIdTramiteRegistro());
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}

	public String eliminarJustificante() {
		String returnStruts = "";
		try {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminarFichero.");
			returnStruts = TRAMITE_REGISTRO_ESCRITURA;
			if (tramiteRegistroJustificante == null) {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " No se ha recuperado tramite en curso de la sesion.");
				return returnStruts;
			}

			// Si está 'finalizado con error' lo pone en 'iniciado':
			ArrayList<FicheroInfo> ficheros = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistroJustificante.getIdTramiteRegistro(), tramiteRegistroJustificante.getTipoTramite());
			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(getIdFicheroEliminar())) {
						// Borra fisicamente el fichero del disco:
						tmp.getFichero().delete();
						it.remove();
						tramiteRegistroJustificante.setFicherosSubidos(ficheros);
						if (ficheros.isEmpty()) {
							tramiteRegistroJustificante.setFicheroSubido("no");
							servicioTramiteRegistro.actualizarFicheroSubido(tramiteRegistroJustificante.getIdTramiteRegistro());
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

	// Descarga el justificante seleccionado
	public String descargarJustificante() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "descargarJustificante.");
		String returnStruts = TRAMITE_REGISTRO_ESCRITURA;
		try {
			ArrayList<FicheroInfo> ficheros = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistroJustificante.getIdTramiteRegistro(), tramiteRegistroJustificante.getTipoTramite());

			if (ficheros != null && !ficheros.isEmpty()) {
				tramiteRegistroJustificante.setFicherosSubidos(ficheros);
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(idFicheroDescargar)) {
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
			addActionError(ex.getMessage());
		}
		recuperar();
		return returnStruts;
	}

	// ---------------------------------------------Fin Bloque justificantes de pago ------------------------------------------------------------------------------
	// ---------------------------------------------Bloque facturas (forma de pago)---------------------------------------------------------------------

	public String borrarFactura() {

		ResultRegistro resultado = new ResultRegistro();

		if (StringUtils.isNotBlank(getIdFactura())) {
			try {
				resultado = servicioFacturaRegistro.borrarFacturaRegistro(Long.parseLong(getIdFactura()));
			} catch (Exception e) {
				resultado.setMensaje("Error al borrar factura");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}
		}
		recuperar();
		return TRAMITE_REGISTRO_ESCRITURA;
	}

	public String modificarFactura() {

		ResultRegistro resultado = new ResultRegistro();

		tramiteRegistro.setFacturaRegistro(null);

		if (StringUtils.isNotBlank(getIdFactura())) {

			try {
				resultado = servicioFacturaRegistro.getFacturaRegistro(Long.parseLong(getIdFactura()));
				tramiteRegistro.setFacturaRegistro((FacturaRegistroDto) resultado.getObj());

				tramiteRegistro.setFacturasRegistro(servicioFacturaRegistro.getFacturasPorTramite(tramiteRegistro.getIdTramiteRegistro()));

			} catch (Exception e) {
				resultado.setMensaje("Error al obtener la factura");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return TRAMITE_REGISTRO_ESCRITURA;
	}
	// ---------------------------------------------Fin bloque facturas (forma de pago)-----------------------------------------------------------------

	public String cargarDatosBancarios() {

		if (StringUtils.isNotBlank(idDatosBancarios)) {
			ResultBean resultDatosBancarios = servicioDatosBancariosFavoritos.getDatoBancarioRegistradoresAsterisco(Long.parseLong(idDatosBancarios));
			if (!resultDatosBancarios.getError()) {
				recuperar();
				getTramiteRegistro().setDatosBancarios((DatosBancariosFavoritosDto) resultDatosBancarios.getAttachment("datosBancariosDto"));
				getTramiteRegistro().getDatosBancarios().setTipoDatoBancario(TipoCuentaBancaria.EXISTENTE.getValorEnum());
				getTramiteRegistro().setFormaPago(new BigDecimal(TipoFormaPago.CUENTA.getValorEnum()));
			} else {
				addActionError(resultDatosBancarios.getListaMensajes().get(0));
			}
		}

		return TRAMITE_REGISTRO_ESCRITURA;
	}

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

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public String getTipoContratoRegistro() {
		return tipoContratoRegistro;
	}

	public void setTipoContratoRegistro(String tipoContratoRegistro) {
		this.tipoContratoRegistro = tipoContratoRegistro;
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

	public InmuebleDto getInmueble() {
		return inmueble;
	}

	public void setInmueble(InmuebleDto inmueble) {
		this.inmueble = inmueble;
	}

	public Long getIdInmueble() {
		return idInmueble;
	}

	public void setIdInmueble(Long idInmueble) {
		this.idInmueble = idInmueble;
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

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public String getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(String idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	public TramiteRegistroDto getTramiteRegistroJustificante() {
		if (null == tramiteRegistroJustificante) {
			tramiteRegistroJustificante = new TramiteRegistroDto();
		}
		if (!UtilesValidaciones.validarObligatoriedad(tramiteRegistroJustificante.getEstado())) {
			tramiteRegistroJustificante.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
		}
		return tramiteRegistroJustificante;
	}

	public void setTramiteRegistroJustificante(TramiteRegistroDto tramiteRegistroJustificante) {
		this.tramiteRegistroJustificante = tramiteRegistroJustificante;
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
}