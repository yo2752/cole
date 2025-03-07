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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioBuqueRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioClausula;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioComision;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioCuadroAmortizacion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioDatoFirma;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioFinanciador;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioIntervinienteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMotorBuque;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOtroImporte;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPacto;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedad;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioReconocimientoDeuda;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegRbm;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesRegistradores;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ClausulaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ComisionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.CuadroAmortizacionDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.DatoFirmaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MotorBuqueDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OtroImporteDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PactoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ReconocimientoDeudaDto;
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

public class ContratoFinanciacionAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3189047408745221541L;

	private static Logger log = Logger.getLogger(ContratoFinanciacionAction.class);

	private static final String RESULT_ALTA = "alta";
	private static final String RESULTADO_EDIT = "editar";
	private static final String RESULTADO_CONSULTA = "consulta";
	private static final String POP_UP_ANIADIR_FIRMA = "popUpDatosFirmaRegistro";
	private static final String POP_UP_ANIADIR_IMPORTE = "popUpOtrosImportesRegistro";
	private static final String POP_UP_ANIADIR_DEUDA = "popUpReconocimientoDeudaRegistro";
	private static final String POP_UP_ANIADIR_COMISION = "popUpComisionRegistro";
	private static final String POP_UP_ANIADIR_CUADRO = "popUpCuadroAmortizacionRegistro";
	private static final String POP_UP_ANIADIR_REPRESENTANTE_CESIONARIO = "popUpRepresentanteCesionarioRegistro";
	private static final String POP_UP_ANIADIR_REPRESENTANTE_COMPRADOR = "popUpRepresentanteCompradorRegistro";
	private static final String POP_UP_ANIADIR_CLAUSULA = "popUpClausulaRegistro";
	private static final String POP_UP_ANIADIR_REPRESENTANTE_AVALISTA = "popUpRepresentanteAvalistaRegistro";
	private static final String POP_UP_ANIADIR_MOTOR = "popUpMotorBuque";
	private static final String ID_CONTRATO_REGISTRO = "Financiacion";
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";

	private TramiteRegRbmDto tramiteRegRbmDto;
	private String identificador;
	private long idRepresentanteInterviniente;
	private long idInterviniente;
	private long idFinanciadorPadre;
	private String idFinanciador;
	private boolean rmFirmar;

	private String idRegistroFueraSecuencia;

	private String estadoAcusePendiente;

	// Atributos para el Documento
	private InputStream inputStream;
	private String ficheroDescarga;

	private String fileUploadFileName;
	private File fileUpload;
	private String fileUploadContentType;
	private String idFicheroEliminar;
	private String idFicheroDescargar;
	private String nombreDoc;
	private String tipoDoc;

	private String tipoInterviniente;
	private String nifInterviniente;

	@Autowired
	private ServicioTramiteRegRbm servicioTramiteRegRbm;

	@Autowired
	private ServicioDatoFirma servicioDatoFirma;

	@Autowired
	private ServicioOtroImporte servicioOtroImporte;

	@Autowired
	private ServicioReconocimientoDeuda servicioReconocimientoDeuda;

	@Autowired
	private ServicioClausula servicioClausula;

	@Autowired
	private ServicioPacto servicioPacto;

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioIntervinienteRegistro servicioIntervinienteRegistro;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioComision servicioComision;

	@Autowired
	private ServicioCuadroAmortizacion servicioCuadroAmortizacion;

	@Autowired
	private ServicioFinanciador servicioFinanciador;

	@Autowired
	private ServicioPropiedad servicioPropiedad;

	@Autowired
	private ServicioBuqueRegistro servicioBuqueRegistro;

	@Autowired
	private ServicioMotorBuque servicioMotorBuque;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private String tipoContratoRegistro;

	/*
	 * ***************************************************************** Methods *****************************************************************
	 */

	@SkipValidation
	public String alta() {
		log.debug("Start");
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		setTramiteRegRbmDto(new TramiteRegRbmDto());
		getTramiteRegRbmDto().setTipoTramite(TipoTramiteRegistro.MODELO_7.getValorEnum());
		getTramiteRegRbmDto().setTipoOperacion(TipoOperacion.C.getKey());
		getTramiteRegRbmDto().setNumColegiado(utilesColegiado.getNumColegiadoSession());
		getTramiteRegRbmDto().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		getTramiteRegRbmDto().setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		getTramiteRegRbmDto().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegRbmDto().getNumColegiado(), getTramiteRegRbmDto().getIdContrato()));

		log.debug("Return");
		return RESULT_ALTA;
	}

	// Botón guardar del formulario
	public String guardar() {
		ResultRegistro guardar;
		guardar = guardarContrato(true);

		if (!guardar.isError()) {
			addActionMessage("Trámite guardado correctamente");
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
			getTramiteRegRbmDto().setPresentador(servicioPersona.obtenerColegiadoCompleto(getTramiteRegRbmDto().getNumColegiado(), getTramiteRegRbmDto().getIdContrato()));
		}

		return RESULTADO_EDIT;
	}

	// Guarda el contrato con los datos que se hayan tecleado en pantalla
	public ResultRegistro guardarContrato(boolean cambiarEstado) {
		ResultRegistro resultado;
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		log.debug("Start");

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
		resultado = servicioTramiteRegRbm.guardarTramiteRegRbm(getTramiteRegRbmDto());

		if (!resultado.isError()) {
			TramiteRegRbmDto tramiteRegRbm = (TramiteRegRbmDto) resultado.getObj();
			getTramiteRegRbmDto().setIdTramiteRegistro(tramiteRegRbm.getIdTramiteRegistro());
			getTramiteRegRbmDto().getDatosFinanciero().setIdDatosFinancieros(tramiteRegRbm.getDatosFinanciero().getIdDatosFinancieros());
		}
		return resultado;
	}

	// Recupera todos los datos del contrato
	public String recuperarContrato() {
		ResultRegistro resultado = new ResultRegistro();
		setTipoContratoRegistro(ID_CONTRATO_REGISTRO);
		try {
			if (null != getTramiteRegRbmDto() && null != getTramiteRegRbmDto().getIdTramiteRegistro()) {
				resultado = servicioTramiteRegRbm.getTramiteRegRbm(getTramiteRegRbmDto().getIdTramiteRegistro().toString());
			} else {
				resultado = servicioTramiteRegRbm.getTramiteRegRbm(getIdentificador());
			}
		} catch (Exception e) {
			resultado.setMensaje("Error al recuperar el contrato Financiación");
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

	public void recuperarListas() {
		ResultRegistro resultado;

		resultado = servicioTramiteRegRbm.getTramiteRegRbm(getTramiteRegRbmDto().getIdTramiteRegistro().toString());
		TramiteRegRbmDto tramite = (TramiteRegRbmDto) resultado.getObj();
		if (null != tramite) {
			getTramiteRegRbmDto().setCesionarios(tramite.getCesionarios());
			getTramiteRegRbmDto().setCompradores(tramite.getCompradores());
			getTramiteRegRbmDto().setVendedores(tramite.getVendedores());
			getTramiteRegRbmDto().setFinanciadores(tramite.getFinanciadores());

			getTramiteRegRbmDto().setAvalistas(tramite.getAvalistas());

			getTramiteRegRbmDto().setDatoFirmas(tramite.getDatoFirmas());
			getTramiteRegRbmDto().setClausulas(tramite.getClausulas());
			getTramiteRegRbmDto().setFicherosSubidos(tramite.getFicherosSubidos());
			getTramiteRegRbmDto().setPactos(tramite.getPactos());
			getTramiteRegRbmDto().setPropiedades(tramite.getPropiedades());
			getTramiteRegRbmDto().getDatosFinanciero().setComisiones(tramite.getDatosFinanciero().getComisiones());
			getTramiteRegRbmDto().getDatosFinanciero().setCuadrosAmortizacion(tramite.getDatosFinanciero().getCuadrosAmortizacion());
			getTramiteRegRbmDto().getDatosFinanciero().setOtroImportes(tramite.getDatosFinanciero().getOtroImportes());
			getTramiteRegRbmDto().getDatosFinanciero().setReconocimientoDeudas(tramite.getDatosFinanciero().getReconocimientoDeudas());
		}
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

				addActionMessage("Trámite validado correctamente");
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
		log.info("Financiacion construirDpr.");

		ResultRegistro resultRegistro;

		if (!UtilesRegistradores.permitidoEnvio(getTramiteRegRbmDto().getEstado())) {
			addActionError("El estado actual del trámite no permite el envío del mismo.");
			recuperarContrato();
			return RESULTADO_EDIT;
		}

		resultRegistro = servicioTramiteRegRbm.construirDpr(getTramiteRegRbmDto().getIdTramiteRegistro().toString(), utilesColegiado.getAlias());

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

	public String construirFirmarAcuse() {
		log.info("Financiacion firmar acuse.");

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
			addActionError("El estado actual: " + EstadoTramiteRegistro.convertirTexto(tramiteRegRbmDto.getEstado().toString()) + " no permite la subsanación del trámite de id: " + tramiteRegRbmDto
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
			addActionMessage("Subsanado correctamente. El trámite subsanado tiene el número de expediente: " + tramiteRegRbmDto.getIdTramiteRegistro());
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Se ha subsanado el tramite con id: " + idTramiteRegistro + " .Id del subsanado: " + tramiteRegRbmDto.getIdTramiteRegistro());
		} else {
			if (null != getTramiteRegRbmDto().getIdTramiteRegistro())
				recuperarListas();
			addActionError("Error subsanando el trámite: " + idTramiteRegistro);
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
			addActionError("El estado actual: " + EstadoTramiteRegistro.convertirTexto(tramiteRegRbmDto.getEstado().toString()) + " no permite el duplicado del trámite de id: " + tramiteRegRbmDto
					.getIdTramiteRegistro());
			return RESULTADO_EDIT;
		}

		BigDecimal idTramiteRegistro = tramiteRegRbmDto.getIdTramiteRegistro();

		servicioTramiteRegistro.inicializarParametros(tramiteRegRbmDto);

		resultRegistro = guardarContrato(true);

		if (resultRegistro != null && !resultRegistro.isError()) {
			addActionMessage("Duplicado correctamente. El trámite duplicado tiene el número de expediente: " + tramiteRegRbmDto.getIdTramiteRegistro());
			log.info(Claves.CLAVE_LOG_REGISTRADORES_COMUN_INFO + " Se ha duplicado el tramite con id: " + idTramiteRegistro + " .Id del duplicado: " + tramiteRegRbmDto.getIdTramiteRegistro());
		} else {
			if (null != getTramiteRegRbmDto().getIdTramiteRegistro())
				recuperarListas();
			addActionError("Error duplicando el trámite: " + idTramiteRegistro);
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

	// -------------------------------Bloque Datos Firma -------------------------------------------------------------------------------------
	public String aniadirDatosFirma() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioDatoFirma.guardarOActualizarDatosFirma(getTramiteRegRbmDto().getDatoFirmaDto(), getTramiteRegRbmDto().getIdTramiteRegistro(), getTramiteRegRbmDto().getTipoTramite());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar el Dato de la Firma");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String borrarDatosFirma() {

		ResultRegistro resultado = new ResultRegistro();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			guardarContrato(true);

			try {
				resultado = servicioDatoFirma.borrarDatosFirma(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al borrar el Dato Firma");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}

		}
		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String cargarPopUpDatosFirmaRegistro() {
		ResultRegistro resultado = new ResultRegistro();

		getTramiteRegRbmDto().setDatoFirmaDto(null);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioDatoFirma.getDatoFirma(getIdentificador());
				getTramiteRegRbmDto().setDatoFirmaDto((DatoFirmaDto) resultado.getObj());

				if (null != getTramiteRegRbmDto().getDatoFirmaDto().getFecFirma()) {
					getTramiteRegRbmDto().getDatoFirmaDto().setFecFirmaDatFirma(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getDatoFirmaDto().getFecFirma()));
				}

				if (StringUtils.isNotBlank(tramiteRegRbmDto.getDatoFirmaDto().getLugar())) {
					tramiteRegRbmDto.getDatoFirmaDto().setDireccion(servicioDatoFirma.convertirLugarFirma(tramiteRegRbmDto.getDatoFirmaDto().getLugar()));
				}

			} catch (Exception e) {
				resultado.setMensaje("Error al obtener el Dato de la Firma");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}
		}

		return POP_UP_ANIADIR_FIRMA;
	}

	// ---------------------------------------------Fin Bloque Datos Firma -------------------------------------------------------------------------------------

	// -------------------------------Bloque Comisión -------------------------------------------------------------------------------------
	public String aniadirComision() {

		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioComision.guardarOActualizarComision(getTramiteRegRbmDto().getComisionDto(), getTramiteRegRbmDto().getDatosFinanciero().getIdDatosFinancieros());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar la comisión");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String borrarComision() {

		ResultRegistro resultado = new ResultRegistro();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			guardarContrato(true);

			try {
				resultado = servicioComision.borrarComision(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al borrar la comisión");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}

		}
		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String cargarPopUpComisionRegistro() {
		ResultRegistro resultado = new ResultRegistro();

		getTramiteRegRbmDto().setComisionDto(null);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioComision.getComision(getIdentificador());
				getTramiteRegRbmDto().setComisionDto((ComisionDto) resultado.getObj());
			} catch (Exception e) {
				resultado.setMensaje("Error al obtener la comisión");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}
		}

		return POP_UP_ANIADIR_COMISION;
	}

	// ---------------------------------------------Fin Bloque Comisión -------------------------------------------------------------------------------------

	// ----------------------------------------------Bloque Otros Importes -------------------------------------------------------------------------------------
	public String aniadirOtrosImportes() {

		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioOtroImporte.guardarOActualizarOtroImporte(getTramiteRegRbmDto().getOtroImporteDto(), getTramiteRegRbmDto().getDatosFinanciero().getIdDatosFinancieros());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar el Importe");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;

	}

	public String borrarOtrosImportes() {

		ResultRegistro resultado = new ResultRegistro();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			guardarContrato(true);

			try {
				resultado = servicioOtroImporte.borrarOtroImporte(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al borrar el Importe");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}

		}
		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String cargarPopUpOtrosImportesRegistro() {
		ResultRegistro resultado = new ResultRegistro();

		getTramiteRegRbmDto().setOtroImporteDto(null);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioOtroImporte.getOtroImporte(getIdentificador());
				getTramiteRegRbmDto().setOtroImporteDto((OtroImporteDto) resultado.getObj());

			} catch (Exception e) {
				resultado.setMensaje("Error al obtener el Importe");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return POP_UP_ANIADIR_IMPORTE;
	}

	// ---------------------------------------------Fin Bloque Otros Importes ------------------------------------------------------------------------------

	// ----------------------------------------------Bloque Reconocimiento de deuda ------------------------------------------------------------------------

	public String aniadirReconocimientoDeuda() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioReconocimientoDeuda.guardarOActualizarReconocimientoDeuda(getTramiteRegRbmDto().getReconocimientoDeudaDto(), getTramiteRegRbmDto().getDatosFinanciero()
					.getIdDatosFinancieros());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar el Reconocimiento de deuda");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String borrarReconocimientoDeuda() {

		ResultRegistro resultado = new ResultRegistro();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			guardarContrato(true);

			try {
				resultado = servicioReconocimientoDeuda.borrarReconocimientoDeuda(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al borrar el Reconocimiento de Deuda");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}

		}

		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String cargarPopUpReconocimientoDeudaRegistro() {
		ResultRegistro resultado = new ResultRegistro();

		getTramiteRegRbmDto().setReconocimientoDeudaDto(null);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioReconocimientoDeuda.getReconocimientoDeuda(getIdentificador());
				getTramiteRegRbmDto().setReconocimientoDeudaDto((ReconocimientoDeudaDto) resultado.getObj());

				if (null != getTramiteRegRbmDto().getReconocimientoDeudaDto().getFecPrimerVencimiento()) {
					getTramiteRegRbmDto().getReconocimientoDeudaDto().setFecPrimerVencimientoReconDeuda(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getReconocimientoDeudaDto()
							.getFecPrimerVencimiento()));
				}
			} catch (Exception e) {
				resultado.setMensaje("Error al obtener el Reconocimiento de deuda");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return POP_UP_ANIADIR_DEUDA;
	}

	// ---------------------------------------------Fin Bloque Reconocimiento de deuda --------------------------------------------------------------

	// ----------------------------------------------Bloque Cuadro Amortizacion -------------------------------------------------------------------------------------
	public String aniadirCuadroAmortizacion() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioCuadroAmortizacion.guardarOActualizarCuadroAmortizacion(getTramiteRegRbmDto().getCuadroAmortizacionDto(), getTramiteRegRbmDto().getDatosFinanciero()
					.getIdDatosFinancieros());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar el cuadro de amortización");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String borrarCuadroAmortizacion() {
		ResultRegistro resultado = new ResultRegistro();
		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			guardarContrato(true);

			try {
				resultado = servicioCuadroAmortizacion.borrarCuadroAmortizacion(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al borrar el cuadro de amortización");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}
			recuperarContrato();
		}
		return RESULTADO_EDIT;
	}

	public String cargarPopUpCuadroAmortizacionRegistro() {
		ResultRegistro resultado = new ResultRegistro();
		getTramiteRegRbmDto().setCuadroAmortizacionDto(null);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioCuadroAmortizacion.getCuadroAmortizacion(getIdentificador());
				getTramiteRegRbmDto().setCuadroAmortizacionDto((CuadroAmortizacionDto) resultado.getObj());
				if (null != getTramiteRegRbmDto().getCuadroAmortizacionDto().getFechaVencimiento()) {
					getTramiteRegRbmDto().getCuadroAmortizacionDto().setFechaVencimientoCuadroAmort(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getCuadroAmortizacionDto()
							.getFechaVencimiento()));
				}
			} catch (Exception e) {
				resultado.setMensaje("Error al obtener el el cuadro de amortización");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return POP_UP_ANIADIR_CUADRO;
	}

	// ---------------------------------------------Fin Bloque cuadro Amortizacion ------------------------------------------------------------------------------

	// ----------------------------------------------Bloque Cesionarios ------------------------------------------------------------------------
	public String aniadirCesionario() {

		ResultRegistro resultObjectCesionario;

		guardarContrato(true);
		getTramiteRegRbmDto().getCesionario().setTipoInterviniente(TipoInterviniente.Cesionario.getValorEnum());
		resultObjectCesionario = servicioIntervinienteRegistro.guardarInterviniente(getTramiteRegRbmDto().getCesionario(), getTramiteRegRbmDto());

		if (resultObjectCesionario.isError()) {
			if (resultObjectCesionario.getValidaciones() != null && !resultObjectCesionario.getValidaciones().isEmpty()) {
				for (String validacion : resultObjectCesionario.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultObjectCesionario.getMensaje());
			}
			IntervinienteRegistroDto cesionarioAux = getTramiteRegRbmDto().getCesionario();
			recuperarContrato();
			getTramiteRegRbmDto().setCesionario(cesionarioAux);
			if (null != getTramiteRegRbmDto().getCesionario() && 0 != getTramiteRegRbmDto().getCesionario().getIdInterviniente())
				getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));
			return RESULTADO_EDIT;
		} else {
			addActionMessage("Cesionario añadido correctamente");
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String modificarCesionario() {

		guardarContrato(true);

		recuperarContrato();

		if (getIdInterviniente() != 0) {
			// Actualización
			getTramiteRegRbmDto().setCesionario(servicioIntervinienteRegistro.getInterviniente(String.valueOf(getIdInterviniente())));
			getTramiteRegRbmDto().getCesionario().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getCesionario().getIdDireccion()));
			getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));
		}

		return RESULTADO_EDIT;
	}

	public String borrarCesionario() {
		ResultRegistro resultObjectCesionario;

		guardarContrato(true);

		if (0 != idInterviniente) {

			// Borramos el cesionario
			resultObjectCesionario = servicioIntervinienteRegistro.borrarInterviniente(String.valueOf(idInterviniente));
			if (resultObjectCesionario.isError()) {
				addActionError(resultObjectCesionario.getMensaje());
			} else {
				addActionMessage("Cesionario borrado correctamente");
			}

		}
		recuperarContrato();

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin bloque Cesionario ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Representantes Cesionario ------------------------------------------------------------------------

	public String cargarPopUpRepresentanteCesionarioRegistro() {
		ResultRegistro resultRegistro;

		if (0 != getTramiteRegRbmDto().getCesionario().getIdInterviniente() && 0 == getIdRepresentanteInterviniente())
			getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));

		if (null == getTramiteRegRbmDto().getRepresentantesCesionario() || getTramiteRegRbmDto().getRepresentantesCesionario().isEmpty() || 0 != getIdRepresentanteInterviniente()) {

			getTramiteRegRbmDto().setRepresentanteCesionario(null);

			// El cesionario no esta guardado, validamos
			resultRegistro = servicioIntervinienteRegistro.validarInterviniente(getTramiteRegRbmDto().getCesionario(), getTramiteRegRbmDto().getTipoTramite());
			if (resultRegistro.isError()) {
				if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
					for (String validacion : resultRegistro.getValidaciones()) {
						addActionError(validacion);
					}
				}
				IntervinienteRegistroDto cesionarioAux = getTramiteRegRbmDto().getCesionario();
				recuperarContrato();
				getTramiteRegRbmDto().setCesionario(cesionarioAux);
				if (null != getTramiteRegRbmDto().getCesionario() && 0 != getTramiteRegRbmDto().getCesionario().getIdInterviniente())
					getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));
				return RESULTADO_EDIT;
			}
			if (0 != getIdRepresentanteInterviniente()) {
				// Actualización
				getTramiteRegRbmDto().setRepresentanteCesionario(servicioIntervinienteRegistro.getRepresentante(String.valueOf(getIdRepresentanteInterviniente())));
				getTramiteRegRbmDto().getRepresentanteCesionario().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getRepresentanteCesionario().getIdDireccion()));
				if (null != getTramiteRegRbmDto().getRepresentanteCesionario().getNotario() && null != getTramiteRegRbmDto().getRepresentanteCesionario().getNotario().getFecOtorgamiento()) {
					getTramiteRegRbmDto().getRepresentanteCesionario().getNotario().setFecOtorgamientoNotario(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getRepresentanteCesionario()
							.getNotario().getFecOtorgamiento()));
				}
			}

			return POP_UP_ANIADIR_REPRESENTANTE_CESIONARIO;

		} else {
			addActionError("Solo puede añadir un representante del cesionario");
			IntervinienteRegistroDto cesionarioAux = getTramiteRegRbmDto().getCesionario();
			recuperarContrato();
			getTramiteRegRbmDto().setCesionario(cesionarioAux);
			getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));
		}

		return RESULTADO_EDIT;
	}

	public String aniadirRepresentanteCesionario() throws ParseException {
		ResultRegistro resultRegistroRepresentanteInterviniente;

		// Guardo contrato para generar el id, con ese id guardo en la tabla intermedia con el cesionario para poder recuperar
		guardarContrato(true);

		if (getIdRepresentanteInterviniente() == 0) {

			getTramiteRegRbmDto().getRepresentanteCesionario().setTipoInterviniente(TipoInterviniente.RepresentanteCesionario.getValorEnum());
			getTramiteRegRbmDto().getRepresentanteCesionario().setIdRepresentado(getTramiteRegRbmDto().getCesionario().getIdInterviniente());
			if (null != getTramiteRegRbmDto().getRepresentanteCesionario().getNotario() && null != getTramiteRegRbmDto().getRepresentanteCesionario().getNotario().getFecOtorgamientoNotario()) {
				getTramiteRegRbmDto().getRepresentanteCesionario().getNotario().setFecOtorgamiento(getTramiteRegRbmDto().getRepresentanteCesionario().getNotario().getFecOtorgamientoNotario()
						.getTimestamp());
			}

		}
		resultRegistroRepresentanteInterviniente = servicioIntervinienteRegistro.guardarInterviniente(getTramiteRegRbmDto().getRepresentanteCesionario(), getTramiteRegRbmDto());
		if (resultRegistroRepresentanteInterviniente.isError()) {
			if (resultRegistroRepresentanteInterviniente.getValidaciones() != null && !resultRegistroRepresentanteInterviniente.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistroRepresentanteInterviniente.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistroRepresentanteInterviniente.getMensaje());
			}
		} else {
			addActionMessage("Representante añadido correctamente");
		}

		IntervinienteRegistroDto cesionarioAux = getTramiteRegRbmDto().getCesionario();
		recuperarContrato();
		getTramiteRegRbmDto().setCesionario(cesionarioAux);
		getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));

		return RESULTADO_EDIT;
	}

	public String borrarRepresentanteCesionario() {
		ResultRegistro resultObjectCesionario;

		guardarContrato(true);

		if (0 != idRepresentanteInterviniente) {

			getTramiteRegRbmDto().setRepresentanteCesionario(servicioIntervinienteRegistro.getInterviniente(String.valueOf(idRepresentanteInterviniente)));
			resultObjectCesionario = servicioIntervinienteRegistro.borrarRepresentante(String.valueOf(idRepresentanteInterviniente));
			if (resultObjectCesionario.isError()) {
				addActionError(resultObjectCesionario.getMensaje());
			} else {
				addActionMessage("Representante borrado correctamente");
			}

			IntervinienteRegistroDto cesionarioAux = getTramiteRegRbmDto().getCesionario();
			recuperarContrato();
			getTramiteRegRbmDto().setCesionario(cesionarioAux);
			getTramiteRegRbmDto().setRepresentantesCesionario(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getCesionario().getIdInterviniente()));
		} else {
			recuperarContrato();
		}

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin Representantes Cesionario ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Comprador ------------------------------------------------------------------------

	public String modificarComprador() {

		guardarContrato(true);

		recuperarContrato();

		if (getIdInterviniente() != 0) {
			// Actualización
			getTramiteRegRbmDto().setComprador(servicioIntervinienteRegistro.getInterviniente(String.valueOf(getIdInterviniente())));
			getTramiteRegRbmDto().getComprador().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getComprador().getIdDireccion()));
			getTramiteRegRbmDto().setRepresentantesComprador(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getComprador().getIdInterviniente()));
		}

		return RESULTADO_EDIT;
	}

	public String borrarComprador() {
		ResultRegistro resultObjectComprador;

		guardarContrato(true);

		if (0 != idInterviniente) {

			// Borramos el comprador
			resultObjectComprador = servicioIntervinienteRegistro.borrarInterviniente(String.valueOf(idInterviniente));
			if (resultObjectComprador.isError()) {
				addActionError(resultObjectComprador.getMensaje());
			} else {
				addActionMessage("Comprador borrado correctamente");
			}

		}
		recuperarContrato();

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin bloque Comprador ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Representantes Comprador ------------------------------------------------------------------------

	public String cargarPopUpRepresentanteCompradorRegistro() {
		ResultRegistro resultRegistro;

		getTramiteRegRbmDto().setRepresentantesComprador(null);

		// El comprador no esta guardado, validamos
		resultRegistro = servicioIntervinienteRegistro.validarInterviniente(getTramiteRegRbmDto().getComprador(), getTramiteRegRbmDto().getTipoTramite());
		if (resultRegistro.isError()) {
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			}
			IntervinienteRegistroDto compradorAux = getTramiteRegRbmDto().getComprador();
			recuperarContrato();
			getTramiteRegRbmDto().setComprador(compradorAux);
			if (null != getTramiteRegRbmDto().getComprador() && 0 != getTramiteRegRbmDto().getComprador().getIdInterviniente())
				getTramiteRegRbmDto().setRepresentantesComprador(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getComprador().getIdInterviniente()));
			return RESULTADO_EDIT;
		}
		if (0 != getIdRepresentanteInterviniente()) {
			// Actualización
			getTramiteRegRbmDto().setRepresentanteComprador(servicioIntervinienteRegistro.getRepresentante(String.valueOf(getIdRepresentanteInterviniente())));
			getTramiteRegRbmDto().getRepresentanteComprador().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getRepresentanteComprador().getIdDireccion()));
			if (null != getTramiteRegRbmDto().getRepresentanteComprador().getNotario() && null != getTramiteRegRbmDto().getRepresentanteComprador().getNotario().getFecOtorgamiento()) {
				getTramiteRegRbmDto().getRepresentanteComprador().getNotario().setFecOtorgamientoNotario(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getRepresentanteComprador().getNotario()
						.getFecOtorgamiento()));
			}
		}

		return POP_UP_ANIADIR_REPRESENTANTE_COMPRADOR;
	}

	public String aniadirRepresentanteComprador() throws ParseException {
		ResultRegistro resultRegistroRepresentanteInterviniente;

		// Guardo contrato para generar el id, con ese id guardo en la tabla intermedia con el comprador para poder recuperar
		guardarContrato(true);

		if (getIdRepresentanteInterviniente() == 0) {

			getTramiteRegRbmDto().getRepresentanteComprador().setTipoInterviniente(TipoInterviniente.RepresentanteComprador.getValorEnum());
			getTramiteRegRbmDto().getRepresentanteComprador().setIdRepresentado(getTramiteRegRbmDto().getComprador().getIdInterviniente());
			if (null != getTramiteRegRbmDto().getRepresentanteComprador().getNotario() && null != getTramiteRegRbmDto().getRepresentanteComprador().getNotario().getFecOtorgamientoNotario()) {
				getTramiteRegRbmDto().getRepresentanteComprador().getNotario().setFecOtorgamiento(getTramiteRegRbmDto().getRepresentanteComprador().getNotario().getFecOtorgamientoNotario()
						.getTimestamp());
			}
		}
		resultRegistroRepresentanteInterviniente = servicioIntervinienteRegistro.guardarInterviniente(getTramiteRegRbmDto().getRepresentanteComprador(), getTramiteRegRbmDto());
		if (resultRegistroRepresentanteInterviniente.isError()) {
			if (resultRegistroRepresentanteInterviniente.getValidaciones() != null && !resultRegistroRepresentanteInterviniente.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistroRepresentanteInterviniente.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistroRepresentanteInterviniente.getMensaje());
			}
		} else {
			addActionMessage("Representante añadido correctamente");
		}

		IntervinienteRegistroDto compradorAux = getTramiteRegRbmDto().getComprador();
		recuperarContrato();
		getTramiteRegRbmDto().setComprador(compradorAux);
		getTramiteRegRbmDto().setRepresentantesComprador(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getComprador().getIdInterviniente()));

		return RESULTADO_EDIT;
	}

	public String borrarRepresentanteComprador() {
		ResultRegistro resultObjectComprador;

		guardarContrato(true);

		if (0 != idRepresentanteInterviniente) {

			getTramiteRegRbmDto().setRepresentanteComprador(servicioIntervinienteRegistro.getInterviniente(String.valueOf(idRepresentanteInterviniente)));
			resultObjectComprador = servicioIntervinienteRegistro.borrarRepresentante(String.valueOf(idRepresentanteInterviniente));
			if (resultObjectComprador.isError()) {
				addActionError(resultObjectComprador.getMensaje());
			} else {
				addActionMessage("Representante borrado correctamente");
			}

			IntervinienteRegistroDto compradorAux = getTramiteRegRbmDto().getComprador();
			recuperarContrato();
			getTramiteRegRbmDto().setComprador(compradorAux);
			getTramiteRegRbmDto().setRepresentantesComprador(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getComprador().getIdInterviniente()));
		} else {
			recuperarContrato();
		}

		return RESULTADO_EDIT;
	}
	// ----------------------------------------------Fin Representantes Comprador ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Vendedores ------------------------------------------------------------------------

	public String modificarVendedor() {

		guardarContrato(true);

		recuperarContrato();

		if (getIdInterviniente() != 0) {
			// Actualización
			getTramiteRegRbmDto().setVendedor(servicioIntervinienteRegistro.getInterviniente(String.valueOf(getIdInterviniente())));
			getTramiteRegRbmDto().getVendedor().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getVendedor().getIdDireccion()));
		}

		return RESULTADO_EDIT;
	}

	public String borrarVendedor() {
		ResultRegistro resultObjectVendedor;

		guardarContrato(true);

		if (0 != idInterviniente) {

			// Borramos el vendedor
			resultObjectVendedor = servicioIntervinienteRegistro.borrarInterviniente(String.valueOf(idInterviniente));
			if (resultObjectVendedor.isError()) {
				addActionError(resultObjectVendedor.getMensaje());
			} else {
				addActionMessage("Vendedor borrado correctamente");
			}

		}
		recuperarContrato();

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin bloque Vendedor ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Financiador -----------------------------------------------------------------------------------

	public String aniadirFinanciador() {
		ResultRegistro resultObjectFinanciador;

		if (StringUtils.isNotBlank(getIdFinanciador())) {
			guardarContrato(true);

			IntervinienteRegistroDto financiador = servicioFinanciador.getFinanciador(getIdFinanciador());
			financiador.setDireccion(servicioDireccion.getDireccionDto(financiador.getIdDireccion()));

			resultObjectFinanciador = servicioIntervinienteRegistro.guardarInterviniente(financiador, getTramiteRegRbmDto());
			if (resultObjectFinanciador.isError()) {
				if (resultObjectFinanciador.getValidaciones() != null && !resultObjectFinanciador.getValidaciones().isEmpty()) {
					for (String validacion : resultObjectFinanciador.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultObjectFinanciador.getMensaje());
				}
				recuperarContrato();
				return RESULTADO_EDIT;
			} else {
				addActionMessage("Financiador añadido correctamente");
			}
		} else {
			addActionError("Debe seleccionar un financiador");
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String borrarFinanciador() {
		ResultRegistro resultObjectFinanciador;

		// No se borra, se desasigna del Trámite
		if (0 != getIdFinanciadorPadre()) {
			IntervinienteRegistroDto financiador = servicioFinanciador.getFinanciador(String.valueOf(idFinanciadorPadre));
			financiador.setDireccion(servicioDireccion.getDireccionDto(financiador.getIdDireccion()));
			financiador.setTramites(servicioFinanciador.getTramites(financiador.getIdInterviniente()));
			List<TramiteRegRbmDto> listTramites;
			if (financiador.getTramites() == null)
				listTramites = new ArrayList<>();
			else
				listTramites = financiador.getTramites();
			listTramites = borrarTramiteLista(listTramites);
			financiador.setTramites(listTramites);
			resultObjectFinanciador = servicioIntervinienteRegistro.guardarInterviniente(financiador, null);
			if (resultObjectFinanciador.isError()) {

				if (resultObjectFinanciador.getValidaciones() != null && !resultObjectFinanciador.getValidaciones().isEmpty()) {
					for (String validacion : resultObjectFinanciador.getValidaciones()) {
						addActionError(validacion);
					}
				} else {
					addActionError(resultObjectFinanciador.getMensaje());
				}

			} else {
				addActionMessage("Financiador borrado correctamente");
			}
		}

		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public List<TramiteRegRbmDto> borrarTramiteLista(List<TramiteRegRbmDto> listaTramites) {
		for (Iterator<TramiteRegRbmDto> iterator = listaTramites.iterator(); iterator.hasNext();) {
			if (0 == iterator.next().getIdTramiteRegistro().compareTo(getTramiteRegRbmDto().getIdTramiteRegistro()))
				iterator.remove();
		}
		return listaTramites;
	}

	// ----------------------------------------------Fin Financiadores ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Cláusulas -------------------------------------------------------------------------------------
	public String aniadirClausula() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioClausula.guardarOActualizarClausula(getTramiteRegRbmDto().getClausulaDto(), getTramiteRegRbmDto().getIdTramiteRegistro());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar cláusula");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;

	}

	public String borrarClausula() {

		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			try {
				resultado = servicioClausula.borrarClausula(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al eliminar cláusula");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}

		}
		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String cargarPopUpClausulaRegistro() {

		getTramiteRegRbmDto().setClausulaDto(null);

		ResultRegistro resultado = new ResultRegistro();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioClausula.getClausula(getIdentificador());
				getTramiteRegRbmDto().setClausulaDto((ClausulaDto) resultado.getObj());
			} catch (Exception e) {
				resultado.setMensaje("Error al obtener cláusula");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return POP_UP_ANIADIR_CLAUSULA;
	}

	// ---------------------------------------------Fin Bloque Cláusulas ------------------------------------------------------------------------------

	// ----------------------------------------------Bloque Avalistas ------------------------------------------------------------------------

	public String modificarAvalista() {

		guardarContrato(true);

		recuperarContrato();
		if (getIdInterviniente() != 0) {
			// Actualización
			getTramiteRegRbmDto().setAvalista(servicioIntervinienteRegistro.getInterviniente(String.valueOf(getIdInterviniente())));
			getTramiteRegRbmDto().getAvalista().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getAvalista().getIdDireccion()));
			getTramiteRegRbmDto().setRepresentantesAvalista(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getAvalista().getIdInterviniente()));
		}

		return RESULTADO_EDIT;
	}

	public String borrarAvalista() {
		ResultRegistro resultObjectAvalista;
		guardarContrato(true);
		if (0 != idInterviniente) {
			// Borramos el avalista
			resultObjectAvalista = servicioIntervinienteRegistro.borrarInterviniente(String.valueOf(idInterviniente));
			if (resultObjectAvalista.isError()) {
				addActionError(resultObjectAvalista.getMensaje());
			} else {
				addActionMessage("Avalista borrado correctamente");
			}
		}
		recuperarContrato();

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin bloque Avalista ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Representantes Avalista ------------------------------------------------------------------------

	public String cargarPopUpRepresentanteAvalistaRegistro() {
		ResultRegistro resultRegistro;

		getTramiteRegRbmDto().setRepresentanteAvalista(null);

		// El avalista no esta guardado, validamos
		resultRegistro = servicioIntervinienteRegistro.validarInterviniente(getTramiteRegRbmDto().getAvalista(), getTramiteRegRbmDto().getTipoTramite());
		if (resultRegistro.isError()) {
			if (resultRegistro.getValidaciones() != null && !resultRegistro.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistro.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistro.getMensaje());
			}
			IntervinienteRegistroDto avalistaAux = getTramiteRegRbmDto().getAvalista();
			recuperarContrato();
			getTramiteRegRbmDto().setAvalista(avalistaAux);
			if (null != getTramiteRegRbmDto().getAvalista() && 0 != getTramiteRegRbmDto().getAvalista().getIdInterviniente())
				getTramiteRegRbmDto().setRepresentantesAvalista(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getAvalista().getIdInterviniente()));
			return RESULTADO_EDIT;
		}
		if (0 != getIdRepresentanteInterviniente()) {
			// Actualización
			getTramiteRegRbmDto().setRepresentanteAvalista(servicioIntervinienteRegistro.getRepresentante(String.valueOf(getIdRepresentanteInterviniente())));
			getTramiteRegRbmDto().getRepresentanteAvalista().setDireccion(servicioDireccion.getDireccionDto(getTramiteRegRbmDto().getRepresentanteAvalista().getIdDireccion()));
			if (null != getTramiteRegRbmDto().getRepresentanteAvalista().getNotario() && null != getTramiteRegRbmDto().getRepresentanteAvalista().getNotario().getFecOtorgamiento()) {
				getTramiteRegRbmDto().getRepresentanteAvalista().getNotario().setFecOtorgamientoNotario(utilesFecha.getFechaConDate(getTramiteRegRbmDto().getRepresentanteAvalista().getNotario()
						.getFecOtorgamiento()));
			}
		}

		return POP_UP_ANIADIR_REPRESENTANTE_AVALISTA;
	}

	public String aniadirRepresentanteAvalista() throws ParseException {
		ResultRegistro resultRegistroRepresentanteInterviniente;

		// Guardo contrato para generar el id, con ese id guardo en la tabla intermedia con el avalista para poder recuperar
		guardarContrato(true);

		if (getIdRepresentanteInterviniente() == 0) {

			getTramiteRegRbmDto().getRepresentanteAvalista().setTipoInterviniente(TipoInterviniente.RepresentanteAvalista.getValorEnum());
			getTramiteRegRbmDto().getRepresentanteAvalista().setIdRepresentado(getTramiteRegRbmDto().getAvalista().getIdInterviniente());
			if (null != getTramiteRegRbmDto().getRepresentanteAvalista().getNotario() && null != getTramiteRegRbmDto().getRepresentanteAvalista().getNotario().getFecOtorgamientoNotario()) {
				getTramiteRegRbmDto().getRepresentanteAvalista().getNotario().setFecOtorgamiento(getTramiteRegRbmDto().getRepresentanteAvalista().getNotario().getFecOtorgamientoNotario()
						.getTimestamp());
			}

		}
		resultRegistroRepresentanteInterviniente = servicioIntervinienteRegistro.guardarInterviniente(getTramiteRegRbmDto().getRepresentanteAvalista(), getTramiteRegRbmDto());
		if (resultRegistroRepresentanteInterviniente.isError()) {
			if (resultRegistroRepresentanteInterviniente.getValidaciones() != null && !resultRegistroRepresentanteInterviniente.getValidaciones().isEmpty()) {
				for (String validacion : resultRegistroRepresentanteInterviniente.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultRegistroRepresentanteInterviniente.getMensaje());
			}
		} else {
			addActionMessage("Representante añadido correctamente");
		}

		IntervinienteRegistroDto avalistaAux = getTramiteRegRbmDto().getAvalista();
		recuperarContrato();
		getTramiteRegRbmDto().setAvalista(avalistaAux);
		getTramiteRegRbmDto().setRepresentantesAvalista(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getAvalista().getIdInterviniente()));

		return RESULTADO_EDIT;
	}

	public String borrarRepresentanteAvalista() {
		ResultRegistro resultObjectAvalista;

		guardarContrato(true);

		if (0 != idRepresentanteInterviniente) {

			getTramiteRegRbmDto().setRepresentanteAvalista(servicioIntervinienteRegistro.getInterviniente(String.valueOf(idRepresentanteInterviniente)));
			resultObjectAvalista = servicioIntervinienteRegistro.borrarRepresentante(String.valueOf(idRepresentanteInterviniente));
			if (resultObjectAvalista.isError()) {
				addActionError(resultObjectAvalista.getMensaje());
			} else {
				addActionMessage("Representante borrado correctamente");
			}

			IntervinienteRegistroDto avalistaAux = getTramiteRegRbmDto().getAvalista();
			recuperarContrato();
			getTramiteRegRbmDto().setAvalista(avalistaAux);
			getTramiteRegRbmDto().setRepresentantesAvalista(servicioIntervinienteRegistro.getRepresentantes(getTramiteRegRbmDto().getAvalista().getIdInterviniente()));
		} else {
			recuperarContrato();
		}

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin Representantes Avalista ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Bienes ------------------------------------------------------------------------

	public String modificarBien() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		recuperarContrato();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioPropiedad.getPropiedad(getIdentificador());
				getTramiteRegRbmDto().setPropiedadDto((PropiedadDto) resultado.getObj());

			} catch (Exception e) {
				resultado.setMensaje("Error al obtener la propiedad");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}
		}
		return RESULTADO_EDIT;
	}

	public String borrarBien() {
		ResultRegistro resultado;

		guardarContrato(true);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			resultado = servicioPropiedad.borrarPropiedad(getIdentificador());
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Bien borrado correctamente");
			}

		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin bloque Bienes ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Motores Buques ------------------------------------------------------------------------

	public String cargarPopUpMotorRegistro() {

		ResultRegistro resultadoPropiedad;
		ResultRegistro resultadoBuque;
		getTramiteRegRbmDto().getPropiedadDto().getBuque().setMotor(null);

		PropiedadDto propiedadAux = null;

		// Validamos la propiedad
		resultadoPropiedad = servicioPropiedad.validarPropiedad(getTramiteRegRbmDto());
		// Validamos el buque
		resultadoBuque = servicioBuqueRegistro.validarBuqueRegistro(getTramiteRegRbmDto().getPropiedadDto().getBuque());

		if (resultadoPropiedad.isError() || resultadoBuque.isError()) {
			if (resultadoPropiedad.getValidaciones() != null && !resultadoPropiedad.getValidaciones().isEmpty()) {
				for (String validacion : resultadoPropiedad.getValidaciones()) {
					addActionError(validacion);
				}
			}

			if (resultadoBuque.getValidaciones() != null && !resultadoBuque.getValidaciones().isEmpty()) {
				for (String validacion : resultadoBuque.getValidaciones()) {
					addActionError(validacion);
				}
			}
			propiedadAux = getTramiteRegRbmDto().getPropiedadDto();
			recuperarContrato();
			getTramiteRegRbmDto().setPropiedadDto(propiedadAux);
			if (null != getTramiteRegRbmDto().getPropiedadDto().getBuque() && 0 != getTramiteRegRbmDto().getPropiedadDto().getBuque().getIdBuque())
				getTramiteRegRbmDto().getPropiedadDto().getBuque().setMotorBuques(servicioMotorBuque.getMotoresPorBuque(String.valueOf(getTramiteRegRbmDto().getPropiedadDto().getBuque()
						.getIdBuque())));
			return RESULTADO_EDIT;
		}

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			resultadoBuque = servicioMotorBuque.getMotorBuque(getIdentificador());
			getTramiteRegRbmDto().getPropiedadDto().getBuque().setMotor((MotorBuqueDto) resultadoBuque.getObj());
		}

		return POP_UP_ANIADIR_MOTOR;
	}

	public String aniadirMotor() {

		ResultRegistro resultado;
		PropiedadDto propiedadAux = null;

		guardarContrato(true);

		resultado = servicioPropiedad.guardarOActualizarPropiedad(getTramiteRegRbmDto());
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
			propiedadAux = getTramiteRegRbmDto().getPropiedadDto();
			recuperarContrato();
			getTramiteRegRbmDto().setPropiedadDto(propiedadAux);
			if (null != getTramiteRegRbmDto().getPropiedadDto().getBuque() && 0 != getTramiteRegRbmDto().getPropiedadDto().getBuque().getIdBuque())
				getTramiteRegRbmDto().getPropiedadDto().getBuque().setMotorBuques(servicioMotorBuque.getMotoresPorBuque(String.valueOf(getTramiteRegRbmDto().getPropiedadDto().getBuque())));
			return RESULTADO_EDIT;
		} else {

			addActionMessage("Motor del buque añadido correctamente");
		}

		recuperarContrato();
		return RESULTADO_EDIT;
	}

	public String borrarMotor() {

		ResultRegistro resultado = new ResultRegistro();
		PropiedadDto propiedadAux = null;

		guardarContrato(true);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			try {
				resultado = servicioMotorBuque.borrarMotorBuque(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al eliminar el motor del buque");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Motor borrado correctamente");
			}

			propiedadAux = getTramiteRegRbmDto().getPropiedadDto();
			recuperarContrato();
			getTramiteRegRbmDto().setPropiedadDto((PropiedadDto) (servicioPropiedad.getPropiedad(String.valueOf(propiedadAux.getIdPropiedad()))).getObj());
		} else {
			recuperarContrato();
		}
		return RESULTADO_EDIT;
	}

	// ----------------------------------------------Fin Motores Buques ------------------------------------------------------------------------

	// ----------------------------------------------Bloque Acuerdos -------------------------------------------------------------------------------------

	public String aniadirAcuerdo() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		try {
			resultado = servicioPacto.guardarOActualizarPacto(getTramiteRegRbmDto().getPactoDto(), getTramiteRegRbmDto().getIdTramiteRegistro());
		} catch (Exception e) {
			resultado.setMensaje("Error al actualizar acuerdo");
			resultado.setError(Boolean.TRUE);
			log.error(e.getMessage());
		}
		if (resultado.isError()) {
			if (resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
				for (String validacion : resultado.getValidaciones()) {
					addActionError(validacion);
				}
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionMessage(resultado.getMensaje());
		}

		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String borrarAcuerdo() {

		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {
			try {
				resultado = servicioPacto.borrarPacto(getIdentificador());
			} catch (Exception e) {
				resultado.setMensaje("Error al eliminar acuerdo");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage(resultado.getMensaje());
			}

		}
		recuperarContrato();

		return RESULTADO_EDIT;
	}

	public String modificarAcuerdo() {
		ResultRegistro resultado = new ResultRegistro();

		guardarContrato(true);

		recuperarContrato();

		if (getIdentificador() != null && !getIdentificador().trim().isEmpty()) {

			try {
				resultado = servicioPacto.getPacto(getIdentificador());
				getTramiteRegRbmDto().setPactoDto((PactoDto) resultado.getObj());

			} catch (Exception e) {
				resultado.setMensaje("Error al obtener acuerdo");
				resultado.setError(Boolean.TRUE);
				log.error(e.getMessage());
			}
			if (resultado.isError()) {
				addActionError(resultado.getMensaje());
			}

		}

		return RESULTADO_EDIT;
	}

	// ---------------------------------------------Fin Bloque Acuerdos ------------------------------------------------------------------------------

	// ---------------------------------------------Bloque Documentos ------------------------------------------------------------------------

	// Para añadir ficheros a la lista desde el botón de la pestaña documentación
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
				// Verifica que no se ha alcanzado el máximo de ficheros permitidos:
				int maxFicheros = Integer.parseInt(gestorPropiedades.valorPropertie("envioEscrituras.numero.maximo.ficheros"));
				if (ficherosSubidos.size() == maxFicheros) {
					addActionError("Alcanzado el máximo de ficheros a incluir en el envío de documentación: " + maxFicheros);
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperarContrato();
					return returnStruts;
				}
				// Verifica que el nombre del fichero no contenga caracteres especiales
				if (!utiles.esNombreFicheroValido(fileUploadFileName)) {
					addActionError("El fichero añadido debe tener un nombre correcto");
					log.error(Claves.CLAVE_LOG_REGISTRADORES_COMUN_ERROR + cadenaIncluirFichero);
					recuperarContrato();
					return returnStruts;
				}
				// Verifica que el fichero subido tiene un formato soportado
				String extension = utiles.dameExtension(fileUploadFileName, false);
				if (extension == null) {
					addActionError("El fichero añadido debe tener un formato soportado: .pkcs7, .pdf, .rtf, .doc, .tif, .xls, .xml, .jpg, .jpeg o .zip");
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
						addActionMessage("Fichero añadido correctamente");
						getTramiteRegRbmDto().setFicherosSubidos(ficherosSubidos);
						getTramiteRegRbmDto().setExtensionFicheroEnviado(ficheroInfo.getExtension());
						if (getTramiteRegRbmDto().getFicheroSubido() == null) {
							getTramiteRegRbmDto().setFicheroSubido("si");
							servicioTramiteRegistro.actualizarFicheroSubido(getTramiteRegRbmDto().getIdTramiteRegistro());
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

			// Si está 'finalizado con error' lo pone en 'iniciado':
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
		addActionError("No existe documentación para descargar");
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

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
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

	public long getIdFinanciadorPadre() {
		return idFinanciadorPadre;
	}

	public void setIdFinanciadorPadre(long idFinanciadorPadre) {
		this.idFinanciadorPadre = idFinanciadorPadre;
	}

	public String getIdFinanciador() {
		return idFinanciador;
	}

	public void setIdFinanciador(String idFinanciador) {
		this.idFinanciador = idFinanciador;
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

	public String getIdFicheroEliminar() {
		return idFicheroEliminar;
	}

	public void setIdFicheroEliminar(String idFicheroEliminar) {
		this.idFicheroEliminar = idFicheroEliminar;
	}

}
