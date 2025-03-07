package org.gestoresmadrid.oegam2comun.registradores.xml.estilos;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.gestoresmadrid.core.springContext.ContextoSpring;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class StaxStreamSource {

	private static final ILoggerOegam log = LoggerOegam.getLogger(StaxStreamSource.class);

	/**
	 * Obtiene un fichero html a partir de aplicar los estilos de una hoja xsl a un xml del registro mercantil (mensaje rm CORPME)
	 * @param idTramite
	 * @return String con la ruta y el nombre del fichero html obtenido
	 * @throws Exception
	 * @throws OegamExcepcion
	 */
	public static String crearFicheroHtml(String idTramite) throws Exception, OegamExcepcion {

		try {
			GestorDocumentos modeloGuardar = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
			// Nombre y ruta del fichero donde se grabará el resultado de la transformación:
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.REGISTRADORES);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.REGISTRADORES_HTML);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_HTML);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(idTramite));
			ficheroBean.setFichero(new File(""));
			ficheroBean.setNombreDocumento(idTramite + ConstantesGestorFicheros.NOMBRE_DOC);
			File ficheroHTML = modeloGuardar.guardarFichero(ficheroBean);
			String ficheroHtml = ficheroHTML.getAbsolutePath();
			FileResultBean xmlSource = modeloGuardar.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.REGISTRADORES, ConstantesGestorFicheros.REGISTRADORES_XML, Utilidades
					.transformExpedienteFecha(idTramite), idTramite + ConstantesGestorFicheros.NOMBRE_RM, ConstantesGestorFicheros.EXTENSION_XML);

			// Fichero xml al que se le aplicarán los estilos del fichero xsl
			if (xmlSource == null || !xmlSource.getFile().exists()) {
				log.error("No se ha encontrado el XML origen");
			}
			StreamSource xmlFile = new StreamSource(xmlSource.getFile());

			// Fichero de estilos xsl
			StreamSource xslFile = new StreamSource(StaxStreamSource.class.getResourceAsStream("/registradores/esquemas/CORPME_RM/CORPME_RM_CeseyNombramiento_fc.xsl"));

			// Transformador para convertir el xml en html a través de las reglas expresadas en el fichero xsl:
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(xslFile);

			// Fichero html que recibirá el resultado de la transformación:
			StreamResult resultStream = new StreamResult(new File(ficheroHtml).getPath());

			// Transformación:
			transformer.transform(xmlFile, resultStream);
			return ficheroHtml;
		} catch (Exception e) {
			log.error(e);
			throw e;
		} catch (OegamExcepcion e) {
			log.error(e);
			throw e;
		}
	}
}
