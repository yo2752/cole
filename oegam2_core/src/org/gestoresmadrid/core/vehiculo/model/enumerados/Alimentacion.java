package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum Alimentacion {

	MONOFUEL("M", "Monofuel") {
		public String toString() {
			return "M";
		}
	},
	BIFUEL("B", "Bifuel") {
		public String toString() {
			return "B";
		}
	},
	FLEXIFUEL("F", "Flexifuel") {
		public String toString() {
			return "F";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Alimentacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static Alimentacion convertirAlimentacion(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (Alimentacion alimentacion : Alimentacion.values()) {
				if (alimentacion.getValorEnum().equals(valorEnum)) {
					return alimentacion;
				}
			}
			return null;
		}
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (Alimentacion alimentacion : Alimentacion.values()) {
				if (alimentacion.getValorEnum().equals(valorEnum)) {
					return alimentacion.getNombreEnum();
				}
			}
			return null;
		}
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}
