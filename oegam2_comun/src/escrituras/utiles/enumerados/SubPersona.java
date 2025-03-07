package escrituras.utiles.enumerados;

public enum SubPersona {

	Publica("PUBLICA", "PUBLICA") {
		public String toString() {
			return "PUBLICA";
		}
	},
	Privada("PRIVADA", "PRIVADA") {
		public String toString() {
			return "PRIVADA";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private SubPersona(String valorEnum, String nombreEnum) {
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
	public static SubPersona convertir(String valorEnum) {
		if ("PUBLICA".equals(valorEnum))
			return Publica;

		if ("PRIVADA".equals(valorEnum))
			return Privada;

		return null;
	}

}