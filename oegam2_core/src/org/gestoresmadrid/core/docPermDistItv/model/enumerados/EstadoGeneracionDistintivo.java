package org.gestoresmadrid.core.docPermDistItv.model.enumerados;

import java.math.BigDecimal;

public enum EstadoGeneracionDistintivo {
	Iniciado("0", "Iniciado") {
		public String toString() {
			return "0";
		}
	},
	Pendiente_Respuesta("1", "Pendiente_Respuesta") {
		public String toString() {
			return "1";
		}

	},
	Solicitud_Creada("2", "Solicitud_Creada") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoGeneracionDistintivo(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoGeneracionDistintivo convertir(String valorEnum) {
		for (EstadoGeneracionDistintivo element : EstadoGeneracionDistintivo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static EstadoGeneracionDistintivo convertir(BigDecimal valorEnum) {
		return valorEnum != null ? convertir(valorEnum.toString()) : null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoGeneracionDistintivo element : EstadoGeneracionDistintivo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static int convertirAValor(String nombreEnum) {
		for (EstadoGeneracionDistintivo element : EstadoGeneracionDistintivo.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				try {
					return Integer.parseInt(element.valorEnum);
				} catch (NumberFormatException e) {}
			}
		}
		return -1;
	}

	public static boolean in(BigDecimal estado, EstadoGeneracionDistintivo[] estadosPermitidos) {
		for (EstadoGeneracionDistintivo element : estadosPermitidos) {
			if (element.valorEnum.equals(estado.toString())) {
				return true;
			}
		}
		return false;
	}
}