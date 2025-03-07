package org.gestoresmadrid.core.trafico.solInfo.model.enumerados;

import java.math.BigDecimal;

public enum EstadoTramiteSolicitudInformacion {

	Recibido("4", "Recibido") {
		public String toString() {
			return "4";
		}
	},
	Pendiente("0", "Pendiente") {
		public String toString() {
			return "0";
		}
	},
	Recibido_error("3", "Recibido error") {
		public String toString() {
			return "3";
		}
	},
	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Finalizado_Parcialmente("22", "Finalizado Parcialmente") {
		public String toString() {
			return "22";
		}
	},
	Finalizado_Con_Error("11", "Finalizado con Error") {
		public String toString() {
			return "11";
		}
	},
	Finalizado_PDF("13", "Finalizado PDF") {
		public String toString() {
			return "13";
		}
	},
	Pendiente_Respuesta_APP("33", "Pendiente Respuesta Aplicación") {
		public String toString() {
			return "33";
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

	public static String convertirEstadoBigDecimal(BigDecimal estado){
		if(estado != null){
			return convertirTexto(estado.toString());
		}
		return "";
	}

	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoTramiteSolicitudInformacion estado : EstadoTramiteSolicitudInformacion.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
}