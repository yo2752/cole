package escrituras.utiles.enumerados;

public enum SujetoExento {

	Sujeto("S", "Sujeto") {
		public String toString() {
			return "S";
		}
	},
	Exento("E", "Exento") {
		public String toString() {
			return "E";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private SujetoExento(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static SujetoExento convertir(String valorEnum) {
		if ("S".equals(valorEnum))
			return Sujeto;

		if ("E".equals(valorEnum))
			return Exento;

		return null;
	}

}