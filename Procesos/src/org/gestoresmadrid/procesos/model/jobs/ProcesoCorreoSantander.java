package org.gestoresmadrid.procesos.model.jobs;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesBastidorVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesMatriculaVO;
import org.gestoresmadrid.core.datosSensibles.model.vo.DatosSensiblesNifVO;
import org.gestoresmadrid.core.model.enumerados.TipoBastidor;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.DatosSensiblesBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ProcesoCorreoSantander extends AbstractProcesoBase {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ProcesoCorreoSantander.class);

	private static final String PROPERTY_USUARIO_CORREO = "usuario.correo.liberacion.bastidores";
	private static final String PROPERTY_CLAVE_CORREO = "password.correo.liberacion.bastidores";
	private static final String PROPERTY_WHITE_LIST = "direccion.correo.autorizada.liberacion";

	private static final String TEXTO_DESBLOQUEO = "desbloquear";

	private static final String BASTIDOR = "BASTIDOR";
	private static final String MATRICULA = "MATRICULA";
	private static final String DNI = "DNI";

	@Autowired
	private ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	//Mantis 0025269 Eduardo Puerro
	//Variables para dar sentido a los mail
	private String tipo = null;
	private String valorIdentificador = null;
	private String mensajeTemporal;

	@Override
	protected void doExecute() throws JobExecutionException {
		LOG.info("Inicio ProcesoCorreoSantander");
		try {
			if (procesarMensajes()) {
				// Se ha desbloqueado o bloqueado un bastidor
				actualizarEjecucion(getProceso(), ConstantesProcesos.RECUPERACION_CORRECTA_CORREO_LIBERACION, ConstantesProcesos.EJECUCION_CORRECTA,"0");

				peticionCorrecta();
				LOG.info("ProcesoCorreoSantander: Desbloqueo de bastidor(es) realizado correctamente");
			} else {
				// No hay bastidores a desbloquear o bloquear
				LOG.info("ProcesoCorreoSantander: No se ha realizado ninguna accion sobre los bastidores sensibles");
				sinPeticiones();
			}
			// Se ha producido una excepcion
		} catch (Exception e) {
			LOG.error("Error en el proceso ProcesoCorreoSantander: ", e);
			String respuesta = ConstantesProcesos.ERROR_RECUPERACION_CORREO_LIBERACION + (e.getMessage() != null ? e.getMessage() : e.toString());
			actualizarEjecucion(getProceso(), ConstantesProcesos.EJECUCION_EXCEPCION, respuesta, "0");
		}
	}

	/**
	 * Recupera todos los mensajes no leidos del buzón
	 * @return
	 * @throws MessagingException
	 */
	private boolean procesarMensajes() throws MessagingException {
		Folder inbox = null;
		Store store = null;
		boolean result = false;
		//Mantis 0025269 Eduardo Puerro
		this.tipo = new String();

		try {
			Session session = createSession();
			//No debería funcionar el servidor de correos con imap
			//Ya que está configurado con pop3
			store = session.getStore("pop3");
			store.connect(gestorPropiedades.valorPropertie("mail.host"), gestorPropiedades.valorPropertie(PROPERTY_USUARIO_CORREO), gestorPropiedades.valorPropertie(PROPERTY_CLAVE_CORREO));
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);

			// Recupera los correos no leidos
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message[] mensajes = inbox.search(unseenFlagTerm);

			if (LOG.isInfoEnabled()) {
				LOG.info("El Proceso " + getProceso() + "  ha encontrado " + mensajes.length + " mensajes nuevos sin leer");
			}
			for (Message message : mensajes) {
				// Si el remitente es de los admitidos
				LOG.info("Lectura asunto :" + message.getSubject());
				if (remitenteAdmitido(message)) {
					LOG.info("Remitente Admitido Lectura asunto :" + message.getSubject());
					boolean desbloqueado = procesarMensaje(message);
					LOG.info("Sale Remitente Admitido Lectura asunto :" + message.getSubject());
					message.setFlag(Flags.Flag.SEEN, true);
					responder(message, desbloqueado);
					result |= desbloqueado;
				}
			}
			return result;
		} finally {
			if (inbox != null && inbox.isOpen()) {
				try {
					inbox.close(true);
				} catch (MessagingException e) {
					LOG.error("Error al cerrar inbox en el proceso " + getProceso(), e);
				}
			}
			if (store != null && store.isConnected()) {
				try {
					store.close();
				} catch (MessagingException e) {
					LOG.error("Error al cerrar store en el proceso " + getProceso(), e);
				}
			}
		}
	}

	private Session createSession() {
		Properties properties = new Properties();;
		properties.setProperty("mail.smtp.host", gestorPropiedades.valorPropertie("mail.host"));
		properties.setProperty("mail.smtp.starttls.enable", Boolean.TRUE.toString());
		properties.setProperty("mail.smtp.port", gestorPropiedades.valorPropertie("mail.puerto"));
		properties.setProperty("mail.smtp.auth", Boolean.TRUE.toString());
		return Session.getInstance(properties);
	}
	
	/**
	 * Trata el correo individualmente
	 * @param mensaje
	 * @return
	 * @throws MessagingException
	 */
	private boolean procesarMensaje(Message mensaje) throws MessagingException {
		boolean desbloqueado = false;
		//Mantis 0025269 Eduardo Puerro
		//Añadimos la misma funcionalidad pero para nif y matrículas
		// Se recupera la accion a realizar y el bastidor del asunto del mensaje
		String asunto = mensaje.getSubject();
		mensajeTemporal = asunto;
		String[] splited = asunto.split("\\s+");
		valorIdentificador = null;
		String accion = null;
		tipo = null;
		LOG.info("Entrada procesarMensaje Asunto :" + mensaje.getSubject());
		for (int j = 0; j < splited.length; j++) {
			
			if (splited[j].equalsIgnoreCase(TEXTO_DESBLOQUEO)) {
				accion = TEXTO_DESBLOQUEO;
				LOG.info("TEXTO_DESBLOQUEO Asunto :" + mensaje.getSubject());
			} else if (verTipoDatoSensible(splited[j]).equalsIgnoreCase(BASTIDOR)) {
				tipo = BASTIDOR;
				valorIdentificador = splited[j];
				LOG.info("Bastidor Asunto :" + mensaje.getSubject());
			} else if (verTipoDatoSensible(splited[j]).equalsIgnoreCase(MATRICULA)){
				tipo = MATRICULA;
				valorIdentificador = splited[j];
				LOG.info("Matricula procesarMensaje Asunto :" + mensaje.getSubject());
			}else if (verTipoDatoSensible(splited[j]).equalsIgnoreCase(DNI)){
				tipo = DNI;
				valorIdentificador = splited[j];
				LOG.info("Dni procesarMensaje Asunto :" + mensaje.getSubject());
			}
		}
		if (valorIdentificador!=null && accion !=null && tipo!=null) {
			desbloqueado = desbloquear(valorIdentificador.toUpperCase(),tipo);
		} else { 
			LOG.info("ProcesoCorreoSantander: No hay " + tipo + " para desbloquear");
		}
		
		return desbloqueado;
	}
	/**
	 * Manda el correo con firma y mensaje personalizado
	 * @param mensaje
	 * @param desbloqueado
	 */
	private void responder(Message mensaje, boolean desbloqueado) {
		try {
			//Mantis 0025269 Eduardo Puerro
			String asunto = new String();
			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if(!"PRODUCCION".equals(entorno)){
				asunto = entorno + ": " + mensaje.getSubject();
			}
			
			String valorEntrada = new String();
			if(tipo==null || valorIdentificador == null){
				valorEntrada = "Asunto : '" + mensajeTemporal + "'";
			}
			else{
				valorEntrada = tipo + ": " + valorIdentificador;
			}
			//Borramos el asunto para los que no se desbloquean.
			mensajeTemporal = new String();
			String mensajeDesbloqueado = valorEntrada + " desbloqueado de forma satisfactoria.\n<br><br><br><br><br>";
			String mensajeNoDesbloqueado = "No se ha podido desbloquear " + valorEntrada 
				+ ", puede no ser un dato sensible o estar previamente desbloqueado\n<br><br><br><br><br>";

			String texto = desbloqueado?mensajeDesbloqueado:mensajeNoDesbloqueado;
			LOG.info("Envio Correo 2º :" + mensaje.getSubject());
			ResultBean resultEnvio;
			resultEnvio = servicioCorreo.enviarCorreo(texto, false, mensaje.getFrom()[0].toString(), asunto, mensaje.getRecipients(Message.RecipientType.TO)[0].toString(), null, null, null, null);
			if (resultEnvio == null || resultEnvio.getError())
					throw new OegamExcepcion("No se ha enviado el correo de confirmación de liberador de bastidores/nif/matricula.");
		} catch (MessagingException me ) {
			LOG.info("Respuesta del correo de liberación de " + tipo, me);
		} catch (OegamExcepcion e) {
			LOG.error("No se ha enviado el correo de confirmación de liberador de bastidores/nif/matricula. Error: " + e.toString());
		} catch (IOException e) {
			LOG.error("No se ha enviado el correo de confirmación de liberador de bastidores/nif/matricula. Error: " + e.toString());

		}
	}

	/**
	 * Comprobar si el remitente es de los admitidos
	 * @param message
	 * @return
	 * @throws MessagingException
	 */
	private boolean remitenteAdmitido(Message message) throws MessagingException {
		String enviadoPor = message.getFrom()[0].toString().toUpperCase();
		String patron = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
		Pattern p = Pattern.compile(patron);
		Matcher m = p.matcher(enviadoPor);
		while(m.find()) {
			enviadoPor = m.group();
		}
		return gestorPropiedades.valorPropertie(PROPERTY_WHITE_LIST) != null && gestorPropiedades.valorPropertie(PROPERTY_WHITE_LIST).toUpperCase().contains(enviadoPor);
	}
	//Mantis 0025269 Eduardo Puerro
	//Añadimos funcionalidad similar de bastidor para nif y matrícula
	private boolean desbloquear(String objetoAdesbloquear, String tipo) {
		boolean resultado = false;
		// Recuperacion de los datos relacionados con el bastidor del correo

		LOG.info("Entrada en desbloquear " + tipo + " : " + objetoAdesbloquear);

		if (tipo.equals(BASTIDOR)) {
			LOG.info("Buscamos el " + tipo + " : " + objetoAdesbloquear);
			List<DatosSensiblesBastidorVO> listaBastidoresSensibles = servicioDatosSensibles.buscarPorBastidor(objetoAdesbloquear);
			LOG.info("Encontrado el " + tipo + " : " + objetoAdesbloquear);
			for (DatosSensiblesBastidorVO datosSensiblesBastidorVO : listaBastidoresSensibles) {
				// Desbloquear bastidor
				LOG.info("¿Condiciones para desbloquear el " + tipo + " : " + objetoAdesbloquear);
				if (datosSensiblesBastidorVO.getFechaBaja() == null && BigDecimal.ONE.equals(datosSensiblesBastidorVO.getEstado())) {
					// Bastidor bloqueado que se quiere desbloquear
					LOG.info("Vamos a desbloquear el " + tipo + " : " + objetoAdesbloquear);
					if (desbloquearObjeto(objetoAdesbloquear, datosSensiblesBastidorVO.getGrupo().getIdGrupo(), tipo) == null) {
						LOG.info("Envio correo Aviso Liberacion " + tipo + " : " + objetoAdesbloquear);
						servicioDatosSensibles.enviarCorreoAvisoLiberacionBastidores(getProceso(), objetoAdesbloquear, "desbloqueado");
						resultado |= true;
					}
				}
			}
		}else if(tipo.equals(DNI)){
			LOG.info("Buscamos el " + tipo + " : " + objetoAdesbloquear);
			List<DatosSensiblesNifVO> listaDniSensibles = servicioDatosSensibles.buscarPorNif(objetoAdesbloquear);
			LOG.info("Encontrado el " + tipo + " : " + objetoAdesbloquear);
			for(DatosSensiblesNifVO datosSensiblesDniVO : listaDniSensibles){
				LOG.info("¿Condiciones para desbloquear el " + tipo + " : " + objetoAdesbloquear);
				if(datosSensiblesDniVO.getFechaBaja()==null && BigDecimal.ONE.equals(datosSensiblesDniVO.getEstado())){
					LOG.info("Vamos a desbloquear el " + tipo + " : " + objetoAdesbloquear);
					if (desbloquearObjeto(objetoAdesbloquear, datosSensiblesDniVO.getGrupo().getIdGrupo(), tipo) == null) {
						LOG.info("Envio correo Aviso Liberacion " + tipo + " : " + objetoAdesbloquear);
						servicioDatosSensibles.enviarCorreoAvisoLiberacionNif(getProceso(), objetoAdesbloquear, "desbloqueado");
						resultado |= true;
					}
				}
			}

		} else if (tipo.equals(MATRICULA)) {
			LOG.info("Buscamos el " + tipo + " : " + objetoAdesbloquear);
			List<DatosSensiblesMatriculaVO> listaMatriculasSensibles = servicioDatosSensibles
					.buscarPorMatricula(objetoAdesbloquear);
			LOG.info("Encontrado el " + tipo + " : " + objetoAdesbloquear);
			for (DatosSensiblesMatriculaVO datosSensiblesMatriculaVO : listaMatriculasSensibles) {
				// Desbloquear matrícula
				LOG.info("¿Condiciones para desbloquear el " + tipo + " : " + objetoAdesbloquear);
				if (datosSensiblesMatriculaVO.getFechaBaja() == null
						&& BigDecimal.ONE.equals(datosSensiblesMatriculaVO.getEstado())) {
					// Matricula Bloqueada que se quiere desbloquear
					LOG.info("Vamos a desbloquear el " + tipo + " : " + objetoAdesbloquear);
					if (desbloquearObjeto(objetoAdesbloquear, datosSensiblesMatriculaVO.getGrupo().getIdGrupo(),
							tipo) == null) {
						LOG.info("Envio correo Aviso Liberacion " + tipo + " : " + objetoAdesbloquear);
						servicioDatosSensibles.enviarCorreoAvisoLiberacionMatricula(getProceso(), objetoAdesbloquear,
								"desbloqueado");
						resultado |= true;
					}

				}
			}
		}

		return resultado;
	}
	//Mantis 0025269 Eduardo Puerro
	//Generalizamos el desbloquearBastidor para nif y matrícula
	private ResultBean desbloquearObjeto(String objetoAdesbloquear, String grupo, String tipo) {
		ResultBean resultado = null;
		DatosSensiblesBean datosSensiblesBean = new DatosSensiblesBean();
		String indices = null;
		LOG.info("Entramos en desbloquear el " + tipo + " : " + objetoAdesbloquear);
		try {
			indices = objetoAdesbloquear;
			indices = indices.concat(",");
			indices = indices.concat(grupo);

			if(tipo.equals(BASTIDOR)){
				LOG.info("Entramos en if desbloquear el " + tipo + " : " + objetoAdesbloquear);
				//Es una caracter que define al bastidor. Exclusivo de éste
				datosSensiblesBean.setTipoControl(TipoBastidor.VN.getValorEnum());
				//datosSensiblesBean.setTipoAgrupacion(TiposDatosSensibles.Bastidor.toString());
				resultado = servicioDatosSensibles.eliminarBastidores(indices, datosSensiblesBean, new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO)),
						new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO)),"SANTANDER");
			}else if (tipo.equals(DNI)){
				LOG.info("Entramos en if desbloquear el " + tipo + " : " + objetoAdesbloquear);
				//datosSensiblesBean.setTipoAgrupacion(TiposDatosSensibles.Nif.toString());
				resultado = servicioDatosSensibles.eliminarNifes(indices, datosSensiblesBean, new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO)),
						new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO)));
			}else if(tipo.equals(MATRICULA)){
				LOG.info("Entramos en if desbloquear el " + tipo + " : " + objetoAdesbloquear);
				//datosSensiblesBean.setTipoAgrupacion(TiposDatosSensibles.Matricula.toString());
				resultado = servicioDatosSensibles.eliminarMatriculas(indices, datosSensiblesBean, new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_CONTRATO)),
						new BigDecimal(gestorPropiedades.valorPropertie(ServicioDatosSensibles.PROPERTY_KEY_DATOSSENSIBLES_ID_USUARIO)),"SANTANDER");
			}

			// Sin errores
			if (resultado == null) {
				LOG.info("Se ha desbloqueado correctamente " + tipo + " solicitado");
				// Con errores
			} else {
				if (resultado.getListaMensajes() == null && !resultado.getMensaje().isEmpty()) {
					LOG.error(resultado.getMensaje());
				} else if (resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()) {
					for (String mensaje : resultado.getListaMensajes()) {
						LOG.error(mensaje);
					}
				}
			}
		} catch (OegamExcepcion e) {
			LOG.error("Error en el proceso ProcesoCorreoSantander: " + e.getMessage());
		}
		return resultado;
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.LECTURA_CORREO_LIBERACION.getNombreEnum();
	}
	/**
	 * Decide si el dato que entra es : Bastidor, DNI o Matrícula
	 * @param mensaje El dato de que se necesita saber de qué tipo es
	 * @return BASTIDOR, DNI, MATRICULA.
	 * 
	 */
	//Mantis 0025269 Eduardo Puerro
	private String verTipoDatoSensible(String mensaje){
		String dato = "";

		//Si es bastidor tendrá 17 caracteres
		if(mensaje.length()==17){
			dato = "BASTIDOR";
		//Si es DNI tendrá 8 números más 1 letra
		}else if(mensaje.matches("\\d{8}[a-zA-Z]{1}")){
			dato = "DNI";
		//Si tiene 4 letras y entre 1 o 4 letras será una matrícula	
		}else if(mensaje.matches("\\d{4}[a-zA-Z]{1,4}")){
			dato = "MATRICULA";
		}

		return dato;
	}

}