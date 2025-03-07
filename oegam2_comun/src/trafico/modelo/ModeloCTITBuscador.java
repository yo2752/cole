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

public class ModeloCTITBuscador implements BuscadorAlternativo {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloCTITBuscador.class);
	private static String RUTA_ALTERNATIVA ="RUTA_ALTERNATIVA";
	private static String RUTA_XML ="/temp/xml/";
	private static String RUTA_PTC ="/temp/pdf/PTC/";
	private static String RUTA_INFORME ="/temp/pdf/informe/";
	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ModeloCTITBuscador() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public List<File> buscar(String tipo, String subTipo, BigDecimal numExpediente) {
		List<File> listaFile = null;
		String nombreHost = gestorPropiedades.valorPropertie("nombreHost");
		String ruta = gestorPropiedades.valorPropertie("ficheros."+nombreHost);
		if(subTipo == null || subTipo.equals(ConstantesGestorFicheros.PTC)){
			ruta += RUTA_PTC;
		} else if(subTipo == null || subTipo.equals(ConstantesGestorFicheros.INFORMES)){
			ruta += RUTA_INFORME;
		} else{
			ruta += RUTA_ALTERNATIVA;
		}
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
		String nombreHost = gestorPropiedades.valorPropertie("nombreHost");
		String ruta = gestorPropiedades.valorPropertie("ficheros."+nombreHost);
		if(subTipo == null || subTipo.equals(ConstantesGestorFicheros.PTC)){
			ruta += RUTA_PTC;

			String ptc = ruta+=nombreFichero;
			File resultado =new File(ptc);
			if(resultado.exists()){
				return resultado;
			}
		}
		if(subTipo == null || subTipo.equals(ConstantesGestorFicheros.INFORMES)){
			ruta += RUTA_INFORME;

			String informe = ruta+=nombreFichero;
			File resultado =new File(informe);
			if(resultado.exists()){
				return resultado;
			}
		}
		if(subTipo == null || subTipo.equals(ConstantesGestorFicheros.ENVIO)){
			ruta += RUTA_XML;

			String envio = ruta+=nombreFichero;
			File resultado =new File(envio);
			if(resultado.exists()){
				return resultado;
			}

		}
		return null;
	}

}