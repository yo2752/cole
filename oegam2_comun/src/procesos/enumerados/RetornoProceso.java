package procesos.enumerados;

/**
 * Tipos de retorno validos de los procesos monitorizados
 * 
 * CORRECTO				Ejecuci�n correcta
 * ERROR_RECUPERABLE	Error no grave que permite volver a ejecutar el proceso en poco tiempo
 * ERROR_NO_RECUPERABLE	Error grave que implica que la ejecuci�n se debe posponer una cantidad mayor de tiempo
 * SIN_DATOS			El proceso se parar� hasta que reciba notificaci�n de que vuelve a tener datos 
 * @author
 *
 */
public enum RetornoProceso {

	CORRECTO(0), ERROR_RECUPERABLE(1), ERROR_NO_RECUPERABLE(2), SIN_DATOS(3), ERROR_RECUPERABLE_TIMEOUT(4);
	
	private RetornoProceso(int numero) {
		valorEnum = numero;
	}
	
	private int valorEnum;
	public int getValorEnum() {
		return valorEnum;
	}
	
}
