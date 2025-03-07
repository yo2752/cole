package org.gestoresmadrid.core.importacionFichero.model.enumerados;

public enum EstadosImportacionEstadistica {
	Ejecutandose("0", "Ejecutándose") {
		public String toString() {
			return "0";
		}
	},
	Finalizado("1", "Finalizado") {
		public String toString() {
			return "1";
		}
	},
	Parado("2", "Parado") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadosImportacionEstadistica(String valorEnum, String nombreEnum) {
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

	public static EstadosImportacionEstadistica convertir(String valorEnum) {
		for (EstadosImportacionEstadistica element : EstadosImportacionEstadistica.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadosImportacionEstadistica element : EstadosImportacionEstadistica.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
