package trafico.utiles.enumerados;

public enum OegamStatusEnum {

	OEGAM("OEGAM", "OEGAM") {
		public String toString() {
			return "OEGAM";
		}
	},
	SEA("SEA", "SEA") {
		public String toString() {
			return "SEA";
		}
	},
	DGT("DGT", "DGT") {
		public String toString() {
			return "DGT";
		}
	},
	AEAT("AEAT", "AEAT") {
		public String toString() {
			return "AEAT";
		}
	},
	FIRMA("FIRMA", "FIRMA") {
		public String toString() {
			return "FIRMA";
		}
	},
	MATRICULACION("MATRICULACION", "MATRICULACION") {
		public String toString() {
			return "MATRICULACION";
		}
	},
	TRANSFERENCIAS("TRANSFERENCIAS", "TRANSFERENCIAS") {
		public String toString() {
			return "TRANSFERENCIAS";
		}
	},
	INFORMES("INFORMES", "INFORMES") {
		public String toString() {
			return "INFORMES";
		}
	},
	BAJAS("BAJAS", "BAJAS") {
		public String toString() {
			return "BAJAS";
		}
	},
	GEST("GEST", "GEST") {
		public String toString() {
			return "GEST";
		}
	},
	TASAS("TASAS", "TASAS") {
		public String toString() {
			return "TASAS";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private OegamStatusEnum(String valorEnum, String nombreEnum) {
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