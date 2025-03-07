package trafico.beans.avpo;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.BuscadorAlternativo;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class BuscadorInformesAVPO implements BuscadorAlternativo {

	private static ILoggerOegam log = LoggerOegam.getLogger(BuscadorInformesAVPO.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public BuscadorInformesAVPO() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public List<File> buscar(String tipo, String subTipo, BigDecimal numExpediente) {
		List<File> result = new ArrayList<File>();
		if (ConstantesGestorFicheros.SOLICITUD_INFORMACION.equals(tipo)) {
			String ruta = gestorPropiedades.valorPropertie("ficheros.NODO2") + gestorPropiedades.valorPropertie(ConstantesTrafico.REF_PROP_ALMACEN_INFORMES_TECNICOS_VEHICULO);

			String nombreFichero = "Consultas_" + numExpediente + ConstantesGestorFicheros.EXTENSION_ZIP;
			File fichero = new File(ruta + nombreFichero);

			Integer j = 0;
			while (fichero.exists()) {
				result.add(fichero);
				fichero = new File(ruta + ++j + "_" + nombreFichero);
			}
		} else if (ConstantesGestorFicheros.ANUNTIS.equals(tipo)) {
			String ruta = gestorPropiedades.valorPropertie("ficheros.NODO2") + gestorPropiedades.valorPropertie("avpo.files.pdf.path");
			if (!ruta.endsWith(System.getProperty("file.separator"))) {
				ruta += System.getProperty("file.separator");
			}
			
			String nombreFichero = "Consulta_" + numExpediente + ConstantesGestorFicheros.EXTENSION_ZIP;
			File fichero = new File(ruta + nombreFichero);

			Integer j = 0;
			while (fichero.exists()) {
				result.add(fichero);
				fichero = new File(ruta + ++j + "_" + nombreFichero);
			}
		} else {
			log.error("No se ha reconocido el tipo de tramite de solicitud de información del fichero a buscar");
		}
		return result;
	}

	@Override
	public File buscarNombre(String tipo, String subTipo, String nombreFichero) {
		File fichero = null;
		if (ConstantesGestorFicheros.SOLICITUD_INFORMACION.equals(tipo)) {
			String ruta = gestorPropiedades.valorPropertie("ficheros.NODO2") + gestorPropiedades.valorPropertie(ConstantesTrafico.REF_PROP_ALMACEN_INFORMES_TECNICOS_VEHICULO);
			fichero = new File(ruta + nombreFichero);
		} else if (ConstantesGestorFicheros.ANUNTIS.equals(tipo)) {
				String ruta = gestorPropiedades.valorPropertie("ficheros.NODO2") + gestorPropiedades.valorPropertie("avpo.files.pdf.path");
				if (!ruta.endsWith(System.getProperty("file.separator"))) {
					ruta += System.getProperty("file.separator");
				}
				
				fichero =  new File(ruta + nombreFichero);
		} else {
			log.error("No se ha reconocido el tipo de tramite de solicitud de información del fichero a buscar");
		}
		return fichero != null && fichero.exists() ? fichero : null;
	}

}
