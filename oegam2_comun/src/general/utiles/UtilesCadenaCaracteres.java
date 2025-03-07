package general.utiles;

import java.sql.Timestamp;
import java.text.Normalizer;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Clase que contiene metodos para el tratamiento de las cadenas de caracteres.
 * 
 *
 */
public class UtilesCadenaCaracteres {
	
	private static UtilesCadenaCaracteres utilesCadenaCaracteres;

	private static char[] OLD_CHAR = {'Á','É','Í','Ó','Ú','º','ª','Ñ','Ä','Ë','Ï','Ö','Ü','À','È','Ì','Ò','Ù'};
	private static char[] NEW_CHAR = {'A','E','I','O','U',' ',' ','&','A','E','I','O','U','A','E','I','O','U'};
	private static char[] OLD_CHAR_P = {'Á','É','Í','Ó','Ú','º','ª','Ñ','Ä','Ë','Ï','Ö','Ü','(',')','=','À','È','Ì','Ò','Ù'};
	private static char[] NEW_CHAR_P = {'A','E','I','O','U',' ',' ','&','A','E','I','O','U',' ',' ',' ','A','E','I','O','U'};
	
	private static char[] OLD_CHAR_NUEVA_APLICACION = {'Á','É','Í','Ó','Ú','º','ª','Ä','Ë','Ï','Ö','Ü','À','È','Ì','Ò','Ù'};
	private static char[] NEW_CHAR_NUEVA_APLICACION = {'A','E','I','O','U',' ',' ','A','E','I','O','U','A','E','I','O','U'};
	
	//MATW PROBLEMA CON LAS Ñs DE MATW
	private static char[] OLD_CHAR_MATW = {'Á','É','Í','Ó','Ú','º','ª','Ä','Ë','Ï','Ö','Ü','=','À','È','Ì','Ò','Ù'};
	private static char[] NEW_CHAR_MATW = {'A','E','I','O','U',' ',' ','A','E','I','O','U',' ','A','E','I','O','U'};
	
	@Autowired
	UtilesFecha utilesFecha;
	
	public static UtilesCadenaCaracteres getInstance() {
		if (utilesCadenaCaracteres == null) {
			utilesCadenaCaracteres = new UtilesCadenaCaracteres();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesCadenaCaracteres);
		}
		return utilesCadenaCaracteres;
	}

	public UtilesCadenaCaracteres() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	/**
	 * Metodo que quita de la cadena de caracteres que recibe los ceros de delante. Si la cadena que
	 * recibes es <code>null</code> o cadena vacia, devuelve el mismo valor.
	 * 
	 * @param valor Cadena a modificar quitando los ceros de delante
	 * @return Devuelve la misma cadena pero sin los ceros de delante
	 */
	public static final String quitaCerosDelante(String valor){
		String cadena = null;
		int index = 0;
		
		if(null != valor && !"".equals(valor.trim())){
			while(index < valor.length() && valor.charAt(index) == '0'){
				index++;
			}
			cadena = valor.substring(index);
		}else if(null != valor){
			cadena = valor.trim();
		}
		
		return cadena;
	}
	
	// Entorno estático para la codificación de un array de bytes en base 64
	public static String doBase64Encode(byte[]array){
		return new String(Base64.encodeBase64(array));
	}
	
	/**
	 * Quita si existen los espacios en blanco del principio de la cadena de caracteres que recibe 
	 * @param valor
	 * @return Devuelve la cadena que recibe pero sin los espacios en blanco de delante
	 */
	public static String quitaEspaciosDelante(String valor){
		String cadena = null;
		int index = 0;

		if(null != valor){
			if(!"".equals(valor.trim())){
				while(valor.charAt(index) == ' '){
					index++;
				}
			}
			cadena = valor.substring(index);
		}		
		return cadena;
	}
	
	/**
	 * Sustituye todos los caracteres del OLD_CHAR encontrados en la cadena por los carácteres de NEW_CHAR
	 * @param valor
	 * @return Cadena con los caracteres sustituidos
	 */
	public static String sustituyeCaracteres(String valor){
		String nuevoValor = valor.toUpperCase();
		for(int i=0;i<OLD_CHAR.length;i++){
			nuevoValor = nuevoValor.replace(OLD_CHAR[i], NEW_CHAR[i]);
		}
		return nuevoValor;
	}
	
	/**
	 * Sustituye todos los caracteres del OLD_CHAR_NUEVA_APLICACION encontrados en la cadena por los carácteres de NEW_CHAR_NUEVA_APLICACION
	 * @param valor
	 * @return Cadena con los caracteres sustituidos
	 */
	public static String sustituyeCaracteresNuevaAplicacion(String valor){
		String nuevoValor = valor.toUpperCase();
		for(int i=0;i<OLD_CHAR_NUEVA_APLICACION.length;i++){
			nuevoValor = nuevoValor.replace(OLD_CHAR_NUEVA_APLICACION[i], NEW_CHAR_NUEVA_APLICACION[i]);
		}
		return nuevoValor;
	}
	
	/**
	 * Sustituye todos los caracteres del OLD_CHAR encontrados en la cadena por los carácteres de NEW_CHAR
	 * Incorpora más caracteres que la anterior.
	 * @param valor
	 * @return Cadena con los caracteres sustituidos
	 */
	public static String sustituyeCaracteres_Plus(String valor){
		String nuevoValor = valor.toUpperCase();
		for(int i=0;i<OLD_CHAR_P.length;i++){
			nuevoValor = nuevoValor.replace(OLD_CHAR_P[i], NEW_CHAR_P[i]);
		}
		return nuevoValor;
	}
	
	/**
	 * Sustituye todos los caracteres del OLD_CHAR encontrados en la cadena por los carácteres de NEW_CHAR
	 * Incorpora más caracteres que la anterior.
	 * @param valor
	 * @return Cadena con los caracteres sustituidos
	 */
	public static String sustituyeCaracteres_MATW(String valor){
		String nuevoValor = valor.toUpperCase();
		for(int i=0;i<OLD_CHAR_MATW.length;i++){
			nuevoValor = nuevoValor.replace(OLD_CHAR_MATW[i], NEW_CHAR_MATW[i]);
		}
		return nuevoValor;
	}

	/**
	 * Elimina los caracteres extraños del texto
	 * @param texto
	 * @return
	 */
	public static String quitarCaracteresExtranios(String texto) {
		texto = texto.replace("º", "");
		texto = texto.replace("ª", "");
		texto = texto.replace("\\", "");
		texto = texto.replace("!", "");
		texto = texto.replace("|", "");
		texto = texto.replace("\"", "");
		texto = texto.replace("·", "");
		texto = texto.replace("#", "");
		texto = texto.replace("$", "");
		texto = texto.replace("~", "");
		texto = texto.replace("%", "");
		texto = texto.replace("&", "");
		texto = texto.replace("/", "");
		texto = texto.replace("(", "");
		texto = texto.replace(")", "");
		texto = texto.replace("=", "");
		texto = texto.replace("'", "");
		texto = texto.replace("?", "");
		texto = texto.replace("¡", "");
		texto = texto.replace("`", "");
		texto = texto.replace("^", "");
		texto = texto.replace("[", "");
		texto = texto.replace("+", "");
		texto = texto.replace("*", "");
		texto = texto.replace("]", "");
		texto = texto.replace("´", "");
		texto = texto.replace("¨", "");
		texto = texto.replace("{", "");
		texto = texto.replace("ç", "");
		texto = texto.replace("}", "");
		texto = texto.replace("<", "");
		texto = texto.replace(">", "");
		texto = texto.replace(",", "");
		texto = texto.replace(";", "");
		texto = texto.replace(".", "");
		texto = texto.replace(":", "");
		texto = texto.replace("-", "");
		texto = texto.replace("_", "");
		return texto;
	}
	
	/** Autor: Julio Molina
	 *  Fecha: 27/08/2012
	 * Metodo para que se devuelva el subString de un TimeStamp
	 */
	public String getFechaSinMilisegundos(Timestamp fecha){
    	return utilesFecha.formatoFecha(fecha) + " " + fecha.toString().substring(10,16);
    }
	
	/** Autor: Julio Molina
	 *  Fecha: 05/09/2012
	 * Metodo para que se devuelva la duracion de la sesion en segundos
	 */
	public static String getTiempoSesion(Timestamp fechaInicio, Timestamp fechaFin){
		
		if (fechaFin != null){
    	long segundos = (fechaFin.getTime() - fechaInicio.getTime()) / 1000;
    	//obtenemos las horas
    	 long horas = segundos / 3600;
    	 
    	   //restamos las horas para continuar con minutos
    	 segundos -= horas*3600;
    	 
    	 //igual que el paso anterior
    	 long minutos = segundos /60;
    	 segundos -= minutos*60;
    	 
    	 return String.valueOf(horas)+"h " + String.valueOf(minutos)+"m " + String.valueOf(segundos)+"s" ;
		} else{
			return "Usuario Activo";
		}
    }
	
	/**
	 * @author Santiago Cuenca
	 * Fecha: 23/01/2013
	 */
	public static String nl2br(String cadena){
		
		String cadenaFormateada=null;
		if(cadena!=null){
			cadenaFormateada = cadena.replaceAll("\n","<br />");
		}
		
		return cadenaFormateada;
		
	}
	
	public static String reemplazarAcentoss(String input) {
	    // Cadena de caracteres original a sustituir.
	    String original = "áéíóúÁÉÍÓÚ";
	    // Cadena de caracteres ASCII que reemplazarán los originales.
	    String ascii = "aeiouAEIOU";
	    String output = input;
	    for (int i=0; i<original.length(); i++) {
	        // Reemplazamos los caracteres con acentos.
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }//for i
	    return output;
	}
	
	/**
     * Método que normaliza los caracteres acentuados
     * */
	public static String stripAccents(String strToStrip){
          String strStripped = null;
          //Normalizamos en la forma NFD (Canonical decomposition)
          strToStrip = Normalizer.normalize(strToStrip, Normalizer.Form.NFD);
          //Reemplazamos los acentos con una una expresión regular de Bloque Unicode
          strStripped = strToStrip.replaceAll("\\p{IsM}+", "");
           
          return strStripped;
     }
	
}