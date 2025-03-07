package viafirma.utilidades;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.xml.ws.soap.SOAPFaultException;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;
import org.viafirma.cliente.exception.InternalException;
import org.viafirma.cliente.firma.TypeFile;
import org.viafirma.cliente.firma.TypeFormatSign;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class UtilesViafirmaWS implements Serializable{

	private static final long serialVersionUID = -1325410843755901669L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesViafirmaWS.class);
	private static final String URL_VIAFIRMA = "url_viafirma";
	private static final String URL_VIAFIRMA_WS = "url_viafirmaWS";
	private static final String DATOS_A_FIRMAR = "DATOS A FIRMAR";
	private static final String NODEID = "D0";
	public final String UTF_8 = "UTF-8";

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	/**
	 * Obtiene una instancia de ViafirmaClient para operaciones en Viafirma
	 * @param aplicacion que instancia el cliente y api con la que se inicializará
	 * @return Instancia de ViafirmaClient para una determinada aplicación del manager de viafirma
	 * @throws Throwable
	 */
	public ViafirmaClient getClient(String aplicacion) {
		if (!ViafirmaClientFactory.isInit()) {
			String apiKey = null;
			String passKey = null;
			if (aplicacion != null && !aplicacion.isEmpty()) {
				apiKey = gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_APP_KEY);
				passKey = gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_PASS_KEY);
			}
			if (apiKey != null && passKey != null) {
				ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS), null, gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_APP_KEY),
					gestorPropiedades.valorPropertie(aplicacion + ConstantesViafirma.PROPIEDAD_PASS_KEY));
			} else {
				ViafirmaClientFactory.init(gestorPropiedades.valorPropertie(URL_VIAFIRMA), gestorPropiedades.valorPropertie(URL_VIAFIRMA_WS));
			}
		}
		return ViafirmaClientFactory.getInstance();
	}

	public String firmarPruebaCertificadoCaducidad(String xml, String alias) {
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		String idFirma = null;
		try {
			byte[] datosAfirmar = new String(xml.getBytes("UTF-8"), "UTF-8").getBytes("UTF-8");
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", datosAfirmar, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);
		} catch (UnsupportedEncodingException e) {
			log.error("Error a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (InternalException e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (SOAPFaultException e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		} catch (Exception e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		}catch (Throwable e) {
			log.error("Error Interno a la hora de comprobar la caducidad del certificado mediante una firma: " + e.toString());
			return null;
		}
		return idFirma;
	}

	public String firmarConsultaTarjetaEitv(byte[] cadena, String alias) {
		log.info("Consulta Tarjeta Eitv WS: Inicio-- firmar Datos");
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		String xmlFirmado = null;
		try {
			if (log.isInfoEnabled()) {
				log.info(DATOS_A_FIRMAR + cadena);
			}
			String password = gestorPropiedades.valorPropertie("trafico.claves.colegio.password");
			String idFirma = viafirmaClient.signByServerWithTypeFileAndFormatSign("prueba.xml", cadena, alias, password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED, NODEID);
			if(idFirma != null && !idFirma.isEmpty()) {
				xmlFirmado = getDocumentoFirmado(idFirma);
			}
		} catch (InternalException e) {
			log.error("Error a la hora de firmar datos de la consulta de tarjeta Eitv", e);
		}catch (Throwable e) {
			log.error("Error a la hora de firmar datos de la consulta de tarjeta Eitv, error: ", e);
		}
		return xmlFirmado;
	}
	
	public String getDocumentoFirmado(String idFirma) {
		ViafirmaClient viafirmaClient = getClient(ConstantesViafirma.OEGAM);
		byte[] documentoCustodiado;
		try {
			if ("SI".equals(gestorPropiedades.valorPropertie("via.firma.sleep"))) {
				Thread.sleep(1500);
			}
			documentoCustodiado = viafirmaClient.getDocumentoCustodiado(idFirma);
			log.info("documento= " + documentoCustodiado);
		} catch (InternalException e) {
			documentoCustodiado = null;
			log.error(e);
		}catch (Throwable e) {
			documentoCustodiado = null;
			log.error(e);
		}
		return new String(documentoCustodiado);
	}
	
}
