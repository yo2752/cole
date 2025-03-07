package org.gestoresmadrid.oegam2.controller.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamBajas.service.ServicioTramiteBaja;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.viafirma.cliente.exception.InternalException;

import escrituras.beans.ResultBean;
import escrituras.beans.ResultValidarTramitesImprimir;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaTramiteTraficoBajaAction extends ActionBase {

	private static final long serialVersionUID = 4182486494616423686L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaTramiteTraficoBajaAction.class);

	private static final String IMPRIMIR_ACTION = "aImprimirAction";

	private ResultValidarTramitesImprimir resultBeanImprimir;

	private TramiteTrafBajaDto tramiteTraficoBaja;

	private String numsExpediente;

	@Autowired
	private ServicioTramiteTraficoBaja servicioTraficoBaja;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioFacturacion servicioFacturacion;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioTramiteBaja servicioTramiteBaja;

	@Autowired
	UtilesColegiado utilesColegiado;

	private BigDecimal numExpediente;

	private String nifBusqueda;

	private String tipoIntervinienteBuscar;

	private String matriculaBusqueda;

	private TramiteTrafFacturacionDto datosFacturacion;

	private boolean utilizarTitular;

	private String TIPO_INTERVINIENTE_FACTURACION = "Facturacion";
	
	private boolean volverAntiguaConsulta;

	public String inicio() {
		if (tramiteTraficoBaja == null) {
			tramiteTraficoBaja = new TramiteTrafBajaDto();
		}
		if (tramiteTraficoBaja.getJefaturaTraficoDto() == null) {
			tramiteTraficoBaja.setJefaturaTraficoDto(new JefaturaTraficoDto());
		}
		tramiteTraficoBaja.getJefaturaTraficoDto().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		tramiteTraficoBaja.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		tramiteTraficoBaja.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		return SUCCESS;
	}

	public String guardar() {
		Boolean tienePermisoBtv = utilesColegiado.tienePermisoBTV();
		ResultBean respuestaG = null;
		if (!utilesColegiado.tienePermisoMantenimientoBajas()) {
			addActionError("No tiene permiso para realizar una baja.");
			return SUCCESS;
		}
		String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
		if("SI".equals(nuevasBajas)){
			ResultadoBajasBean resultado = servicioTramiteBaja.guardarTramite(tramiteTraficoBaja, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				if(StringUtils.isNotBlank(resultado.getMensaje())){
					addActionError(resultado.getMensaje());
				} else if(resultado.getListaMensajesError() != null && !resultado.getListaMensajesError().isEmpty()) {
					String mensaje = "El trámite de baja no se ha podido guardar por los siguientes motivos: ";
					for(String mensajeError : resultado.getListaMensajesError()){
						mensaje += "- " +mensajeError;
					}
					addActionError(mensaje);
				}
			} else {
				gestionarResultadoYObtenerTramite(resultado);
			}
		} else {
			if (tramiteTraficoBaja.getNumExpediente() != null && !tramiteTraficoBaja.getNumColegiado().equals(utilesColegiado.getNumColegiadoSession()) && !utilesColegiado.tienePermisoAdmin()) {
				addActionError("Tramite no guardado");
				addActionError("No tiene permiso para realizar una modificación en el trámite.");
				return SUCCESS;
			}

			if (tramiteTraficoBaja.getIdContrato() == null) {
				tramiteTraficoBaja.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			}
			// 190117 Mantis 0025205. Si el estado es "Finalizado excel con incidencia" lo guardamos en la tabla a "Iniciado"
			if (tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum())) {
				tramiteTraficoBaja.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
			}

			// 190117 Mantis 0025205. Se añade Finalizado_Excel_Incidencia para que permita modificar "Altas de Tramitación de Bajas"

			if (null != tramiteTraficoBaja.getEstado() && !tramiteTraficoBaja.getEstado().equals("") && !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Iniciado.getValorEnum())
					&& !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Validado_PDF.getValorEnum()) && !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Validado_Telematicamente
							.getValorEnum()) && !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Tramitable.getValorEnum()) && !tramiteTraficoBaja.getEstado().equals(
									EstadoTramiteTrafico.No_Tramitable.getValorEnum()) && !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum())
					&& !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum()) && !tramiteTraficoBaja.getEstado().equals(
							EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum()) && !tramiteTraficoBaja.getEstado().equals(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum())) {
				addActionError("No tiene permiso para realizar una modificación en el trámite.");
				return SUCCESS;
			}

			// Mantis 19262. David Sierra. Validación formato fechas de pantalla
			String validacionFecha = validarFechasBaja(tramiteTraficoBaja);
			if (!"OK".equals(validacionFecha)) {
				addActionError(validacionFecha);
				return SUCCESS;
			}

			try {
				if (tramiteTraficoBaja.getNumColegiado() == null || tramiteTraficoBaja.getNumColegiado().isEmpty()) {
					tramiteTraficoBaja.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				}
				tramiteTraficoBaja.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
				tramiteTraficoBaja.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
				respuestaG = servicioTraficoBaja.guardarTramite(tramiteTraficoBaja, utilesColegiado.getIdUsuarioSessionBigDecimal(), false, utilesColegiado.tienePermisoAdmin(), tienePermisoBtv);
			} catch (Exception e) {
				log.error("Error no controlado al finalizar la transaccion de guardado del tramite", e);
				respuestaG = new ResultBean();
				respuestaG.setError(true);
				respuestaG.addMensajeALista(e.getMessage());
			}

			// Recuperamos el trámite
			if (respuestaG.getAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE) != null) {
				numExpediente = (BigDecimal) respuestaG.getAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE);
				actualizarExpediente();
			}

			if (!respuestaG.getError()) {
				addActionMessage("Trámite guardado");
				if (respuestaG.getListaMensajes() != null && !respuestaG.getListaMensajes().isEmpty()) {
					for (String mensaje : respuestaG.getListaMensajes())
						addActionError(mensaje);
				}
			} else {
				log.error("Error al guardar la baja");
				if (respuestaG.getListaMensajes() != null && !respuestaG.getListaMensajes().isEmpty()) {
					addActionError("Error al guardar");
					for (String mensaje : respuestaG.getListaMensajes())
						addActionError(mensaje);
				}
			}

			if (tramiteTraficoBaja != null && tramiteTraficoBaja.getTasa() != null && !"-1".equals(tramiteTraficoBaja.getTasa().getCodigoTasa())) {
				tramiteTraficoBaja.getTasa().setTipoTasa("4.1");
			}

			if ((tramiteTraficoBaja != null && tramiteTraficoBaja.getEstado() != null && tramiteTraficoBaja.getEstado().isEmpty()) || !respuestaG.getError()) {
				tramiteTraficoBaja.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
			}
		}
		return SUCCESS;
	}

	private void gestionarResultadoYObtenerTramite(ResultadoBajasBean resultado) {
		try {
			ResultadoBajasBean resultadoTram = servicioTramiteBaja.obtenerTramiteBajaDto(resultado.getNumExpediente());
			if (!resultadoTram.getError()) {
				tramiteTraficoBaja = resultadoTram.getTramiteBajaDto();
				String mensaje = "Trámite guardado ";
				if ((resultado.getListaMensajesAvisos() != null && !resultado.getListaMensajesAvisos().isEmpty())
					&& (resultado.getListaMensajesError() != null && !resultado.getListaMensajesError().isEmpty())) {
					mensaje += " con los siguientes errores y avisos: ";
					addActionMessage(mensaje);
					String mensajeError = "";
					for (String error : resultado.getListaMensajesError()) {
						mensajeError += "- " +error;
					}
					addActionError(mensajeError);
					String mensajeAviso = "";
					for (String aviso : resultado.getListaMensajesAvisos()) {
						mensajeAviso += "- " +aviso;
					}
					addActionMessage(mensajeAviso);
				} else if (resultado.getListaMensajesAvisos() != null
						&& !resultado.getListaMensajesAvisos().isEmpty()) {
					mensaje += " con los siguientes avisos: ";
					for (String aviso : resultado.getListaMensajesAvisos()) {
						mensaje += "- " +aviso;
					}
					addActionMessage(mensaje);
				} else if (resultado.getListaMensajesError() != null && !resultado.getListaMensajesError().isEmpty()) {
					mensaje += " con los siguientes errores: ";
					addActionMessage(mensaje);
					String mensajeError = "";
					for (String error : resultado.getListaMensajesError()) {
						mensajeError += "- " +error;
					}
					addActionError(mensajeError);
				} else {
					addActionMessage(mensaje);
				}
			} else {
				addActionError(resultadoTram.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tramite con expediente: " + resultado.getNumExpediente() + ", error: ",e);
		}
	}

	public String cargarExpediente() {
		if (numExpediente != null) {
			tramiteTraficoBaja = servicioTraficoBaja.getTramiteBaja(numExpediente, true);
			if (tramiteTraficoBaja == null) {
				addActionError("No se ha podido recuperar el trámite.");
			}
			datosFacturacion();
		}
		return SUCCESS;
	}

	private void actualizarExpediente() {
		tramiteTraficoBaja = servicioTraficoBaja.getTramiteBaja(tramiteTraficoBaja.getNumExpediente(), true);
		datosFacturacion();
	}

	public String validar() {
		ResultBean respuestaV = null;
		ResultBean respuestaG;
		boolean hayTasa = false;
		Boolean tienePermisoBtv = utilesColegiado.tienePermisoBTV();
		if (!utilesColegiado.tienePermisoMantenimientoBajas()) {
			addActionError("No tiene permiso para realizar una baja.");
			return SUCCESS;
		}
		String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
		if ("SI".equals(nuevasBajas)) {
			ResultadoBajasBean resultado = servicioTramiteBaja.validarTramite(tramiteTraficoBaja.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Tramite validado correctamente.");
			}
			ResultadoBajasBean resultadoTram = servicioTramiteBaja.obtenerTramiteBajaDto(tramiteTraficoBaja.getNumExpediente());
			if (!resultadoTram.getError()) {
				tramiteTraficoBaja = resultadoTram.getTramiteBajaDto();
			} else {
				addActionError(resultadoTram.getMensaje());
			}
		} else {
			if (tramiteTraficoBaja != null && tramiteTraficoBaja.getTasa() != null && !"-1".equals(tramiteTraficoBaja.getTasa().getCodigoTasa())) {
				hayTasa = true;
			}
			try {
				respuestaG = servicioTraficoBaja.guardarTramite(tramiteTraficoBaja, utilesColegiado.getIdUsuarioSessionBigDecimal(), true, utilesColegiado.tienePermisoAdmin(), tienePermisoBtv);
			} catch (Exception e) {
				respuestaG = new ResultBean();
				respuestaG.setError(true);
				respuestaG.addMensajeALista("Error al guardar el trámite.");
				log.error("Error al guardar el tramite", e);
			}
			if (!respuestaG.getError()) {
				respuestaV = servicioTraficoBaja.validarTramite(tramiteTraficoBaja.getNumExpediente(), utilesColegiado.tienePermisoBTV());
				if (respuestaV.getError()) {
					aniadirMensajeError(respuestaV);
				} else {
					addActionMessage("Trámite Validado.");
					actualizarExpediente();
					if (EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTraficoBaja.getEstado())) {
						try {
							servicioTramiteTrafico.custodiar(tramiteTraficoBaja, utilesColegiado.getAlias(tramiteTraficoBaja.getNumColegiado()));
						} catch (UnsupportedEncodingException | InternalException e) {
							log.error("Error al guardar el archivo de custodia", e);
						}
					}
				}
			} else {
				aniadirMensajeError(respuestaG);
			}
			// Tras recoger todo, se introduce el Tipo de Tasa manualmente en el Bean de pantalla (SOLO SI HAY UNA TASA), ya que es el único dato que no va a la llamada del PQ de la BD
			if (hayTasa) {
				tramiteTraficoBaja.getTasa().setTipoTasa("4.1");
			}
		}
		return SUCCESS;
	}

	public String comprobar() {
		ResultBean resultadoBean = null;
		boolean hayTasa = false;
		Boolean tienePermisoBtv = utilesColegiado.tienePermisoBTV();
		try {
			if (!utilesColegiado.tienePermisoMantenimientoBajas()) {
				addActionError("No tiene permiso para realizar una baja.");
				return SUCCESS;
			}
			String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
			if("SI".equals(nuevasBajas)){
				ResultadoBajasBean resultado = servicioTramiteBaja.comprobarBtv(tramiteTraficoBaja.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if(resultado.getError()){
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage("La solicitud de se ha encolado correctamente.");
					ResultadoBajasBean resultadoTram = servicioTramiteBaja.obtenerTramiteBajaDto(tramiteTraficoBaja.getNumExpediente());
					if(!resultadoTram.getError()){
						tramiteTraficoBaja = resultadoTram.getTramiteBajaDto();
					} else {
						addActionError(resultadoTram.getMensaje());
					}
				}
			} else {
				if (tramiteTraficoBaja != null && tramiteTraficoBaja.getTasa() != null && !"-1".equals(tramiteTraficoBaja.getTasa().getCodigoTasa())) {
					hayTasa = true;
				}
				resultadoBean = servicioTraficoBaja.guardarTramite(tramiteTraficoBaja, utilesColegiado.getIdUsuarioSessionBigDecimal(), true, utilesColegiado.tienePermisoAdmin(), tienePermisoBtv);
				if (resultadoBean == null || !resultadoBean.getError()) {
					resultadoBean = servicioTraficoBaja.comprobarConsultaBTV(tramiteTraficoBaja, utilesColegiado.getIdUsuarioSessionBigDecimal());
				}
				if (resultadoBean != null && resultadoBean.getError()) {
					for (String mensaje : resultadoBean.getListaMensajes()) {
						addActionError(mensaje);
					}
				} else {
					if (resultadoBean.getAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE) != null) {
						numExpediente = (BigDecimal) resultadoBean.getAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE);
						actualizarExpediente();
					}
					addActionMessage("La solicitud de se ha encolado correctamente.");
				}
				// Tras recoger todo, se introduce el Tipo de Tasa manualmente en el Bean de pantalla (SOLO SI HAY UNA TASA), ya que es el único dato que no va a la llamada del PQ de la BD
				if (hayTasa) {
					tramiteTraficoBaja.getTasa().setTipoTasa("4.1");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar las bajas, error:", e);
			addActionError("Ha sucedido un error a la hora de comprobar las bajas");
		}
		return SUCCESS;
	}

	public String tramitarTelematicamente() {
		ResultBean resultadoBean = null;
		try {
			if (!utilesColegiado.tienePermisoBTV()) {
				addActionError("No tiene permiso para realizar una baja telemática.");
				return SUCCESS;
			}
			String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
			if ("SI".equals(nuevasBajas)) {
				ResultadoBajasBean resultado = servicioTramiteBaja.tramitarBtv(tramiteTraficoBaja.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError(resultado.getMensaje());
				} else {
					addActionMessage("La solicitud de baja se ha encolado correctamente.");
					ResultadoBajasBean resultadoTram = servicioTramiteBaja.obtenerTramiteBajaDto(tramiteTraficoBaja.getNumExpediente());
					if (!resultadoTram.getError()) {
						tramiteTraficoBaja = resultadoTram.getTramiteBajaDto();
					} else {
						addActionError(resultadoTram.getMensaje());
					}
				}
			} else {
				resultadoBean = servicioTraficoBaja.tramitarBajaTelematicamente(tramiteTraficoBaja.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getColegioDelContrato());
				if (resultadoBean != null && resultadoBean.getError()) {
					for (String mensaje : resultadoBean.getListaMensajes()) {
						addActionError(mensaje);
					}
				} else {
					if (resultadoBean.getAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE) != null) {
						numExpediente = (BigDecimal) resultadoBean.getAttachment(ServicioTramiteTraficoBaja.NUMEXPEDIENTE);
						actualizarExpediente();
					}
					addActionMessage("La solicitud se ha encolado correctamente.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tramitar telematicamente, error: ", e);
			addActionError("Ha sucedido un error a la hora de tramitar telemáticamente.");
		}
		return SUCCESS;
	}

	// Consultar persona
	public String consultarPersona() {
		if (nifBusqueda != null && !nifBusqueda.equals("")) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nifBusqueda.toUpperCase(), tramiteTraficoBaja.getNumColegiado());
			if (interviniente == null) {
				addActionMessage("No existen datos para ese NIF.");
				interviniente = new IntervinienteTraficoDto();
				PersonaDto persona = new PersonaDto();
				persona.setNif(nifBusqueda);
				interviniente.setPersona(persona);
			} else {
				if (tipoIntervinienteBuscar != null && TipoInterviniente.Titular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoBaja.setTitular(interviniente);
				} else if (tipoIntervinienteBuscar != null && TipoInterviniente.Adquiriente.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoBaja.setAdquiriente(interviniente);
				} else if (tipoIntervinienteBuscar != null && TipoInterviniente.RepresentanteTitular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoBaja.setRepresentanteTitular(interviniente);
				} else if (tipoIntervinienteBuscar != null && TipoInterviniente.RepresentanteAdquiriente.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoBaja.setRepresentanteAdquiriente(interviniente);
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

	public String imprimir() {
		if (numExpediente != null) {
			numsExpediente = numExpediente.toString();
			resultBeanImprimir = new ResultValidarTramitesImprimir();
			resultBeanImprimir.setTipoTramite(TipoTramiteTrafico.Baja);
			setVolverAntiguaConsulta(Boolean.TRUE);
			return IMPRIMIR_ACTION;
		} else {
			addActionError("No se ha podido imprimir");
			return SUCCESS;
		}
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
			datosFacturacion.setPersona(tramiteTraficoBaja.getTitular().getPersona());
			datosFacturacion.setNif(tramiteTraficoBaja.getTitular().getPersona().getNif());
			datosFacturacion.setDireccion(tramiteTraficoBaja.getTitular().getDireccion());
		}

		try {
			datosFacturacion.setNumColegiado(tramiteTraficoBaja.getNumColegiado());
			datosFacturacion.setIdContrato(tramiteTraficoBaja.getIdContrato().longValue());
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

	public String pendientesEnvioExcel() {
		if (!utilesColegiado.tienePermisoMantenimientoTrafico()) {
			addActionError("No tienes permiso para realizar esta acción.");
		}
		String nuevasBajas = gestorPropiedades.valorPropertie("activar.nuevasBajas");
		if ("SI".equals(nuevasBajas)) {
			ResultadoBajasBean resultado = servicioTramiteBaja.pendienteEnvioExcel(tramiteTraficoBaja.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (resultado.getError()) {
				addActionError(resultado.getMensaje());
			} else {
				addActionMessage("Trámite Pendiente Envío Excel.");
				ResultadoBajasBean resultadoTram = servicioTramiteBaja.obtenerTramiteBajaDto(tramiteTraficoBaja.getNumExpediente());
				if (!resultadoTram.getError()) {
					tramiteTraficoBaja = resultadoTram.getTramiteBajaDto();
				} else {
					addActionError(resultadoTram.getMensaje());
				}
			}
		} else {
			ResultBean respuesta = servicioTraficoBaja.pendientesEnvioExcel(tramiteTraficoBaja, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (respuesta != null && respuesta.getError()) {
				for (String error : respuesta.getListaMensajes()) {
					addActionError(error);
				}
			} else {
				addActionMessage("Trámite Pendiente Envío Excel.");
				actualizarExpediente();
			}
		}
		return SUCCESS;
	}

	public String buscarVehiculo() {
		VehiculoDto vehiculoDto = null;
		if (matriculaBusqueda != null && !matriculaBusqueda.isEmpty()) {
			vehiculoDto = servicioVehiculo.getVehiculoDto(null, tramiteTraficoBaja.getNumColegiado(), matriculaBusqueda.toUpperCase(), null, null, EstadoVehiculo.Activo);
		}
		if (vehiculoDto == null) {
			vehiculoDto = new VehiculoDto();
			vehiculoDto.setMatricula(matriculaBusqueda);
		}
		tramiteTraficoBaja.setVehiculoDto(vehiculoDto);
		return SUCCESS;
	}

	private void datosFacturacion() {
		if (tramiteTraficoBaja != null && (EstadoTramiteTrafico.Finalizado_Excel.getValorEnum().equals(tramiteTraficoBaja.getEstado()) || EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum()
				.equals(tramiteTraficoBaja.getEstado()) || EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramiteTraficoBaja.getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente
						.getValorEnum().equals(tramiteTraficoBaja.getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTraficoBaja.getEstado()))) {
			datosFacturacion = new TramiteTrafFacturacionDto();

			if (tramiteTraficoBaja.getTramiteFacturacion() != null && tramiteTraficoBaja.getTramiteFacturacion().getNif() != null && !tramiteTraficoBaja.getTramiteFacturacion().getNif().equals("")) {
				datosFacturacion.setNif(tramiteTraficoBaja.getTramiteFacturacion().getNif());
			}

			if (tramiteTraficoBaja.getTramiteFacturacion() != null && tramiteTraficoBaja.getTramiteFacturacion().getCodTasa() != null && !tramiteTraficoBaja.getTramiteFacturacion().getCodTasa()
					.isEmpty()) {
				datosFacturacion.setCodTasa(tramiteTraficoBaja.getTramiteFacturacion().getCodTasa());
			} else if (tramiteTraficoBaja.getTasa() != null && tramiteTraficoBaja.getTasa().getCodigoTasa() != null && !tramiteTraficoBaja.getTasa().getCodigoTasa().isEmpty()) {
				datosFacturacion.setCodTasa(tramiteTraficoBaja.getTasa().getCodigoTasa());
			}

			if (tramiteTraficoBaja.getTramiteFacturacion() != null && tramiteTraficoBaja.getTramiteFacturacion().getBastidor() != null && !tramiteTraficoBaja.getTramiteFacturacion().getBastidor()
					.isEmpty()) {
				datosFacturacion.setBastidor(tramiteTraficoBaja.getTramiteFacturacion().getBastidor());
			} else if (tramiteTraficoBaja.getVehiculoDto() != null && tramiteTraficoBaja.getVehiculoDto().getBastidor() != null && !tramiteTraficoBaja.getVehiculoDto().getBastidor().isEmpty()) {
				datosFacturacion.setBastidor(tramiteTraficoBaja.getVehiculoDto().getBastidor());
			}

			if (tramiteTraficoBaja.getTramiteFacturacion() != null && tramiteTraficoBaja.getTramiteFacturacion().getDireccion() != null) {
				datosFacturacion.setDireccion(tramiteTraficoBaja.getTramiteFacturacion().getDireccion());
			}

			if (tramiteTraficoBaja.getTramiteFacturacion() != null && tramiteTraficoBaja.getTramiteFacturacion().getMatricula() != null && !tramiteTraficoBaja.getTramiteFacturacion().getMatricula()
					.isEmpty()) {
				datosFacturacion.setMatricula(tramiteTraficoBaja.getTramiteFacturacion().getMatricula());
			} else if (tramiteTraficoBaja.getVehiculoDto() != null && tramiteTraficoBaja.getVehiculoDto().getMatricula() != null && !tramiteTraficoBaja.getVehiculoDto().getMatricula().isEmpty()) {
				datosFacturacion.setMatricula(tramiteTraficoBaja.getVehiculoDto().getMatricula());
			}

			if (tramiteTraficoBaja.getNumExpediente() != null) {
				datosFacturacion.setNumExpediente(tramiteTraficoBaja.getNumExpediente());
			}

			if (tramiteTraficoBaja.getTramiteFacturacion() != null && tramiteTraficoBaja.getTramiteFacturacion().getPersona() != null) {
				datosFacturacion.setPersona(tramiteTraficoBaja.getTramiteFacturacion().getPersona());
			}

			if (tramiteTraficoBaja.getTipoTramite() != null && !tramiteTraficoBaja.getTipoTramite().isEmpty()) {
				datosFacturacion.setTipoTramite(tramiteTraficoBaja.getTipoTramite());
			}
		}
	}

	// Mantis 19262. David Sierra. Validación para comprobar que las fechas introducidas tienen un formato correcto
	private String validarFechasBaja(TramiteTrafBajaDto tramiteTraficoBaja) {
		String resultadoValidacion = "OK";
		do {
			if (!tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getVehiculoDto().getFechaMatriculacion())) {
				resultadoValidacion = "La fecha de matriculación del vehículo que ha introducido no es válida";
				break;
			}
			if (!tramiteTraficoBaja.getTitular().getPersona().getFechaNacimiento().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getTitular().getPersona().getFechaNacimiento())) {
				resultadoValidacion = "La fecha de nacimiento que ha introducido para el titular no es válida";
				break;
			}
			if (!tramiteTraficoBaja.getRepresentanteTitular().getFechaInicio().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteTitular().getFechaInicio())) {
				resultadoValidacion = "La fecha de inicio de tutela que ha introducido para el representante del titular no es válida";
				break;
			}
			if (!tramiteTraficoBaja.getRepresentanteTitular().getFechaFin().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteTitular().getFechaFin())) {
				resultadoValidacion = "La fecha de fin de tutela que ha introducido para el representante del titular no es válida";
				break;
			}
			if (!tramiteTraficoBaja.getAdquiriente().getPersona().getFechaNacimiento().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getAdquiriente().getPersona().getFechaNacimiento())) {
				resultadoValidacion = "La fecha de nacimiento que ha introducido para el adquiriente no es válida";
				break;
			}
			if (!tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaInicio().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaInicio())) {
				resultadoValidacion = "La fecha de inicio de tutela que ha introducido para el representante del adquiriente no es válida";
				break;
			}
			if (!tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaFin().isfechaNula() &&
				!utilesFecha.validarFormatoFecha(tramiteTraficoBaja.getRepresentanteAdquiriente().getFechaFin())) {
				resultadoValidacion = "La fecha de fin de tutela que ha introducido para el representante del adquiriente no es válida";
				break;
			}
			break;
		}
		while (true);
		return resultadoValidacion;
	}

	// GETTERS AND SETTERS
	public TramiteTrafBajaDto getTramiteTraficoBaja() {
		return tramiteTraficoBaja;
	}

	public void setTramiteTraficoBaja(TramiteTrafBajaDto tramiteTraficoBaja) {
		this.tramiteTraficoBaja = tramiteTraficoBaja;
	}

	public ServicioTramiteTraficoBaja getServicioTraficoBaja() {
		return servicioTraficoBaja;
	}

	public void setServicioTraficoBaja(ServicioTramiteTraficoBaja servicioTraficoBaja) {
		this.servicioTraficoBaja = servicioTraficoBaja;
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

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
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

	public String getMatriculaBusqueda() {
		return matriculaBusqueda;
	}

	public void setMatriculaBusqueda(String matriculaBusqueda) {
		this.matriculaBusqueda = matriculaBusqueda;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioFacturacion getServicioFacturacion() {
		return servicioFacturacion;
	}

	public void setServicioFacturacion(ServicioFacturacion servicioFacturacion) {
		this.servicioFacturacion = servicioFacturacion;
	}

	public boolean isUtilizarTitular() {
		return utilizarTitular;
	}

	public void setUtilizarTitular(boolean utilizarTitular) {
		this.utilizarTitular = utilizarTitular;
	}

	public String getNumsExpediente() {
		return numsExpediente;
	}

	public void setNumsExpediente(String numsExpediente) {
		this.numsExpediente = numsExpediente;
	}

	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}

	public void setResultBeanImprimir(ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}

	public boolean isVolverAntiguaConsulta() {
		return volverAntiguaConsulta;
	}

	public void setVolverAntiguaConsulta(boolean volverAntiguaConsulta) {
		this.volverAntiguaConsulta = volverAntiguaConsulta;
	}
}