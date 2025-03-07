package trafico.modelo;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.interfaz.BuscadorAlternativo;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloCheckCTITBuscador implements BuscadorAlternativo {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloCheckCTITBuscador.class);
	private static String RUTA_ALTERNATIVA ="/temp/xml/";
	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ModeloCheckCTITBuscador() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public List<File> buscar(String tipo, String subTipo,
			BigDecimal numExpediente) {
		List<File> listaFile = null;
		String nombreHost = gestorPropiedades.valorPropertie("nombreHost");
		String ruta = gestorPropiedades.valorPropertie("ficheros."+nombreHost)+RUTA_ALTERNATIVA;
		File directorio = new File (ruta); 
		if(directorio.exists()){
			listaFile = new ArrayList<File>();

			for (File next :directorio.listFiles()){
				if(next.getName().indexOf(numExpediente.toString())!=-1){
					listaFile.add(next);
				}
			}
		}
		return listaFile;
	}

	@Override
	public File buscarNombre(String tipo, String subTipo, String nombreFichero) {
		File file = null;
		String nombreHost = gestorPropiedades.valorPropertie("nombreHost");
		String ruta = gestorPropiedades.valorPropertie("ficheros."+nombreHost)+RUTA_ALTERNATIVA;
		File fichero = new File (ruta+nombreFichero); 
		if(fichero.exists()){
			return fichero;
		} else {
			return null;
		}
	}

}