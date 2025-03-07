
package trafico.inteve;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.ParametrosPeticiones;
import org.gestoresmadrid.oegam2comun.peticionesurl.jaxb.ParametrosPeticiones.Param;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import comunicaciones.http.HttpPostMethod;
import comunicaciones.http.HttpsProtocol;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * Expone el método solicitarInforme() que devolverá un ResultBean con el informe en un attachment
 * El solicitarInforme invoca 3 métodos privados para la obtención del informe de INTEVE:
 * primeraLlamada(): Se obtiene por GET el campo TOKEN y la cookie JSESSIONID requeridos para la segunda llamada
 * segundaLlamada(): Se le pasan los datos por POST del vehículo y la tasa y se obtiene un link
 * terceraLlamada(): Invocación al link para la descarga del informe
 */
public class AplicacionInteve {

	private static final ILoggerOegam log = LoggerOegam.getLogger(AplicacionInteve.class);

	private String tasa;
	private String matricula;
	private String bastidor;
	private String tipoPago;
	private MotivoConsultaInteve motivoConsulta;
	private String jSessionId;
	private String token;

	private static final String PDF = "pdf";
	private static final String XML = "xml";
	private static final String DOCUMENTO_ANTECEDENTE = "documentoAntecedentes";
	private static final String JUSTIFICANTE_REGISTRO_SALIDA = "justificanteRegistroSalida";
	private static final String TYPE_1_DOCUMENTO_ANTECEDENTE_PDF		= "1"; // 1 = Informe de antecedentes del vehículo en formato pdf.
	private static final String TYPE_2_JUSTIFICANTE_REGISTRO_SALIDA_PDF = "2"; // 2 = Justificante Registro de Salida del informe de la DGT en formato pdf.
	private static final String TYPE_3_JUSTIFICANTE_REGISTRO_SALIDA_XML = "3"; // 3 = Justificante Registro de Salida del informe de la DGT en formato xml.

	private byte[] bytesFichero;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	public AplicacionInteve() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Constructor que recibe todos los parámetros requeridos para la generación de un informe
	 * @param tasa
	 * @param matricula
	 * @param bastidor
	 * @param motivoConsulta
	 * @throws OegamExcepcion 
	 * @throws Exception 
	 */
	public AplicacionInteve(String tasa, String matricula, String bastidor, MotivoConsultaInteve motivoConsulta) throws OegamExcepcion, Exception{
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		tipoPago = ConstantesInteve.VALOR_PARAMETRO_TIPO_PAGO;
		this.tasa = tasa;
		this.matricula = matricula;
		this.bastidor = bastidor;
		this.motivoConsulta = motivoConsulta;

		// Establece el protocolo https:
		HttpsProtocol protocolo = new HttpsProtocol();
		protocolo.establecerHttps();
	}

	/**
	 * Método público para la generación de un informe
	 * @return ResultBean
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	public ResultBean solicitarInforme() throws OegamExcepcion, Exception{
		ResultBean resultBean = new ResultBean();
		// ¿Están los parámetros requeridos?
		String mensajeParametros = parametrosEstablecidos();
		if(!mensajeParametros.equals("ok")){
			// No
			resultBean.setError(true);
			resultBean.setMensaje(mensajeParametros);
			return resultBean;
		}else{
			// Si
			resultBean = primeraLlamada();
			if(resultBean.getError()){
				// Error tras la primera llamada:
				return resultBean;
			}else{
				// Todo ok tras la primera llamada:
				resultBean = segundaLlamada();
				if(resultBean.getError()){
					// Error tras la segunda llamada:
					return resultBean;
				}else{
					// Todo ok tras la segunda llamada:
					resultBean = terceraLlamada();
					if(resultBean.getError()){
						// Error tras la tercera llamada:
						return resultBean;
					}
				}
			}
		}
		// Todo OK tras la tercera llamada:
		return resultBean;
	}

	/**
	 * Verifica que antes de iniciar la comunicación http se han establecido
	 * todos los parámetros requeridos
	 * @return ok o mensaje con los parámetros que faltan
	 */
	private String parametrosEstablecidos(){
		String respuesta = "Faltan los siguientes parámetros requeridos: ";
		boolean faltan = false;
		if(tasa == null || tasa.equals("")){
			respuesta += " tasa";
			faltan = true;
		}
		if((matricula == null || matricula.equals("")) && 
				((bastidor == null || bastidor.equals("")))){
			respuesta += " matricula y/o bastidor";
			faltan = true;
		}
		if(motivoConsulta == null){
			respuesta += " motivo de la consulta";
			faltan = true;
		}
		if(faltan){
			return respuesta;
		}else{
			return "ok"; 
		}
	}

	/**
	 * Primera llamada GET a la página principal del servicio para obtener un
	 * parámetro token y el id de la sesión
	 * @return ResultBean
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	private ResultBean primeraLlamada() throws OegamExcepcion, Exception{

		ResultBean resultBean = new ResultBean();

		// Establece el protocolo https:
		HttpsProtocol protocolo = new HttpsProtocol();
		protocolo.establecerHttps();
		GetMethod getMethod = new GetMethod(gestorPropiedades.valorPropertie(ConstantesInteve.REF_PROP_INTEVE_LLAMADA1));
		HttpClient httpClient = new HttpClient();
		int codigoRespuesta = httpClient.executeMethod(getMethod);
		// Comprueba el código de respuesta recibido:
		if(codigoRespuesta != HttpStatus.SC_OK){
			resultBean.setError(true);
			// El mensaje del resultBean es el texto asociado al código recibido:
			resultBean.setMensaje("Error en la conexión con la página principal del servicio INTEVE: " +
					HttpStatus.getStatusText(codigoRespuesta));
			return resultBean;
		}
		String respuesta = getMethod.getResponseBodyAsString();
		// Verifica si la respuesta html tiene el div de los mensajes de error:
		if(respuesta.contains(ConstantesInteve.DIV_MENSAJE_ERROR)){
			resultBean.setError(true);
			// Extrae de la respuesta el mensaje de error recibido:
			resultBean.setMensaje(extraerMensajeError(respuesta));
			if(resultBean.getMensaje() == null){
				resultBean.setMensaje("Error en la conexión con la página principal del servicio INTEVE");
			}
			return resultBean;
		}
		Header[] headers = getMethod.getResponseHeaders();
		// Libera la conexión:
		getMethod.releaseConnection();
		setToken(respuesta);
		// En este punto debe tener valor la propiedad token, imprescindible para la segunda llamada:
		if(token == null || token.equals("")){
			resultBean.setError(true);
			resultBean.setMensaje("No se ha podido establecer el parámetro 'token'");
			return resultBean;
		}
		setjSessionId(headers);
		// En este punto debe tener valor la propiedad jSessionId, imprescindible para la segunda llamada:
		if(jSessionId == null || jSessionId.equals("")){
			resultBean.setError(true);
			resultBean.setMensaje("No se ha podido establecer el parámetro 'jSessionId'");
			return resultBean;
		}

		// Todo OK:
		resultBean.setError(false);
		resultBean.setMensaje(HttpStatus.getStatusText(codigoRespuesta));
		return resultBean;
	}

	/**
	 * Genera la solicitud de informe mediante el envío por post de los parámetros requeridos
	 * obtiene como respuesta un html con el link para la descarga del informe
	 * @return ResultBean
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	private ResultBean segundaLlamada() throws OegamExcepcion, Exception{

		String url = gestorPropiedades.valorPropertie(ConstantesInteve.REF_PROP_INTEVE_LLAMADA2)+";jsessionid="+jSessionId.substring(jSessionId.indexOf("=")+1);

		List<Param> parametros = new ArrayList<>();
		// Establece los parámetros de la petición:
		if(bastidor != null && !bastidor.equals("")){
			anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_BASTIDOR,bastidor);
		}
		if(matricula != null && !matricula.equals("")){
			anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_MATRICULA, matricula);
		}
		anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_MOTIVO_CONSULTA, motivoConsulta.getValorEnum());
		anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_TOKEN, token);
		anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_TASA, tasa);
		anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_TIPO_PAGO, tipoPago);

		// Mete en la llamada la cookie JSESSIONID obtenida en la primera llamada como cabecera:
		List<Header> headers = new ArrayList<>();
		headers.addAll(Arrays.asList(AplicacionInteve.getCabeceras()));
		Header cookieHeader = new Header();
		cookieHeader.setName("Cookie");
		cookieHeader.setValue(jSessionId);
		headers.add(cookieHeader);

		// Establece el protocolo https:
		HttpsProtocol protocol = new HttpsProtocol();
		protocol.establecerHttps();

		// Crea el post method:
		HttpPostMethod postMethod = new HttpPostMethod();
		ParametrosPeticiones parametrosPeticiones = new ParametrosPeticiones();
		parametrosPeticiones.setParam(parametros);
		ResultBean resultBean = postMethod.ejecutarPeticion(url, headers, parametrosPeticiones, false);
		if(resultBean.getError()){
			// Error al ejecutar la petición:
			return resultBean;
		}else{
			String respuesta = (String)resultBean.getAttachment("respuesta");
			if(respuesta.contains(ConstantesInteve.DIV_MENSAJE_ERROR)){
				// Mensaje de error embebido en la respuesta html:
				resultBean.setError(true);
				resultBean.setMensaje(extraerMensajeError(respuesta));
			}
		}
		setToken((String)resultBean.getAttachment("respuesta"));
		// En este punto debe tener valor la propiedad token, imprescindible para la segunda llamada:
		if(token == null || token.equals("")){
			resultBean.setError(true);
			resultBean.setMensaje("No se ha podido establecer el parámetro 'token'");
			return resultBean;
		}
		return resultBean;
	}

	/**
	 * Añade un parametro a la lista de parametros, a partir de su nombre y valor
	 * @param parametros
	 * @param nombre
	 * @param valor
	 */
	private void anadirParametro(List<Param> parametros, String nombre, String valor) {
		Param param = new Param();
		param.setNombre(nombre);
		param.setValor(valor);
		parametros.add(param);
	}

	/**
	 * Invocación por POST al link de descarga del fichero en zip contenedor del informe
	 * Almacena en disco el fichero recibido
	 * @return ResultBean
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	private ResultBean terceraLlamada() throws OegamExcepcion, Exception{
		String url = gestorPropiedades.valorPropertie(ConstantesInteve.REF_PROP_INTEVE_LLAMADA3)+";jsessionid="+jSessionId.substring(jSessionId.indexOf("=")+1);
		// Establece los parámetros de la petición:
		List<Param> parametros = new ArrayList<>();
		anadirParametro(parametros, ConstantesInteve.NOMBRE_PARAMETRO_TOKEN, token);

		// Mete en la llamada la cookie JSESSIONID obtenida en la primera llamada como cabecera:
		List<Header> headers = new ArrayList<>();
		headers.addAll(Arrays.asList(AplicacionInteve.getCabeceras()));
		Header cookieHeader = new Header();
		cookieHeader.setName("Cookie");
		cookieHeader.setValue(jSessionId);
		headers.add(cookieHeader);

		// Establece el protocolo https:
		HttpsProtocol protocol = new HttpsProtocol();
		protocol.establecerHttps();

		// Crea el post method:
		HttpPostMethod postMethod = new HttpPostMethod();
		ParametrosPeticiones parametrosPeticiones = new ParametrosPeticiones();
		parametrosPeticiones.setParam(parametros);
		ResultBean resultBean = postMethod.ejecutarPeticion(url, headers, parametrosPeticiones, true);

		if(resultBean.getError()){
			// Error al ejecutar la petición
			return resultBean;
		}else{
			String respuesta = (String)resultBean.getAttachment("respuesta");
			if(respuesta.contains(ConstantesInteve.DIV_MENSAJE_ERROR)){
				// Mensaje de error embebido en la respuesta HTML:
				resultBean.setError(true);
				resultBean.setMensaje(extraerMensajeError(respuesta));
				return resultBean;
			}
			// Recupera los bytes del fichero
			bytesFichero = (byte[])resultBean.getAttachment("bytesFichero");
		}

		return resultBean;
	}

	/**
	 * Recupera mediante constantes las cabeceras de la petición por POST
	 * @return Array de headers
	 */
	private static Header[] getCabeceras(){
		Header[] cabeceras = new Header[7];
		Header cabecera0 = new Header();
		cabecera0.setName(ConstantesInteve.CABECERA_ACCEPT_ENCODING_NOMBRE);
		cabecera0.setValue(ConstantesInteve.CABECERA_ACCEPT_ENCODING_VALOR);
		cabeceras[0] = cabecera0;
		Header cabecera1 = new Header();
		cabecera1.setName(ConstantesInteve.CABECERA_ACCEPT_LANGUAGE_NOMBRE);
		cabecera1.setValue(ConstantesInteve.CABECERA_ACCEPT_LANGUAGE_VALOR);
		cabeceras[1] = cabecera1;
		Header cabecera2 = new Header();
		cabecera2.setName(ConstantesInteve.CABECERA_USER_AGENT_NOMBRE);
		cabecera2.setValue(ConstantesInteve.CABECERA_USER_AGENT_VALOR);
		cabeceras[2] = cabecera2;
		Header cabecera3 = new Header();
		cabecera3.setName(ConstantesInteve.CABECERA_ACCEPT_NOMBRE);
		cabecera3.setValue(ConstantesInteve.CABECERA_ACCEPT_VALOR);
		cabeceras[3] = cabecera3;
		Header cabecera4 = new Header();
		cabecera4.setName(ConstantesInteve.CABECERA_CONNECTION_NOMBRE);
		cabecera4.setValue(ConstantesInteve.CABECERA_CONNECTION_VALOR);
		cabeceras[4] = cabecera4;
		Header cabecera5 = new Header();
		cabecera5.setName(ConstantesInteve.CABECERA_HOST_NOMBRE);
		cabecera5.setValue(ConstantesInteve.CABECERA_HOST_VALOR);
		cabeceras[5] = cabecera5;
		Header cabecera6 = new Header();
		cabecera6.setName(ConstantesInteve.CABECERA_REFERER_NOMBRE);
		cabecera6.setValue(ConstantesInteve.CABECERA_REFERER_VALOR);
		cabeceras[6] = cabecera6;

		return cabeceras;
	}

	/**
	 * Extrae de una respuesta html el mensaje de error contenido en un div de clase 'centrado'
	 * @param respuesta
	 * @return cadena del mensaje de error
	 */
	private String extraerMensajeError(String respuesta){
		// Saca el div class=centrado de la respuesta:
		String divCentrado = respuesta.substring(respuesta.indexOf(ConstantesInteve.DIV_MENSAJE_ERROR));
		// Quita la declaración del div de la cadena:
		divCentrado = divCentrado.substring(ConstantesInteve.DIV_MENSAJE_ERROR.length() + 1);
		// Corta el div tras el primer salto de línea:
		divCentrado = divCentrado.substring(0, divCentrado.indexOf("<br>") -1);
		// Quita los espacios:
		return divCentrado.trim();
	}

	/**
	 * Si la propiedad bytesFichero tiene un valor válido, crea un fichero lo almacena y lo nombra con los valores
	 * de los parámetros con ese array de bytes
	 * @param ruta
	 * @param nombreFichero
	 * @return false en caso de fallo, true si el fichero se nombra y se almacena de forma correcta
	 * @throws Exception
	 */
	public boolean etiquetarYalmacenarInforme(String ruta, String nombreFichero) throws Exception{
		// Verifica que hay un informe para etiquetar y almacenar:
		if(bytesFichero == null || bytesFichero.length == 0){
			return false;
		}else{
			// Crea el fichero, lo etiqueta y lo almacena:
			utiles.creaFicheroConBytes(ruta + nombreFichero, bytesFichero);
			return true;
		}
	}

	/**
	 * Borra del zip recibido del servicio inteve los ficheros que no se encuentren
	 * en el properties como propiedad REF_PROP_DESCARGA_INTEVE = "tipo.fichero.descarga.inteve"
	 * @param bytesInforme
	 * @param sProperties nombre del properties donde se encuentra la propiedad
	 * @return los bytes del zip sin el xml
	 * @throws IOException 
	 */
	public static byte[] descargarSeleccionJustiticantesZip(byte[] bytesInforme) throws IOException{
		byte[] bytesReturn = null;
		String ficherosADescargas = "";
		ficherosADescargas = ContextoSpring.getInstance().getBean(GestorPropiedades.class).valorPropertie(ConstantesInteve.REF_PROP_DESCARGA_INTEVE);

		// Leer el zip
		InputStream in = new ByteArrayInputStream(bytesInforme);
		ZipInputStream zis = new ZipInputStream(in);

		//Archivo temporal, el zip:
		OutputStream outPut = new ByteArrayOutputStream();
		ZipOutputStream zipOutput = new ZipOutputStream(outPut);

		ZipEntry entry = null;
		while((entry = zis.getNextEntry()) != null) {
			boolean empaquetar = false;
			if (esDocumentoAntecentePDF(entry, ficherosADescargas)
					|| esJustificanteRegistroSalidaPDF(entry, ficherosADescargas)
					|| esJustificanteRegistroSalidaXML(entry, ficherosADescargas)){
				empaquetar = true;
			}
			if (empaquetar) {
				ByteArrayOutputStream output = null;
				try {
					output = new ByteArrayOutputStream();
					int data = 0;
					while ((data = zis.read()) != -1) {
						output.write(data);
					}
					zipOutput.putNextEntry(entry);
					zipOutput.write(output.toByteArray());
					zipOutput.closeEntry();

				} catch (Exception e) {
					log.error("Error recuperando entradas antiguas", e);
				} finally {
					if (output != null) {
						try {
							output.close();
						} catch (IOException ioe) {
							log.error("Error cerrando ByteArrayOutputStream", ioe);
						}
					}
				}
			}
		}
		zipOutput.flush();
		zipOutput.close();

		// Guarda los bytes del return:

		in.close();
		bytesReturn=((ByteArrayOutputStream)outPut).toByteArray();
		outPut.close();
		return bytesReturn;
	}

	/**
	 * Setea la propiedad jSessionId buscando la cabecera Set-Cookie de la response y verificando
	 * que contiene la cookie: 'JSESSIONID'
	 * @param headers
	 */
	private void setjSessionId(Header[] headers) {
		for(Header header : headers){
			if(header.getName().equals(ConstantesInteve.NOMBRE_CABECERA_CONTENEDORA_JSESSIONID)){
				// JSESSIONID=00019BMJCKpP7bhqOabOysxI8Gr:-1100MQ2; Path=/
				if(header.getValue() != null && !header.getValue().equals("")){
					String valorCabecera = header.getValue();
					// Comprueba que la cookie es la JSESSIONID:
					if(valorCabecera.contains(ConstantesInteve.COOKIE_SESSION) || valorCabecera.contains(ConstantesInteve.COOKIE_SESSION_INTV)){
						// Corta el valor de la cookie en el punto y coma:
						valorCabecera = valorCabecera.substring(0, valorCabecera.indexOf(";"));
						jSessionId = valorCabecera;
					}
					return;
				}
			}
		}
	}

	//Determina si la extension es .pdf y el tipo de documento
	public static boolean esDocumentoAntecentePDF(ZipEntry zip, String ficherosADescargas){
		try {
			if(zip.getName().endsWith(PDF) && zip.getName().startsWith(DOCUMENTO_ANTECEDENTE) && ficherosADescargas.contains(TYPE_1_DOCUMENTO_ANTECEDENTE_PDF))
				return true;
		} catch (Exception e) {
			log.error("Error esDocumentoAntecentePDF: " + e);
		}
		return false;
	}
	// Determina si la extension es .pdf y el tipo de documento
	public static boolean esJustificanteRegistroSalidaPDF(ZipEntry zip, String ficherosADescargas){
		try {
			if(zip.getName().endsWith(PDF) && zip.getName().startsWith(JUSTIFICANTE_REGISTRO_SALIDA) && ficherosADescargas.contains(TYPE_2_JUSTIFICANTE_REGISTRO_SALIDA_PDF))
				 return true;
		} catch (Exception e) {
			log.error("Error esJustificanteRegistroSalidaPDF: " + e);
		}
		return false;
	}

	//Determina si la extension es .xml y el tipo de documento
	public static boolean esJustificanteRegistroSalidaXML(ZipEntry zip, String ficherosADescargas){
		try {
			if(zip.getName().endsWith(XML) && zip.getName().startsWith(JUSTIFICANTE_REGISTRO_SALIDA) && ficherosADescargas.contains(TYPE_3_JUSTIFICANTE_REGISTRO_SALIDA_XML))
				return true;
		} catch (Exception e) {
			log.error("Error esJustificanteRegistroSalidaXML: " + e);
		}
		return false;
	}

	// GET & SET
	public String getToken() {
		return token;
	}
	/**
	 * Busca en la respuesta html el parámetro TOKEN requerido para sucesivas llamadas y extrae su
	 * atributo valor para setear la propiedad token
	 * @param respuesta
	*/
	private void setToken(String respuesta) {
		// Muestra recibida : <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="8f26e51da77677229668515ba86a4e30">
		if(respuesta.indexOf(ConstantesInteve.NOMBRE_PARAMETRO_TOKEN) != -1){
			// Saca el tag token de la respuesta:
			String token = respuesta.substring(respuesta.indexOf(ConstantesInteve.NOMBRE_PARAMETRO_TOKEN));
			// Corta el token tras el tag:
			token = token.substring(0, token.indexOf(">") -1);
			// Quita las comillas:
			String tokenSinComillas = token.replace("\"","");
			// Extrae el value del tag:
			String valorToken = tokenSinComillas.substring(tokenSinComillas.indexOf("=") + 1);
			this.token = valorToken;
		}
	}

	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public MotivoConsultaInteve getMotivoConsulta() {
		return motivoConsulta;
	}
	public void setMotivoConsulta(MotivoConsultaInteve motivoConsulta) {
		this.motivoConsulta = motivoConsulta;
	}
	public String getjSessionId() {
		return jSessionId;
	}
}