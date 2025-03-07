package utilidades.mensajes;

import java.util.Properties;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

public class GestorFicherosPropiedades
{   
	private static final ILoggerOegam log = LoggerOegam.getLogger(GestorFicherosPropiedades.class);
	
	private Properties ficheroPropiedades=null;

	public Properties getFicheroPropiedades() {
		return ficheroPropiedades;
	}

	public GestorFicherosPropiedades(String nombreFichero) throws OegamExcepcion
	{
		
		if(null==nombreFichero || nombreFichero.length()==0){
			throw new OegamExcepcion(EnumError.error_00012);
		}
		
		ficheroPropiedades = new Properties();
        try {
        	ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(nombreFichero));
        }catch(Throwable e)
        {   
        	log.error("Error al cargar el archivo", e);
        	ficheroPropiedades=null;
//        	throw new OegamExcepcion(EnumError.error_00007);
        	//e.printStackTrace();
        }		
	}

	public String getMensaje(String claveMensaje) throws OegamExcepcion
	{
		if(ficheroPropiedades==null){
			throw new OegamExcepcion(EnumError.error_00012);    		
		}
		if(null==claveMensaje || claveMensaje.length()==0){
			throw new OegamExcepcion(EnumError.error_00015);
		}
    	return ficheroPropiedades.getProperty(claveMensaje);
 	}
	
	public Object setMensaje(String claveMensaje, String valor) throws OegamExcepcion
	{
		if(ficheroPropiedades==null){
			throw new OegamExcepcion(EnumError.error_00012);    		
		}
		if(null==claveMensaje || claveMensaje.length()==0){
			throw new OegamExcepcion(EnumError.error_00015);
		}
    	
		return ficheroPropiedades.setProperty(claveMensaje, valor);

 	}
	
}