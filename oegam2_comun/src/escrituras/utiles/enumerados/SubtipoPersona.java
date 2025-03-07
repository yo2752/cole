package escrituras.utiles.enumerados;

public enum SubtipoPersona {

	Publica("2", "PUBLICA") {
		public String toString() {
			return "PUBLICA";
		}
	},
	Privada("3", "PRIVADA") {
		public String toString() {
			return "PRIVADA";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private SubtipoPersona(String valorEnum, String nombreEnum) {
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
	public static SubtipoPersona convertir(String valorEnum) {
		if ("PUBLICA".equals(valorEnum))
			return Publica;

		if ("PRIVADA".equals(valorEnum))
			return Privada;

		return null;
	}

}