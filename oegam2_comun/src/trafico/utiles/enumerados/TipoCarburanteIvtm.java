package trafico.utiles.enumerados;

public enum TipoCarburanteIvtm {

	Gasolina("GA", "GASOLINA") {
		public String toString() {
			return "GA";
		}
	},
	Camiones("GO", "GASOIL") {
		public String toString() {
			return "GO";
		}
	},
	Autobuses("EL", "ELECTRICO") {
		public String toString() {
			return "EL";
		}
	},
	Ciclomotores("BU", "BUTANO") {
		public String toString() {
			return "BU";
		}
	},
	Motocicletas("SO", "SOLAR") {
		public String toString() {
			return "MT";
		}
	},
	Remolques("OT", "OTROS MEDIOS") {
		public String toString() {
			return "OT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoCarburanteIvtm(String valorEnum, String nombreEnum) {
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