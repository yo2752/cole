package org.gestoresmadrid.core.model.enumerados;

public enum TiposReduccion576 {

	FamiliaNumerosa("RE1", "FAMILIA NUMEROSA") {
		public String toString() {
			return "RE1";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TiposReduccion576(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static TiposReduccion576 convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;
		switch (valorEnum) {
			case 1:
				return FamiliaNumerosa;
			default:
				return null;
		}
	}

	public static TiposReduccion576 convertir(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("FAMILIA NUMEROSA".equals(valorEnum)) {
			return FamiliaNumerosa;
		} else {
			return null;
		}
	}
}
