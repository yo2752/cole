package trafico.utiles.enumerados;

/**
 * Enumeraci�n de los diferentes c�digos de respuesta que se pueden obtener
 * despu�s de invocar una solicitud de datos a trav�s del emulador TN3270.
 *
 */
public enum CodigoRespuestaTraficoTN3270 {

	/**
	 * El proceso se ha ejecutado correctamente y se ha obtenido el resultado
	 * deseado.
	 */
	OK("OK", "OK") {
		public String toString() {
			return "OK";
		}
	},

	/**
	 * Se ha producido un error de timeout en la comunicaci�n.
	 */
	ERROR_TIMEOUT("ERROR_TIMEOUT", "ERROR_TIMEOUT") {
		public String toString() {
			return "OK";
		}
	},

	/**
	 * Se ha producido un error en la conexi�n con el servicio de tr�fico.
	 */
	ERROR_CONEXION("ERROR_CONEXION", "ERROR_CONEXION") {
		public String toString() {
			return "OK";
		}
	},

	/**
	 * Se ha producido un error en el proceso de consulta de datos debido a alg�n
	 * error en los par�metros de entrada, definidos en ParamsTraficoTN3270Bean. En
	 * este caso se indicar� el mensaje de error en la respuesta
	 * ResultTraficoTN3270, usando la CasillaTraficoTN3270 MENSAJE_ERROR.
	 */
	ERROR_PARAMETROS("ERROR_PARAMETROS", "ERROR_PARAMETROS") {
		public String toString() {
			return "OK";
		}
	},

	/**
	 * Se ha producido un error t�cnico en el proceso de obtenci�n de datos con el
	 * emulador TN3270.
	 */
	ERROR_TECNICO("ERROR_TECNICO", "ERROR_TECNICO") {
		public String toString() {
			return "OK";
		}
	},

	/**
	 * No existen los datos buscados con los par�metros dados.
	 */
	NO_EXISTEN_DATOS("NO_EXISTEN_DATOS", "NO_EXISTEN_DATOS") {
		public String toString() {
			return "OK";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private CodigoRespuestaTraficoTN3270(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}
