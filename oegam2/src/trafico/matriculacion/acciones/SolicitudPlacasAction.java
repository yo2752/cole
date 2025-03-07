package trafico.matriculacion.acciones;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes.ConstantesPlacasMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.service.ServicioValidacionPlacasMatricula;
import org.gestoresmadrid.oegamPlacasMatricula.view.bean.ValidacionPlacasBean;
import org.gestoresmadrid.oegamPlacasMatricula.view.dto.SolicitudPlacaDto;
import org.gestoresmadrid.placas.utilities.enumerados.TipoSolicitudPlacasEnum;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import general.acciones.ScriptFeaturesBean;
import general.acciones.TipoPosicionScript;
import general.acciones.TipoScript;
import hibernate.utiles.constantes.ConstantesHibernate;
import trafico.beans.EstadisticasPlacasBean;
import trafico.utiles.UtilesConversiones;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Action para la solicitud de placas de matriculación
 * @author Santiago Cuenca
 * @date 11/02/2014
 */
public class SolicitudPlacasAction extends ActionBase {

	// Constantes
	private static final long serialVersionUID = 1L;

	private static final String NO_SE_ENCONTRARON_SOLICITUDES = "No se encontraron solicitudes.";

	// Atributos
	private static final ILoggerOegam log = LoggerOegam.getLogger(SolicitudPlacasAction.class);

	private String numsExpedientes;
	private String solicitudSelecBorrar;
	private String idSolicitud;
	private String estadoSolicitudId;
	private ArrayList<SolicitudPlacaDto> listaSolicitudes;
	private String estado;
	private boolean aplicarTodas;
	private BigDecimal numCreditosTotales;
	private BigDecimal numCreditosDisponibles;
	private BigDecimal numCreditosDisponiblesCTP1;
	private BigDecimal numCreditosDisponiblesCTP2;
	private BigDecimal numCreditosDisponiblesCTP3;
	private BigDecimal numCreditosDisponiblesCTP4;
	private BigDecimal numCreditosDisponiblesCTP5;
	private BigDecimal numCreditosDisponiblesCTP6;
	private BigDecimal numCreditosDisponiblesCTP8;
	private BigDecimal numCreditosBloqueados;
	private String listaExpedientes;
	private String botonVolver;

	private EstadisticasPlacasBean estadisticasPlacasBean;

	private BigDecimal idUsuario;

	private HttpServletRequest request;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private UtilesConversiones utilesConv;

	@Autowired
	private ServicioPlacasMatricula servicioPlacasMatricula;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioValidacionPlacasMatricula servicioValidacionPlacasMatricula;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioCredito servicioCredito;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**
	 * Carga en la plantilla la hoja de estilos adicional para las solicitudes de placas
	 */
	private void cargarCSSBasico() {
		// Carga de CSS específico para placas de matriculación
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean css = new ScriptFeaturesBean();
		css.setName(ConstantesPlacasMatriculacion.PLACAS_MATR_CSS);
		css.setPosicion(TipoPosicionScript.TOP);
		css.setTipo(TipoScript.CSS);
		addScripts.getScripts().add(css);
		/* Fin Carga de javaScript específico para placas de matriculación */
	}

	/**
	 * Carga en la plantilla las funciones de Javascript adicionales para las solicitudes de placas
	 */
	private void cargarJSBasico() {
		/* Carga de javaScript específico para placas de matriculación */
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean js = new ScriptFeaturesBean();
		js.setName(ConstantesPlacasMatriculacion.PLACAS_MATR_JS_BOTTOM);
		js.setPosicion(TipoPosicionScript.BOTTOM);
		js.setTipo(TipoScript.JS);
		addScripts.getScripts().add(js);
		/* Fin Carga de javaScript específico para placas de matriculación */
	}

	public String alta() {
		// Se ha metido iterador en la jsp, y es necesario que la lista al menos tenga 1 elemento vacío para que muestre
		// un registro de inserccion de datos.
		listaSolicitudes = new ArrayList<SolicitudPlacaDto>();
		listaSolicitudes.add(new SolicitudPlacaDto());

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.ALTA_SOLICITUD;
	}

	private InputStream inputStream; // Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName; // Nombre del fichero a imprimir
	private String fileSize; // Tamaño del fichero a imprimir

	/**
	 * Prepara la pantalla de inicio de solicitudes de placas de matriculación
	 * @return inicioSolicitud
	 */
	public String nueva() {

		if (listaSolicitudes == null) {
			listaSolicitudes = new ArrayList<SolicitudPlacaDto>();
		}

		if (isDuplicate(listaSolicitudes)) {
			addActionError("No puede haber más de 1 solicitud con la misma matricula.");
			return "error";
		}

		idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal() : null;
		UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
		Integer idContrato = utilesColegiado.getIdContratoSessionBigDecimal() != null ? utilesColegiado.getIdContratoSessionBigDecimal().intValue() : null;

		String[] expedientes = null;
		if (getListaExpedientes() != null && !getListaExpedientes().isEmpty()) {
			expedientes = getListaExpedientes().replace(" ", "").split(",");
			numsExpedientes = getListaExpedientes();
		} else if (numsExpedientes != null && !numsExpedientes.isEmpty()) {
			expedientes = numsExpedientes.replace(" ", "").split(",");
		}

		try {
			if (expedientes != null || listaSolicitudes != null) {
				listaSolicitudes = getServicio().crearNuevasSolicitudes(listaSolicitudes, expedientes, usuario, idContrato);
			} else {
				addActionError(ConstantesPlacasMatriculacion.NO_SE_PUDO_RECUPERAR_NINGUN_EXPEDIENTE_PARA_CREAR_LA_SOLICITUD_DE_PLACAS);
			}
		} catch (OegamExcepcion e) {
			addActionError(e.getMessage() != null ? e.getMessage() : "");

			cargarCSSBasico();
			cargarJSBasico();

			return ConstantesPlacasMatriculacion.INICIO_SOLICITUD;
		}

		// Es necesario guardar en sesión la lista de solicitudes creada puesto que
		// en la ventana de solicitud de placas se ofrece la posibilidad de borrar
		// una solicitud con un icono de papelera que habrá en cada una de ellas. Si
		// se pulsa sobre el icono, lo que tenemos es un hiperenlace, el cual no produce
		// una submit por método POST y, por tanto, no se envía una request a la
		// acción de refrescar, de manera que no tendremos lista de solicitudes si
		// no la rescatamos de la sesión. No es posible cargar manualmente los parámetros
		// de la request en la llamada a la acción de refrescar desde una función JS,
		// porque el método usado es GET, el cual establece un límite para la URL de
		// 2048 bytes, con más de 10 solicitudes seleccionadas da un pete por exceder
		// el límite
		getSession().put("listaSolicitudes", listaSolicitudes);

		if (expedientes == null) {
			setBotonVolver("A");
		} else {
			setBotonVolver("CT");
		}
		getSession().put("direccionVolver", getBotonVolver());

		validarCreditos();

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.INICIO_SOLICITUD;

	}

	private boolean isDuplicate(ArrayList<SolicitudPlacaDto> lista) {

		for (int i = 0; i < lista.size() - 1; i++) {
			for (int j = i + 1; j < lista.size(); j++) {
				if (lista.get(i).getMatricula().equals(lista.get(j).getMatricula()))
					return true;
			}
		}
		return false;
	}

	/**
	 * Refresca los contenedores de solicitud de matriculación
	 * @return refrescarSolicitud
	 */
	@SuppressWarnings("unchecked")
	public String refrescar() {

		if (getSession().get("listaSolicitudes") != null) {
			listaSolicitudes = (ArrayList<SolicitudPlacaDto>) getSession().get("listaSolicitudes");
			listaSolicitudes.remove(Integer.parseInt(solicitudSelecBorrar));

			if (getSession().get("direccionVolver") != null) {
				setBotonVolver((String) getSession().get("direccionVolver"));
			}
		} else {
			addActionError(ConstantesPlacasMatriculacion.NO_SE_PUDO_RECUPERAR_NINGUN_EXPEDIENTE_PARA_CREAR_LA_SOLICITUD_DE_PLACAS);
		}

		validarCreditos();

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.INICIO_SOLICITUD;

	}

	/**
	 * Prepara la pantalla de edición de solicitudes
	 * @return inicioSolicitud
	 */
	public String modificar() {

		String[] ids = (String[]) idSolicitud.split("-");
		String[] estados = (String[]) estadoSolicitudId.split("-");
		String resultado = "";

		ValidacionPlacasBean resultadoValidacion = servicioValidacionPlacasMatricula.validarSolicitudesModificar(estados);
		if (resultadoValidacion.isError()) {
			super.setActionErrors(resultadoValidacion.getMensajes());
			getSession().put("listaErrores", super.getActionErrors());
			resultado = ConstantesPlacasMatriculacion.RECARGAR_LISTADO;
		} else {
			listaSolicitudes = new ArrayList<>();
			for (String id : ids) {
				SolicitudPlacaDto solicitud = getServicio().getSolicitudPantalla(Integer.valueOf(id));

				if (solicitud != null) {
					listaSolicitudes.add(solicitud);
				} else {
					addActionError("No se encontró una de la solicitudes indicadas o no es editable.");
				}

			}

			// Idem que en la creación de nuevas solicitudes, solo que si están en estado iniciado
			// sí que las metemos en sesión, supuesto en el cual se mostrarán con el icono de la papelera
			setBotonVolver("CS");
			if (listaSolicitudes.get(0).getEstado() == 1) {
				getSession().put("listaSolicitudes", listaSolicitudes);
			}
			getSession().put("direccionVolver", getBotonVolver());

			resultado = ConstantesPlacasMatriculacion.INICIO_SOLICITUD;
			validarCreditos();
		}

		cargarCSSBasico();
		cargarJSBasico();

		return resultado;
	}

	/**
	 * Pantalla de borrado de solicitudes
	 * @return inicioSolicitud
	 */
	public String borrar() {

		String[] ids = (String[]) idSolicitud.split("-");
		String[] estados = (String[]) estadoSolicitudId.split("-");

		ValidacionPlacasBean resultadoValidacion = servicioValidacionPlacasMatricula.validarSolicitudesBorrar(estados);
		if (resultadoValidacion.isError()) {
			super.setActionErrors(resultadoValidacion.getMensajes());

			getSession().put("listaErrores", super.getActionErrors());
		} else {
			Integer numEntidades = new Integer(1);
			String[] entidades = (String[]) Array.newInstance(String.class, numEntidades);
			entidades[0] = ConstantesHibernate.USUARIO_PROPERTY;

			for (String id : ids) {
				SolicitudPlacaVO solicitud = getServicio().getSolicitud(Integer.valueOf(id), entidades);

				if (solicitud == null) {
					addActionError("No se encontró una de la solicitudes indicadas o no es editable.");
					getSession().put("listaErrores", super.getActionErrors());
				} else {
					servicioPlacasMatricula.borrarSolicitud(solicitud);
					addActionMessage("Solicitud de placas para la matrícula " + solicitud.getMatricula() + " borrada correctamente");
					getSession().put("listaMensajes", super.getActionMessages());
				}
			}

		}

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.RECARGAR_LISTADO;
	}

	private ResultBean grabarPersona(ValidacionPlacasBean validarBean) {
		PersonaDto persona = new PersonaDto();
		persona.setApellido1RazonSocial(listaSolicitudes.get(validarBean.getNoElemento()).getTitular().getApellido1RazonSocial());
		persona.setApellido2(listaSolicitudes.get(validarBean.getNoElemento()).getTitular().getApellido2() != null ? listaSolicitudes.get(validarBean.getNoElemento()).getTitular().getApellido2()
				: null);
		persona.setNif(listaSolicitudes.get(validarBean.getNoElemento()).getNifTitular());
		persona.setNombre(listaSolicitudes.get(validarBean.getNoElemento()).getTitular().getNombre() != null ? listaSolicitudes.get(validarBean.getNoElemento()).getTitular().getNombre() : null);
		persona.setEstado("1");
		persona.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		persona.setTipoPersona((utilesConv.isNifNie(persona.getNif())) ? TipoPersona.Fisica.getValorEnum() : TipoPersona.Juridica.getValorEnum());

		return servicioPersona.guardarActualizar(persona);
	}

	/**
	 * Gestiona los datos introducidos en pantalla para realizar la solicitud o solicitudes de placas
	 * @return solicitudRealizada
	 */
	public String realizarSolicitud() {

		request = ServletActionContext.getRequest();
		this.setAplicarTodas(new Boolean(request.getParameter("aplicarTodas")));

		if (listaSolicitudes != null) {
			comprobarConfiguracion();

			ValidacionPlacasBean validaRealizacion = servicioValidacionPlacasMatricula.validarRealizacion(listaSolicitudes);
			if (validaRealizacion.isError()) {
				setActionErrors(validaRealizacion.getMensajes());
			}

			ArrayList<ValidacionPlacasBean> validar = servicioPlacasMatricula.realizarSolicitud(listaSolicitudes);
			for (ValidacionPlacasBean validarBean : validar) {
				if (validarBean.isError()) {
					for (String mensaje : validarBean.getMensajes()) {
						if (mensaje.contains("no existe o no tiene DNI")) {
							if (listaSolicitudes.get(validarBean.getNoElemento()).getNifTitular() != null && !listaSolicitudes.get(validarBean.getNoElemento()).getNifTitular().equals("")
									&& listaSolicitudes.get(validarBean.getNoElemento()).getTitular() != null && listaSolicitudes.get(validarBean.getNoElemento()).getTitular()
											.getApellido1RazonSocial() != null && !listaSolicitudes.get(validarBean.getNoElemento()).getTitular().getApellido1RazonSocial().equals("")) {

								ResultBean resultado = grabarPersona(validarBean);

								if (resultado.getError()) {
									addActionError("Error al guardar una persona desde solicitud de placas de matricula " + validarBean.getMatricula());
									log.error("Error al guardar una persona desde solicitud de placas de matricula " + validarBean.getMatricula());
								} else {
									// Meter l llamada a guardar la solicitud si el codigo de respuesta es 0 (se guardó correctamente la persona).
									realizarSolicitud();
								}
							} else {
								addActionError("Error al solicitar placas para " + validarBean.getMatricula() + ": " + mensaje);
								log.error("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
								if (listaSolicitudes.get(validarBean.getNoElemento()).getNifTitular() != null && !"".equals(listaSolicitudes.get(validarBean.getNoElemento()).getNifTitular())) {
									listaSolicitudes.get(validarBean.getNoElemento()).setMostrarSeccionPersona("true");
								}
							}
						} else if (mensaje.contains("no existe Matrícula")) {
							addActionError("Error al solicitar placas para la solicitud " + validarBean.getNoElemento() + 1 + ": " + mensaje);
							log.error("Error al solicitar placas para la solicitud " + validarBean.getNoElemento() + 1 + ": " + mensaje);
						} else {
							addActionError("Error al solicitar placas para " + validarBean.getMatricula() + ": " + mensaje);
							log.error("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
						}
					}
					break;
				} else {
					if (listaSolicitudes.get(validarBean.getNoElemento()).getIdSolicitud() == null) {
						listaSolicitudes.get(validarBean.getNoElemento()).setIdSolicitud(validarBean.getIdSolicitud());
					}
					addActionMessage("Solicitud de placas para la matrícula " + validarBean.getMatricula() + " realizada correctamente");
				}
			}

			// Si se produjo algún error validando cualquier solicitud, suprimimos los mensajes de éxito
			// porque no se ha persistido nada
			if (!getActionErrors().isEmpty()) {
				setActionMessages(null);
			}

			getSession().put("listaSolicitudes", listaSolicitudes);
		} else {
			addActionError(NO_SE_ENCONTRARON_SOLICITUDES);
		}

		if (getSession().get("direccionVolver") != null) {
			setBotonVolver((String) getSession().get("direccionVolver"));
		}

		validarCreditos();

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;

	}

	/**
	 * Comprueba si se marcó aplicar a todas las solicitudes la misma configuración, y en caso afirmativo, copia los atributos de la primera en el resto
	 */
	private void comprobarConfiguracion() {

		if (isAplicarTodas()) {
			SolicitudPlacaDto primera = listaSolicitudes.get(BigDecimal.ZERO.intValue());
			for (SolicitudPlacaDto spBean : listaSolicitudes) {
				spBean.setTipoDelantera(primera.getTipoDelantera());
				spBean.setTipoTrasera(primera.getTipoTrasera());
				spBean.setTipoAdicional(primera.getTipoAdicional());
				spBean.setDuplicada(primera.getDuplicada());
				spBean.setRefPropia(primera.getRefPropia());
			}
		}

	}

	private void cargarFechaSolic() {
		for (SolicitudPlacaDto solic : listaSolicitudes) {
			SolicitudPlacaDto solAnt = servicioPlacasMatricula.getSolicitudPantalla(solic.getIdSolicitud());
			if (solAnt != null) {
				solic.setFechaSolicitud(solAnt.getFechaSolicitud());
			} else {
				solic.setFechaSolicitud(utilesFecha.getFechaActual());
			}
		}
	}

	/**
	 * Cambia el estado de una solicitud
	 * @return solicitudRealizada
	 */
	public String cambiarEstadoSolicitud() {

		// Validamos las distintas solicitudes de placas
		ArrayList<ValidacionPlacasBean> validarSol = servicioValidacionPlacasMatricula.validarSolicitudes(listaSolicitudes, EstadoSolicitudPlacasEnum.Confirmado);

		for (ValidacionPlacasBean resultadoValidar : validarSol) {
			if (resultadoValidar.isError()) {
				cargarFechaSolic();
				setActionErrors(resultadoValidar.getMensajes());

				cargarCSSBasico();
				cargarJSBasico();

				return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
			}
		}

		if (listaSolicitudes != null) {
			boolean haySolicNula = false;
			for (SolicitudPlacaDto solicitudPlacaDto : listaSolicitudes) {
				SolicitudPlacaDto solicitud = getServicio().getSolicitudPantalla(solicitudPlacaDto.getIdSolicitud());
				if (solicitud == null) {
					haySolicNula = true;
					break;
				}
				solicitudPlacaDto.setFechaSolicitud(solicitud.getFechaSolicitud());
				solicitudPlacaDto.setDuplicada(solicitud.getDuplicada());
				solicitudPlacaDto.setTipoDelantera(solicitud.getTipoDelantera());
				solicitudPlacaDto.setTipoTrasera(solicitud.getTipoTrasera());
				solicitudPlacaDto.setTipoAdicional(solicitud.getTipoAdicional());
				solicitudPlacaDto.setRefPropia(solicitud.getRefPropia());
				solicitudPlacaDto.setEstado(Integer.parseInt(estado));
				solicitudPlacaDto.setDescEstado(EstadoSolicitudPlacasEnum.convertirTexto(estado));
				idUsuario = utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal() : null;
				UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
				solicitudPlacaDto.setUsuario(usuario);
			}

			if (haySolicNula) {
				addActionError("Se produjo un error al cambiar la/s solicitud/es a estado " + EstadoSolicitudPlacasEnum.convertirTexto(estado));
			} else if (servicioPlacasMatricula.cambiarEstadoSolicitud(listaSolicitudes)) {
				addActionMessage("La/s solicitud/es ha/n cambiado correctamente a estado " + EstadoSolicitudPlacasEnum.convertirTexto(estado));
			} else {
				addActionError("Se produjo un error al cambiar la/s solicitud/es a estado " + EstadoSolicitudPlacasEnum.convertirTexto(estado));
			}
		} else {
			addActionError(NO_SE_ENCONTRARON_SOLICITUDES);
		}

		if (getSession().get("direccionVolver") != null) {
			setBotonVolver((String) getSession().get("direccionVolver"));
		}

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;

	}

	/**
	 * Gestiona los datos introducidos en pantalla para realizar la solicitud o solicitudes de placas
	 * @return solicitudRealizada
	 */
	public String confirmarSolicitud() {

		if (listaSolicitudes != null) {
			comprobarConfiguracion();

			// Validamos las distintas solicitudes de placas
			ArrayList<ValidacionPlacasBean> validarSol = servicioValidacionPlacasMatricula.validarSolicitudes(listaSolicitudes, EstadoSolicitudPlacasEnum.Iniciado);

			for (ValidacionPlacasBean resultadoValidar : validarSol) {
				if (resultadoValidar.isError()) {
					cargarFechaSolic();
					setActionErrors(resultadoValidar.getMensajes());

					cargarCSSBasico();
					cargarJSBasico();

					return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
				}
			}

			// Validaciones de negocio para la confirmación
			ValidacionPlacasBean validarConf = servicioValidacionPlacasMatricula.validarConfirmacion(listaSolicitudes);
			if (validarConf.isError()) {
				setActionErrors(validarConf.getMensajes());
				if ("Debe guardar la/s solicitud/es".equals(validarConf.getMensajes().get(0))) {
					cargarCSSBasico();
					cargarJSBasico();

					return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
				}
			}

			ArrayList<ValidacionPlacasBean> validar = servicioPlacasMatricula.confirmarSolicitud(listaSolicitudes);
			for (ValidacionPlacasBean validarBean : validar) {

				// Si hay error lo mostramos
				if (validarBean.isError()) {

					// Buscamos la solicitud en la lista, y revertimos su estado a Iniciado
					for (String mensaje : validarBean.getMensajes()) {
						for (SolicitudPlacaDto solicitud : listaSolicitudes) {
							if (solicitud.getNumColegiado().equals(validarBean.getNumColegiado()) && solicitud.getMatricula().equals(validarBean.getMatricula())) {
								solicitud.setEstado(Integer.parseInt(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum()));
							}
						}
						addActionError("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
						log.error("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
					}
					// Si no hay error, enviamos correo de solicitud al colegio y mostramos mensaje de confirmación
				} else {
					for (SolicitudPlacaDto solicitud : listaSolicitudes) {
						if (validarBean.getNumColegiado().equals(solicitud.getNumColegiado()) && validarBean.getMatricula().equals(solicitud.getMatricula())) {
							getServicio().notificar(solicitud, false);
						}
					}
					addActionMessage("Solicitud de placas para la matrícula " + validarBean.getMatricula() + " confirmada correctamente");
				}
			}

			validarCreditos();
		} else {
			addActionError(NO_SE_ENCONTRARON_SOLICITUDES);
		}

		if (getSession().get("direccionVolver") != null) {
			setBotonVolver((String) getSession().get("direccionVolver"));
		}

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;

	}

	/**
	 * Gestiona los datos introducidos en pantalla para realizar la solicitud o solicitudes de placas
	 * @return solicitudRealizada
	 */
	public String finalizarSolicitud() {

		if (listaSolicitudes != null) {
			comprobarConfiguracion();

			// Validamos las distintas solicitudes de placas
			ArrayList<ValidacionPlacasBean> validar = servicioValidacionPlacasMatricula.validarSolicitudes(listaSolicitudes, EstadoSolicitudPlacasEnum.Tramitado);

			for (ValidacionPlacasBean resultadoValidar : validar) {
				if (resultadoValidar.isError()) {
					cargarFechaSolic();
					setActionErrors(resultadoValidar.getMensajes());

					cargarCSSBasico();
					cargarJSBasico();

					return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
				}
			}

			// Validaciones de negocio para finalizar
			ValidacionPlacasBean validarFin = servicioValidacionPlacasMatricula.validarFinalizacion(listaSolicitudes);
			if (validarFin.isError()) {
				setActionErrors(validarFin.getMensajes());

				cargarCSSBasico();
				cargarJSBasico();

				return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
			}

			for (SolicitudPlacaDto solicitud : listaSolicitudes) {
				BigDecimal idUsuarioSolicitud = utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal() : null;
				UsuarioVO usuario = servicioUsuario.getUsuario(idUsuarioSolicitud);
				solicitud.setUsuario(usuario);
			}
			HashMap<Integer, Boolean> notificaciones = getServicio().notificarVarias(listaSolicitudes, true);

			int indice = 0;
			ArrayList<SolicitudPlacaDto> listaAFinalizar = new ArrayList<SolicitudPlacaDto>();
			for (SolicitudPlacaDto spBean : listaSolicitudes) {
				boolean notificar = notificaciones.get(spBean.getIdSolicitud());

				if (notificar) {
					listaAFinalizar.add(listaSolicitudes.get(indice));
				} else {
					addActionError("Error al finalizar solicitud para la matrícula " + spBean.getMatricula() + " al no haber podido ser notificada");
				}
				indice++;
			}

			ArrayList<ValidacionPlacasBean> finalizar = servicioPlacasMatricula.finalizarSolicitud(listaAFinalizar);

			for (ValidacionPlacasBean finalizarBean : finalizar) {
				SolicitudPlacaDto solicitudPlacaDto = listaAFinalizar.get(finalizarBean.getNoElemento());
				if (finalizarBean.getNumColegiado().equals(solicitudPlacaDto.getNumColegiado()) && finalizarBean.getMatricula().equals(solicitudPlacaDto.getMatricula())) {

					// Si hay error lo mostramos, y revertimos el estado
					if (finalizarBean.isError()) {

						// Buscamos la solicitud en la lista, y revertimos su estado
						for (String mensaje : finalizarBean.getMensajes()) {
							for (SolicitudPlacaDto solicitud : listaSolicitudes) {
								if (finalizarBean.getNumColegiado().equals(solicitudPlacaDto.getNumColegiado()) && finalizarBean.getMatricula().equals(solicitudPlacaDto.getMatricula())) {
									solicitud.setEstado(Integer.parseInt(EstadoSolicitudPlacasEnum.Tramitado.getValorEnum()));
								}
							}
							addActionError("Error al finalizar solicitud para la matrícula " + finalizarBean.getMatricula() + ": " + mensaje);
							log.error("Error al finalizar solicitud para la matrícula " + finalizarBean.getMatricula() + ": " + mensaje);
						}
						// Si no hay error, mostramos mensaje de confirmación
					} else {
						addActionMessage("Solicitud de placas para la matrícula " + finalizarBean.getMatricula() + " finalizada correctamente");
					}
				}
			}

			validarCreditos();
		} else {
			addActionError(NO_SE_ENCONTRARON_SOLICITUDES);
		}

		if (getSession().get("direccionVolver") != null) {
			setBotonVolver((String) getSession().get("direccionVolver"));
		}

		cargarCSSBasico();
		cargarJSBasico();

		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
	}

	/**
	 * Método para la vuelta a la ventana de alta de solicitudes, o de consulta de placas, o de consulta de trámites
	 * @return
	 */
	public String volver() {
		String resultado = "";

		if (getSession().get("listaSolicitudes") != null) {
			getSession().remove("listaSolicitudes");
		}

		if (getSession().get("direccionVolver") != null) {
			setBotonVolver((String) getSession().get("direccionVolver"));
			getSession().remove("direccionVolver");
		}

		if ("CS".equals(getBotonVolver())) {
			resultado = "recargarListado";
		} else if ("CT".equals(getBotonVolver())) {
			resultado = "resultadoVolverConsTram";
		} else {
			resultado = "resultadoVolverAltaSolic";
		}

		return resultado;
	}

	private void validarCreditos() {

		TipoSolicitudPlacasEnum[] tiposSolicitudes = TipoSolicitudPlacasEnum.values();

		for (TipoSolicitudPlacasEnum tipoSolicitud : tiposSolicitudes) {

			ResultBean resultado = servicioCredito.validarCreditos(tipoSolicitud.getValorEnum(), utilesColegiado.getIdContratoSessionBigDecimal(), new BigDecimal(1));

			switch (tipoSolicitud) {
				case Solicitud_Placa_Ordinaria_Larga:
					setNumCreditosDisponiblesCTP1((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ordinaria_Corta_Alfa_Romeo:
					setNumCreditosDisponiblesCTP2((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Motocicleta:
					setNumCreditosDisponiblesCTP3((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Motocicleta_Trial:
					setNumCreditosDisponiblesCTP4((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Vehiculo_Especial:
					setNumCreditosDisponiblesCTP5((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ciclomotor:
					setNumCreditosDisponiblesCTP6((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_TaxiVTC:
					setNumCreditosDisponiblesCTP8((BigDecimal) resultado.getAttachment(ServicioCredito.CREDITOS_DISPONIBLES));
					break;
			}

		}

	}

	public String estadisticas() {
		if (estadisticasPlacasBean.getFecha().isfechaNula()) {
			addActionError("Debe indicar una fecha inicio y fin completa");
			cargarCSSBasico();
			cargarJSBasico();
			return ConstantesPlacasMatriculacion.ESTADISTICAS_SOLICITUD;
		}
		byte[] pdfBytes = getServicio().generarEstadisticas(estadisticasPlacasBean);
		if (pdfBytes == null) {
			addActionError("Se ha producido un error en la generación de las estadísticas");
			cargarCSSBasico();
			cargarJSBasico();
			return ConstantesPlacasMatriculacion.ESTADISTICAS_SOLICITUD;
		} else {
			addActionError("Documento de estadísticas generado correctamente");
		}
		setFileName("estadisticas_placas.pdf");
		inputStream = new ByteArrayInputStream(pdfBytes);
		return ConstantesPlacasMatriculacion.DESCARGAR_PDF;
	}

	public String inicioEstadisticas() throws ParseException, OegamExcepcion {
		estadisticasPlacasBean = new EstadisticasPlacasBean();

		cargarCSSBasico();
		cargarJSBasico();

		Fecha fechaFin = utilesFecha.getFechaActual();
		Fecha fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);
		FechaFraccionada fechaConsulta = new FechaFraccionada();
		fechaConsulta.setDiaInicio(fechaInicio.getDia());
		fechaConsulta.setMesInicio(fechaInicio.getMes());
		fechaConsulta.setAnioInicio(fechaInicio.getAnio());
		fechaConsulta.setDiaFin(fechaFin.getDia());
		fechaConsulta.setMesFin(fechaFin.getMes());
		fechaConsulta.setAnioFin(fechaFin.getAnio());
		estadisticasPlacasBean.setFecha(fechaConsulta);

		return ConstantesPlacasMatriculacion.ESTADISTICAS_SOLICITUD;
	}

	/* Inicio getters & setters */

	public String getNumsExpedientes() {
		return numsExpedientes;
	}

	public void setNumsExpedientes(String numsExpedientes) {
		this.numsExpedientes = numsExpedientes;
	}

	public String getSolicitudSelecBorrar() {
		return solicitudSelecBorrar;
	}

	public void setSolicitudSelecBorrar(String solicitudSelecBorrar) {
		this.solicitudSelecBorrar = solicitudSelecBorrar;
	}

	public ArrayList<SolicitudPlacaDto> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(ArrayList<SolicitudPlacaDto> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isAplicarTodas() {
		return aplicarTodas;
	}

	public void setAplicarTodas(boolean aplicarTodas) {
		this.aplicarTodas = aplicarTodas;
	}

	public BigDecimal getNumCreditosTotales() {
		return numCreditosTotales;
	}

	public void setNumCreditosTotales(BigDecimal numCreditosTotales) {
		this.numCreditosTotales = numCreditosTotales;
	}

	public BigDecimal getNumCreditosDisponibles() {
		return numCreditosDisponibles;
	}

	public void setNumCreditosDisponibles(BigDecimal numCreditosDisponibles) {
		this.numCreditosDisponibles = numCreditosDisponibles;
	}

	public BigDecimal getNumCreditosDisponiblesCTP1() {
		return numCreditosDisponiblesCTP1;
	}

	public void setNumCreditosDisponiblesCTP1(BigDecimal numCreditosDisponiblesCTP1) {
		this.numCreditosDisponiblesCTP1 = numCreditosDisponiblesCTP1;
	}

	public BigDecimal getNumCreditosDisponiblesCTP2() {
		return numCreditosDisponiblesCTP2;
	}

	public void setNumCreditosDisponiblesCTP2(BigDecimal numCreditosDisponiblesCTP2) {
		this.numCreditosDisponiblesCTP2 = numCreditosDisponiblesCTP2;
	}

	public BigDecimal getNumCreditosDisponiblesCTP3() {
		return numCreditosDisponiblesCTP3;
	}

	public void setNumCreditosDisponiblesCTP3(BigDecimal numCreditosDisponiblesCTP3) {
		this.numCreditosDisponiblesCTP3 = numCreditosDisponiblesCTP3;
	}

	public BigDecimal getNumCreditosDisponiblesCTP4() {
		return numCreditosDisponiblesCTP4;
	}

	public void setNumCreditosDisponiblesCTP4(BigDecimal numCreditosDisponiblesCTP4) {
		this.numCreditosDisponiblesCTP4 = numCreditosDisponiblesCTP4;
	}

	public BigDecimal getNumCreditosDisponiblesCTP5() {
		return numCreditosDisponiblesCTP5;
	}

	public void setNumCreditosDisponiblesCTP5(BigDecimal numCreditosDisponiblesCTP5) {
		this.numCreditosDisponiblesCTP5 = numCreditosDisponiblesCTP5;
	}

	public BigDecimal getNumCreditosDisponiblesCTP6() {
		return numCreditosDisponiblesCTP6;
	}

	public void setNumCreditosDisponiblesCTP6(BigDecimal numCreditosDisponiblesCTP6) {
		this.numCreditosDisponiblesCTP6 = numCreditosDisponiblesCTP6;
	}

	public BigDecimal getNumCreditosDisponiblesCTP8() {
		return numCreditosDisponiblesCTP8;
	}

	public void setNumCreditosDisponiblesCTP8(BigDecimal numCreditosDisponiblesCTP8) {
		this.numCreditosDisponiblesCTP8 = numCreditosDisponiblesCTP8;
	}

	public BigDecimal getNumCreditosBloqueados() {
		return numCreditosBloqueados;
	}

	public void setNumCreditosBloqueados(BigDecimal numCreditosBloqueados) {
		this.numCreditosBloqueados = numCreditosBloqueados;
	}

	/* Fin getters & setters */

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ServicioPlacasMatricula getServicio() {
		return servicioPlacasMatricula;
	}

	public void setServicioPlacasMatricula(ServicioPlacasMatricula servicio) {
		this.servicioPlacasMatricula = servicio;
	}

	public EstadisticasPlacasBean getEstadisticasPlacasBean() {
		return estadisticasPlacasBean;
	}

	public void setEstadisticasPlacasBean(EstadisticasPlacasBean estadisticasPlacasBean) {
		this.estadisticasPlacasBean = estadisticasPlacasBean;
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

	public void setBotonVolver(String botonVolver) {
		this.botonVolver = botonVolver;
	}

	public void setIdUsuario(BigDecimal idUsario) {
		this.idUsuario = idUsario;
	}

	public String getEstadoSolicitudId() {
		return estadoSolicitudId;
	}

	public void setEstadoSolicitudId(String estadoSolicitudId) {
		this.estadoSolicitudId = estadoSolicitudId;
	}

	public String getListaExpedientes() {
		return listaExpedientes;
	}

	public void setListaExpedientes(String listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public String getBotonVolver() {
		return botonVolver;
	}

}