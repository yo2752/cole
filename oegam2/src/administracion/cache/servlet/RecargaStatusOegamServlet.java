package administracion.cache.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class RecargaStatusOegamServlet extends RecargaCacheServlet {

	private static final long serialVersionUID = -812206476802590240L;
	private static final String PROPERTY_URL_STATUS = "recarga.status.url";

	private static final ILoggerOegam log = LoggerOegam.getLogger(RecargaStatusOegamServlet.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("Recibo una peticion de refresco status de Oegam por POST desde la IP: "+req.getRemoteAddr());
		log.info("IP detras del proxy: "+req.getHeader("X-FORWARDED-FOR"));

		URL urlStatus = null;
		InputStream is = null;
		ByteArrayOutputStream os = null;
		Source hojaTransformacion = null;
		Transformer transformer = null;
		StreamResult result = null;
		DocumentBuilderFactory domFact = null;
		DocumentBuilder builder = null;
		Document doc = null;
		DOMSource domSource = null;
		TransformerFactory tf = null;

		try {
			urlStatus = new URL(gestorPropiedades.valorPropertie(PROPERTY_URL_STATUS));
			is = urlStatus.openConnection().getInputStream();

			hojaTransformacion = new StreamSource(getClass().getClassLoader().
					getResourceAsStream("hoja_transformacion.xslt"));

			domFact = DocumentBuilderFactory.newInstance();

			domFact.setValidating(false);
			domFact.setIgnoringElementContentWhitespace(true);

			builder = domFact.newDocumentBuilder();
			doc = builder.parse(is);
			domSource = new DOMSource(doc);

			os = new ByteArrayOutputStream();
			result = new StreamResult(os);

			tf = TransformerFactory.newInstance();

			transformer = tf.newTransformer(hojaTransformacion);
			transformer.transform(domSource, result);

			utiles.borraFicheroSiExiste(getServletContext().getRealPath("/") + "statusOegamsalida.html");

			utiles.crearFicheroFisicoConBytes("salida", "html",
					getServletContext().getRealPath("/statusOegam/"), os.toByteArray());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}