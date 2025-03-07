package trafico.utiles.enumerados;

public enum ResultadoAvpo {

	Recibido("1", "Recibido") {
		public String toString() {
			return "1";
		}
	},
	Pendiente("0", "Pendiente") {
		public String toString() {
			return "0";
		}
	},
	Recibido_error("3", "Recibido error") {
		public String toString() {
			return "3";
		}
	},
	Referencia("2", "Referencia") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ResultadoAvpo(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static ResultadoAvpo convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if ("1".equals(valorEnum))
			return Recibido;

		if ("0".equals(valorEnum))
			return Pendiente;

		if ("3".equals(valorEnum))
			return Recibido_error;

		if ("2".equals(valorEnum))
			return Referencia;
		else
			return null;
	}

}