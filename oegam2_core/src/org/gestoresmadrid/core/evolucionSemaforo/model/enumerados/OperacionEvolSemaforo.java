package org.gestoresmadrid.core.evolucionSemaforo.model.enumerados;

public enum OperacionEvolSemaforo {

	Alta("ALT", "ALTA SEMAFORO") {
		public String toString() {
			return "ALR";
		}
	},
	Cambio_Estado("CES", "CAMBIO DE ESTADO") {
		public String toString() {
			return "CES";
		}
	},
	Inhabilitar("INH", "INHABILITAR") {
		public String toString() {
			return "INH";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private OperacionEvolSemaforo(String valorEnum, String nombreEnum) {
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
		for (OperacionEvolSemaforo element : OperacionEvolSemaforo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
