package estadisticas.utiles.enumerados;

public enum AgrupacionVehiculos {
	Anyo("1", "A�o") {
		public String toString() {
			return "1";
		}
	},
	Mes_Anyo("2", "Mes/A�o") {
		public String toString() {
			return "2";
		}
	},
	Estado("3", "Estado") {
		public String toString() {
			return "3";
		}
	},
	Tipo_Tramite("4", "Tipo Tr�mite") {
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private AgrupacionVehiculos(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static AgrupacionVehiculos convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		Integer numero = new Integer(valorEnum);
		switch (numero) {
			case 1:
				return Anyo;
			case 2:
				return Mes_Anyo;
			case 3:
				return Estado;
			case 4:
				return Tipo_Tramite;
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
				return "A�o";
			case 2:
				return "Mes/A�o";
			case 3:
				return "Estado";
			case 4:
				return "Tipo Tr�mite";
			default:
				return null;
		}
	}
}