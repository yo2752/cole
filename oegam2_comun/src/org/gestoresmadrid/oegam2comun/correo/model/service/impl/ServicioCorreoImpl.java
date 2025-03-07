package org.gestoresmadrid.oegam2comun.correo.model.service.impl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import utiles.correo.FicheroAdjunto;
import utiles.correo.objetos.CorreoRequest;
import utiles.correo.objetos.CorreoResponse;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioCorreoImpl implements ServicioCorreo{

	private static final long serialVersionUID = -317299097783120946L;
	private static final String PROPERTY_URL_CORREO = "gestorcorreo.url";

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ResultBean enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos) throws OegamExcepcion, IOException {
		ResultBean resultBean = null;

		// Seteo de la clase Correo con los parámetros
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos=new ArrayList<>();
		if (adjuntos != null) {
			ficherosAdjuntos = dameAdjuntos(adjuntos);
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		resultBean=invocarRest(correo, "enviarCorreo");

		return resultBean;
	}

	@Override
	public ResultBean enviarNotificacion(String texto, Boolean textoPlano, String from, String subject,
			String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos)
			throws OegamExcepcion, IOException {
		ResultBean resultBean = null;

		// Seteo de la clase Correo con los parámetros
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos=new ArrayList<>();
		if (adjuntos != null) {
			ficherosAdjuntos = dameAdjuntos(adjuntos);
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		resultBean=invocarRest(correo, "enviarNotificacion");

		return resultBean;
	}

	@Override
	public ResultBean enviarCorreoDuplicados(String texto, Boolean textoPlano, String from, String subject,
			String recipient, String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos)
			throws OegamExcepcion, IOException {
		ResultBean resultBean = null;

		// Seteo de la clase Correo con los parámetros
		CorreoRequest correo=new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos = new ArrayList<>();
		if (adjuntos != null) {
			ficherosAdjuntos = dameAdjuntos(adjuntos);
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		resultBean=invocarRest(correo, "enviarCorreoDuplicados");

		return resultBean;
	}

	@Override
	public ResultBean enviarCorreoBajas(String texto, Boolean textoPlano, String from, String subject, String recipient,
			String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos)
			throws OegamExcepcion, IOException {
		ResultBean resultBean = null;

		//Seteo de la clase Correo con los parámetros
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos=new ArrayList<>();
		if (adjuntos != null) {
			ficherosAdjuntos = dameAdjuntos(adjuntos);
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		resultBean=invocarRest(correo, "enviarCorreoBajas");

		return resultBean;
	}

	@Override
	public ResultBean enviarCorreoNotificacionesEni(String texto, Boolean textoPlano, String from, String subject, String recipient,
			String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos)
			throws OegamExcepcion, IOException {

		ResultBean resultBean = null;

		//Seteo de la clase Correo con los parametros
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos = new ArrayList<>();
		if (adjuntos != null) {
			ficherosAdjuntos = dameAdjuntos(adjuntos);
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		resultBean=invocarRest(correo,"enviarCorreoNotificacionesEni");

		return resultBean;
	}

	@Override
	public ResultBean enviarCorreoInciJptm(String texto, Boolean textoPlano, String from, String subject, String recipient,
			String copia, String direccionesOcultas, String origen, FicheroBean... adjuntos)
			throws OegamExcepcion, IOException {
		ResultBean resultBean = null;

		//Seteo de la clase Correo con los parametros
		CorreoRequest correo = new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos=new ArrayList<>();
		if (adjuntos != null) {
			ficherosAdjuntos = dameAdjuntos(adjuntos);
		}
		correo.setOrigen(origen);
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(textoPlano);

		resultBean = invocarRest(correo, "enviarCorreoInciJptm");

		return resultBean;
	}

	private ResultBean invocarRest(CorreoRequest correo, String metodo){
		String url = gestorPropiedades.valorPropertie(PROPERTY_URL_CORREO) + metodo;
		ResultBean respuestaBean = new ResultBean(false);
		try {
			fixUntrustCertificate();
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
			Client client = Client.create(clientConfig);
			WebResource webResource = client
					.resource(url);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, correo);
			/*TODO Más adelante se implementará correctamente el cliente y el servidor para devolver el objeto ResultBean directamente
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			*/
			CorreoResponse respuestaCadena = response.getEntity(CorreoResponse.class);

			respuestaBean.setError(respuestaCadena.getError());
			respuestaBean.setListaMensajes(respuestaCadena.getListaMensajes());
		} catch (Exception e) {
			respuestaBean.setError(true);
			respuestaBean.setMensaje(e.getMessage());
			e.printStackTrace();
		}

		return respuestaBean;
	}

	private List<FicheroAdjunto> dameAdjuntos(FicheroBean[] adjuntos) throws IOException {
		List<FicheroAdjunto> ficherosAdjuntos = new ArrayList<>();

		for (FicheroBean adjunto: adjuntos) {
			FicheroAdjunto fichero = new FicheroAdjunto();
			if (adjunto.getFichero()!=null){
				fichero.setFicheroByte(org.apache.commons.io.FileUtils.readFileToByteArray(adjunto.getFichero()));
			} else if (adjunto.getFicheroByte() != null) {
				fichero.setFicheroByte(adjunto.getFicheroByte());
			}

			if (adjunto.getNombreYExtension() != null) {
				fichero.setNombreDocumento(adjunto.getNombreYExtension());
			} else if (adjunto.getNombreDocumento() != null) {
				fichero.setNombreDocumento(adjunto.getNombreDocumento());
			} else {
				fichero.setNombreDocumento("default");
			}
			if (fichero.getFicheroByte() != null) {
				ficherosAdjuntos.add(fichero);
			}
		}
		return ficherosAdjuntos;
	}

	public void fixUntrustCertificate() throws KeyManagementException, NoSuchAlgorithmException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		// set the allTrusting verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

}