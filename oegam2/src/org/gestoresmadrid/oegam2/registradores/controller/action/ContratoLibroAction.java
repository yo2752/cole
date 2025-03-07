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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.LibroRegistroVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioLibroRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.LibroRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.utiles.ConstantesPDF;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class ContratoLibroAction extends ActionBase {

	private static final long serialVersionUID = -6349047434597090432L;

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	private static final String TRAMITE_REGISTRO_LIBRO = "tramiteRegistroLibro";
	private static final String ID_CONTRATO_REGISTRO = "Libro";
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private ServicioLibroRegistro servicioLibroRegistro;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioRegistro servicioRegistro;

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private TramiteRegistroDto tramiteRegistro;

	private BigDecimal numExpediente;

	private boolean rmFirmar;

	private String tipoContratoRegistro;

	private String identificador;

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

	public String alta() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		if (tramiteRegistro == null) {
			tramiteRegistro = new TramiteRegistroDto();
		}
		tramiteRegistro.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		tramiteRegistro.setTipoTramite(TipoTramiteRegistro.MODELO_12.getValorEnum());
		tramiteRegistro.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		tramiteRegistro.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
		return TRAMITE_REGISTRO_LIBRO;
	}

	// Botón guardar del formulario
	public String guardar() {
		ResultRegistro guardar;
		guardar = guardarContrato(true);

		if (null != guardar && guardar.isError()) {
			if (null != tramiteRegistro && null != tramiteRegistro.getIdTramiteRegistro()) {
				List<LibroRegistroDto> librosRegistro = servicioLibroRegistro.getLibrosRegistro(tramiteRegistro.getIdTramiteRegistro());
				if (null != librosRegistro && !librosRegistro.isEmpty()) {
					tramiteRegistro.setLibrosRegistro(librosRegistro);
				}
			}
			if (guardar.getValidaciones() != null && !guardar.getValidaciones().isEmpty()) {
				for (String validacion : guardar.getValidaciones()) {
					addActionError(validacion);
				}
			} else if (StringUtils.isNotBlank(guardar.getMensaje())){
				addActionError(guardar.getMensaje());
			}
			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
		} else {
			addActionMessage("Trámite guardado correctamente");
			recuperar();

		}

		return TRAMITE_REGISTRO_LIBRO;
	}

	public ResultRegistro guardarContrato(boolean cambiarEstado) {
		ResultRegistro resultado = new ResultRegistro();

		try {

			ResultBean result = servicioTramiteRegistro.guardarTramiteRegistroLibroCuenta(tramiteRegistro, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (result.getError() && result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				resultado.setError(true);
				for (String mensaje : result.getListaMensajes()) {
					addActionError(mensaje);
				}

				if (null != tramiteRegistro && null != tramiteRegistro.getIdTramiteRegistro()) {
					List<LibroRegistroDto> librosRegistro = servicioLibroRegistro.getLibrosRegistro(tramiteRegistro.getIdTramiteRegistro());
					if (null != librosRegistro && !librosRegistro.isEmpty()) {
						tramiteRegistro.setLibrosRegistro(librosRegistro);
					}
				}
				tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));

				ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
				if (null != ficherosSubidos && !ficherosSubidos.isEmpty()) {
					tramiteRegistro.setFicherosSubidos(ficherosSubidos);
				}

			}

			if (cambiarEstado) {
				ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(
						EstadoTramiteRegistro.Iniciado.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuestaBean != null && respuestaBean.getError()) {
					log.error(Claves.CLAVE_LOG_MODELO_REGISTRADORES_ERROR + "Ha ocurrido el siguiente error actualizando a 'Iniciado' el tramite con identificador: " + tramiteRegistro
							.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
				} else {
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
				}
			}

		} catch (Exception ex) {
			log.error(ex);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error: " + ex.getMessage());
		}

		return resultado;
	}

	public String recuperar() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO12_INICIO + "inicio.");
		if (null == numExpediente)
			numExpediente = tramiteRegistro.getIdTramiteRegistro();

		if (null == numExpediente) {
			tramiteRegistro.setPresentador(servicioPersona.obtenerColegiadoCompleto(tramiteRegistro.getNumColegiado(), tramiteRegistro.getIdContrato()));
			return TRAMITE_REGISTRO_LIBRO;
		}
		tramiteRegistro = servicioTramiteRegistro.getTramite(numExpediente);
		return TRAMITE_REGISTRO_LIBRO;
	}

	public String validarTramite() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		ResultRegistro resultado = new ResultRegistro();

		try {
			guardarContrato(false);
			recuperar();

			resultado = servicioTramiteRegistro.validarRegistro(tramiteRegistro);

			if (!resultado.isError()) {
				ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(
						EstadoTramiteRegistro.Validado.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuestaBean != null && respuestaBean.getError()) {
					log.error(Claves.CLAVE_LOG_REGISTRADORES_MODELO11_ERROR + "Ha ocurrido el siguiente error actualizando a 'Validado' el tramite con identificador: " + tramiteRegistro
							.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
				} else {
					tramiteRegistro.setEstado(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()));
				}

				addActionMessage("Trámite validado");
				guardarContrato(false);
				recuperar();
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
		return TRAMITE_REGISTRO_LIBRO;
	}

	public String construirDpr() {
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_MODELO12_INICIO + "construirDpr.");
		ResultRegistro resultRegistro;

		if (!utilesColegiado.tienePermisoEnvioDprRegistradores()) {
			addActionError("No tiene permiso para enviar a DPR");
			recuperar();
			return TRAMITE_REGISTRO_LIBRO;
		}
		// String entorno = gestorPropiedades.valorPropertie("Entorno");

		/*
		 * if (entorno != null && ("PREPRODUCCION".equals(entorno) || "TEST".equals(entorno))) { addActionError("No se puede enviar un trámite de registro desde los entornos de PRE o TEST"); return TRAMITE_REGISTRO_LIBRO; }
		 */

		ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getTipoTramite());
		tramiteRegistro.setFicherosSubidos(ficherosSubidos);
		if (tramiteRegistro.getFicherosSubidos() == null || tramiteRegistro.getFicherosSubidos().isEmpty()) {
			addActionError("No se ha adjuntado ninguna documentación");
			recuperar();
			return TRAMITE_REGISTRO_LIBRO;
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
		return TRAMITE_REGISTRO_LIBRO;
	}

	public String construirFirmarAcuse() {
		log.info("Libro firmar acuse.");

		ResultRegistro resultRegistro;

		resultRegistro = servicioTramiteRegistro.construirAcuse(getTramiteRegistro().getIdTramiteRegistro().toString(), utilesColegiado.getAlias(), estadoAcusePendiente, idRegistroFueraSecuencia);

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
		return TRAMITE_REGISTRO_LIBRO;
	}

	public String subsanar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " subsanar");

		ResultRegistro resultRegistro;

		recuperar();

		if (!EstadoTramiteRegistro.Calificado_Defectos.getValorEnum().equals(getTramiteRegistro().getEstado().toString()) && !EstadoTramiteRegistro.Inscrito_Parcialmente.getValorEnum().equals(
				getTramiteRegistro().getEstado().toString())) {
			addActionError("El estado actual: " + EstadoTramiteRegistro.convertirTexto(getTramiteRegistro().getEstado().toString()) + " no permite la subsanación del trámite de id: "
					+ getTramiteRegistro().getIdTramiteRegistro());
			return TRAMITE_REGISTRO_LIBRO;
		}

		BigDecimal idTramiteRegistro = getTramiteRegistro().getIdTramiteRegistro();
		getTramiteRegistro().setSubsanacion("S");
		guardarContrato(false);

		servicioTramiteRegistro.incluirDatosSubsanacion(getTramiteRegistro());
		servicioTramiteRegistro.inicializarParametros(getTramiteRegistro());

		tramiteRegistro.setSociedad(new PersonaDto());
		tramiteRegistro.getSociedad().setDireccionDto(new DireccionDto());
		tramiteRegistro.setRegistro(new RegistroDto());
		tramiteRegistro.setIdRegistro(null);
		tramiteRegistro.setAplicarIrpf(null);
		tramiteRegistro.setPorcentajeIrpf(null);
		tramiteRegistro.setTipoDestinatario(null);

		resultRegistro = guardarContrato(true);

		if (resultRegistro != null && !resultRegistro.isError()) {
			addActionMessage("Subsanado correctamente. El trámite subsanado tiene el número de expediente: " + getTramiteRegistro().getIdTramiteRegistro());
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Se ha subsanado el tramite con id: " + idTramiteRegistro + " .Id del subsanado: " + getTramiteRegistro().getIdTramiteRegistro());
		} else {
			addActionError("Error subsanando el trámite: " + idTramiteRegistro);
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
			getTramiteRegistro().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegistro().getNumColegiado(), getTramiteRegistro().getIdContrato()));
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " Error subsanando el tramite con id: " + idTramiteRegistro + " .Error: " + resultRegistro.getMensaje());
		}

		tramiteRegistro.setIdTramiteRegistro(idTramiteRegistro);
		recuperar();
		return TRAMITE_REGISTRO_LIBRO;
	}

	public String duplicar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " duplicar");

		ResultRegistro resultRegistro;

		recuperar();

		if (!EstadoTramiteRegistro.Confirmada_Denegacion.getValorEnum().equals(getTramiteRegistro().getEstado().toString())) {
			addActionError("El estado actual: " + EstadoTramiteRegistro.convertirTexto(getTramiteRegistro().getEstado().toString()) + " no permite el duplicado del trámite de id: "
					+ getTramiteRegistro().getIdTramiteRegistro());
			return TRAMITE_REGISTRO_LIBRO;
		}

		BigDecimal idTramiteRegistro = getTramiteRegistro().getIdTramiteRegistro();

		servicioTramiteRegistro.inicializarParametros(getTramiteRegistro());

		tramiteRegistro.setSociedad(new PersonaDto());
		tramiteRegistro.getSociedad().setDireccionDto(new DireccionDto());
		tramiteRegistro.setRegistro(new RegistroDto());
		tramiteRegistro.setIdRegistro(null);
		tramiteRegistro.setAplicarIrpf(null);
		tramiteRegistro.setPorcentajeIrpf(null);
		tramiteRegistro.setTipoDestinatario(null);

		resultRegistro = guardarContrato(true);

		if (resultRegistro != null) {
			if (!resultRegistro.isError()) {
				addActionMessage("Duplicado correctamente. El trámite duplicado tiene el número de expediente: " + tramiteRegistro.getIdTramiteRegistro());
				log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Se ha duplicado el tramite con id: " + idTramiteRegistro + " .Id del duplicado: " + tramiteRegistro.getIdTramiteRegistro());
			} else {
				addActionError("Error duplicando el trámite: " + idTramiteRegistro);
				if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
					for (String validacion : resultRegistro.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultRegistro.getMensaje());
				}
				getTramiteRegistro().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegistro().getNumColegiado(), getTramiteRegistro().getIdContrato()));
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " Error duplicando el tramite con id: " + idTramiteRegistro + " .Error: " + resultRegistro.getMensaje());
			}
		}
		tramiteRegistro.setIdTramiteRegistro(idTramiteRegistro);
		recuperar();
		return TRAMITE_REGISTRO_LIBRO;
	}
	// ----------------------------------------------Bloque Libros ------------------------------------------------------------------------

	public String modificar() {

		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		recuperar();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioLibroRegistro.getLibroRegistro(getIdentificador());
				tramiteRegistro.setLibroRegistro((LibroRegistroDto) resultado.getObj());

				if (null != tramiteRegistro.getLibroRegistro().getFecApertura()) {
					tramiteRegistro.getLibroRegistro().setFecAperturaLibro(utilesFecha.getFechaConDate(tramiteRegistro.getLibroRegistro().getFecApertura()));
				}

				if (null != tramiteRegistro.getLibroRegistro().getFecCierre()) {
					tramiteRegistro.getLibroRegistro().setFecCierreLibro(utilesFecha.getFechaConDate(tramiteRegistro.getLibroRegistro().getFecCierre()));
				}

				if (null != tramiteRegistro.getLibroRegistro().getFecCierreAnterior()) {
					tramiteRegistro.getLibroRegistro().setFecCierreAnteriorLibro(utilesFecha.getFechaConDate(tramiteRegistro.getLibroRegistro().getFecCierreAnterior()));
				}

			} catch (Exception e) {
				resultado.setMensaje("Error al obtener los datos del Libro");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return TRAMITE_REGISTRO_LIBRO;
	}

	public String borrar() {
		ResultRegistro resultObject;

		guardarContrato(true);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			// Borramos el libro
			resultObject = servicioLibroRegistro.borrarLibroRegistro(getIdentificador());
			if (resultObject.isError()) {
				addActionError(resultObject.getMensaje());
			} else {
				addActionMessage("Libro borrado correctamente");
			}

		}
		recuperar();

		return TRAMITE_REGISTRO_LIBRO;
	}
	// -------------------------------------------------------------------------------------------------------------------------------------------

	// ---------------------------------------------Bloque Documentos ------------------------------------------------------------------------

	// Para añadir ficheros a la lista desde el botón de la pestaña documentación
	public String incluirFichero() throws Exception, OegamExcepcion {
		String cadenaIncluirFichero = "incluirFichero.";
		String returnStruts = "";
		ResultRegistro resultado;

		// Previamente guardamos el contrato si es el primer fichero que se añade
		if (null == tramiteRegistro.getIdTramiteRegistro()) {
			guardarContrato(true);
			recuperar();
		}

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + cadenaIncluirFichero);

		try {
			returnStruts = TRAMITE_REGISTRO_LIBRO;
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
					recuperar();
					return returnStruts;
				}
				// Verifica que el nombre del fichero no contenga caracteres especiales
				if (!utiles.esNombreFicheroValido(fileUploadFileName)) {
					addActionError("El fichero añadido debe tener un nombre correcto");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperar();
					return returnStruts;
				}
				// Verifica que el fichero subido tiene un formato soportado
				String extension = utiles.dameExtension(fileUploadFileName, false);
				if (extension == null) {
					addActionError("El fichero añadido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .xls, .xml, .jpg, .jpeg o .zip");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperar();
					return returnStruts;
				}

				// Se comprueba que sólo haya un ZIP por trámite
				if ("zip".equalsIgnoreCase(extension)) {
					for (FicheroInfo ficheros : ficherosSubidos) {
						if (extension.equalsIgnoreCase(ficheros.getExtension())) {
							addActionError("Sólo se puede añadir un fichero tipo ZIP por trámite.");
							log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
							recuperar();
							return returnStruts;
						}
					}
				}

				// Si se ha añadido el ZIP de legalización de libros se valida que los datos de los ficheros comprimidos son correctos.
				if ("zip".equalsIgnoreCase(extension)) {
					resultado = servicioTramiteRegistro.validarZIPLibros(tramiteRegistro.getIdTramiteRegistro().toString(), fileUpload);
					if (resultado.isError()) {
						if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
							for (String validacion : resultado.getValidaciones()) {
								addActionError(validacion);
							}
						} else {
							addActionError(resultado.getMensaje());
						}
						log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
						recuperar();
						return returnStruts;
					}
				}

				// PCKS7, PDF , RTF, DOC, TIF, ASC, XLS, XML,JPG, JPEG y ZIP
				if (extension.equalsIgnoreCase("pkcs7") || extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("rtf") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase(
						"tif") || extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xml") || extension.equalsIgnoreCase("jpg") || extension
								.equalsIgnoreCase("jpeg")) {
					try {
						if ("zip".equalsIgnoreCase(extension)) {
							resultado = volcarDatos();
							if (resultado.isError()) {
								log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
								recuperar();
								return returnStruts;
							}
						}
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
						addActionError("Se ha lanzado una excepción Oegam relacionada con el fichero de propiedades.");
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
			addActionError("Se ha lanzado una excepción Oegam relacionada con el fichero de propiedades.");
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
		ficheroBean.setNombreDocumento(fileUploadFileName.substring(0, fileUploadFileName.length() - 4).toUpperCase() + "_" + idFichero + "_" + tramiteRegistro.getIdTramiteRegistro());
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}

	public String eliminarFichero() {
		String returnStruts = "";

		// Previamente guardamos el contrato
		guardarContrato(true);
		recuperar();

		try {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminarFichero.");
			returnStruts = TRAMITE_REGISTRO_LIBRO;
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
						String extension = utiles.dameExtension(getIdFicheroEliminar(), false);
						// Si el fichero que borramos es el ZIP, borramos tambien todos los datos del trámite.
						if ("zip".equalsIgnoreCase(extension)) {
							tramiteRegistro.setSociedad(new PersonaDto());
							tramiteRegistro.getSociedad().setDireccionDto(new DireccionDto());
							tramiteRegistro.setRegistro(new RegistroDto());
							tramiteRegistro.setFechaDocumento(null);
							tramiteRegistro.setAplicarIrpf(null);
							tramiteRegistro.setPorcentajeIrpf(null);
							tramiteRegistro.setIdRegistro(null);
							tramiteRegistro.setRegistroSeleccionadoOculto(null);
							tramiteRegistro.setTipoDestinatario(null);

							if (null != tramiteRegistro.getLibrosRegistro() && !tramiteRegistro.getLibrosRegistro().isEmpty()) {
								for (LibroRegistroDto elemento : tramiteRegistro.getLibrosRegistro()) {
									ResultRegistro resultObject = servicioLibroRegistro.borrarLibroRegistro(Long.toString(elemento.getIdLibro()));
									if (resultObject.isError()) {
										addActionMessage(resultObject.getMensaje());
										TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
										return returnStruts;
									}
								}
							}
							guardarContrato(true);
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
		return TRAMITE_REGISTRO_LIBRO;
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
		return TRAMITE_REGISTRO_LIBRO;

	}

	// Extrae del zip los datos de los documentos y los guarda en el trámite
	public ResultRegistro volcarDatos() {
		ResultRegistro result = new ResultRegistro();
		String cadenaVolcarDatos = "Volcar datos libros.";
		String formatoFecha = "ddMMyyyy";
		String municipioNombre = null;

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + cadenaVolcarDatos);

		List<String> listaFicheroDatos = null;
		List<String> listaFicheroNombres = null;
		try {

			File ficheroDatos = servicioTramiteRegistro.extraerFicheroZipFile(fileUpload, "DATOS.TXT");
			File ficheroNombres = servicioTramiteRegistro.extraerFicheroZipFile(fileUpload, "NOMBRES.TXT");

			listaFicheroDatos = utiles.obtenerLineasFicheroTexto(ficheroDatos);
			listaFicheroNombres = utiles.obtenerLineasFicheroTexto(ficheroNombres);

			int numeroLibros = 0;

			tramiteRegistro.setSociedad(new PersonaDto());
			tramiteRegistro.getSociedad().setDireccionDto(new DireccionDto());
			tramiteRegistro.setRegistro(new RegistroDto());
			tramiteRegistro.setLibrosRegistro(new ArrayList<LibroRegistroDto>());
			for (String linea : listaFicheroDatos) {
				switch (linea.trim().substring(0, 3)) {

					case "100": // Código de registro
						RegistroDto registro = servicioRegistro.getRegistroPorNombre(linea.substring(3), "RM");
						if (null != registro) {
							tramiteRegistro.getRegistro().setIdProvincia(registro.getIdProvincia());
							tramiteRegistro.getRegistro().setNombre(registro.getNombre());
							tramiteRegistro.getRegistro().setIdRegistro(registro.getIdRegistro());
							tramiteRegistro.getRegistro().setId(registro.getId());
							tramiteRegistro.setIdRegistro(Long.toString(registro.getId()));
						}

						break;

					case "101": // Fecha de documento
						tramiteRegistro.setFechaDocumento(new Fecha(utilesFecha.formatoFecha(formatoFecha, linea.substring(3))));
						break;

					case "102": // Razón social de sociedad y depositante
						tramiteRegistro.getSociedad().setApellido1RazonSocial(linea.substring(3).trim());
						break;

					case "105": // Nif sociedad y depositante
						tramiteRegistro.getSociedad().setNif(linea.substring(3).trim());
						break;

					case "106": // Nombre vía Sociedad (Datos facturación)
						tramiteRegistro.getSociedad().getDireccionDto().setNombreVia(linea.substring(3).trim());
						// Tipo de vía "CALLE"
						tramiteRegistro.getSociedad().getDireccionDto().setIdTipoVia("CALLE");
						// País "ES"
						tramiteRegistro.getSociedad().getDireccionDto().setPais("ES");
						// Número de vía
						tramiteRegistro.getSociedad().getDireccionDto().setNumero("0");
						break;

					case "107": // Municipio Sociedad (Datos facturación)
						municipioNombre = servicioTramiteRegistro.cleanString(linea.substring(3).trim());
						break;

					case "108": // Código postal Sociedad (Datos facturación)
						tramiteRegistro.getSociedad().getDireccionDto().setCodPostal(linea.substring(3).trim());
						break;

					case "109": // Provincia Sociedad (Datos facturación)
						tramiteRegistro.getSociedad().getDireccionDto().setIdProvincia(StringUtils.leftPad(linea.substring(3).trim(), 2, "0"));
						MunicipioDto municipio = servicioMunicipio.getMunicipioPorNombre(municipioNombre, tramiteRegistro.getSociedad().getDireccionDto().getIdProvincia());
						if (null != municipio) {
							tramiteRegistro.getSociedad().getDireccionDto().setIdMunicipio(municipio.getIdMunicipio());
						} else {
							tramiteRegistro.getSociedad().getDireccionDto().setIdMunicipio("0");
						}
						break;
					// Se elimina campo 203 según petición del cliente
					// case "203": // Sección de la sociedad
					// if (StringUtils.isNotBlank(linea.substring(3))) {
					// tramiteRegistro.getSociedad().setSeccion(new BigDecimal(linea.substring(3).trim()));
					// }
					// break;

					case "206": // Hoja de la sociedad
						if (StringUtils.isNotBlank(linea.substring(3))) {
							tramiteRegistro.getSociedad().setHoja(new BigDecimal(linea.substring(3).trim()));
						}
						break;

					case "401":
						if ("SI".equalsIgnoreCase(linea.substring(3))) {
							tramiteRegistro.setAplicarIrpf("S");
						} else {
							tramiteRegistro.setAplicarIrpf("N");
						}
						break;

					case "501":
						numeroLibros = Integer.parseInt(linea.substring(3));
						break;

					default:
						break;
				}
			}

			LibroRegistroVO libro;

			for (int i = 1; i <= numeroLibros; i++) {
				libro = new LibroRegistroVO();
				for (String linea : listaFicheroDatos) {
					if (linea.trim().startsWith("0")) {

						if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "01")) {
							libro.setNombreLibro(linea.trim().substring(5));
						} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "02")) {
							libro.setNumero(new BigDecimal(linea.trim().substring(5)));
						} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "03")) {
							libro.setFecApertura(utilesFecha.formatoFecha(formatoFecha, linea.substring(5)));
						} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "04")) {
							libro.setFecCierre(utilesFecha.formatoFecha(formatoFecha, linea.substring(5)));
						} else if (linea.trim().substring(0, 5).startsWith(StringUtils.leftPad(String.valueOf(i), 3, "0") + "05")) {
							libro.setFecCierreAnterior(utilesFecha.formatoFecha(formatoFecha, linea.substring(5)));
						}
					}
					libro.setNombreFichero(listaFicheroNombres.get(i - 1));
				}
				result = servicioLibroRegistro.guardarOActualizarLibroRegistro(libro, tramiteRegistro.getIdTramiteRegistro());
			}

		} catch (Throwable ex) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + ex);
			addActionError("Se ha lanzado una excepción Oegam relacionada con el volcado de datos de Libros.");
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaVolcarDatos);
			return result;
		}

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + cadenaVolcarDatos);

		result = guardarContrato(true);

		return result;
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

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the idRegistroFueraSecuencia
	 */
	public String getIdRegistroFueraSecuencia() {
		return idRegistroFueraSecuencia;
	}

	/**
	 * @param idRegistroFueraSecuencia the idRegistroFueraSecuencia to set
	 */
	public void setIdRegistroFueraSecuencia(String idRegistroFueraSecuencia) {
		this.idRegistroFueraSecuencia = idRegistroFueraSecuencia;
	}

	/**
	 * @return the estadoAcusePendiente
	 */
	public String getEstadoAcusePendiente() {
		return estadoAcusePendiente;
	}

	/**
	 * @param estadoAcusePendiente the estadoAcusePendiente to set
	 */
	public void setEstadoAcusePendiente(String estadoAcusePendiente) {
		this.estadoAcusePendiente = estadoAcusePendiente;
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