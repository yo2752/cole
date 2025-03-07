package com.registradoresWS.utilidades;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.gestoresmadrid.oegam2comun.registradores.corpme.CORPMEEDoc;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.web.OegamExcepcion;

/*
 * Construye la jerarquía de objetos java con jaxb relativa a la cadena xml contenedora
 * de los mensajes del CORPME (xml CORPME-eDoc)
 */
public class RespuestaRegistro {

	private static final Logger log = Logger.getLogger(RespuestaRegistro.class);

	// Propiedad que almacena la jerarquía de objetos java construída por JAXB a partir
	// del file recibido en el constructor
	private CORPMEEDoc corpmeEdoc;

	public RespuestaRegistro() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Autowired
	private GestorPropiedades gestorPropiedades;

	// El constructor recibe el fichero de la respuesta recibida y construye mediante el
	// api Jaxb una jerarquía de objetos java que permite el acceso a la información contenida
	// en el xml que se ha recibido como respuesta
	public RespuestaRegistro(File fichero) throws JAXBException, FileNotFoundException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		corpmeEdoc = (CORPMEEDoc) unmarshaller.unmarshal(new FileInputStream(fichero));
	}
	
	public RespuestaRegistro(byte[] fichero) throws JAXBException, FileNotFoundException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		corpmeEdoc = (CORPMEEDoc) unmarshaller.unmarshal(new ByteArrayInputStream(fichero));
	}
	

	// Obtiene el tipo de mensaje analizando el atributo Tipo_Mensaje del xml de respuesta
	public String getTipoMensaje() {
		return corpmeEdoc.getTipoMensaje();
	}

	// Obtiene el identificador del trámite a través del atributo correspondiente
	public String getIdTramite() {
		return corpmeEdoc.getIdTramite();
	}

	// Obtiene el codigo de retorno de la respuesta recibida
	public BigInteger getCodigoRetorno() {
		return corpmeEdoc.getCodigoRetorno();
	}

	// Obtiene la descripcion del retorno de la respuesta recibida:
	public String getDescripcionRetorno() {
		return corpmeEdoc.getDescripcionRetorno();
	}

	// Devuelve true si el mensaje contiene un documento con un solo fichero:
	public boolean esSimple() {
		if (corpmeEdoc.getDocumentos().getNumeroDocumentos() != BigInteger.ONE) {
			// Más de un documento
			return false;
		} else {
			// Un solo documento
			if (corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getNumeroFicheros() != BigInteger.ONE) {
				// Más de un fichero contenido en el documento
				return false;
			}
			// Un solo documento y un solo fichero en dicho documento
			return true;
		}
	}

	// // Recibe un parámetro para establecer el fichero
	// public void getContenido(File file) throws Exception{
	// String stringBase64=corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getContenido();
	// byte[]bytes=RespuestaRegistro.doBase64Decode(stringBase64);
	// FileOutputStream fos = new FileOutputStream(file);
	// fos.write(bytes);
	// fos.flush();
	// fos.close();
	// log.info("SERVICIO WEB REGISTRADORES : Se ha extraido correctamente el contenido del mensaje.");
	// }
	//
	// // Modificado para soportar un número indeterminado de documentos y de ficheros devueltos en un array list:
	// public ArrayList<File> getContenidos() throws Exception{
	//
	// ArrayList<File> contenidos = new ArrayList<File>();
	// BigInteger numeroDocumentos = corpmeEdoc.getDocumentos().getNumeroDocumentos();
	// log.info("SERVICIO WEB REGISTRADORES : El mensaje recibido contiene : " + numeroDocumentos + " documentos");
	// // Recorre los documentos para extraer los ficheros de cada documento:
	// for(int i = 0 ; i < numeroDocumentos.intValue() ; i ++ ){
	// BigInteger numeroFicheros = corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getNumeroFicheros();
	// log.info("SERVICIO WEB REGISTRADORES : El documento " + (i+1) + " contiene : " + numeroFicheros + " ficheros");
	// // Recorre todos los ficheros de un documento:
	// for(int y = 0 ; y < numeroFicheros.intValue() ; y ++ ){
	// // Verifica si viene o no codificado en Base64 y el formato:
	// String base64 = corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getFichero().get(y).getBase64();
	// if(base64.equalsIgnoreCase("N")){
	// log.info("SERVICIO WEB REGISTRADORES : El contenido del fichero " + (y+1) + " no viene codificado en Base64");
	// }else if(base64.equalsIgnoreCase("S")){
	// log.info("SERVICIO WEB REGISTRADORES : El contenido del fichero " + (y+1) + " viene codificado en Base64");
	// String stringBase64=corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getFichero().get(y).getContenido();
	// byte[]bytes=RespuestaRegistro.doBase64Decode(stringBase64);
	// File file = new File(Propiedades.leer(Propiedades.RUTA_NOTIFICACIONES_CONTENIDO) +
	// this.corpmeEdoc.getIdTramite() + "_" + this.corpmeEdoc.getTipoMensaje() + "_" + (i+1) + "_" + (y+1) + "." +
	// corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getFichero().get(y).getTipoFormato().toLowerCase());
	// FileOutputStream fos = new FileOutputStream(file);
	// fos.write(bytes);
	// fos.flush();
	// fos.close();
	// log.info("SERVICIO WEB REGISTRADORES : Se ha grabado correctamente el fichero " + (y+1) + " con el nombre : " + file.getName());
	// contenidos.add(file);
	// }
	// }
	// }
	// return contenidos;
	// }

	public Map<String, byte[]> getContenido() throws Exception {
		Map<String, byte[]> resultado = new HashMap<String, byte[]>();
		BigInteger numeroDocumentos = corpmeEdoc.getDocumentos().getNumeroDocumentos();
		log.info("SERVICIO WEB REGISTRADORES : El mensaje recibido contiene : " + numeroDocumentos + " documentos");
		for (int i = 0; i < numeroDocumentos.intValue(); i++) {
			BigInteger numeroFicheros = corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getNumeroFicheros();
			log.info("SERVICIO WEB REGISTRADORES : El documento " + (i + 1) + " contiene : " + numeroFicheros + " ficheros");
			for (int y = 0; y < numeroFicheros.intValue(); y++) {
				String base64 = corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getFichero().get(y).getBase64();
				if (base64.equalsIgnoreCase("N")) {
					log.info("SERVICIO WEB REGISTRADORES : El contenido del fichero " + (y + 1) + " no viene codificado en Base64");
				} else if (base64.equalsIgnoreCase("S")) {
					log.info("SERVICIO WEB REGISTRADORES : El contenido del fichero " + (y + 1) + " viene codificado en Base64");
					String stringBase64 = corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getFichero().get(y).getContenido();
					byte[] bytes = RespuestaRegistro.doBase64Decode(stringBase64);
					String nombre = this.corpmeEdoc.getIdTramite() + "_" + this.corpmeEdoc.getTipoMensaje() + "_" + (i + 1) + "_" + (y + 1) + "."
							+ corpmeEdoc.getDocumentos().getDocumento().get(i).getFicheros().getFichero().get(y).getTipoFormato().toLowerCase();
					resultado.put(nombre, bytes);
				}
			}
		}

		return resultado;
	}
	
	// Obtiene un fichero a partir del contenido del elemento <Contenido> del xml
		// de la respuesta que se descodifica y se vuelca en un fichero
		public File getContenidoAsync() throws Exception, OegamExcepcion {
			String stringBase64 = corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getContenido();
			byte[] bytes = RespuestaRegistro.doBase64Decode(stringBase64);
			File file = new File(gestorPropiedades.valorPropertie("registradores.ruta.NumeroEntrada.contenido") + corpmeEdoc.getIdTramite() + "_NE_CONTENIDO.xml");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
			return file;
		}

	// Método que devuelve la cadena en base 64 del elemento <Contenido>
	public String getCadena64Contenido() throws Exception {
		log.info("SERVICIO WEB REGISTRADORES : Inicio. " + " getCadena64Contenido.");
		String stringBase64 = corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getContenido();
		log.info("SERVICIO WEB REGISTRADORES : Fin. " + " getCadena64Contenido.");
		return stringBase64;
	}

	// Entorno estático para la decodificación de una cadena en base 64
	private static byte[] doBase64Decode(String bas64) throws Exception {
		return Base64.decodeBase64(bas64.getBytes());
	}

	// Devuelve el tipo de fichero (extensión) del elemento <Contenido>
	// OJO: Devuelve el tipo de formato del primer fichero.
	public String getExtensionFichero() {
		return corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getTipoFormato().toLowerCase();
	}

	// Obtiene el tipo de registro:
	public BigInteger getTipoRegistro() {
		return corpmeEdoc.getTipoRegistro();
	}

	// Obtiene el código de registro:
	public String getCodigoRegistro() {
		return corpmeEdoc.getCodigoRegistro();
	}
}
