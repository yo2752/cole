/*
 * 
 * Developer: Alejandro Castilla Armero
 * 
 * Desarrollado para el Ilustre Colegio Oficial de Gestores Administrativos de Madrid
 * 
 */

package viafirma.utilidades;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.viafirma.cliente.ViafirmaClientFactory;
import org.viafirma.cliente.vo.Documento;

/**
 *	Clase de utilidades para la ayuda en la construccion del XML  
 */
public class XmlBuilderSupportUtils {
		
	
	public static final String TIMESTAMPID= "1.2.840.113549.1.9.16.2.14";
	

	private XmlBuilderSupportUtils() {
	}
	
	public static String getOriginalDocument(String id) throws Exception{
		Documento documento=ViafirmaClientFactory.getInstance().getOriginalDocument(id);
		return doBase64Encode(documento.getDatos());
	}
	
	public static String getTimeStampFromCMS(String id) throws Exception{
		byte[] signedDocument=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		CMSSignedData cmsSignedData=new CMSSignedData(signedDocument);
		SignerInformationStore signerInformationStore =cmsSignedData.getSignerInfos();
		SignerInformation signerInformation = (SignerInformation)signerInformationStore.getSigners().iterator().next();
		AttributeTable attributeTable=signerInformation.getUnsignedAttributes();
		if(attributeTable != null){
			DERObject timestamp=attributeTable.get(new DERObjectIdentifier(TIMESTAMPID)).getDERObject();
			return doBase64Encode(((DERSet)DERSequence.getInstance(timestamp).getObjectAt(1)).getObjectAt(0).getDERObject().getDEREncoded());
		}else{
			throw new Exception("Time out al conectar con el servidor de sellado de tiempo");
		}
	}
	
	// Ricardo: Para que lo devuelva sin codificar:
	public static byte[] getTimeStampFromCMSbytes(String id) throws Exception{
		byte[] signedDocument=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		CMSSignedData cmsSignedData=new CMSSignedData(signedDocument);
		SignerInformationStore signerInformationStore =cmsSignedData.getSignerInfos();
		SignerInformation signerInformation = (SignerInformation)signerInformationStore.getSigners().iterator().next();
		AttributeTable attributeTable=signerInformation.getUnsignedAttributes();
		if(attributeTable != null){
			DERObject timestamp=attributeTable.get(new DERObjectIdentifier(TIMESTAMPID)).getDERObject();
			return ((DERSet)DERSequence.getInstance(timestamp).getObjectAt(1)).getObjectAt(0).getDERObject().getDEREncoded();
		}else{
			throw new Exception("Time out al conectar con el servidor de sellado de tiempo");
		}
	}
	
	
	/**
	 * Obtiene un CMS con TimeStamp, elimina el campo timestamp y lo retorna.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static String getCMSSignatureWithOutTimeStamp(String id) throws Exception{
	    // Obtenemos el CMS desde Viafirma
		byte[] signedData=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		doBase64Encode(signedData);
		// Generamos la estructura CMS
		//CMSSignedData cmsSignedData=new CMSSignedData(signedData);
		// Eliminamos el timestamp del cms
		//CMSSignedData cmsWithOutTimeStamp=removeTimeStamp(cmsSignedData);
		//signedData=dodefiniteLenghFormat(cmsWithOutTimeStamp);
		return doBase64Encode(signedData);
	}
	
	// Ricardo: Para que lo devuelva sin codificar
	public static byte[] getCMSSignatureWithOutTimeStampBytes(String id) throws Exception{
		// Obtenemos el CMS desde Viafirma
		byte[] signedData=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		doBase64Encode(signedData);
		// Generamos la estructura CMS
		//CMSSignedData cmsSignedData=new CMSSignedData(signedData);
		// Eliminamos el timestamp del cms
		//CMSSignedData cmsWithOutTimeStamp=removeTimeStamp(cmsSignedData);
		//signedData=dodefiniteLenghFormat(cmsWithOutTimeStamp);
		return signedData;
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
	    List vNewSigners = new ArrayList();
	    Collection ovSigners = signerInformationStore.getSigners();
	    for (Iterator iter = ovSigners.iterator();iter.hasNext();){
	    	SignerInformation oSi = (SignerInformation) iter. next();
	    	Hashtable ht = new Hashtable();
	    	// sustituimos por empty
	    	//AttributeTable unsignedAtts = new AttributeTable(ht);
	    	//vNewSigners.add(SignerInformation.replaceUnsignedAttributes(oSi,unsignedAtts));
	    	vNewSigners.add(SignerInformation.replaceUnsignedAttributes(oSi,null));
	    }
	    SignerInformationStore oNewSignerInformationStore = new SignerInformationStore(vNewSigners);
	    CMSSignedData oNewSd =CMSSignedData.replaceSigners(cmsSignedData, oNewSignerInformationStore);
	    return oNewSd;
	}
	
	private static byte[] dodefiniteLenghFormat(CMSSignedData cmsSignedData) throws Exception{
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		DEROutputStream dOut = new DEROutputStream(stream); 
		dOut.writeObject(ASN1Object.fromByteArray(cmsSignedData.getEncoded())); 
		return stream.toByteArray();
	}
	
	public static String getCMSSignature(String id) throws Exception{
		byte[] signedData=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		return doBase64Encode(signedData);
	}
	
	// Ricardo: Para que lo devuelva sin codificar:
	public static byte[] getCMSSignatureBytes(String id) throws Exception{
		byte[] signedData=ViafirmaClientFactory.getInstance().getDocumentoCustodiado(id);
		return signedData;
	}
	
	public static byte[] doDigest(byte[] data) throws Exception{
		return DigestUtils.sha(data);
	}
	
	public static byte[] doDigest(String data) throws Exception{
		return DigestUtils.sha(data);
	}
	
	public static String doBase64Encode(byte[] data) throws Exception{
		return new String(Base64.encodeBase64(data));
	}
	
	public static byte[] doBase64Decode(String bas64) throws Exception{
		return Base64.decodeBase64(bas64.getBytes());
	}
	
	public static byte[] getDatos(String id){
		try{
			Documento documento=ViafirmaClientFactory.getInstance().getOriginalDocument(id);
			return documento.getDatos();
		}catch(Exception e){
			return null;
		}
	}
	
}
