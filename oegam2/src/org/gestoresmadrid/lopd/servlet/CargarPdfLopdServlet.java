package org.gestoresmadrid.lopd.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.web.OegamExcepcion;

public abstract class CargarPdfLopdServlet extends HttpServlet{

	private static final long serialVersionUID = 6879322699407531503L;

	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesColegiado utilesColegiado;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
        try {
            FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.DOCUMENTOS_LOPD, 
					null, null, utilesColegiado.getNumColegiadoSession(), ConstantesGestorFicheros.EXTENSION_PDF);
            if(fichero != null && fichero.getFile() != null){
	            PdfReader reader = new PdfReader(gestorDocumentos.transformFiletoByte(fichero.getFile()));
	            PdfStamper stamper = new PdfStamper(reader,response.getOutputStream());
	            PdfWriter writer = stamper.getWriter();
	            StringBuffer javascript = new StringBuffer();
	            javascript.append("var params = this.getPrintParams();");
	            javascript.append("params.interactive = params.constants.interactionLevel.silent;");
	            javascript.append("params.pageHandling = params.constants.handling.shrink;");
	            javascript.append("this.print(params);");
	            PdfAction pdfAction= PdfAction.javaScript(javascript.toString(), writer);
	            writer.addJavaScript(pdfAction);
	            stamper.close();
            }
  
        } catch (DocumentException de) {
            de.printStackTrace();
            System.err.println("document: " + de.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OegamExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
