package org.gestoresmadrid.core.model.enumerados;

public enum EstadoTasaBloqueo {

	DESBLOQUEADA("0", "Desbloqueda") {
		public String toString() {
			return "0";
		}
	},
	BLOQUEADA("1", "Bloqueada") {
		public String toString() {
			return "1";
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

	private EstadoTasaBloqueo(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static EstadoTasaBloqueo convertir(String valorEnum) {
		for (EstadoTasaBloqueo element : EstadoTasaBloqueo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoTasaBloqueo element : EstadoTasaBloqueo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
