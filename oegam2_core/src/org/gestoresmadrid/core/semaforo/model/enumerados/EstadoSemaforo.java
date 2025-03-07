package org.gestoresmadrid.core.semaforo.model.enumerados;

public enum EstadoSemaforo {

	Activo("1", "Activo") {
		public String toString() {
			return "1";
		}
	},
	Intermitente("2", "Intermitente") {
		public String toString() {
			return "2";
		}
	},
	Parado("3", "Parado") {
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoSemaforo(String valorEnum, String nombreEnum) {
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
		for (EstadoSemaforo element : EstadoSemaforo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		if ("0".equals(valorEnum)) {
			return "Deshabilitado";
		}
		return "";
	}
}
