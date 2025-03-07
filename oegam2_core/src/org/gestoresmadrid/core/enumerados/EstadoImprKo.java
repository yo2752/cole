package org.gestoresmadrid.core.enumerados;

import java.math.BigDecimal;

public enum EstadoImprKo {
	Creado("0", "Creado") {
		public String toString() {
			return "0";
		}
	},
	Creado_Gestoria("1", "Creado Gestoria") {
		public String toString() {
			return "1";
		}
	},
	Anotado_Gestoria("2", "Anotado Gestoria") {
		public String toString() {
			return "2";
		}
	},
	Pdt_Respuesta_DGT("3", "Pdt. Respuesta DGT") {
		public String toString() {
			return "3";
		}
	},
	Documentacion_Recibida("4", "Documentación Recibida") {
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoImprKo(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static EstadoImprKo convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (EstadoImprKo estado : EstadoImprKo.values()) {
				if (estado.getValorEnum().equals(valorEnum)) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null && !valor.isEmpty()) {
			for (EstadoImprKo estado : EstadoImprKo.values()) {
				if (estado.getValorEnum().equals(valor)) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombre) {
		if (nombre != null) {
			for (EstadoImprKo estado : EstadoImprKo.values()) {
				if (estado.getNombreEnum().equals(nombre)) {
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(EstadoImprKo estadoKo) {
		if (estadoKo != null) {
			for (EstadoImprKo estado : EstadoImprKo.values()) {
				if (estado.getValorEnum() == estadoKo.getValorEnum()) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirEstadoBigDecimal(BigDecimal estado) {
		if (estado != null) {
			return convertirTexto(estado.toString());
		}
		return "";
	}
}
