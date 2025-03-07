package org.gestoresmadrid.oegam2comun.administracion.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.gestoresmadrid.core.administracion.model.dao.RecargaCacheDao;
import org.gestoresmadrid.core.administracion.model.enumerados.EstadoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.vo.RecargaCacheVO;
import org.gestoresmadrid.oegam2comun.administracion.service.ServicioRecargaCache;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
@Transactional
public class ServicioRecargaCacheImpl implements ServicioRecargaCache {

	private static final long serialVersionUID = -5968951471854035730L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioRecargaCacheImpl.class);

	@Autowired 
	RecargaCacheDao daoRecargaCache;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public void guardarPeticion(RecargaCacheVO peticion) {
		daoRecargaCache.guardarPeticion(peticion);
	}

	@Override
	public void guardarPeticion(TipoRecargaCacheEnum tipo) {
		RecargaCacheVO peticion = new RecargaCacheVO();
		try {
			peticion.setIP(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			peticion.setIP("-");
		}
		peticion.setFechaPeticion(new Date());
		peticion.setTipo(tipo);

		guardarPeticion(peticion);
	}

	@Override
	public void marcarPeticionTratada(RecargaCacheVO peticion, EstadoRecargaCacheEnum estado, String error) {
		peticion.setError(error);
		marcarPeticionTratada(peticion, estado);
	}

	@Override
	public void marcarPeticionTratada(RecargaCacheVO peticion, EstadoRecargaCacheEnum estado) {
		daoRecargaCache.marcarPeticionTratada(peticion, estado);
	}

	@Override
	public List<RecargaCacheVO> recuperarSolicitudesPendientes() {
		return daoRecargaCache.recuperarSolicitudesPendientes();
	}

	@Override
	public void registrarPeticionRecargaCombos() {
		guardarPeticion(TipoRecargaCacheEnum.COMBOS);
	}

	@Override
	public void registrarPeticionRecargaDatosSensibles() {
		guardarPeticion(TipoRecargaCacheEnum.DATOS_SENSIBLES);
	}

	@Override
	public ResultBean refrescarCache(List<RecargaCacheVO> peticionesPendientes) {
		boolean realizadoPeticionCombos = false;
		boolean realizadoPeticionDatosSensibles = false;
		List<String> listaIPFrontales = recuperarIPFrontalesActivos();
		ResultBean resultado = new ResultBean();
		for (RecargaCacheVO peticion : peticionesPendientes) {

			if (peticion.getTipo().equals(TipoRecargaCacheEnum.COMBOS) && !realizadoPeticionCombos) {
				resultado = enviarRefresco(listaIPFrontales, URL_RECARGA_COMBOS);
			} else if (peticion.getTipo().equals(TipoRecargaCacheEnum.DATOS_SENSIBLES) && !realizadoPeticionDatosSensibles) {
				resultado = enviarRefresco(listaIPFrontales, URL_RECARGA_DATOS_SENSIBLES);
			}

			// Actualizo el resultado
			if (!resultado.getError() && resultado.getListaMensajes().isEmpty()) {
				marcarPeticionTratada(peticion, EstadoRecargaCacheEnum.OK);
				if (peticion.getTipo().equals(TipoRecargaCacheEnum.COMBOS)) {
					realizadoPeticionCombos = true;
				} else if (peticion.getTipo().equals(TipoRecargaCacheEnum.DATOS_SENSIBLES)) {
					realizadoPeticionDatosSensibles = true;
				}
			} else if (!resultado.getError() && !resultado.getListaMensajes().isEmpty()) {
				String listaMensajesCompleta = "";
				for (String mensaje : resultado.getListaMensajes()) {
					listaMensajesCompleta += mensaje + ";";
				}
				marcarPeticionTratada(peticion, EstadoRecargaCacheEnum.INDETERMINADO, listaMensajesCompleta);
			} else {
				marcarPeticionTratada(peticion, EstadoRecargaCacheEnum.ERROR);
				guardarPeticion(peticion.getTipo()); // Se intenta refrescar más tarde
			}
		}
		return resultado;
	}

	private ResultBean enviarRefresco(List<String> listaIPFrontales, String url) {
		ResultBean resultado = new ResultBean();
		for (String ipFrontal : listaIPFrontales) {
			try {
				sendPost(ipFrontal, url);
			} catch (Exception e) {
				log.error("El frontal " + ipFrontal + " no se pudo refrescar: " + e.getMessage());
				resultado.addMensajeALista(ipFrontal);
			}
		}

		if (listaIPFrontales.size() == resultado.getListaMensajes().size())
			resultado.setError(true);

		return resultado;
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

		if ((!responseMessage.equalsIgnoreCase("ok"))) {
			throw new Exception();
		}
	}

	private List<String> recuperarIPFrontalesActivos() {
		return Arrays.asList(gestorPropiedades.valorPropertie(PROPERTY_FRONTALES_ACTIVOS).split(";"));
	}
}