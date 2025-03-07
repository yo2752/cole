package trafico.utiles.enumerados;

public enum ModeloIEDTM {

	CincoSetentaYSeis("576", "576") {
		public String toString() {
			return "576";
		}
	},
	CeroCinco("05", "05") {
		public String toString() {
			return "05";
		}
	},
	CeroSeis("06", "06") {
		public String toString() {
			return "06";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ModeloIEDTM(String valorEnum, String nombreEnum) {
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