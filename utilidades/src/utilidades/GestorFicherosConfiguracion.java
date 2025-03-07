package utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestorFicherosConfiguracion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorFicherosConfiguracion.class);
	private static String ARCHIVO = "config.dat";
	Properties propiedades;

	public GestorFicherosConfiguracion(String Archivo) {
		ARCHIVO = Archivo;
		inicializar();
	}

	public GestorFicherosConfiguracion() {
		inicializar();
	}

	public void inicializar() {
		propiedades = new Properties();

		String sFichero = ARCHIVO;
		File fichero = new File(sFichero);

		if (fichero.exists()) {
			try {
				FileInputStream archivo = new FileInputStream(ARCHIVO);
				propiedades.load(archivo);
			} catch (Exception e) {
				log.error(e);
			}
		} else {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
				log.error(e);
			}
		}

	}

	public String getPropiedad(String propiedad) {
		return propiedades.getProperty(propiedad);
	}

	public void setPropiedad(String propiedad, String dato) {
		try {
			propiedades.setProperty(propiedad, dato);
			propiedades.store(new FileOutputStream(ARCHIVO), null); // Para que los cambios se almacenen en archivo
		} catch (IOException e) {
			log.error(e);
		}

	}

}