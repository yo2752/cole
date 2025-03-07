package trafico.utiles.enumerados;

public enum ResultadoITV {

	FAVORABLE("FAVORABLE", "FAVORABLE") {
		public String toString() {
			return "FAVORABLE";
		}
	},
	DESFAVORABLE("DESFAVORABLE", "DESFAVORABLE") {
		public String toString() {
			return "DESFAVORABLE";
		}
	},
	NEGATIVA("NEGATIVA", "NEGATIVA") {
		public String toString() {
			return "NEGATIVA";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ResultadoITV(String valorEnum, String nombreEnum) {
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