package colas.utiles;

public enum EstadoPeticiones {

	PENDIENTES_ENVIO("1", "Pendiente de envío") {
		public String toString() {
			return "1";
		}
	},
	IMPRIMIENDO("5", "Imprimiendo") {
		public String toString() {
			return "1";
		}
	},
	ERROR_SERVICIO("9", "Error servicio") {
		public String toString() {
			return "9";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoPeticiones(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (PENDIENTES_ENVIO.valorEnum.equals(valorEnum)) {
			return PENDIENTES_ENVIO.nombreEnum;
		} else if (ERROR_SERVICIO.valorEnum.equals(valorEnum)) {
			return ERROR_SERVICIO.nombreEnum;
		} else if (IMPRIMIENDO.valorEnum.equals(valorEnum)) {
			return IMPRIMIENDO.nombreEnum;
		} else {
			return null;
		}
	}
}