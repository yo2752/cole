package trafico.utiles.enumerados;

//TODO JJRR. Cambio IVTM. Esta clase es nueva.
//TODO MPC. Cambio IVTM. Esta clase es nueva.
public enum RespuestaPagoIVTM {

	Error("0", "Se ha producido un error durante el proceso de pago") {
		public String toString() {
			return "0";
		}
	},
	Correcto("1", "El pago se ha realizado correctamente") {
		public String toString() {
			return "1";
		}
	},
	Cancelado("2", "El proceso de pago se ha cancelado") {
		public String toString() {
			return "2";
		}
	},
	Datos_No_Correctos("3", "Los datos del pago recibidos no son correctos") {
		public String toString() {
			return "3";
		}
	},
	Pago_Ya_Realziado("4", "El pago ya ha sido realizado anteriormente") {
		public String toString() {
			return "4";
		}
	},
	Sin_Permisos("5", "El entorno no tiene permisos para realizar el pago") {
		public String toString() {
			return "5";
		}
	},
	Enviado("6", "El pago se ha enviado a la entidad bancaria a través de la banca electrónica") {
		public String toString() {
			return "6";
		}
	},
	Error_Certificado("7", "Se ha producido un error durante el tratamiento del certificado del usuario") {
		public String toString() {
			return "7";
		}
	},
	Error_Pago("8", "Se ha producido un error en el pago") {
		public String toString() {
			return "8";
		}
	},
	Sim_Permisos_Pago_Emisor("9", "El entorno no tiene permisos para realizar el pago del emisor") {
		public String toString() {
			return "9";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private RespuestaPagoIVTM(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static RespuestaPagoIVTM convertir(String valorEnum) {
		for (RespuestaPagoIVTM element : RespuestaPagoIVTM.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (RespuestaPagoIVTM element : RespuestaPagoIVTM.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return "Identificador del error devuelto por el banco";
	}
}