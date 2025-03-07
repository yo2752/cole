package servlets;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.viafirma.cliente.ViafirmaClientServlet;
import org.viafirma.cliente.exception.CodigoError;
import org.viafirma.cliente.vo.FirmaInfoViafirma;
import org.viafirma.cliente.vo.UsuarioGenericoViafirma;

/**
 * Invocado desde la página del applet
 */
public class ViafirmaClientResponseServlet extends ViafirmaClientServlet {
	
	private static final long serialVersionUID = 1L;
	// log de errores	
	private static final Logger log = Logger.getLogger(ViafirmaClientResponseServlet.class);

	public ViafirmaClientResponseServlet() {
		super();
	}
	
	public void init(ServletConfig config) {
		try {
			super.init(config);
		} catch (ServletException e) {
			//e.printStackTrace();
			log.error("SIGCER : Se ha lanzado la siguiente excepcion inicializando el servlet de respuesta : " + e);
		}
	}

	@Override
	public void authenticateOK(UsuarioGenericoViafirma usuario,
			HttpServletRequest request, HttpServletResponse response) {
		// Este servlet sólo va a recibir peticiones de firma.
	}

	@Override
	public void cancel(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info("SIGCER : Se ha cancelado el proceso de firma.");
			request.setAttribute("mensaje", "Se ha cancelado el proceso de firma");
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		} catch (Exception e) {
			//e.printStackTrace();
			log.error("SIGCER : Se ha lanzado la siguiente excepcion cancelando el proceso de firma : " + e);
		}
	}

	@Override
	public void error(CodigoError codError, HttpServletRequest request,
			HttpServletResponse response) {
			try {
				if((codError.getName() == null || codError.getName().equals("")) && (codError.getMensaje() == null || codError.getMensaje().equals(""))){
					log.error("SIGCER : Se ha ejecutado el método error del servlet de viafirma sin recuperarse mensaje del mismo");
					request.setAttribute("mensaje", "Se ha ejecutado el método error del servlet de viafirma sin recuperarse mensaje del mismo");
				}else{
					log.error("SIGCER :" + codError.getCodigo() + " " + codError.getName() + " " + codError.getMensaje());
					request.setAttribute("mensaje", codError.getCodigo() + " " + codError.getName() + " " + codError.getMensaje());
				}
            	request.getRequestDispatcher("/result.jsp").forward(request, response);
			} catch (Exception e) {
				//e.printStackTrace();
				log.error("SIGCER : Se ha lanzado la siguiente excepcion en el metodo error del servlet : " + e);
			}
	}

	@Override
	public void signOK(FirmaInfoViafirma fiv, HttpServletRequest request,
			HttpServletResponse response) {
		
		try{
			log.info("SIGCER : Se ha ejecutado correctamente el proceso de firma.");
			// Pone en sesión el identificador de la firma:
			String idFirma=fiv.getSignId();
			request.getSession().setAttribute("idFirmaCertificante",idFirma);
			
			// Pone en sesión el nif del certificante que acaba de firmar:
			String nifCertificante = ((Map<String,String>)fiv.getProperties()).get("numberIdResponsable");
			
			if (nifCertificante == null || "".equals(nifCertificante)){
				nifCertificante=fiv.getNumberUserId();
			}
			request.getSession().setAttribute("nifCertificante",nifCertificante);

			request.getRequestDispatcher("/SignComplete").forward(request,response);

		}catch(Exception ex){
			log.error(ex);
		}

	}	

}
