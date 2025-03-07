package org.gestoresmadrid.core.model.enumerados;

public enum EstadoCombosConsTram {

	SI("SI", "Presentado") {
		public String toString() {
			return "SI";
		}
	},
	NO("NO", "No Presentado") {
		public String toString() {
			return "NO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoCombosConsTram(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoCombosConsTram element : EstadoCombosConsTram.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
