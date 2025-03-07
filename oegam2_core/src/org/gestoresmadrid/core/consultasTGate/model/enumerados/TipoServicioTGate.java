package org.gestoresmadrid.core.consultasTGate.model.enumerados;

public enum TipoServicioTGate {

	BSTI("BSTI", "Consulta de matr�cula") {
		public String toString() {
			return "BSTI";
		}
	},
	AVPO("AVPO", "Consulta de datos del veh�culo") {
		public String toString() {
			return "AVPO";
		}
	},
	GEST("GEST", "Consulta de cargas del veh�culo") {
		public String toString() {
			return "GEST";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoServicioTGate(String valorEnum, String nombreEnum) {
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
		for (TipoServicioTGate element : TipoServicioTGate.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
