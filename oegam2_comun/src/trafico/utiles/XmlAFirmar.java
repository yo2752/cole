package trafico.utiles;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.Utiles;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

public class XmlAFirmar {
		private static final String ERROR_AL_OBTENER_BYTES_UTF_8 = "Error al obtener bytes UTF-8";
		private static final String UTF_8 = "UTF-8";
		private static final String XML_ENCODING_UTF8 =UTF_8;
		//Valor del atributo ID del nodo a firmar en la petición XML a MATEGE
		private static final String MATEGE_NODO_FIRMAR_ID = "D0";
		private static final String HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA = "Ha ocurrido un error recuperando el xml firmado a traves del id de firma";
		private static final String HA_OCURRIDO_UN_ERROR_AL_COMPROBAR_LA_CADUCIDAD_DE_LA_FIRMA = "Ha ocurrido un error al comprobar la caducidad de la firma";
		private static final String ERROR = "ERROR";
		private static final ILoggerOegam log = LoggerOegam.getLogger(XmlAFirmar.class);
		
		public  static ResultBean realizarFirmaDatosFirmadosHsm(String xml,String alias){
			String nodoDatosFirmados = extraerNodoDatosFirmados(xml);
			//Se codifican los datos a firmar en base 64
			String encodedAFirmar = convertirB64(nodoDatosFirmados);
			//Se construye el XML que contiene los datos a firmar
			String xmlAFirmar = encerrarAfirmaContent(encodedAFirmar);
			
			return  firmarHsm(xmlAFirmar,alias);
				
	    }
	
	 private static String extraerNodoDatosFirmados(String xml) {
			//Quitamos los namespaces porque da error en MATEGE
			xml = xml.replaceAll("<ns2:", "<");
			xml = xml.replaceAll("</ns2:", "</");
			
			//Obtenemos los datos que realmente se tienen que firmar
			int inicio = xml.indexOf("<Datos_Firmados>") + 16;
			int fin = xml.indexOf("</Datos_Firmados>");
			String datosFirmados = xml.substring(inicio, fin);
			log.debug("LOG_MATEGE DATOS FIRMADOS "+datosFirmados);
			log.debug("Datos a firmar:" + datosFirmados);
			return datosFirmados;
		}
	
	 private static String convertirB64(String datosFirmados) {
			String encodedAFirmar = "";
			
			try{				
//				encodedAFirmar = UtilesRegistradores.doBase64Encode(datosFirmados.getBytes(XML_ENCODING_UTF8));
				encodedAFirmar = ContextoSpring.getInstance().getBean(Utiles.class).doBase64Encode(datosFirmados.getBytes(XML_ENCODING_UTF8));
				encodedAFirmar = encodedAFirmar.replaceAll("\n", "");
				log.debug("LOG_MATEGE ENCODE AFIRMAR: "+encodedAFirmar);
				}
			catch(Exception e)
				{	
				log.error("Error al codificar en base 64 el " + XML_ENCODING_UTF8);
				}
			return encodedAFirmar;
		}
	
	 private static String encerrarAfirmaContent(String encodedAFirmar) {
			String xmlAFirmar = "<AFIRMA><CONTENT Id=\"" + MATEGE_NODO_FIRMAR_ID + "\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
			log.debug("XML a firmar:" + xmlAFirmar);
			return xmlAFirmar;
		}
	
	 private static ResultBean firmarHsm(String cadenaXML,String alias) {
			
			UtilesViafirma utilesViafirma=new UtilesViafirma();
			ResultBean resultBean = new ResultBean();

			if(utilesViafirma.firmarPruebaCertificadoCaducidad(cadenaXML, alias)==null){
				
				log.error(HA_OCURRIDO_UN_ERROR_AL_COMPROBAR_LA_CADUCIDAD_DE_LA_FIRMA); 
				resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_AL_COMPROBAR_LA_CADUCIDAD_DE_LA_FIRMA);
				resultBean.setError(true); 
				
			}else{
				
				
				String xmlFirmado= utilesViafirma.firmarMensajeMatriculacionServidor(cadenaXML,alias);
						
				if(xmlFirmado.equals(ERROR))
					{
					// Ha fallado el proceso de firma en servidor
					log.error(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA); 
					resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
					resultBean.setError(true); 
					}
				else{//firma correctamente
					 resultBean.setError(false);
					 resultBean.setMensaje(xmlFirmado);
					}		
				
			}
						
			return resultBean; 
		
		}
	
	
}
