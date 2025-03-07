package trafico.utiles.enumerados;

public enum Epigrafe {

	Epigrafe1(1, "EPIGRAFE 1º") {
		public String toString() {
			return "1";
		}
	},
	Epigrafe2(2, "EPIGRAFE 2º") {
		public String toString() {
			return "2";
		}
	},
	Epigrafe3(3, "EPIGRAFE 3º") {
		public String toString() {
			return "3";
		}
	},

	Epigrafe4(4, "EPIGRAFE 4º") {
		public String toString() {
			return "4";
		}
	},

	Epigrafe5(5, "EPIGRAFE 5º") {
		public String toString() {
			return "5";
		}
	},

	Epigrafe6(6, "EPIGRAFE 6º") {
		public String toString() {
			return "6";
		}
	},

	Epigrafe7(7, "EPIGRAFE 7º") {
		public String toString() {
			return "7";
		}
	},

	Epigrafe8(8, "EPIGRAFE 8º") {
		public String toString() {
			return "8";
		}
	},

	Epigrafe9(9, "EPIGRAFE 9º") {
		public String toString() {
			return "9";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private Epigrafe(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 1) {
			return "GASOLINA";
		} else if (valorEnum == 2) {
			return "GASOIL";
		} else if (valorEnum == 3) {
			return "ELECTRICO";
		} else if (valorEnum == 4) {
			return "BUTANO";
		} else if (valorEnum == 5) {
			return "SOLAR";
		} else if (valorEnum == 6) {
			return "SIN TRACCION";
		}
		return null;
	}

	public static Epigrafe convertir(Integer valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (Epigrafe epigrafe : Epigrafe.values()) {
				if (epigrafe.getValorEnum().equals(valorEnum)) {
					return epigrafe;
				}
			}
			return null;
		}
	}
}