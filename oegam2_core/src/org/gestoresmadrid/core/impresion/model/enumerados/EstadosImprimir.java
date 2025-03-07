package org.gestoresmadrid.core.impresion.model.enumerados;

public enum EstadosImprimir {
	Creacion("0", "Pdt. Impresión") {
		public String toString() {
			return "0";
		}
	},
	Generado("1", "Generado") {
		public String toString() {
			return "1";
		}

	},
	Descargado("2", "Descargado") {
		public String toString() {
			return "2";
		}
	},
	Eliminado("3", "Eliminado") {
		public String toString() {
			return "3";
		}
	},
	Finalizado_Error("4", "Finalizado Error") {
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadosImprimir(String valorEnum, String nombreEnum) {
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

	public static EstadosImprimir convertir(String valorEnum) {
		for (EstadosImprimir element : EstadosImprimir.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadosImprimir element : EstadosImprimir.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
