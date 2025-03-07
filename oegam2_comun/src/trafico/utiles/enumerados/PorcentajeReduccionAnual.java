package trafico.utiles.enumerados;

public enum PorcentajeReduccionAnual {

	Cero("0", "100") {
		public String toString() {
			return "0";
		}
	},

	Uno("1", "84") {
		public String toString() {
			return "1";
		}
	},

	Dos("2", "67") {
		public String toString() {
			return "2";
		}
	},

	Tres("3", "56") {
		public String toString() {
			return "3";
		}
	},

	Cuatro("4", "47") {
		public String toString() {
			return "4";
		}
	},

	Cinco("5", "39") {
		public String toString() {
			return "5";
		}
	},

	Seis("6", "34") {
		public String toString() {
			return "6";
		}
	},

	Siete("7", "28") {
		public String toString() {
			return "7";
		}
	},

	Ocho("8", "24") {
		public String toString() {
			return "8";
		}
	},

	Nueve("9", "19") {
		public String toString() {
			return "9";
		}
	},

	Diez("10", "17") {
		public String toString() {
			return "10";
		}
	},

	Once("11", "13") {
		public String toString() {
			return "11";
		}
	},

	Doce("12", "10") {
		public String toString() {
			return "12";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private PorcentajeReduccionAnual(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	// valueOf
	public static PorcentajeReduccionAnual convertir(Integer valorEnum) {

		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 0:
				return Cero;
			case 1:
				return Uno;
			case 2:
				return Dos;
			case 3:
				return Tres;
			case 4:
				return Cuatro;
			case 5:
				return Cinco;
			case 6:
				return Seis;
			case 7:
				return Siete;
			case 8:
				return Ocho;
			case 9:
				return Nueve;
			case 10:
				return Diez;
			case 11:
				return Once;
			case 12:
				return Doce;
			default:
				return null;
		}
	}

	// valueOf
	public static PorcentajeReduccionAnual convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if ("0".equals(valorEnum))
			return Cero;
		else if ("1".equals(valorEnum))
			return Uno;
		else if ("2".equals(valorEnum))
			return Dos;
		else if ("3".equals(valorEnum))
			return Tres;
		else if ("4".equals(valorEnum))
			return Cuatro;
		else if ("5".equals(valorEnum))
			return Cinco;
		else if ("6".equals(valorEnum))
			return Seis;
		else if ("7".equals(valorEnum))
			return Siete;
		else if ("8".equals(valorEnum))
			return Ocho;
		else if ("9".equals(valorEnum))
			return Nueve;
		else if ("10".equals(valorEnum))
			return Diez;
		else if ("11".equals(valorEnum))
			return Once;
		else if ("12".equals(valorEnum))
			return Doce;
		else
			return null;
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 0) {
			return "100";
		}
		if (valorEnum == 1) {
			return "84";
		}
		if (valorEnum == 2) {
			return "67";
		}
		if (valorEnum == 3) {
			return "56";
		}
		if (valorEnum == 4) {
			return "47";
		}
		if (valorEnum == 5) {
			return "39";
		}
		if (valorEnum == 6) {
			return "34";
		}
		if (valorEnum == 7) {
			return "28";
		}
		if (valorEnum == 8) {
			return "24";
		}
		if (valorEnum == 9) {
			return "19";
		}
		if (valorEnum == 10) {
			return "17";
		}
		if (valorEnum == 11) {
			return "13";
		}
		if (valorEnum == 12) {
			return "10";
		}

		return null;
	}
}