package trafico.utiles.enumerados;

public enum EstadoJustificante {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},

	Pendiente_DGT("2", "Pendiente DGT") {
		public String toString() {
			return "2";
		}
	},

	Ok("3", "OK") {
		public String toString() {
			return "3";
		}
	},

	Anulado("4", "Anulado") {
		public String toString() {
			return "4";
		}
	},
	Pendiente_autorizacion_colegio("5", "Pendiente_autorizacion_colegio") {
		public String toString() {
			return "5";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoJustificante convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		Integer numero = new Integer(valorEnum);
		switch (numero) {
			case 1:
				return Iniciado;
			case 2:
				return Pendiente_DGT;
			case 3:
				return Ok;
			case 4:
				return Anulado;
			case 5:
				return Pendiente_autorizacion_colegio;
			default:
				return null;
		}
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;

		Integer numero = new Integer(valorEnum);
		switch (numero) {
			case 1:
				return "Iniciado";
			case 2:
				return "Pendiente_DGT";
			case 3:
				return "Ok";
			case 4:
				return "Anulado";
			case 5:
				return "Pendiente autorizacion colegio";
			default:
				return null;
		}
	}

	// Devuelve las siglas del mensaje que se debe consignar en tipo de mensaje
	// cuando se

}