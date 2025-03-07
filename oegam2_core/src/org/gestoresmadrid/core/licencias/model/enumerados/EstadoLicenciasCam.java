package org.gestoresmadrid.core.licencias.model.enumerados;

import java.math.BigDecimal;

public enum EstadoLicenciasCam {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Validado("2", "Validado") {
		public String toString() {
			return "2";
		}
	},
	Pendiente_Autoliquidacion("3", "Pendiente Autoliquidación") {
		public String toString() {
			return "3";
		}
	},
	Autoliquidado("4", "Autoliquidado") {
		public String toString() {
			return "4";
		}
	},
	Pendiente_Comprobar("5", "Pdt. Comprobar") {
		public String toString() {
			return "5";
		}
	},
	No_Tramitable("6", "No Tramitable") {
		public String toString() {
			return "6";
		}
	},
	Tramitable("7", "Tramitable") {
		public String toString() {
			return "7";
		}
	},
	Pendiente_Respuesta_Cam("8", "Pdt. Respuesta CAM") {
		public String toString() {
			return "8";
		}
	},
	Entrada_Cam("9", "Entrada CAM") {
		public String toString() {
			return "9";
		}
	},
	Registrada("10", "Registrada") {
		public String toString() {
			return "10";
		}
	},
	Pendiente_Presentacion("11", "Pdt. Presentación") {
		public String toString() {
			return "11";
		}
	},
	Presentada("12", "Presentada") {
		public String toString() {
			return "12";
		}
	},
	No_Presentada("13", "No Presentada") {
		public String toString() {
			return "13";
		}
	},
	Finalizado_Con_Error("14", "Finalizado con Error") {
		public String toString() {
			return "14";
		}
	},
	Anulado("15", "Anulado") {
		public String toString() {
			return "15";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoLicenciasCam(String valorEnum, String nombreEnum) {
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

	public static EstadoLicenciasCam convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (EstadoLicenciasCam estado : EstadoLicenciasCam.values()) {
				if (estado.getValorEnum().equals(valorEnum)) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (EstadoLicenciasCam estado : EstadoLicenciasCam.values()) {
				if (estado.getValorEnum().equals(valor)) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(EstadoLicenciasCam estadoM) {
		if (estadoM != null) {
			for (EstadoLicenciasCam estado : EstadoLicenciasCam.values()) {
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
