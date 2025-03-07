package org.gestoresmadrid.core.importacionFichero.model.enumerados;

public enum EstadosImpContrato {
	Sin_Permiso("0", "Sin Permiso") {
		public String toString() {
			return "0";
		}
	},
	Con_Permiso("1", "Con Permiso") {
		public String toString() {
			return "1";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadosImpContrato(String valorEnum, String nombreEnum) {
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

	public static EstadosImpContrato convertir(String valorEnum) {
		for (EstadosImpContrato element : EstadosImpContrato.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadosImpContrato element : EstadosImpContrato.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
