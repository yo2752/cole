package trafico.utiles;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class UtilesCustodia {


	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesCustodia.class);
	
	
	private String rutaXML;
	private static final String NODO_NO_TELEMATICAS = "NodoLecturaNoTelematicas";
	private static final String RUTA_XML_NO_TELE = "ruta_xml_no_telematicas";
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public UtilesCustodia() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	
	/**
	 * 
	 * @return da la ruta de los xml que deben ser enviados.
	 */
	public String getRutaXML(){
		try {
			rutaXML = getIpNodo()+  getRutaLocalXml();
			log.info("ruta que devolvemos: " + rutaXML);
		} catch (OegamExcepcion e) {
			log.error(e);
		}
		return rutaXML; 
	}

	public String getRutaLocalXml() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(RUTA_XML_NO_TELE);
	}

	public String getIpNodo() throws OegamExcepcion {
		String ipNodo = gestorPropiedades.valorPropertie("ficheros." +getNodoTransmision());
		return ipNodo;
	}

	public String getNodoTransmision()	throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(NODO_NO_TELEMATICAS);
	}
	
	public String getRutaLocalLecturaXml() throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(RUTA_XML_NO_TELE);
	}
	public String getNodoLecturaXml()	throws OegamExcepcion {
		return gestorPropiedades.valorPropertie(NODO_NO_TELEMATICAS);
	}
	
	
	
}
