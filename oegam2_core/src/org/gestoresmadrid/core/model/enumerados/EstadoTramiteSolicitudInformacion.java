package org.gestoresmadrid.core.model.enumerados;

import java.math.BigDecimal;

public enum EstadoTramiteSolicitudInformacion {

	Recibido("1", "Recibido") {
		public String toString() {
			return "1";
		}
	},
	Pendiente("0", "Pendiente") {
		public String toString() {
			return "0";
		}
	},Iniciado("5", "Iniciado") {
		public String toString() {
			return "5";
		}
	},Error("6", "No hay informe") {
		public String toString() {
			return "6";
		}
	},
	Recibido_error("3", "Recibido error") {
		public String toString() {
			return "3";
		}
	},
	Finalizado_PDF("13", "Finalizado PDF") {
		public String toString() {
			return "13";
		}
	},
	Finalizado_Parcialmente("22", "Finalizado Parcialmente") {
		public String toString() {
			return "22";
		}
	},
	Pendiente_Respuesta_APP("33", "Pendiente Respuesta Aplicación") {
		public String toString() {
			return "33";
		}
	},
	Pendiente_Importacion("4", "Pendiente Importación") {
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoTramiteSolicitudInformacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoTramiteSolicitudInformacion convertir(String valorEnum) {
		for (EstadoTramiteSolicitudInformacion element : EstadoTramiteSolicitudInformacion.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static EstadoTramiteSolicitudInformacion convertir(BigDecimal valorEnum) {
		return valorEnum != null ? convertir(valorEnum.toString()) : null;
	}

	public static String convertirTexto(String valorEnum) {
		EstadoTramiteSolicitudInformacion estadoTramiteSolicitudInformacion = convertir(valorEnum);
		return estadoTramiteSolicitudInformacion != null ? estadoTramiteSolicitudInformacion.getNombreEnum() : null;
	}

}
