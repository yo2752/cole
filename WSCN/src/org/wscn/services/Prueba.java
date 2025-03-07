package org.wscn.services;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.gi.infra.wscn.pruebas.ws.WSCNPruebasExcepcionSistema;

import utilidades.web.OegamExcepcion;

public class Prueba {

	private static final String URL = "https://ws.seg-social.gob.es/INFRWSCN_Pruebas/WSCNPruebasService?wsdl";
	private static final String NAMESPACE_URI = "http://ws.pruebas.wscn.infra.gi.org/";
	private static final String LOCAL_PART = "WSCNPruebas";

	private static final int ROL_TODOS = 0;
	private static final int ROL_PROPIO = 1;
	private static final int ROL_AUTORIZADO_RED = 2;
	private static final int ROL_APODERADO = 3;
	private String javax_net_ssl_trustStore;
	private String javax_net_ssl_trustStorePassword;
	private String javax_net_sslkeyStore;
	private String javax_net_ssl_keyStorePassword;

	/*
	 * "javax.net.ssl.trustStore"
"javax.net.ssl.trustStorePassword"
"javax.net.ssl.keyStore"
"javax.net.ssl.keyStorePassword"
	 */

	private static final String JAVAX_NET_SSL_TRUSTSTORE = "javax.net.ssl.trustStore";
	private static final String JAVAX_NET_SSL_TRUSTSTOREPASSWORD = "javax.net.ssl.trustStorePassword";
	private static final String JAVAX_NET_SSL_KEYSTORE = "javax.net.ssl.keyStore";
	private static final String JAVAX_NET_SSL_KEYSTOREPASSWORD = "javax.net.ssl.keyStorePassword";

	//private UtilesViafirma utilesViafirma;

	public void cargarAlmacenesTrafico() throws OegamExcepcion {

		 /*
		  * "trafico.trustStore"
"trafico.passTrustStore"
"trafico.keyStore"
"trafico.passKeyStore"
		  */
		javax_net_ssl_trustStore = "trafico.trustStore";
		javax_net_ssl_trustStorePassword = "trafico.passTrustStore";
		javax_net_sslkeyStore = "trafico.keyStore";
		javax_net_ssl_keyStorePassword = "trafico.passKeyStore";

		System.setProperty(JAVAX_NET_SSL_TRUSTSTORE,
				//javax_net_ssl_trustStore
				"C:\\datos\\oegam\\keystore\\clavesPublicas.jks"
				);

		System.setProperty(JAVAX_NET_SSL_TRUSTSTOREPASSWORD, 
				//javax_net_ssl_trustStorePassword
				"123456"
				);

		System.setProperty(JAVAX_NET_SSL_KEYSTORE, 
				//javax_net_sslkeyStore
				"C:\\datos\\oegam\\keystore\\colegioGestores.jks"
				);

		System.setProperty(JAVAX_NET_SSL_KEYSTOREPASSWORD,
				//javax_net_ssl_keyStorePassword
				"admin123456"
				);
	}

	public Prueba() throws WSCNPruebasExcepcionSistema, RemoteException, ServiceException {
//		try {
//			cargarAlmacenesTrafico();
//		} catch (OegamExcepcion e) {
//			e.printStackTrace();
//		}
//		WSCNPruebasServiceLocator locator = new WSCNPruebasServiceLocator();
//
//		AcuseNotificacion acuse = locator.getWSCNPruebasPort()
//				.solicitarAcuseNotificacion(ROL_PROPIO, null, 36, true);
//		String idFirma = getUtilesViafirma().firmarConsultaTarjetaEitv(acuse.getXML(),"2014C2668");
//		idFirma= getUtilesViafirma().firmarPruebaCertificadoCaducidad(acuse.getXML().toString(),"2014C2668");
//		byte[] txtFirmado = getUtilesViafirma().getBytesDocumentoFirmado(idFirma);
//
//		NotificacionRecuperada notificacion = locator.getWSCNPruebasPort().enviarAcuseNotificacion(ROL_PROPIO, null, txtFirmado);
//
//		System.out.println(notificacion);
	}

	public static void main(String[] args) throws WSCNPruebasExcepcionSistema, RemoteException, ServiceException {
		Prueba prueba = new Prueba();
	}

//	public UtilesViafirma getUtilesViafirma() {
//		if(utilesViafirma == null){
//			utilesViafirma = new UtilesViafirma();
//		}
//		return utilesViafirma;
//	}

}