package org.gestoresmadrid.core.model.enumerados;

public enum TipoBastidor {

	VN("0", "VN") {
		public String toString() {
			return "0";
		}
	},
	VO("1", "VO") {
		public String toString() {
			return "1";
		}
	},
	DM("2", "DM") {
		public String toString() {
			return "2";
		}
	},
	FI("3", "FI") {
		public String toString() {
			return "3";
		}
	},
	LE("4", "LE") {
		public String toString() {
			return "4";
		}
	},
	RE("5", "RE") {
		public String toString() {
			return "5";
		}
	},
	FR("6", "FR") {
		public String toString() {
			return "6";
		}
	};
	;

	private String valorEnum;
	private String nombreEnum;

	private TipoBastidor(String valorEnum, String nombreEnum) {
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

	public static TipoBastidor convertir(String valorEnum) {
		for (TipoBastidor element : TipoBastidor.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoBastidor element : TipoBastidor.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String getValorEnumPorPorNombreEnum(String nombre) {
		if (nombre != null && !nombre.isEmpty()) {
			for (TipoBastidor element : TipoBastidor.values()) {
				if (element.nombreEnum.equals(nombre)) {
					return element.valorEnum;
				}
			}
		}
		return null;
	}

	public static Boolean validarTipoBastidorPorNombre(String nombre) {
		if (nombre != null && !nombre.isEmpty()) {
			for (TipoBastidor tipo : TipoBastidor.values()) {
				if (tipo.getNombreEnum().equals(nombre)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Boolean validarTipoBastidorPorValor(String valor) {
		if (valor != null && !valor.isEmpty()) {
			for (TipoBastidor tipo : TipoBastidor.values()) {
				if (tipo.getValorEnum().equals(valor)) {
					return true;
				}
			}
		}
		return false;
	}

}