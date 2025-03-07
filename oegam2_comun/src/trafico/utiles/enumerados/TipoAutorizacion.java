package trafico.utiles.enumerados;

public enum TipoAutorizacion {

	FICHA_A("FICHA_A", "FICHA_A") {
		public String toString() {
			return "FICHA_A";
		}
	},
	EXENTO_CTR("EXENTO_CTR", "EXENTO_CTR") {
		public String toString() {
			return "EXENTO_CTR";
		}
	},
	IVTM("IVTM", "IVTM") {
		public String toString() {
			return "IVTM";
		}
	},
	HERENCIA("HERENCIA", "HERENCIA") {
		public String toString() {
			return "HERENCIA";
		}
	},
	DONACION("DONACION", "DONACION") {
		public String toString() {
			return "DONACION";
		}
	},
	SUBASTA("SUBASTA", "SUBASTA") {
		public String toString() {
			return "SUBASTA";
		}
	},
	JUDICIAL("JUDICIAL", "JUDICIAL") {
		public String toString() {
			return "JUDICIAL";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoAutorizacion(String valorEnum, String nombreEnum) {
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