package utiles.correo.clientes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import utiles.correo.FicheroAdjunto;
import utiles.correo.objetos.CorreoRequest;
import utilidades.web.OegamExcepcion;


public class ClienteRestCorreo {
	private static final String PROPERTY_KEY_TEXTO_PLANO = "mail.icogam.formato.textoPlano";
	
	public ClienteRestCorreo() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private static boolean isTextoPlano () {
		// Se comprueba si el formato va a ser en texto plano o no
		GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
		return "si".equalsIgnoreCase(gestorPropiedades.valorPropertie(PROPERTY_KEY_TEXTO_PLANO));
	}
	
	public static ResultBean enviarCorreo(String texto, Boolean textoPlano, String from, String subject, String recipient, String copia, String direccionesOcultas, FicheroBean... adjuntos) throws OegamExcepcion, IOException{
		if(texto == null){
			throw new OegamExcepcion("No se ha recibido el texto del mensaje");
		}
		if(recipient == null){
			throw new OegamExcepcion("No se han configurado los destinatarios del mensaje");
		}
		
		// Si no se informa del booleano textoplano, se toma el de por defecto
		Boolean plano = textoPlano!=null?textoPlano:isTextoPlano();

		ResultBean resultBean = null;
		
		//Seteo de la clase Correo con los parametros
		
		CorreoRequest correo=new CorreoRequest();
		List<FicheroAdjunto> ficherosAdjuntos=new ArrayList<FicheroAdjunto>();		
		
		for (FicheroBean adjunto: adjuntos) {
			FicheroAdjunto fichero = new FicheroAdjunto();
			if (adjunto.getFichero()!=null){
				fichero.setFicheroByte(org.apache.commons.io.FileUtils.readFileToByteArray(adjunto.getFichero()));
			}else if(adjunto.getFicheroByte()!=null){
				fichero.setFicheroByte(adjunto.getFicheroByte());
			}
			
			if(adjunto.getNombreYExtension()!=null){
				fichero.setNombreDocumento(adjunto.getNombreYExtension());
			}else if(adjunto.getNombreDocumento()!=null){
				fichero.setNombreDocumento(adjunto.getNombreDocumento());
			}else{
				fichero.setNombreDocumento("default");
			}
			if(fichero.getFicheroByte()!=null){
				ficherosAdjuntos.add(fichero);
			}
		}
		correo.setAdjuntos(ficherosAdjuntos);
		correo.setCopia(copia);
		correo.setDireccionesOcultas(direccionesOcultas);
		correo.setFrom(from);
		correo.setRecipient(recipient);
		correo.setSubject(subject);
		correo.setTexto(texto);
		correo.setTextoPlano(plano);
		
		resultBean=invocarRest(correo);

		
		return resultBean;
	}
	
	private static ResultBean invocarRest(CorreoRequest correo){
		ResultBean respuestaBean=new ResultBean(false);
		try {
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(
					JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
			Client client = Client.create(clientConfig);
			WebResource webResource = client
					.resource("http://172.26.0.80:8080/oegamGestorCorreos/services/enviarCorreo");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, correo);
			/*TODO Mas adelante se implementara correctamente el cliente y el servidor para devolver el objeto ResultBean directamente
			  if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			*/
			String respuestaCadena = response.getEntity(String.class);
			if (respuestaCadena.indexOf("KO")>=0){
				respuestaBean.setError(true);
				respuestaBean.addMensajeALista(respuestaCadena.substring(respuestaCadena.indexOf("_")+1, respuestaCadena.length()));
			}
			

		} catch (Exception e) {

			e.printStackTrace();

		}
		
		return respuestaBean;
	}

	public static void main(String[] args) {
		ResultBean resultado = new ResultBean();
		File file = new File("C:\\oegam\\Registro_de_Configuracion.xls");
		FicheroBean fichero= new FicheroBean();
		try {
			fichero.setFicheroByte(org.apache.commons.io.FileUtils.readFileToByteArray(file));
			fichero.setNombreDocumento("prueba.xls");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FicheroBean[] adjuntos={fichero};
		
		try {
			resultado=enviarCorreo("prueba",true,"javier.conde@globaltms.es","pruebaadjunto","javier.conde@globaltms.es","javier.conde@globaltms.es","javier.conde@globaltms.es",adjuntos);
		} catch (OegamExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
