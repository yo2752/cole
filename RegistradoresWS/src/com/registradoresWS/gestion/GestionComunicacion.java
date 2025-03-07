package com.registradoresWS.gestion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.registradores.constantes.ConstantesRegistradores;
import org.gestoresmadrid.core.registradores.model.enumerados.EstadoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoRegistro;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioRegistroFueraSecuencia;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioTramiteRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.registradoresWS.buildXml.BuildACK;
import com.registradoresWS.buildXml.xmlElements.AckCorpme;
import com.registradoresWS.enumeraciones.ErroresWS;
import com.registradoresWS.excepciones.CambioEstadoIlegalException;
import com.registradoresWS.excepciones.TramiteDesconocidoException;
import com.registradoresWS.utilidades.RespuestaRegistro;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class GestionComunicacion {

	public GestionComunicacion() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	private static final Logger log = Logger.getLogger(GestionComunicacion.class);
	
	private final String NO_RECUPERADO = "No recuperado";

	@Autowired
	private ServicioTramiteRegistro servicioTramiteRegistro;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	private static final String SERVICIO_WEB_REGISTRADORES = "SERVICIO WEB REGISTRADORES : ";
	private static final String WSOEGAM = "wsOegam.";
	private static final String ACTUALIZARACK = " actualizarACK.";
	private static final String INFO = "Info. ";
	private static final String ERROR = "Error. ";
	private static final String PILA_LLAMADAS = " Pila de llamadas de la excepción:\n";

	public byte[] wsOegam(byte[] datosEntrada) {
		log.info(SERVICIO_WEB_REGISTRADORES + "Mensaje recibido.");

		// Propiedades para los atributos Codigo_Retorno y Descripcion_Retorno
		BigInteger codigoRetorno = BigInteger.ZERO;
		String descripcionRetorno = "";
		boolean esFactura = false;

		RespuestaRegistro comunicacion = null;
		TramiteRegistroDto tramiteRegistroDto = null;
		List<TramiteRegistroDto> lista = null;
		try {

			// RespuestaRegistro extrae con Jaxb la información contenida en el xml:
			comunicacion = new RespuestaRegistro(datosEntrada);
			lista = servicioTramiteRegistro.getTramiteIdCorpmeConCodigoRegistro(comunicacion.getIdTramite(), comunicacion.getCodigoRegistro());
			//tramiteRegistroDto = servicioTramiteRegistro.getTramiteIdCorpme(comunicacion.getIdTramite());
			if (lista != null && !lista.isEmpty()) {
				if (lista.size() == 1) {
					tramiteRegistroDto = lista.get(0);
				} else {
					// TODO: crear ACK y mandar correo
					codigoRetorno = BigInteger.ZERO;
					descripcionRetorno = "";
					byte[] respuesta = BuildACK.obtenerACK(comunicacion.getTipoMensaje(), comunicacion.getIdTramite(), codigoRetorno, descripcionRetorno, comunicacion.getTipoRegistro(), comunicacion
							.getCodigoRegistro(), lista.get(0).getIdTramiteRegistro());
					log.info(SERVICIO_WEB_REGISTRADORES + "Respuesta enviada");
					enviarCorreoDuplicados(comunicacion.getIdTramite(), comunicacion.getTipoMensaje(), comunicacion.getCodigoRegistro(), lista);
					return respuesta;
				}
			} else {
				tramiteRegistroDto = servicioTramiteRegistro.getTramiteIdCorpme(comunicacion.getIdTramite());
			}
			
			if (tramiteRegistroDto == null) {
				codigoRetorno = new BigInteger(ErroresWS.TRAMITE_DESCONOCIDO_EXCEPTION.getValor().toString());
				descripcionRetorno = ErroresWS.TRAMITE_DESCONOCIDO_EXCEPTION.toString();
				return BuildACK.obtenerACK(NO_RECUPERADO, comunicacion.getIdTramite(), codigoRetorno, descripcionRetorno, comunicacion.getTipoRegistro(), comunicacion.getCodigoRegistro(), null);
			}
			
			// Traza para registrar el mensaje recibido:
			log.info(SERVICIO_WEB_REGISTRADORES + "Mensaje Recibido: " + comunicacion.getTipoMensaje() + " para el tramite con identificador de Corpme: " + comunicacion.getIdTramite());
			// Comunicacion Recibida se comprimira.
			String tipo = "";
			if (TipoRegistro.REGISTRO_PROPIEDAD.getNumeroEnum().equals(comunicacion.getTipoRegistro())) {
				tipo = ConstantesGestorFicheros.ESCRITURAS;
			} else {
				tipo = ConstantesGestorFicheros.REGISTRADORES;
			}

			gestorDocumentos.guardarFichero(tipo, ConstantesGestorFicheros.REGISTRADORES_RECIBIDOXML, Utilidades.transformExpedienteFecha(tramiteRegistroDto.getIdTramiteRegistro()),
					tramiteRegistroDto.getIdTramiteRegistro() + "_" + comunicacion.getTipoMensaje().toUpperCase(), ConstantesGestorFicheros.EXTENSION_XML, datosEntrada);

			Map<String, byte[]> contenido = comunicacion.getContenido();
			byte[] bytesContenido = null;
			Iterator<Entry<String, byte[]>> it = contenido.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, byte[]> e = it.next();
				bytesContenido = (byte[]) e.getValue();

				String extension =  e.getKey().split("\\.")[1];
				String nombreFichero = tramiteRegistroDto.getIdTramiteRegistro() + "_" + comunicacion.getTipoMensaje().toUpperCase() ;

				// En los trámites de escritura recibimos dos mensajes tipo RITDR. La segunda es la factura.
				// No debe machacar el primer RITDR y se agrega al nombre del documento "_Factura".
				if (EstadoTramiteRegistro.Inscrito.getValorEnum().equals(tramiteRegistroDto.getEstado().toString()) 
						&& ConstantesRegistradores.TIPOMENSAJE_RESOLUCION_TOTAL.equalsIgnoreCase(comunicacion
								.getTipoMensaje()) && TipoTramiteRegistro.MODELO_6.getValorEnum().equals(tramiteRegistroDto.getTipoTramite())) {
					nombreFichero += "_Factura";
					esFactura = true;
				}

				FicheroBean fichero = new FicheroBean();
				fichero.setExtension("." + extension);
				fichero.setFecha(Utilidades.transformExpedienteFecha(tramiteRegistroDto.getIdTramiteRegistro()));
				fichero.setTipoDocumento(tipo);
				fichero.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_DOCUMENTACION);
				fichero.setFicheroByte(bytesContenido);
				fichero.setSobreescribir(Boolean.FALSE);
				fichero.setNombreDocumento(nombreFichero);
				gestorDocumentos.guardarByte(fichero);
			}
			
			
			// Si se recibe una notificación tipo FDR (factura) solamente mandamos un correo avisando de su recepción
			if (ConstantesRegistradores.TIPOMENSAJE_FACTURA.equalsIgnoreCase(comunicacion.getTipoMensaje())){
				esFactura = true;
			}

			// Recupera el estado del trámite que se va a establecer según el tipo de mensaje recibido:
			EstadoTramiteRegistro estado = EstadoTramiteRegistro.relacionarMensaje(comunicacion.getTipoMensaje(), esFactura);
			log.info(SERVICIO_WEB_REGISTRADORES + "El tramite con identificador : " + tramiteRegistroDto.getIdTramiteRegistro() + " pasará según el mensaje a estado : " + estado.getNombreEnum());

			if (null != tramiteRegistroDto && null != estado && !tramiteRegistroDto.getEstado().equals(new BigDecimal(estado.getValorEnum()))) {
				boolean mensajeFueraSecuencia = EstadoTramiteRegistro.mensajeFueraSecuencia(tramiteRegistroDto.getEstado().toString(), estado.getValorEnum());
				if (!mensajeFueraSecuencia) {
					BigDecimal estadoAnterior = tramiteRegistroDto.getEstado();
					ResultBean respuesta = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getEstado(), new BigDecimal(estado.getValorEnum()),
							true, TextoNotificacion.Cambio_Estado.getNombreEnum(), null);
					if (!respuesta.getError()) {
						// Devuelve un ack que retorna el tipo de mensaje recibido:
						codigoRetorno = BigInteger.ZERO;
						descripcionRetorno = "";
						tramiteRegistroDto.setEstado(new BigDecimal(estado.getValorEnum()));
						log.info(SERVICIO_WEB_REGISTRADORES + "Actualizado correctamente el estado del tramite.");
						ContratoVO contrato = servicioContrato.getContrato(tramiteRegistroDto.getIdContrato());
						enviarCorreo(estado.getNombreEnum(), contrato, tramiteRegistroDto.getIdTramiteRegistro().toString(), tramiteRegistroDto.getIdTramiteOrigen(), mensajeFueraSecuencia, esFactura, tramiteRegistroDto.getRegistro().getNombre(), tramiteRegistroDto.getTipoTramite());
						enviarNotificacion(tramiteRegistroDto, estadoAnterior, tramiteRegistroDto.getIdUsuario());
					} else {
						log.error(SERVICIO_WEB_REGISTRADORES + "Ha ocurrido un error cambiando el estado del tramite : " + tramiteRegistroDto.getIdTramiteRegistro() + " a : " + estado
								.getNombreEnum());
						throw new CambioEstadoIlegalException("El tramite: " + tramiteRegistroDto.getIdTramiteRegistro() + " no puede pasar a estado: " + estado.getNombreEnum());
					}
				} else {
					if (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Presentacion.equals(estado) || EstadoTramiteRegistro.Pendiente_Firma_Acuse_Calificado_Defectos.equals(estado)
							|| EstadoTramiteRegistro.Pendiente_Firma_Acuse_Denegacion.equals(estado) || (EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Total.equals(estado) && !esFactura)
							|| EstadoTramiteRegistro.Pendiente_Firma_Acuse_Inscripcion_Parcial.equals(estado)) {

						registroFueraSecuencia(comunicacion, estado, tramiteRegistroDto);
						
						ContratoVO contrato = servicioContrato.getContrato(tramiteRegistroDto.getIdContrato());
						enviarCorreo(estado.getNombreEnum(), contrato, tramiteRegistroDto.getIdTramiteRegistro().toString(), tramiteRegistroDto.getIdTramiteOrigen(), mensajeFueraSecuencia, esFactura, tramiteRegistroDto.getRegistro().getNombre(), tramiteRegistroDto.getTipoTramite());

						log.info(SERVICIO_WEB_REGISTRADORES + "No cambio de estado, mensaje fuera de secuancia con acuse");
					} else if (EstadoTramiteRegistro.Tramitandose_Denegacion.equals(estado) || EstadoTramiteRegistro.Finalizado.equals(estado) || EstadoTramiteRegistro.Tramitandose_Presentacion
							.equals(estado)) {
						log.info(SERVICIO_WEB_REGISTRADORES + "No cambio de estado, mensaje fuera de secuancia sin acuse");
					}
				}
			}

			byte[] respuesta = BuildACK.obtenerACK(comunicacion.getTipoMensaje(), comunicacion.getIdTramite(), codigoRetorno, descripcionRetorno, comunicacion.getTipoRegistro(), comunicacion
					.getCodigoRegistro(), tramiteRegistroDto.getIdTramiteRegistro());
			log.info(SERVICIO_WEB_REGISTRADORES + "Respuesta enviada");

			return respuesta;

		} catch (CambioEstadoIlegalException e) {
			codigoRetorno = new BigInteger(ErroresWS.CAMBIO_ESTADO_ILEGAL_EXCEPTION.getValor().toString());
			descripcionRetorno = ErroresWS.CAMBIO_ESTADO_ILEGAL_EXCEPTION.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (TramiteDesconocidoException e) {
			codigoRetorno = new BigInteger(ErroresWS.TRAMITE_DESCONOCIDO_EXCEPTION.getValor().toString());
			descripcionRetorno = ErroresWS.TRAMITE_DESCONOCIDO_EXCEPTION.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (FileNotFoundException e) {
			codigoRetorno = new BigInteger(ErroresWS.FILE_NOT_FOUND.getValor().toString());
			descripcionRetorno = ErroresWS.FILE_NOT_FOUND.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (IOException e) {
			codigoRetorno = new BigInteger(ErroresWS.IO.getValor().toString());
			descripcionRetorno = ErroresWS.IO.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (Base64DecodingException e) {
			codigoRetorno = new BigInteger(ErroresWS.BASE64_DECODING.getValor().toString());
			descripcionRetorno = ErroresWS.BASE64_DECODING.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (JAXBException e) {
			// Entrará aquí si Jaxb no puede crear la jerarquía de objetos java. Esto sería
			// indicativo de que la respuesta no es un xml CORPME-eDoc válido
			codigoRetorno = new BigInteger(ErroresWS.JAXB.getValor().toString());
			descripcionRetorno = ErroresWS.JAXB.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (OegamExcepcion e) {
			codigoRetorno = new BigInteger(ErroresWS.OEGAM.getValor().toString());
			descripcionRetorno = ErroresWS.OEGAM.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		} catch (Exception e) {
			codigoRetorno = new BigInteger(ErroresWS.GENERIC_EXCEPTION.getValor().toString());
			descripcionRetorno = ErroresWS.GENERIC_EXCEPTION.toString();
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		}
		// Enviar el ack con los datos establecidos en el catch
		try {
			final String MENSAJE_FIN = "Fin. ";
			if (comunicacion == null) {
				log.info(SERVICIO_WEB_REGISTRADORES + MENSAJE_FIN + WSOEGAM);
				return BuildACK.obtenerACK(NO_RECUPERADO, NO_RECUPERADO, codigoRetorno, descripcionRetorno, null, NO_RECUPERADO, tramiteRegistroDto.getIdTramiteRegistro());
			} else if (comunicacion.getIdTramite() == null) {
				log.info(SERVICIO_WEB_REGISTRADORES + MENSAJE_FIN + WSOEGAM);
				return BuildACK.obtenerACK(NO_RECUPERADO, NO_RECUPERADO, codigoRetorno, descripcionRetorno, comunicacion.getTipoRegistro(), comunicacion.getCodigoRegistro(), tramiteRegistroDto
						.getIdTramiteRegistro());
			} else {
				log.info(SERVICIO_WEB_REGISTRADORES + MENSAJE_FIN + WSOEGAM);
				return BuildACK.obtenerACK(comunicacion.getTipoMensaje(), comunicacion.getIdTramite(), codigoRetorno, descripcionRetorno, comunicacion.getTipoRegistro(), comunicacion
						.getCodigoRegistro(), tramiteRegistroDto.getIdTramiteRegistro());
			}
		} catch (Exception e) {
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + WSOEGAM + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		}
		return null;
	}

	private void enviarCorreo(String estado, ContratoVO contrato, String idTramite, BigDecimal idTramiteOrigen, boolean mensajeFueraSecuencia, boolean esFactura, String nombreRegistro, String tipoTramite) {
		String subject = null;
		StringBuffer texto = null;
		try {

			if (null != idTramiteOrigen) {
				subject = "Cambio estado justificante de pago del trámite: " + idTramiteOrigen.toString() + " presentado en el registro " + nombreRegistro;
				texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">El justificante de pago del trámite de registro con identificador <b>" + idTramiteOrigen.toString() + "</b>");
				texto.append(" ha pasado a estado <b>" + estado + "</b>.");
			} else if(esFactura){
				subject = "Recibida factura trámite registro: " + idTramite + " presentado en el registro " + nombreRegistro;
				texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">El trámite de registro con identificador <b>" + idTramite + "</b>");
				texto.append(" ha recibido la factura.");
			} else if(mensajeFueraSecuencia){
				subject = "Existen acuses pendientes para el trámite: " + idTramite + " presentado en el registro " + nombreRegistro;
				texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">El trámite de registro con identificador <b>" + idTramite + "</b>");
				texto.append(" tiene acuses pendientes de firmar.");
			}else{
				subject = "Cambio estado trámite registro: " + idTramite + " presentado en el registro " + nombreRegistro;
				texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">El trámite de registro con identificador <b>" + idTramite + "</b>");
				texto.append(" ha pasado a estado <b>" + estado + "</b>.");

			}

			texto.append("<br><br></span>");

			String emailCopia = gestorPropiedades.valorPropertie("email.compia.registradoresWS");

			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if (!"PRODUCCION".equals(entorno)) {
				subject = entorno + ": " + subject;
			}
			
			String direccionCorreo = contrato.getCorreoElectronico();
			if (contrato.getCorreosTramites() != null && !contrato.getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : contrato.getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			
			log.info("Envio correo a: " + direccionCorreo + " y copia oculta a: " + emailCopia);
			
			ResultBean resultado;

			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, direccionCorreo, null, emailCopia, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo para los cambios de estado de registradores. Error: " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos para los cambios de estado de registradores", e);
		}
	}
	
	private void enviarCorreoDuplicados(String idTramiteCorpme, String tipoMensaje, String codigoRegistro, List<TramiteRegistroDto> lista) {
		String subject = null;
		StringBuffer texto = null;
		try {
			subject = "Duplicado de Registradores. Id tramite Corpme: " + idTramiteCorpme;
			
			texto = new StringBuffer("ID TRAMITE CORPME <b>" + idTramiteCorpme + "</b><BR>");
			texto.append("CODIGO REGISTRO <b>" + codigoRegistro + "</b><BR>");
			texto.append("TIPO MENSAJE <b>" + tipoMensaje + "</b><BR><BR>");
			
			texto.append("ID TRAMITES:<BR>");
			
			for (TramiteRegistroDto dto : lista) {
				texto.append("<b>" + dto.getIdTramiteRegistro() + "</b><BR>");
			}

			String email = "desarrollo1@gestoresmadrid.org,jose.rumbo@globaltms.es,javier.conde@globaltms.es";

			ResultBean resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, email, null, null, null);

			if (resultado.getError()) {
				log.error("No se envió el correo de duplicados de registradores. Error: " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se envió el correo de duplicados de registradores", e);
		}
	}

	public boolean actualizarACK(byte[] datosEntrada) {
		boolean errorSercon = false;
		boolean resultado = false;
		log.info(SERVICIO_WEB_REGISTRADORES + "Mensaje recibido.");

		// Propiedades para los atributos Codigo_Retorno y Descripcion_Retorno
		BigInteger codigoRetorno = BigInteger.ZERO;
		String descripcionRetorno = "";
		try {
			String entrada = new String(Base64.decode(datosEntrada), "ISO-8859-1");
			StringReader stringReader = new StringReader(entrada);

			JAXBContext jaxbContext = JAXBContext.newInstance(AckCorpme.class);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			AckCorpme ackCorpme = (AckCorpme) um.unmarshal(stringReader);

			/* LA RESPUESTA DE SERCON SERA OK_RECIBIDO_SERCON Y EL XML A NULL */
			if (ackCorpme.getCodigo_Retorno() != null && !ackCorpme.getCodigo_Retorno().trim().equalsIgnoreCase("0")) {
				errorSercon = true;
			}

			TramiteRegistroDto tramiteRegistroDto = servicioTramiteRegistro.getTramiteIdCorpme(ackCorpme.getId_Tramite());
			if (tramiteRegistroDto == null) {
				tramiteRegistroDto = servicioTramiteRegistro.getTramite(new BigDecimal(ackCorpme.getId_Tramite()));
			}

			String tipoDocumento = null;
			if (TipoTramiteRegistro.MODELO_6.getValorEnum().equalsIgnoreCase(tramiteRegistroDto.getTipoTramite())) {
				tipoDocumento = ConstantesGestorFicheros.ESCRITURAS;
			} else {
				tipoDocumento = ConstantesGestorFicheros.REGISTRADORES;
			}

			if (!errorSercon) {

				if (!new BigDecimal(EstadoTramiteRegistro.Pendiente_Respuesta_Sercon.getValorEnum()).equals(tramiteRegistroDto.getEstado())) {
					log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + "El tramite con identificador: " + tramiteRegistroDto.getIdTramiteRegistro()
					+ " deberia estar 'pendiente de envio' o 'pendiente envio subsanacion' y esta en estado: " + EstadoTramiteRegistro.convertirTexto(tramiteRegistroDto.getEstado()
							.toString()));
					return false;
				}

				// Se ha obtenido un array de bytes válido como respuesta:
				File ficheroRespuesta = new File(gestorPropiedades.valorPropertie("registradores.ruta.mensaje.recibido.temporal") + tramiteRegistroDto.getIdTramiteRegistro().toString() + ".xml");
				FileOutputStream fos = new FileOutputStream(ficheroRespuesta);
				fos.write(Base64.decode(datosEntrada));
				fos.flush();
				fos.close();

				RespuestaRegistro respuestaRegistro = new RespuestaRegistro(ficheroRespuesta);

				String tipoMensaje = respuestaRegistro.getTipoMensaje();
				if (tipoMensaje.equalsIgnoreCase(ConstantesRegistradores.TIPOMENSAJE_DOCUMENTO_REGISTRO)) {
					// Se ha recibido como respuesta un DPR con codigo y descripcion de retorno
					log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + "Se ha recibido un DPR con codigo y descripcion de retorno");

					FicheroBean ficheroBean = new FicheroBean();
					ficheroBean.setTipoDocumento(tipoDocumento);
					ficheroBean.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_XML);
					ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
					ficheroBean.setFichero(ficheroRespuesta);
					ficheroBean.setSobreescribir(true);
					ficheroBean.setFecha(Utilidades.transformExpedienteFecha(tramiteRegistroDto.getIdTramiteRegistro()));
					ficheroBean.setNombreDocumento(tramiteRegistroDto.getIdTramiteRegistro() + "_" + tipoMensaje + "_ACK");
					gestorDocumentos.guardarFichero(ficheroBean);

					codigoRetorno = respuestaRegistro.getCodigoRetorno();
					log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + "Codigo retorno del dpr recibido: " + codigoRetorno);
					descripcionRetorno = respuestaRegistro.getDescripcionRetorno();
					log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + "Descripcion retorno del dpr recibido: " + descripcionRetorno);
					String respuesta = codigoRetorno + " _ " + descripcionRetorno;
					finalizarNoRecuperable(respuesta, tramiteRegistroDto);
				}

				// Borra el fichero temporal 'ficheroRespuesta'
				if (ficheroRespuesta.delete()) {
					log.info(SERVICIO_WEB_REGISTRADORES + INFO + ACTUALIZARACK + "Borrado correctamente el fichero temporal.");
				} else {
					log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + "Error borrando el fichero temporal");
				}

			}

		} catch (Exception | OegamExcepcion e) {
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + e.getMessage() + "\n" + e + PILA_LLAMADAS + UtilesExcepciones.stackTraceAcadena(e, 10));
		}
		return resultado;
	}

	private void enviarNotificacion(TramiteRegistroDto tramiteRegistro, BigDecimal estadoAnterior, BigDecimal idUsuario) {
		NotificacionDto dto = new NotificacionDto();
		dto.setDescripcion(TextoNotificacion.Cambio_Estado.getNombreEnum());
		dto.setEstadoAnt(estadoAnterior);
		dto.setEstadoNue(tramiteRegistro.getEstado());
		dto.setIdTramite(tramiteRegistro.getIdTramiteRegistro());
		dto.setIdUsuario(idUsuario.longValue());
		dto.setTipoTramite(tramiteRegistro.getTipoTramite());
		servicioNotificacion.crearNotificacion(dto);
	}

	private void finalizarNoRecuperable(String error, TramiteRegistroDto tramiteRegistroDto) {
		log.info(SERVICIO_WEB_REGISTRADORES + INFO + ACTUALIZARACK + "finalizar no recuperable");
		ResultBean result;

		result = servicioTramiteRegistro.cambiarEstado(true, tramiteRegistroDto.getIdTramiteRegistro(), tramiteRegistroDto.getEstado(), new BigDecimal(EstadoTramiteRegistro.Finalizado_error
				.getValorEnum()), true, TextoNotificacion.Cambio_Estado.getNombreEnum(), tramiteRegistroDto.getIdUsuario());

		if (result != null && result.getError()) {
			log.error(SERVICIO_WEB_REGISTRADORES + ERROR + ACTUALIZARACK + "Ha ocurrido el siguiente error actualizando a 'Finalizado_Error' el tramite con identificador: " + tramiteRegistroDto
					.getIdTramiteRegistro());
		} else {
			log.info(SERVICIO_WEB_REGISTRADORES + INFO + ACTUALIZARACK + "Se ha actualizado el estado del tramite con identificador: " + tramiteRegistroDto.getIdTramiteRegistro()
			+ " a 'Finalizado_Error' debido al siguiente error: " + error);
		}
	}

	private void registroFueraSecuencia(RespuestaRegistro comunicacion, EstadoTramiteRegistro estado, TramiteRegistroDto tramiteRegistroDto) {
		try {
			RegistroFueraSecuenciaDto registroFueraSecuenciaDto = new RegistroFueraSecuenciaDto();
			registroFueraSecuenciaDto.setIdTramiteRegistro(tramiteRegistroDto.getIdTramiteRegistro());
			registroFueraSecuenciaDto.setTipoMensaje(comunicacion.getTipoMensaje());
			registroFueraSecuenciaDto.setEstado(new BigDecimal(estado.getValorEnum()));

			ServicioRegistroFueraSecuencia servicioRegistroFueraSecuencia = ContextoSpring.getInstance().getBean(ServicioRegistroFueraSecuencia.class);
			ResultBean result = servicioRegistroFueraSecuencia.guardarRegistroFueraSecuencia(registroFueraSecuenciaDto, null);
			if (result.getError()) {
				log.error(SERVICIO_WEB_REGISTRADORES + "No se ha guardado el acuse pendiente:" + result.getMensaje());
			}
		} catch (Exception e) {
			log.error(SERVICIO_WEB_REGISTRADORES + "No se ha guardado el acuse pendiente:" + e.getMessage());
		}
	}
}
