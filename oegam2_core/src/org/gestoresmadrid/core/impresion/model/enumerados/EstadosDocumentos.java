package org.gestoresmadrid.core.impresion.model.enumerados;

public enum EstadosDocumentos {

	Iniciado("I", "Iniciado") {
		public String toString() {
			return "I";
		}
	},
	Firmado("F", "Firmado") {
		public String toString() {
			return "F";
		}

	},
	Enviado("E", "Enviado") {
		public String toString() {
			return "E";
		}
	},
	Subido("S", "Subido") {
		public String toString() {
			return "S";
		}
	},
	Generado("G", "Generado") {
		public String toString() {
			return "G";
		}
	},
	Eliminado("D", "Eliminado") {
		public String toString() {
			return "D";
		}
	},
	Error("R", "Error") {
		public String toString() {
			return "R";
		}
	},
	Error_Envio("V", "Error Envío") {
		public String toString() {
			return "V";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadosDocumentos(String valorEnum, String nombreEnum) {
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

	public static EstadosDocumentos convertir(String valorEnum) {
		for (EstadosDocumentos element : EstadosDocumentos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadosDocumentos element : EstadosDocumentos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
