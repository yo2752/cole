package org.gestoresmadrid.oegam2comun.wscn.model.service.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.core.notificacion.model.vo.IdNumColegiadoPK;
import org.gestoresmadrid.core.notificacion.model.vo.NotificacionSSVO;
import org.gestoresmadrid.core.notificacion.model.vo.RespuestaSSVO;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacion;
import org.gestoresmadrid.oegam2comun.notificacion.model.service.ServicioConsultaNotificacionTransactional;
import org.gestoresmadrid.oegam2comun.wscn.model.dto.RespuestaNotificacionesSS;
import org.gestoresmadrid.oegam2comun.wscn.model.handler.NotificacionesSecurityClientHandler;
import org.gestoresmadrid.oegam2comun.wscn.model.handler.SoapNotificacionesSSFilesHandler;
import org.gestoresmadrid.oegam2comun.wscn.model.service.ServicioWebServiceConsultaNotificaciones;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gi.infra.wscn.pruebas.ws.AcuseNotificacion;
import org.gi.infra.wscn.pruebas.ws.ListadoNotificaciones;
import org.gi.infra.wscn.pruebas.ws.ListadoPoderdantes;
import org.gi.infra.wscn.pruebas.ws.Notificacion;
import org.gi.infra.wscn.pruebas.ws.NotificacionRecuperada;
import org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;
import org.gi.infra.wscn.pruebas.ws.WSCNPruebasServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioWebServiceConsultaNotificacionesImpl extends org.apache.axis.client.Service implements
		ServicioWebServiceConsultaNotificaciones {
	
	private static final long serialVersionUID = -1524958344841187798L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceConsultaNotificacionesImpl.class);
	
	private final static String SERVICIO_SEGURIDAD_SOCIAL_URL = "servicio.seguridad.social.url";
	
	@Autowired
	private ServicioConsultaNotificacion servicioConsultaNotificacion;
	
	@Autowired
	private ServicioConsultaNotificacionTransactional consultaNotificacionTransactional;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	public RespuestaNotificacionesSS aceptarNotificaciones(String datosNotificaciones, String alias) {
		//Recorremos el xmlEnviar (split) y solicitamos acuse, firmamos y enviamos acuse.
		RespuestaNotificacionesSS respuesta = null;
		String[] notificacion = datosNotificaciones.split("-");
		
		for (int i = 0; i < notificacion.length; i++) {
			String[] valoresNotif = notificacion[i].split(",");
			respuesta = servicioConsultaNotificacion.aceptarNotificaciones(valoresNotif[0], valoresNotif[1], alias, valoresNotif[3]);
			if (respuesta != null){
				return respuesta;
			}
		}
		
		return null;
	}


	@Override
	public RespuestaNotificacionesSS rechazarNotificaciones(String datosNotificaciones, String alias) {
		//Recorremos el xmlEnviar (split) y enviamos la solictud de acuse con el campo en false
		RespuestaNotificacionesSS respuesta = null;
		String[] notificacion = datosNotificaciones.split("-");
		
		for (int i = 0; i < notificacion.length; i++) {
			String[] valoresNotif = notificacion[i].split(",");
			respuesta = servicioConsultaNotificacion.rechazarNotificaciones(valoresNotif[0], valoresNotif[1], alias, valoresNotif[3]);
			if (respuesta != null){
				return respuesta;
			}
		}
		
		return null;
	}


	@Override
	public RespuestaNotificacionesSS imprimirNotificaciones(String datosNotificaciones, String alias) {
		//Recorremos el xmlEnviar (split) primero comprobamos si tenemos el PDF custodiado, sino llamamos al WS
		//Solamente están disponibles para descargar las notificaciones por aceptación. Estado - 2
		RespuestaNotificacionesSS respuesta = null;
		String[] notificacion = datosNotificaciones.split("-");
		
		for (int i = 0; i < notificacion.length; i++) {
			String[] valoresNotif = notificacion[i].split(",");
			respuesta = servicioConsultaNotificacion.imprimirNotificaciones(valoresNotif[0], valoresNotif[1], alias, valoresNotif[3]);
			if (respuesta != null){
				return respuesta;
			}
		}
		
		return null;
	}
	
	@Override
	public RespuestaNotificacionesSS recuperaListadoNotificacionesByRol(int rol,
			String apoderdante, String alias, BigDecimal numColegiado) {
		RespuestaNotificacionesSS respuesta = new RespuestaNotificacionesSS();
		List<NotificacionSSVO> listaNotificacionSS = new ArrayList<NotificacionSSVO>();
		List<RespuestaSSVO> listaRespuestaNotificacionSS = new ArrayList<RespuestaSSVO>();
		List<Notificacion> listaAuxiliarNotificacion = new ArrayList<Notificacion>();
		int siguienteNotificacionPropia = 0;
		int siguienteNotificacionAutorizadoRED = 0;
		int siguienteNotificacionApoderado = 0;

		/*
		 * 1.- Según que rol sea creamos la llamada 2.- Creamos un bucle que
		 * vaya llamando hasta que no existan más notificaciones (flag hayMas)
		 * 3.- Guardamos las notificaciones de respuesta en
		 * listadeNotificaciones hasta que salgamos del bucle 4.- Insertamos.
		 */

		NotificacionSSVO ultima = null;
		
		try {

			if (rol == 1) {
				siguienteNotificacionPropia = consultaNotificacionTransactional.getUltimaNotificacionByRolNumColegiado(rol, numColegiado);
				ultima = consultaNotificacionTransactional.getNotificacionByIdNumColegiado(siguienteNotificacionPropia, numColegiado.toString());
			} else if (rol == 2) {
				siguienteNotificacionAutorizadoRED = consultaNotificacionTransactional.getUltimaNotificacionByRolNumColegiado(rol, numColegiado);
				ultima = consultaNotificacionTransactional.getNotificacionByIdNumColegiado(siguienteNotificacionAutorizadoRED, numColegiado.toString());
			} else if (rol == 3) {
				siguienteNotificacionApoderado = consultaNotificacionTransactional.getUltimaNotificacionByRolNumColegiado(rol, numColegiado);
				ultima = consultaNotificacionTransactional.getNotificacionByIdNumColegiado(siguienteNotificacionApoderado, numColegiado.toString());
			}
	
			boolean hayMas = true;
			while (hayMas) {
				ListadoNotificaciones listado;
					listado = initialize(alias).getWSCNPruebasPort()
							.consultarListadoNotificaciones(rol, apoderdante==null?new String():apoderdante,
									siguienteNotificacionPropia,
									siguienteNotificacionAutorizadoRED,
									siguienteNotificacionApoderado);
				if (rol == 1) {
					if (listado.getNotificacionesPropias() != null) {
						for (Notificacion notificacion : listado
								.getNotificacionesPropias()) {
							listaAuxiliarNotificacion.add(notificacion);
						}
						siguienteNotificacionPropia = listado
								.getCodigoSiguienteNotificacionPropia();
					}
				} else if (rol == 2) {
					if (listado.getNotificacionesAutorizadoRED() != null) {
						for (Notificacion notificacion : listado
								.getNotificacionesAutorizadoRED()) {
							listaAuxiliarNotificacion.add(notificacion);
						}
						siguienteNotificacionAutorizadoRED = listado
								.getCodigoSiguienteNotificacionAutorizadoRED();
					}
				} else if (rol == 3) {
					if (listado.getNotificacionesApoderado() != null) {
						for (Notificacion notificacion : listado
								.getNotificacionesApoderado()) {
							listaAuxiliarNotificacion.add(notificacion);
						}
						siguienteNotificacionApoderado = listado
								.getCodigoSiguienteNotificacionApoderado();
					}
				}
				hayMas = listado.isHayMas();
			}
			listaNotificacionSS = crearListaNotificacionSS(
					listaAuxiliarNotificacion, rol, numColegiado);
			listaNotificacionSS.remove(ultima);
	
			consultaNotificacionTransactional.insertarNotificacion(listaNotificacionSS);
			
			listaRespuestaNotificacionSS = crearListaRespuestaNotificacion(listaNotificacionSS);
			consultaNotificacionTransactional.insertarResultadoNotificacion(listaRespuestaNotificacionSS);
			
			respuesta.setListaNotificacionSS(listaNotificacionSS);
			
		} catch (WSCNPruebasExcepcionSistema e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (RemoteException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (ServiceException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		}

		return respuesta;
	}
	
	@Override
	public RespuestaNotificacionesSS recuperaNotificaciones(String alias, BigDecimal idUsuario, BigDecimal idContrato){
		
		RespuestaNotificacionesSS respuesta = null;
		String numColegiado = utilesColegiado.getNumColegiadoByIdUsuario(idUsuario);
		respuesta = servicioConsultaNotificacion.recuperarNotificaciones(alias, new BigDecimal(numColegiado), idContrato);
		if (respuesta != null){
			return respuesta;
		}
		
		return null;

	}
	
	private List<NotificacionSSVO> crearListaNotificacionSS(
			List<Notificacion> lista, int rol, BigDecimal numColegiado) {
		List<NotificacionSSVO> listaNotificacionSS = new ArrayList<NotificacionSSVO>();
		for (Notificacion notificacion : lista) {
			NotificacionSSVO notificacionSS = parseNotificacion(notificacion,
					rol, numColegiado);
			listaNotificacionSS.add(notificacionSS);
		}
		return listaNotificacionSS;
	}

	private NotificacionSSVO parseNotificacion(Notificacion notificacion,
			int rol, BigDecimal numColegiado) {
		NotificacionSSVO notificacionSS = new NotificacionSSVO();

		IdNumColegiadoPK id = new IdNumColegiadoPK();
		id.setCodigo(notificacion.getCodigo());
		id.setNumColegiado(numColegiado.toString());
		notificacionSS.setId(id);
		notificacionSS
				.setDescripcionEstado(notificacion.getDescripcionEstado());
		notificacionSS.setDescripcionTipoDestinatario(notificacion
				.getDescripcionTipoDestinatario());
		notificacionSS.setDestinatario(notificacion.getDestinatario());
		notificacionSS.setEstado(notificacion.getEstado());
		notificacionSS
				.setFechaFinDisponibilidad(convertirStringtoDate(notificacion
								.getFechaFinDisponibilidad()));
		notificacionSS
				.setFechaPuestaDisposicion(convertirStringtoDate(notificacion
								.getFechaPuestaDisposicion()));
		notificacionSS.setNombreAppRazonSocial(notificacion
				.getNombreAppRazonSocial());
		notificacionSS.setProcedimiento(notificacion.getProcedimiento());
		notificacionSS.setTipoDestinatario(notificacion.getTipoDestinatario());
		notificacionSS.setRol(rol);

		return notificacionSS;
	}
	
	private List<RespuestaSSVO> crearListaRespuestaNotificacion(List<NotificacionSSVO> listaNotificacion){
		List<RespuestaSSVO> listaRespuestaNotificacion = new ArrayList<RespuestaSSVO>();
		for (NotificacionSSVO notificacion : listaNotificacion) {
			RespuestaSSVO respuesta = new RespuestaSSVO();
			respuesta.setNotificacion(notificacion);
			respuesta.setEstado("Notificación creada");
			respuesta.setFechaNotificacion(new Date());
			listaRespuestaNotificacion.add(respuesta);
		}
		return listaRespuestaNotificacion;
	}
	
	@Override
	public RespuestaNotificacionesSS solicitarAcuseNotificacion(int rol,
			String identificadorPoderdante, int codigoNotificacion,
			boolean esDeAceptacion, String alias){

		RespuestaNotificacionesSS respuesta = new RespuestaNotificacionesSS();
		AcuseNotificacion acuse = null;
		
		try {
			acuse = initialize(alias).getWSCNPruebasPort()
					.solicitarAcuseNotificacion(rol, identificadorPoderdante==null?new String():identificadorPoderdante,
							codigoNotificacion, esDeAceptacion);
			
			respuesta.setAcuse(acuse);
			
		} catch (WSCNPruebasExcepcionSistema e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (RemoteException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (ServiceException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		}

		return respuesta;
	}

	@Override
	public RespuestaNotificacionesSS enviarAcuseNotificacion(int rol,
			String identificadorPoderdante, byte[] xmlAcuseFirmado, String alias) {

		RespuestaNotificacionesSS respuesta = new RespuestaNotificacionesSS();
		NotificacionRecuperada recuperada = null;
		try {
			recuperada = initialize(alias).getWSCNPruebasPort()
					.enviarAcuseNotificacion(rol, identificadorPoderdante==null?new String():identificadorPoderdante,
							xmlAcuseFirmado);
			
			respuesta.setNotificacion(recuperada);
			
		} catch (WSCNPruebasExcepcionSistema e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (RemoteException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (ServiceException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		}

		return respuesta;
	}
	
	@Override
	public RespuestaNotificacionesSS verNotificacionAceptada(int rol, String identificadorPoderdante, int codigoNotificacion, String alias){
		
		NotificacionRecuperada recuperada = null;
		RespuestaNotificacionesSS respuesta = new RespuestaNotificacionesSS();
		try {
			recuperada = initialize(alias).getWSCNPruebasPort().verNotificacionAceptada(rol, identificadorPoderdante==null?new String():identificadorPoderdante, codigoNotificacion);
			
			respuesta.setNotificacion(recuperada);
			
		} catch (WSCNPruebasExcepcionSistema e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (RemoteException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (ServiceException e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaNotificaciones.NOTIFICACIONES_SS_ERROR_LOG + " EXCEPCION: " + e);
			respuesta = new RespuestaNotificacionesSS();
			respuesta.setException(e);
		}

		return respuesta;
	}

	@Override
	public ListadoPoderdantes solicitarPoderdantes(String alias) {
		ListadoPoderdantes listado = null;
		try {
			listado = initialize(alias).getWSCNPruebasPort().solicitarPoderdantes();
		} catch (WSCNPruebasExcepcionSistema e) {
			log.error("Error al solicitar poderdantes: "+ e);
		} catch (RemoteException e) {
			log.error("Error al solicitar poderdantes: "+ e);
		} catch (ServiceException e) {
			log.error("Error al solicitar poderdantes: "+ e);
		} catch (Exception e) {
			log.error("Error al solicitar poderdantes: "+ e);
		}
		return listado;
	}
	
	/**
	 * Inicializa el cliente del WS
	 * @return
	 * @throws ServiceException
	 */
	private WSCNPruebasServiceLocator initialize(String alias) {
		String address = gestorPropiedades.valorPropertie(SERVICIO_SEGURIDAD_SOCIAL_URL);
		if (log.isDebugEnabled()) {
			log.info("Inicializando cliente del WS de notificaciones seguridad social con la URL " + address);
		}

		WSCNPruebasServiceLocator pruebasServiceLocator = new WSCNPruebasServiceLocator();
		pruebasServiceLocator.setWSCNPruebasPortEndpointAddress(address);

		// Añadimos los handlers de firmado y logs
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(address, pruebasServiceLocator.getWSCNPruebasPortWSDDServiceName());
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = pruebasServiceLocator.getHandlerRegistry().getHandlerChain(portName);
		list.add(getSignerHandler(alias));
		list.add(getFilesHandler());

		return pruebasServiceLocator;
	}


	private HandlerInfo getSignerHandler(String alias) {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(NotificacionesSecurityClientHandler.ALIAS_KEY, alias);
		// Handler de firmado
		HandlerInfo signerHandlerInfo = new HandlerInfo();
		signerHandlerInfo.setHandlerClass(NotificacionesSecurityClientHandler.class);
		signerHandlerInfo.setHandlerConfig(config);
		
		return signerHandlerInfo;
	}
	
	private HandlerInfo getFilesHandler() {

		// Handler de logs
		HandlerInfo filesHandlerInfo = new HandlerInfo();
		filesHandlerInfo.setHandlerClass(SoapNotificacionesSSFilesHandler.class);

		return filesHandlerInfo;
	}
	
	private static Date convertirStringtoDate(String fecha){
		Date date = null;
		try {
			if(null != fecha){
				date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(fecha);
			}
		} catch (ParseException e) {
			return null;
		}
		
		return date;
	}
	
	
}