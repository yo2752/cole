package org.gestoresmadrid.core.contrato.model.enumerados;


public enum EstadoMobileGest {

	SI("1", "SI") {
		public String toString() {
			return "1";
		}
	},
	NO("2", "NO") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoMobileGest(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoMobileGest convertir(String valorEnum) {
		for (EstadoMobileGest element : EstadoMobileGest.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoMobileGest element : EstadoMobileGest.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
