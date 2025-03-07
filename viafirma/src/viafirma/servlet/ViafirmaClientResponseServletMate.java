package viafirma.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class ViafirmaClientResponseServletMate extends ViafirmaClientServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ViafirmaClientResponseServlet.class);

	public ViafirmaClientResponseServletMate() {
		super();
	}

	@Override
	public void authenticateOK(UsuarioGenericoViafirma usuario,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_INICIO + "authenticateOK");
			String tipoDeCertificado = usuario.getTypeCertificate();
			if(tipoDeCertificado == null || !tipoDeCertificado.equalsIgnoreCase("FirmaProfesional")){
				request.getSession().setAttribute("mensajeError", "La plataforma sólo reconoce, como" +
						" válidos los certificados de 'Firma Profesional' para acceder a la misma.");
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "authenticateOK");
				response.sendRedirect("discriminarError.action");
				return;
			}

			String nifUsuario = ((Map<String,String>)usuario.getProperties()).get("numberIdResponsable");

			if (nifUsuario == null || "".equals(nifUsuario)){
				nifUsuario = usuario.getNumberUserId();
			}
			HttpSession sesion = request.getSession();
			sesion.setAttribute(utilidades.mensajes.Claves.CLAVE_NIF_Viafirma,nifUsuario);
			sesion.setAttribute("validada", "true");
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "authenticateOK");
			response.sendRedirect("Registrar.action");
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
		
		// Recupera el valor del atributo de request tipoMensajeFirmado 
		// para saber si lo que se ha firmado es un rm o un dpr:
				String idFirma=fiv.getSignId();
				// Pone en sesión la firma y el timestamp:
				try
				{
				
				String firmaColegiado =new String( XmlBuilderSupportUtils.getCMSSignatureBytes(idFirma));
//				firmaColegiado = "<AFIRMA>"+firmaColegiado+"</AFIRMA>";//encerramos el contenido del resultado de la firma para pruebas.
				request.getSession().setAttribute("firmaColegiado",firmaColegiado);
				log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
				response.sendRedirect("completarXmlConFirmaColegiadoAltasMatriculacion.action");
				
				if(comprobarFirmante.equalsIgnoreCase("si")){
					String nifPresentante = (String) request.getSession().getAttribute("nifPresentanteTramite");
					String nifCertificadoFirmante = fiv.getProperties().get("numberIdResponsable");
					if(!nifPresentante.equalsIgnoreCase(nifCertificadoFirmante)){
						request.getSession().setAttribute("mensajeError","Se ha cancelado el envío. " +
								"La firma del mensaje debe correr a cargo de la personalidad que figura " +
						"como presentante del trámite.");
						log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "signOK");
						response.sendRedirect("discriminarError.action");
						return;
					}
				}
				}
				catch (Exception e) {
					try{
						log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "error " + e);
						request.getSession().setAttribute("mensajeError",e);
						response.sendRedirect("discriminarError.action");
						return;
					}catch(IOException ex){
						log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "error " + ex);
					}
				}
		
	}

	public void init(ServletConfig config) {
		try {
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_INICIO + "init");
			super.init(config);
			log.info(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_FIN + "init");
		} catch (ServletException e) {
			log.error(Claves.CLAVE_LOG_SERVLET_VIAFIRMA_ERROR + "init " + e.getMessage());
			e.printStackTrace();
		}
	}

}
