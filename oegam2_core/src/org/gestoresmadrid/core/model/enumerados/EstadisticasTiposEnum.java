package org.gestoresmadrid.core.model.enumerados;

public enum EstadisticasTiposEnum {

	CTIT("CTIT", "CTIT") {
		public String toString() {
			return "CTIT";
		}
	},
	MATE("MATE", "MATE") {
		public String toString() {
			return "MATE";
		}
	},
	DELEGACIONES("DELEGACIONES", "DELEGACIONES") {
		public String toString() {
			return "DELEGACIONES";
		}
	};

	private EstadisticasTiposEnum(String nombreEnum, String valorEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

}