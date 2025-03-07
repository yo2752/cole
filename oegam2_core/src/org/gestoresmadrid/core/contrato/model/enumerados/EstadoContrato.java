package org.gestoresmadrid.core.contrato.model.enumerados;


public enum EstadoContrato {

	Habilitado("1", "Habilitado") {
		public String toString() {
			return "1";
		}
	},
	Deshabilitado("2", "Deshabilitado") {
		public String toString() {
			return "2";
		}
	},
	Eliminado("3", "Eliminado") {
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoContrato(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoContrato convertir(String valorEnum) {
		for (EstadoContrato element : EstadoContrato.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoContrato element : EstadoContrato.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
