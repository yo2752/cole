package org.gestoresmadrid.core.enumerados;

public enum AccionesGestionCreditos {

	CARGA_MANUAL("CARGA_MANUAL", "Carga Manual") {
		public String toString() {
			return "CARGA_MANUAL";
		}
	},
	DEVOLVER_MANUAL("DEVOLVER_MANUAL", "Devolver Manual") {
		public String toString() {
			return "DEVOLVER_MANUAL";
		}
	},
	CARGA_AUTO("CARGA_AUTO", "Carga Automática") {
		public String toString() {
			return "CARGA_AUTO";
		}
	},
	DEVOLVER_AUTO("DEVOLVER_AUTO", "Devolver Automática") {
		public String toString() {
			return "DEVOLVER_AUTO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private AccionesGestionCreditos(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static AccionesGestionCreditos convertir(String valorEnum) {
		for (AccionesGestionCreditos element : AccionesGestionCreditos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (AccionesGestionCreditos element : AccionesGestionCreditos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
