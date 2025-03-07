package org.gestoresmadrid.core.licencias.model.enumerados;

import java.math.BigDecimal;

public enum EstadoSolicitudLicenciasCam {

	Solicitud_Inexistente("1", "Solicitud Inexistente") {
		public String toString() {
			return "1";
		}
	},
	Solicitud_Con_Errores("2", "Solicitud con Errores") {
		public String toString() {
			return "2";
		}
	},
	Validando_Solicitud("3", "Validando Solicitud") {
		public String toString() {
			return "3";
		}
	},
	Validada_Pendiente_Registro("4", "Validada Pendiente de Registro") {
		public String toString() {
			return "4";
		}
	},
	Registrada("5", "Registrada") {
		public String toString() {
			return "5";
		}
	},
	Error_Registrar("6", "Error al Registrar") {
		public String toString() {
			return "6";
		}
	},
	Iniciado_Expediente("7", "Iniciado Expediente") {
		public String toString() {
			return "7";
		}
	},
	Registro_Correcto_Error_Expediente("8", "Registro Correcto con error creación expediente") {
		public String toString() {
			return "8";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoSolicitudLicenciasCam(String valorEnum, String nombreEnum) {
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

	public static EstadoSolicitudLicenciasCam convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (EstadoSolicitudLicenciasCam estado : EstadoSolicitudLicenciasCam.values()) {
				if (estado.getValorEnum().equals(valorEnum)) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (EstadoSolicitudLicenciasCam estado : EstadoSolicitudLicenciasCam.values()) {
				if (estado.getValorEnum().equals(valor)) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(EstadoSolicitudLicenciasCam estadoM) {
		if (estadoM != null) {
			for (EstadoSolicitudLicenciasCam estado : EstadoSolicitudLicenciasCam.values()) {
				if (estado.getValorEnum() == estadoM.getValorEnum()) {
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
