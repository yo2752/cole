package org.gestoresmadrid.oegam2comun.registradores.utiles;

public final class UtilesExcepciones {

	private UtilesExcepciones() {
		super();
	}

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

}
