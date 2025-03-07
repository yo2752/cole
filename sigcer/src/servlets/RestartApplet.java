package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import viafirma.utilidades.UtilesViafirma;

public class RestartApplet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RestartApplet.class);
       
    public RestartApplet() {
        super();
    }

    // Método que se ejecuta cuando un certificante pulse el botón de seleccionar un nuevo certificado.
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		log.info("SIGCER : Inicio del proceso de recarga de la página del applet");
		
		try{
			// Recupera de la sesion los datos a firmar:
			byte[] datosAfirmar = (byte[])request.getSession().getAttribute("datosAfirmar");
			// Recupera de la sesión el identificador del tramite:
			String idTramite = (String)request.getSession().getAttribute("idTramite");
			UtilesViafirma utilesViafirma=new UtilesViafirma();
			utilesViafirma.firmarSigcer(datosAfirmar, request, response);
			log.info("SIGCER : Invocado el applet para la firma del tramite con identificador : " + idTramite);
		}catch(Exception e){
			//e.printStackTrace();
			log.error("SIGCER : Se ha lanzado la siguiente excepcion : " + e);
		}
		
	}

}
