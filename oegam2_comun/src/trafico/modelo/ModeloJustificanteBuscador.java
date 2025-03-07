package trafico.modelo;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.interfaz.BuscadorAlternativo;

public class ModeloJustificanteBuscador implements BuscadorAlternativo {

	private static String RUTA_ALTERNATIVA_JUST ="pdf/justificantes/";
	private static String RUTAALTERNATIVA ="RUTA_ALTERNATIVA";
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public ModeloJustificanteBuscador() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public List<File> buscar(String tipo, String subTipo, BigDecimal numExpediente) {
		List<File> listaFile = null;
		String ruta = gestorPropiedades.valorPropertie(RUTAALTERNATIVA)+RUTA_ALTERNATIVA_JUST;
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
		File fichero = null;
		String ruta = gestorPropiedades.valorPropertie(RUTAALTERNATIVA)+RUTA_ALTERNATIVA_JUST;
		ruta +=nombreFichero;
		fichero = new File(ruta);
		return fichero;
	}

}
