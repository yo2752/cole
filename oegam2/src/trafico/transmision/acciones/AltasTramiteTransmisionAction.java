package trafico.transmision.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.enumerados.ProcesosAmEnum;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioCheckCTIT;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.am.service.ServicioWebServiceAm;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.opensymphony.xwork2.Action;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.Direccion;
import escrituras.beans.Municipio;
import escrituras.beans.Persona;
import escrituras.beans.Provincia;
import escrituras.beans.ResultBean;
import escrituras.beans.TipoVia;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import hibernate.dao.trafico.VerifyRentingDAO;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import procesos.daos.BeanPQCrearSolicitud;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.JefaturaTrafico;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.BeanPQCambiarEstadoTramite;
import trafico.beans.schemas.generated.checkctit.SolicitudTramite;
import trafico.beans.schemas.generated.transTelematica.SolicitudRegistroEntrada;
import trafico.beans.utiles.TransmisionTramiteTraficoBeanPQConversion;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.ComprobadorDatosSensibles;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.UtilidadesAccionTrafico;
import trafico.utiles.XmlCheckCTITFactory;
import trafico.utiles.XmlTransTelematicaFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoCreacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.AccionException;
import utilidades.web.excepciones.ValidacionJustificantePorFechaExcepcion;
import utilidades.web.excepciones.ValidacionJustificanteRepetidoExcepcion;

@SuppressWarnings("serial")
public class AltasTramiteTransmisionAction extends ActionBase implements ServletRequestAware {

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltasTramiteTransmisionAction.class);

	private static final String ALTAS_TRAMITE_TRANSMISION = "altasTramiteTransmision";
	private static final String ALTA_TRAMITE_TRAFICO_JUSTIFICANTE = "altaTramiteTraficoJustificante";
	private static final String DESCARGAR_DOCUMENTO_BASE = "descargarDocumentoBase";
	private static final String POP_UP_JUSTIFICANTE_PRO = "popUpJustificanteProf";
	
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";
	private String nombreDoc;
	
	private HttpServletRequest request;

	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;
	private String nui;
	private String tipoTasa;
	private String motivoJustificantes;
	private String documentoJustificantes;
	private String diasValidezJustificantes;
	private String mensajeErrorFormulario;
	private InputStream inputStream; // Flujo de bytes del fichero a exportar (download)
	private String fileName; // Nombre del fichero a exportar
	private String tipoTramiteGenerar;
	private Boolean ficheroXML620;
	private String tipoIntervinienteBuscar;// Para el botón de buscar interviniente del DNI

	private String fileNameCheckCTIT; // Nombre del fichero CheckCTIT
	private String fileNameTransTelematica; // Nombre del fichero de Transmisión Telemática
	private HashMap<String, Object> parametrosBusqueda;
	private PaginatedList listaAcciones;

	private boolean justificanteRepetido = false;// Resultado de la validación del justificante.

	private String propTexto;

	private String tipoTasaUsada;

	private ModeloTrafico modeloTrafico;
	private ModeloTransmision modeloTransmision;
	private ModeloSolicitud modeloSolicitud;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;
	private ModeloCreditosTrafico modeloCreditosTrafico;

	private String idVehiculo;
	private static final String antiguoDatosSensibles = "datosSensibles.antiguo";
	private static final String ERROR_GUARDAR_TRANSMISION = "No se ha podido guardar el trámite de transmisión";

	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioCheckCTIT servicioCheckCTIT;

	@Autowired
	Utiles utiles;

	private TramiteTrafTranDto tramiteTrafTranDto;

	private int numPeticiones;

	private String tipoTramiteJustificante;

	private DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean;

	// DESCARGAR FICHEROS
	private File fichero;
	private String ficheroFileName;
	private String ficheroDescarga;
	private String fileUploadFileName;

	private String idFicheroEliminar;
	private String idFicheroDescargar;

	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	TransmisionTramiteTraficoBeanPQConversion transmisionTramiteTraficoBeanPQConversion;

	@Autowired
	ServicioWebServiceAm servicioWebServiceAm;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	// MÉTODOS

	public String inicio() {
		tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean(true);
		tramiteTraficoTransmisionBean.getTramiteTraficoBean().getJefaturaTrafico().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		tramiteTraficoTransmisionBean.setElectronica("S");
		tramiteTraficoTransmisionBean.getTramiteTraficoBean().setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		limpiarCamposSession();
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		return guardarTransmision(true);
	}

	public String guardarTramiteTransmision() {
		return guardarTransmision(false);
	}

	public String guardarTransmision(Boolean formularioInicial) {
		log.info("Transmision Tramite Trafico: inicio--guardarTramiteTransmision:");

		if (formularioInicial) {
			log.info("Transmision Tramite Trafico: fin-- guardarTramiteTransmision: formulario inicial" + formularioInicial);
			if (tramiteTraficoTransmisionBean.getPresentadorBean() != null && tramiteTraficoTransmisionBean.getPresentadorBean().getPersona() != null
					&& (tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif() == null || tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif().equals(""))
					&& tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null) {
				ModeloColegiado modeloColegiado = new ModeloColegiado();
				tramiteTraficoTransmisionBean.getPresentadorBean().setPersona(
						modeloColegiado.obtenerColegiadoCompleto(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato() != null ? tramiteTraficoTransmisionBean
								.getTramiteTraficoBean().getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal()));
				tramiteTraficoTransmisionBean.getPresentadorBean().setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());
				tramiteTraficoTransmisionBean.getTramiteTraficoBean().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
				tramiteTraficoTransmisionBean.setImpresionPermiso("true");
				tramiteTraficoTransmisionBean.setHistoricoCheckImpresionPermiso("true");
			}
		} else {
			try {
				try {
					// Validamos que el número de autoliquidación de ITP no viene informado, pues no es necesario.
					// CFS 05/07/2013 Mantis 4068
					if (null != tramiteTraficoTransmisionBean && null != tramiteTraficoTransmisionBean.getNumAutoItp() && !"".equals(tramiteTraficoTransmisionBean.getNumAutoItp())) {
						tramiteTraficoTransmisionBean.setNumAutoItp("");
					}
					// Fin Mantis 4068

					// Mantis 19262. David Sierra. Validación formato fechas
					String validacionFecha = validarFechasTransmision(tramiteTraficoTransmisionBean);
					if(!"OK".equals(validacionFecha)) {
						addActionError(validacionFecha);
						try {
							mantenerAcciones();
						} catch (Throwable e) {
							log.error(e.getMessage());
						}
						return ALTAS_TRAMITE_TRANSMISION;
					}
					guardarTramite();
				} catch (ParseException e) {
					addActionError(e.getMessage());
				}
			} catch (OegamExcepcion e) {
				addActionError(e.getMensajeError1());
			}
		}

		try {
			mantenerAcciones();
		} catch (Throwable e) {
			addActionError(e.toString());
		}

		// 30/10/2012 Incidencia: 0002616 Ricardo Rodríguez
		try {
			// Comprueba que renting está seleccionado:
			if (tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null && tramiteTraficoTransmisionBean.getTramiteTraficoBean().getRenting() != null
					&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getRenting().equals("true")) {
				boolean mensajeYaEstablecido = false;
				// Comprueba si hay un CIF de transmitente:
				if (tramiteTraficoTransmisionBean.getTransmitenteBean() != null && tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona() != null
						&& tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif() != null && !tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif().equals("")) {
					// Busca concordancia en la tabla:
					boolean enTabla = VerifyRentingDAO.cifEnTabla(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif());
					if (enTabla) {
						// Recupera el mensaje:
						String mensaje = ConstantesMensajes.MENSAJE_VERIFICACION_RENTING;
						// Avisa al usuario:
						addActionMessage(mensaje);
						mensajeYaEstablecido = true;
					}
				}
				// Comprueba que no se ha establecido ya el mensaje:
				if (!mensajeYaEstablecido) {
					// Comprueba que hay un CIF de adquiriente:
					if (tramiteTraficoTransmisionBean.getAdquirienteBean() != null && tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona() != null
							&& tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif() != null && !tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif().equals("")) {
						// Busca concordancia en la tabla:
						boolean enTabla = VerifyRentingDAO.cifEnTabla(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif());
						if (enTabla) {
							// Recupera el mensaje:
							String mensaje = ConstantesMensajes.MENSAJE_VERIFICACION_RENTING;
							// Avisa al usuario:
							addActionMessage(mensaje);
						}
					}
				}
			}
		} catch (Exception ex) {
			addActionError(ex.toString());
		}
		// FIN 30/10/2012 Incidencia: 0002616 Ricardo Rodríguez
		return ALTAS_TRAMITE_TRANSMISION;
	}

	private void guardarTramite() throws OegamExcepcion, ParseException {
		// DRC@15-02-2013 Incidencia Mantis: 2708
		tramiteTraficoTransmisionBean.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

		boolean kilometrajeBorrado = false;
		if (tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros() != null
				&& BigDecimal.ZERO.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().getKilometros())) {
			kilometrajeBorrado = true;
			tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getVehiculoTramiteTraficoBean().setKilometros(null);
			tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().setDoiResponsableKm(null);
			tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().setFechaLecturaKm(null);
		}

		// Llamo al método estático del modelo para guardar el trámite después de recogerlo del formulario web.
		// GuardarAltaTramiteTransmision tiene el BeanPQ y realiza todos los pasos necesarios.
		Map<String, Object> resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(
				transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(tramiteTraficoTransmisionBean), tramiteTraficoTransmisionBean);
		ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
		log.debug("resultado = " + resultBean.getMensaje());
		// Mantis 19172. David Sierra: Comprobación caducidad NIFs intervinientes
		ResultBean resultValCadNif = getModeloTransmision().comprobarCaducidadNifs(tramiteTraficoTransmisionBean, false);
		if (resultValCadNif.getListaMensajes() != null && !resultValCadNif.getListaMensajes().isEmpty()) {
			for (String mensaje : resultValCadNif.getListaMensajes()) {
				if (resultValCadNif.getError()) {
					addActionError(mensaje);
				} else {
					addActionMessage(mensaje);
				}
			}
		}

		if (!resultBean.getError()) {
			setTramiteTraficoTransmisionBean((TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
			if (kilometrajeBorrado) {
				addActionMessage("El kilometraje del vehículo no puede ser 0, se borra ese dato al realizar el guardado");
			}
			addActionMessage("Trámite guardado");
		} else {
			addActionError(ERROR_GUARDAR_TRANSMISION);
		}

		for (String mensaje : resultBean.getListaMensajes()) {
			throw new OegamExcepcion(mensaje);
		}
	}

	public String validar620() {
		log.info("Transmision Tramite Trafico: inicio--validar620:");
		Map<String, Object> resultadoModelo = null;
		String estadoTramite = tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado() != null ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum()
				: EstadoTramiteTrafico.Iniciado.getValorEnum();
		// Si el estado del trámite es uno de los siguientes, se guardará en BD antes de validar, por si ha habido algún cambio...
		if (estadoTramite.equals(EstadoTramiteTrafico.Iniciado.getValorEnum()) || estadoTramite.equals(EstadoTramiteTrafico.No_Tramitable.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum()) || estadoTramite.equals(EstadoTramiteTrafico.Tramitable.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()) || estadoTramite.equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())) {
			resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(tramiteTraficoTransmisionBean),
					tramiteTraficoTransmisionBean);
		} else { // Si no, se obtiene el detalle...
			resultadoModelo = getModeloTransmision().obtenerDetalle(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		}
		ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
		if (!Boolean.TRUE.equals(resultBean.getError())) {
			setTramiteTraficoTransmisionBean((TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
			List<String> errores = getModeloTransmision().validarCamposXML(tramiteTraficoTransmisionBean);
			for (String error : errores) {
				addActionError(error);
			}
			if (errores.isEmpty()) {
				addActionMessage("Validación correcta");
			}
		} else {
			addActionError(ERROR_GUARDAR_TRANSMISION);
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
		}

		try {
			mantenerAcciones();
		} catch (Throwable e) {
			log.error("Error en la llamada a mantenerAcciones del validar620", e);
			addActionError(e.toString());
		}
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Método para generar el XML del 620 en el action de transmisión del CTIT
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public String generarXML() throws Throwable {
		log.info("Transmision Tramite Trafico: inicio--generarXML:");
		// Generamos el XML
		List<TramiteTraficoTransmisionBean> tramites = new ArrayList<>();

		// Mantis 14664. David Sierra. Control valor Vehiculo.cilindrada
		if (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo() != null
				&& (null == tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getCilindrada()
						|| tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().isEmpty())) {
			tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().setCilindrada("0");
		} // Fin Mantis

		tramites.add(tramiteTraficoTransmisionBean);
		String idSession = ServletActionContext.getRequest().getSession().getId();
		Map<String, Object> respuestaGenerarXMLJaxb = null;
		try {
			respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(tramites, idSession);
		} catch (AccionException e) {
			log.error("Error generando XMLJaxb", e);
			addActionError(e.toString());
		}

		if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha habido errores, los mostramos...
			List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
			for (String error : errores) {
				addActionError(error);
			}
			return "altasTramiteTransmision";
		} else { // Si todo ha ido bien...
			// Lo validamos contra el XSD
			File fichero = new File((String) respuestaGenerarXMLJaxb.get("fichero"));
			String resultadoValidar = validarXML620(fichero);
			if (null == resultadoValidar || !ConstantesTrafico.XML_VALIDO.equals(resultadoValidar)) {
				addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
				mantenerAcciones();
				return ALTAS_TRAMITE_TRANSMISION;
			}

			inputStream = new FileInputStream(fichero);
			setFicheroXML620(true);
			addActionMessage("Fichero generado correctamente");
			getSession().put(ConstantesSession.FICHERO620, inputStream);
		}

		mantenerAcciones();
		return "altasTramiteTransmision";
	}

	/**
	 * Método que comprueba si hay fichero y en el caso de que lo haya, se exporta
	 * @return
	 * @throws Throwable
	 */
	public String obtenerFichero620() throws Throwable {
		fileName = "oegamGata620.xml";
		inputStream = (InputStream) getSession().get(ConstantesSession.FICHERO620);

		if (null == inputStream) {
			addActionError("No se encuentra el fichero");
			mantenerAcciones();
			return "altasTramiteTransmision";
		}

		getSession().remove(ConstantesSession.FICHERO620);
		setFicheroXML620(false);
		return "exportar";
	}

	/**
	 * Método que valida un XML contra el xsd del 620 (620_V1.4.xsd)
	 * @param fichero xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso devolverá el string de la excepción de validación
	 */
	public String validarXML620(File fichero) {
		// Constantes para validación de Schemas
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		URL esquema = this.getClass().getResource("/trafico/schemas/620_V1.4.xsd");
		final String MY_SCHEMA = esquema.getFile();
		try {
			// Get validation driver:
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
			// Creamos el schema leyendolo desde el XSD
			Schema schema = factory.newSchema(new StreamSource(MY_SCHEMA));
			Validator validator = schema.newValidator();
			// Validamos el XML, si no lo valida devolverá el string de la excepción, si lo valida devolverá 'CORRECTO'
			validator.validate(new StreamSource(fichero));
			// Mantis 11823.David Sierra: Controla la excepcion org.xml.sax.SAXParseException y muestra un mensaje legible al usuario
			// indicándole el error producido. También se muestra un mensaje más especifico en los logs
		} catch (SAXParseException spe) {
			String error = "Trámite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + ": Error en la generación del XML 620. "
					+ getModeloTransmision().parseSAXParseException(spe);
			addActionError(error);
			log.error("Error al validar el XML contra el xsd del 620 (620_V1.4.xsd)", spe);
			return spe.toString();
			// Fin Mantis
		} catch (SAXException saxEx) {
			log.error(saxEx);
			return saxEx.toString();
		} catch (Exception ex) {
			log.error(ex);
			return ex.toString();
		}
		return ConstantesTrafico.XML_VALIDO;
	}

	public String validarTramiteTransmision() throws Throwable {
		log.info("Transmision Tramite Trafico: inicio--validarTramiteTransmision:");

		boolean solicitudComprobacion = tramiteTraficoTransmisionBean != null && tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
				&& EstadoTramiteTrafico.Iniciado.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado());

		String gestionarConAM = gestorPropiedades.valorPropertie("gestionar.conAm");
		if ("2631".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()) && "SI".equals(gestionarConAM)) {
			if (solicitudComprobacion) {
				// Comprobar CheckCTIT
				ResultBean resultComp = servicioCheckCTIT.crearSolicitud(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), null,
						utilesColegiado.getIdUsuarioSessionBigDecimal(), tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
				if (!resultComp.getError()) {
					addActionMessage("Solicitud creada correctamente");
					// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Check_Ctit.getValorEnum());
				} else {
					if (resultComp.getMensaje() != null && !resultComp.getMensaje().isEmpty()) {
						addActionError(resultComp.getMensaje());
					}
					if (resultComp.getListaMensajes() != null) {
						for (String mensajeComprobar : resultComp.getListaMensajes()) {
							addActionError(mensajeComprobar);
						}
					}
				}
			} else {
				ResultadoBean resultVal = servicioWebServiceAm.validarCtit(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),
						utilesColegiado.getIdUsuarioSession());
				if (!resultVal.getError()) {
					if (StringUtils.isNotBlank(resultVal.getEstado())) {
						tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(resultVal.getEstado());
						if (StringUtils.isNotBlank(resultVal.getMensaje())) {
							addActionMessage(resultVal.getMensaje());
						}
					} else {
						addActionError("No se ha podido recuperar el estado del trámite.");
					}
				} else {
					addActionError(resultVal.getMensaje());
				}
				if (EstadoTramiteTrafico.Validado_PDF.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado())) {
					Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLTransmisionNoTelematica(tramiteTraficoTransmisionBean);
					if (respuestaGenerarXMLJaxb.get("errores") != null) {
						// Si ha habido errores, los mostramos...
						List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
						for (String error : errores) {
							addActionError(error);
						}
					}
				}
			}
		} else {
			Map<String, Object> resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(
					transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(tramiteTraficoTransmisionBean), tramiteTraficoTransmisionBean);
			ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);

			if (!resultBean.getError()) {
				setTramiteTraficoTransmisionBean((TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));

				// Se genera el XML para el CheckCTIT si el estado es iniciado...
				if (solicitudComprobacion) {
					// Comprobar CheckCTIT
					comprobarTramiteTransmisionIntero();
				} else {
					// Validar
					validarTramiteTransmisionInterno();
				}
			} else {
				addActionError("No se ha podido guardar el trámite de transmisión");
				for (String mensaje : resultBean.getListaMensajes()) {
					addActionError(mensaje);
				}
			}
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	private void comprobarTramiteTransmisionIntero() {
		log.info("Comprobando tramitabilidad trámite transmisión:");
		ResultBean resultadoComprobar = null;
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("nuevas.url.sega.checkCtit"))) {
			resultadoComprobar = servicioTramiteTraficoTransmision.comprobarTramitabilidadTramiteTransmisionSega(tramiteTraficoTransmisionBean, utilesColegiado.getIdUsuarioSessionBigDecimal(),
					utilesColegiado.getIdContratoSessionBigDecimal());
		} else {
			resultadoComprobar = servicioTramiteTraficoTransmision.comprobarTramitabilidadTramiteTransmision(tramiteTraficoTransmisionBean, utilesColegiado.getIdUsuarioSessionBigDecimal(),
					utilesColegiado.getIdContratoSessionBigDecimal());
		}
		if (!resultadoComprobar.getError()) {
			addActionMessage("Solicitud creada correctamente");
			// 15-10-2012. Ricardo Rodriguez. Incidencia : 0002589
			if (resultadoComprobar.getListaMensajes() != null) {
				for (String mensajeComprobar : resultadoComprobar.getListaMensajes()) {
					if (mensajeComprobar.contains(ConstantesTrafico.AVISO_INE_DIRECCION)) {
						addActionMessage(mensajeComprobar);
					}
				}
			}
			tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Check_Ctit.getValorEnum());
		} else {
			if (resultadoComprobar.getMensaje() != null && !resultadoComprobar.getMensaje().isEmpty()) {
				addActionError(resultadoComprobar.getMensaje());
			}
			if (resultadoComprobar.getListaMensajes() != null) {
				for (String mensajeComprobar : resultadoComprobar.getListaMensajes()) {
					addActionError(mensajeComprobar);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void validarTramiteTransmisionInterno() {
		log.info("Validando trámite transmisión:");
		ResultBean resultBean = getModeloTransmision().validar(tramiteTraficoTransmisionBean);
		if (!resultBean.getError()) {
			// Se han validado los anagramas y el trámite
			addActionMessage("Validación correcta");
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionMessage(mensaje.replace("--", " -"));
			}
		} else {
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje.replace("--", " -"));
			}
		}
		if (EstadoTramiteTrafico.Validado_PDF.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado())) {
			Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLTransmisionNoTelematica(tramiteTraficoTransmisionBean);
			if (respuestaGenerarXMLJaxb.get("errores") != null) {
				// Si ha habido errores, los mostramos...
				List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
				for (String error : errores) {
					addActionError(error);
				}
			}
		}
	}

	// @SuppressWarnings("unchecked")
	// public String validarTramiteTransmision() throws Throwable{
	// log.info("Transmision Tramite Trafico: inicio--validarTramiteTransmision:");
	// Map <String,Object> resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(
	// TransmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(tramiteTraficoTransmisionBean), tramiteTraficoTransmisionBean);
	// ResultBean resultBean = (ResultBean)resultadoModelo.get(ConstantesPQ.RESULTBEAN);
	//
	// if (!resultBean.getError()){
	// setTramiteTraficoTransmisionBean((TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
	//
	// resultBean = getModeloTransmision().validar(tramiteTraficoTransmisionBean);
	// if(!resultBean.getError()){
	// //Se han validado los anagramas y el trámite
	// addActionMessage("Validación correcta");
	// for(String mensaje:resultBean.getListaMensajes()){
	// addActionMessage(mensaje.replace("--", "<br>-"));
	// }
	//
	// //Se genera el XML para el CheckCTIT si el estado es iniciado...
	// if(EstadoTramiteTrafico.Iniciado.equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado())){
	// String tipoFirma = gestorPropiedades.valorPropertie(ConstantesFirma.TIPO_FIRMA_TRANSMISION);
	// Map<String,Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLCheckCTIT(tramiteTraficoTransmisionBean,tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
	// if(respuestaGenerarXMLJaxb.get("errores") != null){
	// //Si ha habido errores, los mostramos...
	// List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
	// for(String error:errores){
	// addActionError(error);
	// }
	// } else if(tipoFirma.equals(ConstantesFirma.TIPO_FIRMA_CERTIFICADO)){
	// //Si todo ha ido bien y la firma es con el applet...
	// fileNameCheckCTIT = (String)respuestaGenerarXMLJaxb.get("fichero");
	// request.getSession().setAttribute("fileNameCheckCTIT", fileNameCheckCTIT);
	// request.getSession().setAttribute("numExpediente", tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
	// //Redirigimos a una página vacía puesto que se ha invocado al applet de firma
	// return "applet";
	// } else if(tipoFirma.equals(ConstantesFirma.TIPO_FIRMA_HSM)){
	// //Si todo ha ido bien y la firma es con HSM...
	// SolicitudTramite solicitudTramite = (SolicitudTramite)respuestaGenerarXMLJaxb.get("solicitud_tramite");
	//
	// fileNameCheckCTIT = (String)respuestaGenerarXMLJaxb.get("fichero");
	//
	// Marshaller marshaller;
	// XmlCheckCTITFactory xmlCheckCTITFactory = new XmlCheckCTITFactory();
	// try {
	// //marshaller = JAXBContext.newInstance("trafico.beans.schemas.generated.checkctit").createMarshaller();
	// marshaller = xmlCheckCTITFactory.getContext().createMarshaller();
	// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// marshaller.marshal(solicitudTramite, new FileOutputStream(fileNameCheckCTIT));
	// } catch (JAXBException e) {
	// addActionError("Ha ocurrido un error al parsear el XML");
	// } catch (FileNotFoundException e){
	// addActionError("No se ha encontrado el fichero XML");
	// }
	//
	// //Lo validamos contra el XSD
	// File fichero = new File(fileNameCheckCTIT);
	// // Ricardo Rodriguez. Incidencia 3149. 20/12/2012
	// try{
	// xmlCheckCTITFactory.validarXMLCheckCTIT(fichero);
	// }catch(OegamExcepcion ex){
	// addActionError(ex.getMensajeError1());
	// mantenerAcciones();
	// return ALTAS_TRAMITE_TRANSMISION;
	// }
	// // XmlCheckCTITFactory.validarXMLCheckCTIT(fichero);
	// // Fin. Ricardo Rodriguez. Incidencia 3149. 20/12/2012
	//
	// //Se añade la solicitud a la cola
	// crearSolicitud(fichero.getName(), ConstantesProcesos.PROCESO_CHECKCTIT);
	//
	// mantenerAcciones();
	// return ALTAS_TRAMITE_TRANSMISION;
	// }
	// }
	// } else {
	// for(String mensaje:resultBean.getListaMensajes()){
	// addActionError(mensaje.replace("--", "<br>-"));
	// }
	// }
	// if(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum().equalsIgnoreCase(EstadoTramiteTrafico.Validado_PDF.getValorEnum())){
	// Map<String,Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLTransmisionNoTelematica(tramiteTraficoTransmisionBean, tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
	// if(respuestaGenerarXMLJaxb.get("errores") != null){ //Si ha habido errores, los mostramos...
	// List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
	// for(String error:errores){
	// addActionError(error);
	// }
	// }
	// }
	// } else {
	// addActionError("No se ha podido guardar el tramite de transmisión");
	// for(String mensaje:resultBean.getListaMensajes()){
	// addActionError(mensaje);
	// }
	// }
	// mantenerAcciones();
	// return ALTAS_TRAMITE_TRANSMISION;
	// }

	public String cargarPopUpJustificantesProf(){
		tipoTramiteGenerar = TipoTramiteTrafico.TransmisionElectronica.getValorEnum();
		return POP_UP_JUSTIFICANTE_PRO;
	}

	public String transmisionTelematica() throws Throwable {
		log.info("Transmision Tramite Trafico: inicio--transmisionTelematica:");
		BigDecimal idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal();
		tramiteTraficoTransmisionBean.getTramiteTraficoBean().setIdUsuario(idUsuario);
		// Validamos que tenga créditos suficientes
		if (tramiteTraficoTransmisionBean.getTipoTransferencia() == null) {
			addActionError("El tipo de transferencia es obligatoria");
			mantenerAcciones();
			return ALTAS_TRAMITE_TRANSMISION;
		}

		if (!"2631".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado())) {
			// Comprobamos si hay créditos suficientes en función del tipo de créditos a cobrar.
			String tipoTransferencia = TipoTramiteTrafico.Transmision.getValorEnum();
			if (tramiteTraficoTransmisionBean.getTipoTransferencia() == TipoTransferencia.tipo4 || tramiteTraficoTransmisionBean.getTipoTransferencia() == TipoTransferencia.tipo5) {
				tipoTransferencia = TipoTramiteTrafico.Baja.getValorEnum();
			}
			HashMap<String, Object> resultadoMetodo = getModeloCreditosTrafico().validarCreditosPorNumColegiado(utilesColegiado.getIdContratoSessionBigDecimal().toString(), new BigDecimal(1),
					tipoTransferencia);
			ResultBean resultBean = (ResultBean) resultadoMetodo.get(ConstantesPQ.RESULTBEAN);
			if (resultBean.getError()) {
				addActionError("Créditos insuficientes para realizar la operación");
				mantenerAcciones();
				return ALTAS_TRAMITE_TRANSMISION;
			}
		}

		String[] numsExpedientes = new String[] { tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente().toString() };
		List<String> expedientesSensibles = null;
		String datosSensiblesAnt = gestorPropiedades.valorPropertie(antiguoDatosSensibles);
		if ("true".equals(datosSensiblesAnt)) {
			expedientesSensibles = new ComprobadorDatosSensibles().isSensibleData(numsExpedientes);
		} else {
			expedientesSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(utiles.convertirStringArrayToBigDecimal(numsExpedientes), utilesColegiado
					.getIdUsuarioSessionBigDecimal());
		}
		if (expedientesSensibles != null && !expedientesSensibles.isEmpty()) {
			for (String expedienteSensible : expedientesSensibles) {
				addActionError("Se ha recibido un error técnico. No intenten presentar el tramite. Les rogamos comuniquen con el Colegio.");
				log.error(Claves.CLAVE_LOG_TRAFICO_TRAMITE_CONSULTA + "Datos sensibles en el trámite " + expedienteSensible);
			}
			mantenerAcciones();
			return ALTAS_TRAMITE_TRANSMISION;
		}
		ResultBean resultado = servicioTramiteTraficoTransmision.cambiarEstadoTramite(tramiteTraficoTransmisionBean);
		if (!resultado.getError()) {
			// Actualizamos el estado de tramiteTraficoTransmisionBean para que lo que aparece en pantalla sea coherente con el estado del trámite
			if (resultado.getAttachment("tramiteTrafico") != null) {
				tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(((TramiteTraficoVO) resultado.getAttachment("tramiteTrafico")).getEstado().toString());
			}
			String proceso = null;
			if ("2631".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado())) {
				if (TipoTransferencia.tipo1.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia().toString()) || TipoTransferencia.tipo2.getValorEnum().equals(
						tramiteTraficoTransmisionBean.getTipoTransferencia().toString()) || TipoTransferencia.tipo3.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia()
								.toString())) {
					proceso = ProcesosAmEnum.FULLCTIT.getValorEnum();
				} else if (TipoTransferencia.tipo4.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia().toString())) {
					proceso = ProcesosAmEnum.NOTIFICATIONCTIT.getValorEnum();
				} else if (TipoTransferencia.tipo5.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia().toString())) {
					proceso = ProcesosAmEnum.TRADECTIT.getValorEnum();
				}
			} else {
				if (TipoTransferencia.tipo1.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia().toString()) || TipoTransferencia.tipo2.getValorEnum().equals(
						tramiteTraficoTransmisionBean.getTipoTransferencia().toString()) || TipoTransferencia.tipo3.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia()
								.toString())) {
					proceso = ProcesosEnum.FULLCTIT.getNombreEnum();
				} else if (TipoTransferencia.tipo4.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia().toString())) {
					proceso = ProcesosEnum.NOTIFICATIONCTIT.getNombreEnum();
				} else if (TipoTransferencia.tipo5.getValorEnum().equals(tramiteTraficoTransmisionBean.getTipoTransferencia().toString())) {
					proceso = ProcesosEnum.TRADECTIT.getNombreEnum();
				}
			}
			ResultBean resulCola = servicioCola.crearSolicitud(proceso, null, gestorPropiedades.valorPropertie("nombreHostSolicitud"), TipoTramiteTrafico.TransmisionElectronica.getValorEnum(),
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente().toString(), tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdUsuario(), null,
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			if (resulCola.getError()) {
				addActionError("Ha sucedido un error a la hora de encolar la transmisión para su finalización.");
			}
		} else {
			addActionError("Ha ocurrido un error a la hora de cambiar el estado.");
		}
		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	public String completarXmlConFirmaColegiado() throws Throwable {
		ResultBean resultBeanFirmaColegiado = new ResultBean();
		resultBeanFirmaColegiado.addAttachment("solicitud_registro_entrada", request.getSession().getAttribute("solicitud_registro_entrada"));
		resultBeanFirmaColegiado.addAttachment("firmaColegiado", request.getSession().getAttribute("firmaColegiado"));

		ResultBean resultBeanSolicitudConfirmaColegiado = getModeloTransmision().anhadirFirmaColegiado(resultBeanFirmaColegiado);

		ResultBean resultBeanConFirmas = getModeloTransmision().firmarColegio((SolicitudRegistroEntrada) resultBeanSolicitudConfirmaColegiado.getAttachment("solicitud_registro_entrada"),
				tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());

		SolicitudRegistroEntrada solicitudRegistroEntrada = (SolicitudRegistroEntrada) resultBeanConFirmas.getAttachment("solicitud_registro_entrada");

		fileNameTransTelematica = (String) request.getSession().getAttribute("fileNameTransTelematica");

		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalle((BigDecimal) request.getSession().getAttribute("numExpediente"));
		tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		XmlTransTelematicaFactory xmlTransTelematicaFactory = new XmlTransTelematicaFactory();
		try {
			Marshaller marshaller = xmlTransTelematicaFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudRegistroEntrada, new FileOutputStream(fileNameTransTelematica));
		} catch (JAXBException e) {
			addActionError("Ha ocurrido un error al parsear el XML");
		} catch (FileNotFoundException e) {
			addActionError("No se ha encontrado el fichero XML");
		}

		// Lo validamos contra el XSD
		File fichero = new File(fileNameTransTelematica);
		String resultadoValidar = xmlTransTelematicaFactory.validarXMLTransTelematica(fichero);
		if (!ConstantesTrafico.XML_VALIDO.equals(resultadoValidar)) {
			addActionError("Ha ocurrido un error al validar el XML generado para realizar la transferencia telemática contra el XSD");
			mantenerAcciones();
			return ALTAS_TRAMITE_TRANSMISION;
		}

		// Se añade la solicitud a la cola
		if (tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo1.getValorEnum())
				|| // Antes tipo1
				tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo2.getValorEnum())
				|| tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo3.getValorEnum())) {
			crearSolicitud(fichero.getName(), ConstantesProcesos.PROCESO_FULLCTIT);
		} else if (tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo4.getValorEnum())) {// Antes tipo2
			crearSolicitud(fichero.getName(), ConstantesProcesos.PROCESO_NOTIFICATIONCTIT);
		} else if (tramiteTraficoTransmisionBean.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())) {// Antes tipo3
			crearSolicitud(fichero.getName(), ConstantesProcesos.PROCESO_TRADECTIT);
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	public String checkCTITConFirmaColegiado() throws Throwable {
		ResultBean resultBeanFirmaColegiado = new ResultBean();
		resultBeanFirmaColegiado.addAttachment("solicitud_tramite", request.getSession().getAttribute("solicitud_tramite"));
		resultBeanFirmaColegiado.addAttachment("firmaColegiado", request.getSession().getAttribute("firmaColegiado"));

		ResultBean resultBeanSolicitudConfirmaColegiado = getModeloTransmision().anhadirFirmaColegiadoCheckCTIT(resultBeanFirmaColegiado);

		SolicitudTramite solicitudTramite = (SolicitudTramite) resultBeanSolicitudConfirmaColegiado.getAttachment("solicitud_tramite");

		fileNameCheckCTIT = (String) request.getSession().getAttribute("fileNameCheckCTIT");

		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalle((BigDecimal) request.getSession().getAttribute("numExpediente"));
		tramiteTraficoTransmisionBean = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);
		XmlCheckCTITFactory xmlCheckCTITFactory = new XmlCheckCTITFactory();
		try {
			Marshaller marshaller = xmlCheckCTITFactory.getContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(solicitudTramite, new FileOutputStream(fileNameCheckCTIT));
		} catch (JAXBException e) {
			addActionError("Ha ocurrido un error al parsear el XML");
		} catch (FileNotFoundException e) {
			addActionError("No se ha encontrado el fichero XML");
		}

		// Lo validamos contra el XSD
		File fichero = new File(fileNameCheckCTIT);
		// Ricardo Rodríguez. Incidencia 3149. 20/12/2012
		try {
			xmlCheckCTITFactory.validarXMLCheckCTIT(fichero);
		} catch (OegamExcepcion ex) {
			addActionError(ex.getMensajeError1());
			mantenerAcciones();
			return ALTAS_TRAMITE_TRANSMISION;
		}
		// XmlCheckCTITFactory.validarXMLCheckCTIT(fichero);
		// Fin. Ricardo Rodriguez. Incidencia 3149. 20/12/2012

		// Se añade la solicitud a la cola
		crearSolicitud(fichero.getName(), ConstantesProcesos.PROCESO_CHECKCTIT);

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Añade una fila en la tabla de colas para su envio a DGT
	 * @param nombreXml
	 * @param nombreProceso
	 * @return
	 * @throws Throwable
	 */
	private String crearSolicitud(String nombreXml, String nombreProceso) throws Throwable {
		BeanPQCrearSolicitud beanPQCrearSolicitud = new BeanPQCrearSolicitud();
		beanPQCrearSolicitud.setP_ID_TRAMITE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		beanPQCrearSolicitud.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		beanPQCrearSolicitud.setP_TIPO_TRAMITE(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());
		beanPQCrearSolicitud.setP_XML_ENVIAR(nombreXml);
		beanPQCrearSolicitud.setP_PROCESO(nombreProceso);

		HashMap<String, Object> resultadoSolicitud = getModeloSolicitud().crearSolicitud(beanPQCrearSolicitud);

		ResultBean resultBean = (ResultBean) resultadoSolicitud.get(ConstantesPQ.RESULTBEAN);

		if (resultBean.getError() == false) {
			addActionMessage("Solicitud creada correctamente");

			BeanPQCambiarEstadoTramite beanPQCambiarEstadoTramite = new BeanPQCambiarEstadoTramite();
			if (nombreProceso.equals(ConstantesProcesos.PROCESO_CHECKCTIT)) {
				beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Check_Ctit.getValorEnum()));
			} else {
				beanPQCambiarEstadoTramite.setP_ESTADO(new BigDecimal(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum()));
			}
			beanPQCambiarEstadoTramite.setP_NUM_EXPEDIENTE(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());

			HashMap<String, Object> resultadoCambiarEstado = getModeloTrafico().cambiarEstadoTramite(beanPQCambiarEstadoTramite);

			ResultBean resultBeanCambiarEstado = (ResultBean) resultadoCambiarEstado.get(ConstantesPQ.RESULTBEAN);
			if (resultBeanCambiarEstado.getError()) {
				addActionError("Error al cambiar el estado del trámite");
				for (String mensaje : resultBeanCambiarEstado.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				if (nombreProceso.equals(ConstantesProcesos.PROCESO_CHECKCTIT)) {
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Check_Ctit.getValorEnum());
				} else {
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().setEstado(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum());
				}
			}

			if (docBaseCarpetaTramiteBean == null) {
				docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
			}
		} else {
			addActionError("Error al crear la solicitud");
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Método que busca un interviniente en BBDD por el DNI y devuelve todos los datos del interviniente.
	 * @return
	 * @throws Throwable
	 */
	public String buscarInterviniente() throws Throwable {
		Persona persona = new Persona(true);

		// Si el interviniente que hay que buscar es un Arrendatario
		if (tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.Arrendatario.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getArrendatarioBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setArrendatarioBean(interviniente);

		} else if // Si el interviniente que hay que buscar es el Representante Arrendatario
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteArrendatario.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getRepresentanteArrendatarioBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentanteArrendatarioBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Adquiriente
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.Adquiriente.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setAdquirienteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Representante Adquiriente
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteAdquiriente.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentanteAdquirienteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Transmitente
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.TransmitenteTrafico.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setTransmitenteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Representante Transmitente
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteTransmitente.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getRepresentanteTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentanteTransmitenteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Primer Cotitular
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.CotitularTransmision.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getPrimerCotitularTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setPrimerCotitularTransmitenteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Representante del primer cotitular del Transmitente
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentantePrimerCotitularTransmision.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentantePrimerCotitularTransmitenteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Segundo Cotitular
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals("Segundo Cotitular Transmision")) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getSegundoCotitularTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setSegundoCotitularTransmitenteBean(interviniente);
		} else if // Si el interviniente que hay que buscar es un Representante del segundo cotitular del Transmitente
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteSegundoCotitularTransmision.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentanteSegundoCotitularTransmitenteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Compraventa(Poseedor)
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.Compraventa.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setPoseedorBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Representante Compraventa(Poseedor)
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteCompraventa.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getRepresentantePoseedorBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentantePoseedorBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Presentador
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.PresentadorTrafico.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setPresentadorBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Conductor
		(tipoIntervinienteBuscar != null && tipoIntervinienteBuscar.equals(TipoInterviniente.ConductorHabitual.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() && !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean()
					.getNumColegiado())) ? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() : utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(tramiteTraficoTransmisionBean.getConductorHabitualBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setConductorHabitualBean(interviniente);
		}
		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	public String validarYGenerarJustificantePro() throws Throwable {
		try {
			if(tramiteTraficoTransmisionBean == null || tramiteTraficoTransmisionBean.getTramiteTraficoBean() == null){
				addActionError("No existen datos del trámite para realizar el justificante.");
				return ALTAS_TRAMITE_TRANSMISION;
			}
			// Nueva versión de justificantes con servicios e hibernate
			String justificantesProfNuevos = gestorPropiedades.valorPropertie("justificantes.profesional.nuevos");
			if("SI".equalsIgnoreCase(justificantesProfNuevos)){
				TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), Boolean.TRUE);
				if(tramiteTrafTranDto != null){
					if(tramiteTrafTranDto.getTipoTransferencia() != null && TipoTransferencia.tipo5.getValorEnum().equals(tramiteTrafTranDto.getTipoTransferencia())){
						addActionError("Error al generar el Justificante: no se puede emitir un Justificante para una Entraga Compraventa");
					} else {
						ResultBean resultado = servicioTramiteTraficoTransmision.generarJustitificanteProf(tramiteTrafTranDto, utilesColegiado.getIdUsuarioSessionBigDecimal(),motivoJustificantes, documentoJustificantes, diasValidezJustificantes);
						if(resultado.getError()){
							addActionError(resultado.getMensaje());
						} else {
							addActionMessage(resultado.getMensaje());
						}
					}
				} else {
					addActionError("No existen datos del trámite para poder generar el justificante");
				}
			}else{
				if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES)) {
					addActionError("No tiene permiso para realizar un justificante.");
					return ALTAS_TRAMITE_TRANSMISION;
				}
				if (UtilesVistaTrafico.getInstance().esConsultableOGuardableTransmision(tramiteTraficoTransmisionBean.getTramiteTraficoBean())) {
					guardarTramite();
				}
				String habilitarJustificantesNuevo = gestorPropiedades.valorPropertie("habilitar.justificante.nuevo");

				if (habilitarJustificantesNuevo != null && "SI".equals(habilitarJustificantesNuevo)) {
					if (TipoTransferencia.tipo5.equals(tramiteTraficoTransmisionBean.getTipoTransferencia())) {
						addActionError("Error al generar el Justificante: no se puede emitir un Justificante para una Entraga Compraventa");
					} else {
						JustificanteProfDto justificanteProfDto = new JustificanteProfDto();

						generarDtoTransmisionToJustificante(tramiteTraficoTransmisionBean);
						ResultBean result = servicioJustificanteProfesional.generarJustificanteTransmision(tramiteTrafTranDto, justificanteProfDto, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());

						if (result != null) {
							String mensajes = "";
							for (String mensaje : result.getListaMensajes()) {
								mensajes = mensajes + mensaje + " ";
							}

							if (!result.getError()) {
								addActionMessage(mensajes);
							} else {
								if (result.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES) != null) {
									numPeticiones = (Integer) result.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES);
									if (numPeticiones == 1 || numPeticiones == 2) {
										tipoTramiteJustificante = TipoTramiteTrafico.TransmisionElectronica.getValorEnum();
										return ALTA_TRAMITE_TRAFICO_JUSTIFICANTE;
									}
								}
								addActionError("Error al generar el Justificante: " + mensajes);
							}
						}
					}
				} else {
					getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionElectronica(tramiteTraficoTransmisionBean, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
							utilesColegiado.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
							ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
					addActionMessage("Solicitud de justificante firmada y enviada");
				}
			}
		} catch (ValidacionJustificanteRepetidoExcepcion e) {
			log.info("Traza de prueba cuando un justificante esta repetido. Expediente: " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
			if ("SI".equals(Constantes.PERMITE_SER_FORZADO)) {
				log.info("En el true de permite ser forzado.");
				servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
				addActionError("Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
			} else {
				log.info("En el false de permite ser forzado.");
				addActionError("Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + "; " + Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION);
			}
		} catch (ValidacionJustificantePorFechaExcepcion e) {
			servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
			addActionError("Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + "; Ya se ha emitido un Justificante profesional para la matrícula: "
					+ tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getMatricula()
					+ " y distinto adquiriente en menos de 30 días. Si quiere volver a generar un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");

		} catch (SAXParseException spe) {
			String error = " Error en la generación de Justificante. - " + getModeloJustificanteProfesional().parseSAXParseException(spe);
			addActionError(error);
			log.error("Error al generar el justificante", spe);
		} catch (OegamExcepcion e) {
			String error = "Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + ": Error en la generación de Justificante. ";
			error += " - " + e.getMensajeError1();
			addActionError(error);
		}
		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	private void generarDtoTransmisionToJustificante(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean2) {
		tramiteTrafTranDto = new TramiteTrafTranDto();
		tramiteTrafTranDto.setEstado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum());
		tramiteTrafTranDto.setNumExpediente(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		tramiteTrafTranDto.setFechaPresentacion(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getFechaPresentacion());
		tramiteTrafTranDto.setRefPropia(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getReferenciaPropia());
		tramiteTrafTranDto.setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getValorEnum());

		if (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() == null) {
			tramiteTrafTranDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		} else {
			tramiteTrafTranDto.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
		}

		if (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato() == null) {
			tramiteTrafTranDto.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		} else {
			tramiteTrafTranDto.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
		}

		tramiteTrafTranDto.setAdquiriente(convertirIntervinienteBeanADto(tramiteTraficoTransmisionBean.getAdquirienteBean()));
		tramiteTrafTranDto.setVehiculoDto(convertirVehiculoBeanADto(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo()));
		tramiteTrafTranDto.setJefaturaTraficoDto(convertirJefaturaBeanADto(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getJefaturaTrafico()));
	}

	private IntervinienteTraficoDto convertirIntervinienteBeanADto(IntervinienteTrafico adquiriente) {
		IntervinienteTraficoDto intervinienteTraficoDto = new IntervinienteTraficoDto();
		intervinienteTraficoDto.setNifInterviniente(adquiriente.getNifInterviniente());

		if (adquiriente.getPersona() != null) {
			PersonaDto personaDto = new PersonaDto();
			personaDto.setNif(adquiriente.getPersona().getNif());
			intervinienteTraficoDto.setNifInterviniente(adquiriente.getPersona().getNif());
			personaDto.setApellido1RazonSocial(adquiriente.getPersona().getApellido1RazonSocial());

			if (adquiriente.getPersona().getTipoPersona() != null) {
				personaDto.setTipoPersona(adquiriente.getPersona().getTipoPersona().getValorEnum());
			}

			personaDto.setSexo(adquiriente.getPersona().getSexo());
			personaDto.setApellido2(adquiriente.getPersona().getApellido2());
			personaDto.setNombre(adquiriente.getPersona().getNombre());
			personaDto.setAnagrama(adquiriente.getPersona().getAnagrama());
			personaDto.setFechaNacimiento(utilesFecha.getFechaFracionada(adquiriente.getPersona().getFechaNacimiento()));
			personaDto.setTelefonos(adquiriente.getPersona().getTelefonos());

			intervinienteTraficoDto.setPersona(personaDto);

			if (adquiriente.getPersona().getDireccion() != null) {
				DireccionDto direccionDto = new DireccionDto();
				direccionDto.setIdProvincia(adquiriente.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());
				direccionDto.setIdMunicipio(adquiriente.getPersona().getDireccion().getMunicipio().getIdMunicipio());
				direccionDto.setIdTipoVia(adquiriente.getPersona().getDireccion().getTipoVia().getIdTipoVia());

				direccionDto.setPueblo(adquiriente.getPersona().getDireccion().getPueblo());
				direccionDto.setCodPostal(adquiriente.getPersona().getDireccion().getCodPostal());
				direccionDto.setNombreVia(adquiriente.getPersona().getDireccion().getNombreVia());
				direccionDto.setNumero(adquiriente.getPersona().getDireccion().getNumero());
				direccionDto.setLetra(adquiriente.getPersona().getDireccion().getLetra());
				direccionDto.setEscalera(adquiriente.getPersona().getDireccion().getEscalera());
				direccionDto.setPlanta(adquiriente.getPersona().getDireccion().getPlanta());
				direccionDto.setPuerta(adquiriente.getPersona().getDireccion().getPuerta());
				direccionDto.setBloque(adquiriente.getPersona().getDireccion().getBloque());
				if (adquiriente.getPersona().getDireccion().getHm() != null && !adquiriente.getPersona().getDireccion().getHm().isEmpty()) {
					direccionDto.setHm(new BigDecimal(adquiriente.getPersona().getDireccion().getHm()));
				}
				if (adquiriente.getPersona().getDireccion().getPuntoKilometrico() != null && !adquiriente.getPersona().getDireccion().getPuntoKilometrico().isEmpty()) {
					direccionDto.setKm(new BigDecimal(adquiriente.getPersona().getDireccion().getPuntoKilometrico()));
				}

				intervinienteTraficoDto.setDireccion(direccionDto);
			}
		}
		return intervinienteTraficoDto;
	}

	private VehiculoDto convertirVehiculoBeanADto(VehiculoBean vehiculo) {
		VehiculoDto vehiculoDto = new VehiculoDto();

		if (vehiculo.getMarcaBean() != null && vehiculo.getMarcaBean().getCodigoMarca() != null) {
			vehiculoDto.setCodigoMarca(vehiculo.getMarcaBean().getCodigoMarca().toString());
		}

		vehiculoDto.setMatricula(vehiculo.getMatricula());
		vehiculoDto.setBastidor(vehiculo.getBastidor());
		vehiculoDto.setModelo(vehiculo.getModelo());

		if (vehiculo.getTipoVehiculoBean() != null) {
			vehiculoDto.setTipoVehiculo(vehiculo.getTipoVehiculoBean().getTipoVehiculo());
		}
		return vehiculoDto;
	}

	private JefaturaTraficoDto convertirJefaturaBeanADto(JefaturaTrafico jefatura) {
		JefaturaTraficoDto jefaturaTraficoDto = new JefaturaTraficoDto();
		jefaturaTraficoDto.setJefaturaProvincial(jefatura.getJefaturaProvincial());
		return jefaturaTraficoDto;
	}

	// Mantis 19262. David Sierra. Validación para comprobar que las fechas obligatorias introducidas tienen un formato correcto
	private String validarFechasTransmision(TramiteTraficoTransmisionBean tramite) {
		String resultadoValidacion = "OK";
		if (tramite == null) {
			return "No existen datos del trámite para guardar.";
		}
		if (tramite.getTramiteTraficoBean() != null) {
			if (tramite.getTramiteTraficoBean().getFechaPresentacion() != null
					&& !tramite.getTramiteTraficoBean().getFechaPresentacion().isfechaNula()
					&& !utilesFecha.validarFormatoFecha(tramite.getTramiteTraficoBean().getFechaPresentacion())) {
				return "La fecha de presentación del trámite que ha introducido no es válida.";
			}
			if (tramite.getTramiteTraficoBean().getVehiculo() != null) {
				if (tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion() != null
						&& !tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion().isfechaNula()
						&& !utilesFecha.validarFormatoFecha(tramite.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion())) {
					return "La fecha de matriculación del vehículo no es válida.";
				}
				if (tramite.getTramiteTraficoBean().getVehiculo().getFechaInspeccion() != null
						&& !tramite.getTramiteTraficoBean().getVehiculo().getFechaInspeccion().isfechaNula()
						&& !utilesFecha.validarFormatoFecha(tramite.getTramiteTraficoBean().getVehiculo().getFechaInspeccion())) {
					return "La fecha de ITV del vehículo no es válida.";
				}
				if (tramite.getTramiteTraficoBean().getVehiculo().getFechaItv() != null
						&& !tramite.getTramiteTraficoBean().getVehiculo().getFechaItv().isfechaNula()
						&& !utilesFecha.validarFormatoFecha(tramite.getTramiteTraficoBean().getVehiculo().getFechaItv())) {
					return "La fecha de caducidad ITV del vehículo no es válida.";
				}
				if (tramite.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri() != null
						&& !tramite.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri().isfechaNula()
						&& !utilesFecha.validarFormatoFecha(tramite.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri())) {
					return "La fecha de 1ª matriculación del modelo 620 no es válida.";
				}
				if (tramite.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm() != null
						&& !tramite.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm().isfechaNula()
						&& !utilesFecha.validarFormatoFecha(tramite.getTramiteTraficoBean().getVehiculo().getFechaLecturaKm())) {
					return "La fecha de lectura de kilometraje del vehículo no es válida.";
				}
			}
		}
		if (tramite.getAdquirienteBean() != null
				&& tramite.getAdquirienteBean().getPersona() != null
				&& tramite.getAdquirienteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getAdquirienteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getAdquirienteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el adquiriente no es válida.";
		}
		if (tramite.getConductorHabitualBean() != null
				&& tramite.getConductorHabitualBean().getPersona() != null
				&& tramite.getConductorHabitualBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getConductorHabitualBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getConductorHabitualBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el conductor habitual no es válida.";
		}
		if (tramite.getRepresentanteAdquirienteBean() != null
				&& tramite.getRepresentanteAdquirienteBean().getPersona() != null
				&& tramite.getRepresentanteAdquirienteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getRepresentanteAdquirienteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getRepresentanteAdquirienteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el representante del adquiriente no es válida.";
		}
		if (tramite.getTransmitenteBean() != null
				&& tramite.getTransmitenteBean().getPersona() != null
				&& tramite.getTransmitenteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getTransmitenteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getTransmitenteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el transmitente no es valida.";
		}
		if (tramite.getRepresentanteTransmitenteBean() != null
				&& tramite.getRepresentanteTransmitenteBean().getPersona() != null
				&& tramite.getRepresentanteTransmitenteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getRepresentanteTransmitenteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getRepresentanteTransmitenteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el representante del transmitente no es válida.";
		}
		if (tramite.getPrimerCotitularTransmitenteBean() != null
				&& tramite.getPrimerCotitularTransmitenteBean().getPersona() != null
				&& tramite.getPrimerCotitularTransmitenteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getPrimerCotitularTransmitenteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getPrimerCotitularTransmitenteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el primer cotitular no es válida.";
		}
		if (tramite.getRepresentantePrimerCotitularTransmitenteBean() != null
				&& tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona() != null
				&& tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getRepresentantePrimerCotitularTransmitenteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getRepresentantePrimerCotitularTransmitenteBean()
						.getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el representante del primer cotitular no es válida.";
		}
		if (tramite.getSegundoCotitularTransmitenteBean() != null
				&& tramite.getSegundoCotitularTransmitenteBean().getPersona() != null
				&& tramite.getSegundoCotitularTransmitenteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getSegundoCotitularTransmitenteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getSegundoCotitularTransmitenteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para el segundo cotitular no es válida.";
		}
		if (tramite.getRepresentanteSegundoCotitularTransmitenteBean() != null
				&& tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona() != null
				&& tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getFechaCaducidadNif() != null
				&& !tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getFechaCaducidadNif().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getRepresentanteSegundoCotitularTransmitenteBean().getPersona().getFechaCaducidadNif())) {
			return "La fecha de caducidad del NIF que ha introducido para representante del segundo cotitular no es válida.";
		}
		if (tramite.getFechaDevengoItp620() != null && !tramite.getFechaDevengoItp620().isfechaNula()
				&& !utilesFecha.validarFormatoFecha(tramite.getFechaDevengoItp620())) {
			return "La fecha de devengo del modelo 620 no es válida.";
		}
		return resultadoValidacion;
	}

	/**
	 * Genera un justificante sin validar datos. Se utilizará cuando se consiga en doble submit a través del Confirm, en un futuro.
	 * @return
	 * @throws Throwable
	 */
	public String generarJustificantePro() throws Throwable {
		try {
			getModeloJustificanteProfesional().generarJustificanteTransmisionElectronica(tramiteTraficoTransmisionBean, utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(),
					utilesColegiado.getIdContratoSessionBigDecimal(), ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(), utilesColegiado.getNumColegiadoSession(),
					ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO, ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
			addActionMessage("Solicitud de justificante firmada y enviada");
		} catch (OegamExcepcion e) {
			addActionError(e.getMessage());
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Método para mantener las acciones de un trámite según se vayan realizando acciones con el mismo.
	 * @throws Throwable
	 */
	public void mantenerAcciones() throws Throwable {
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		if (tramiteTraficoTransmisionBean != null &&
				tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null &&
				null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()) {
			// Incorporamos la obtención de acciones por trámite
			String numExpediente = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente().toString();
			listaAcciones = UtilidadesAccionTrafico.getInstance().generarListaAcciones(numExpediente);
		}
		// Fin Acciones
	}

	public String consultaDireccionVehiculo() {
		ServicioVehiculo servicioVehiculo = ContextoSpring.getInstance().getBean(ServicioVehiculo.class);

		if (idVehiculo != null && !idVehiculo.isEmpty()) {
			VehiculoDto vehiculoDto = servicioVehiculo.getVehiculoDto(Long.valueOf(idVehiculo), null, null, null, null, null);

			if (vehiculoDto != null && vehiculoDto.getDireccion() != null) {
				Direccion direccion = new Direccion();

				Municipio municipio = new Municipio();
				TipoVia tipoVia = new TipoVia();
				Provincia provincia = new Provincia();

				direccion.setIdDireccion(vehiculoDto.getDireccion().getIdDireccion().intValue());

				provincia.setIdProvincia(vehiculoDto.getDireccion().getIdProvincia());
				municipio.setProvincia(provincia);
				municipio.setIdMunicipio(vehiculoDto.getDireccion().getIdMunicipio());
				direccion.setMunicipio(municipio);
				direccion.setCodPostal(vehiculoDto.getDireccion().getCodPostal());
				direccion.setPueblo(vehiculoDto.getDireccion().getPueblo());
				tipoVia.setIdTipoVia(vehiculoDto.getDireccion().getIdTipoVia());
				direccion.setTipoVia(tipoVia);
				direccion.setNombreVia(vehiculoDto.getDireccion().getNombreVia());
				direccion.setNumero(vehiculoDto.getDireccion().getNumero());
				direccion.setLetra(vehiculoDto.getDireccion().getLetra());
				direccion.setEscalera(vehiculoDto.getDireccion().getEscalera());
				direccion.setPlanta(vehiculoDto.getDireccion().getPlanta());
				direccion.setPuerta(vehiculoDto.getDireccion().getPuerta());
				direccion.setBloque(vehiculoDto.getDireccion().getBloque());
				if (vehiculoDto.getDireccion().getKm() != null) {
					direccion.setPuntoKilometrico(vehiculoDto.getDireccion().getKm().toString());
				}
				if (vehiculoDto.getDireccion().getHm() != null) {
					direccion.setHm(vehiculoDto.getDireccion().getHm().toString());
				}
				tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().setDireccionBean(direccion);
			}
		}
		return ALTAS_TRAMITE_TRANSMISION;
	}

	public String descargarDocBase() {
		try {
			if (docBaseCarpetaTramiteBean == null) {
				docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
			}

			if (docBaseCarpetaTramiteBean != null) {
				ResultadoDocBaseBean resultado = servicioGestionDocBase.descargarDocBase(docBaseCarpetaTramiteBean.getId().toString());
				inputStream = new FileInputStream(resultado.getFichero());
				if (inputStream == null) {
					addActionError("No se puede recuperar el PDF del documento base para el trámite: '" + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + "'");
					return ALTAS_TRAMITE_TRANSMISION;
				}
				fileName = resultado.getNombreFichero();
			} else {
				addActionError("No se puede recuperar el documento base para el trámite: '" + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + "'");
				return ALTAS_TRAMITE_TRANSMISION;
			}
			return DESCARGAR_DOCUMENTO_BASE;
		} catch (Exception e) {
			addActionError("No se puede recuperar el documento base para el trámite: '" + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente() + "'");
			log.error(e.getMessage());
			return ALTAS_TRAMITE_TRANSMISION;
		}
	}
	
	public String descargarDocumentacion() {
		ArrayList<FicheroInfo> ficheros = null;

		try {
			ficheros = servicioTramiteTraficoTransmision.recuperarDocumentos(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());

			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(nombreDoc)) {
						File fichero = tmp.getFichero();
						setInputStream(new FileInputStream(fichero));
						setFicheroDescarga(tmp.getNombre());
						return "descargarDocumento";
					}
				}
			} else {
				addActionError("No existe fichero para descargar.");
			}
			log.info("TRÁMITE DUPLICADO: Fin -- descargarDocumentacion.");
		} catch (FileNotFoundException ex) {
			addActionError("No se ha podido recuperar el fichero");
			log.error("TRÁMITE DUPLICADO: Error -- Error en descargarDocumentacion.");
		} catch (Exception ex) {
			log.error(ex);
			log.error(UtilesExcepciones.stackTraceAcadena(ex, 3));
			addActionError(ex.toString());
			addActionError(UtilesExcepciones.stackTraceAcadena(ex, 3));
		}

		return ALTAS_TRAMITE_TRANSMISION;
	}
	
	public String eliminarFichero() {
//		guardarTramiteTransmision();
		try {
			if (tramiteTraficoTransmisionBean == null) {
				log.error("TRÁMITE DUPLICADO: Error -- No se ha recuperado tramite en curso de la sesion.");
				return ALTAS_TRAMITE_TRANSMISION;
			}

			ArrayList<FicheroInfo> ficheros = servicioTramiteTraficoTransmision
					.recuperarDocumentos(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());

			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(idFicheroEliminar)) {
						tmp.getFichero().delete();
						it.remove();
						tramiteTraficoTransmisionBean.setFicherosSubidos(ficheros);
						addActionMessage("Fichero eliminado correctamente.");
						return ALTAS_TRAMITE_TRANSMISION;
					}
				}
			} else {
				addActionError("No existe fichero para eliminar.");
			}
		} catch (Exception e) {
			log.error(e);
			log.error(UtilesExcepciones.stackTraceAcadena(e, 3));
			addActionError(e.getMessage());
		}
		return ALTAS_TRAMITE_TRANSMISION;
	}
	
	public String incluirFichero() throws Exception, OegamExcepcion {
//		guardarTramiteTransmision();
		try {
			if (!hasActionErrors()) {
				if (fichero != null) {
					// Traemos los ficheros asociados al trámite
					ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteTraficoTransmision
							.recuperarDocumentos(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
					// Se comenta porque se quiere que siempre que se suba un fichero se sobreescriba el que estaba antes
//					if (ficherosSubidos == null) {
					ficherosSubidos = new ArrayList<FicheroInfo>();
//					}
					
					// Verifica que el nombre del fichero sea válido.
					if (!utiles.esNombreFicheroValido(ficheroFileName)) {
						addActionError("El fichero añadido debe tener un nombre correcto");
						log.error(
								"TRÁMITE CTIT: Error -- El nombre del fichero que se intenta subir no es válido: "
										+ ficheroFileName);
						tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),Boolean.TRUE);
						return ALTAS_TRAMITE_TRANSMISION;
					}
					// Verifica que el fichero subido tiene un formato soportado.
					String extension = utiles.dameExtension(ficheroFileName, false);
					if (extension == null) {
						addActionError("El fichero añadido debe tener un formato soportado: .pdf");
						log.error(
								"TRÁMITE CTIT: Error -- El nombre del fichero que se intenta subir no es válido: "
										+ ficheroFileName);
						tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),Boolean.TRUE);
						return ALTAS_TRAMITE_TRANSMISION;
					}
					// Verifica que solo se aporten PDFs
					if (!extension.equalsIgnoreCase("PDF")) {
						addActionError("El fichero añadido debe tener un formato soportado: .pdf");
						log.error(
								"TRÁMITE CTIT: Error -- El nombre del fichero que se intenta subir no es válido: "
										+ ficheroFileName);
						tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(),Boolean.TRUE);
						return ALTAS_TRAMITE_TRANSMISION;
					}
					// Se guarda el fichero
					try {
						File fichero = guardarFichero(extension);
						FicheroInfo ficheroInfo = new FicheroInfo(fichero, ficherosSubidos.size());
						ficherosSubidos.add(ficheroInfo);
						addActionMessage("Fichero añadido correctamente.");
						tramiteTraficoTransmisionBean.setFicherosSubidos(ficherosSubidos);
						log.info("TRÁMITE CTIT: Fin del guardado del fichero.");
					} catch (Exception e) {
						log.error("TRÁMITE CTIT: Error -- " + e);
						addActionError("Se ha lanzado una excepción a la hora de guardar el fichero.");
					}
				} else {
					addActionError(
							"Utilice el botón 'Elegir archivo' para seleccionar el fichero a adjuntar en el envío.");
				}
			} else {
				if (fichero != null) {
					addActionError("No se ha guardado el fichero");
				} else {
					addActionError(
							"Utilice el botón 'Elegir archivo' para seleccionar el fichero a adjuntar en el envío.");
				}
			}
		} catch (Exception e) {
			log.error("TRÁMITE DUPLICADO: Error -- " + e);
			addActionError("Se ha lanzado una excepción Oegam relacionada con el fichero de propiedades.");
		}


		return ALTAS_TRAMITE_TRANSMISION;
	}
	
	private File guardarFichero(String extension) throws Exception, OegamExcepcion {
		Long idFichero = new Date().getTime();
		FileInputStream fis = new FileInputStream(fichero);
		byte[] array = IOUtils.toByteArray(fis);
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.CTIT);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.DOCUMENTOS_SUBIDOS);
		ficheroBean.setExtension("." + extension);
		ficheroBean.setFicheroByte(array);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento("fichero_ivtm_" + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado() + "_" + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getMatricula());
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}
	
public String descargarAnexoHerencia(){
		try{
			File fichero = null;
			// Muestra el manual con la parte de administración dependiendo de si el usuario en sesión tiene
			// o no los permisos de un administrador:
				fichero = new File("C:/datos/descargas/ANEXO_I.pdf");
			inputStream = new FileInputStream(fichero);
			return "descargarAnexoHerencia";
		}catch(FileNotFoundException ex){
			addActionError("No se ha podido recuperar el manual");
			log.error(ex);
			return Action.ERROR;
		}catch(Exception ex){
			addActionError("No se ha podido recuperar el manual");
			return Action.ERROR;
		}
	}
	
	
	// GETTERS & SETTERS

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
	}

	public HashMap<String, Object> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	public void setParametrosBusqueda(HashMap<String, Object> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	public PaginatedList getListaAcciones() {
		return listaAcciones;
	}

	public void setListaAcciones(PaginatedList listaAcciones) {
		this.listaAcciones = listaAcciones;
	}

	public void setTramiteTraficoTransmisionBean(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean) {
		this.tramiteTraficoTransmisionBean = tramiteTraficoTransmisionBean;
	}

	public String getMensajeErrorFormulario() {
		return mensajeErrorFormulario;
	}

	public void setMensajeErrorFormulario(String mensajeErrorFormulario) {
		this.mensajeErrorFormulario = mensajeErrorFormulario;
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

	public String getFileNameCheckCTIT() {
		return fileNameCheckCTIT;
	}

	public void setFileNameCheckCTIT(String fileNameCheckCTIT) {
		this.fileNameCheckCTIT = fileNameCheckCTIT;
	}

	public String getFileNameTransTelematica() {
		return fileNameTransTelematica;
	}

	public void setFileNameTransTelematica(String fileNameTransTelematica) {
		this.fileNameTransTelematica = fileNameTransTelematica;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public String getNui() {
		return nui;
	}

	public Boolean getFicheroXML620() {
		return ficheroXML620;
	}

	public void setFicheroXML620(Boolean ficheroXML620) {
		this.ficheroXML620 = ficheroXML620;
	}

	public void setNui(String nui) {
		this.nui = nui;
	}

	public boolean isJustificanteRepetido() {
		return justificanteRepetido;
	}

	public void setJustificanteRepetido(boolean justificanteRepetido) {
		this.justificanteRepetido = justificanteRepetido;
	}

	/**
	 * @return the propTexto
	 */
	public String getPropTexto() {
		return propTexto;
	}

	/**
	 * @param propTexto the propTexto to set
	 */
	public void setPropTexto(String propTexto) {
		this.propTexto = propTexto;
	}

	/* ********************************************** */
	/* MODELOS ************************************** */
	/* ********************************************** */

	public String getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(String idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ModeloJustificanteProfesional getModeloJustificanteProfesional() {
		if (modeloJustificanteProfesional == null) {
			modeloJustificanteProfesional = new ModeloJustificanteProfesional();
		}
		return modeloJustificanteProfesional;
	}

	public void setModeloJustificanteProfesional(ModeloJustificanteProfesional modeloJustificanteProfesional) {
		this.modeloJustificanteProfesional = modeloJustificanteProfesional;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ServicioTramiteTraficoTransmision getServicioTramiteTraficoTransmision() {
		return servicioTramiteTraficoTransmision;
	}

	public void setServicioTramiteTraficoTransmision(ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision) {
		this.servicioTramiteTraficoTransmision = servicioTramiteTraficoTransmision;
	}

	public ServicioDatosSensibles getServicioDatosSensibles() {
		return servicioDatosSensibles;
	}

	public void setServicioDatosSensibles(ServicioDatosSensibles servicioDatosSensibles) {
		this.servicioDatosSensibles = servicioDatosSensibles;
	}

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
	}

	public TramiteTrafTranDto getTramiteTrafTranDto() {
		return tramiteTrafTranDto;
	}

	public void setTramiteTrafTranDto(TramiteTrafTranDto tramiteTrafTranDto) {
		this.tramiteTrafTranDto = tramiteTrafTranDto;
	}

	public int getNumPeticiones() {
		return numPeticiones;
	}

	public void setNumPeticiones(int numPeticiones) {
		this.numPeticiones = numPeticiones;
	}

	public String getTipoTramiteJustificante() {
		return tipoTramiteJustificante;
	}

	public void setTipoTramiteJustificante(String tipoTramiteJustificante) {
		this.tipoTramiteJustificante = tipoTramiteJustificante;
	}

	public DocBaseCarpetaTramiteBean getDocBaseCarpetaTramiteBean() {
		return docBaseCarpetaTramiteBean;
	}

	public void setDocBaseCarpetaTramiteBean(DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean) {
		this.docBaseCarpetaTramiteBean = docBaseCarpetaTramiteBean;
	}

	public String getMotivoJustificantes() {
		return motivoJustificantes;
	}

	public void setMotivoJustificantes(String motivoJustificantes) {
		this.motivoJustificantes = motivoJustificantes;
	}

	public String getDocumentoJustificantes() {
		return documentoJustificantes;
	}

	public void setDocumentoJustificantes(String documentoJustificantes) {
		this.documentoJustificantes = documentoJustificantes;
	}

	public String getDiasValidezJustificantes() {
		return diasValidezJustificantes;
	}

	public void setDiasValidezJustificantes(String diasValidezJustificantes) {
		this.diasValidezJustificantes = diasValidezJustificantes;
	}

	public String getTipoTramiteGenerar() {
		return tipoTramiteGenerar;
	}

	public void setTipoTramiteGenerar(String tipoTramiteGenerar) {
		this.tipoTramiteGenerar = tipoTramiteGenerar;
	}

	public String getTipoTasaUsada() {
		return tipoTasaUsada;
	}

	public void setTipoTasaUsada(String tipoTasaUsada) {
		this.tipoTasaUsada = tipoTasaUsada;
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

}