package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados;

public enum TiposDuplicadosPermisos {

	DETERIORO("D", "Deterioro", "DETERIORO PERMISO CONDUCIR") {
		public String toString() {
			return "D";
		}
	},
	PERDIDA("P", "Perdida", "PERDIDA PERMISO CONDUCIR") {
		public String toString() {
			return "P";
		}
	},
	SUSTRACCION("S", "Sustracción", "SUSTRACCIÓN PERMISO CONDUCIR") {
		public String toString() {
			return "S";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String nombreEntero;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getNombreEntero() {
		return nombreEntero;
	}

	private TiposDuplicadosPermisos(String valorEnum, String nombreEnum, String nombreEntero) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.nombreEntero = nombreEntero;
	}

	public static TiposDuplicadosPermisos convertir(String valorEnum) {
		for (TiposDuplicadosPermisos element : TiposDuplicadosPermisos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TiposDuplicadosPermisos element : TiposDuplicadosPermisos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEntero;
			}
		}
		return null;
	}

	public static String convertirNombre(String valorEnum) {
		for (TiposDuplicadosPermisos element : TiposDuplicadosPermisos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
