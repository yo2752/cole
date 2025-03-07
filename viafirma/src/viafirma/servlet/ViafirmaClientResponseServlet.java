package viafirma.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.ViafirmaClientServlet;
import org.viafirma.cliente.exception.CodigoError;
import org.viafirma.cliente.vo.FirmaInfoViafirma;
import org.viafirma.cliente.vo.UsuarioGenericoViafirma;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import viafirma.utilidades.XmlBuilderSupportUtils;

/**
 * Invocado desde la página del applet
 */
public class ViafirmaClientResponseServlet extends ViafirmaClientServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ViafirmaClientResponseServlet.class);

	public ViafirmaClientResponseServlet() {
		super();
	}

	@Override
	public void authenticateOK(UsuarioGenericoViafirma usuario,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_INICIO + "authenticateOK");
			// Recupera un parámetro de web.xml que indica si hay que verificar que los únicos
			// certificados válidos para entrar en la plataforma son los de Firma Profesional:
			String soloFirmaProfesional = request.getSession().getServletContext().getInitParameter("soloFirmaProfesional");
			if(soloFirmaProfesional != null && soloFirmaProfesional.equalsIgnoreCase("Si")){
				String tipoDeCertificado = usuario.getTypeCertificate();
				if(tipoDeCertificado != null && !tipoDeCertificado.equalsIgnoreCase("FirmaProfesional")){
					request.getSession().setAttribute("mensajeError", "La plataforma sólo reconoce, como" +
					" válidos los certificados de 'Firma Profesional' para acceder a la misma.");
					log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "authenticateOK");
					response.sendRedirect("discriminarError.action");
					return;
				}
			}
			String cifColegio = null;
			String nifUsuario = ((Map<String,String>)usuario.getProperties()).get("numberIdResponsable");

			if (nifUsuario == null || "".equals(nifUsuario)){
				nifUsuario = usuario.getNumberUserId();
			} else{
				cifColegio = usuario.getNumberUserId();
			}
			HttpSession sesion = request.getSession();
			sesion.setAttribute(utilidades.mensajes.Claves.CLAVE_NIF_Viafirma,nifUsuario);
			sesion.setAttribute(utilidades.mensajes.Claves.CLAVE_CIF_COLEGIO,cifColegio);
			sesion.setAttribute("validada", "true");
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "authenticateOK");
			response.sendRedirect("bienvenidaVacio.jsp");
		} catch (Exception e) {
			try{
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "authenticateOK " + e);
				request.getSession().setAttribute("mensajeError","ERROR: " + e);
				response.sendRedirect("discriminarError.action");
				return;
			}catch(IOException ex){
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "authenticateOK " + ex);
			}
		}
	}

	@Override
	public void cancel(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().setAttribute("mensajeError","ERROR: " + "Operación cancelada");
			response.sendRedirect("discriminarError.action");
			return;
		} catch (Exception e) {
			try{
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "cancel " + e);
				response.sendRedirect("discriminarError.action");
				return;
			}catch(IOException ex){
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "cancel " + ex);
			}
		}
	}
	
	@Override
	public void error(CodigoError codError, HttpServletRequest request,
			HttpServletResponse response) {
			
		try {
			request.getSession().setAttribute("mensajeError","ERROR: " + codError.getMensaje());
			response.sendRedirect("discriminarError.action");
			return;
		} catch (Exception e) {
			try{
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "error " + e);
				response.sendRedirect("discriminarError.action");
				return;
			}catch(IOException ex){
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "error " + ex);
			}
		}
	}

	@Override
	public void signOK(FirmaInfoViafirma fiv, HttpServletRequest request,
			HttpServletResponse response) {
		
		log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_INICIO + "signOK");
		// Recupera el context-param que verificará o no que la firma corresponda con la
		// personalidad que figura como presentante del tramite:
		String comprobarFirmante = request.getSession().getServletContext().getInitParameter("comprobarFirmante");
		log.info(Claves.CLAVE_LOG_CONTEXT_PARAM + "comprobarFirmante: " + comprobarFirmante);
		ViafirmaClient viafirmaClient=(ViafirmaClient)request.getSession().getServletContext().
			getAttribute("viafirmaClient");
		
		// Recupera el valor del atributo de request tipoMensajeFirmado 
		// para saber si lo que se ha firmado es un rm o un dpr:
		String tipoMensajeFirmado=request.getSession().getAttribute("tipoMensajeFirmado").toString();
		Integer modeloFirmado =(Integer)request.getSession().getAttribute("modeloFirmado");
		request.getSession().setAttribute("tipoMensajeFirmado", null);
		// Comprobación para firmar un dpr de un acuse de notificacion
		if(request.getSession().getAttribute("acuseNotificacion")!=null&&
				request.getSession().getAttribute("acuseNotificacion")=="si"){
			try{
				request.getSession().removeAttribute("acuseNotificacion");
				String idFirma=fiv.getSignId();
				// Pone en sesión la firma y el timestamp:
				request.getSession().setAttribute("firmaDprBytes",XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
				request.getSession().setAttribute("selloDprBytes",XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				response.sendRedirect("construirContinue2Acuse.action");
			}catch(Exception ex){
				try{
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK " + ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + ex);
					response.sendRedirect("discriminarError.action");
					return;
				}catch(IOException e){
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK " + e);
				}
			}
		}
		if(tipoMensajeFirmado!=null&&tipoMensajeFirmado.equals("dpr")){
			try{
				// Comprueba que se ha firmado con un certificado cuyo nif coincide
				// con el presentante del trámite:
				if(comprobarFirmante.equalsIgnoreCase("si")){
					String nifPresentante = (String) request.getSession().getAttribute("nifPresentanteTramite");
					// Recupera el nif del certificado de las dos formas posibles:
					String nifCertificadoFirmanteMapa = fiv.getProperties().get("numberIdResponsable");
					String nifCertificadoFirmantePropiedad = fiv.getNumberUserId();
					String nifCertificadoFirmante = null;
					// Si la propiedad numberIdResponsable tiene el nif:
					if(nifCertificadoFirmanteMapa != null && !nifCertificadoFirmanteMapa.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmanteMapa;
					// Si la propiedad numberUserId tiene el nif:
					}else if(nifCertificadoFirmantePropiedad != null && !nifCertificadoFirmantePropiedad.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmantePropiedad;
					}
					// Si el nifCertificadoFirmante no tiene valor, no se ha recuperado el nif del certificado
					// de ninguna de las dos formas posibles:
					if(nifCertificadoFirmante == null){
						request.getSession().setAttribute("mensajeError","No se ha podido recuperar el nif " +
								"del certificado con el que se ha firmado. No se ha podido comprobar que presentante " + 
								" y firmante son la misma persona. Cancelado el envío.");
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
					if(!nifPresentante.equalsIgnoreCase(nifCertificadoFirmante)){
						request.getSession().setAttribute("mensajeError","Se ha cancelado el envío. " +
							"La firma del mensaje debe correr a cargo de la personalidad que figura " +
							"como presentante del trámite. El nif de la persona que consta como presentante es " +
							nifPresentante + " y el nif que consta en el certificado con el que se ha intentado firmar es " +
							nifCertificadoFirmante);
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
				}
				// Si entra aquí se ha firmado un dpr:
				String idFirma=fiv.getSignId();
				// Pone en sesión la firma y el timestamp:
				request.getSession().setAttribute("firmaDprBytes",XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
				request.getSession().setAttribute("selloDprBytes",XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				if (modeloFirmado != null && modeloFirmado == 1){
					response.sendRedirect("construirDprContinueModelo1.action");
				}else if (modeloFirmado != null && modeloFirmado == 2){
					response.sendRedirect("construirDprContinueModelo2.action");
				}else if (modeloFirmado != null && modeloFirmado == 3){
					response.sendRedirect("construirDprContinueModelo3.action");
				}else if (modeloFirmado != null && modeloFirmado == 4){
					response.sendRedirect("construirDprContinueModelo4.action");
				}else if (modeloFirmado != null && modeloFirmado == 5){
					response.sendRedirect("construirDprContinueModelo5.action");
				}else if(modeloFirmado != null && modeloFirmado == 6){
					response.sendRedirect("construirContinueEnvioEscritura.action");
				}
				
			}catch (org.bouncycastle.tsp.TSPException ex) {
				try {
					request.getSession().setAttribute("mensajeError","ERROR: " + "Imposible contactar con el servidor TSA ");
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK " + ex);
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}catch(Exception ex){
				log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
				try {
					if(ex.getMessage() != null){
						request.getSession().setAttribute("mensajeError","ERROR: " + ex.getMessage());
					}else{
						request.getSession().setAttribute("mensajeError","ERROR: " + ex);
					}
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}
		}else if(tipoMensajeFirmado!=null&&tipoMensajeFirmado.equals("rm")){
			// Si entra aquí se ha firmado un rm:
			try{	
				// Pone en sesión el identificador de la firma:
				String idFirma=fiv.getSignId();
				request.getSession().setAttribute("firmaCertificante",idFirma);
				// Pone en sesión el nif del certificante que acaba de firmar:
				Map<String,String> mapa = fiv.getProperties();
				request.getSession().setAttribute("nifCertificante",(String)mapa.get("numberIdResponsable"));
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				response.sendRedirect("enSesionFirmas.action");
			}catch(Exception ex){
				try {
					request.getSession().setAttribute("mensajeError","ERROR: " + ex);
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK " + ex);
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}
		}else if(tipoMensajeFirmado!=null&&tipoMensajeFirmado.equals("resumenAcuse")){
			try{
				// Comprueba que se ha firmado con un certificado cuyo nif coincide
				// con el presentante del trámite:
				if(comprobarFirmante.equalsIgnoreCase("si")){
					String nifPresentante = (String) request.getSession().getAttribute("nifPresentanteTramite");
					// Recupera el nif del certificado de las dos formas posibles:
					String nifCertificadoFirmanteMapa = fiv.getProperties().get("numberIdResponsable");
					String nifCertificadoFirmantePropiedad = fiv.getNumberUserId();
					String nifCertificadoFirmante = null;
					// Si la propiedad numberIdResponsable tiene el nif:
					if(nifCertificadoFirmanteMapa != null && !nifCertificadoFirmanteMapa.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmanteMapa;
					// Si la propiedad numberUserId tiene el nif:
					}else if(nifCertificadoFirmantePropiedad != null && !nifCertificadoFirmantePropiedad.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmantePropiedad;
					}
					// Si el nifCertificadoFirmante no tiene valor, no se ha recuperado el nif del certificado
					// de ninguna de las dos formas posibles:
					if(nifCertificadoFirmante == null){
						request.getSession().setAttribute("mensajeError","No se ha podido recuperar el nif " +
								"del certificado con el que se ha firmado. No se ha podido comprobar que presentante " + 
								" y firmante son la misma persona. Cancelado el envío.");
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
					if(!nifPresentante.equalsIgnoreCase(nifCertificadoFirmante)){
						request.getSession().setAttribute("mensajeError","Se ha cancelado el envío del mensaje. " +
								"La firma del mensaje debe correr a cargo de la personalidad que figura " +
						"como presentante del trámite. El nif de la persona que consta como presentante es " +
							nifPresentante + " y el nif que consta en el certificado con el que se ha intentado firmar es " +
							nifCertificadoFirmante);
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
				}
				String idFirma=fiv.getSignId();
				// Pone en sesión la firma y el timestamp:
				request.getSession().setAttribute("firmaResumenAcuseBytes",XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
				request.getSession().setAttribute("selloResumenAcuseBytes",XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				response.sendRedirect("alertAcuse.action");
			}catch (org.bouncycastle.tsp.TSPException ex) {
				try {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + 
					"Imposible contactar con el servidor TSA");
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}catch(Exception ex){
				try {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + ex);
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}
		}else if(tipoMensajeFirmado!=null&&tipoMensajeFirmado.equals("acuse")){
			try{
				// Comprueba que se ha firmado con un certificado cuyo nif coincide
				// con el presentante del trámite:
				if(comprobarFirmante.equalsIgnoreCase("si")){
					String nifPresentante = (String) request.getSession().getAttribute("nifPresentanteTramite");
					// Recupera el nif del certificado de las dos formas posibles:
					String nifCertificadoFirmanteMapa = fiv.getProperties().get("numberIdResponsable");
					String nifCertificadoFirmantePropiedad = fiv.getNumberUserId();
					String nifCertificadoFirmante = null;
					// Si la propiedad numberIdResponsable tiene el nif:
					if(nifCertificadoFirmanteMapa != null && !nifCertificadoFirmanteMapa.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmanteMapa;
					// Si la propiedad numberUserId tiene el nif:
					}else if(nifCertificadoFirmantePropiedad != null && !nifCertificadoFirmantePropiedad.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmantePropiedad;
					}
					// Si el nifCertificadoFirmante no tiene valor, no se ha recuperado el nif del certificado
					// de ninguna de las dos formas posibles:
					if(nifCertificadoFirmante == null){
						request.getSession().setAttribute("mensajeError","No se ha podido recuperar el nif " +
								"del certificado con el que se ha firmado. No se ha podido comprobar que presentante " + 
								" y firmante son la misma persona. Cancelado el envío.");
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
					if(!nifPresentante.equalsIgnoreCase(nifCertificadoFirmante)){
						request.getSession().setAttribute("mensajeError","Se ha cancelado el envío. " +
							"La firma de la notificación debe correr a cargo de la personalidad que figura " +
							"como presentante del trámite. El nif de la persona que consta como presentante es " +
							nifPresentante + " y el nif que consta en el certificado con el que se ha intentado firmar es " +
							nifCertificadoFirmante);
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
				}
				String idFirma=fiv.getSignId();
				// Pone en sesión la firma y el timestamp:
				request.getSession().setAttribute("firmaResumenAcuseBytes",XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
				request.getSession().setAttribute("selloResumenAcuseBytes",XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				response.sendRedirect("construirContinue2Acuse.action");
			}catch (org.bouncycastle.tsp.TSPException ex) {
				try {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + 
					"Imposible contactar con el servidor TSA ");
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}catch(Exception ex){
				try {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + ex);
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}
		}else if(tipoMensajeFirmado!=null&&tipoMensajeFirmado.equals("documentacionEscritura")){
			try{
				// Comprueba que se ha firmado con un certificado cuyo nif coincide
				// con el presentante del trámite:
				if(comprobarFirmante.equalsIgnoreCase("si")){
					String nifPresentante = (String) request.getSession().getAttribute("nifPresentanteTramite");
					// Recupera el nif del certificado de las dos formas posibles:
					String nifCertificadoFirmanteMapa = fiv.getProperties().get("numberIdResponsable");
					String nifCertificadoFirmantePropiedad = fiv.getNumberUserId();
					String nifCertificadoFirmante = null;
					// Si la propiedad numberIdResponsable tiene el nif:
					if(nifCertificadoFirmanteMapa != null && !nifCertificadoFirmanteMapa.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmanteMapa;
					// Si la propiedad numberUserId tiene el nif:
					}else if(nifCertificadoFirmantePropiedad != null && !nifCertificadoFirmantePropiedad.equals("")){
						nifCertificadoFirmante = nifCertificadoFirmantePropiedad;
					}
					// Si el nifCertificadoFirmante no tiene valor, no se ha recuperado el nif del certificado
					// de ninguna de las dos formas posibles:
					if(nifCertificadoFirmante == null){
						request.getSession().setAttribute("mensajeError","No se ha podido recuperar el nif " +
								"del certificado con el que se ha firmado. No se ha podido comprobar que presentante " + 
								" y firmante son la misma persona. Cancelado el envío.");
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
					if(!nifPresentante.equalsIgnoreCase(nifCertificadoFirmante)){
						request.getSession().setAttribute("mensajeError","Se ha cancelado el envío. " +
								"La firma de la documentación debe correr a cargo de la personalidad que figura " +
						"como presentante del trámite. El nif de la persona que consta como presentante es " +
							nifPresentante + " y el nif que consta en el certificado con el que se ha intentado firmar es " +
							nifCertificadoFirmante);
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
				}
				String idFirma=fiv.getSignId();
				// Pone en sesión la firma y el timestamp:
				request.getSession().setAttribute("firmaPdfEscrituraBytes",XmlBuilderSupportUtils.getCMSSignatureWithOutTimeStampBytes(idFirma));
				request.getSession().setAttribute("selloPdfEscrituraBytes",XmlBuilderSupportUtils.getTimeStampFromCMSbytes(idFirma));
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				response.sendRedirect("mostrarAlertEnvioEscritura.action");
			}catch (org.bouncycastle.tsp.TSPException ex) {
				try {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + 
					"Imposible contactar con el servidor TSA ");
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}catch(Exception ex){
				try {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", ex);
					request.getSession().setAttribute("mensajeError","ERROR: " + ex);
					response.sendRedirect("discriminarError.action");
					return;
				}catch (Exception e) {
					log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "signOK ", e);
				}
			}
		}
		
	}

	public void init(ServletConfig config) {
		try {
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_INICIO + "init");
			super.init(config);
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "init");
		} catch (ServletException e) {
			log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "init ", e);
		}
	}

}
