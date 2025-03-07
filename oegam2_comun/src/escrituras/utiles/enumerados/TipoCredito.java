package escrituras.utiles.enumerados;

public enum TipoCredito {

	tipo1(1, "CR1") {
		public String toString() {
			return "1";
		}
	},
	tipo2(2, "CE1") {
		public String toString() {
			return "2";
		}
	},
	tipo3(3, "CT1") {
		public String toString() {
			return "3";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private TipoCredito(Integer valorEnum, String nombreEnum) {
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
	public static TipoCredito convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return tipo1;
			case 2:
				return tipo2;
			case 3:
				return tipo3;
			default:
				return null;
		}
	}

	public static String convertirTexto(String valorEnum) {
		if ("1".equals(valorEnum)) {
			return "CR1";
		}
		if ("2".equals(valorEnum)) {
			return "CE1";
		}
		if ("3".equals(valorEnum)) {
			return "CT1";
		}
		return null;
	}

	public static Integer convertirToInteger(String valorEnum) {
		if (valorEnum.equals("CR1")) {
			return 1;
		}
		if (valorEnum.equals("CE1")) {
			return 2;
		}
		if (valorEnum.equals("CT1")) {
			return 3;
		}
		return null;
	}

}