package escrituras.utiles.enumerados;

public enum Resultado {

	resultado1(1, "OK") {
		public String toString() {
			return "1";
		}
	},
	resultado2(2, "ERROR") {
		public String toString() {
			return "2";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private Resultado(Integer valorEnum, String nombreEnum) {
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
	public static Resultado convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return resultado1;
			case 2:
				return resultado2;
			default:
				return null;
		}
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 1) {
			return "OK";
		}
		if (valorEnum == 2) {
			return "ERROR";
		}
		return null;
	}

}