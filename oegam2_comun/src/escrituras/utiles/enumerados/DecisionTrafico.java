package escrituras.utiles.enumerados;

public enum DecisionTrafico {
	Si("SI", "Sí") {
		public String toString() {
			return "SI";
		}
	},
	No("NO", "No") {
		public String toString() {
			return "NO";
		}
	};

	private DecisionTrafico(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	// valueOf
	public static DecisionTrafico convertir(String valorEnum) {
		if (valorEnum.equals("SI"))
			return Si;
		else if (valorEnum.equals("NO"))
			return No;
		else
			return null;

	}
}