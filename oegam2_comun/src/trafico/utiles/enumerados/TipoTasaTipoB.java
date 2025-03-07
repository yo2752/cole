package trafico.utiles.enumerados;

public enum TipoTasaTipoB {
	UnoDos("1.2", "1.2") {
		public String toString() {
			return "1.2";
		}
	},
	UnoCinco("1.5", "1.5") {
		public String toString() {
			return "1.5";
		}
	},
	CuatroUno("4.1", "4.1") {
		public String toString() {
			return "4.1";
		}
	},
	SM2("SM2", "SM2") {
		public String toString() {
			return "SM2";
		}
	},
	SM3("SM3", "SM3") {
		public String toString() {
			return "SM3";
		}
	},
	SM4("SM4", "SM4") {
		public String toString() {
			return "SM4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTasaTipoB(String valorEnum, String nombreEnum) {
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