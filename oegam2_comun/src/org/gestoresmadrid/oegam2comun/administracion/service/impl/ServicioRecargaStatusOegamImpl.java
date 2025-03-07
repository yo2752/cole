package org.gestoresmadrid.oegam2comun.administracion.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaStatusOegam;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
@Transactional
public class ServicioRecargaStatusOegamImpl implements ServicioRecargaStatusOegam {

	private static final long serialVersionUID = -230407573965563020L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioRecargaStatusOegamImpl.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public void refrescarStatus() {
		List<String> listaIPFrontales = recuperarIPFrontalesActivos();
		ResultBean resultado = new ResultBean();
		for (String ipFrontal : listaIPFrontales) {
			try {
				sendPost(ipFrontal, URL_RECARGA_STATUS_OEGAM);
			} catch (Exception e) {
				log.error("El frontal " + ipFrontal + " no se pudo refrescar: " + e.getMessage());
				resultado.addMensajeALista(ipFrontal);
			}
		}

		if (listaIPFrontales.size() == resultado.getListaMensajes().size()) {
			resultado.setError(true);
		}
	}

	private List<String> recuperarIPFrontalesActivos() {
		return Arrays.asList(gestorPropiedades.valorPropertie(PROPERTY_FRONTALES_ACTIVOS).split(";"));
	}

	private void sendPost(String ip, String action) throws Exception {
		String url = "https://" + ip + "/oegam2" + action;

		final HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(final String arg0, final SSLSession arg1) {
				return true;
			}
		};

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setHostnameVerifier(hv);
		// Optional default is GET
		con.setRequestMethod("POST");

		// Add request header ¿?
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		log.info("\nSending 'POST' request to URL : " + url);
		log.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		con.disconnect();

		// Print result
		if ((!responseMessage.equalsIgnoreCase("ok"))) {
			throw new Exception();
		}
	}
}
