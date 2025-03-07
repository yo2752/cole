package pruebas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;
import org.viafirma.cliente.exception.InternalException;
import org.viafirma.cliente.firma.TypeFile;
import org.viafirma.cliente.firma.TypeFormatSign;
import org.viafirma.cliente.vo.Documento;

public class Test {
	
	public static void main0(String[]args) throws IOException, InternalException{
		String url_viafirma="http://172.26.0.60:8080/viafirma";
		String url_viafirmaWS="http://172.26.0.60:8080/viafirma/rest";
		ViafirmaClientFactory.init(url_viafirma,url_viafirmaWS);
		ViafirmaClient viafirmaClient=ViafirmaClientFactory.getInstance();
		String pass = "123456";
		String alias = "coleconcpkeypair";
		//File fichero = new File("C:/original.xml");
		//File fichero = new File("C:/dpr.xml");
		File fichero = new File("C:/xmlCesar.xml");
		FileInputStream fis;
		fis = new FileInputStream(fichero);
		byte[]datosAfirmar = IOUtils.toByteArray(fis);
		//String idFirma=viafirmaClient.signByServerWithTypeFileAndFormatSign(
		//	"dpr.xml", datosAfirmar, alias, pass, TypeFile.XML, TypeFormatSign.CMS_DETACHED);
		String idFirma=viafirmaClient.signByServerWithTypeFileAndFormatSign(
				"dpr.xml", datosAfirmar, alias, pass, TypeFile.XML, TypeFormatSign.XADES_EPES_ENVELOPED);
		Documento original = viafirmaClient.getOriginalDocument(idFirma);
		byte[] originales = original.getDatos();
		creaFicheroConBytes("C:/originalTrasFirma.xml", originales);
		Test test = new Test();
		test.getDocumentoFirmado(viafirmaClient,idFirma);
	}
	
	public String getDocumentoFirmado(ViafirmaClient viafirmaClient,String idFirma){
		viafirmaClient=ViafirmaClientFactory.getInstance();
		byte[] documentoCustodiado = null;
		try {
			documentoCustodiado =viafirmaClient.getDocumentoCustodiado(idFirma);
		}
		catch (InternalException e) 
		{
			System.out.println(e);
		} 
		System.out.println(new String(documentoCustodiado));
		return new String(documentoCustodiado);
	}

	public static void creaFicheroConBytes(String ruta,byte[] ptcBytes ) throws IOException{
		
		FileOutputStream ficheroSalida = new FileOutputStream(ruta ,true);
		ficheroSalida.write(ptcBytes);
		ficheroSalida.flush(); 
		ficheroSalida.close(); 
	}

}
