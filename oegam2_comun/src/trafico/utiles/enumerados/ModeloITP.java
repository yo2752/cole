package trafico.utiles.enumerados;

public enum ModeloITP {

	SeisVeinte("620", "620") {
		public String toString() {
			return "620";
		}
	},
	SeisVeintiuno("621", "621") {
		public String toString() {
			return "621";
		}
	},
	Seiscientos("600", "600") {
		public String toString() {
			return "600";
		}

	},
	Seiscientosuno("601", "601") {
		public String toString() {
			return "601";
		}

	},
	SeisVeintitres("623", "623") {
		public String toString() {
			return "623";
		}

	},
	SeiscientosVeintisiete("627", "627") {
		public String toString() {
			return "627";
		}

	};

	private String valorEnum;
	private String nombreEnum;

	private ModeloITP(String valorEnum, String nombreEnum) {
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