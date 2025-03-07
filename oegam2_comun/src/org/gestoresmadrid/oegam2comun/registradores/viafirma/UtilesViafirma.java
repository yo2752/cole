package org.gestoresmadrid.oegam2comun.registradores.viafirma;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xerces.impl.dv.util.Base64;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.gestoresmadrid.oegam2comun.registradores.constantes.Constantes;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientFactory;
import org.viafirma.cliente.exception.InternalException;
import org.viafirma.cliente.firma.TypeFile;
import org.viafirma.cliente.firma.TypeFormatSign;

public final class UtilesViafirma {

	public static final String TIMESTAMPID= "1.2.840.113549.1.9.16.2.14";

	private UtilesViafirma() {
		super();
	}

	public static void firmarApplet(String nombre, String formato, byte[] datosAFirmar, HttpServletRequest request, HttpServletResponse response) throws InternalException {

		ViafirmaClient viafirmaClient = getClienteRest();
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(Constantes.PROPERTIES_SEARBM);
		String urlRespuesta = resourceBundle.getString("url.viafirma.respuesta");

		TypeFile typeFile = TypeFile.OTHER;
		for (TypeFile tf: TypeFile.values()) {
			if(tf.name().equals(formato)){
				typeFile = tf;
				break;
			}
		}
		// Invocación al método de ViafirmaClient que prepara la solicitud de la firma
		String idTemporal=viafirmaClient.prepareFirmaWithTypeFileAndFormatSign(nombre,typeFile,TypeFormatSign.CMS_DETACHED, datosAFirmar);
		
		viafirmaClient.solicitarFirma(idTemporal,request,response, urlRespuesta);
		
		return;
	}
	
	public static ViafirmaClient getClienteRest() {
		
		ResourceBundle resourceBundle = null;
		if(!ViafirmaClientFactory.isInit()){
			resourceBundle = ResourceBundle.getBundle(Constantes.PROPERTIES_SEARBM);
			String urlViafirma = resourceBundle.getString("url.viafirma");
			String urlViafirmaRest = resourceBundle.getString("url.viafirma.rest");
			ViafirmaClientFactory.init(urlViafirma, urlViafirmaRest);
		}
		return ViafirmaClientFactory.getInstance();
	}
	
	public static ViafirmaClient getClienteWs() {

		ResourceBundle resourceBundle = null;
		if(!ViafirmaClientFactory.isInit()){
			resourceBundle = ResourceBundle.getBundle(Constantes.PROPERTIES_SEARBM);
			String urlViafirma = resourceBundle.getString("url.viafirma");
			String urlViafirmaWs = resourceBundle.getString("url.viafirma.ws");
			ViafirmaClientFactory.init(urlViafirma,urlViafirmaWs);
		}
		return ViafirmaClientFactory.getInstance();
	}

	/**
	 * Elimina el campo timeStamp del CMS.
	 * @param cmsSignedData
	 * @param signerInformationStore
	 * @return 
	 */
	private static CMSSignedData removeTimeStamp(CMSSignedData cmsSignedData) {
		SignerInformationStore signerInformationStore =cmsSignedData.getSignerInfos();

		// Recorre los firmantes y elimina los atributos unsigned
		List<SignerInformation> vNewSigners = new ArrayList<SignerInformation>();
		@SuppressWarnings("unchecked")
		Collection<SignerInformation> ovSigners = signerInformationStore.getSigners();
		for (Iterator<SignerInformation> iter = ovSigners.iterator();iter.hasNext();){
			SignerInformation oSi = iter. next();
//			Hashtable ht = new Hashtable();
			// sustituimos por empty
			//AttributeTable unsignedAtts = new AttributeTable(ht);
			//vNewSigners.add(SignerInformation.replaceUnsignedAttributes(oSi,unsignedAtts));
			vNewSigners.add(SignerInformation.replaceUnsignedAttributes(oSi,null));
		}
		SignerInformationStore oNewSignerInformationStore = new SignerInformationStore(vNewSigners);
		return CMSSignedData.replaceSigners(cmsSignedData, oNewSignerInformationStore);
	}

	/**
	 * Obtiene un CMS con TimeStamp, elimina el campo timestamp y lo retorna.
	 * 
	 * @param id
	 * @return
	 * @throws InternalException 
	 * @throws CMSException 
	 * @throws IOException 
	 */
	public static String getCMSSignatureWithOutTimeStamp(String id) throws InternalException, CMSException, IOException {
	    // Obtenemos el CMS desde Viafirma
		byte[] signedData=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		// Generamos la estructura CMS
		CMSSignedData cmsSignedData=new CMSSignedData(signedData);
		// Eliminamos el timestamp del cms
		CMSSignedData cmsWithOutTimeStamp=removeTimeStamp(cmsSignedData);
		signedData=dodefiniteLenghFormat(cmsWithOutTimeStamp);
		return Base64.encode(signedData);
	}

	private static byte[] dodefiniteLenghFormat(CMSSignedData cmsSignedData) throws IOException {
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		DEROutputStream dOut = null;
		try {
			dOut = new DEROutputStream(stream);
			dOut.writeObject(ASN1Object.fromByteArray(cmsSignedData.getEncoded()));
			return stream.toByteArray(); 
		} finally {
			dOut.close();
		}
	}

}
