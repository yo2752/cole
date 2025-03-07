package escrituras.utiles.enumerados;

public enum TipoUsoRustico {

	Ganaderia("GANADERIA", "Ganadería") {
		public String toString() {
			return "GANADERIA";
		}
	},
	Otros("OTROS", "Otros") {
		public String toString() {
			return "OTROS";
		}
	},
	Cultivo("CULTIVO", "Cultivo") {
		public String toString() {
			return "CULTIVO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoUsoRustico(String valorEnum, String nombreEnum) {
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
	public static TipoUsoRustico convertir(String valorEnum) {
		if ("GANADERIA".equals(valorEnum))
			return Ganaderia;

		if ("OTROS".equals(valorEnum))
			return Otros;

		if ("CULTIVO".equals(valorEnum))
			return Cultivo;

		return null;
	}

}