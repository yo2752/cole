package administracion.cache.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.web.OegamExcepcion;

public class PdfLopdServlet extends HttpServlet {

	private static final long serialVersionUID = -2563460079936215624L;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public void init() throws ServletException {
		gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
		super.init();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FileResultBean fichero;
		try {
			fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DOCUMENTOS_LOPD,
					null, null, utilesColegiado.getNumColegiadoSession(), ConstantesGestorFicheros.EXTENSION_PDF);

		/*  byte[] pdf = gestorDocumentos.transformFiletoByte(fichero.getFile());
			if(pdf != null){
				PdfReader reader = new PdfReader(pdf);
				PdfStamper stamper = new PdfStamper(reader,response.getOutputStream());
				PdfWriter writer = stamper.getWriter();
				StringBuffer javascript = new StringBuffer();
				javascript.append("var params = this.getPrintParams();");
				javascript.append("params.interactive = params.constants.interactionLevel.silent;");
				javascript.append("params.pageHandling = params.constants.handling.shrink;");
				javascript.append("this.print(params);");
				PdfAction pdfAction= PdfAction.javaScript(javascript.toString(), writer);
				writer.addJavaScript(pdfAction);
				//stamper.close();
			}*/
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=\"" + utilesColegiado.getNumColegiadoSession() + ".pdf" + "\"");
			response.setContentLength((int)fichero.getFile().length());
			ServletOutputStream os = response.getOutputStream();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(fichero.getFile()));
			int readBytes = 0;
			while ((readBytes = in.read()) != -1) {
				os.write(readBytes);
			}
			os.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (OegamExcepcion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}