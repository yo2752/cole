package org.gestoresmadrid.oegam2.controller.action;

import java.math.BigDecimal;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioFacturacionTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import trafico.utiles.UtilesVistaTrafico;
import trafico.utiles.UtilidadesAccionTrafico;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoCreacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class SolicitudInformacionAction extends ActionBase {

	private static final long serialVersionUID = -3762738548568575920L;

	private static final String DETALLE = "detalle";

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(SolicitudInformacionAction.class);

	private String idVehiculoBorrar;
	private String numExpediente;
	private TramiteTrafSolInfoDto tramite;
	private SolicitudInformeVehiculoDto solicitud;
	private boolean imprimible = false;
	private boolean existeFichero = false;
	private String nifBusqueda;
	private PaginatedList listaAcciones;

	private String tipoIntervinienteBuscar;
	private TramiteTrafFacturacionDto datosFacturacion;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo;

	@Autowired
	private ServicioFacturacionTasa servicioFacturacionTasa;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesColegiado utilesColegiado;

	/**
	 * Primera entrada del controlador, devuelve el detalle del trámite si existe o el formulario en blanco si es nuevo
	 * @return
	 */
	public String inicio() {
		if (numExpediente != null && !numExpediente.isEmpty()) {
			ResultBean result = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfo(new BigDecimal(numExpediente));
			if (result.getError()) {
				addActionError("No se pudo recuperar el tramite");
				mostrarMensajesError(result);
			} else {
				tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
			}
		}
		if (tramite == null) {
			tramite = new TramiteTrafSolInfoDto();
			tramite.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			tramite.setContrato(new ContratoDto());
			tramite.getContrato().setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
			tramite.setUsuarioDto(new UsuarioDto());
			tramite.getUsuarioDto().setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
			tramite.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
		} else {
			mantenerAcciones();
			existeFichero = servicioTramiteTraficoSolInfo.existeFicheroInforme(tramite.getNumExpediente());
			if (EstadoTramiteTrafico.Finalizado_PDF.getValorEnum().equals(tramite.getEstado())) {
				datosFacturacion = servicioFacturacionTasa.getFacturacionTasa(tramite.getNumExpediente(), null);
			}
		}

		comprobarPermisos();
		return DETALLE;
	}

	/**
	 * Guarda o actualiza el tramite de solicitud de informacion
	 * @return
	 */
	public String guardar() {
		if (comprobarPermisos() && validarMaximoNumSolicitudes()) {
			try {
				ResultBean result = servicioTramiteTraficoSolInfo.guardarSolicitudInformacion(tramite, solicitud, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
				if (result.getError()) {
					addActionError("No se pudo guardar el tramite");
					mostrarMensajesError(result);
				} else {
					BigDecimal numExpediente = (BigDecimal) result.getAttachment(ServicioTramiteTraficoSolInfo.NUM_EXPEDIENTE);
					result = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfo(numExpediente);
					tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
					addActionMessage("Trámite guardado.");
					solicitud = null;
				}
			} catch (RuntimeException e) {
				LOG.error("Ocurrio un error al guardar la solicitud de informacion", e);
				addActionError("Detalle de la solicitud:" + e.getMessage());
			}
		}
		mantenerAcciones();

		return DETALLE;
	}

	/**
	 * Método que busca un interviniente en bbdd por el DNI y devuelve todos los datos del interviniente.
	 * @return
	 * @throws Throwable
	 */
	public String buscarInterviniente() {
		if (nifBusqueda != null && !nifBusqueda.isEmpty()) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico.crearIntervinienteNif(nifBusqueda.toUpperCase(), tramite.getNumColegiado());
			if (interviniente == null) {
				addActionMessage("No existen datos para ese nif.");
				interviniente = new IntervinienteTraficoDto();
				PersonaDto persona = new PersonaDto();
				persona.setNif(nifBusqueda);
				interviniente.setPersona(persona);
			} else {
				if (TipoInterviniente.Solicitante.getValorEnum().equals(tipoIntervinienteBuscar)){
					tramite.setSolicitante(interviniente);
				} else {
					// Es facturacion, no hay mas intervinientes
					if (datosFacturacion == null) {
						datosFacturacion = new TramiteTrafFacturacionDto();
					}
					datosFacturacion.setPersona(interviniente.getPersona());
					datosFacturacion.setDireccion(interviniente.getDireccion());
					datosFacturacion.setNif(interviniente.getNifInterviniente());
				}
			}
		} else {
			addActionError("Se debe de rellenar el nif");
		}
		return DETALLE;
	}

	/**
	 * Borra una solicitud de informe de un vehiculo del listado del tramite
	 * @return
	 */
	public String borrar() {
		// Se comprueba que el usuario tenga el permiso adecuado
		if (!utilesColegiado.tienePermisoMantenimientoSolicitudes()) {
			addActionError("No tiene permiso para realizar un borrado de solicitud");
		} else if (tramite == null || tramite.getNumExpediente() == null || idVehiculoBorrar == null || idVehiculoBorrar.isEmpty()) {
			// Comprueba que le falta algun parametro necesario
			addActionError("Error en los datos de llamada");
		} else {
			ResultBean result = servicioTramiteTraficoSolInfo.borrarSolicitudVehiculo(tramite.getNumExpediente(), Long.parseLong(idVehiculoBorrar));
			tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
			if (result.getError()) {
				mostrarMensajesError(result);
			}
		}
		mantenerAcciones();
		return DETALLE;
	}

	/**
	 * Reinicia las solicitudes de infomacion del tramite, y el tramite pasa a estado iniciado
	 * @return
	 */
	public String reiniciar() {
		// Se comprueba que el usuario tenga el permiso adecuado
		if (!utilesColegiado.tienePermisoMantenimientoSolicitudes()) {
			addActionError("No tiene permiso para realizar el reinicio de la solicitud");
		} else if (tramite == null || tramite.getNumExpediente() == null) {
			// Comprueba que le falta algun parametro necesario
			addActionError("Error en los datos de llamada");
		} else {
			ResultBean result = servicioTramiteTraficoSolInfo.reiniciarTramite(tramite.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
			if (result.getError()) {
				mostrarMensajesError(result);
			}
		}
		mantenerAcciones();
		return DETALLE;
	}

	/**
	 * Solicita informe Avpo
	 * @return
	 */
	public String solicitarInformeAvpo() {
		if (comprobarPermisos() && validarMaximoNumSolicitudes()) {
			ResultBean result = servicioTramiteTraficoSolInfo.obtenerInformeAVPO(tramite, solicitud, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getNumColegiadoSession(), utilesColegiado
					.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if (result.getError()) {
				mostrarMensajesError(result);
			} else {
				tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
				existeFichero = servicioTramiteTraficoSolInfo.existeFicheroInforme(tramite.getNumExpediente());
				solicitud = null;
			}
		}
		mantenerAcciones();
		return DETALLE;
	}

	/**
	 * Solicita informe Atem
	 * @return
	 */
	public String solicitarInformeAtem() {
		if (comprobarPermisos() && validarMaximoNumSolicitudes()) {
			ResultBean result = servicioTramiteTraficoSolInfo.obtenerInformeAtem(tramite, solicitud, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if (result.getError()) {
				mostrarMensajesError(result);
			} else {
				addActionMessage("Recibirá notificación tras el envío de las solicitudes a DGT.");
				tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
				solicitud = null;
			}
		}
		mantenerAcciones();
		return DETALLE;
	}

	/**
	 * Solicita informe Inteve
	 * @return
	 */
	public String solicitarInformeInteve() {
		if (comprobarPermisos() && validarMaximoNumSolicitudes()) {
			ResultBean result = servicioTramiteTraficoSolInfo.obtenerInformeInteve(tramite, solicitud, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if (result.getError()) {
				mostrarMensajesError(result);
			} else {
				addActionMessage("Recibirá notificación tras el envío de las solicitudes a DGT.");
				tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
				solicitud = null;
			}
		}
		mantenerAcciones();
		return DETALLE;
	}
	
	/**
	 * Solicita informe Inteve Nuevo
	 * @return
	 */
	public String solicitarInformeInteveNuevo() {
		if (comprobarPermisos() && validarMaximoNumSolicitudes()) {
			ResultBean result = servicioTramiteTraficoSolInfo.obtenerInformeInteveNuevo(tramite, solicitud, utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.tienePermisoAdmin());
			if (result.getError()) {
				mostrarMensajesError(result);
			} else {
				addActionMessage("Recibirá notificación tras el envío de las solicitudes a DGT.");
				tramite = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
				solicitud = null;
			}
		}
		mantenerAcciones();
		return DETALLE;
	}

	public String guardarTitularFacturacion() {
		if (datosFacturacion == null || datosFacturacion.getNif() == null || datosFacturacion.getNif().isEmpty()) {
			addActionError("Error debe de estar los datos rellenos");
		} else {
			try {
				datosFacturacion.setNumColegiado(tramite.getNumColegiado());
				datosFacturacion.setIdContrato(tramite.getIdContrato().longValue());
				datosFacturacion.setNumExpediente(tramite.getNumExpediente());
				ResultBean resultado = servicioTramiteTraficoSolInfo.guardarFacturacion(datosFacturacion, utilesColegiado.getIdUsuarioSessionBigDecimal());
				if (resultado.getError()) {
					addActionError("Error al guardar los datos de facturación");
				} else {
					addActionMessage("Datos de Facturación guardados.");
					tramite = (TramiteTrafSolInfoDto) resultado.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
				}
			} catch (Exception e) {
				LOG.error("Ocurrio un error al guardar los datos de facturacion de un tramite de solicitud de informacion", e);
				addActionError("Error al guardar los datos de facturación");
			}
		}
		mantenerAcciones();
		return DETALLE;
	}

	/**
	 * Método para mantener las acciones de un trámite según se vayan realizando acciones con el mismo.
	 * @throws Throwable
	 */
	private void mantenerAcciones() {
		if (tramite != null && tramite.getNumExpediente() != null) {
			// Incorporamos la obtención de acciones por trámite
			String numExpediente = tramite.getNumExpediente().toString();
			try {
				listaAcciones = UtilidadesAccionTrafico.getInstance().generarListaAcciones(numExpediente);
			} catch (NumberFormatException e) {
				LOG.error("Error al recuperar las acciones asociadas al tramite", e);
			} catch (OegamExcepcion e) {
				LOG.error("Error al recuperar las acciones asociadas al tramite", e);
			}
		}
	}

	/**
	 * Comprueba si excede el numero máximo de informes por tramite
	 * @return
	 */
	private boolean validarMaximoNumSolicitudes() {
		// Si lo que se guarda no es un nuevo vehiculo, no comprueba nada
		if (solicitud != null
				&& solicitud.getVehiculo() != null
				&& ((solicitud.getVehiculo().getMatricula() != null && !solicitud.getVehiculo().getMatricula().isEmpty()) || (solicitud.getVehiculo().getBastidor() != null && !solicitud.getVehiculo()
						.getBastidor().isEmpty()))) {
			// Si está configurado AVPO o si el usuario NO es usuario de pruebas INTEVE
			// limitación de N solicitudes por trámite:
			int maximoNumSolicitudes;
			try {
				maximoNumSolicitudes = Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesTrafico.LIMITACION_NUMERO_SOLICITUDES));
			} catch (Exception e) {
				LOG.error("No se puede recuperar el número máximo de solicitudes", e);
				maximoNumSolicitudes = ConstantesTrafico.NUM_MAXIMO_SOLICITUDES_POR_DEFECTO;
			}

			String servidor = UtilesVistaTrafico.getInstance().servidorInformesTecnicos();
			String usuarioPruebasInteve = UtilesVistaTrafico.getInstance().sesionUsuarioPruebasInteve();
			if ((servidor == null || !servidor.equalsIgnoreCase("INTEVE")) && (usuarioPruebasInteve == null || usuarioPruebasInteve.equalsIgnoreCase("NO"))) {
				if (tramite != null && tramite.getSolicitudes() != null && tramite.getSolicitudes().size() >= maximoNumSolicitudes) {
					addActionError("No puede guardar más de " + maximoNumSolicitudes + " vehículos");
					LOG.error("No puede guardar más de " + maximoNumSolicitudes + " vehículos");
					return false;
				}
			}
		}
		return true;
	}

	private boolean comprobarPermisos() {
		// Se comprueba que el usuario tenga el permiso adecuado
		if (!utilesColegiado.tienePermisoMantenimientoSolicitudes()) {
			addActionError("No tiene permiso para realizar una solicitud");
			return false;
		}
		return true;
	}

	private void mostrarMensajesError(ResultBean result) {
		if (result.getMensaje() != null && !result.getMensaje().isEmpty()) {
			addActionError(result.getMensaje());
		}
		for (String messageError : result.getListaMensajes()) {
			addActionError(messageError);
		}
	}

	public TramiteTrafSolInfoDto getTramite() {
		return tramite;
	}

	public void setTramite(TramiteTrafSolInfoDto tramite) {
		this.tramite = tramite;
	}

	public boolean isImprimible() {
		return imprimible;
	}

	public void setImprimible(boolean imprimible) {
		this.imprimible = imprimible;
	}

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public PaginatedList getListaAcciones() {
		return listaAcciones;
	}

	public void setListaAcciones(PaginatedList listaAcciones) {
		this.listaAcciones = listaAcciones;
	}

	public SolicitudInformeVehiculoDto getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudInformeVehiculoDto solicitud) {
		this.solicitud = solicitud;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getIdVehiculoBorrar() {
		return idVehiculoBorrar;
	}

	public void setIdVehiculoBorrar(String idVehiculoBorrar) {
		this.idVehiculoBorrar = idVehiculoBorrar;
	}

	public boolean isExisteFichero() {
		return existeFichero;
	}

	public void setExisteFichero(boolean existeFichero) {
		this.existeFichero = existeFichero;
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public TramiteTrafFacturacionDto getDatosFacturacion() {
		return datosFacturacion;
	}

	public void setDatosFacturacion(TramiteTrafFacturacionDto datosFacturacion) {
		this.datosFacturacion = datosFacturacion;
	}

}