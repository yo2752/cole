package trafico.utiles.enumerados;

public enum LugarPrimeraMatriculacion {

	UE("1", "UE") {
		public String toString() {
			return "1";
		}
	},
	CANARIAS("2", "CANARIAS") {
		public String toString() {
			return "2";
		}
	},
	MELILLA("3", "MELILLA") {
		public String toString() {
			return "3";
		}
	},

	NOCOMUNITARIO("4", "NO COMUNITARIO") {
		public String toString() {
			return "4";
		}
	},

	OTROS("5", "OTROS") {
		public String toString() {
			return "5";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private LugarPrimeraMatriculacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static LugarPrimeraMatriculacion convertir(String valorEnum) {

		if (valorEnum == null)
			return null;
		if ("1".equals(valorEnum))
			return UE;
		if ("2".equals(valorEnum))
			return CANARIAS;
		if ("3".equals(valorEnum))
			return MELILLA;
		if ("4".equals(valorEnum))
			return NOCOMUNITARIO;
		if ("5".equals(valorEnum))
			return OTROS;
		else {
			return null;
		}
	}
}