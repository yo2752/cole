package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.oegam2comun.registradores.corpme.CORPMEEDoc;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.web.OegamExcepcion;

/*
 * Construye la jerarqu�a de objetos java con jaxb relativa a la cadena xml contenedora
 * de los mensajes del CORPME (xml CORPME-eDoc)
 */
public class RespuestaRegistro {

	// Propiedad que almacena la jerarqu�a de objetos java constru�da por JAXB a partir
	// del file recibido en el constructor
	private CORPMEEDoc corpmeEdoc;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public RespuestaRegistro() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	// El constructor recibe el fichero de la respuesta recibida y construye mediante el
	// api Jaxb una jerarqu�a de objetos java que permite el acceso a la informaci�n contenida
	// en el xml que se ha recibido como respuesta
	public RespuestaRegistro(File fichero) throws JAXBException, FileNotFoundException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.registradores.corpme");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		corpmeEdoc = (CORPMEEDoc) unmarshaller.unmarshal(new FileInputStream(fichero));
	}

	// Obtiene un fichero a partir del contenido del elemento <Contenido> del xml
	// de la respuesta que se descodifica y se vuelca en un fichero
	public File getContenido() throws Exception, OegamExcepcion {
		String stringBase64 = corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getContenido();
		byte[] bytes = RespuestaRegistro.doBase64Decode(stringBase64);
		File file = new File(gestorPropiedades.valorPropertie("registradores.ruta.NumeroEntrada.contenido") + corpmeEdoc.getIdTramite() + "_NE_CONTENIDO.xml");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.flush();
		fos.close();
		return file;
	}

	// Igual que getContenido pero tiene como par�metro la ruta donde se debe almacenar el fichero extra�do del xml
	// y el idTramite para construir el nombre dinamicamente:
	public File getContenidoEn(String rutaDestino, String idTramite) throws Exception, OegamExcepcion {
		String stringBase64 = corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getContenido();
		byte[] bytes = RespuestaRegistro.doBase64Decode(stringBase64);
		File file = new File(rutaDestino + "contenido_ne_" + idTramite + ".xml");
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytes);
		fos.flush();
		fos.close();
		return file;
	}

	// M�todo que devuelve la cadena en base 64 del elemento <Contenido>
	public String getCadena64Contenido() throws Exception, OegamExcepcion {
		String stringBase64 = corpmeEdoc.getDocumentos().getDocumento().get(0).getFicheros().getFichero().get(0).getContenido();
		return stringBase64;
	}

	// Entorno est�tico para la decodificaci�n de una cadena en base 64
	public static byte[] doBase64Decode(String bas64) throws Exception {
		return Base64.decodeBase64(bas64.getBytes());
	}

	// Obtiene el tipo de mensaje analizando el atributo Tipo_Mensaje del xml de respuesta
	public String getTipoMensaje() {
		return corpmeEdoc.getTipoMensaje();
	}

	// Obtiene el identificador del tr�mite a trav�s del atributo correspondiente
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

	// Para pruebas: Permite establecer el identificador del tr�mite que se ha 'recibido'
	public void setIdTramite(String idTramite) {
		corpmeEdoc.setIdTramite(idTramite);
	}
}
