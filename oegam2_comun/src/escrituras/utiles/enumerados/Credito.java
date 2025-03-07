package escrituras.utiles.enumerados;

public enum Credito {

	oegamReg(1, "R1") {
		public String toString() {
			return "1";
		}
	},
	oegamEsc(2, "E1") {
		public String toString() {
			return "2";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private Credito(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static Credito convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return oegamReg;
			case 2:
				return oegamEsc;
			default:
				return null;
		}
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 1) {
			return "R1";
		}
		if (valorEnum == 2) {
			return "E1";
		}
		return null;
	}
}