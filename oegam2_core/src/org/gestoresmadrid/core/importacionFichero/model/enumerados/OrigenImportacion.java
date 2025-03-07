package org.gestoresmadrid.core.importacionFichero.model.enumerados;

public enum OrigenImportacion {

	SIGA("SIGA", "SIGA") {
		public String toString() {
			return "SIGA";
		}
	},
	OEGAM_WS("OEGAM_WS", "OEGAM_WS") {
		public String toString() {
			return "OEGAM_WS";
		}
	},
	IMPEX("IMPEX", "IMPEX") {
		public String toString() {
			return "IMPEX";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private OrigenImportacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static OrigenImportacion convertir(String valorEnum) {
		for (OrigenImportacion element : OrigenImportacion.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (OrigenImportacion element : OrigenImportacion.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
