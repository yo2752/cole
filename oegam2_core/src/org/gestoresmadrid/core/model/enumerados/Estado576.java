package org.gestoresmadrid.core.model.enumerados;

public enum Estado576 {

	SIN_PETICION("0", "Sin Petición") {
		public String toString() {
			return "0";
		}
	},
	EN_EJECUCION("1", "En Ejecución") {
		public String toString() {
			return "1";
		}
	},
	PRESENTADO("2", "Presentado") {
		public String toString() {
			return "2";
		}
	},
	ERROR("3", "Erróneo") {
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private Estado576(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static Estado576 convertir(String valorEnum) {
		for (Estado576 element : Estado576.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (Estado576 element : Estado576.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
