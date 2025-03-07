package org.gestoresmadrid.core.model.enumerados;

public enum Estado {

	Habilitado(1, "Habilitado") {
		public String toString() {
			return "1";
		}
	},
	Deshabilitado(2, "Deshabilitado") {
		public String toString() {
			return "2";
		}
	},
	Eliminado(3, "Eliminado") {
		public String toString() {
			return "3";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private Estado(Integer valorEnum, String nombreEnum) {
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
	public static Estado convertir(Integer valorEnum) {

		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return Habilitado;
			case 2:
				return Deshabilitado;
			case 3:
				return Eliminado;
			default:
				return null;
		}
	}

	public static Estado convertir(String valorEnum) {
		if (valorEnum == null || valorEnum.equals("")) {
			return null;
		}

		Integer valor = Integer.parseInt(valorEnum);

		switch (valor) {
			case 1:
				return Habilitado;
			case 2:
				return Deshabilitado;
			case 3:
				return Eliminado;
			default:
				return null;
		}
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 1) {
			return "Habilitado";
		}
		if (valorEnum == 2) {
			return "Deshabilitado";
		}
		if (valorEnum == 3) {
			return "Eliminado";
		}
		return null;

	}

	public static Integer convertirToValor(String valorEnum) {
		if (valorEnum == "Habilitado") {
			return 1;
		}
		if (valorEnum == "Deshabilitado") {
			return 2;
		}
		if (valorEnum == "Eliminado") {
			return 3;
		}
		return null;
	}

}