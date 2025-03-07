package utilidades.mensajes;

import java.util.*;

import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

public class GestorFicherosMensajes extends ResourceBundle
{
	private ResourceBundle ficheroMensajes=null;

	public GestorFicherosMensajes(String nombreFichero) throws OegamExcepcion
	{

		super();
		if(null==nombreFichero || nombreFichero.length()==0){
			throw new OegamExcepcion(EnumError.error_00012);
		}
		try{
			ficheroMensajes=getBundle(nombreFichero);
		}catch(java.lang.NullPointerException|MissingResourceException e){
			ficheroMensajes=null;
			throw e;
		}
	}

	public String getMensaje(String claveMensaje) throws OegamExcepcion
	{
		if(ficheroMensajes==null){
			throw new OegamExcepcion(EnumError.error_00012);    		
		}
		if(null==claveMensaje || claveMensaje.length()==0){
			throw new OegamExcepcion(EnumError.error_00056);
		}
    	return ficheroMensajes.getString(claveMensaje);
 	}

	@Override
	public Enumeration<String> getKeys() {
		if(ficheroMensajes==null){
			return null;    		
		}		
		return ficheroMensajes.getKeys();
	}

	@Override
	protected Object handleGetObject(String key) {
		if(ficheroMensajes==null){
			return null;    		
		}
		return ficheroMensajes.getObject(key);
	}
}
