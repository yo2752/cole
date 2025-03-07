package utilidades.utiles;


import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
public class UtilesExcepciones {
	
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(UtilesExcepciones.class);

	/**
	 * Recorre los elementos de la pila de llamadas de una excepción
	 * y devuelve una cadena con todos los valores de los getter
	 * de todos los elementos de la stack trace de dicha excepción.
	 * Útil para pintar en las trazas de log la pila de llamadas de una excepción.
	 * Tiene como parámetros la excepción y el número de elementos de la pila de
	 * llamadas que se desea que se devuelvan en la cadena.
	 */
	public static String stackTraceAcadena(Throwable e,int elementosAmostrar){
		String infoExcepcion=null;
		String pilaDeLlamadas="";
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		if(stackTraceElements==null||stackTraceElements.length==0){
			return "La pila de llamadas de la excepción vale null o tiene 0 elementos.";
		}
		int i = 1;
		for(StackTraceElement temp:stackTraceElements){
			infoExcepcion = "Elemento " + i + " de la pila de llamadas: ";  
			infoExcepcion += "Fichero: " + temp.getFileName() + ", ";
			infoExcepcion += "Clase: " + temp.getClassName() + ", ";
			infoExcepcion += "Metodo: " + temp.getMethodName() + ", ";
			infoExcepcion += "Numero de linea: " + temp.getLineNumber() + "\n";
			pilaDeLlamadas += infoExcepcion;
			if(elementosAmostrar==i){
				break;
			}
			i++;
		}
		return pilaDeLlamadas;
	}
	
	
	public static void logarException(Throwable e,String mensaje) {
		
		if(e.getMessage() != null)
			{
			log.error( e.getMessage() + "\n" + e + 
				" Pila de llamadas de la excepción:\n" + stackTraceAcadena(e, 3));
			}
		else{
			log.error( mensaje + "\n" + e + 
					" Pila de llamadas de la excepción:\n" + stackTraceAcadena(e, 3));
			}
	}
	
	
	
	public static void logarException(Throwable e,String mensaje,ILoggerOegam log) {

		if(e.getMessage() != null)
			{
			log.error(e.getMessage() + "\n" + e + 
				" Pila de llamadas de la excepción:\n" + stackTraceAcadena(e, 3));
			}
		else{
			log.error(mensaje + "\n" + e + 
					" Pila de llamadas de la excepción:\n" + stackTraceAcadena(e, 3));
			}
	}
	
	public static void logarOegamExcepcion(OegamExcepcion e,String mensaje,ILoggerOegam log) {

		log.error("mensaje de errror" + e.getMensajeError1());
		log.error("codigo de error" + e.getCodigoError()); 
		logarException(e, mensaje);
		
		
	}	
	
	
	
	
	
}
