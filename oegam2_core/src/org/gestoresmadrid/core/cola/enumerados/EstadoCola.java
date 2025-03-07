package org.gestoresmadrid.core.cola.enumerados;

import java.math.BigDecimal;

public enum EstadoCola {

	HILO_CREADO("0", "Hilo creado") {
		public String toString() {
			return "0";
		}
	},
	PENDIENTES_ENVIO("1", "Pendiente de envío") {
		public String toString() {
			return "1";
		}
	},
	EJECUTANDO("2", "Ejecutando") {
		public String toString() {
			return "2";
		}
	},
	IMPRIMIENDO("5", "Imprimiendo") {
		public String toString() {
			return "5";
		}
	},
	ERROR_SERVICIO("9", "Error servicio") {
		public String toString() {
			return "9";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoCola(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoCola convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (EstadoCola estado : EstadoCola.values()) {
				if (estado.getValorEnum().equals(valorEnum)) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (EstadoCola estado : EstadoCola.values()) {
				if (estado.getValorEnum().equals(valor)) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(EstadoCola estadoC) {
		if (estadoC != null) {
			for (EstadoCola estado : EstadoCola.values()) {
				if (estado.getValorEnum() == estadoC.getValorEnum()) {
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

	public static EstadoCola[] getListaEstadoColaSinHilo() {
		EstadoCola[] estadosCola = new EstadoCola[4];
		estadosCola[0] = EstadoCola.PENDIENTES_ENVIO;
		estadosCola[1] = EstadoCola.EJECUTANDO;
		estadosCola[2] = EstadoCola.IMPRIMIENDO;
		estadosCola[3] = EstadoCola.ERROR_SERVICIO;
		return estadosCola;
	}

}