package trafico.datosSensibles.utiles.enumerados;

public enum TiposDatosSensibles {

	Nif("Nif", "Nif") {
		public String toString() {
			return "Nif";
		}
	},
	Bastidor("Bastidor", "Bastidor") {
		public String toString() {
			return "Bastidor";
		}
	},

	Matricula("Matricula", "Matricula") {
		public String toString() {
			return "Matricula";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TiposDatosSensibles(String valorEnum, String nombreEnum) {
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