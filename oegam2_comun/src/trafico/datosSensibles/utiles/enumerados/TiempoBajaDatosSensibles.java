package trafico.datosSensibles.utiles.enumerados;

// Enumerado para controlar la caducidad de los datos sensibles en BBDD
public enum TiempoBajaDatosSensibles {

	No_caduca("0", "No caduca") {
		public String toString() {
			return "0";
		}
	},

	H24("1", "24 h") {
		public String toString() {
			return "1";
		}
	},

	H48("2", "48 h") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TiempoBajaDatosSensibles(String valorEnum, String nombreEnum) {
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
	public static TiempoBajaDatosSensibles convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 0:
				return No_caduca;
			case 1:
				return H24;
			case 2:
				return H48;
			default:
				return null;
		}
	}

	public static TiempoBajaDatosSensibles convertir(String valorEnum) {
		if (valorEnum == null || valorEnum.equals("")) {
			return null;
		}

		Integer valor = Integer.parseInt(valorEnum);

		switch (valor) {
			case 0:
				return No_caduca;
			case 1:
				return H24;
			case 2:
				return H48;
			default:
				return null;
		}
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 0) {
			return "No caduca";
		} else if (valorEnum == 1) {
			return "24 h";
		} else if (valorEnum == 2) {
			return "48 h";
		}
		return null;
	}

	public static Integer convertirToValor(String valorEnum) {
		if (valorEnum == "No caduca") {
			return 0;
		} else if (valorEnum == "24 h") {
			return 1;
		} else if (valorEnum == "48 h") {
			return 2;
		}
		return null;
	}
}