package escrituras.utiles.enumerados;

public enum Modelos {

	Modelo600(600, "600", "A1") {
		public String toString() {
			return "600";
		}
	},
	Modelo601(601, "601", "A1") {
		public String toString() {
			return "601";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;
	private String version;

	private Modelos(Integer valorEnum, String nombreEnum, String version) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.version = version;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getVersion() {
		return version;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static Modelos convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 600:
				return Modelo600;
			case 601:
				return Modelo601;
			default:
				return null;
		}
	}

}