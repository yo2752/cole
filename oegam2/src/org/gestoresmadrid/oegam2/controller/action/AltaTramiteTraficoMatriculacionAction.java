package org.gestoresmadrid.oegam2.controller.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.ivtmMatriculacion.model.enumerados.EstadoIVTM;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.NPasos;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteMatriculacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.oegam2.utiles.UtilesVistaMatriculacion;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioConsultaEitv;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioPresentacion576;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.oegamDocBase.view.bean.ResultadoDocBaseBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.viafirma.cliente.exception.InternalException;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import oegam.constantes.ConstantesSession;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.constantes.ConstantesAEAT;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.constantes.ConstantesMensajes;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class AltaTramiteTraficoMatriculacionAction extends ActionBase {

	private static final long serialVersionUID = 3773600995683885185L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaTramiteTraficoMatriculacionAction.class);

	private static final String CLONAR_TRAMITE = "aClonarTramite";
	private static final String PAGO_IVTM = "pagoIVTM";
	private static final String DESCARGAR_DOCUMENTO_BASE = "descargarDocumentoBase";
	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	private static final String DESCARGAR_PRESENTACION_576 = "descargarFichero";

	private TramiteTrafMatrDto tramiteTrafMatrDto;

	private IvtmMatriculacionDto ivtmMatriculacionDto;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	ServicioFacturacion servicioFacturacion;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioIvtmMatriculacion servicioIvtmMatriculacion;

	@Autowired
	ServicioPresentacion576 servicioPresentacion576;

	@Autowired
	ServicioConsultaEitv servicioConsultaEitv;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	UtilesColegiado utilesColegiado;

	private BigDecimal numExpediente;

	private String nifBusqueda;

	private String tipoIntervinienteBuscar;

	private TramiteTrafFacturacionDto datosFacturacion;

	private boolean utilizarTitular;

	private static final String TIPO_INTERVINIENTE_FACTURACION = "Facturacion";

	private boolean marcadoConductorHabitual;

	private String asignarPrincipalTitular;
	private String asignarPrincipalRepresentanteTitular;
	private String asignarPrincipalArrendatario;
	private String asignarPrincipalConductorHabitual;

	private PersonaDto presentador;

	private Boolean ficheroDeclaracion576;
	private Boolean pdf576;

	private String numsExpediente;
	private String tipoTramiteSeleccionado;
	private String estadoTramiteSeleccionado;
	private String bastidorSeleccionado;
	private boolean esAccionClonado = false;

	private String url;

	private String propTexto;

	private InputStream inputStream;
	private String fileName;
	private String fileSize;

	private File fileJustifIvtm;
	private String fileJustifIvtmFileName;
	private String nombreFicheroJustifIvtm;

	private DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean;

	public String inicio() {
		if (tramiteTrafMatrDto == null) {
			tramiteTrafMatrDto = new TramiteTrafMatrDto();
		}
		if (tramiteTrafMatrDto.getJefaturaTraficoDto() == null) {
			tramiteTrafMatrDto.setJefaturaTraficoDto(new JefaturaTraficoDto());
		}
		tramiteTrafMatrDto.getJefaturaTraficoDto().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		tramiteTrafMatrDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		tramiteTrafMatrDto.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		tramiteTrafMatrDto.setTipoTramiteMatr(TipoTramiteMatriculacion.MATRICULAR_TIPO_DEFINITIVA.getValorEnum());
		tramiteTrafMatrDto.setNpasos(NPasos.Uno.getValorEnum());
		tramiteTrafMatrDto.setTipoTramite(TipoTramiteTrafico.Matriculacion.getValorEnum());
		tramiteTrafMatrDto.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));

		tramiteTrafMatrDto.setExento576(false);
		setFicheroDeclaracion576(false);
		setPdf576(false);

		if (utilesColegiado.tienePermisoAdmin()) {
			presentador = servicioPersona.obtenerColegiadoCompleto(utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
		}

		propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;

		return SUCCESS;
	}

	public String guardar() throws OegamExcepcion {
		guardarTramite();
		return SUCCESS;
	}

	private ResultBean guardarTramite() throws OegamExcepcion {
		ResultBean respuestaG = new ResultBean();

		if (validarEnGuardar()) {
			addActionError("Tramite no guardado");
			respuestaG.setError(true);
			return respuestaG;
		}

		if (tramiteTrafMatrDto.getIdContrato() == null) {
			tramiteTrafMatrDto.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		}

		Boolean revisado = tramiteTrafMatrDto.getVehiculoDto().getRevisado();

		try {
			if (tramiteTrafMatrDto.getNumColegiado() == null || tramiteTrafMatrDto.getNumColegiado().isEmpty()) {
				tramiteTrafMatrDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			}
			respuestaG = servicioTramiteTraficoMatriculacion.guardarTramite(tramiteTrafMatrDto, ivtmMatriculacionDto, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermiso(
					UtilesColegiado.PERMISO_ADMINISTRACION), utilesColegiado.tienePermisoLiberarEEFF(), false);
		} catch (Exception e) {
			respuestaG = new ResultBean();
			respuestaG.setError(true);
			respuestaG.addMensajeALista(e.getMessage());
			log.error("Error al guardar el tramite.", e);
		}

		// Recuperamos el tramite
		if (respuestaG.getAttachment(ServicioTramiteTraficoMatriculacion.NUMEXPEDIENTE) != null) {
			numExpediente = (BigDecimal) respuestaG.getAttachment(ServicioTramiteTraficoMatriculacion.NUMEXPEDIENTE);
			actualizarExpediente();
		}

		if (!respuestaG.getError()) {
			addActionMessage("Trámite guardado");
			if (respuestaG.getListaMensajes() != null && !respuestaG.getListaMensajes().isEmpty()) {
				for (String mensaje : respuestaG.getListaMensajes()) {
					if (!mensaje.contains("Tasa asignada al tramite con numero de expediente")) {
						addActionError(mensaje);
					}
				}
			}
		} else {
			log.error("Error al guardar la matriculación");
			if (respuestaG.getListaMensajes() != null && !respuestaG.getListaMensajes().isEmpty()) {
				addActionError("Error al guardar");
				for (String mensaje : respuestaG.getListaMensajes())
					addActionError(mensaje);
			}
		}

		if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getVehiculoDto() != null) {
			tramiteTrafMatrDto.getVehiculoDto().setRevisado(revisado);
		}

		if (tramiteTrafMatrDto.getCheckJustifFicheroIvtm() != null && tramiteTrafMatrDto.getCheckJustifFicheroIvtm()) {
			ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(tramiteTrafMatrDto.getNumExpediente().toString());
			if (!resultado.getError()) {
				setNombreFicheroJustifIvtm(ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + tramiteTrafMatrDto.getNumExpediente().toString() + ConstantesGestorFicheros.EXTENSION_PDF);
			}
		}

		return respuestaG;
	}

	private boolean validarEnGuardar() throws OegamExcepcion {
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_MATRICULACION_MATW)) {
			addActionError("No tiene permiso de mantenimiento de matriculacion");
			return true;
		}

		if (tramiteTrafMatrDto.getNumExpediente() != null && !tramiteTrafMatrDto.getNumColegiado().equals(utilesColegiado.getNumColegiadoSession()) && !utilesColegiado.tienePermisoAdmin()) {
			addActionError("No tiene permiso para realizar una modificación en el trámite.");
			return true;
		}

		return false;
	}

	public String cargarExpediente() throws OegamExcepcion {
		if (numExpediente != null) {
			tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(numExpediente, utilesColegiado.tienePermisoLiberarEEFF(), true);
			if (tramiteTrafMatrDto == null) {
				addActionError("No se ha podido recuperar el trámite.");
			} else {
				if (utilesColegiado.tienePermisoAdmin() && tramiteTrafMatrDto.getNumColegiado() != null && !tramiteTrafMatrDto.getNumColegiado().isEmpty() && tramiteTrafMatrDto
						.getIdContrato() != null) {
					presentador = servicioPersona.obtenerColegiadoCompleto(tramiteTrafMatrDto.getNumColegiado(), tramiteTrafMatrDto.getIdContrato());
				}
				ivtmMatriculacionDto = servicioIvtmMatriculacion.getIvtmPorExpedienteDto(numExpediente);
			}
			datosFacturacion();
			if (tramiteTrafMatrDto.getCheckJustifFicheroIvtm() != null && tramiteTrafMatrDto.getCheckJustifFicheroIvtm()) {
				ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(tramiteTrafMatrDto.getNumExpediente().toString());
				if (!resultado.getError()) {
					setNombreFicheroJustifIvtm(ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + tramiteTrafMatrDto.getNumExpediente().toString() + ConstantesGestorFicheros.EXTENSION_PDF);
				}
			}

			docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(numExpediente);

			propTexto = ConstantesMensajes.MENSAJE_TRAMITESRESPONSABILIDAD;
		}
		return SUCCESS;
	}

	private void actualizarExpediente() throws OegamExcepcion {
		tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(tramiteTrafMatrDto.getNumExpediente(), utilesColegiado.tienePermisoLiberarEEFF(), true);
		if (utilesColegiado.tienePermisoAdmin() && tramiteTrafMatrDto.getNumColegiado() != null && !tramiteTrafMatrDto.getNumColegiado().isEmpty() && tramiteTrafMatrDto.getIdContrato() != null) {
			presentador = servicioPersona.obtenerColegiadoCompleto(tramiteTrafMatrDto.getNumColegiado(), tramiteTrafMatrDto.getIdContrato());
		}
		ivtmMatriculacionDto = servicioIvtmMatriculacion.getIvtmPorExpedienteDto(tramiteTrafMatrDto.getNumExpediente());
		datosFacturacion();
	}

	public String validar() throws OegamExcepcion {
		ResultBean respuestaV = null;
		ResultBean respuestaG;
		try {
			respuestaG = guardarTramite();
			clearMessages();
		} catch (Exception e) {
			respuestaG = new ResultBean();
			respuestaG.setError(true);
			respuestaG.addMensajeALista("Error al guardar el trámite.");
			log.error("Error al guardar el trámite", e);
		}
		if (!respuestaG.getError()) {
			actualizarExpediente();
			try {
				respuestaV = servicioTramiteTraficoMatriculacion.validarTramite(tramiteTrafMatrDto);
			} catch (ParseException e1) {
				log.error("Error al validar los datos del tramite", e1);
			}
			if (respuestaV.getError()) {
				for (String mensaje : respuestaV.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				actualizarExpediente();
				try {
					if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTrafMatrDto.getEstado())) {
						servicioTramiteTrafico.custodiar(tramiteTrafMatrDto, utilesColegiado.getAlias(tramiteTrafMatrDto.getNumColegiado()));
						addActionMessage("Trámite Validado PDF.");
					} else {
						if(EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio.getValorEnum().equals(tramiteTrafMatrDto.getEstado())){
							addActionMessage("Trámite Validado Telemáticamente Pendiente Autorización.");
						}else if(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafMatrDto.getEstado())) {
							addActionMessage("Trámite Validado Telemáticamente.");
						}
					}
				} catch (UnsupportedEncodingException e) {
					log.error("Error al guardar el archivo de custodia", e);
				} catch (InternalException e) {
					log.error("Error al guardar el archivo de custodia", e);
				}
			}
		} else {
			for (String mensaje : respuestaG.getListaMensajes()) {
				addActionError(mensaje);
			}
		}
		return SUCCESS;
	}

	// Consultar persona
	public String consultarPersona() {
		if (nifBusqueda != null && !nifBusqueda.equals("")) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nifBusqueda.toUpperCase(), tramiteTrafMatrDto.getNumColegiado());
			if (interviniente == null) {
				addActionMessage("No existen datos para ese NIF.");
				interviniente = new IntervinienteTraficoDto();
				PersonaDto persona = new PersonaDto();
				persona.setNif(nifBusqueda);
				interviniente.setPersona(persona);
			} else {
				if (tipoIntervinienteBuscar != null && TipoInterviniente.Titular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTrafMatrDto.setTitular(interviniente);
				}
				if (tipoIntervinienteBuscar != null && TipoInterviniente.RepresentanteTitular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTrafMatrDto.setRepresentanteTitular(interviniente);
				} else if (tipoIntervinienteBuscar != null && TipoInterviniente.Arrendatario.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					if (tramiteTrafMatrDto.getArrendatario() != null) {
						interviniente.setFechaInicio(tramiteTrafMatrDto.getArrendatario().getFechaInicio());
						interviniente.setFechaFin(tramiteTrafMatrDto.getArrendatario().getFechaFin());
						interviniente.setHoraInicio(tramiteTrafMatrDto.getArrendatario().getHoraInicio());
						interviniente.setHoraFin(tramiteTrafMatrDto.getArrendatario().getHoraFin());
					}
					tramiteTrafMatrDto.setArrendatario(interviniente);
				} else if (tipoIntervinienteBuscar != null && TipoInterviniente.RepresentanteArrendatario.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTrafMatrDto.setRepresentanteArrendatario(interviniente);
				} else if (tipoIntervinienteBuscar != null && TipoInterviniente.ConductorHabitual.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTrafMatrDto.setConductorHabitual(interviniente);
				} else if (tipoIntervinienteBuscar != null && TIPO_INTERVINIENTE_FACTURACION.equals(tipoIntervinienteBuscar)) {
					if (datosFacturacion == null) {
						datosFacturacion = new TramiteTrafFacturacionDto();
					}
					datosFacturacion.setPersona(interviniente.getPersona());
					datosFacturacion.setDireccion(interviniente.getDireccion());
				}
			}
		} else {
			addActionError("Se debe de rellenar el NIF");
		}
		return SUCCESS;
	}

	public String consultaDatosITV() throws Throwable {
		ResultBean result = servicioVehiculo.consultaDatosItv(tramiteTrafMatrDto.getVehiculoDto());
		if (!result.getError()) {
			VehiculoDto vehiculoDto = (VehiculoDto) result.getAttachment(ServicioVehiculo.VEHICULO);
			tramiteTrafMatrDto.setVehiculoDto(vehiculoDto);
		}
		return SUCCESS;
	}

	public String guardarTitularFacturacion() {
		if (!utilizarTitular && (datosFacturacion == null || datosFacturacion.getNif() == null || datosFacturacion.getNif().isEmpty())) {
			addActionError("Error debe de estar los datos rellenos");
			return SUCCESS;
		}
		if (datosFacturacion.getCodTasa() == null || datosFacturacion.getCodTasa().isEmpty()) {
			addActionError("No existe Tasa");
			return SUCCESS;
		}
		if (datosFacturacion.getGenerado() == null) {
			datosFacturacion.setGenerado(false);
		}
		if (utilizarTitular) {
			datosFacturacion.setPersona(tramiteTrafMatrDto.getTitular().getPersona());
			datosFacturacion.setNif(tramiteTrafMatrDto.getTitular().getPersona().getNif());
			datosFacturacion.setDireccion(tramiteTrafMatrDto.getTitular().getDireccion());
		}
		try {
			datosFacturacion.setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
			datosFacturacion.setIdContrato(tramiteTrafMatrDto.getIdContrato().longValue());
			ResultBean resultado = servicioFacturacion.guardar(datosFacturacion, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilizarTitular);
			if (resultado.getError()) {
				addActionError("Error al guardar los datos de facturación");
				log.error(resultado.getMensaje());
				return SUCCESS;
			} else {
				addActionMessage("Datos de Facturación guardados.");
			}
		} catch (Exception e) {
			addActionError("Error al guardar los datos de facturación");
			log.error("Error al guardar los datos de facturación", e);
		}

		return SUCCESS;
	}

	private void datosFacturacion() {
		if (tramiteTrafMatrDto != null && (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafMatrDto.getEstado())
				|| EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafMatrDto.getEstado()) || EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(
						tramiteTrafMatrDto.getEstado()))) {
			datosFacturacion = new TramiteTrafFacturacionDto();
			if (tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getNif() != null && !tramiteTrafMatrDto.getTramiteFacturacion().getNif().isEmpty()) {
				datosFacturacion.setNif(tramiteTrafMatrDto.getTramiteFacturacion().getNif());
			}
			if (tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getCodTasa() != null && !tramiteTrafMatrDto.getTramiteFacturacion().getCodTasa()
					.isEmpty()) {
				datosFacturacion.setCodTasa(tramiteTrafMatrDto.getTramiteFacturacion().getCodTasa());
			} else if (tramiteTrafMatrDto.getTasa() != null && tramiteTrafMatrDto.getTasa().getCodigoTasa() != null && !tramiteTrafMatrDto.getTasa().getCodigoTasa().isEmpty()) {
				datosFacturacion.setCodTasa(tramiteTrafMatrDto.getTasa().getCodigoTasa());
			}
			if (tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getBastidor() != null && !tramiteTrafMatrDto.getTramiteFacturacion().getBastidor()
					.isEmpty()) {
				datosFacturacion.setBastidor(tramiteTrafMatrDto.getTramiteFacturacion().getBastidor());
			} else if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getBastidor() != null && !tramiteTrafMatrDto.getVehiculoDto().getBastidor().isEmpty()) {
				datosFacturacion.setBastidor(tramiteTrafMatrDto.getVehiculoDto().getBastidor());
			}
			if (tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getDireccion() != null) {
				datosFacturacion.setDireccion(tramiteTrafMatrDto.getTramiteFacturacion().getDireccion());
			}
			if (tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getMatricula() != null && !tramiteTrafMatrDto.getTramiteFacturacion().getMatricula()
					.isEmpty()) {
				datosFacturacion.setMatricula(tramiteTrafMatrDto.getTramiteFacturacion().getMatricula());
			} else if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getMatricula() != null && !tramiteTrafMatrDto.getVehiculoDto().getMatricula().isEmpty()) {
				datosFacturacion.setMatricula(tramiteTrafMatrDto.getVehiculoDto().getMatricula());
			}
			if (tramiteTrafMatrDto.getNumExpediente() != null) {
				datosFacturacion.setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			}
			if (tramiteTrafMatrDto.getTramiteFacturacion() != null && tramiteTrafMatrDto.getTramiteFacturacion().getPersona() != null) {
				datosFacturacion.setPersona(tramiteTrafMatrDto.getTramiteFacturacion().getPersona());
			}
			if (tramiteTrafMatrDto.getTipoTramite() != null && !tramiteTrafMatrDto.getTipoTramite().isEmpty()) {
				datosFacturacion.setTipoTramite(tramiteTrafMatrDto.getTipoTramite());
			}
		}
	}

	public String presentarAEAT() {
		ResultBean resultadoPresentacion = new ResultBean();
		byte[] respuestaHtml = null;
		try {
			// Comprobación para saber si ya se ha tramitado correctamente la presentación del modelo 576 para el trámite desde el que se llama a esta Action
			if (existePresentacionPrevia576Correcta()) {
				addActionError("Ya se ha obtenido una respuesta correcta para la Presentación del Modelo 576 para este trámite");
				return SUCCESS;
			}
			// Comprobación para saber si existe una solicitud en cola de presentación del modelo 576 para el trámite trámite desde el que se llama a esta Action
			if (exiteSolicitud576enCola()) {
				addActionError("Existe un solicitud en cola para la Presentación del Modelo 576 para este trámite, espere a que finalice para saber el resultado de la presentación");
				return SUCCESS;
			}
			resultadoPresentacion = guardarTramite();
			if (resultadoPresentacion.getError()) {
				addActionError("Trámite no guardado.");
				for (int i = 0; i < resultadoPresentacion.getListaMensajes().size(); i++) {
					addActionError(resultadoPresentacion.getListaMensajes().get(i));
				}
				return SUCCESS;
			}

			/*
			 * String numExpediente = tramiteTrafMatrDto.getNumExpediente().toString();
			 * String nifPresentador = "Q2861007I"; String nombrePresentador =
			 * "COL OF DE GESTORES ADMINISTRATIVOS MADRID"; String F01 = "T576020230A0000";
			 * String cuotaIngresar = "100"; String nrc576 = "NA"; String[] args =
			 * {F01,nifPresentador,nombrePresentador,nrc576,cuotaIngresar,numExpediente};
			 * new FormularioOegam576(args);
			 */

			String alias = "";
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumColegiado() != null && !tramiteTrafMatrDto.getNumColegiado().isEmpty()) {
				alias = utilesColegiado.getAlias(tramiteTrafMatrDto.getNumColegiado());
			} else {
				alias = utilesColegiado.getAlias();
			}

			ResultBean resultsTramitacion = servicioPresentacion576.tramitar576(tramiteTrafMatrDto, alias);
			// Si tenemos errores en la presentación
			if (resultsTramitacion != null && resultsTramitacion.getError()) {
				if (resultsTramitacion.getListaMensajes() != null) {
					for (String mensaje : resultsTramitacion.getListaMensajes()) {
						addActionError(mensaje);
					}
				}

				if (tramiteTrafMatrDto.getRespuesta576() != null && !tramiteTrafMatrDto.getRespuesta576().isEmpty()) {
					tramiteTrafMatrDto.setRespuesta("Error al presentar en la AEAT.");
					clearMessages();
					guardarTramite();
				}

				return SUCCESS;
			// Si no hay errores en la presentación
			} else {
				addActionMessage("La presentación en la AEAT se ha realizado correctamente.");
				tramiteTrafMatrDto.setRespuesta("La presentacion del 576 en la AEAT del expediente: " + tramiteTrafMatrDto.getNumExpediente() + " se ha realizado correctamente.");
				if (null != resultsTramitacion.getAttachment(ConstantesSession.RESPUESTA576)) {
					respuestaHtml = (byte[]) resultsTramitacion.getAttachment(ConstantesSession.RESPUESTA576);
					guardarTramite();
					addActionMessage("Se ha actualizado el trámite con el CEM recibido");
				}
			}
			if (respuestaHtml != null) {
				pdf576 = true;
				ficheroDeclaracion576 = false;
				getSession().put(ConstantesSession.FICHERO_RESPUESTA, respuestaHtml);
			}
		} catch (Throwable ex) {
			log.error("Error presentacion AEAT", ex);
			addActionError(ex.toString());
		}
		return SUCCESS;
	}

	private boolean existePresentacionPrevia576Correcta() {
		if (StringUtils.isBlank(tramiteTrafMatrDto.getRespuesta576())) {
			return false;
		}
		return tramiteTrafMatrDto.getRespuesta576().toLowerCase().contains("correcta");
	}

	private boolean exiteSolicitud576enCola() {
		return Objects.nonNull(servicioCola.getColaIdTramite(tramiteTrafMatrDto.getNumExpediente(), ConstantesProcesos.PROCESO_576));
	}

	public String recuperar576() {
		try {
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumExpediente() != null) {
				ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerFicheroPresentacion576(tramiteTrafMatrDto.getNumExpediente().toString());
				if (!resultado.getError()) {
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = resultado.getNombreFichero();
					return DESCARGAR_PRESENTACION_576;
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				addActionError("No se puede descargar el fichero de presentacion del 576 al estar el numero de expediente vacio.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar el fichero para la presentación del 576, error: ", e, tramiteTrafMatrDto.getNumExpediente().toString());
			addActionError("Ha sucedido un error a la hora de recuperar el fichero para la presentación del 576.");
		}
		return SUCCESS;
	}

	public String exportarRespuesta576() {
		log.debug("Inicio: Obtiene Respuesta de 576");
		byte[] paraImprimir = (byte[]) getSession().get(ConstantesSession.FICHERO_RESPUESTA);

		if (null == paraImprimir) {
			addActionError("Se ha producido un error al obtener la Respuesta Generada.");
			return SUCCESS;
		}

		ByteArrayInputStream stream = new ByteArrayInputStream(paraImprimir);

		setInputStream(stream);

		String nombreFichero = ConstantesAEAT.NOMBRE_FICHERO_RESPUESTA;

		setPdf576(false);

		setFileName(nombreFichero + ConstantesPDF.EXTENSION_PDF);

		log.debug("Exportando el fichero de 576");
		getSession().remove(ConstantesSession.FICHERO_RESPUESTA);
		return DESCARGAR_PRESENTACION_576;
	}

	public String getDataEitv() throws OegamExcepcion {
		try {
			ResultBean resultado = servicioConsultaEitv.consultaEitv(tramiteTrafMatrDto.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal());
			if (resultado != null && resultado.getError()) {
				for (String mensaje : resultado.getListaMensajes()) {
					addActionError(mensaje);
				}
			} else {
				actualizarExpediente();
				addActionMessage("Solicitud de Consulta de Tarjeta EITV realizada con éxito.");
			}
		} catch (Exception e) {
			log.error("Ha surgido un error a la hora de crear la solicutd de consulta de tarjeta EITV, error: " + e);
			addActionError("Ha ocurrido un error interno tramitando la solicitud");
		}
		return SUCCESS;
	}

	public String liberarTramite() throws OegamExcepcion {
		log.debug("Ha entrado al método liberarTramite ");
		if (!utilesColegiado.tienePermisoLiberarEEFF()) {
			addActionError(ConstantesEEFF.EEFF_TEXTO_NO_PERMISO);
			log.debug("El colegiado " + utilesColegiado.getNumColegiadoSession() + " ha intentado liberar un trámite y no tiene permisos");
			return SUCCESS;
		}
		log.debug("El colegiado " + utilesColegiado.getNumColegiadoSession() + " ha intentado liberar un trámite");
		if (tramiteTrafMatrDto == null || tramiteTrafMatrDto.getNumExpediente() == null) {
			addActionError("El trámite no tiene numéro de expediente");
			log.debug("El colegiado " + utilesColegiado.getNumColegiadoSession() + " ha intentado liberar un trámite que no tiene número de expediente");
			return ERROR;
		}

		ResultBean resultado = servicioTramiteTraficoMatriculacion.liberarTramiteMatriculacion(tramiteTrafMatrDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			aniadirMensajeError(resultado);
		} else {
			aniadirMensajeAviso(resultado, null);
			actualizarExpediente();
		}

		return SUCCESS;
	}

	public String actualizarMatManual() {
		ResultBean resultBean = new ResultBean();
		HttpServletRequest request = ServletActionContext.getRequest();

		String numExpediente = request.getParameter("numExpediente");
		String matricula = request.getParameter("matricula");
		String fechaMatriculacion = request.getParameter("fechaMatriculacion");

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateMatriculacion = null;
		try {
			dateMatriculacion = formatter.parse(fechaMatriculacion);
			resultBean = servicioVehiculo.actualizarMatricula(new BigDecimal(numExpediente), matricula, dateMatriculacion, servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal()));
		} catch (ParseException e) {
			resultBean.addError("Error en el formato de los datos de entrada");
			resultBean.setError(true);
		}

		if (resultBean != null && resultBean.getError()) {
			addActionError(resultBean.getMensaje());
		} else {
			addActionMessage("Se ha actualizado la matrícula.");
		}
		return SUCCESS;
	}

	public String abrirPopMatriculacion() {
		return "popUpMatriculaManual";
	}

	public String autoliquidarIVTM() throws OegamExcepcion {
		ResultBean rs = new ResultBean();

		if (!utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return SUCCESS;
		}

		if (!UtilesVistaMatriculacion.getInstance().esConsultableOGuardableMatriculacion(tramiteTrafMatrDto)) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return SUCCESS;
		}

		if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumExpediente() != null) {
			rs = servicioIvtmMatriculacion.puedeGenerarAutoliquidacion(tramiteTrafMatrDto.getNumExpediente());
			if (rs.getError()) {
				for (String error : rs.getListaMensajes()) {
					addActionError(error);
				}
				return SUCCESS;
			}
		}
		// Ajustamos la tara
		ajustarTara(tramiteTrafMatrDto.getVehiculoDto());
		// Guardamos el trámite de tráfico
		try {
			rs = guardarTramite();
		} catch (Throwable e1) {
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("Error al guardar el trámite.");
			log.error("Error al guardar el trámite:", e1);
		}
		if (rs.getError()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			for (String error : rs.getListaMensajes()) {
				addActionError(error);
			}
			return SUCCESS;
		} else if (tramiteTrafMatrDto == null || tramiteTrafMatrDto.getNumExpediente() == null) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			return SUCCESS;
		}
		if (ivtmMatriculacionDto == null) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_LUQIDACION);
		} else {
			rs = servicioIvtmMatriculacion.validarTramite(tramiteTrafMatrDto.getNumExpediente());
			if (rs.getError()) {
				ivtmMatriculacionDto.setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
				ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
				ivtmMatriculacionDto.setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
				ivtmMatriculacionDto.setFechaReq(new Fecha(new SimpleDateFormat(ConstantesIVTM.FORMATO_FECHA_GUARDAR_IVTM).format(new Date())));
				ivtmMatriculacionDto.setBastidor(tramiteTrafMatrDto.getVehiculoDto().getBastidor());

				try {
					rs = servicioIvtmMatriculacion.guardarIvtm(ivtmMatriculacionDto);
					if (rs != null && rs.getError()) {
						addActionError(ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
					}
				} catch (Exception e) {
					addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDAR_IVTM);
				}
				for (String error : rs.getListaMensajes()) {
					addActionError(error);
				}
				addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_LUQIDACION);
			} else {
				ivtmMatriculacionDto.setFechaReq(new Fecha(new SimpleDateFormat(ConstantesIVTM.FORMATO_FECHA_GUARDAR_IVTM).format(new Date())));
				ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Pendiente_Respuesta_Ayto.getValorEnum()));
				ivtmMatriculacionDto.setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
				if (tramiteTrafMatrDto.getNumColegiado() != null && !tramiteTrafMatrDto.getNumColegiado().isEmpty()) {
					ivtmMatriculacionDto.setIdPeticion(servicioIvtmMatriculacion.generarIdPeticion(tramiteTrafMatrDto.getNumColegiado()));
				} else {
					ivtmMatriculacionDto.setIdPeticion(servicioIvtmMatriculacion.generarIdPeticion(utilesColegiado.getNumColegiadoSession()));
				}
				ivtmMatriculacionDto.setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
				ivtmMatriculacionDto.setBastidor(tramiteTrafMatrDto.getVehiculoDto().getBastidor());

				rs = servicioIvtmMatriculacion.guardarIvtm(ivtmMatriculacionDto);
				try {
					if (rs != null && rs.getError()) {
						addActionError(gestorPropiedades.valorPropertie(ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO));
					} else {
						addActionMessage(ConstantesIVTM.TEXTO_IVTM_VALIDACION_CORRECTA);
						rs = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTrafMatrDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteTrafMatrDto.getEstado()),
								EstadoTramiteTrafico.Pendiente_Respuesta_IVTM, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
						if (rs != null && !rs.getError()) {
							tramiteTrafMatrDto.setEstado(EstadoTramiteTrafico.Pendiente_Respuesta_IVTM.getValorEnum());
							try {
								rs = servicioCola.crearSolicitud(ConstantesProcesos.PROCESO_IVTM, ConstantesIVTM.TIPO_ALTA_IVTM_WS, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
										tramiteTrafMatrDto.getTipoTramite(), tramiteTrafMatrDto.getNumExpediente().toString(), utilesColegiado.getIdUsuarioSessionBigDecimal(), null, tramiteTrafMatrDto
												.getIdContrato());
								if (rs != null && !rs.getError()) {
									addActionMessage(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_CORRECTO);
								} else {
									addActionError(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
									servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTrafMatrDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteTrafMatrDto.getEstado()),
											EstadoTramiteTrafico.Iniciado, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
									tramiteTrafMatrDto.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
									ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
									rs = servicioIvtmMatriculacion.guardarIvtm(ivtmMatriculacionDto);
									if (rs != null && rs.getError()) {
										addActionError(ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
									}
								}
							} catch (Exception e) {
								addActionError(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
								servicioTramiteTrafico.cambiarEstadoConEvolucion(tramiteTrafMatrDto.getNumExpediente(), EstadoTramiteTrafico.convertir(tramiteTrafMatrDto.getEstado()),
										EstadoTramiteTrafico.Iniciado, true, TextoNotificacion.Cambio_Estado.getNombreEnum(), utilesColegiado.getIdUsuarioSessionBigDecimal());
								tramiteTrafMatrDto.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
								ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Iniciado.getValorEnum()));
								rs = servicioIvtmMatriculacion.guardarIvtm(ivtmMatriculacionDto);
								if (rs != null && rs.getError()) {
									addActionError(ConstantesIVTM.TEXTO_IVTM_IVTM_NO_GUARDADO);
								}
							}
						}
					}
				} catch (Exception e) {
					addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDAR_IVTM);
					addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_GUARDADO_NO_ALTA_IVTM);
				}
			}
		}

		return SUCCESS;
	}

	private void ajustarTara(VehiculoDto vehiculo) {
		if (vehiculo != null && (vehiculo.getTara() == null || vehiculo.getTara().isEmpty()) && vehiculo.getMom() != null && !vehiculo.getMom().equals("")) {
			vehiculo.setTara(vehiculo.getMom().subtract(new BigDecimal(75)).toString());
		}
	}

	public String pagoIVTM() throws OegamExcepcion, ParseException {
		ResultBean rs = new ResultBean();

		if (!utilesColegiado.tienePermisoAutoliquidarIvtm()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return SUCCESS;
		}

		if (!UtilesVistaMatriculacion.getInstance().esConsultableOGuardableMatriculacion(tramiteTrafMatrDto)) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_NO_PERMISO_AUTOLIQUIDACION);
			return SUCCESS;
		}

		try {
			rs = guardarTramite();
		} catch (Throwable e1) {
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("Existen errores al guardar");
		}
		if (rs.getError()) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			for (String error : rs.getListaMensajes()) {
				addActionError(error);
			}
			return SUCCESS;
		} else if (tramiteTrafMatrDto == null || tramiteTrafMatrDto.getNumExpediente() == null) {
			addActionError(ConstantesIVTM.TEXTO_IVTM_TRAMITE_NO_GUARDADO);
			return SUCCESS;
		}

		if (ivtmMatriculacionDto == null) {
			addActionError(gestorPropiedades.valorPropertie(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_PAGO));
		} else {
			rs = servicioIvtmMatriculacion.validarTramitePago(tramiteTrafMatrDto.getNumExpediente());
		}
		if (rs.getError()) {
			for (String error : rs.getListaMensajes()) {
				addActionError(error);
			}
			addActionError(ConstantesIVTM.TEXTO_IVTM_ERROR_NO_PAGO);
			return SUCCESS;
		} else {
			url = servicioIvtmMatriculacion.crearUrl(false, ivtmMatriculacionDto, tramiteTrafMatrDto);
		}

		return PAGO_IVTM;
	}

	public String clonar() {
		try {
			esAccionClonado = true;
			numsExpediente = tramiteTrafMatrDto.getNumExpediente().toString();
			tipoTramiteSeleccionado = TipoTramiteTrafico.Matriculacion.getValorEnum();
			estadoTramiteSeleccionado = tramiteTrafMatrDto.getEstado();
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getBastidor() != null) {
				bastidorSeleccionado = tramiteTrafMatrDto.getVehiculoDto().getBastidor();
			}
		} catch (Throwable e) {
			addActionError("Este trámite no se ha podido clonar, porque ha sucedido un error interno.");
			log.error("Trámite no guardado. error: " + e.getMessage(), e);
			return SUCCESS;
		}
		return CLONAR_TRAMITE;
	}

	public String matricularTelematicamente() throws Throwable {
		if (tramiteTrafMatrDto.getNumExpediente() == null) {
			addActionError("No puede estar vacio el numero de expediente");
			return SUCCESS;
		}

		ResultBean resultBean = servicioTramiteTraficoMatriculacion.tramitacion(tramiteTrafMatrDto.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getAlias(),
				utilesColegiado.getColegioDelContrato(), utilesColegiado.tienePermisoLiberarEEFF(), utilesColegiado.getIdContratoSessionBigDecimal());

		if (!resultBean.getError()) {
			addActionMessage(resultBean.getMensaje());
			actualizarExpediente();
		} else {
			addActionError("Error en la tramitación telemática: ");
			if (resultBean.getListaMensajes() != null && !resultBean.getListaMensajes().isEmpty()) {
				for (String mensaje : resultBean.getListaMensajes()) {
					addActionError(mensaje);
				}
			}
		}
		return SUCCESS;
	}

	public String descargarDocBase() throws OegamExcepcion {
		try {
			if (docBaseCarpetaTramiteBean == null) {
				docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(tramiteTrafMatrDto.getNumExpediente());
			}
			if (docBaseCarpetaTramiteBean != null) {
				ResultadoDocBaseBean resultado = servicioGestionDocBase.descargarDocBase(docBaseCarpetaTramiteBean.getId().toString());
				if (resultado != null && !resultado.getError()) {
					inputStream = new FileInputStream((File) resultado.getFichero());
					if (inputStream == null) {
						addActionError("No se puede recuperar el documento base para el trámite: '" + tramiteTrafMatrDto.getNumExpediente() + "'");
						actualizarExpediente();
						return SUCCESS;
					}
					fileName = (String) resultado.getNombreFichero();
				} else {
					addActionError("No se puede recuperar el documento base para el trámite: '" + tramiteTrafMatrDto.getNumExpediente() + "'");
					return SUCCESS;
				}
				return DESCARGAR_DOCUMENTO_BASE;
			}
		} catch (Exception e) {
			addActionError("No se puede recuperar el documento base para el trámite: '" + tramiteTrafMatrDto.getNumExpediente() + "'");
			log.error(e.getMessage());
		}
		return SUCCESS;
	}

	public String verJustificanteIVTM() {
		Boolean error = Boolean.FALSE;
		try {
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumExpediente() != null) {
				TramiteTrafMatrDto trafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(tramiteTrafMatrDto.getNumExpediente(), Boolean.FALSE, Boolean.FALSE);
				trafMatrDto.setEstado(trafMatrDto.getEstado());
				trafMatrDto.setCheckJustifFicheroIvtm(trafMatrDto.getCheckJustifFicheroIvtm());
				ResultadoMatriculacionBean resultado = servicioTramiteTraficoMatriculacion.obtenerJustificanteIVTM(tramiteTrafMatrDto.getNumExpediente().toString());
				if (!resultado.getError()) {
					inputStream = new FileInputStream((File) resultado.getFichero());
					fileName = ConstantesGestorFicheros.NOMBRE_JUSTIFICANTE_IVTM + trafMatrDto.getNumExpediente() + ConstantesGestorFicheros.EXTENSION_PDF;
					return "descargarJustificanteIVTM";
				} else {
					addActionError(resultado.getMensaje());
					error = Boolean.TRUE;
				}
			} else {
				addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");
				error = Boolean.TRUE;
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM, error: ", e);
			addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");

		}
		if (error) {
			if (tramiteTrafMatrDto.getEstado() != null && EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafMatrDto.getEstado())) {
				addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");
				error = Boolean.TRUE;
			} else {
				addActionError("No se puede consultar el justificante del pago IVTM sin tener guardado un trámite con un número de expediente asociado.");
				error = Boolean.TRUE;
			}
		}
		return SUCCESS;
	}

	public String subirJustificanteIVTM() {
		ResultadoMatriculacionBean resultado = new ResultadoMatriculacionBean(false);
		try {
			if (tramiteTrafMatrDto != null && tramiteTrafMatrDto.getNumExpediente() != null) {
				if (fileJustifIvtm != null) {
					String extension = utiles.dameExtension(fileJustifIvtmFileName, false);
					if (extension == null || !"pdf".equals(extension)) {
						addActionError("El fichero añadido debe tener un formato soportado: .pdf");
						return SUCCESS;
					}
					resultado = servicioTramiteTraficoMatriculacion.subirJustificanteIVTM(getFileJustifIvtm(), tramiteTrafMatrDto.getNumExpediente().toString());
				} else {
					addActionError("Introduzca un fichero para subir");
					return SUCCESS;
				}
				if (!resultado.getError()) {
					tramiteTrafMatrDto.setCheckJustifFicheroIvtm(true);
					setNombreFicheroJustifIvtm(resultado.getFichero().getName());
					addActionMessage("Se ha subido correctamente el justificante de pago IVTM");
				} else {
					addActionError(resultado.getMensaje());
				}
			} else {
				addActionError("No se puede subir el fichero de justificante de pago IVTM, al estar el numero de expediente vacio.");
			}

		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM, error: ", e);
			addActionError("Ha sucedido un error a la hora de recuperar el fichero justificante del pago IVTM.");

		}
		return SUCCESS;
	}

	// GETTERS AND SETTERS
	public TramiteTrafMatrDto getTramiteTrafMatrDto() {
		return tramiteTrafMatrDto;
	}

	public void setTramiteTrafMatrDto(TramiteTrafMatrDto tramiteTrafMatrDto) {
		this.tramiteTrafMatrDto = tramiteTrafMatrDto;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public TramiteTrafFacturacionDto getDatosFacturacion() {
		return datosFacturacion;
	}

	public void setDatosFacturacion(TramiteTrafFacturacionDto datosFacturacion) {
		this.datosFacturacion = datosFacturacion;
	}

	public boolean isUtilizarTitular() {
		return utilizarTitular;
	}

	public void setUtilizarTitular(boolean utilizarTitular) {
		this.utilizarTitular = utilizarTitular;
	}

	public boolean isMarcadoConductorHabitual() {
		return marcadoConductorHabitual;
	}

	public void setMarcadoConductorHabitual(boolean marcadoConductorHabitual) {
		this.marcadoConductorHabitual = marcadoConductorHabitual;
	}

	public String getAsignarPrincipalTitular() {
		return asignarPrincipalTitular;
	}

	public void setAsignarPrincipalTitular(String asignarPrincipalTitular) {
		this.asignarPrincipalTitular = asignarPrincipalTitular;
	}

	public String getAsignarPrincipalRepresentanteTitular() {
		return asignarPrincipalRepresentanteTitular;
	}

	public void setAsignarPrincipalRepresentanteTitular(String asignarPrincipalRepresentanteTitular) {
		this.asignarPrincipalRepresentanteTitular = asignarPrincipalRepresentanteTitular;
	}

	public String getAsignarPrincipalArrendatario() {
		return asignarPrincipalArrendatario;
	}

	public void setAsignarPrincipalArrendatario(String asignarPrincipalArrendatario) {
		this.asignarPrincipalArrendatario = asignarPrincipalArrendatario;
	}

	public String getAsignarPrincipalConductorHabitual() {
		return asignarPrincipalConductorHabitual;
	}

	public void setAsignarPrincipalConductorHabitual(String asignarPrincipalConductorHabitual) {
		this.asignarPrincipalConductorHabitual = asignarPrincipalConductorHabitual;
	}

	public PersonaDto getPresentador() {
		return presentador;
	}

	public void setPresentador(PersonaDto presentador) {
		this.presentador = presentador;
	}

	public IvtmMatriculacionDto getIvtmMatriculacionDto() {
		return ivtmMatriculacionDto;
	}

	public void setIvtmMatriculacionDto(IvtmMatriculacionDto ivtmMatriculacionDto) {
		this.ivtmMatriculacionDto = ivtmMatriculacionDto;
	}

	public Boolean getFicheroDeclaracion576() {
		return ficheroDeclaracion576;
	}

	public void setFicheroDeclaracion576(Boolean ficheroDeclaracion576) {
		this.ficheroDeclaracion576 = ficheroDeclaracion576;
	}

	public Boolean getPdf576() {
		return pdf576;
	}

	public void setPdf576(Boolean pdf576) {
		this.pdf576 = pdf576;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public String getTipoTramiteSeleccionado() {
		return tipoTramiteSeleccionado;
	}

	public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
		this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
	}

	public String getEstadoTramiteSeleccionado() {
		return estadoTramiteSeleccionado;
	}

	public void setEstadoTramiteSeleccionado(String estadoTramiteSeleccionado) {
		this.estadoTramiteSeleccionado = estadoTramiteSeleccionado;
	}

	public String getBastidorSeleccionado() {
		return bastidorSeleccionado;
	}

	public void setBastidorSeleccionado(String bastidorSeleccionado) {
		this.bastidorSeleccionado = bastidorSeleccionado;
	}

	public boolean isEsAccionClonado() {
		return esAccionClonado;
	}

	public void setEsAccionClonado(boolean esAccionClonado) {
		this.esAccionClonado = esAccionClonado;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPropTexto() {
		return propTexto;
	}

	public void setPropTexto(String propTexto) {
		this.propTexto = propTexto;
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

	public File getFileJustifIvtm() {
		return fileJustifIvtm;
	}

	public void setFileJustifIvtm(File fileJustifIvtm) {
		this.fileJustifIvtm = fileJustifIvtm;
	}

	public String getFileJustifIvtmFileName() {
		return fileJustifIvtmFileName;
	}

	public void setFileJustifIvtmFileName(String fileJustifIvtmFileName) {
		this.fileJustifIvtmFileName = fileJustifIvtmFileName;
	}

	public String getNombreFicheroJustifIvtm() {
		return nombreFicheroJustifIvtm;
	}

	public void setNombreFicheroJustifIvtm(String nombreFicheroJustifIvtm) {
		this.nombreFicheroJustifIvtm = nombreFicheroJustifIvtm;
	}

	public DocBaseCarpetaTramiteBean getDocBaseCarpetaTramiteBean() {
		return docBaseCarpetaTramiteBean;
	}

	public void setDocBaseCarpetaTramiteBean(DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean) {
		this.docBaseCarpetaTramiteBean = docBaseCarpetaTramiteBean;
	}
}