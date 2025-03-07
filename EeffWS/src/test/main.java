package test;

import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//	// Con el proxy escribir el localeId y el peticion
//
//	SolicitudOperacionesITVWS proxy = new SolicitudOperacionesITVWSProxy();
//
//	try{
//	String localeId = "";
//	String peticion = "";
//
//	//Preparar el xml para firmarlo.
//	String direccion ="src/test/prueba.xml";
//	//	Path path = Paths.get(direccion);
//		byte[] data;
//		try {
//	//		data = Files.readAllBytes(path);
//
//			String xml = new String(data);
//			//Quitamos los namespaces porque da error en MATW
//			xml = xml.replaceAll("<ns2:", "<");
//			xml = xml.replaceAll("</ns2:", "</");
//
//			//Obtenemos los datos que realmente se tienen que firmar
////		int inicio = xml.indexOf("<FORMATO_GA>");
////		int fin = xml.indexOf("</FORMATO_GA>");
////		String datosAFirmar = xml.substring(inicio, fin);
//
//			String xmlAFirmar = "<Datos_Firmados>" + xml + "</Datos_Firmados>";
//
//		//	System.out.println(xmlAFirmar);
//
//			UtilesViafirma utilesViafirma=new UtilesViafirma();
//			String idFirma;
//			String xmlFirmado; 
//			ViafirmaClient viafirmaClient =getClienteRest();
//			byte[] Afirmar=xml.getBytes();
//
//			String password = ""; //pasword de Natividad
//
//			idFirma=viafirmaClient.signByServerWithTypeFileAndFormatSign(
//						"prueba.xml", Afirmar, "", password, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);
//
////		idFirma=viafirmaClient.signByServerWithTypeFileAndFormatSign(
////						"prueba.xml", Afirmar, "", "", TypeFile.XML, TypeFormatSign.XADES_T_ENVELOPED,"DO");
//
//			byte[] documentoCustodiado;
//
//			documentoCustodiado =viafirmaClient.getDocumentoCustodiado(idFirma);
//
//			xmlFirmado=new String(documentoCustodiado);
//
//			System.out.print(xmlFirmado+"\n");
//
//			peticion = xmlAFirmar+"" + xmlFirmado;
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		//SolicitudOperacionesITVWSService locator = new SolicitudOperacionesITVWSServiceLocator();
//
//	proxy.liberarEITV(localeId, peticion);
//	}catch(Exception e){
//		 System.out.println(e);
//	}
	}

	private static ViafirmaClient getClienteRest() {
		try {
			if(!ViafirmaClientFactory.isInit()){
				ViafirmaClientFactory.init("http://192.168.50.27/viafirma",
							"http://192.168.50.27/viafirma/rest");
				}
		} catch (Exception e2) {
			System.out.print(e2);
		}
		return ViafirmaClientFactory.getInstance();
	}

}