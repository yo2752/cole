package org.gestoresmadrid.core.model.enumerados;

import java.math.BigDecimal;

public enum EstadoTramiteTrafico {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Duplicado("2", "Duplicado") {
		public String toString() {
			return "2";
		}
	},
	Anulado("3", "Anulado") {
		public String toString() {
			return "3";
		}
	},
	Pendiente_Check_Ctit("4", "Pendiente CheckCTIT") {
		public String toString() {
			return "4";
		}
	},
	No_Tramitable("5", "No Tramitable") {
		public String toString() {
			return "5";
		}
	},
	Tramitable_Incidencias("6", "Tramitable Incidencias") {
		public String toString() {
			return "6";
		}
	},
	Tramitable("7", "Tramitable") {
		public String toString() {
			return "7";
		}
	},
	Validado_Telematicamente("8", "Validado Telemáticamente") {
		public String toString() {
			return "8";
		}
	},
	Validado_PDF("9", "Validado PDF") {
		public String toString() {
			return "9";
		}
	},
	Pendiente_Envio("10", "Pendiente de respuesta de la DGT") {
		public String toString() {
			return "10";
		}
	},
	Finalizado_Con_Error("11", "Finalizado con Error") {
		public String toString() {
			return "11";
		}
	},
	Finalizado_Telematicamente("12", "Finalizado Telemáticamente") {
		public String toString() {
			return "12";
		}
	},
	Finalizado_PDF("13", "Finalizado PDF") {
		public String toString() {
			return "13";
		}
	},
	Finalizado_Telematicamente_Impreso("14", "Finalizado Telemáticamente Impreso") {
		public String toString() {
			return "14";
		}
	},
	Tramitable_Jefatura("15", "Tramitable Jefatura") {
		public String toString() {
			return "15";
		}
	},
	Informe_Telematico("16", "Informe Telemático") {
		public String toString() {
			return "16";
		}
	},
	Pendiente_Envio_Excel("17", "Pendiente Envío Excel") {
		public String toString() {
			return "17";
		}
	},
	Pendiente_Respuesta_Jefatura("18", "Pendiente Respuesta Jefatura") {
		public String toString() {
			return "18";
		}
	},
	Finalizado_Excel("19", "Finalizado Excel") {
		public String toString() {
			return "19";
		}
	},
	Finalizado_Excel_Incidencia("20", "Finalizado Excel con Incidencia") {
		public String toString() {
			return "20";
		}
	},
	Finalizado_Excel_Impreso("21", "Finalizado Excel Impreso") {
		public String toString() {
			return "21";
		}
	},
	Finalizado_Parcialmente("22", "Finalizado Parcialmente") {
		public String toString() {
			return "22";
		}
	},
	Pendiente_Respuesta_AEAT("23", "Pendiente de respuesta de la AEAT") {
		public String toString() {
			return "23";
		}
	},
	Pendiente_Respuesta_IVTM("24", "Pendiente de respuesta del IVTM") {
		public String toString() {
			return "24";
		}
	},
	Recibida_Referencia_Atem("25", "Recibida referencia Atem") {
		public String toString() {
			return "25";
		}
	},
	LiberadoEEFF("26", "Liberado EEFF") {
		public String toString() {
			return "26";
		}
	},
	Pendiente_Liberar("27", "Pendiente de respuesta de Liberacion") {
		public String toString() {
			return "27";
		}
	},
	Modificado_Peticion("28", "Modificado a petición del colegiado") {
		public String toString() {
			return "28";
		}
	},
	Pendiente_Consulta_BTV("29", "Pendiente ConsultaBTV") {
		public String toString() {
			return "29";
		}
	},
	Pendiente_Consulta_EITV("30", "Pte Respuesta Consulta Tarjeta Eitv") {
		public String toString() {
			return "30";
		}
	},
	Error_Consulta_EITV("31", "Error Consulta Eitv") {
		public String toString() {
			return "31";
		}
	},
	Consultado_EITV("32", "Consultado Eitv") {
		public String toString() {
			return "32";
		}
	},
	Pendiente_Respuesta_APP("33", "Pendiente Respuesta Aplicación") {
		public String toString() {
			return "33";
		}
	},
	Finalizado_Incidencia("34", "Finalizado Incidencia") {
		public String toString() {
			return "34";
		}
	},
	Finalizado_Telematicamente_Revisado("35", "Finalizado Telemáticamente Revisado") {
		public String toString() {
			return "35";
		}
	},
	Pendiente_Check_Duplicado("36", "Pendiente CheckDuplicado") {
		public String toString() {
			return "36";
		}
	},
	Recibida_Respuesta_APP("55", "Recibida Respuesta Aplicación") {
		public String toString() {
			return "55";
		}
	},
	Telematicos("98", "Telemáticos") {
		public String toString() {
			return "98";
		}
	},
	Pendiente_Respuesta_PD("100", "Pendiente Respuesta Permiso / Distintivo") {
		public String toString() {
			return "100";
		}
	},
	Recibida_Respuesta_PD("101", "Recibida Respuesta Permiso / Distintiv") {
		public String toString() {
			return "101";
		}
	},
	Recibida_Respuesta_Error_PD("102", "Recibida Respuesta Error Permiso / Distintiv") {
		public String toString() {
			return "102";
		}
	},
	TelematicosYPdf("99", "Telemáticos Y PDF") {
		public String toString() {
			return "99";
		}
	},
	TramitadoNRE06("103", "Tramitado NRE06") {
		public String toString() {
			return "103";
		}
	},
	PendienteTramitarNRE06("104", "Pendiente Tramitar NRE06") {
		public String toString() {
			return "104";
		}
	},
	Pendiente_Autorizacion_Colegio("105", "Pendiente Autorización Colegio") {
		public String toString() {
			return "105";
		}
	},
	Autorizado("106", "Trámite autorizado por el colegio") {
		public String toString() {
			return "106";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoTramiteTrafico(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoTramiteTrafico convertir(String valorEnum) {
		for (EstadoTramiteTrafico element : EstadoTramiteTrafico.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static EstadoTramiteTrafico convertir(BigDecimal valorEnum) {
		return valorEnum != null ? convertir(valorEnum.toString()) : null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoTramiteTrafico element : EstadoTramiteTrafico.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static int convertirAValor(String nombreEnum) {
		for (EstadoTramiteTrafico element : EstadoTramiteTrafico.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				try {
					return Integer.parseInt(element.valorEnum);
				} catch (NumberFormatException e) {}
			}
		}
		return -1;
	}

	public static boolean in(BigDecimal estado, EstadoTramiteTrafico[] estadosPermitidos) {
		for (EstadoTramiteTrafico element : estadosPermitidos) {
			if (element.valorEnum.equals(estado.toString())) {
				return true;
			}
		}
		return false;
	}

}