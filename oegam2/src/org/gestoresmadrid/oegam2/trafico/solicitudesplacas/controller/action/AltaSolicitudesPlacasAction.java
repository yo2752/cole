package org.gestoresmadrid.oegam2.trafico.solicitudesplacas.controller.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes.ConstantesPlacasMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.model.service.PlacasMatriculacionService;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.ConsultaPlacasBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.SolicitudPlacaBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.ValidacionPlacasBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import general.acciones.ScriptFeaturesBean;
import general.acciones.TipoPosicionScript;
import general.acciones.TipoScript;
import hibernate.entities.general.Usuario;
import oegam.constantes.ConstantesCreditos;
import oegam.constantes.ConstantesSession;
import trafico.modelo.ModeloCreditosTrafico;
//import trafico.utiles.UtilesConversiones;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Action para la solicitud de placas de matriculación
 * @author Álvaro Fernández
 * @date 27/12/2018
 *
 */
public class AltaSolicitudesPlacasAction extends ActionBase {

	// Constantes
	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaSolicitudesPlacasAction.class);

	@Autowired
	PlacasMatriculacionService placasMatriculacionService;

	@Autowired
	private Conversor conversor;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	private ArrayList<SolicitudPlacaBean> listaSolicitudes;
	private String selSolicitud;

	private String listaExpedientes;
	private String numsExpedientes;
	private ModeloCreditosTrafico modeloCreditosTrafico;

	private BigDecimal numCreditosDisponiblesCTP1;
	private BigDecimal numCreditosDisponiblesCTP2;
	private BigDecimal numCreditosDisponiblesCTP3;
	private BigDecimal numCreditosDisponiblesCTP4;
	private BigDecimal numCreditosDisponiblesCTP5;
	private BigDecimal numCreditosDisponiblesCTP6;
	private BigDecimal numCreditosDisponiblesCTP8;

	private boolean aplicarTodas;
	private Integer idUsuario;

	private String estado;

	private ConsultaPlacasBean consultaPlacasBean;

	/*********************************************************************************************************acciones*/

	public String load_altaSolicitudPlaca(){
		cargarCSSBasico();
		cargarJSBasico();
		// Se ha metido iterador en la jsp, y es necesario que la lista al menos tenga 1 elemento vacío para que muestre
		// un registro de inserccion de datos.
		listaSolicitudes = new ArrayList<>();
		listaSolicitudes.add(new SolicitudPlacaBean());

		return ConstantesPlacasMatriculacion.ALTA_SOLICITUD;
	}

	/**
	 * Prepara la pantalla de inicio de solicitudes de placas de matriculación
	 * @return inicioSolicitud
	 */
	public String boton_AltaDesolicitudes() { // Es el load de la página de alta de placa de matrícula
		cargarCSSBasico();
		cargarJSBasico();

		if (listaSolicitudes == null) {
			listaSolicitudes = new ArrayList<>();
		} else {
			if (selSolicitud != null) { // Borrar e elemento seleccionado
				int num=Integer.parseInt(selSolicitud);
				if (listaSolicitudes.get(num).getIdSolicitud() != null) {
					SolicitudPlacaVO solicitudPlacaVO=conversor.transform(listaSolicitudes.get(num), SolicitudPlacaVO.class);
					placasMatriculacionService.borrarSolicitud(solicitudPlacaVO);
				}
				listaSolicitudes.remove(num);
			}
		}

		if (isDuplicate(listaSolicitudes)) {
			addActionError("No puede haber más de 1 solicitud con la misma matrícula.");
			return "error";
		}

		Integer idUsuario = Integer.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() : null);
		Usuario usuario = placasMatriculacionService.getUsuario(idUsuario);
		Integer idContrato = Integer.valueOf(utilesColegiado.getIdContratoSessionBigDecimal() != null ? utilesColegiado.getIdContratoSessionBigDecimal().intValue() : null);

		String[] expedientes = null;
		if (getListaExpedientes() != null && !getListaExpedientes().isEmpty()){
			expedientes = getListaExpedientes().replaceAll(" ","").split(",");
			numsExpedientes=getListaExpedientes();
		} else if (numsExpedientes != null && !numsExpedientes.isEmpty()) {
			expedientes = numsExpedientes.replaceAll(" ","").split(",");
		}
		setListaExpedientes(null);

		/*
		 * Vamos a cambiar todo desde la base. Ahora lo trataremos todo a partir de vehículos.
		 * Si son trámites, obtenemos sus vehículos primero.
		 */
		try {
			if (expedientes != null || listaSolicitudes != null) {
				ArrayList<TramiteTraficoVO> tramites = new ArrayList<>();

				/*
				 * Este sería el caso en el que se han mandado expedientes
				 * desde consulta de trámites de tráfico
				 */
				if (expedientes != null) {
					for (String expediente : expedientes) {
						TramiteTraficoVO tramite = servicioTramiteTrafico.getTramite(new BigDecimal(expediente), Boolean.TRUE);
						tramites.add(tramite);
					}
					expedientes = null;
					numsExpedientes = null;
				}

				listaSolicitudes = (ArrayList<SolicitudPlacaBean>)placasMatriculacionService.crearNuevasSolicitudes(listaSolicitudes, tramites, usuario, idContrato);

			} else {
				addActionError(ConstantesPlacasMatriculacion.NO_SE_PUDO_RECUPERAR_NINGUN_EXPEDIENTE_PARA_CREAR_LA_SOLICITUD_DE_PLACAS);
			}

		} catch (OegamExcepcion e){
			addActionError(e.getMessage() != null ? e.getMessage() : "");
			return ConstantesPlacasMatriculacion.INICIO_SOLICITUD;
		}

		validarCreditos();

		return ConstantesPlacasMatriculacion.INICIO_SOLICITUD;
	}

	/**
	 * Gestiona los datos introducidos en pantalla para realizar la solicitud o solicitudes de placas
	 * @return solicitudRealizada
	 */
	public String boton_guardarSolicitud() {

		if (listaSolicitudes != null) {
			cargarCSSBasico();
			cargarJSBasico();

			comprobarConfiguracion();

			Integer idUsuario = Integer.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal()!=null ? utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() : null);
			Usuario usuario = placasMatriculacionService.getUsuario(idUsuario);

			/*
			 * Comprobamos si la solicitud existía en BBDD a través de su índice único (numExpediente + fechaSolicitud),
			 * para así asignarle su idSolicitud correspondiente (necesario para solicitudes recién guardadas, que aún no tenían id en el bean de pantalla,
			 * de tal modo que hibernate no intente realizar otro insert por encontrar idSolicitud nulo.
			 */
			for (int i = 0; i < listaSolicitudes.size(); i++){
				SolicitudPlacaBean spBean = listaSolicitudes.get(i);
				spBean.setMostrarSeccionPersona("false");
				spBean.setUsuario(usuario);
				spBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				spBean.setIdContrato(Integer.valueOf(utilesColegiado.getIdContratoSessionBigDecimal()!=null ? utilesColegiado.getIdContratoSessionBigDecimal().intValue() : null));

				if (usuario != null) {
					spBean.setNombreUsuario(usuario.getApellidosNombre());
				}

				SolicitudPlacaBean solicitud = placasMatriculacionService.getSolicitudPorClaveUnica(spBean);

				if (solicitud != null && solicitud.getEstado() != null) {
					if (solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Iniciado)) {
						spBean.setIdSolicitud(solicitud.getIdSolicitud());
					} else if (!solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Iniciado)) {
						listaSolicitudes.remove(spBean);
						i--;
						addActionError("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud guardada con la misma fecha.");
					}
				}
			}

			List<ValidacionPlacasBean> validar = (List<ValidacionPlacasBean>)placasMatriculacionService.realizarSolicitud(listaSolicitudes);

			for (ValidacionPlacasBean validarBean : validar) {
				if (validarBean.isError()) {
					for (String mensaje : validarBean.getMensajes()) {
						addActionError("Error al solicitar placas para " + validarBean.getMatricula() + ": " + mensaje);
						log.error("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
					}
				} else {
					if (listaSolicitudes.get(validarBean.getNoElemento()).getIdSolicitud() == null) {
						listaSolicitudes.get(validarBean.getNoElemento()).setIdSolicitud(validarBean.getIdSolicitud());
					}
					addActionMessage("Solicitud de placas para la matrícula " + validarBean.getMatricula() + " realizada correctamente");
				}
			}

		} else {
			addActionError("No se encontraron solicitudes.");
		}

		validarCreditos();

		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
	}

	/**
	 * Gestiona los datos introducidos en pantalla para realizar la solicitud o solicitudes de placas
	 * @return solicitudRealizada
	 */
	public String boton_confirmarSolicitud() {

		cargarCSSBasico();
		cargarJSBasico();

		if (listaSolicitudes != null) {
			comprobarConfiguracion();

			Integer idUsuario = Integer.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal()!=null ? utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() : null);
			Usuario usuario = placasMatriculacionService.getUsuario(idUsuario);
			/*
			 * Comprobamos si la solicitud existía en BBDD a través de su índice único (numExpediente + fechaSolicitud),
			 * para así asignarle su idSolicitud correspondiente (necesario para solicitudes recién guardadas, que aún no tenían id en el bean de pantalla,
			 * de tal modo que hibernate no intente realizar otro insert por encontrar idSolicitud nulo.
			 */
			for (int i = 0; i < listaSolicitudes.size(); i++){
				SolicitudPlacaBean spBean = listaSolicitudes.get(i);
				spBean.setUsuario(usuario);
				spBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
				if (usuario != null) {
					spBean.setNombreUsuario(usuario.getApellidosNombre());
				}
				SolicitudPlacaBean solicitud = placasMatriculacionService.getSolicitudPorClaveUnica(spBean);
				if(solicitud!=null && solicitud.getEstado()!=null){
					if(solicitud!=null && solicitud.getEstado()!=null && solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Iniciado)){
						spBean.setIdSolicitud(solicitud.getIdSolicitud());
						spBean.setEstado(EstadoSolicitudPlacasEnum.Confirmado.getValorEnum());
					} else if (!solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Iniciado)) {
						listaSolicitudes.remove(spBean);
						i--;
						addActionError("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud confirmada con la misma fecha.");
					}
				} else {
					addActionError("Debe guardar la solicitud antes de confirmarla.");
					return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
				}
			}

			ArrayList<ValidacionPlacasBean> validar = (ArrayList<ValidacionPlacasBean>)placasMatriculacionService.confirmarSolicitud(listaSolicitudes);

			for (ValidacionPlacasBean validarBean : validar) {
				// Si hay error lo mostramos
				if (validarBean.isError()) {
					// Buscamos la solicitud en la lista, y revertimos su estado a Iniciado
					for (String mensaje : validarBean.getMensajes()) {
						for (SolicitudPlacaBean solicitud : listaSolicitudes) {
							if(solicitud.getNumColegiado().equals(validarBean.getNumColegiado())
								&& solicitud.getMatricula().equals(validarBean.getMatricula())){
								solicitud.setEstado(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum());
							}
						}
						addActionError("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
						log.error("Error al confirmar solicitud para la matrícula " + validarBean.getMatricula() + ": " + mensaje);
					}
				// Si no hay error, enviamos correo de solicitud al colegio y mostramos mensaje de confirmación
				} else {
					for (SolicitudPlacaBean solicitud : listaSolicitudes) {
						if(validarBean.getNumColegiado().equals(solicitud.getNumColegiado())
								&& validarBean.getMatricula().equals(solicitud.getMatricula())){
							placasMatriculacionService.notificar(solicitud, false);
						}
					}
					addActionMessage("Solicitud de placas para la matrícula " + validarBean.getMatricula() + " confirmada correctamente");
				}
			}
			validarCreditos();
		} else {
			addActionError("No se encontraron solicitudes.");
		}
		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;

	}

	/**
	 * Cambia el estado de una solicitud
	 * @return solicitudRealizada
	 */
	public String boton_cambiarEstadoSolicitud() {
		cargarCSSBasico();
		cargarJSBasico();

		Integer idUsuario = Integer.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal() != null ? utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() : null);
		Usuario usuario = placasMatriculacionService.getUsuario(idUsuario);

		if (listaSolicitudes!= null) {
			for (SolicitudPlacaBean solicitudPlacaBean : listaSolicitudes) {
				solicitudPlacaBean.setEstado(estado);
				if (usuario != null) {
					solicitudPlacaBean.setNombreUsuario(usuario.getApellidosNombre());
				}
				solicitudPlacaBean.setUsuario(usuario);
			}

			if (placasMatriculacionService.cambiarEstadoSolicitud(listaSolicitudes, estado)) {
				addActionMessage("La solicitud ha cambiado correctamente a estado " + EstadoSolicitudPlacasEnum.convertirTexto(estado));
			} else {
				addActionError("Se produjo un error al cambiar la solicitud a estado " + EstadoSolicitudPlacasEnum.convertirTexto(estado));
			}
		} else {
			addActionError("No se encontraron solicitudes.");
		}
		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
	}

	/**
	 * Gestiona los datos introducidos en pantalla para realizar la solicitud o solicitudes de placas
	 * @return solicitudRealizada
	 */
	public String boton_finalizarSolicitud() {
		cargarCSSBasico();
		cargarJSBasico();
		Integer idUsuario = Integer.valueOf(utilesColegiado.getIdUsuarioSessionBigDecimal()!=null ? utilesColegiado.getIdUsuarioSessionBigDecimal().intValue() : null);
		Usuario usuario = placasMatriculacionService.getUsuario(idUsuario);

		if (listaSolicitudes != null) {
			comprobarConfiguracion();

			/*
			 * Comprobamos si la solicitud existía en BBDD a través de su índice único (numExpediente + fechaSolicitud),
			 * para así asignarle su idSolicitud correspondiente de tal modo que hibernate no intente realizar otro insert por encontrar idSolicitud nulo.
			 */
			for (int i = 0; i < listaSolicitudes.size(); i++) {
				SolicitudPlacaBean spBean = listaSolicitudes.get(i);

				spBean.setUsuario(usuario);
				spBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());

				if (usuario != null) {
					spBean.setNombreUsuario(usuario.getApellidosNombre());
					spBean.setNif(usuario.getNif());
				}

				SolicitudPlacaBean solicitud = placasMatriculacionService.getSolicitudPorClaveUnica(spBean);

				if (solicitud != null && solicitud.getEstado() != null) {
					if (solicitud != null && solicitud.getEstado() != null
							&& solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Tramitado)) {
						spBean.setIdSolicitud(solicitud.getIdSolicitud());
						spBean.setEstado(EstadoSolicitudPlacasEnum.Finalizado.getValorEnum());
					} else if (!solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Tramitado)) {
						addActionError("Se eliminó la solicitud para " + spBean.getMatricula() + " debido a que ya existía una solicitud confirmada con la misma fecha.");
						return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
					}
				}
			}
			for (SolicitudPlacaBean solicitud : listaSolicitudes){
				solicitud.setUsuario(usuario);
				solicitud.setNombreUsuario(usuario.getApellidosNombre());
			}
			HashMap<Long, Boolean> notificaciones = placasMatriculacionService.notificarVarias(listaSolicitudes, true);

			for (SolicitudPlacaBean spBean : listaSolicitudes) {
				boolean notificar = notificaciones.get(spBean.getNumExpediente());

				if (notificar) {
					ArrayList<ValidacionPlacasBean> finalizar = (ArrayList<ValidacionPlacasBean>) placasMatriculacionService.finalizarSolicitud(listaSolicitudes);

					for (ValidacionPlacasBean finalizarBean : finalizar) {

						if (finalizarBean.getNumColegiado().equals(spBean.getNumColegiado())
								&& finalizarBean.getMatricula().equals(spBean.getMatricula())) {

							// Si hay error lo mostramos, y revertimos el estado
							if (finalizarBean.isError()) {

								// Buscamos la solicitud en la lista, y revertimos su estado
								for (String mensaje : finalizarBean.getMensajes()) {
									for (SolicitudPlacaBean solicitud : listaSolicitudes) {
										if(finalizarBean.getNumColegiado().equals(spBean.getNumColegiado())
											&& finalizarBean.getMatricula().equals(spBean.getMatricula())){
											solicitud.setEstado(EstadoSolicitudPlacasEnum.Tramitado.getValorEnum());
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
				} else {
					addActionError("Error al notificar solicitud para la matrícula " + spBean.getMatricula() );
				}
			}

			validarCreditos();
		} else {
			addActionError("No se encontraron solicitudes.");
		}
		return ConstantesPlacasMatriculacion.SOLICITUD_REALIZADA;
	}

	/************************************************************************************funciones adicionales*/

	public String refrescar() {
		cargarCSSBasico();
		cargarJSBasico();

		mantenimientoCamposNavegar();

		String[] expedientes = numsExpedientes!=null ? numsExpedientes.split("-") : null;

		if (listaSolicitudes != null) {
			for (int i = 0; i < listaSolicitudes.size(); i++) {
				SolicitudPlacaBean spBean = listaSolicitudes.get(i);
				boolean mantener = false;
				for (String expediente : expedientes) {
					if (expediente.equals(String.valueOf(spBean.getNumExpediente()))) {
						mantener = true;
					}
				}
				if (!mantener) {
					listaSolicitudes.remove(spBean);
					i--;
				}
			}
		} else {
			addActionError(ConstantesPlacasMatriculacion.NO_SE_PUDO_RECUPERAR_NINGUN_EXPEDIENTE_PARA_CREAR_LA_SOLICITUD_DE_PLACAS);
		}

		validarCreditos();

		return ConstantesPlacasMatriculacion.REFRESCAR_SOLICITUD;
	}

	/**
	 * Comprueba si se marcó aplicar a todas las solicitudes la misma configuración, y en caso afirmativo, copia los atributos de la primera en el resto
	 */
	private void comprobarConfiguracion() {
		if (isAplicarTodas()) {
			SolicitudPlacaBean primera = listaSolicitudes.get(BigDecimal.ZERO.intValue());
			for (SolicitudPlacaBean spBean : listaSolicitudes) {
				spBean.setTipoPlacaDelantera(primera.getTipoPlacaDelantera() != null ? primera.getTipoPlacaDelantera().getValorEnum() : null);
				spBean.setTipoPlacaTrasera(primera.getTipoPlacaTrasera() != null ? primera.getTipoPlacaTrasera().getValorEnum() : null);
				spBean.setTipoPlacaAdicional(primera.getTipoPlacaAdicional() != null ? primera.getTipoPlacaAdicional().getValorEnum() : null);
			}
		}
	}

	public boolean isAplicarTodas() {
		return aplicarTodas;
	}
	/*******************************************************************************************************final de las acciones*/

	/**
	 * Método para actualizar los parámetros de paginación. Proceso:
	 * 
	 * -Recupero el bean de búsqueda de sesión.
	 * -Actualizo los parámetros de búsqueda.
	 * -Actualizo en sesión los de la paginación.
	 * 
	 */
	private void mantenimientoCamposNavegar() {
		ConsultaPlacasBean consultaPlacasBean = (ConsultaPlacasBean) getSession().get(ConstantesSession.CONSULTA_SOLICITUDES_PLACAS);
		if (consultaPlacasBean != null) {
			setConsultaPlacasBean(consultaPlacasBean);
		}
	}

	/**
	 * Carga en la plantilla la hoja de estilos adicional para las solicitudes de placas
	 */
	private void cargarCSSBasico() { // Se queda
		// Carga de CSS específico para placas de matriculación
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean css = new ScriptFeaturesBean();
		css.setName(ConstantesPlacasMatriculacion.PLACAS_MATR_CSS);
		css.setPosicion(TipoPosicionScript.TOP);
		css.setTipo(TipoScript.CSS);
		addScripts.getScripts().add(css);
		// Fin Carga de JavaScript específico para placas de matriculación
	}

	/**
	 * Carga en la plantilla las funciones de Javascript adicionales para las solicitudes de placas
	 */
	private void cargarJSBasico(){ // Se queda
		// Carga de JavaScript específico para placas de matriculación
		if (null == addScripts) {
			inicializarScripts();
		}
		ScriptFeaturesBean js = new ScriptFeaturesBean();
		js.setName(ConstantesPlacasMatriculacion.PLACAS1_MATR_JS_BOTTOM);
		js.setPosicion(TipoPosicionScript.BOTTOM);
		js.setTipo(TipoScript.JS);
		addScripts.getScripts().add(js);
		// Fin Carga de JavaScript específico para placas de matriculación
	}

	public boolean isDuplicate(ArrayList<SolicitudPlacaBean> lista) {
		for (int i = 0; i < lista.size() - 1; i++) {
			for (int j = i + 1; j < lista.size(); j++) {
				if (lista.get(i).getMatricula().equals(lista.get(j).getMatricula()))
					return true;
			}
		}
		return false;
	}

	private void validarCreditos() {
		String contratoSesion = null != utilesColegiado.getIdContratoSessionBigDecimal() ? utilesColegiado.getIdContratoSessionBigDecimal().toString() : null;

		TipoSolicitudPlacasEnum[] tiposSolicitudes = TipoSolicitudPlacasEnum.values();

		for (TipoSolicitudPlacasEnum tipoSolicitud : tiposSolicitudes) {

			Map<String, Object> resultadoModelo = getModeloCreditosTrafico().validarCreditosPorNumColegiado(
				contratoSesion,
				new BigDecimal(1),
				tipoSolicitud.getValorEnum());

			switch (tipoSolicitud) {
				case Solicitud_Placa_Ordinaria_Larga:
					setNumCreditosDisponiblesCTP1((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ordinaria_Corta_Alfa_Romeo:
					setNumCreditosDisponiblesCTP2((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Motocicleta:
					setNumCreditosDisponiblesCTP3((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Motocicleta_Trial:
					setNumCreditosDisponiblesCTP4((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Vehiculo_Especial:
					setNumCreditosDisponiblesCTP5((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_Ciclomotor:
					setNumCreditosDisponiblesCTP6((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
				case Solicitud_Placa_TaxiVTC:
					setNumCreditosDisponiblesCTP6((BigDecimal) resultadoModelo.get(ConstantesCreditos.CREDITOS_DISPONIBLES));
					break;
			}
		}
	}

	/********************************getters & setters*/

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ArrayList<SolicitudPlacaBean> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(ArrayList<SolicitudPlacaBean> listaSolicitudes) {
		this.listaSolicitudes=listaSolicitudes;
	}

	public String getListaExpedientes() {
		return listaExpedientes;
	}

	public void setListaExpedientes(String listaExpedientes) {
		this.listaExpedientes = listaExpedientes;
	}

	public String getNumsExpedientes() {
		return numsExpedientes;
	}

	public void setNumsExpedientes(String numsExpedientes) {
		this.numsExpedientes = numsExpedientes;
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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsario) {
		this.idUsuario = idUsario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ConsultaPlacasBean getConsultaPlacasBean() {
		return consultaPlacasBean;
	}

	public void setConsultaPlacasBean(ConsultaPlacasBean consultaPlacasBean) {
		this.consultaPlacasBean = consultaPlacasBean;
	}

	public String getSelSolicitud() {
		return selSolicitud;
	}

	public void setSelSolicitud(String selSolicitud) {
		this.selSolicitud = selSolicitud;
	}
}