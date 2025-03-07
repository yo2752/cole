package trafico.utiles.enumerados;
//TODO MPC. Cambio IVTM. Esta clase está bien aquí. Hay que sobrescribir.

public enum EstadoIVTM {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Pendiente_Respuesta_Ayto("2", "Pendiente Respuesta Ayto") {
		public String toString() {
			return "2";
		}
	},

	Ivtm_Error("3", "IVTM Error") {
		public String toString() {
			return "3";
		}
	},

	Ivtm_Ok("4", "IVTM OK") {
		public String toString() {
			return "4";
		}
	},
	Pendiente_Respuesta_Modificacion_Ayto("5", "Pendiente Respuesta Modificacion Ayto") {
		public String toString() {
			return "5";
		}
	},

	Ivtm_Error_Modificacion("6", "IVTM Error Modificacion") {
		public String toString() {
			return "6";
		}
	},

	Ivtm_Ok_Modificacion("7", "IVTM OK Modificacion") {
		public String toString() {
			return "7";
		}
	},

	Ivtm_Importado("8", "IVTM Importado") {
		public String toString() {
			return "8";
		}
	},
	Pagado("9", "IVTM Pagado") {
		public String toString() {
			return "9";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoIVTM(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoIVTM convertir(String valorEnum) {
		for (EstadoIVTM element : EstadoIVTM.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoIVTM element : EstadoIVTM.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}