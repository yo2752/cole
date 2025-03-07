package org.gestoresmadrid.procesos.model.jobs.circularesOegam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.gestoresmadrid.oegam2comun.circular.model.service.ServicioCircular;
import org.gestoresmadrid.oegam2comun.circular.view.bean.ResultadoCircularBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.procesos.constantes.ConstantesProcesos;
import org.gestoresmadrid.procesos.model.jobs.AbstractProcesoBase;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesoCircularesOegam extends AbstractProcesoBase {

	private static ILoggerOegam Log = LoggerOegam.getLogger(ProcesoCircularesOegam.class);
	private static final String PROPERTY_FRONTALES_ACTIVOS = "frontales.activos.ip";
	private static final String URL_RECARGA_PROPERTIES_OEGAM = "/recargaPropertiesOegamServlet";

	@Autowired
	ServicioCircular servicioCircular;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	
	@Override
	protected String getProceso() {
		return ProcesosEnum.GESTION_CIRCULARES.getNombreEnum();
	}

	@Override
	protected void doExecute() throws JobExecutionException {

		try {

			ResultadoCircularBean resultado = servicioCircular.gestionarCirculares();
			if(!resultado.getError()){
				//es correcta ejecucion
				refrescarFrontales();
				peticionCorrecta();
				Log.info("Proceso de refresco de frontales");
				actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_CORRECTA, "SE HAN REFRESCADO LOS FRONTALES DE MANERA CORRECTA", "0");
			} else {
				 servicioCircular.enviarMail(resultado.getMensaje());
			}
		} catch (Exception e) {
			Log.error("Ha ocurrido un error a la hora de gestionar las circulares: " ,e);
			servicioCircular.enviarMail(e.getMessage());
		}
	}

	private void refrescarFrontales() {
		List<String> listaIPFrontales = recuperarIPFrontalesActivos();

		ResultBean resultado = new ResultBean();
		for (String ipFrontal : listaIPFrontales) {
			try {
				sendPost(ipFrontal);

			} catch (Exception e) {
				Log.error("El frontal " + ipFrontal + " no se pudo refrescar: " + e.getMessage());
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

	private void sendPost(String ip) throws Exception {

		String url = "https://" + ip + "/oegam2" + URL_RECARGA_PROPERTIES_OEGAM;

		final HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(final String arg0, final SSLSession arg1) {
				return true;
			}
		};

		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		con.setHostnameVerifier(hv);
		// optional default is GET
		con.setRequestMethod("POST");

		// add request header ¿?
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		String responseMessage = con.getResponseMessage();
		Log.info("\nSending 'POST' request to URL : " + url);
		Log.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		con.disconnect();

		// print result
		if ((!responseMessage.equalsIgnoreCase("ok"))) {
			throw new Exception();
		}
	}
}
