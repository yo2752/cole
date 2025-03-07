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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.enums.TipoOperacion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.utiles.ConstantesPDF;
import utilidades.mensajes.Claves;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class ContratoDesistimientoAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3189047408745221541L;

	private static Logger log = Logger.getLogger(ContratoDesistimientoAction.class);

	private static final String RESULT_ALTA = "alta";
	private static final String RESULTADO_EDIT = "editar";
	private static final String RESULTADO_CONSULTA = "consulta";
	private static final String POP_UP_ANIADIR_REPRESENTANTE_SOLICITANTE = "popUpRepresentanteSolicitanteRegistro";
	private static final String ID_CONTRATO_REGISTRO = "Desistimiento";
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";
	private TramiteRegRbmDto tramiteRegRbmDto;
	private String identificador;
	private long idRepresentanteInterviniente;
	private long idInterviniente;

	private boolean rmFirmar;

	private String tipoInterviniente;
	private String nifInterviniente;

	private String idRegistroFueraSecuencia;

	private String estadoAcusePendiente;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String ficheroDescarga;
	private String fileUploadFileName;
	private File fileUpload;
	private String idFicheroEliminar;
	private String idFicheroDescargar;
	private String nombreDoc;
	private String tipoDoc;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	private String tipoContratoRegistro;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	/*
	 * ***************************************************************** Methods *****************************************************************
	 */

	@SkipValidation
	public String alta() {
		log.debug("Start");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		setTramiteRegRbmDto(new TramiteRegRbmDto());
		getTramiteRegRbmDto().setTipoTramite(TipoTramiteRegistro.MODELO_13.getValorEnum());
		getTramiteRegRbmDto().setNumColegiado(utilesColegiado.getNumColegiadoSession());
		getTramiteRegRbmDto().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		getTramiteRegRbmDto().setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		getTramiteRegRbmDto().setTipoOperacion(TipoOperacion.D.getKey());
		getTramiteRegRbmDto().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegRbmDto().getNumColegiado(), getTramiteRegRbmDto().getIdContrato()));

		log.debug("Return");
		return RESULT_ALTA;
	}

	// Bot�n guardar del formulario
	public String guardar() {
		ResultRegistro guardar;
		guardar = guardarContrato(true);

		if (!guardar.isError()) {
			addActionMessage("Tr�mite guardado correctamente");
			recuperarContrato();
		} else {
			if (null != getTramiteRegRbmDto().getIdTramiteRegistro()) {
				recuperarListas();
			}
			if (guardar.getValidaciones() != null && !guardar.getValidaciones().isEmpty()) {
				for (String validacion : guardar.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(guardar.getMensaje());
			}
		}

		return RESULTADO_EDIT;
	}

	// Guarda el contrato con los datos que se hayan tecleado en pantalla
	private ResultRegistro guardarContrato(boolean cambiarEstado) {
		ResultRegistro resultado;
		log.debug("Start");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		if (cambiarEstado) {
			ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getEstado(), new BigDecimal(EstadoTramiteRegistro.Iniciado
					.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (respuestaBean != null && respuestaBean.getError()) {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando a 'Iniciado' el tramite con identificador: " + tramiteRegRbmDto
						.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
			} else {
				getTramiteRegRbmDto().setEstado(new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()));
			}
		}
		resultado = servicioTramiteRegRbm.guardarTramiteRegRbmCancelDesist(getTramiteRegRbmDto(), getTramiteRegRbmDto().getEstado(), utilesColegiado.getIdUsuarioSessionBigDecimal());

		return resultado;
	}

	// Recupera todos los datos del contrato
	public void recuperarListas() {
		ResultRegistro resultado;

		resultado = servicioTramiteRegRbm.getListasCancelacion(getTramiteRegRbmDto().getIdTramiteRegistro().toString());
		TramiteRegRbmDto tramite = (TramiteRegRbmDto) resultado.getObj();
		if (null != tramite) {
			getTramiteRegRbmDto().setRepresentantesSolicitante(tramite.getRepresentantesSolicitante());
		}
	}

	// Recupera todos los datos del contrato
	public String recuperarContrato() {
		ResultRegistro resultado = new ResultRegistro();
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		try {
			if (null != getTramiteRegRbmDto() && null != getTramiteRegRbmDto().getIdTramiteRegistro()) {
				resultado = servicioTramiteRegRbm.getTramiteRegRbmCancelDesist(getTramiteRegRbmDto().getIdTramiteRegistro().toString());
			} else {
				resultado = servicioTramiteRegRbm.getTramiteRegRbmCancelDesist(getIdentificador());
			}
		} catch (Exception e) {
			resultado.setMensaje("Error al recuperar el contrato Desistimiento");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			getTramiteRegRbmDto().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegRbmDto().getNumColegiado(), getTramiteRegRbmDto().getIdContrato()));
			addActionError(resultado.getMensaje());
			return RESULTADO_CONSULTA;
		} else {
			setTramiteRegRbmDto((TramiteRegRbmDto) resultado.getObj());
		}

		return RESULT_ALTA;
	}

	public String validarContrato() {
		ResultRegistro resultado = new ResultRegistro();

		try {
			guardarContrato(false);
			recuperarContrato();
			resultado = servicioTramiteRegRbm.validarContrato(getTramiteRegRbmDto());
			if (!resultado.isError()) {
				ResultBean respuestaBean = servicioTramiteRegistro.cambiarEstado(true, tramiteRegRbmDto.getIdTramiteRegistro(), tramiteRegRbmDto.getEstado(), new BigDecimal(
						EstadoTramiteRegistro.Validado.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (respuestaBean != null && respuestaBean.getError()) {
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando a 'Validado' el tramite con identificador: " + tramiteRegRbmDto
							.getIdTramiteRegistro() + " : " + respuestaBean.getMensaje());
				} else {
					tramiteRegRbmDto.setEstado(new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()));
				}

				addActionMessage("Tr�mite validado correctamente");
				guardarContrato(false);
				recuperarContrato();
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
			addActionError("Error al validar el contrato.");
		}
		return RESULT_ALTA;
	}

	public String construirDpr() {
		log.info("Desistimiento construirDpr.");

		ResultRegistro resultRegistro;

		if (!UtilesRegistradores.permitidoEnvio(getTramiteRegRbmDto().getEstado())) {
			addActionError("El estado actual del tr�mite no permite el env�o del mismo.");
			recuperarContrato();
			return RESULTADO_EDIT;
		}

		resultRegistro = servicioTramiteRegRbm.construirDpr(getTramiteRegRbmDto().getIdTramiteRegistro().toString(), utilesColegiado.getAlias());

		if (resultRegistro.isError()) {
			addActionError(resultRegistro.getMensaje());
		} else {
			addActionMessage(resultRegistro.getMensaje());
		}

		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String construirFirmarAcuse() {
		log.info("Desistimiento firmar acuse.");

		ResultRegistro resultRegistro;

		resultRegistro = servicioTramiteRegistro.construirAcuse(getTramiteRegRbmDto().getIdTramiteRegistro().toString(), utilesColegiado.getAlias(), estadoAcusePendiente, idRegistroFueraSecuencia);

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

		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String subsanar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " subsanar");

		ResultRegistro resultRegistro;

		recuperarContrato();

		if (!EstadoTramiteRegistro.Calificado_Defectos.getValorEnum().equals(tramiteRegRbmDto.getEstado().toString()) && !EstadoTramiteRegistro.Inscrito_Parcialmente.getValorEnum().equals(
				tramiteRegRbmDto.getEstado().toString())) {
			addActionError("El estado actual: " + EstadoTramiteRegistro.convertirTexto(tramiteRegRbmDto.getEstado().toString()) + " no permite la subsanaci�n del tr�mite de id: " + tramiteRegRbmDto
					.getIdTramiteRegistro());
			return RESULTADO_EDIT;
		}

		BigDecimal idTramiteRegistro = tramiteRegRbmDto.getIdTramiteRegistro();
		tramiteRegRbmDto.setSubsanacion("S");
		guardarContrato(false);

		servicioTramiteRegistro.incluirDatosSubsanacion(tramiteRegRbmDto);
		servicioTramiteRegistro.inicializarParametros(tramiteRegRbmDto);

		resultRegistro = guardarContrato(true);

		if (resultRegistro != null && !resultRegistro.isError()) {
			addActionMessage("Subsanado correctamente. El tr�mite subsanado tiene el n�mero de expediente: " + tramiteRegRbmDto.getIdTramiteRegistro());
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Se ha subsanado el tramite con id: " + idTramiteRegistro + " .Id del subsanado: " + tramiteRegRbmDto.getIdTramiteRegistro());
		} else {
			if (null != getTramiteRegRbmDto().getIdTramiteRegistro()) {
				recuperarListas();
			}
			addActionError("Error subsanando el tr�mite: " + idTramiteRegistro);
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
			getTramiteRegRbmDto().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegRbmDto().getNumColegiado(), getTramiteRegRbmDto().getIdContrato()));
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " Error subsanando el tramite con id: " + idTramiteRegistro + " .Error: " + resultRegistro.getMensaje());
		}

		tramiteRegRbmDto.setIdTramiteRegistro(idTramiteRegistro);
		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String duplicar() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + " duplicar");

		ResultRegistro resultRegistro;

		recuperarContrato();

		if (!EstadoTramiteRegistro.Confirmada_Denegacion.getValorEnum().equals(tramiteRegRbmDto.getEstado().toString())) {
			addActionError("El estado actual: " + EstadoTramiteRegistro.convertirTexto(tramiteRegRbmDto.getEstado().toString()) + " no permite el duplicado del tr�mite de id: " + tramiteRegRbmDto
					.getIdTramiteRegistro());
			return RESULTADO_EDIT;
		}

		BigDecimal idTramiteRegistro = tramiteRegRbmDto.getIdTramiteRegistro();

		servicioTramiteRegistro.inicializarParametros(tramiteRegRbmDto);

		resultRegistro = guardarContrato(true);

		if (resultRegistro != null && !resultRegistro.isError()) {
			addActionMessage("Duplicado correctamente. El tr�mite duplicado tiene el n�mero de expediente: " + tramiteRegRbmDto.getIdTramiteRegistro());
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Se ha duplicado el tramite con id: " + idTramiteRegistro + " .Id del duplicado: " + tramiteRegRbmDto.getIdTramiteRegistro());
		} else {
			if (null != getTramiteRegRbmDto().getIdTramiteRegistro()) {
				recuperarListas();
			}
			addActionError("Error duplicando el tr�mite: " + idTramiteRegistro);
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
			getTramiteRegRbmDto().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegRbmDto().getNumColegiado(), getTramiteRegRbmDto().getIdContrato()));
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " Error duplicando el tramite con id: " + idTramiteRegistro + " .Error: " + resultRegistro.getMensaje());
		}

		tramiteRegRbmDto.setIdTramiteRegistro(idTramiteRegistro);
		recuperarContrato();
		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Bloque Representantes Solicitante ------------------------------------------------------------------------

	public String cargarPopUpRepresentanteSolicitanteRegistro() {
		ResultRegistro resultRegistro;

		if (0 != getTramiteRegRbmDto().getSolicitante().getIdInterviniente() && 0 == getIdRepresentanteInterviniente())
			getTramiteRegRbmDto().setRepresentantesSolicitante(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getSolicitante().getIdInterviniente()));

		getTramiteRegRbmDto().setRepresentanteSolicitante(null);

		// El Solicitante no est� guardado, validamos
		getTramiteRegRbmDto().getSolicitante().setTipoInterviniente(TipoInterviniente.Solicitante.getValorEnum());
		resultRegistro = servicioIntervinienteRegistro.validarInterviniente(getTramiteRegRbmDto().getSolicitante(), getTramiteRegRbmDto().getTipoTramite());
		if (resultRegistro.isError()) {
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			}
			IntervinienteRegistroDto solicitanteAux = getTramiteRegRbmDto().getSolicitante();
			recuperarContrato();
			getTramiteRegRbmDto().setSolicitante(solicitanteAux);
			if (null != getTramiteRegRbmDto().getSolicitante() && 0 != getTramiteRegRbmDto().getSolicitante().getIdInterviniente())
				getTramiteRegRbmDto().setRepresentantesSolicitante(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getSolicitante().getIdInterviniente()));
			return RESULTADO_EDIT;
		}
		if (0 != getIdRepresentanteInterviniente()) {
			// Actualizaci�n
			getTramiteRegRbmDto().setRepresentanteSolicitante(servicioIntervinienteRegistro.getRepresentante(String.valueOf(getIdRepresentanteInterviniente())));
			getTramiteRegRbmDto().getRepresentanteSolicitante().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getRepresentanteSolicitante().getIdDireccion()));
			if (null != getTramiteRegRbmDto().getRepresentanteSolicitante().getNotario() && null != getTramiteRegRbmDto().getRepresentanteSolicitante().getNotario().getFecOtorgamiento()) {
				getTramiteRegRbmDto().getRepresentanteSolicitante().getNotario().setFecOtorgamientoNotario(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getRepresentanteSolicitante().getNotario()
						.getFecOtorgamiento()));
			}
		}

		return POP_UP_ANIADIR_REPRESENTANTE_SOLICITANTE;

	}

	public String aniadirRepresentanteSolicitante() throws ParseException {
		ResultRegistro resultRegistroRepresentanteInterviniente;

		// Guardo contrato para generar el id, con ese id guardo en la tabla intermedia con el solicitante para poder recuperar
		resultRegistroRepresentanteInterviniente = guardarContrato(true);

		if (resultRegistroRepresentanteInterviniente.isError()) {
			if (resultRegistroRepresentanteInterviniente.getValidaciones() != null && !resultRegistroRepresentanteInterviniente.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistroRepresentanteInterviniente.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistroRepresentanteInterviniente.getMensaje());
			}
		} else {
			addActionMessage("Representante a�adido correctamente");
		}
		recuperarContrato();
		// getTramiteRegRbmDto().setRepresentantesSolicitante(servicioIntervinienteRegistro.getRepresentantes( getTramiteRegRbmDto().getSolicitante().getIdInterviniente()));

		return RESULTADO_EDIT;
	}

	public String borrarRepresentanteSolicitante() {
		ResultRegistro resultObjectSolicitante;

		guardarContrato(true);

		if (0 != idRepresentanteInterviniente) {

			getTramiteRegRbmDto().setRepresentanteSolicitante(servicioIntervinienteRegistro.getInterviniente(String.valueOf(idRepresentanteInterviniente)));
			resultObjectSolicitante = servicioIntervinienteRegistro.borrarRepresentante(String.valueOf(idRepresentanteInterviniente));
			if (resultObjectSolicitante.isError()) {
				addActionError(resultObjectSolicitante.getMensaje());
			} else {
				addActionMessage("Representante borrado correctamente");
			}

			IntervinienteRegistroDto solicitanteAux = getTramiteRegRbmDto().getSolicitante();
			recuperarContrato();
			getTramiteRegRbmDto().setSolicitante(solicitanteAux);
			getTramiteRegRbmDto().setRepresentantesSolicitante(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getSolicitante().getIdInterviniente()));
		} else {
			recuperarContrato();
		}

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin Representantes Solicitante ------------------------------------------------------------------------

	// ---------------------------------------------Bloque Documentos ------------------------------------------------------------------------

	// Para a�adir ficheros a la lista desde el bot�n de la pesta�a documentaci�n
	public String incluirFichero() throws Exception, OegamExcepcion {
		String cadenaIncluirFichero = "incluirFichero.";
		guardarContrato(true);
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + cadenaIncluirFichero);
		String returnStruts = "";
		try {
			returnStruts = RESULTADO_EDIT;
			ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteRegistro.recuperarDocumentos(getTramiteRegRbmDto().getIdTramiteRegistro(), getTramiteRegRbmDto().getTipoTramite());
			if (ficherosSubidos == null) {
				ficherosSubidos = new ArrayList<FicheroInfo>();
			}
			if (fileUpload != null) {
				// Verifica que no se ha alcanzado el m�ximo de ficheros permitidos:
				int maxFicheros = Integer.parseInt(gestorPropiedades.valorPropertie("envioEscrituras.numero.maximo.ficheros"));
				if (ficherosSubidos.size() == maxFicheros) {
					addActionError("Alcanzado el m�ximo de ficheros a incluir en el env�o de documentaci�n: " + maxFicheros);
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperarContrato();
					return returnStruts;
				}
				// Verifica que el nombre del fichero no contenga caracteres especiales
				if (!utiles.esNombreFicheroValido(fileUploadFileName)) {
					addActionError("El fichero a�adido debe tener un nombre correcto");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperarContrato();
					return returnStruts;
				}
				// Verifica que el fichero subido tiene un formato soportado
				String extension = utiles.dameExtension(fileUploadFileName, false);
				if (extension == null) {
					addActionError("El fichero a�adido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .xls, .xml, .jpg, .jpeg o .zip");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperarContrato();
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
						addActionMessage("Fichero a�adido correctamente");
						getTramiteRegRbmDto().setFicherosSubidos(ficherosSubidos);
						getTramiteRegRbmDto().setExtensionFicheroEnviado(ficheroInfo.getExtension());
						if (getTramiteRegRbmDto().getFicheroSubido() == null) {
							getTramiteRegRbmDto().setFicheroSubido("si");
							servicioTramiteRegistro.actualizarFicheroSubido(getTramiteRegRbmDto().getIdTramiteRegistro());
						}
						log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + cadenaIncluirFichero);
					} catch (Exception ex) {
						log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + ex);
						addActionError("Se ha lanzado una excepci�n Oegam relacionada con el fichero de propiedades");
						log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					}
				} else {
					addActionError("El fichero a�adido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .asc, .xls, .xml, .jpg, .jpeg o .zip");
					log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
				}
			} else {
				addActionError("Utilice el bot�n 'Examinar' para seleccionar los ficheros a adjuntar en el env�o.");
				log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
			}
		} catch (Exception ex) {
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + ex);
			addActionError("Se ha lanzado una excepci�n Oegam relacionada con el fichero de propiedades");
			log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
		}

		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_FIN + cadenaIncluirFichero);

		recuperarContrato();

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
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(getTramiteRegRbmDto().getIdTramiteRegistro()));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento("fichero_registro_" + idFichero + "_" + getTramiteRegRbmDto().getIdTramiteRegistro());
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}

	public String eliminarFichero() {
		String returnStruts = "";
		guardarContrato(true);
		try {
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + "eliminarFichero.");
			returnStruts = RESULTADO_EDIT;
			if (null == getTramiteRegRbmDto()) {
				log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + " No se ha recuperado tramite en curso de la sesion.");
				return returnStruts;
			}

			// Si est� 'finalizado con error' lo pone en 'iniciado':
			ArrayList<FicheroInfo> ficheros = servicioTramiteRegistro.recuperarDocumentos(getTramiteRegRbmDto().getIdTramiteRegistro(), getTramiteRegRbmDto().getTipoTramite());
			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(getIdFicheroEliminar())) {
						// Borra fisicamente el fichero del disco:
						tmp.getFichero().delete();
						it.remove();
						getTramiteRegRbmDto().setFicherosSubidos(ficheros);
						if (ficheros.isEmpty()) {
							getTramiteRegRbmDto().setFicheroSubido("no");
							servicioTramiteRegistro.actualizarFicheroSubido(getTramiteRegRbmDto().getIdTramiteRegistro());
						}
						addActionMessage("Fichero eliminado correctamente");
						recuperarContrato();
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

		recuperarContrato();

		return returnStruts;
	}

	public String descargarDocumentacion() {
		log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INICIO + DESCARGAR_DOCUMENTO);
		ArrayList<FicheroInfo> ficheros = null;
		try {

			if (tipoDoc.equals("recibida")) {
				ficheros = servicioTramiteRegistro.recuperarDocumentosRecibidos(getTramiteRegRbmDto().getIdTramiteRegistro(), getTramiteRegRbmDto().getTipoTramite());
			} else {
				ficheros = servicioTramiteRegistro.recuperarDocumentos(getTramiteRegRbmDto().getIdTramiteRegistro(), getTramiteRegRbmDto().getTipoTramite());
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

		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String descargarAdjuntos() throws OegamExcepcion, IOException {

		boolean descargar = false;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(baos);

		FileResultBean ficheros = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION, Utilidades
				.transformExpedienteFecha(getTramiteRegRbmDto().getIdTramiteRegistro().toString().trim()), getTramiteRegRbmDto().getIdTramiteRegistro().toString().trim());

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
			ficheroDescarga = getTramiteRegRbmDto().getIdTramiteRegistro().toString() + ConstantesPDF.EXTENSION_ZIP;
			inputStream = stream;
			return DESCARGAR_DOCUMENTO;
		}

		recuperarContrato();
		addActionError("No existe documentaci�n para descargar");
		return RESULTADO_EDIT;
	}

	// ---------------------------------------------Fin Bloque Documentos ------------------------------------------------------------------------------
	/*
	 * ***************************************************************** Getters & setters *****************************************************************
	 */

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public long getIdRepresentanteInterviniente() {
		return idRepresentanteInterviniente;
	}

	public void setIdRepresentanteInterviniente(long idRepresentanteInterviniente) {
		this.idRepresentanteInterviniente = idRepresentanteInterviniente;
	}

	public long getIdInterviniente() {
		return idInterviniente;
	}

	public void setIdInterviniente(long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getNifInterviniente() {
		return nifInterviniente;
	}

	public void setNifInterviniente(String nifInterviniente) {
		this.nifInterviniente = nifInterviniente;
	}

	public boolean isRmFirmar() {
		return rmFirmar;
	}

	public void setRmFirmar(boolean rmFirmar) {
		this.rmFirmar = rmFirmar;
	}

	public TramiteRegRbmDto getTramiteRegRbmDto() {
		return tramiteRegRbmDto;
	}

	public void setTramiteRegRbmDto(TramiteRegRbmDto tramiteRegRbmDto) {
		this.tramiteRegRbmDto = tramiteRegRbmDto;
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
