package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.viafirma.cliente.ViafirmaClient;
import org.viafirma.cliente.vo.Documento;

import utilidades.utiles.UtilesExcepciones;
import viafirma.utilidades.ConstantesViafirma;
import viafirma.utilidades.UtilesViafirma;

public class SignDoc extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SignDoc.class);
       
    public SignDoc() {
        super();
    }

    // Método que se ejecuta cuando un certificante pulse el enlace de la firma.
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		log.info("SIGCER : Inicio del proceso de firma del documento");
		
		try{
			String processId=request.getParameter("processId");
			String idc = request.getParameter("idc");
			if(processId == null){ 
				log.error("SIGCER : Ejecucion de servlet no soportada ");
				request.setAttribute("mensaje", "SIGCER : Ejecución de servlet no soportada ");
				request.getRequestDispatcher("/result.jsp").forward(request, response);
				return; 
			}
			// Comprueba si el processId viene con una barra y si la trae la quita:
			if(processId.contains("/")||processId.contains("\\")){
				processId=processId.substring(0,processId.length()-1);
			}
			// Pone en sesión el identificador del trámite:
			request.getSession().setAttribute("idTramite",processId);
			request.getSession().setAttribute("idc",idc);
			// Recupera los datos del documento mediante el parámetro que llega desde el enlace del correo del certificante:
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			ViafirmaClient viafirmaClient = utilesViafirma.getClient(ConstantesViafirma.SIGCER);
			Documento documento = viafirmaClient.getOriginalDocument(request.getParameter("signId"));
			byte[] datosAfirmar = documento.getDatos();
			if(datosAfirmar == null){
				String cad = "SIGCER : No se ha recuperado el documento de viafirma con id firma: \n" + 
				request.getParameter("signId");
				log.error(cad);
				request.setAttribute("mensaje", cad);
				request.getRequestDispatcher("/result.jsp").forward(request, response);
				return;
			}
			request.getSession().setAttribute("datosAfirmar",datosAfirmar);
			utilesViafirma.firmarSigcer(datosAfirmar, request, response);
			log.info("SIGCER : Invocado el applet para la firma del tramite con identificador : " + processId);
		}catch(Throwable e){
			String cad = "SIGCER : Se ha lanzado la siguiente excepcion : " + e + "\n" + 
				UtilesExcepciones.stackTraceAcadena(e, 1);
			log.error(cad);
			request.setAttribute("mensaje", cad);
			request.getRequestDispatcher("/result.jsp").forward(request, response);
		}
		
	}

}
