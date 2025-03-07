package utilidades.firma;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.transforms.Transforms;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Document;

import utilidades.web.OegamExcepcion;
import xades4j.UnsupportedAlgorithmException;
import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.production.XadesSigningProfile;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.DataObjectFormatProperty;
import xades4j.properties.DataObjectTransform;
import xades4j.providers.AlgorithmsProvider;
import xades4j.providers.BasicSignatureOptionsProvider;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.DirectKeyingDataProvider;

/**
 * Clase encargada de securizar los mensajes SOAP de petición realizados desde
 * un cliente.
 * 
 * 
 */
public class XMLSignerXADESbEnveloped {
	static {
		org.apache.xml.security.Init.init();
	}

	// Opciones de seguridad

	// Localización del keystore con certificado y clave privada de usuario
	private String keystoreLocation = null;

	// Tipo de keystore
	private String keystoreType = null;

	// Clave del keystore
	private String keystorePassword = null;

	// Alias del certificado usado para firmar el tag soapBody de la petición y
	// que será alojado en el token BinarySecurityToken
	private String keystoreCertAlias = null;

	// Password del certificado usado para firmar el tag soapBody de la petición
	// y que será alojado en el token BinarySecurityToken
	private String keystoreCertPassword = null;

	private KeyStore almacen;
	private PrivateKey clavePrivada;
	private X509Certificate certificado;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	/**
	 * Constructor que inicializa el atributo securityOption
	 * 
	 * @param securityOption opción de seguridad.
	 * @throws OegamExcepcion 
	 * @throws Exception
	 */
	public XMLSignerXADESbEnveloped() throws OegamExcepcion {
		try {
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
			keystoreLocation = gestorPropiedades.valorPropertie("security.keystore.location");
			keystoreType = gestorPropiedades.valorPropertie("security.keystore.type");
			keystorePassword = gestorPropiedades.valorPropertie("security.keystore.password");
			keystoreCertAlias = gestorPropiedades.valorPropertie("security.keystore.cert.alias");
			keystoreCertPassword = gestorPropiedades.valorPropertie("security.keystore.cert.password");

		} catch (Exception e) {
			System.err.println("Error leyendo el fichero de configuración de securización");
		}
	}

	/**
	 * Obtiene el certificado del almacen
	 * 
	 * @throws Exception
	 */
	private void GeneraCertificado() throws Exception {
		// Extrae la clave privada y el certificado del almacén de claves
		try {
			almacen = KeyStore.getInstance((String) keystoreType);
			FileInputStream fis = new FileInputStream((String) keystoreLocation);
			almacen.load(fis, ((String) keystorePassword).toCharArray());
			clavePrivada = (PrivateKey) almacen.getKey((String) keystoreCertAlias, ((String) keystoreCertPassword).toCharArray());
			certificado = (X509Certificate) almacen.getCertificate((String) keystoreCertAlias);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Firma el XML pasado por parametro
	 * 
	 * @param xml el XML que debe ser firmado
	 * 
	 * @return el XML firmado
	 * @throws Exception
	 */
	public String createFirma(String xml) throws Exception {

		// Lectura del XML, parseo, y obtención del Document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
/*		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");*/
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

		// Recuperar el certificado
		GeneraCertificado();

		// Implementación de las opciones de firma, para lograr que incluya el certificado, el par IssuerSerial, y una referencia al keyvalue 
		BasicSignatureOptionsProvider customSignatureOptionsProvider = new BasicSignatureOptionsProvider() {
			@Override
			public boolean signSigningCertificate() {
				return true;
			}

			@Override
			public boolean includeSigningCertificate() {
				return true;
			}

			@Override
			public boolean includePublicKey() {
				return true;
			}
		};

		// Se definen los algoritmos de firma, digest y canonicalización
		AlgorithmsProvider algorithmsProvider = new AlgorithmsProvider() {

			@Override
			public String getSignatureAlgorithm(String keyAlgorithmName) throws UnsupportedAlgorithmException {
				return SignatureMethod.RSA_SHA1;
			}

			@Override
			public String getDigestAlgorithmForTimeStampProperties() {
				return DigestMethod.SHA1;
			}

			@Override
			public String getDigestAlgorithmForReferenceProperties() {
				return DigestMethod.SHA1;
			}

			@Override
			public String getDigestAlgorithmForDataObjsReferences() {
				return DigestMethod.SHA1;
			}

			@Override
			public String getCanonicalizationAlgorithmForTimeStampProperties() {
				return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
			}

			@Override
			public String getCanonicalizationAlgorithmForSignature() {
				return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
			}
		};

		// Se crea el tipo de firma
		KeyingDataProvider kp = new DirectKeyingDataProvider(certificado, clavePrivada);
		XadesSigningProfile p = new XadesBesSigningProfile(kp)
				.withBasicSignatureOptionsProvider(
						customSignatureOptionsProvider).withAlgorithmsProvider(
						algorithmsProvider);
		XadesSigner signer = p.newSigner();

		DataObjectDesc obj1 = new DataObjectReference("").withTransform(
				new DataObjectTransform(
						Transforms.TRANSFORM_ENVELOPED_SIGNATURE))
				.withDataObjectFormat(
						new DataObjectFormatProperty("text/xml")
								.withDescription("Proteccion global"));
		;
		signer.sign(new SignedDataObjects(obj1), doc.getDocumentElement());

		// Devolver el xml
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();

		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));

		return writer.toString();

	}

	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Solicitud_Registro_Entrada version=\"3.0\"><Datos_Firmados><Datos_Genericos><Organismo>DGT</Organismo><Remitente><Nombre>COLEGIO OFICIAL DE GESTORES ADMINISTRATIVOS DE MADRID</Nombre><Apellidos/><Documento_Identificacion><Numero>Q2861007I</Numero></Documento_Identificacion><Correo_Electronico/></Remitente><Interesados><Interesado><Nombre>FERNANDO JESUS</Nombre><Apellidos>SANTIAGO OLLERO</Apellidos><Documento_Identificacion><Numero>00821563A</Numero></Documento_Identificacion><Correo_Electronico>2056@GESTORESMADRID.ORG</Correo_Electronico></Interesado></Interesados><Asunto><Codigo>MGDT</Codigo><Descripcion>Solicitud de Matriculacion Online para Gestores con Datos Tecnicos</Descripcion></Asunto><Destino><Codigo>101001</Codigo><Descripcion>DGT - Vehiculos</Descripcion></Destino><Numero_Expediente>205627061300007</Numero_Expediente></Datos_Genericos><Datos_Especificos Version=\"1.1\"><Jefatura>M</Jefatura><Sucursal/><Numero_Expediente_Colegio>205627061300007</Numero_Expediente_Colegio><Tasa>003129701202</Tasa><Datos_Vehiculo><Servicio>B00</Servicio><Bastidor>W0F4FFL28D4299931</Bastidor><Fecha_Validez_ITV>20180510</Fecha_Validez_ITV><Importado>N</Importado><Subasta>N</Subasta><Usado>N</Usado><Tipo_Inspeccion_Itv>M</Tipo_Inspeccion_Itv></Datos_Vehiculo><Titular><Identificacion><Datos_Nombre><Razon_Social/><Nombre>ANTONIO</Nombre><Primer_Apellido>RODRIGUEZ</Primer_Apellido><Segundo_Apellido>MOLINA</Segundo_Apellido></Datos_Nombre><Documento_Identidad><Numero>13859098B</Numero></Documento_Identidad><Fecha_Nacimiento>19410115</Fecha_Nacimiento><Sexo>V</Sexo></Identificacion><Servicio_Autonomo><Autonomo>N</Autonomo></Servicio_Autonomo><Domicilio_Titular><Cambio_Domicilio>S</Cambio_Domicilio><Datos_Domicilio><Municipio>28079</Municipio><Localidad/><Provincia>28</Provincia><Codigo_Postal>28040</Codigo_Postal><Tipo_Via>CALLE</Tipo_Via><Nombre_Via>LEONARDO PRIETO CASTRO</Nombre_Via><Numero>6</Numero><Bloque/><Portal/><Escalera/><Planta>BJ</Planta><Puerta/><Pais>ESP</Pais></Datos_Domicilio></Domicilio_Titular></Titular><Domicilio_Vehiculo><Municipio>28079</Municipio><Localidad/><Provincia>28</Provincia><Codigo_Postal>28040</Codigo_Postal><Tipo_Via>CALLE</Tipo_Via><Nombre_Via>LEONARDO PRIETO CASTRO</Nombre_Via><Numero>6</Numero><Bloque/><Portal/><Escalera/><Planta>BJ</Planta><Puerta/><Pais>ESP</Pais></Domicilio_Vehiculo><Datos_Tecnicos><Numero_Homologacion>E1*2001/116*0379*23</Numero_Homologacion><Codigo_ITV>OPE12247H</Codigo_ITV><Tipo_Vehiculo_Industria>1000</Tipo_Vehiculo_Industria><Tipo_Vehiculo_DGT>40</Tipo_Vehiculo_DGT><Marca>OPEL</Marca><Fabricante>ADAM OPEL AG</Fabricante><Modelo>CORSA</Modelo><Color/><Tipo>S-D</Tipo><Variante>FR11</Variante><Version>AA06D5X0MKA5</Version><Caracteristicas><Potencia_Fiscal>9.99</Potencia_Fiscal><Cilindrada>9.99</Cilindrada><Tara>1168</Tara><Masa_Maxima_Autorizada>1625</Masa_Maxima_Autorizada><Numero_Maximo_Plazas>5</Numero_Maximo_Plazas><Tipo_Carburante>G</Tipo_Carburante><Relacion_Potencia_Peso>9.99</Relacion_Potencia_Peso><Numero_Plazas_De_Pie>0</Numero_Plazas_De_Pie><Co2>9.999</Co2><Masa_En_Servicio>1163</Masa_En_Servicio><Potencia_Neta_Maxima>9.999</Potencia_Neta_Maxima><Carroceria>ND</Carroceria><CategoriaEu>M1</CategoriaEu><DistanciaEjes>2511</DistanciaEjes><Via_Anterior>1485</Via_Anterior><Via_Posterior>1478</Via_Posterior><Nivel_Emisiones>EURO 5J</Nivel_Emisiones><Reduccion_Eco>0</Reduccion_Eco><Tipo_Alimentacion>M</Tipo_Alimentacion></Caracteristicas><Pais_Fabricacion>0</Pais_Fabricacion></Datos_Tecnicos><Impuestos><CEM>502E564B</CEM><Justificado_IVTM>N</Justificado_IVTM></Impuestos><Tipo_Tramite>1</Tipo_Tramite></Datos_Especificos><Documentos/></Datos_Firmados></Solicitud_Registro_Entrada>";
		System.out.println("XML a firmar = " + xml);

		try {
			XMLSignerXADESbEnveloped test = new XMLSignerXADESbEnveloped();
			String signed = test.createFirma(xml);
			System.out.println("=============");
			System.out.println(signed);
		} catch (Throwable e) {

			e.printStackTrace();
		}

	}
}
