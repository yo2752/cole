package trafico.transmision.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.struts2.ServletActionContext;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.JefaturaTrafico;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.utiles.TransmisionTramiteTraficoBeanPQConversion;
import trafico.modelo.ModeloJustificanteProfesional;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.UtilidadesAccionTrafico;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoCreacion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.AccionException;
import utilidades.web.excepciones.ValidacionJustificantePorFechaExcepcion;
import utilidades.web.excepciones.ValidacionJustificanteRepetidoExcepcion;

@SuppressWarnings("serial")
public class AltasTramiteTransmisionActualAction extends ActionBase {

	private static final String ALTAS_TRAMITE_TRANSMISION = "altasTramiteTransmision";

	// Log para errores
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltasTramiteTransmisionActualAction.class);
	private String tipoTramiteGenerar;
	private TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean;
	private static final String POP_UP_JUSTIFICANTE_PRO = "popUpJustificanteProf";
	private String tipoTasa;
	private String mensajeErrorFormulario;
	private static final String XML_VALIDO = "CORRECTO";
	private InputStream inputStream; // Flujo de bytes del fichero a exportar
										// (download)
	private String fileName; // Nombre del fichero a exportar
	private Boolean ficheroXML620;
	private static final String CODIGO_PERMISO_GUARDAR_T2 = "OT2TA";

	private String tipoIntervinienteBuscar; // Para el botón de buscar
											// interviniente del DNI

	private PaginatedList listaAcciones;

	private String propTexto;

	private ModeloTransmision modeloTransmision;
	private ModeloJustificanteProfesional modeloJustificanteProfesional;

	private TramiteTrafTranDto tramiteTrafTranDto;
	private int numPeticiones;
	private String motivoJustificantes;
	private String documentoJustificantes;
	private String diasValidezJustificantes;
	private static final String ALTA_TRAMITE_TRAFICO_JUSTIFICANTE = "altaTramiteTraficoJustificante";

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	TransmisionTramiteTraficoBeanPQConversion transmisionTramiteTraficoBeanPQConversion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private String tipoTramiteJustificante;

	// MÉTODOS

	public String cargarPopUpJustificantesProf() {
		tipoTramiteGenerar = TipoTramiteTrafico.TransmisionElectronica.getValorEnum();
		return POP_UP_JUSTIFICANTE_PRO;
	}

	public String inicio() {
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		tramiteTraficoTransmisionBean = new TramiteTraficoTransmisionBean(true);
		tramiteTraficoTransmisionBean.getTramiteTraficoBean().getJefaturaTrafico()
				.setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		tramiteTraficoTransmisionBean.setElectronica("N");
		tramiteTraficoTransmisionBean.getTramiteTraficoBean()
				.setTipoTramite(TipoTramiteTrafico.Transmision.getValorEnum());
		limpiarCamposSession();
		return guardarTransmision(true);
	}

	public String guardarTramiteTransmision() {
		return guardarTransmision(false);
	}

	public String guardarTransmision(Boolean formularioInicial) {
		log.info("Transmision Tramite Trafico: inicio--guardarTramiteTransmision:");
		try {
			if (!utilesColegiado.tienePermisoSobreT2(CODIGO_PERMISO_GUARDAR_T2)) {
				addActionError("No tiene permiso para guardar un trámite de este tipo.");
				return ALTAS_TRAMITE_TRANSMISION;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			addActionError("No se ha podido guardar el tramite de transmisión");
			return ALTAS_TRAMITE_TRANSMISION;
		} catch (OegamExcepcion e) {
			log.error(e.getMessage());
			addActionError("No se ha podido guardar el tramite de transmisión");
			return ALTAS_TRAMITE_TRANSMISION;
		}

		if (formularioInicial) {
			log.info("Transmision Tramite Trafico: fin--  guardarTramiteTransmision: formulario inicial"
					+ formularioInicial);
			if (tramiteTraficoTransmisionBean.getPresentadorBean() != null
					&& tramiteTraficoTransmisionBean.getPresentadorBean().getPersona() != null
					&& (tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif() == null
							|| tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif().equals(""))
					&& tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null) {
					ModeloColegiado modeloColegiado = new ModeloColegiado();
					tramiteTraficoTransmisionBean.getPresentadorBean()
							.setPersona(modeloColegiado.obtenerColegiadoCompleto(
					tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato() != null
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato()
							: utilesColegiado.getIdContratoSessionBigDecimal()));
					tramiteTraficoTransmisionBean.getPresentadorBean()
							.setTipoInterviniente(TipoInterviniente.PresentadorTrafico.getValorEnum());
			}
		} else {
			// Llamo al metodo estático del modelo para guardar el trámite
			// después de recogerlo del formulario web.
			// GuardarAltaTramiteTransmision tiene el BeanPQ y realiza todos los
			// pasos necesarios.
			Map<String, Object> resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(
					transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(
							tramiteTraficoTransmisionBean),
					tramiteTraficoTransmisionBean);
			ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);

			log.debug("resultado = " + resultBean.getMensaje());

			if (!resultBean.getError()) {
				setTramiteTraficoTransmisionBean(
						(TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
				addActionMessage("Trámite guardado");
			} else {
				addActionError("No se ha podido guardar el tramite de transmisión");
			}

			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}

			try {
				mantenerAcciones();
			} catch (Throwable e) {
				log.error("Error en la llamada al manterAcciones desde guardarTransmision", e);
				addActionError(e.toString());
			}
		}

		return ALTAS_TRAMITE_TRANSMISION;
	}

	public String validar620() throws Throwable {
		log.info("Transmision Tramite Trafico: inicio--validar620:");
		Map<String, Object> resultadoModelo = null;
		String estadoTramite = tramiteTraficoTransmisionBean != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado() != null
						? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum()
						: EstadoTramiteTrafico.Iniciado.getValorEnum();
		// Si el estado del trámite es uno de los siguientes, se guardará en BD
		// antes de validar, por si ha habido algún cambio...
		if (estadoTramite.equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.No_Tramitable.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Tramitable.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())
				|| estadoTramite.equals(EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum())) {
			resultadoModelo = getModeloTransmision().guardarAltaTramiteTransmision(
					transmisionTramiteTraficoBeanPQConversion.convertirTramiteTransmisionToPQ(
							tramiteTraficoTransmisionBean),
					tramiteTraficoTransmisionBean);
		} else {
			// Si no, se obtiene el detalle...
			resultadoModelo = getModeloTransmision()
					.obtenerDetalle(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
		}
		ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);
		List<String> errores = new ArrayList<>();
		if (!resultBean.getError()) {
			setTramiteTraficoTransmisionBean(
					(TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
			errores = getModeloTransmision().validarCamposXML(tramiteTraficoTransmisionBean);
			for (String error : errores) {
				addActionError(error);
			}
			if (errores.isEmpty()) {
				addActionMessage("Validación correcta");
			}
		} else {
			addActionError("No se ha podido guardar el tramite de transmisión");
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Método para generar el XML del 620 en el action de transmisión antiguo
	 * 
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public String generarXML() throws Throwable {
		log.info("Transmision Tramite Trafico: inicio--generarXML:");
		// Generamos el XML
		List<TramiteTraficoTransmisionBean> tramites = new ArrayList<>();
		tramites.add(tramiteTraficoTransmisionBean);
		String idSession = ServletActionContext.getRequest().getSession().getId();
		Map<String, Object> respuestaGenerarXMLJaxb = null;
		try {
			respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLJaxb(tramites, idSession);
		} catch (AccionException e) {
			log.error("Error al generar XMLJaxb", e);
			addActionError(e.toString());
		}

		if (null == respuestaGenerarXMLJaxb) {
			addActionError("Error al generar el fichero");
		} else {
			if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha
																	// habido
																	// errores,
																	// los
																	// mostramos...
				List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
				for (String error : errores) {
					addActionError(error);
				}
			} else { // Si todo ha ido bien...
				// Lo validamos contra el XSD
				File fichero = new File((String) respuestaGenerarXMLJaxb.get("fichero"));
				String resultadoValidar = validarXML620(fichero);
				if (!XML_VALIDO.equals(resultadoValidar)) {
					addActionError("Ha ocurrido un error al validar el XML generado contra el XSD");
					mantenerAcciones();
					return ALTAS_TRAMITE_TRANSMISION;
				}

				inputStream = new FileInputStream(fichero);
				setFicheroXML620(true);
				addActionMessage("Fichero generado correctamente");
				getSession().put(ConstantesSession.FICHERO620, inputStream);

			}
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Método que comprueba si hay fichero y en el caso de que lo haya, se
	 * exporta
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String obtenerFichero620() throws Throwable {
		fileName = "oegamGata620.xml";
		inputStream = (InputStream) getSession().get(ConstantesSession.FICHERO620);

		if (null == inputStream) {
			addActionError("No se encuentra el fichero");
			mantenerAcciones();
			return ALTAS_TRAMITE_TRANSMISION;
		}

		getSession().remove(ConstantesSession.FICHERO620);
		setFicheroXML620(false);
		return "exportar";
	}

	/**
	 * Método que valida un XML contra el xsd del 620 (620_V1.4.xsd)
	 * 
	 * @param fichero
	 *            xml a validar
	 * @return Devolverá XML_CORRECTO si es válido, en cualquier otro caso
	 *         devolverá el string de la excepción de validación
	 */
	public String validarXML620(File fichero) {
		// Constantes para validacion de Schemas
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
		URL esquema = this.getClass().getResource("/trafico/schemas/620_V1.4.xsd");
		final String MY_SCHEMA = esquema.getFile();
		try {
			// get validation driver:
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA);
			// creamos el schema leyéndolo desde el XSD
			Schema schema = factory.newSchema(new StreamSource(MY_SCHEMA));
			Validator validator = schema.newValidator();
			// Validamos el XML, si no lo valida devolverá el string de la
			// excepción, si lo valida devolverá 'CORRECTO'
			validator.validate(new StreamSource(fichero));
			// Mantis 11823.David Sierra: Controla la excepción
			// org.xml.sax.SAXParseException y muestra un mensaje legible al
			// usuario
			// indicándole el error producido. También se muestra un mensaje más
			// específico en los logs
		} catch (SAXParseException spe) {
			String error = "Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()
					+ ": Error en la generacion del XML 620. " + getModeloTransmision().parseSAXParseException(spe);
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
		return XML_VALIDO;
	}

	@SuppressWarnings("unchecked")
	public String validarTramiteTransmision() throws Throwable {
		log.info("Transmision Tramite Trafico: inicio--validarTramiteTransmision:");
		Map<String, Object> resultadoModelo = getModeloTransmision()
				.guardarAltaTramiteTransmision(transmisionTramiteTraficoBeanPQConversion
						.convertirTramiteTransmisionToPQ(tramiteTraficoTransmisionBean), tramiteTraficoTransmisionBean);
		ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);

		if (!resultBean.getError()) {
			setTramiteTraficoTransmisionBean(
					(TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
			resultBean = getModeloTransmision().validar(tramiteTraficoTransmisionBean);
			if (!resultBean.getError()) {
				// Se han validado los anagramas y el trámite
				addActionMessage("Validación correcta");
			} else {
				for (String mensaje : resultBean.getListaMensajes()) {
					addActionError(mensaje.replace("--", " -"));
				}
			}
			// Generamos XML con firma, y se custodia en servidor de documentos
			if (tramiteTraficoTransmisionBean.getTramiteTraficoBean().getEstado().getValorEnum()
					.equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum())) {
				Map<String, Object> respuestaGenerarXMLJaxb = getModeloTransmision().generarXMLTransmisionNoTelematica(tramiteTraficoTransmisionBean);

				if (respuestaGenerarXMLJaxb.get("errores") != null) { // Si ha
																		// habido
																		// errores,
																		// los
																		// mostramos...
					List<String> errores = (List<String>) respuestaGenerarXMLJaxb.get("errores");
					for (String error : errores) {
						addActionError(error);
					}
				}
			}
		} else {
			addActionError("No se ha podido guardar el tramite de transmisión");
			for (String mensaje : resultBean.getListaMensajes()) {
				addActionError(mensaje);
			}
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	/**
	 * Método que busca un interviniente en bbdd por el DNI y devuelve todos los
	 * datos del interviniente.
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String buscarInterviniente() throws Throwable {
		Persona persona = new Persona(true);

		if // Si el interviniente que hay que buscar es un Adquiriente
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.Adquiriente.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setAdquirienteBean(interviniente);
		} else if // Si el interviniente que hay que buscar es un Representante
					// Adquiriente
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteAdquiriente.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getRepresentanteAdquirienteBean().getPersona().getNif(),
					numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentanteAdquirienteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un Transmitente
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.TransmitenteTrafico.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getTransmitenteBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setTransmitenteBean(interviniente);
		} else if // Si el interviniente que hay que buscar es un Representante
					// Transmitente
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteTransmitente.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getRepresentanteTransmitenteBean().getPersona().getNif(),
					numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentanteTransmitenteBean(interviniente);

		} else if // Si el interviniente que hay que buscar es un
					// Compraventa(Poseedor)
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.Compraventa.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getPoseedorBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setPoseedorBean(interviniente);
		} else if // Si el interviniente que hay que buscar es un Representante
					// Compraventa(Poseedor)
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.RepresentanteCompraventa.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getRepresentantePoseedorBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setRepresentantePoseedorBean(interviniente);
		} else if // Si el interviniente que hay que buscar es un Presentador
		(tipoIntervinienteBuscar != null
				&& tipoIntervinienteBuscar.equals(TipoInterviniente.PresentadorTrafico.getNombreEnum())) {
			String numColegiado = (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
					&& !"".equals(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()))
							? tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado()
							: utilesColegiado.getNumColegiadoSession();
			persona = ModeloPersona.obtenerDetallePersonaCompleto(
					tramiteTraficoTransmisionBean.getPresentadorBean().getPersona().getNif(), numColegiado);
			IntervinienteTrafico interviniente = new IntervinienteTrafico(true);
			interviniente.setPersona(persona);
			interviniente.setIdContrato(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getIdContrato());
			interviniente.setNumColegiado(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumColegiado());
			tramiteTraficoTransmisionBean.setPresentadorBean(interviniente);
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	public String validarYGenerarJustificantePro() throws Throwable {
		try {
			guardarTramite();
			String habilitarJustificantesNuevo = gestorPropiedades.valorPropertie("habilitar.justificante.nuevo");
			if (habilitarJustificantesNuevo != null && "SI".equals(habilitarJustificantesNuevo)) {
				TramiteTrafTranDto tramiteTrafTranDto = servicioTramiteTraficoTransmision.getTramiteTransmision(
						tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), Boolean.TRUE);
				if (tramiteTrafTranDto != null) {
					if (tramiteTrafTranDto.getTipoTransferencia() != null && TipoTransferencia.tipo5.getValorEnum()
							.equals(tramiteTrafTranDto.getTipoTransferencia())) {
						addActionError(
								"Error al generar el Justificante: no se puede emitir un Justificante para una Entraga Compraventa");
					} else {
						ResultBean resultado = servicioTramiteTraficoTransmision.generarJustitificanteProf(
								tramiteTrafTranDto, utilesColegiado.getIdUsuarioSessionBigDecimal(), motivoJustificantes,
								documentoJustificantes, diasValidezJustificantes);
						if (resultado != null) {
							String mensajes = "";
							for (String mensaje : resultado.getListaMensajes()) {
								mensajes = mensajes + mensaje + " ";
							}

							if (!resultado.getError()) {
								addActionMessage(mensajes);
							} else {
								if (resultado.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES) != null) {
									numPeticiones = (Integer) resultado
											.getAttachment(ServicioJustificanteProfesional.NUM_PETICIONES);
									if (numPeticiones == 1 || numPeticiones == 2) {
										tipoTramiteJustificante = TipoTramiteTrafico.Transmision.getValorEnum();
										return ALTA_TRAMITE_TRAFICO_JUSTIFICANTE;
									}
								}
								addActionError("Error al generar el Justificante: " + mensajes);
							}
						}
					}
				} else {
					addActionError("No existen datos del expediente para poder generar el justificante.");
				}
			} else {
				getModeloJustificanteProfesional().validarYGenerarJustificanteTransmisionActual(
						tramiteTraficoTransmisionBean, utilesColegiado.getAlias(),
						utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(),
						ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(),
						utilesColegiado.getNumColegiadoSession(), ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO,
						ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);

				addActionMessage("solicitud de justificante firmada y enviada");
			}
		} catch (ValidacionJustificanteRepetidoExcepcion e) {
			log.info("Traza de prueba cuando un justificante esta repetido. Expediente: "
					+ tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente());
			/*
			 * Si no se permite crear un justificante (ni siquiera forzandolo)
			 * para un adquiriente y matricula que previamente ya se había
			 * generado
			 */
			if (gestorPropiedades.valorPropertie(Constantes.PERMITE_SER_FORZADO).equals("SI")) {
				log.info("En el true de permite ser forzado.");
				servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
				addActionError("Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()
						+ "; " + gestorPropiedades.valorPropertie(Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION));
			} else {
				log.info("En el false de permite ser forzado.");
				addActionError("Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()
						+ "; " + gestorPropiedades.valorPropertie(Constantes.JUSTIFICANTE_REPETIDO_EXCEPTION));
			}
		} catch (ValidacionJustificantePorFechaExcepcion e) {
			servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente(), EstadoJustificante.Iniciado, EstadoJustificante.Pendiente_autorizacion_colegio, utilesColegiado.getIdUsuarioSessionBigDecimal());
			addActionError("Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()
					+ "; Ya se ha emitido un Justificante profesional para la matricula: "
					+ tramiteTraficoTransmisionBean.getTramiteTraficoBean().getVehiculo().getMatricula()
					+ " y distinto adquiriente en menos de 30 días. Si quiere volver a generar un Justificante Profesional póngase en contacto con el Colegio de Gestores Administrativos de Madrid.");
		} catch (OegamExcepcion e) {
			String error = "Tramite " + tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente()
					+ ": Error en la generacion de Justificante. ";
			error += " - " + e.getMensajeError1();
			addActionError(error);
		}
		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
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
			personaDto
					.setFechaNacimiento(utilesFecha.getFechaFracionada(adquiriente.getPersona().getFechaNacimiento()));
			personaDto.setTelefonos(adquiriente.getPersona().getTelefonos());

			intervinienteTraficoDto.setPersona(personaDto);

			if (adquiriente.getPersona().getDireccion() != null) {
				DireccionDto direccionDto = new DireccionDto();
				direccionDto.setIdProvincia(
						adquiriente.getPersona().getDireccion().getMunicipio().getProvincia().getIdProvincia());
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
				if (adquiriente.getPersona().getDireccion().getHm() != null) {
					direccionDto.setHm(new BigDecimal(adquiriente.getPersona().getDireccion().getHm()));
				}
				if (adquiriente.getPersona().getDireccion().getPuntoKilometrico() != null) {
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

	/**
	 * Genera un justificante sin validar datos. Se utilizará cuando se consiga
	 * en doble submit a traves del Confirm, en un futuro.
	 * 
	 * @return
	 * @throws Throwable
	 */

	public String generarJustificantePro() throws Throwable {
		try {
			getModeloJustificanteProfesional().generarJustificanteTransmisionActual(tramiteTraficoTransmisionBean,
					utilesColegiado.getAlias(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(),
					ConstantesTrafico.DIAS_VALIDEZ_POR_DEFECTO, utilesColegiado.getColegioDelContrato(),
					utilesColegiado.getNumColegiadoSession(), ConstantesTrafico.MOTIVO_TRANSMISION_POR_DEFECTO,
					ConstantesTrafico.DOCUMENTOS_POR_DEFECTO, false, false);
			addActionMessage("Solicitud de justificante firmada y enviada");
		} catch (OegamExcepcion e) {
			addActionError(e.getMessage());
		}

		mantenerAcciones();
		return ALTAS_TRAMITE_TRANSMISION;
	}

	private void guardarTramite() throws OegamExcepcion {
		// DRC@15-02-2013 Incidencia Mantis: 2708
		tramiteTraficoTransmisionBean.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

		// Llamo al método estático del modelo para guardar el trámite después
		// de recogerlo del formulario web.
		// GuardarAltaTramiteTransmision tiene el BeanPQ y realiza todos los
		// pasos necesarios.
		Map<String, Object> resultadoModelo = getModeloTransmision()
				.guardarAltaTramiteTransmision(transmisionTramiteTraficoBeanPQConversion
						.convertirTramiteTransmisionToPQ(tramiteTraficoTransmisionBean), tramiteTraficoTransmisionBean);
		ResultBean resultBean = (ResultBean) resultadoModelo.get(ConstantesPQ.RESULTBEAN);

		log.debug("resultado = " + resultBean.getMensaje());

		if (!resultBean.getError()) {
			setTramiteTraficoTransmisionBean(
					(TramiteTraficoTransmisionBean) resultadoModelo.get(ConstantesPQ.BEANPANTALLA));
			addActionMessage("Trámite guardado");
		} else {
			addActionError("No se ha podido guardar el trámite de transmisión");
		}
		for (String mensaje : resultBean.getListaMensajes()) {
			throw new OegamExcepcion(mensaje);
		}
	}

	/**
	 * Método para mantener las acciones de un trámite según se vayan realizando
	 * acciones con el mismo.
	 * 
	 * @throws Throwable
	 */
	public void mantenerAcciones() throws Throwable {
		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		if ((null != tramiteTraficoTransmisionBean.getTramiteTraficoBean())
				&& (null != tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente())) {
			// Incorporamos la obtención de acciones por trámite
			String numExpediente = tramiteTraficoTransmisionBean.getTramiteTraficoBean().getNumExpediente().toString();
			listaAcciones = UtilidadesAccionTrafico.getInstance().generarListaAcciones(numExpediente);
		}
		// Fin Acciones
	}

	// GETTERS & SETTERS

	public TramiteTraficoTransmisionBean getTramiteTraficoTransmisionBean() {
		return tramiteTraficoTransmisionBean;
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

	public Boolean getFicheroXML620() {
		return ficheroXML620;
	}

	public void setFicheroXML620(Boolean ficheroXML620) {
		this.ficheroXML620 = ficheroXML620;
	}

	/**
	 * @return the propTexto
	 */
	public String getPropTexto() {
		return propTexto;
	}

	/**
	 * @param propTexto
	 *            the propTexto to set
	 */
	public void setPropTexto(String propTexto) {
		this.propTexto = propTexto;
	}

	/*
	 * ********************************************************
	 * MODELOS ************************************************
	 * ********************************************************
	 */

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
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

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
	}

	public String getTipoTramiteJustificante() {
		return tipoTramiteJustificante;
	}

	public void setTipoTramiteJustificante(String tipoTramiteJustificante) {
		this.tipoTramiteJustificante = tipoTramiteJustificante;
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
}