package trafico.utiles.enumerados;

public enum EstadoTramiteAnuntis {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},

	Consultado_AVPO("4", "Consultado") {
		public String toString() {
			return "4";
		}
	},

	Visualizado("2", "Visualizado") {
		public String toString() {
			return "2";
		}
	},

	Validado("3", "Validado") {
		public String toString() {
			return "3";
		}
	},

	Procesado("9", "Procesado") {
		public String toString() {
			return "9";
		}
	},
	Pendiente_Envio("10", "Pendiente de respuesta de la DGT") {
		public String toString() {
			return "10";
		}
	},

	Finalizado_Con_Error("11", "Finalizado con Error") {
		public String toString() {
			return "11";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoTramiteAnuntis(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoTramiteAnuntis convertir(String valorEnum) {

		if (valorEnum == null) {
			return null;
		}
		for (EstadoTramiteAnuntis e : EstadoTramiteAnuntis.values()) {
			if (e.getValorEnum().equals(valorEnum)) {
				return e;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		EstadoTramiteAnuntis e = convertir(valorEnum);
		return e != null ? e.getNombreEnum() : null;
	}

}