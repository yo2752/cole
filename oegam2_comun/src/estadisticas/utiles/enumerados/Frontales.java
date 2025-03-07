package estadisticas.utiles.enumerados;

public enum Frontales {

	frontal_31("31", "Frontal 31") {
		public String toString() {
			return "31";
		}
	},

	frontal_32("32", "Frontal 32") {
		public String toString() {
			return "32";
		}
	},

	frontal_33("33", "Frontal 33") {
		public String toString() {
			return "33";
		}
	},

	frontal_34("34", "Frontal 34") {
		public String toString() {
			return "34";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Frontales(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Frontales convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		Integer numero = new Integer(valorEnum);
		switch (numero) {
			case 1:
				return frontal_31;
			case 2:
				return frontal_32;
			case 3:
				return frontal_33;
			case 4:
				return frontal_34;
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
				return "Frontal 31";
			case 2:
				return "Frontal 32";
			case 3:
				return "Frontal 33";
			case 4:
				return "Frontal 34";
			default:
				return null;
		}
	}

}