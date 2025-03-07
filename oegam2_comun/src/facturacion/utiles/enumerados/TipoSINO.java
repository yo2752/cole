package facturacion.utiles.enumerados;

public enum TipoSINO {

	NO("-1", "NO") {
		public String toString() {
			return "-1";
		}
	},
	tipo1("1", "1") {
		public String toString() {
			return "1";
		}
	},
	tipo2("2", "2") {
		public String toString() {
			return "2";
		}
	},
	tipo3("3", "3") {
		public String toString() {
			return "3";
		}
	},
	tipo4("4", "4") {
		public String toString() {
			return "4";
		}
	},
	tipo5("5", "5") {
		public String toString() {
			return "5";
		}
	},
	tipo6("6", "6") {
		public String toString() {
			return "6";
		}
	},
	tipo7("7", "7") {
		public String toString() {
			return "7";
		}
	},
	tipo8("8", "8") {
		public String toString() {
			return "8";
		}
	},
	tipo9("9", "9") {
		public String toString() {
			return "9";
		}
	},
	tipo10("10", "10") {
		public String toString() {
			return "10";
		}
	},
	tipo11("11", "11") {
		public String toString() {
			return "11";
		}
	},
	tipo12("12", "12") {
		public String toString() {
			return "12";
		}
	},
	tipo13("13", "13") {
		public String toString() {
			return "13";
		}
	},
	tipo14("14", "14") {
		public String toString() {
			return "14";
		}
	},
	tipo15("15", "15") {
		public String toString() {
			return "15";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoSINO(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}