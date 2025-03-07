package org.gestoresmadrid.core.trafico.interga.model.enumerados;

public enum EstadoDeclaracion {

	No_Enviado("1", "No Enviado") {
		public String toString() {
			return "1";
		}
	},
	No_Enviado_Colegiado("2", "No Enviado Declaración Colegiado") {
		public String toString() {
			return "2";
		}
	},
	No_Enviado_Colegio("3", "No Enviado Declaración Colegio") {
		public String toString() {
			return "3";
		}
	},
	Enviados("4", "Enviadas Declaraciones") {
		public String toString() {
			return "4";
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

	private EstadoDeclaracion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static EstadoDeclaracion convertir(String valorEnum) {
		for (EstadoDeclaracion element : EstadoDeclaracion.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoDeclaracion element : EstadoDeclaracion.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
