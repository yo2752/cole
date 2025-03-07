package utilidades.validaciones;

public class LongitudCampo {

	

	public static String truncarString(Object  cadena, int tamanoMax) {
		
		String cadenaaux=null;
		if (cadena!=null)
		{
			if (cadena.toString().length()>tamanoMax)
			cadenaaux = cadena.toString().substring(0,tamanoMax);
			else cadenaaux = cadena.toString();
		}
		
		return cadenaaux;	
		
		}
}
