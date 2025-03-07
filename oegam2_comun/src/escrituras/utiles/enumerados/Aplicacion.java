package escrituras.utiles.enumerados;

public enum Aplicacion {

	aplicacion1(1, "BSTI") {
		public String toString() {
			return "1";
		}
	},
	aplicacion2(2, "AVPO") {
		public String toString() {
			return "2";
		}
	},
	aplicacion3(3, "GEST") {
		public String toString() {
			return "3";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private Aplicacion(Integer valorEnum, String nombreEnum) {
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
	public static Aplicacion convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return aplicacion1;
			case 2:
				return aplicacion2;
			case 3:
				return aplicacion3;
			default:
				return null;
		}
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 1) {
			return "BSTI";
		}
		if (valorEnum == 2) {
			return "AVPO";
		}
		if (valorEnum == 3) {
			return "GEST";
		}
		return null;
	}

}