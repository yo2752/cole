package trafico.modelo;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.BuscadorAlternativo;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ModeloMateBuscador implements BuscadorAlternativo {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloMateBuscador.class);
	private static final String RUTA_ALTERNATIVA = "RUTA_ALTERNATIVA";
	private static final String RUTA_XML = "xml/";
	private static final String RUTA_PTC = "pdf/PTC/";
	private static final String RUTA_FICHATECNICA = "pdf/FichaTecnica";
	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ModeloMateBuscador() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public List<File> buscar(String tipo, String subTipo, BigDecimal numExpediente) {
		List<File> listaFile = null;
		String ruta = gestorPropiedades.valorPropertie(RUTA_ALTERNATIVA);
		if(subTipo.equals(ConstantesGestorFicheros.PTC)){
			ruta += RUTA_PTC;
		} else if(subTipo.equals(ConstantesGestorFicheros.FICHATECNICA)){
			ruta += RUTA_FICHATECNICA;
		} else{
			ruta += RUTA_XML;
		}
		File directorio = new File (ruta);
		if(directorio.exists()){
			listaFile = new ArrayList<>();

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
		String ruta = gestorPropiedades.valorPropertie(RUTA_ALTERNATIVA);
		if(subTipo.equals(ConstantesGestorFicheros.PTC)){
			ruta += RUTA_PTC;
			String ptc = ruta+=nombreFichero;
			File resultado =new File(ptc);
			if(resultado.exists()){
				return resultado;
			}
		}
		if(subTipo.equals(ConstantesGestorFicheros.FICHATECNICAALTERNATIVA)){
			ruta += RUTA_FICHATECNICA;
			String envio = ruta+=nombreFichero;
			File resultado = new File(envio);
			if(resultado.exists()){
				return resultado;
			}
		}
		if(subTipo.equals(ConstantesGestorFicheros.ENVIO)){
			ruta += RUTA_XML;
			String envio = ruta+=nombreFichero;
			File resultado = new File(envio);
			if(resultado.exists()){
				return resultado;
			}
		}
		return null;
	}

}