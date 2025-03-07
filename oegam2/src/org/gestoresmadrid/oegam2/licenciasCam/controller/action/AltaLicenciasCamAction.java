package org.gestoresmadrid.oegam2.licenciasCam.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPlantaBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPortalAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDocumentoLicencia;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEdificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcEpigrafe;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInterviniente;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcObra;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcParteAutonoma;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcSupNoComputablePlanta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicConsultarSolicitudRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicEnviarDocuRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicEnviarSolicitudRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicRegSolicitudRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaBajaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPortalAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcEpigrafeDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioBajaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcParteAutonomaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcSupNoComputablePlantaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

public class AltaLicenciasCamAction extends ActionBase {

	private static final long serialVersionUID = 6285830755409836849L;
	private static Logger log = Logger.getLogger(AltaLicenciasCamAction.class);

	private static final String ALTA_LICENCIAS_CAM = "altaSolicitudLicencia";
	private static final String DESCARGAR_DOCUMENTO = "descargarDocumento";

	private LcTramiteDto lcTramiteDto;
	private Long identificador;
	private String identificadorTramite;
	private String identificadorTipoObra;
	private boolean utilizarTitular;
	private String numDocOtroInteresado;

	// Divs Baja
	private boolean mostrarDivInfoEdificioBajaLicencia;
	private boolean mostrarDivPlantaBaja;

	// Divs Alta
	private boolean mostrarDivInfoEdificioAltaLicencia;
	private boolean mostrarDivPortalAlta;
	private boolean mostrarDivPlantaAlta;
	private boolean mostrarDivSupNoComputable;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String ficheroDescarga;
	private String fileUploadFileName;
	private File fileUpload;
	private String idFicheroEliminar;
	private String idFicheroDescargar;
	private String nombreDoc;
	private String tipoDocumentoLic;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioLcTramite servicioLcTramite;

	@Autowired
	ServicioLcInterviniente servicioLcInterviniente;

	@Autowired
	ServicioLcEpigrafe servicioLcEpigrafe;

	@Autowired
	ServicioLcParteAutonoma servicioLcParteAutonoma;

	@Autowired
	ServicioLcObra servicioLcObra;

	@Autowired
	ServicioLcEdificacion servicioLcEdificacion;

	@Autowired
	ServicioLcInfoEdificioAlta servicioLcInfoEdificioAlta;

	@Autowired
	ServicioLcDatosPortalAlta servicioLcDatosPortalAlta;

	@Autowired
	ServicioLcDatosPlantaAlta servicioLcDatosPlantaAlta;

	@Autowired
	ServicioLcSupNoComputablePlanta servicioLcSupNoComputablePlanta;

	@Autowired
	ServicioLcInfoEdificioBaja servicioLcInfoEdificioBaja;

	@Autowired
	ServicioLcDatosPlantaBaja servicioLcDatosPlantaBaja;

	@Autowired
	ServicioLcDocumentoLicencia servicioLcDocumentoLicencia;

	@Autowired
	ServicioLicEnviarDocuRestWS servicioLicEnviarDocuRestWS;

	@Autowired
	ServicioLicEnviarSolicitudRestWS servicioLicEnviarSolicitudRestWS;

	@Autowired
	ServicioLicConsultarSolicitudRestWS servicioLicConsultarSolicitudRestWS;

	@Autowired
	ServicioLicRegSolicitudRestWS servicioLicRegSolicitudRestWS;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicio() {
		lcTramiteDto = new LcTramiteDto();
		lcTramiteDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		lcTramiteDto.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		lcTramiteDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(lcTramiteDto.getNumColegiado(), lcTramiteDto.getIdContrato()));
		utilizarTitular = false;
		return ALTA_LICENCIAS_CAM;
	}

	public String guardar() {
		ResultadoLicenciasBean respuestaBean = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			if (lcTramiteDto.getNumExpediente() == null) {
				respuestaBean = servicioLcTramite.crearTramite(lcTramiteDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
			}

			if (!respuestaBean.getError()) {
				respuestaBean = servicioLcTramite.guardarTramite(lcTramiteDto);
			}
		} catch (Exception e) {
			respuestaBean.setError(Boolean.TRUE);
			respuestaBean.addMensaje("Error al guardar el trámite");
			log.error(e.getMessage());
		}
		if (!respuestaBean.getError()) {
			addActionMessage("Trámite guardado correctamente");
			respuestaBean = servicioLcTramite.cambiarEstado(true, lcTramiteDto.getNumExpediente(), lcTramiteDto.getEstado(), new BigDecimal(EstadoLicenciasCam.Iniciado.getValorEnum()), utilesColegiado
					.getIdUsuarioSessionBigDecimal());
			if (respuestaBean.getError()) {
				log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + "Ha ocurrido el siguiente error actualizando a 'Iniciado' el trámite con identificador: " + lcTramiteDto.getNumExpediente() + " : "
						+ respuestaBean.getMensaje());
			}
			recuperarLicencia();
		} else {
			if (lcTramiteDto.getNumExpediente() != null) {
				recuperarListas();
			}
			addActionError(respuestaBean.getMensaje());
			lcTramiteDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(lcTramiteDto.getNumColegiado(), lcTramiteDto.getIdContrato()));
		}
		return ALTA_LICENCIAS_CAM;
	}

	// Recupera todos los datos de la licencia
	public String recuperarLicencia() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			if (lcTramiteDto != null && lcTramiteDto.getNumExpediente() != null) {
				resultado = servicioLcTramite.getTramiteLc(lcTramiteDto.getNumExpediente());
			} else {
				resultado = servicioLcTramite.getTramiteLc(new BigDecimal(getIdentificadorTramite()));
			}
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Error al recuperar el trámite");
			log.error(e.getMessage());
		}
		if (resultado.getError()) {
			lcTramiteDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(lcTramiteDto.getNumColegiado(), lcTramiteDto.getIdContrato()));
			addActionError(resultado.getMensaje());
			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto = (LcTramiteDto) resultado.getObj();
			if (lcTramiteDto.getIntervinienteNotificacion() != null) {
				utilizarTitular = true;
			}
		}
		return ALTA_LICENCIAS_CAM;
	}

	public String validar() {
		ResultadoLicenciasBean resultado = servicioLcTramite.getTramiteLc(lcTramiteDto.getNumExpediente());
		if (resultado != null && !resultado.getError()) {
			LcTramiteDto tramiteDto = (LcTramiteDto) resultado.getObj();

			resultado = servicioLcTramite.validarTramite(tramiteDto);
			if (resultado != null && !resultado.getError()) {
				addActionMessage("Trámite validado correctamente");
				resultado = servicioLcTramite.cambiarEstado(true, lcTramiteDto.getNumExpediente(), lcTramiteDto.getEstado(), new BigDecimal(EstadoLicenciasCam.Validado.getValorEnum()), utilesColegiado
						.getIdUsuarioSessionBigDecimal());
				recuperarLicencia();
			} else {
				if (lcTramiteDto.getNumExpediente() != null) {
					recuperarListas();
				}
				if (resultado != null && resultado.getValidaciones() != null && !resultado.getValidaciones().isEmpty()) {
					int numLinea = 0;
					for (String validacion : resultado.getValidaciones()) {
						if (numLinea < 10) {
							addActionError(validacion);
						} else {
							addActionError("...");
							break;
						}
						numLinea++;
					}
				}
				lcTramiteDto.setPresentador(servicioPersona.obtenerColegiadoCompleto(lcTramiteDto.getNumColegiado(), lcTramiteDto.getIdContrato()));
			}
		} else {
			if (lcTramiteDto.getNumExpediente() != null) {
				recuperarListas();
			}
			addActionError("Trámite no recuperado");
		}
		return ALTA_LICENCIAS_CAM;
	}

	public String comprobar() {
		try {
			if (lcTramiteDto.getNumExpediente() != null) {
				ResultBean resultado = servicioLicEnviarSolicitudRestWS.validarSolicitud(lcTramiteDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession(), lcTramiteDto
						.getIdContrato().longValue(), utilesColegiado.getAlias());
				if (resultado != null && !resultado.getError()) {
					addActionMessage("Solicitud creada correctamente.");
				} else {
					addActionError("Ha sucedido un error al validar la solicitud: " + resultado.getMensaje());
				}
			} else {
				addActionError("No existe número expediente asociado al trámite, guarde primero el trámite para validar la solicitud.");
			}
		} catch (Exception e) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + "Error al validar la solicitud");
			addActionError("Ha sucedido un error al validar la solicitud.");
		}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	public String enviar() {
		try {
			if (lcTramiteDto.getNumExpediente() != null) {
				ResultBean resultado = servicioLicEnviarSolicitudRestWS.enviarSolicitud(lcTramiteDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession(), lcTramiteDto
						.getIdContrato().longValue(), utilesColegiado.getAlias());
				if (resultado != null && !resultado.getError()) {
					addActionMessage("Solicitud creada correctamente.");
				} else {
					addActionError("Ha sucedido un error al enviar la solicitud: " + resultado.getMensaje());
				}
			} else {
				addActionError("No existe número expediente asociado al trámite, guarde primero el trámite para enviar la solicitud.");
			}
		} catch (Exception e) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + "Error al enviar la solicitud");
			addActionError("Ha sucedido un error al enviar la solicitud.");
		}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	public String consultar() {
		try {
			if (lcTramiteDto.getNumExpediente() != null) {
				ResultBean resultado = servicioLicConsultarSolicitudRestWS.consultarSolicitudRest(lcTramiteDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession());
				if (resultado != null && !resultado.getError()) {
					addActionMessage("Solicitud registrada.");
				} else {
					addActionMessage("La solicitud esta en el siguiente estado: " + resultado.getMensaje());
					if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
						addActionMessage("Contiene los siguientes mensajes: ");
						for (String mensaje : resultado.getListaMensajes()) {
							addActionError(mensaje);
						}
					}
				}
			} else {
				addActionError("No existe número expediente asociado al trámite, guarde primero el trámite para consultar la solicitud.");
			}
		} catch (Exception e) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + "Error al consultar la solicitud");
			addActionError("Ha sucedido un error al consultar la solicitud.");
		}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	public String presentar() {
		try {
			if (lcTramiteDto.getNumExpediente() != null) {
				ResultBean resultado = servicioLicRegSolicitudRestWS.registrarSolicitud(lcTramiteDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession(), lcTramiteDto
						.getIdContrato().longValue(), utilesColegiado.getAlias());
				if (resultado != null && !resultado.getError()) {
					addActionMessage("Solicitud creada correctamente.");
				} else {
					addActionError("Ha sucedido un error al presentar la solicitud: " + resultado.getMensaje());
				}
			} else {
				addActionError("No existe número expediente asociado al trámite, guarde primero el trámite para presentar la solicitud.");
			}
		} catch (Exception e) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + "Error al presentar la solicitud");
			addActionError("Ha sucedido un error al presentar la solicitud.");
		}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	// Recupera solamente las listas del contrato
	public void recuperarListas() {
		ResultadoLicenciasBean resultado = servicioLcTramite.getTramiteLc(lcTramiteDto.getNumExpediente());
		LcTramiteDto tramite = (LcTramiteDto) resultado.getObj();
		if (tramite != null) {
			lcTramiteDto.setOtrosInteresados(tramite.getOtrosInteresados());

			// Se recupera la lista de Tipos Obra
			if (tramite.getLcObra() != null && tramite.getLcObra().getTiposObra() != null && !tramite.getLcObra().getTiposObra().isEmpty()) {
				lcTramiteDto.getLcObra().setTiposObra(tramite.getLcObra().getTiposObra());
			}

			// Se recupera la información edificio Baja
			if (tramite.getLcEdificacionBaja() != null) {
				lcTramiteDto.getLcEdificacionBaja().setLcInfoEdificiosBaja(tramite.getLcEdificacionBaja().getLcInfoEdificiosBaja());
			}

			// Se recuperan las plantas baja
			if (null != lcTramiteDto.getLcEdificacionBaja() && null != lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja() && null != lcTramiteDto.getLcEdificacionBaja()
					.getLcInfoEdificioBaja().getIdInfoEdificioBaja()) {
				lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setLcDatosPlantasBaja(servicioLcDatosPlantaBaja.getPlantasBaja(lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja()
						.getIdInfoEdificioBaja()));
			}

			// Se recupera la información edificio alta
			if (null != tramite.getLcEdificacionAlta()) {
				lcTramiteDto.getLcEdificacionAlta().setLcInfoEdificiosAlta(tramite.getLcEdificacionAlta().getLcInfoEdificiosAlta());
			}

			// Se recuperan los portales alta
			if (null != lcTramiteDto.getLcEdificacionAlta() && null != lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta()) {
				if (null != lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getIdInfoEdificioAlta() && mostrarDivInfoEdificioAltaLicencia) {
					lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setLcDatosPortalesAlta(servicioLcDatosPortalAlta.getPortalesAlta(lcTramiteDto.getLcEdificacionAlta()
							.getLcInfoEdificioAlta().getIdInfoEdificioAlta()));
				}

				// Se recuperan las plantas alta
				if (mostrarDivPortalAlta && null != lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta() && null != lcTramiteDto.getLcEdificacionAlta()
						.getLcInfoEdificioAlta().getLcDatosPortalAlta().getIdDatosPortalAlta()) {
					lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setLcDatosPlantasAlta(servicioLcDatosPlantaAlta.getPlantasAlta(lcTramiteDto
							.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getIdDatosPortalAlta()));
				}

				// Se recuperan las Superficies no computables alta
				if (mostrarDivPlantaAlta && null != lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta() && null != lcTramiteDto.getLcEdificacionAlta()
						.getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta() && null != lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta()
								.getLcDatosPlantaAlta().getIdDatosPlantaAlta()) {
					lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setLcSupNoComputablesPlanta(servicioLcSupNoComputablePlanta
							.getSupNoComputablesPlanta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getIdDatosPlantaAlta()));
				}
			}

			// Se recuperan los epígrafes
			if (tramite.getLcInfoLocal() != null) {
				lcTramiteDto.getLcInfoLocal().setLcEpigrafes(tramite.getLcInfoLocal().getLcEpigrafes());
			}
			// Se recuperan las partes autónomas
			if (tramite.getLcObra() != null) {
				lcTramiteDto.getLcObra().setPartesAutonomas(tramite.getLcObra().getPartesAutonomas());
			}
		}
	}

	// ----------------------------------------------Bloque Otros interesados
	// ------------------------------------------------------------------------

	public String modificarOtroInteresado() {
		if (StringUtils.isNotBlank(getNumDocOtroInteresado())) {
			ResultadoLicenciasBean resultado = servicioLcInterviniente.buscarPersona(getNumDocOtroInteresado(), lcTramiteDto.getNumColegiado());
			if (!resultado.getError()) {
				lcTramiteDto.setIntervinienteOtrosInteresados(resultado.getInterviniente());
				lcTramiteDto.getIntervinienteOtrosInteresados().setTipoInterviniente(TipoInterviniente.OtroInteresado.getValorEnum());
			}
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borrarOtroInteresado() {
		if (identificador != null) {
			ResultadoLicenciasBean result = servicioLcInterviniente.borrarInterviniente(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Interviniente interesado borrado correctamente");
			}
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Otros interesados
	// ------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Epígrafes
	// ------------------------------------------------------------------------

	public String modificarEpigrafe() {
		if (identificador != null) {
			ResultadoLicenciasBean resultado = servicioLcEpigrafe.getEpigrafe(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcInfoLocal().setLcEpigrafeDto((LcEpigrafeDto) resultado.getObj());
			}
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borrarEpigrafe() {
		if (identificador != null) {
			ResultadoLicenciasBean result = servicioLcEpigrafe.borrarEpigrafe(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Epígrafe borrado correctamente");
			}
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Epígrafes
	// ------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Partes Autónomas
	// ------------------------------------------------------------------------

	public String modificarParteAutonoma() {
		if (identificador != null) {
			ResultadoLicenciasBean resultado;
			resultado = servicioLcParteAutonoma.getParteAutonoma(getIdentificador());

			if (!resultado.getError()) {
				lcTramiteDto.getLcObra().setParteAutonoma((LcParteAutonomaDto) resultado.getObj());
			}
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borrarParteAutonoma() {
		if (identificador != null) {
			ResultadoLicenciasBean result = servicioLcParteAutonoma.borrarParteAutonoma(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Parte Autónoma borrada correctamente");
			}
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Partes Autónomas
	// ------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Info Edificio Baja
	// Licencia
	// ------------------------------------------------------------------------

	public String cargarDivInfoEdificioBajaLicencia() {
		lcTramiteDto.getLcEdificacionBaja().setLcInfoEdificioBaja(null);

		// Edificación Baja Licencia no esta guardado, validamos
		lcTramiteDto.getLcEdificacionBaja().setNumExpediente(lcTramiteDto.getNumExpediente());
		ResultadoLicenciasBean resultado = servicioLcEdificacion.guardarOActualizarEdificacion(lcTramiteDto.getLcEdificacionBaja(), false);
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioBajaLicencia(Boolean.FALSE);
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto.getLcEdificacionBaja().setIdEdificacion((Long) resultado.getObj());
		}

		// Comprobamos que se hayan guardado los datos edificación baja para poder dar de alta la información de edificio baja
		if (lcTramiteDto.getLcEdificacionBaja().getIdEdificacion() == null) {
			addActionError("Para añadir edificios debe antes rellenar el campo Tipo Demolición e Industrial pertenecientes a la Edificación Baja.");
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		}

		if (identificador != null) {
			resultado = servicioLcInfoEdificioBaja.getDatosInfoEdificioBaja(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcEdificacionBaja().setLcInfoEdificioBaja((LcInfoEdificioBajaDto) resultado.getObj());
			}
		}

		setMostrarDivInfoEdificioBajaLicencia(Boolean.TRUE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String aniadirInfoEdificioBajaLicencia() {

		if (identificador == null) {
			lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setIdEdificacion(lcTramiteDto.getLcEdificacionBaja().getIdEdificacion());
		}
		ResultadoLicenciasBean resultado = servicioLcInfoEdificioBaja.guardarOActualizarInfoEdificioBaja(lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setIdInfoEdificioBaja((Long) resultado.getObj());
			addActionMessage("Información de edificio añadido correctamente");
		}

		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borrarInfoEdificioBajaLicencia() {

		if (identificador != null) {
			ResultadoLicenciasBean resultado = servicioLcInfoEdificioBaja.borrarInfoEdificioBaja(getIdentificador());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Información de edificio borrado correctamente");
			}
			recuperarListas();
		} else {
			recuperarListas();
		}

		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Info Edificio Baja
	// Licencia
	// ------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Planta Baja
	// ------------------------------------------------------------------------

	public String cargarDivPlantaBaja() {
		lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setLcDatosPlantaBaja(null);

		// Edificación Baja Licencia no esta guardado, validamos
		lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setIdEdificacion(lcTramiteDto.getLcEdificacionBaja().getIdEdificacion());

		ResultadoLicenciasBean resultado = servicioLcInfoEdificioBaja.guardarOActualizarInfoEdificioBaja(lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioBajaLicencia(Boolean.TRUE);
			setMostrarDivPlantaBaja(Boolean.FALSE);
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setIdInfoEdificioBaja((Long) resultado.getObj());
		}

		if (identificador != null) {
			resultado = servicioLcDatosPlantaBaja.getDatosPlantaBaja(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().setLcDatosPlantaBaja((LcDatosPlantaBajaDto) resultado.getObj());
			}
		}

		setMostrarDivInfoEdificioBajaLicencia(Boolean.TRUE);
		setMostrarDivPlantaBaja(Boolean.TRUE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String aniadirDatosPlantaBaja() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		if (lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getIdInfoEdificioBaja() != null) {
			lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDatosPlantaBaja().setIdInfoEdificioBaja(lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja()
					.getIdInfoEdificioBaja());
		}

		try {
			resultado = servicioLcDatosPlantaBaja.guardarOActualizarDatosPlantaBaja(lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDatosPlantaBaja());
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Error al actualizar el los datos de Planta Baja");
			log.error(e.getMessage());
		}
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioBajaLicencia(Boolean.TRUE);
			setMostrarDivPlantaBaja(Boolean.TRUE);
		} else {
			lcTramiteDto.getLcEdificacionBaja().getLcInfoEdificioBaja().getLcDatosPlantaBaja().setIdDatosPlantaBaja((Long) resultado.getObj());
			addActionMessage(resultado.getMensaje());
			setMostrarDivInfoEdificioBajaLicencia(Boolean.TRUE);
			setMostrarDivPlantaBaja(Boolean.FALSE);
		}

		recuperarListas();

		return ALTA_LICENCIAS_CAM;
	}

	public String borraPlantaBaja() {
		ResultadoLicenciasBean result;
		if (identificador != null) {
			result = servicioLcDatosPlantaBaja.borrarDatosPlantaBaja(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Datos de Planta Baja borrados correctamente");
			}
		}

		setMostrarDivInfoEdificioBajaLicencia(Boolean.TRUE);
		setMostrarDivPlantaBaja(Boolean.FALSE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Planta Baja
	// -----------------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Info Edificio Alta
	// Licencia
	// ------------------------------------------------------------------------

	public String cargarDivInfoEdificioAltaLicencia() {
		lcTramiteDto.getLcEdificacionAlta().setLcInfoEdificioAlta(null);

		// Parámetro Edificación Alta Licencia no esta guardado, validamos
		lcTramiteDto.getLcEdificacionAlta().setNumExpediente(lcTramiteDto.getNumExpediente());
		ResultadoLicenciasBean resultado = servicioLcEdificacion.guardarOActualizarEdificacion(lcTramiteDto.getLcEdificacionAlta(), true);
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.FALSE);
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto.getLcEdificacionAlta().setIdEdificacion((Long) resultado.getObj());
		}

		// Comprobamos que se haya guardado los parámetros de Edificación Alta para poder dar de alta la información de edificio alta
		if (lcTramiteDto.getLcEdificacionAlta().getIdEdificacion() == null) {
			addActionError(
					"Para añadir edificios debe antes rellenar alguno de los bloques de Edificación Alta (Datos Edificación, Datos Aparcamiento, Proyecto Garaje Aparcamiento y/o Resumen Edificación).");
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		}

		if (identificador != null) {
			resultado = servicioLcInfoEdificioAlta.getDatosInfoEdificioAlta(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcEdificacionAlta().setLcInfoEdificioAlta((LcInfoEdificioAltaDto) resultado.getObj());
			}
		}
		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		recuperarListas();

		return ALTA_LICENCIAS_CAM;
	}

	public String aniadirInfoEdificioAltaLicencia() {
		if (identificador != null) {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setIdEdificacion(lcTramiteDto.getLcEdificacionAlta().getIdEdificacion());
		}

		ResultadoLicenciasBean resultado = servicioLcInfoEdificioAlta.guardarOActualizarInfoEdificioAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setIdInfoEdificioAlta((Long) resultado.getObj());
			addActionMessage("Información de edificio añadido correctamente");
		}

		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borrarInfoEdificioAltaLicencia() {
		if (identificador != null) {
			ResultadoLicenciasBean resultado = servicioLcInfoEdificioAlta.borrarInfoEdificioAlta(getIdentificador());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Información de edificio borrado correctamente");
			}
			recuperarListas();
		} else {
			recuperarListas();
		}
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Info Edificio Alta
	// Licencia
	// ------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Portal Alta
	// ------------------------------------------------------------------------

	public String cargarDivPortalAlta() {
		lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setLcDatosPortalAlta(null);

		// Info edificio alta no esta guardado, validamos
		lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setIdEdificacion(lcTramiteDto.getLcEdificacionAlta().getIdEdificacion());
		ResultadoLicenciasBean resultado = servicioLcInfoEdificioAlta.guardarOActualizarInfoEdificioAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.FALSE);
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setIdInfoEdificioAlta((Long) resultado.getObj());
		}

		if (identificador != null) {
			resultado = servicioLcDatosPortalAlta.getDatosPortalAlta(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().setLcDatosPortalAlta((LcDatosPortalAltaDto) resultado.getObj());
			}
		}

		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		setMostrarDivPortalAlta(Boolean.TRUE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String aniadirDatosPortalAlta() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		if (lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getIdInfoEdificioAlta() != null) {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setIdInfoEdificioAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta()
					.getIdInfoEdificioAlta());
		}

		try {
			resultado = servicioLcDatosPortalAlta.guardarOActualizarDatosPortalAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta());
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Error al actualizar el los datos de portal alta");
			log.error(e.getMessage());
		}
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setIdDatosPortalAlta((Long) resultado.getObj());
			addActionMessage(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.FALSE);
		}

		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borraPortalAlta() {
		if (identificador != null) {
			ResultadoLicenciasBean result = servicioLcDatosPortalAlta.borrarDatosPortalAlta(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Datos de Portal Alta borrados correctamente");
			}
		}
		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		setMostrarDivPortalAlta(Boolean.FALSE);
		recuperarListas();

		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Portal Alta
	// -----------------------------------------------------------------------------------

	// ---------------------------------------------- Bloque Planta Alta
	// ------------------------------------------------------------------------

	public String cargarDivPlantaAlta() {
		lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setLcDatosPlantaAlta(null);

		// Portal alta no esta guardado, validamos
		lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setIdInfoEdificioAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getIdInfoEdificioAlta());
		ResultadoLicenciasBean resultado = servicioLcDatosPortalAlta.guardarOActualizarDatosPortalAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
			setMostrarDivPlantaAlta(Boolean.FALSE);
			recuperarListas();
			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setIdDatosPortalAlta((Long) resultado.getObj());
		}

		if (identificador != null) {
			resultado = servicioLcDatosPlantaAlta.getDatosPlantaAlta(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().setLcDatosPlantaAlta((LcDatosPlantaAltaDto) resultado.getObj());
			}
		}
		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		setMostrarDivPortalAlta(Boolean.TRUE);
		setMostrarDivPlantaAlta(Boolean.TRUE);
		recuperarListas();

		return ALTA_LICENCIAS_CAM;
	}

	public String aniadirDatosPlantaAlta() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		if (lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getIdDatosPortalAlta() != null) {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setIdDatosPortalAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta()
					.getLcDatosPortalAlta().getIdDatosPortalAlta());
		}

		try {
			resultado = servicioLcDatosPlantaAlta.guardarOActualizarDatosPlantaAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta());
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Error al actualizar los datos de planta alta");
			log.error(e.getMessage());
		}
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
			setMostrarDivPlantaAlta(Boolean.TRUE);
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setIdDatosPlantaAlta((Long) resultado.getObj());
			addActionMessage(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
			setMostrarDivPlantaAlta(Boolean.FALSE);
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borraPlantaAlta() {
		if (identificador != null) {
			ResultadoLicenciasBean result = servicioLcDatosPlantaAlta.borrarDatosPlantaAlta(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Datos de Planta Alta borrados correctamente");
			}
		}
		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		setMostrarDivPortalAlta(Boolean.TRUE);
		setMostrarDivPlantaAlta(Boolean.FALSE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Planta Alta
	// -----------------------------------------------------------------------------------
	// ---------------------------------------------- Bloque Superficie No
	// Computable Planta
	// ------------------------------------------------------------------------

	public String cargarDivSupNoComputablePlanta() {
		lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setLcSupNoComputablePlanta(null);

		// Planta alta no esta guardado, validamos
		lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setIdDatosPortalAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta()
				.getLcDatosPortalAlta().getIdDatosPortalAlta());
		ResultadoLicenciasBean resultado = servicioLcDatosPlantaAlta.guardarOActualizarDatosPlantaAlta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta()
				.getLcDatosPlantaAlta());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
			setMostrarDivPlantaAlta(Boolean.TRUE);
			setMostrarDivSupNoComputable(Boolean.FALSE);
			recuperarListas();

			return ALTA_LICENCIAS_CAM;
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setIdDatosPlantaAlta((Long) resultado.getObj());
		}

		if (identificador != null) {
			resultado = servicioLcSupNoComputablePlanta.getSupNoComputablePlanta(getIdentificador());
			if (!resultado.getError()) {
				lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().setLcSupNoComputablePlanta((LcSupNoComputablePlantaDto) resultado.getObj());
			}
		}
		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		setMostrarDivPortalAlta(Boolean.TRUE);
		setMostrarDivPlantaAlta(Boolean.TRUE);
		setMostrarDivSupNoComputable(Boolean.TRUE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String aniadirSupNoComputablePlanta() {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);

		if (lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getIdDatosPlantaAlta() != null) {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getLcSupNoComputablePlanta().setIdDatosPlantaAlta(lcTramiteDto
					.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getIdDatosPlantaAlta());
		}

		try {
			resultado = servicioLcSupNoComputablePlanta.guardarOActualizarSupNoComputablesPlanta(lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta()
					.getLcDatosPlantaAlta().getLcSupNoComputablePlanta());
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Error al actualizar los datos de Superficie No Computable Alta");
			log.error(e.getMessage());
		}
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
			setMostrarDivPlantaAlta(Boolean.TRUE);
			setMostrarDivSupNoComputable(Boolean.TRUE);
		} else {
			lcTramiteDto.getLcEdificacionAlta().getLcInfoEdificioAlta().getLcDatosPortalAlta().getLcDatosPlantaAlta().getLcSupNoComputablePlanta().setIdSupNoComputablePlanta((Long) resultado
					.getObj());
			addActionMessage(resultado.getMensaje());
			setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
			setMostrarDivPortalAlta(Boolean.TRUE);
			setMostrarDivPlantaAlta(Boolean.TRUE);
			setMostrarDivSupNoComputable(Boolean.FALSE);
		}
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	public String borraSupNoComputablePlanta() {
		if (identificador != null) {
			ResultadoLicenciasBean result = servicioLcSupNoComputablePlanta.borrarSupNoComputablePlanta(getIdentificador());
			if (result.getError()) {
				addActionError(result.getMensaje());
			} else {
				addActionMessage("Datos de Superficie No Computable Alta borrados correctamente");
			}
		}
		setMostrarDivInfoEdificioAltaLicencia(Boolean.TRUE);
		setMostrarDivPortalAlta(Boolean.TRUE);
		setMostrarDivPlantaAlta(Boolean.TRUE);
		setMostrarDivSupNoComputable(Boolean.FALSE);
		recuperarListas();
		return ALTA_LICENCIAS_CAM;
	}

	// ---------------------------------------------- Fin Bloque Superficie No
	// Computable Planta
	// -----------------------------------------------------------------------------------

	// -------------------------------Tipo de obra -------------------------------------------------------------------------------------

	public String borrarTipoObra() {
		StringBuffer tipoCadena = new StringBuffer();
		if (StringUtils.isNotBlank(getIdentificadorTipoObra()) && StringUtils.isNotBlank(lcTramiteDto.getLcObra().getTipoObra())) {
			String[] tipos = lcTramiteDto.getLcObra().getTipoObra().split(";");
			for (String tipo : tipos) {
				if (!tipo.equals(getIdentificadorTipoObra())) {
					if (tipoCadena.length() > 0) {
						tipoCadena.append(";");
					}
					tipoCadena.append(tipo);
				}
			}
			lcTramiteDto.getLcObra().setTipoObra(tipoCadena.toString());
		}

		ResultadoLicenciasBean resultado = servicioLcObra.guardarOActualizarObra(lcTramiteDto.getLcObra());
		if (!resultado.getError()) {
			addActionMessage("Tipo Obra eliminado correctamente");
		} else {
			addActionError(resultado.getMensaje());
		}
		recuperarLicencia();
		return ALTA_LICENCIAS_CAM;
	}

	// -----------------------------------------------Bloque Documentos---------------------------
	// ------------------------------------------------------------------------------------------

	public String enviarDocumentacion() {
		try {
			if (lcTramiteDto.getNumExpediente() != null) {
				ArrayList<FicheroInfo> ficherosSubidos = servicioLcDocumentoLicencia.recuperarDocumentos(lcTramiteDto.getNumExpediente());
				if (ficherosSubidos != null && !ficherosSubidos.isEmpty()) {
					ResultBean resultado = servicioLicEnviarDocuRestWS.enviarDocumentacion(lcTramiteDto.getNumExpediente(), utilesColegiado.getIdUsuarioSession(), lcTramiteDto
							.getIdContrato().longValue(), utilesColegiado.getAlias(), ficherosSubidos);
					if (resultado != null && !resultado.getError()) {
						addActionMessage("Solicitud creada correctamente.");
					} else {
						addActionError("Ha sucedido un error al enviar la documentación: " + resultado.getMensaje());
					}
				}
			} else {
				addActionError("No existe número expediente asociado al trámite, guarde primero el trámite para poder subir documentación.");
			}
		} catch (Exception e) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + "Error al enviar la documentación");
			addActionError("Ha sucedido un error al enviar la documentación.");
		}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	// Para añadir ficheros a la lista desde el botón de la pestaña documentación
	public String incluirFichero() throws OegamExcepcion {
		try {
				if(!utiles.esNombreFicheroValido(fileUploadFileName)) {
					addActionError("El nombre del fichero es erroneo");
				}else {
					if (lcTramiteDto.getNumExpediente() != null) {
						ArrayList<FicheroInfo> ficherosSubidos = servicioLcDocumentoLicencia.recuperarDocumentos(lcTramiteDto.getNumExpediente());
						if (ficherosSubidos == null) {
							ficherosSubidos = new ArrayList<FicheroInfo>();
						}
						if (fileUpload != null) {
							String extension = utiles.dameExtension(fileUploadFileName, false);
							if (extension == null) {
								addActionError("El fichero añadido no tiene extensión");
							} else if (validarExtension(extension)) {
								try {
									ResultadoLicenciasBean resultado = servicioLcDocumentoLicencia.guardar(lcTramiteDto.getNumExpediente(), lcTramiteDto.getIdContrato().longValue(), tipoDocumentoLic);
									if (resultado != null && !resultado.getError()) {
										Long idFichero = (Long) resultado.getAttachment(ServicioLcDocumentoLicencia.ID_DOCUMENTO_LICENCIA);
										File fichero = servicioLcDocumentoLicencia.guardarFichero(fileUpload, lcTramiteDto.getNumExpediente(), extension, tipoDocumentoLic, idFichero);
										FicheroInfo ficheroInfo = new FicheroInfo(fichero, ficherosSubidos.size());
										ficheroInfo.setTipoDocumento(servicioLcDocumentoLicencia.obtenerDescripcionTipoDocumento(ficheroInfo.getNombre()));
										ficherosSubidos.add(ficheroInfo);
										addActionMessage("Fichero añadido correctamente");
										lcTramiteDto.setFicherosSubidos(ficherosSubidos);
										tipoDocumentoLic = null;
									} else {
										addActionError("Existe un error al tratar de subir el fichero.");
									}
								} catch (Exception ex) {
									log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + ex);
									addActionError("Existe un error al tratar de subir el fichero.");
								}
							} else {
								addActionError("El fichero añadido debe tener un formato soportado.");
							}
						} else {
							addActionError("Utilice el botón 'Examinar' para seleccionar los ficheros a adjuntar en el envío.");
						}
					} else {
						addActionError("No existe número expediente asociado al trámite, guarde primero el trámite para poder subir documentación.");
					}
				}
			} catch (Exception ex) {
				log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + ex);
				addActionError("Existe un error al tratar de subir el fichero.");
			}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	private boolean validarExtension(String extension) {
		List<DatoMaestroLicBean> formatos = gestorDatosMaestrosLic.obtenerFormatoArchivoSoportados();
		if (formatos != null && !formatos.isEmpty()) {
			for (DatoMaestroLicBean formato : formatos) {
				if (formato.getDescripcion().equals(extension.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}

	public String eliminarFichero() {
		try {
			if (lcTramiteDto == null) {
				log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + " No se ha recuperado trámite en curso de la sesión.");
				return ALTA_LICENCIAS_CAM;
			}
			ArrayList<FicheroInfo> ficheros = servicioLcDocumentoLicencia.recuperarDocumentos(lcTramiteDto.getNumExpediente());
			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(getIdFicheroEliminar())) {
						// Borrado fisico
						ResultadoLicenciasBean resultado = servicioLcDocumentoLicencia.eliminar(tmp.getNombre());
						if (resultado != null && !resultado.getError()) {
							tmp.getFichero().delete();
							it.remove();
							lcTramiteDto.setFicherosSubidos(ficheros);
							addActionMessage("Fichero eliminado correctamente");
						} else {
							addActionError("Fichero no eliminado");
						}
						recuperarLicencia();
						return ALTA_LICENCIAS_CAM;
					}
				}
			} else {
				addActionError("No existe fichero para eliminar");
			}
		} catch (Exception ex) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + ex);
			addActionError("Existe un error al tratar de eliminar el fichero.");
		}

		recuperarLicencia();

		return ALTA_LICENCIAS_CAM;
	}

	public String descargarDocumentacion() {
		ArrayList<FicheroInfo> ficheros = null;
		try {
			ficheros = servicioLcDocumentoLicencia.recuperarDocumentos(lcTramiteDto.getNumExpediente());
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
		} catch (FileNotFoundException ex) {
			addActionError("No se ha podido recuperar el fichero");
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + DESCARGAR_DOCUMENTO);
		} catch (Exception ex) {
			log.error(Claves.CLAVE_LOG_LICENCIAS_COMUN_ERROR + ex);
			addActionError("Existe un error al tratar de descargar el fichero.");
		}
		return DESCARGAR_DOCUMENTO;
	}

	// ---------------------------------------------Fin Bloque Documentos
	// ------------------------------------------------------------------------------

	public LcTramiteDto getLcTramiteDto() {
		return lcTramiteDto;
	}

	public void setLcTramiteDto(LcTramiteDto lcTramiteDto) {
		this.lcTramiteDto = lcTramiteDto;
	}

	public String getIdentificadorTramite() {
		return identificadorTramite;
	}

	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public Long getIdentificador() {
		return identificador;
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

	public boolean isMostrarDivInfoEdificioBajaLicencia() {
		return mostrarDivInfoEdificioBajaLicencia;
	}

	public void setMostrarDivInfoEdificioBajaLicencia(boolean mostrarDivInfoEdificioBajaLicencia) {
		this.mostrarDivInfoEdificioBajaLicencia = mostrarDivInfoEdificioBajaLicencia;
	}

	public boolean isMostrarDivPlantaBaja() {
		return mostrarDivPlantaBaja;
	}

	public void setMostrarDivPlantaBaja(boolean mostrarDivPlantaBaja) {
		this.mostrarDivPlantaBaja = mostrarDivPlantaBaja;
	}

	public boolean isMostrarDivInfoEdificioAltaLicencia() {
		return mostrarDivInfoEdificioAltaLicencia;
	}

	public void setMostrarDivInfoEdificioAltaLicencia(boolean mostrarDivInfoEdificioAltaLicencia) {
		this.mostrarDivInfoEdificioAltaLicencia = mostrarDivInfoEdificioAltaLicencia;
	}

	public boolean isMostrarDivPortalAlta() {
		return mostrarDivPortalAlta;
	}

	public void setMostrarDivPortalAlta(boolean mostrarDivPortalAlta) {
		this.mostrarDivPortalAlta = mostrarDivPortalAlta;
	}

	public boolean isMostrarDivPlantaAlta() {
		return mostrarDivPlantaAlta;
	}

	public void setMostrarDivPlantaAlta(boolean mostrarDivPlantaAlta) {
		this.mostrarDivPlantaAlta = mostrarDivPlantaAlta;
	}

	public boolean isMostrarDivSupNoComputable() {
		return mostrarDivSupNoComputable;
	}

	public void setMostrarDivSupNoComputable(boolean mostrarDivSupNoComputable) {
		this.mostrarDivSupNoComputable = mostrarDivSupNoComputable;
	}

	public String getTipoDocumentoLic() {
		return tipoDocumentoLic;
	}

	public void setTipoDocumentoLic(String tipoDocumentoLic) {
		this.tipoDocumentoLic = tipoDocumentoLic;
	}

	public String getIdentificadorTipoObra() {
		return identificadorTipoObra;
	}

	public void setIdentificadorTipoObra(String identificadorTipoObra) {
		this.identificadorTipoObra = identificadorTipoObra;
	}

	public boolean isUtilizarTitular() {
		return utilizarTitular;
	}

	public void setUtilizarTitular(boolean utilizarTitular) {
		this.utilizarTitular = utilizarTitular;
	}

	public String getNumDocOtroInteresado() {
		return numDocOtroInteresado;
	}

	public void setNumDocOtroInteresado(String numDocOtroInteresado) {
		this.numDocOtroInteresado = numDocOtroInteresado;
	}
}
