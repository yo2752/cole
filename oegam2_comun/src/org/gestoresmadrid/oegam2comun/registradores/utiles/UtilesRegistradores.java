package org.gestoresmadrid.oegam2comun.registradores.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;

import javax.xml.rpc.ServiceException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.registradores.ws.Edoc;
import org.gestoresmadrid.oegam2comun.registradores.ws.EdocService;
import org.gestoresmadrid.oegam2comun.registradores.ws.EdocServiceLocator;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;

import escrituras.beans.ResultBean;
import net.consejogestores.sercon.core.model.integracion.dto.RegistroRequest;
import net.consejogestores.sercon.core.model.integracion.dto.RegistroResponse;
import net.consejogestores.sercon.ws.model.integracion.RegistroService;
import net.consejogestores.sercon.ws.model.integracion.RegistroServiceServiceLocator;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;

public class UtilesRegistradores {

	private static final ILoggerOegam log = LoggerOegam.getLogger("Registradores");

	// Método que envía mediante el web service el fichero construido:
	public static byte[] enviarTramite(File fichero, String idTramite) throws OegamExcepcion, IOException, NullPointerException, ServiceException {
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Inicio de envio al WS del registro.");
		EdocService service = new EdocServiceLocator();
		URL url = new URL(ContextoSpring.getInstance().getBean(GestorPropiedades.class).valorPropertie("registradores.webService.url"));

		Edoc edoc = service.getedoc(url);
		// Establecer el fichero como un array de bytes:
		byte[] envio = establecerEnvio(fichero);

		cargarAlmacenesRegistro();

		// Invocar al método expuesto por el web service:
		byte[] respuesta = edoc.enviar(envio);

		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Fin correcto del envio al WS del registro.");
		return respuesta;
	}

	// Método que envía mediante el web service el fichero construido:
	public static RegistroResponse enviarTramiteSercon(String idTramite, String cifPlataforma, String codColegio, String nifGestor, String numColegiadoGestor, File fichero) throws OegamExcepcion,
			IOException, ServiceException {
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Inicio de envio al WS de sercon.");
		RegistroServiceServiceLocator service = new RegistroServiceServiceLocator();

		URL url = new URL(ContextoSpring.getInstance().getBean(GestorPropiedades.class).valorPropertie("sercon.webService.url"));

		RegistroService registroService = service.getRegistroService(url);

		RegistroRequest request = establecerEnvioSercon(idTramite, cifPlataforma, codColegio, nifGestor, numColegiadoGestor, fichero);

		RegistroResponse response = registroService.envioMensaje(request);

		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Fin correcto del envio al WS de sercon.");
		return response;
	}

	// Establece el contenido del envio al servicio web a partir de un fichero xml:
	public static byte[] establecerEnvio(File fichero) throws IOException {
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Estableciendo el envio...");
		// Convierte el xml en un array de bytes:
		FileInputStream fis = new FileInputStream(fichero);
		byte[] envio = IOUtils.toByteArray(fis);
		log.info(Claves.CLAVE_LOG_PROCESO_WREG + "Envio establecido correctamente.");
		return envio;
	}

	public static RegistroRequest establecerEnvioSercon(String idTramite, String cifPlataforma, String codColegio, String nifGestor, String numColegiadoGestor, File fichero) throws IOException {
		RegistroRequest request = new RegistroRequest();
		request.setXml(establecerEnvio(fichero));
		request.setCifPlataforma(cifPlataforma);
		request.setCodColegio(codColegio);
		request.setIdTramite(idTramite);
		request.setNifGestor(nifGestor);
		request.setNumColegiadoGestor(numColegiadoGestor);
		return request;
	}

	public static boolean aEstadoIniciado(TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		if (tramiteRegistro != null && !EstadoTramiteRegistro.Iniciado.equals(tramiteRegistro.getEstado())) {
			ServicioTramiteRegistro servicioTramiteRegistro = ContextoSpring.getInstance().getBean(ServicioTramiteRegistro.class);
			ResultBean respuesta = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getEstado(), new BigDecimal(EstadoTramiteRegistro.Iniciado
					.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
			if (respuesta != null && respuesta.getError()) {
				return false;
			}
		}
		return true;
	}

	public static boolean permitidaModificacion(BigDecimal estadoActual) {
		if (new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()).equals(estadoActual) || new BigDecimal(
				EstadoTramiteRegistro.Finalizado_error.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Calificado_Defectos.getValorEnum()).equals(estadoActual)
				|| new BigDecimal(EstadoTramiteRegistro.Inscrito_Parcialmente.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Confirmada_Presentacion.getValorEnum())
						.equals(estadoActual)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean permitidoEnvio(BigDecimal estadoActual) {
		if (new BigDecimal(EstadoTramiteRegistro.Firmado.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Presentacion.getValorEnum()).equals(
				estadoActual) || new BigDecimal(EstadoTramiteRegistro.Confirmada_Presentacion.getValorEnum()).equals(estadoActual) || new BigDecimal(
						EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum()).equals(estadoActual) || new BigDecimal(
								EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Inscrito_Parcialmente
										.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum()).equals(estadoActual)
				|| new BigDecimal(EstadoTramiteRegistro.Calificado_Defectos.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Validado.getValorEnum()).equals(
						estadoActual)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean permitidoFirmarAcuse(BigDecimal estadoActual) {
		if (new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Denegacion.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Total
				.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum()).equals(estadoActual) || new BigDecimal(
						EstadoTramiteRegistro.Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Presentacion
								.getValorEnum()).equals(estadoActual)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean permitidoEnvioEscrituras(BigDecimal estadoActual) {
		if (new BigDecimal(EstadoTramiteRegistro.Iniciado.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Firmado.getValorEnum()).equals(estadoActual) || new BigDecimal(
				EstadoTramiteRegistro.Pendiente_Firma_Acuse_Presentacion.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Confirmada_Presentacion.getValorEnum()).equals(
						estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Total.getValorEnum()).equals(estadoActual) || new BigDecimal(
								EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Parcial.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Inscrito_Parcialmente
										.getValorEnum()).equals(estadoActual) || new BigDecimal(EstadoTramiteRegistro.Pendiente_Firma_Acuse_Calificado_Defectos.getValorEnum()).equals(estadoActual)
				|| new BigDecimal(EstadoTramiteRegistro.Calificado_Defectos.getValorEnum()).equals(estadoActual)) {
			return true;
		} else {
			return false;
		}
	}

	public static String hashPassword(String password) throws Exception {
		String hashword = null;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(password.getBytes());
		BigInteger hash = new BigInteger(1, md5.digest());
		hashword = hash.toString(16);
		return hashword;
	}

	// Recibe como parámetro la cadena en base 64 del elemento <Contenido>
	// Devuelve una cadena en base 64 que es a su vez un hash md5 de la cadena
	// recibida. Útil para el elemento <Contenido> de los acuses de notificación
	public static String obtenerHashMd5(String stringBase64) throws Exception {
		byte[] bytesDelContenido = doBase64Decode(stringBase64, "UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] elDigest = md.digest(bytesDelContenido);
		return doBase64Encode(elDigest);
	}

	// Sobrescrito que recibe un file
	public static byte[] obtenerHashMd5(File fichero) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(fichero);
		byte[] array = IOUtils.toByteArray(fileInputStream);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] elDigest = md.digest(array);
		return elDigest;
	}

	// Entorno estático para la decodificación de una cadena en base 64
	public static byte[] doBase64Decode(String bas64, String codificacion) throws Exception {
		if (codificacion != null) {
			return Base64.decodeBase64(bas64.getBytes());
		} else {
			return Base64.decodeBase64(bas64.getBytes(codificacion));
		}
	}

	// Entorno estático para la codificación de un array de bytes en base 64
	public static String doBase64Encode(byte[] array) {
		return new String(Base64.encodeBase64(array));
	}

	// El esquema no incluye dos atributos requeridos por el registro con lo que la
	// jerarquía Jaxb no tiene 'sets' para ellos con lo que hay que meterlos 'a mano'
	public static String incorporarAtributos(String cadenaXML) throws OegamExcepcion {
		String urlXSD = ContextoSpring.getInstance().getBean(GestorPropiedades.class).valorPropertie("registradores.urlXSD");
		String atributos = " xsi:noNamespaceSchemaLocation=\"" + urlXSD + "\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
		StringBuilder modificada = new StringBuilder();
		// Para poder hacer inserciones en la cadena:
		modificada.append(cadenaXML);
		// Localiza la posición exacta de la segunda inserción:
		int pos = modificada.indexOf("/>");
		// Inserta la cadena en la posición:
		modificada.insert(pos, atributos);
		return modificada.toString();
	}

	// Establece las propiedades de sistema para que utilice los almacenes
	// que contienen el certificado y la clave pública del registro:
	public static void cargarAlmacenesRegistro() throws OegamExcepcion {
		GestorPropiedades gestorPropiedades = ContextoSpring.getInstance().getBean(GestorPropiedades.class);
		System.setProperty("javax.net.ssl.trustStore", gestorPropiedades.valorPropertie("registradores.ruta.almacen.truststore"));
		System.setProperty("javax.net.ssl.trustStorePassword",gestorPropiedades.valorPropertie("registradores.password.almacen.truststore"));
		System.setProperty("javax.net.ssl.keyStore", gestorPropiedades.valorPropertie("registradores.ruta.almacen.keystore"));
		System.setProperty("javax.net.ssl.keyStorePassword", gestorPropiedades.valorPropertie("registradores.password.almacen.keystore"));
	}
}
