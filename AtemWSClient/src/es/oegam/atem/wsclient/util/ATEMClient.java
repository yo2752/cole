package es.oegam.atem.wsclient.util;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.trafico.servicios.vehiculos.atem.ConsultaWS;
import es.trafico.servicios.vehiculos.atem.ConsultaWSService;
import es.trafico.servicios.vehiculos.atem.ConsultaWSServiceLocator;
import es.trafico.servicios.vehiculos.atem.SoapLoggingHandler;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.ws.SoapXMLDSignerHandler;

public class ATEMClient {
	private static ILoggerOegam log = LoggerOegam.getLogger("ProcesoAtem");
	private static final String INTEVE_MASIVO_URL = "atem.service.inteve.url";

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	private ConsultaWS consultaWS;

	/**
	 * Instancia el cliente que se comunica con el webservice de ATEM, la url
	 * del servicio lo toma de las properties, en la propiedad
	 * "atem.service.inteve.url". La ruta del almacen de claves, certificado,
	 * password... la toma de los properties
	 * 
	 * @throws ServiceException
	 * @throws OegamExcepcion
	 */
	public ATEMClient() throws ServiceException, OegamExcepcion {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		// Se recupera de properties la dirección del servicio
		String address = gestorPropiedades.valorPropertie(INTEVE_MASIVO_URL);

		if (log.isDebugEnabled()) {
			log.debug("URL del servicio ATEM: " + address);
		}

		ConsultaWSService consultaWSService = new ConsultaWSServiceLocator();
		((ConsultaWSServiceLocator) consultaWSService).setConsultaWSEndpointAddress(address);

		// Configuración del almacén de claves y certificado a usar
		
		String keystoreLocation = gestorPropiedades.valorPropertie("security.keystore.location");
		String keystoreType = gestorPropiedades.valorPropertie("security.keystore.type");
		String keystorePassword = gestorPropiedades.valorPropertie("security.keystore.password");
		String keystoreCertAlias = gestorPropiedades.valorPropertie("security.keystore.cert.alias");
		String keystoreCertPassword = gestorPropiedades.valorPropertie("security.keystore.cert.password");

		Map<String,String> securityConfig = new HashMap<String, String>();
		securityConfig.put("keystoreLocation", keystoreLocation);
		securityConfig.put("keystoreType", keystoreType);
		securityConfig.put("keystorePassword", keystorePassword);
		securityConfig.put("keystoreCertAlias", keystoreCertAlias);
		securityConfig.put("keystoreCertPassword", keystoreCertPassword);

		// Añadimos los handlers
		javax.xml.namespace.QName portName = new javax.xml.namespace.QName(address, "ConsultaWS");
		@SuppressWarnings("unchecked")
		List<HandlerInfo> list = consultaWSService.getHandlerRegistry().getHandlerChain(portName);
		// Handler de firmado
		HandlerInfo securityHandlerInfo = new HandlerInfo();
		securityHandlerInfo.setHandlerClass(SoapXMLDSignerHandler.class);
		securityHandlerInfo.setHandlerConfig(securityConfig);
		list.add(securityHandlerInfo);
		// Handler de logs
		HandlerInfo logHandlerInfo = new HandlerInfo();
		logHandlerInfo.setHandlerClass(SoapLoggingHandler.class);
		list.add(logHandlerInfo);

		consultaWS = consultaWSService.getConsultaWS();
	}

	/**
	 * Realiza la petición de consulta unitaria por bastidor al WS de ATEM, devuelve
	 * el XML de respuesta
	 * 
	 * @param tasa
	 * @param bastidor
	 * @return
	 * @throws RemoteException
	 */
	public String consultaBastidor(String tasa, String bastidor) throws RemoteException {
		return consultaWS.consultaBastidor(tasa, bastidor);
	}

	/**
	 * Realiza la petición de consulta unitaria por matricula al WS de ATEM, devuelve
	 * el XML de respuesta
	 * 
	 * @param tasa
	 * @param matricula
	 * @return
	 * @throws RemoteException
	 */
	public String consultaMatricula(String tasa, String matricula) throws RemoteException {
		return consultaWS.consultaMatricula(tasa, matricula);
	}

	/**
	 * Realiza la petición de consulta de persona al WS de ATEM, devuelve
	 * el XML de respuesta. Esta funcionalidad no está contemplada para oegam.
	 * 
	 * @param tasa
	 * @param persona
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String consultaPersona(String tasa, String persona) throws RemoteException {
		return consultaWS.consultaPersona(tasa, persona);
	}

	/**
	 * Realiza la petición de consulta masiva al WS de ATEM, devuelve
	 * el XML de respuesta
	 * 
	 * @param tipo: "Matricula" o "Bastidor"
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String consultaMasiva(String tipo, String xml) throws RemoteException {
		return consultaWS.consultaMasiva(tipo, xml);
	}

	/**
	 * Realiza la petición de consulta por referencia al WS de ATEM, devuelve
	 * el XML de respuesta
	 * 
	 * @param referencia
	 * @return
	 * @throws RemoteException
	 */
	public String obtenerResultadoConsulta(String referencia) throws RemoteException {
		return consultaWS.obtenerResultadoConsulta(referencia);
	}

}
