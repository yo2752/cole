package org.gestoresmadrid.oegam2comun.peticionesurl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.Header;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.Fichero;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.Llamadas;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.Llamadas.Llamada;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.ParametrosPeticiones.Param;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import comunicaciones.http.HttpPostMethod;
import comunicaciones.http.HttpsProtocol;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import trafico.utiles.XMLManejadorErrores;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Component
public class EjecutadorPeticionesAUrl {

	private static final ILoggerOegam log = LoggerOegam.getLogger(EjecutadorPeticionesAUrl.class);
	private static final String CADENA_INICIO_CLAVE = "#·";
	private static final String CADENA_FIN_CLAVE = "·";
	private static final String XSD_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	
	public EjecutadorPeticionesAUrl(){
		
	}
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	public ResultBean ejecutarPeticionAUrlSegura(String rutaXMLPeticiones, String rutaXSD, Object beanValores){
		ResultBean rs = null;
		HttpsProtocol protocolo;
		try {
			protocolo = new HttpsProtocol();
			protocolo.establecerHttps();
		} catch (OegamExcepcion e) {
			log.equals("No se puede establecer el protocolo https. No se pueden hacer las llamadas a las url");
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("No se puede utilizar el servicio");
			return rs;
		} catch (Exception e) {
			log.equals("No se puede establecer el protocolo https. No se pueden hacer las llamadas a las url");
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("No se puede utilizar el servicio");
			return rs;
		}
		List<Llamada> peticiones=convertirXMLToBean(rutaXMLPeticiones, rutaXSD, beanValores);
		if (peticiones!=null){
			for (int i=0; i<peticiones.size() &&!(rs = hacerLlamada(peticiones.get(i), beanValores)).getError();i++){
			}
		}
		if (rs==null){
			rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("No hay nada para ejecutar o existe un error no localizado");
		}
		return rs;
	}
	
	
	private ResultBean hacerLlamada(Llamada peticion, Object beanValores) {
		if (peticion==null){
			ResultBean rs = new ResultBean();
			rs.setError(true);
			rs.addMensajeALista("No existe la petición");
		}
		List<Header> headers = null;
		if (peticion.getHeaderAEnviar()!=null && peticion.getHeaderAEnviar().getParam()!=null && !peticion.getHeaderAEnviar().getParam().isEmpty()){
			 headers = cargarHeaders(peticion.getHeaderAEnviar().getParam(),beanValores);
		}
		HttpPostMethod postMethod = new HttpPostMethod();
		if (peticion.getParametrosAEnviar()!=null && peticion.getParametrosAEnviar().getParam()!=null && !peticion.getParametrosAEnviar().getParam().isEmpty()){
			actualizarParametros(peticion.getParametrosAEnviar().getParam(), beanValores);
		}
		ResultBean resultBean = postMethod.ejecutarPeticion(peticion.getUrl(), headers, peticion.getParametrosAEnviar(), peticion.isDescarga());
		//TODO Faltaría procesar los parametros recibidos.
		if(!resultBean.getError()){
			//TODO Falta comprobar que ha ido correctamente. Hay que meter algún validador o algo.
			if (peticion.getHeaderARecibir()!=null && peticion.getHeaderARecibir().getParam()!=null && !peticion.getHeaderARecibir().getParam().isEmpty()){
				procesarHeadersRespuesta((Header[])resultBean.getAttachment(HttpPostMethod.RESPONSE_HEADERS),peticion.getHeaderARecibir().getParam(), beanValores);
			}
			if (peticion.getParametrosARecibir()!=null && peticion.getParametrosARecibir().getParam()!=null && !peticion.getParametrosARecibir().getParam().isEmpty()){
				procesarParametrosRespuesta((String) resultBean.getAttachment(HttpPostMethod.RESPUESTA),peticion.getParametrosARecibir().getParam(), beanValores);
			}
			if (resultBean.getAttachment(HttpPostMethod.BYTES_FICHERO)!=null && peticion.isDescarga()){
				try {
					if (peticion.getTipoMime().equals(new AutoDetectParser().getDetector().detect(new ByteArrayInputStream((byte[])resultBean.getAttachment(HttpPostMethod.BYTES_FICHERO)), new Metadata()).toString())){
						gestorDocumentos.guardarFichero(procesarFichero(peticion.getFicheroSkeleton(), (byte[])resultBean.getAttachment(HttpPostMethod.BYTES_FICHERO), beanValores));
					} else {
						resultBean = new ResultBean();
						resultBean.setError(true);
						resultBean.addMensajeALista("No se ha obtenido un fichero del tipo experado");
					}
				} catch (OegamExcepcion e) {
					log.error("No se ha podido guardar el fichero descargado");
					resultBean = new ResultBean();
					resultBean.setError(true);
					resultBean.addMensajeALista("No se ha podido guardar el fichero descargado");
				} catch (IOException e) {
					log.error("No se ha podido guardar el fichero descargado");
					resultBean = new ResultBean();
					resultBean.setError(true);
					resultBean.addMensajeALista("No se ha podido guardar el fichero descargado");
				}
			}
		}
		return resultBean;
		
	}


	private FicheroBean procesarFichero(Fichero ficheroSkeleton, byte[] attachment, Object beanValores) {
		FicheroBean ficheroBean = new FicheroBean();
		if (ficheroSkeleton==null || beanValores==null){
			return ficheroBean;
		}
		if (ficheroSkeleton.getTipoDocumento()!=null){
			try {
				ficheroBean.setTipoDocumento(generarValor(beanValores, ficheroSkeleton.getTipoDocumento()));
			} catch (IllegalAccessException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (InvocationTargetException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (NoSuchMethodException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			}
		}
		if (ficheroSkeleton.getSubtipo()!=null){
			try {
				ficheroBean.setSubTipo(generarValor(beanValores, ficheroSkeleton.getSubtipo()));
			} catch (IllegalAccessException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (InvocationTargetException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (NoSuchMethodException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			}
		}
		if (ficheroSkeleton.getFecha()!=null){
			if (esReferencia(ficheroSkeleton.getFecha())){
				ficheroBean.setFecha(recuperarFecha(beanValores, ficheroSkeleton.getFecha()));
			} else {
				ficheroBean.setFecha(new Fecha(ficheroSkeleton.getFecha()));
			}
		}
		if (ficheroSkeleton.getNombreDocumento()!=null){
			try {
				ficheroBean.setNombreDocumento(generarValor(beanValores, ficheroSkeleton.getNombreDocumento()));
			} catch (IllegalAccessException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (InvocationTargetException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (NoSuchMethodException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			}
		}
		if (ficheroSkeleton.getExtension()!=null){
			
			try {
				ficheroBean.setExtension(generarValor(beanValores, ficheroSkeleton.getExtension()));
			} catch (IllegalAccessException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (InvocationTargetException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			} catch (NoSuchMethodException e) {
				log.error("No se ha encontrado el valor para el Tipo de documento");
			}
		}
		ficheroBean.setFicheroByte(attachment);
		return ficheroBean;
	}


	private boolean esReferencia(String fecha) {
		return fecha.contains(CADENA_FIN_CLAVE) && fecha.contains(CADENA_INICIO_CLAVE);
	}


	private Fecha recuperarFecha(Object beanValores, String clave) {
		if (clave==null || beanValores==null){
			return null;
		}
		String campo = recuperarCampo(clave);
		try {
			return ((Fecha)PropertyUtils.getProperty(beanValores, campo));
		} catch (ClassCastException e) {
			log.error("No se ha encontrado la property, o no se puede convertir a fecha");
			return null;
		} catch (IllegalAccessException e) {
			log.error("No se ha encontrado la property, o no se puede convertir a fecha");
			return null;
		} catch (InvocationTargetException e) {
			log.error("No se ha encontrado la property, o no se puede convertir a fecha");
			return null;
		} catch (NoSuchMethodException e) {
			log.error("No se ha encontrado la property, o no se puede convertir a fecha");
			return null;
		}

	}


	private void procesarHeadersRespuesta(Header[] headersResponse, List<Param> headerARecibir, Object beanValores) {
		if (headerARecibir==null || headerARecibir.isEmpty()){
			return;
		}
		for (Param header : headerARecibir){
			try {
				String clave = recuperarCampo(header.getValor());
				BeanUtils.setProperty(beanValores, clave, getValorHeader(headersResponse, header.getNombre()));
			} catch (IllegalAccessException e) {
				log.error("No se ha encontrado el valor para el header");
			} catch (InvocationTargetException e) {
				log.error("No se ha encontrado el valor para el header");
			}
		}
	}

	private void procesarParametrosRespuesta(String respuesta, List<Param> parameters, Object beanValores) {
		if (parameters==null || parameters.isEmpty() || respuesta == null || respuesta.isEmpty()){
			return;
		}
		Document doc = Jsoup.parse(respuesta);
		if (doc != null) {
			for (Param parameter : parameters){
				try {
					String clave = recuperarCampo(parameter.getValor());
					Elements elementos = doc.getElementsByAttributeValue("name", parameter.getNombre());
					if (elementos.size() == 1) {
						String valor = elementos.isEmpty()?null:elementos.first().val();
						BeanUtils.setProperty(beanValores, clave, valor);
					} else if (!elementos.isEmpty()) {
						// es un array
						String[] valores = new String[elementos.size()];
						for (int i = 0; i< elementos.size(); i++) {
							valores[i] = elementos.get(i).val();
						}
					}
				} catch (IllegalAccessException e) {
					log.error("No se ha encontrado el valor para el header");
				} catch (InvocationTargetException e) {
					log.error("No se ha encontrado el valor para el header");
				}
			}
		}
	}


	private List<Header> cargarHeaders(List<Param> headerAEnviar, Object beanValores) {
		if (headerAEnviar==null || headerAEnviar.isEmpty()){
			return null;
		}
		List<Header> headers = new ArrayList<Header>();
		for (Param header : headerAEnviar){
			Header headerACargar = new Header();
			headerACargar.setName(header.getNombre());
			darValorAHeader(headerACargar, header.getValor(), beanValores);
			headers.add(headerACargar);
		}
		return headers;
	}


	private void actualizarParametros(List<Param> parametrosAEnviar, Object beanValores) {
		if (parametrosAEnviar!=null){
			for (Param parametro : parametrosAEnviar){
				try {
					parametro.setValor(generarValor(beanValores, parametro.getValor()));
				} catch (IllegalAccessException e) {
					log.error("No se ha encontrado el valor, y no se puede puede poner en la propiedad");
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error("No se ha encontrado el valor, y no se puede puede poner en la propiedad");
					log.error(e);
				} catch (NoSuchMethodException e) {
					log.error("No se ha encontrado el valor, y no se puede puede poner en la propiedad");
					log.error(e);
				}
			}
		}
	}


	private void darValorAHeader(Header header, String valor, Object beanValores) {
		try {
			header.setValue(generarValor(beanValores, valor));
		} catch (IllegalAccessException e) {
			log.error("No se ha encontrado el valor, y no se puede puede poner en la propiedad");
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error("No se ha encontrado el valor, y no se puede puede poner en la propiedad");
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error("No se ha encontrado el valor, y no se puede puede poner en la propiedad");
			log.error(e);
		}
	}


	private String generarValor(Object beanValores, String valor) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String patron = CADENA_INICIO_CLAVE+"[^·]+"+CADENA_FIN_CLAVE;
		Pattern p = Pattern.compile(patron);
		String texto = valor;
		Matcher encaja = p.matcher(texto);
		while (encaja.find()){
			String match = encaja.group();
			texto = recuperarValor(beanValores, match);
			if(texto == null) {
				texto = encaja.replaceFirst("");
			}else {
				texto = encaja.replaceFirst(recuperarValor(beanValores, match));
			}
			encaja = p.matcher(texto);
		}
		return texto;
	}

	private String recuperarValor(Object beanValores, String clave) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String campo = recuperarCampo(clave);
		return BeanUtils.getProperty(beanValores, campo);
	}


	private String recuperarCampo(String clave) {
		String campo = clave.replaceFirst(CADENA_INICIO_CLAVE, "");
		return campo.substring(0,campo.lastIndexOf(CADENA_FIN_CLAVE));
	}

	private Object fromXML(File fichero) throws JAXBException {
		Object obj = null;
		
		Unmarshaller um = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.peticionesurl.jaxb").createUnmarshaller();
		try {
			obj = um.unmarshal(fichero);
		}
		catch(Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		return obj;
	}

	private List<Llamada> convertirXMLToBean(String rutaXMLPeticiones, String rutaXSD, Object beanValores) {
		if (!validarContraSchema(rutaXMLPeticiones, rutaXSD)){
			return null;
		}
		try {
			return ((Llamadas)fromXML(new File(rutaXMLPeticiones))).getLlamada();
		} catch (ClassCastException e){
			log.error("Error al parsear el XML");
			log.error(e);
			return null;
		} catch (JAXBException e) {
			log.error("Error al parsear el XML");
			log.error(e);
			return null;
		}
		
		
		
//		List<Llamada> lista = new ArrayList<Llamada>();
//		
//		Llamada p = new Llamada();
//		p.setDescarga(false);
//		p.setUrl("https://sedecr.dgt.gob.es/WEB_EITV_INET/consulta_tarjeta_info.faces");
//		ParametrosPeticiones headerARecibir = new ParametrosPeticiones();
//		Param par0 = new Param();
//		par0.setNombre("Set-Cookie");
//		par0.setValor("#·cookie·");
//		List<Param> listParam = new ArrayList<Param>();
//		listParam.add(par0);
//		headerARecibir.setParam(listParam);
//		p.setHeaderARecibir(headerARecibir);
//		lista.add(p);
//		
//		Llamada p1 = new Llamada();
//		p1.setDescarga(false);
//		p1.setUrl("https://sedecr.dgt.gob.es/WEB_EITV_INET/consulta_tarjeta_info.faces");
//		ParametrosPeticiones par1 = new ParametrosPeticiones();
//		List<Param> listParam1 = new ArrayList<Param>();
//		Param param11 = new Param();
//		param11.setNombre("form1:_id11");
//		param11.setValor("Continuar");
//		listParam1.add(param11);
//		Param param12 = new Param();
//		param12.setNombre("form1");
//		param12.setValor("form1");
//		listParam1.add(param12);
//		par1.setParam(listParam1);
//		p1.setParametrosAEnviar(par1);
//		ParametrosPeticiones headerAEnviar1 = new ParametrosPeticiones();
//		List<Param> ppb1 = new ArrayList<Param>();
//		Param param13 = new Param();
//		param13.setNombre("Cookie");
//		param13.setValor("#·cookie·");
//		ppb1.add(param13);
//		headerAEnviar1.setParam(ppb1);
//		p1.setHeaderAEnviar(headerAEnviar1);
//		lista.add(p1);
//
//		Llamada p2 = new Llamada();
//		p2.setDescarga(false);
//		p2.setUrl("https://sedecr.dgt.gob.es/WEB_EITV_INET/consulta_tarjeta_busc.faces");
//		ParametrosPeticiones par2 = new ParametrosPeticiones();
//		List<Param> listParam2 = new ArrayList<Param>();
//		Param param21 = new Param();
//		param21.setNombre("form1:vinTxt");
//		param21.setValor("#·bastidor·");
//		listParam2.add(param21);
//		Param param22 = new Param();
//		param22.setNombre("form1:niveTxt");
//		param22.setValor("#·nive·");
//		listParam2.add(param22);
//		Param param23 = new Param();
//		param23.setNombre("form1:_id17");
//		param23.setValor("Buscar");
//		listParam2.add(param23);
//		Param param24 = new Param();
//		param24.setNombre("form1");
//		param24.setValor("form1");
//		listParam2.add(param24);
//		par2.setParam(listParam2);
//		p2.setParametrosAEnviar(par2);
//		
//		ParametrosPeticiones headerAEnviar2 = new ParametrosPeticiones();
//		List<Param> ppb2 = new ArrayList<Param>();
//		Param param25 = new Param();
//		param25.setNombre("Cookie");
//		param25.setValor("#·cookie·");
//		ppb2.add(param25);
//		headerAEnviar2.setParam(ppb2);
//		p2.setHeaderAEnviar(headerAEnviar2);
//		lista.add(p2);
//		
//		Llamada p3 = new Llamada();
//		p3.setDescarga(true);
//		p3.setUrl("https://sedecr.dgt.gob.es/WEB_EITV_INET/consulta_tarjeta_dato.faces");
//		ParametrosPeticiones par3 = new ParametrosPeticiones();
//		List<Param> env3 = new ArrayList<Param>();
//		Param param31 = new Param();
//		param31.setNombre("form1:_id15");
//		param31.setValor("obtener una copia de la tarjeta en PDF");
//		env3.add(param31);
//		Param param32 = new Param();
//		param32.setNombre("form1");
//		param32.setValor("form1");
//		env3.add(param32);
//		par3.setParam(env3);
//		p3.setParametrosAEnviar(par3);
//		
//		ParametrosPeticiones headerAEnviar3 = new ParametrosPeticiones();
//		List<Param> h3 = new ArrayList<Param>();
//		Param param33 = new Param();
//		param33.setNombre("Cookie");
//		param33.setValor("#·cookie·");
//		h3.add(param33);
//		headerAEnviar3.setParam(h3);
//		p3.setHeaderAEnviar(headerAEnviar3);
//		
//		Fichero ficheroSkeleton = new Fichero();
//		ficheroSkeleton.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
//		ficheroSkeleton.setTipoDocumento(ConstantesGestorFicheros.MATE);
//		ficheroSkeleton.setSubtipo("PREMATRICULADOS");
//		ficheroSkeleton.setFecha("#·fecha·");
//		ficheroSkeleton.setNombreDocumento("#·nive·");
//		p3.setFicheroSkeleton(ficheroSkeleton);
//		p3.setTipoMime("application/pdf");
//		lista.add(p3);
//		
//		return lista;
	}


	private boolean validarContraSchema(String rutaXMLPeticiones, String rutaXSD) {
		Schema schema;
		try {
			schema = SchemaFactory.newInstance(XSD_SCHEMA).newSchema(new StreamSource(rutaXSD));
			Validator validator = schema.newValidator();
			XMLManejadorErrores errores = new XMLManejadorErrores();
			validator.setErrorHandler(errores);
			validator.validate(new StreamSource(rutaXMLPeticiones));
			if (errores!=null && errores.getListaErrores()!=null && !errores.getListaErrores().isEmpty()) {
				log.error("Error al procesar el xml. No se pueden realizar las llamadas");
				for (String error : errores.getListaErrores()){
					log.error(error);
				}
				return false;
			}
		} catch (SAXException e) {
			log.error("Error recuperando el schema. No se pueden realizar las llamadas");
			log.error(e);
			return false;
		} catch (IOException e) {
			log.error("Error leyendo el fichero con las llamadas. No se pueden realizar las llamadas");
			return false;
		}
		return true;
	}


	private String getValorHeader(Header[] headers, String nombre) {
		String valor = null;
		for(Header header : headers){
			if(header.getName().equals(nombre)){
				if (valor == null) {
					valor = header.getValue();
				} else if (valor.endsWith(";")) {
					valor += header.getValue();
				} else {
					valor += "; " + header.getValue();
				}
			}
		}
		return valor;
	}

}
