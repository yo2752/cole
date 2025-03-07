package org.gestoresmadrid.oegam2.controller.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class AltaTramiteTraficoDuplicadoAction extends ActionBase {

	private static final long serialVersionUID = 7392473041688739478L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaTramiteTraficoDuplicadoAction.class);

	private TramiteTrafDuplicadoDto tramiteTraficoDuplicado;

	@Autowired
	private ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioFacturacion servicioFacturacion;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	private BigDecimal numExpediente;

	private String nifBusqueda;

	private String tipoIntervinienteBuscar;

	private String matriculaBusqueda;

	private TramiteTrafFacturacionDto datosFacturacion;

	private boolean utilizarTitular;

	private String TIPO_INTERVINIENTE_FACTURACION = "Facturacion";

	private File fichero;

	private String ficheroFileName;

	private String nombreDoc;

	private InputStream inputStream;

	private String ficheroDescarga;

	private String idFicheroEliminar;

	public String inicio() throws Throwable {
		if (tramiteTraficoDuplicado == null) {
			tramiteTraficoDuplicado = new TramiteTrafDuplicadoDto();
		}
		if (tramiteTraficoDuplicado.getJefaturaTraficoDto() == null) {
			tramiteTraficoDuplicado.setJefaturaTraficoDto(new JefaturaTraficoDto());
		}
		tramiteTraficoDuplicado.getJefaturaTraficoDto().setJefaturaProvincial(utilesColegiado.getIdJefaturaSesion());
		tramiteTraficoDuplicado.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		tramiteTraficoDuplicado.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());
		return SUCCESS;
	}

	public String guardar() {
		ResultBean respuestaG = null;
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
			addActionError("No tiene permiso para realizar un duplicado.");
			return SUCCESS;
		}

		if (tramiteTraficoDuplicado.getMotivoDuplicado() != null) {
			if (tramiteTraficoDuplicado.getTitular().getDireccion().getIdProvincia() == null
					|| "-1".equals(tramiteTraficoDuplicado.getTitular().getDireccion().getIdProvincia())
					|| tramiteTraficoDuplicado.getTitular().getDireccion().getIdMunicipio() == null
					|| "-1".equals(tramiteTraficoDuplicado.getTitular().getDireccion().getIdMunicipio())
					|| tramiteTraficoDuplicado.getTitular().getDireccion().getCodPostal() == null
					|| "".equals(tramiteTraficoDuplicado.getTitular().getDireccion().getCodPostal())
					|| tramiteTraficoDuplicado.getTitular().getDireccion().getNombreVia() == null
					|| "".equals(tramiteTraficoDuplicado.getTitular().getDireccion().getNombreVia())
					|| tramiteTraficoDuplicado.getTitular().getDireccion().getIdTipoVia() == null
					|| "-1".equals(tramiteTraficoDuplicado.getTitular().getDireccion().getIdTipoVia())) {
				addActionError("Duplicados es necesario domicilio fiscal");
				return SUCCESS;
			}
		}

		// 190117 Mantis 0025205. Si el estado es "Finalizado excel con incidencia" lo
		// guardamos en la tabla a "Iniciado"
		if (tramiteTraficoDuplicado.getEstado()
				.equals(EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum())) {
			tramiteTraficoDuplicado.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
		}

		try {
			if (tramiteTraficoDuplicado.getNumColegiado() == null
					|| tramiteTraficoDuplicado.getNumColegiado().isEmpty()) {
				tramiteTraficoDuplicado.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			}
			tramiteTraficoDuplicado.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
			tramiteTraficoDuplicado.setIdTipoCreacion(new BigDecimal(TipoCreacion.OEGAM.getValorEnum()));
			if(utiles.esUsuarioSive(tramiteTraficoDuplicado.getNumColegiado())) {
				TasaDto tasa = null;
				if (tramiteTraficoDuplicado.getTasaPermiso() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaPermiso())) {
					tasa = new TasaDto();
					tasa.setTipoTasa(ServicioTasa.TIPO_TASA_4_4);
					tasa.setCodigoTasa(tramiteTraficoDuplicado.getTasaPermiso());
				} else if (tramiteTraficoDuplicado.getTasaFichaTecnica() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaFichaTecnica())) {
					tasa = new TasaDto();
					tasa.setTipoTasa(ServicioTasa.TIPO_TASA_4_1);
					tasa.setCodigoTasa(tramiteTraficoDuplicado.getTasaFichaTecnica());
				}
	//			else if (tramiteTraficoDuplicado.getTasaImportacion() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaImportacion())) {
	//				tasa = new TasaDto();
	//				tasa.setTipoTasa(ServicioTasa.TIPO_TASA_4_1);
	//				tasa.setCodigoTasa(tramiteTraficoDuplicado.getTasaImportacion());
	//			}
				if (tramiteTraficoDuplicado.getTasaPermiso() != null && "-1".equals(tramiteTraficoDuplicado.getTasaPermiso())) {
					tramiteTraficoDuplicado.setTasaPermiso(null);
				}
				if (tramiteTraficoDuplicado.getTasaFichaTecnica() != null && "-1".equals(tramiteTraficoDuplicado.getTasaFichaTecnica())) {
					tramiteTraficoDuplicado.setTasaFichaTecnica(null);
				}
				if (tramiteTraficoDuplicado.getTasaImportacion() != null && "-1".equals(tramiteTraficoDuplicado.getTasaImportacion())) {
					tramiteTraficoDuplicado.setTasaImportacion(null);
				}
				tramiteTraficoDuplicado.setTasa(tasa);
			} else if (tramiteTraficoDuplicado.getTasa() != null && "-1".equals(tramiteTraficoDuplicado.getTasa().getCodigoTasa())) {
				tramiteTraficoDuplicado.setTasa(null);
			}
			respuestaG = servicioTramiteTraficoDuplicado.guardarTramite(tramiteTraficoDuplicado,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), false, false, utilesColegiado.tienePermisoAdmin());
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
				for (String mensaje : respuestaG.getListaMensajes()) {
					if (!mensaje.contains("Tasa asignada al tramite con numero de expediente")) {
						addActionError(mensaje);
					}
				}
			}
		} else {
			log.error("Error al guardar el duplicado");
			if (respuestaG.getListaMensajes() != null && !respuestaG.getListaMensajes().isEmpty()) {
				addActionError("Error al guardar");
				for (String mensaje : respuestaG.getListaMensajes())
					addActionError(mensaje);
			}
		}

		if ((tramiteTraficoDuplicado.getEstado() != null && tramiteTraficoDuplicado.getEstado().isEmpty())
				|| !respuestaG.getError()) {
			tramiteTraficoDuplicado.setEstado(EstadoTramiteTrafico.Iniciado.getValorEnum());
		}

		return SUCCESS;
	}

	// Consultar persona
	public String consultarPersona() {
		if (nifBusqueda != null && !nifBusqueda.equals("")) {
			IntervinienteTraficoDto interviniente = servicioIntervinienteTrafico
					.crearIntervinienteNif(nifBusqueda.toUpperCase(), tramiteTraficoDuplicado.getNumColegiado());
			if (interviniente == null) {
				addActionMessage("No existen datos para ese NIF.");
				interviniente = new IntervinienteTraficoDto();
				PersonaDto persona = new PersonaDto();
				persona.setNif(nifBusqueda);
				interviniente.setPersona(persona);
			} else {
				if (tipoIntervinienteBuscar != null
						&& TipoInterviniente.Titular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoDuplicado.setTitular(interviniente);
				} else if (tipoIntervinienteBuscar != null
						&& TipoInterviniente.RepresentanteTitular.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoDuplicado.setRepresentanteTitular(interviniente);
				} else if (tipoIntervinienteBuscar != null
						&& TipoInterviniente.CotitularTransmision.getNombreEnum().equals(tipoIntervinienteBuscar)) {
					tramiteTraficoDuplicado.setCotitular(interviniente);
				} else if (tipoIntervinienteBuscar != null
						&& TIPO_INTERVINIENTE_FACTURACION.equals(tipoIntervinienteBuscar)) {
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

	public String buscarVehiculo() {
		VehiculoDto vehiculoDto = null;
		if (matriculaBusqueda != null && !matriculaBusqueda.equals("")) {
			vehiculoDto = servicioVehiculo.getVehiculoDto(null, tramiteTraficoDuplicado.getNumColegiado(),
					matriculaBusqueda.toUpperCase(), null, null, EstadoVehiculo.Activo);
		}
		if (vehiculoDto == null) {
			vehiculoDto = new VehiculoDto();
			vehiculoDto.setMatricula(matriculaBusqueda);
		}
		tramiteTraficoDuplicado.setVehiculoDto(vehiculoDto);
		return SUCCESS;
	}

	public String guardarTitularFacturacion() {
		if (!utilizarTitular && (datosFacturacion == null || datosFacturacion.getNif() == null
				|| datosFacturacion.getNif().isEmpty())) {
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
			datosFacturacion.setPersona(tramiteTraficoDuplicado.getTitular().getPersona());
			datosFacturacion.setNif(tramiteTraficoDuplicado.getTitular().getPersona().getNif());
			datosFacturacion.setDireccion(tramiteTraficoDuplicado.getTitular().getDireccion());
		}

		try {
			datosFacturacion.setNumColegiado(tramiteTraficoDuplicado.getNumColegiado());
			datosFacturacion.setIdContrato(tramiteTraficoDuplicado.getIdContrato().longValue());
			ResultBean resultado = servicioFacturacion.guardar(datosFacturacion,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), utilizarTitular);
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

		ResultBean respuesta = servicioTramiteTraficoDuplicado.pendientesEnvioExcel(tramiteTraficoDuplicado,
				utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (respuesta != null && respuesta.getError()) {
			for (String error : respuesta.getListaMensajes()) {
				addActionError(error);
			}
		} else {
			addActionMessage("Trámite Pendiente Envío Excel.");
			actualizarExpediente();
		}
		return SUCCESS;
	}

	public String validar() {
		ResultBean respuestaV = null;
		ResultBean respuestaG;
//		boolean hayTasa = false;
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
			addActionError("No tiene permiso para realizar un duplicado.");
			return SUCCESS;
		}

//		if (tramiteTraficoDuplicado != null && (tramiteTraficoDuplicado.getTasa() != null && !"-1".equals(tramiteTraficoDuplicado.getTasa().getCodigoTasa()) 
//				|| ( tramiteTraficoDuplicado.getTasaPermiso() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaPermiso()) )
//				|| ( tramiteTraficoDuplicado.getTasaImportacion() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaImportacion()) )
//				)) {
//			hayTasa = true;
//		}

		if (utiles.esUsuarioSive(tramiteTraficoDuplicado.getNumColegiado())) {
			TasaDto tasa = null;
			if (tramiteTraficoDuplicado.getTasaPermiso() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaPermiso())) {
				tasa = new TasaDto();
				tasa.setTipoTasa(ServicioTasa.TIPO_TASA_4_4);
				tasa.setCodigoTasa(tramiteTraficoDuplicado.getTasaPermiso());
			} else if (tramiteTraficoDuplicado.getTasaFichaTecnica() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaFichaTecnica())) {
				tasa = new TasaDto();
				tasa.setTipoTasa(ServicioTasa.TIPO_TASA_4_1);
				tasa.setCodigoTasa(tramiteTraficoDuplicado.getTasaFichaTecnica());
			}
//			else if (tramiteTraficoDuplicado.getTasaImportacion() != null && !"-1".equals(tramiteTraficoDuplicado.getTasaImportacion())) {
//				tasa = new TasaDto();
//				tasa.setTipoTasa(ServicioTasa.TIPO_TASA_4_1);
//				tasa.setCodigoTasa(tramiteTraficoDuplicado.getTasaImportacion());
//			}
			if (tramiteTraficoDuplicado.getTasaPermiso() != null && "-1".equals(tramiteTraficoDuplicado.getTasaPermiso())) {
				tramiteTraficoDuplicado.setTasaPermiso(null);
			}
			if (tramiteTraficoDuplicado.getTasaFichaTecnica() != null && "-1".equals(tramiteTraficoDuplicado.getTasaFichaTecnica())) {
				tramiteTraficoDuplicado.setTasaFichaTecnica(null);
			}
			if (tramiteTraficoDuplicado.getTasaImportacion() != null && "-1".equals(tramiteTraficoDuplicado.getTasaImportacion())) {
				tramiteTraficoDuplicado.setTasaImportacion(null);
			}
			tramiteTraficoDuplicado.setTasa(tasa);
		} else if (tramiteTraficoDuplicado.getTasa() != null && "-1".equals(tramiteTraficoDuplicado.getTasa().getCodigoTasa())) {
			tramiteTraficoDuplicado.setTasa(null);
		}

		try {
			respuestaG = servicioTramiteTraficoDuplicado.guardarTramite(tramiteTraficoDuplicado,
					utilesColegiado.getIdUsuarioSessionBigDecimal(), true, false, utilesColegiado.tienePermisoAdmin());
		} catch (Exception e) {
			respuestaG = new ResultBean();
			respuestaG.setError(true);
			respuestaG.addMensajeALista("Error al guardar el trámite.");
			log.error("Error al guardar el tramite", e);
		}
		if (!respuestaG.getError()) {
			actualizarExpediente();
			respuestaV = servicioTramiteTraficoDuplicado.validarTramite(tramiteTraficoDuplicado);
			if (respuestaV.getError()) {
				for (String mensaje : respuestaV.getListaMensajes())
					addActionError(mensaje);
			} else {
//				if(UtilesVistaDuplicado.getInstance().esUsuarioSive() 
//						&& !tramiteTraficoDuplicado.getMotivoDuplicado().equals(MotivoDuplicado.CambD.getValorEnum()) 
//						&& !tramiteTraficoDuplicado.getMotivoDuplicado().equals(MotivoDuplicado.CambDCond.getValorEnum()) ) {
//					tramiteTraficoDuplicado.setEstado(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum());
//					try {
//						tramiteTraficoDuplicado.setEstado(respuestaV.getAttachment(ServicioTramiteTraficoDuplicado.ESTADO_VALIDAR).toString());
//						respuestaG = servicioTramiteTraficoDuplicado.guardarTramite(tramiteTraficoDuplicado,
//								utilesColegiado.getIdUsuarioSessionBigDecimal(), true, false, utilesColegiado.tienePermisoAdmin());
//					} catch (Exception e) {
//						respuestaG = new ResultBean();
//						respuestaG.setError(true);
//						respuestaG.addMensajeALista("Error al guardar el tramite estado nuevo.");
//						log.error("Error al guardar el tramite estado nuevo", e);
//					}
//				}
				addActionMessage("Trámite Validado.");
				actualizarExpediente();
			}
		} else {
			for (String mensaje : respuestaG.getListaMensajes()) {
				addActionError(mensaje);
			}
		}
		return SUCCESS;
	}

	public String comprobar() {
		try {
			if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
				addActionError("No tiene permiso para realizar un duplicado.");
				return SUCCESS;
			}
			ResultBean resultado = servicioTramiteTraficoDuplicado.comprobarDuplicado(tramiteTraficoDuplicado.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else {
				tramiteTraficoDuplicado.setEstado(EstadoTramiteTrafico.Pendiente_Check_Duplicado.getValorEnum());
				addActionMessage("La solicitud se ha enviado correctamente.");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de comprobar los duplicados, error:", e);
			addActionError("Ha sucedido un error a la hora de comprobar los duplicados");
		}
		return SUCCESS;
	}

	public String tramitar() {
		try {
			if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_DUPLICADOS)) {
				addActionError("No tiene permiso para realizar un duplicado.");
				return SUCCESS;
			}
			ResultBean resultado = servicioTramiteTraficoDuplicado.tramitarDuplicado(tramiteTraficoDuplicado.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				addActionError("Error en la tramitación telemática: ");
				if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					for (String mensaje : resultado.getListaMensajes()) {
						addActionError(mensaje);
					}
				}
				if (StringUtils.isNotBlank(resultado.getMensaje())) {
					addActionError(resultado.getMensaje());
				}
			} else {
				tramiteTraficoDuplicado.setEstado(EstadoTramiteTrafico.Pendiente_Envio.getValorEnum());
				addActionMessage("La solicitud se ha enviado correctamente.");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tramitar los duplicados, error:", e);
			addActionError("Ha sucedido un error a la hora de tramitar los duplicados");
		}
		return SUCCESS;
	}

	public String cargarExpediente() {
		if (numExpediente != null) {
			tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(numExpediente, true);
			if (tramiteTraficoDuplicado == null) {
				addActionError("No se ha podido recuperar el trámite.");
			}
			datosFacturacion();
		}
		return SUCCESS;
	}

	public String incluirFichero() throws Exception, OegamExcepcion {
		guardar();
		log.info("TRÁMITE DUPLICADO: Inicio -- incluirFichero.");

		try {
			if (!hasActionErrors()) {
				if (fichero != null) {
					ArrayList<FicheroInfo> ficherosSubidos = servicioTramiteTraficoDuplicado
							.recuperarDocumentos(tramiteTraficoDuplicado.getNumExpediente());
					if (ficherosSubidos == null) {
						ficherosSubidos = new ArrayList<FicheroInfo>();
					}
					// Verifica que no se ha alcanzado el máximo de ficheros permitidos.
					int maxFicheros = Integer
							.parseInt(gestorPropiedades.valorPropertie("envioDuplicados.numero.maximo.ficheros"));
					if (ficherosSubidos.size() == maxFicheros) {
						addActionError("Alcanzado el máximo de ficheros a incluir en el envío de documentación: "
								+ maxFicheros + ". Elimine el antiguo si quiere subir uno nuevo.");
						log.error(
								"TRÁMITE DUPLICADO: Error -- Se ha alcanzado el número máximo de ficheros permitidos: "
										+ maxFicheros);
						tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(numExpediente,
								true);
						return SUCCESS;
					}
					// Verifica que el fichero no supere los 200KB
					String tamanioDuaPermitido = gestorPropiedades
							.valorPropertie("tamanio.maximo.adjuntos.DUA.duplicado");
					if (StringUtils.isNotBlank(tamanioDuaPermitido) && tamanioDuaPermitido.matches("[0-9]+")
							&& (fichero.length()/1000) > Long.parseLong(tamanioDuaPermitido)) {
						addActionError("Fichero demasiado grande para incluir en el envío de documentación: "
								+ fichero.length() / 1000 + "KB. Puede subir ficheros de hasta " + tamanioDuaPermitido
								+ "KB.");
						log.error(
								"TRÁMITE DUPLICADO: Error -- Fichero demasiado grande para incluir en el envío de documentación: "
										+ fichero.length() / 1000 + "KB. Se permite ficheros DUA hasta"
										+ tamanioDuaPermitido + "KB.");
						tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(numExpediente,
								true);
						return SUCCESS;
					}
					// Verifica que el nombre del fichero sea válido.
					if (!utiles.esNombreFicheroValido(ficheroFileName)) {
						addActionError("El fichero añadido debe tener un nombre correcto");
						log.error(
								"TRÁMITE DUPLICADO: Error -- El nombre del fichero que se intenta subir no es válido: "
										+ ficheroFileName);
						tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(numExpediente,
								true);
						return SUCCESS;
					}
					// Verifica que el fichero subido tiene un formato soportado.
					String extension = utiles.dameExtension(ficheroFileName, false);
					if (extension == null) {
						addActionError("El fichero añadido debe tener un formato soportado: .pdf");
						log.error(
								"TRÁMITE DUPLICADO: Error -- El formato del fichero que se intenta subir no es válido.");
						tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(numExpediente,
								true);
						return SUCCESS;
					}
					// Verifica que solo se aporten PDFs
					if (!extension.equalsIgnoreCase("PDF")) {
						addActionError("El fichero añadido debe tener un formato soportado: .pdf");
						log.error(
								"TRÁMITE DUPLICADO: Error -- El formato del fichero que se intenta subir no es válido.");
						tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(numExpediente,
								true);
						return SUCCESS;
					}
					// Se guarda el fichero
					try {
						File fichero = guardarFichero(extension);
						FicheroInfo ficheroInfo = new FicheroInfo(fichero, ficherosSubidos.size());
						ficherosSubidos.add(ficheroInfo);
						addActionMessage("Fichero añadido correctamente.");
						tramiteTraficoDuplicado.setFicherosSubidos(ficherosSubidos);
						log.info("TRÁMITE DUPLICADO: Fin del guardado del fichero.");
					} catch (Exception e) {
						log.error("TRÁMITE DUPLICADO: Error -- " + e);
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
		return SUCCESS;
	}

	public String descargarDocumentacion() {
		log.info("TRÁMITE DUPLICADO: Inicio -- descargarDocumentacion.");
		ArrayList<FicheroInfo> ficheros = null;

		try {
			ficheros = servicioTramiteTraficoDuplicado.recuperarDocumentos(tramiteTraficoDuplicado.getNumExpediente());

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
		return SUCCESS;
	}

	public String eliminarFichero() {
		guardar();
		log.info("TRÁMITE DUPLICADO: Inicio -- eliminarFichero.");

		try {
			if (tramiteTraficoDuplicado == null) {
				log.error("TRÁMITE DUPLICADO: Error -- No se ha recuperado tramite en curso de la sesion.");
				return SUCCESS;
			}

			ArrayList<FicheroInfo> ficheros = servicioTramiteTraficoDuplicado
					.recuperarDocumentos(tramiteTraficoDuplicado.getNumExpediente());

			if (ficheros != null && !ficheros.isEmpty()) {
				Iterator<FicheroInfo> it = ficheros.iterator();
				while (it.hasNext()) {
					FicheroInfo tmp = it.next();
					if (tmp.getNombre().equalsIgnoreCase(idFicheroEliminar)) {
						tmp.getFichero().delete();
						it.remove();
						tramiteTraficoDuplicado.setFicherosSubidos(ficheros);
						addActionMessage("Fichero eliminado correctamente.");
						cargarExpediente();
						return SUCCESS;
					}
				}
			} else {
				addActionError("No existe fichero para eliminar.");
			}
			log.info("TRÁMITE DUPLICADO: Fin -- descargarDocumentacion.");
		} catch (Exception e) {
			log.error(e);
			log.error(UtilesExcepciones.stackTraceAcadena(e, 3));
			addActionError(e.getMessage());
		}
		return SUCCESS;
	}

	private File guardarFichero(String extension) throws Exception, OegamExcepcion {
		FileInputStream fis = new FileInputStream(fichero);
		byte[] array = IOUtils.toByteArray(fis);
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.EXCELS);
		ficheroBean.setSubTipo(ConstantesGestorFicheros.DUPLICADO_PERMISO_CONDUCIR_DUA);
		ficheroBean.setExtension("." + extension);
		ficheroBean.setFicheroByte(array);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteTraficoDuplicado.getNumExpediente()));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(
				tramiteTraficoDuplicado.getNumColegiado() + "_" + tramiteTraficoDuplicado.getNumExpediente());
		File fichero = gestorDocumentos.guardarByte(ficheroBean);

		FileOutputStream fos = new FileOutputStream(fichero);
		fos.write(array);
		fos.close();

		return fichero;
	}

	private void datosFacturacion() {
		if (tramiteTraficoDuplicado != null
				&& (EstadoTramiteTrafico.Finalizado_Excel.getValorEnum().equals(tramiteTraficoDuplicado.getEstado())
						|| EstadoTramiteTrafico.Finalizado_Excel_Impreso.getValorEnum()
								.equals(tramiteTraficoDuplicado.getEstado())
						|| EstadoTramiteTrafico.Finalizado_PDF.getValorEnum()
								.equals(tramiteTraficoDuplicado.getEstado()))) {
			datosFacturacion = new TramiteTrafFacturacionDto();

			if (tramiteTraficoDuplicado.getTramiteFacturacion() != null
					&& tramiteTraficoDuplicado.getTramiteFacturacion().getNif() != null
					&& !tramiteTraficoDuplicado.getTramiteFacturacion().getNif().equals("")) {
				datosFacturacion.setNif(tramiteTraficoDuplicado.getTramiteFacturacion().getNif());
			}

			if (tramiteTraficoDuplicado.getTramiteFacturacion() != null
					&& tramiteTraficoDuplicado.getTramiteFacturacion().getCodTasa() != null
					&& !tramiteTraficoDuplicado.getTramiteFacturacion().getCodTasa().isEmpty()) {
				datosFacturacion.setCodTasa(tramiteTraficoDuplicado.getTramiteFacturacion().getCodTasa());
			} else if (tramiteTraficoDuplicado.getTasa() != null
					&& tramiteTraficoDuplicado.getTasa().getCodigoTasa() != null
					&& !tramiteTraficoDuplicado.getTasa().getCodigoTasa().isEmpty()) {
				datosFacturacion.setCodTasa(tramiteTraficoDuplicado.getTasa().getCodigoTasa());
			}

			if (tramiteTraficoDuplicado.getTramiteFacturacion() != null
					&& tramiteTraficoDuplicado.getTramiteFacturacion().getBastidor() != null
					&& !tramiteTraficoDuplicado.getTramiteFacturacion().getBastidor().isEmpty()) {
				datosFacturacion.setBastidor(tramiteTraficoDuplicado.getTramiteFacturacion().getBastidor());
			} else if (tramiteTraficoDuplicado.getVehiculoDto() != null
					&& tramiteTraficoDuplicado.getVehiculoDto().getBastidor() != null
					&& !tramiteTraficoDuplicado.getVehiculoDto().getBastidor().isEmpty()) {
				datosFacturacion.setBastidor(tramiteTraficoDuplicado.getVehiculoDto().getBastidor());
			}

			if (tramiteTraficoDuplicado.getTramiteFacturacion() != null
					&& tramiteTraficoDuplicado.getTramiteFacturacion().getDireccion() != null) {
				datosFacturacion.setDireccion(tramiteTraficoDuplicado.getTramiteFacturacion().getDireccion());
			}

			if (tramiteTraficoDuplicado.getTramiteFacturacion() != null
					&& tramiteTraficoDuplicado.getTramiteFacturacion().getMatricula() != null
					&& !tramiteTraficoDuplicado.getTramiteFacturacion().getMatricula().isEmpty()) {
				datosFacturacion.setMatricula(tramiteTraficoDuplicado.getTramiteFacturacion().getMatricula());
			} else if (tramiteTraficoDuplicado.getVehiculoDto() != null
					&& tramiteTraficoDuplicado.getVehiculoDto().getMatricula() != null
					&& !tramiteTraficoDuplicado.getVehiculoDto().getMatricula().isEmpty()) {
				datosFacturacion.setMatricula(tramiteTraficoDuplicado.getVehiculoDto().getMatricula());
			}

			if (tramiteTraficoDuplicado.getNumExpediente() != null) {
				datosFacturacion.setNumExpediente(tramiteTraficoDuplicado.getNumExpediente());
			}

			if (tramiteTraficoDuplicado.getTramiteFacturacion() != null
					&& tramiteTraficoDuplicado.getTramiteFacturacion().getPersona() != null) {
				datosFacturacion.setPersona(tramiteTraficoDuplicado.getTramiteFacturacion().getPersona());
			}

			if (tramiteTraficoDuplicado.getTipoTramite() != null
					&& !tramiteTraficoDuplicado.getTipoTramite().isEmpty()) {
				datosFacturacion.setTipoTramite(tramiteTraficoDuplicado.getTipoTramite());
			}
		}
	}

	private void actualizarExpediente() {
		tramiteTraficoDuplicado = servicioTramiteTraficoDuplicado
				.getTramiteDuplicado(tramiteTraficoDuplicado.getNumExpediente(), true);
	}

	public String getTipoIntervinienteBuscar() {
		return tipoIntervinienteBuscar;
	}

	public void setTipoIntervinienteBuscar(String tipoIntervinienteBuscar) {
		this.tipoIntervinienteBuscar = tipoIntervinienteBuscar;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public TramiteTrafDuplicadoDto getTramiteTraficoDuplicado() {
		return tramiteTraficoDuplicado;
	}

	public void setTramiteTraficoDuplicado(TramiteTrafDuplicadoDto tramiteTraficoDuplicado) {
		this.tramiteTraficoDuplicado = tramiteTraficoDuplicado;
	}

	public String getNifBusqueda() {
		return nifBusqueda;
	}

	public void setNifBusqueda(String nifBusqueda) {
		this.nifBusqueda = nifBusqueda;
	}

	public String getMatriculaBusqueda() {
		return matriculaBusqueda;
	}

	public void setMatriculaBusqueda(String matriculaBusqueda) {
		this.matriculaBusqueda = matriculaBusqueda;
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

	public String getNombreDoc() {
		return nombreDoc;
	}

	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
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

	public String getIdFicheroEliminar() {
		return idFicheroEliminar;
	}

	public void setIdFicheroEliminar(String idFicheroEliminar) {
		this.idFicheroEliminar = idFicheroEliminar;
	}

}