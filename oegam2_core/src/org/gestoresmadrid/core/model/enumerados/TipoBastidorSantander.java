package org.gestoresmadrid.core.model.enumerados;

public enum TipoBastidorSantander {

	CT("0", "Carterizado") {
		public String toString() {
			return "Carterizado";
		}
	},
	RT("1", "Retail") {
		public String toString() {
			return "Retail";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoBastidorSantander(String valorEnum, String nombreEnum) {
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

	public static TipoBastidorSantander convertir(String valorEnum) {
		for (TipoBastidorSantander element : TipoBastidorSantander.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoBastidorSantander element : TipoBastidorSantander.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String getValorEnumPorPorNombreEnum(String nombre) {
		if (nombre != null && !nombre.isEmpty()) {
			for (TipoBastidorSantander element : TipoBastidorSantander.values()) {
				if (element.nombreEnum.equals(nombre)) {
					return element.valorEnum;
				}
			}
		}
		return null;
	}

	public static Boolean validarTipoBastidorPorNombre(String nombre) {
		if (nombre != null && !nombre.isEmpty()) {
			for (TipoBastidorSantander tipo : TipoBastidorSantander.values()) {
				if (tipo.getNombreEnum().equals(nombre)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Boolean validarTipoBastidorPorValor(String valor) {
		if (valor != null && !valor.isEmpty()) {
			for (TipoBastidorSantander tipo : TipoBastidorSantander.values()) {
				if (tipo.getValorEnum().equals(valor)) {
					return true;
				}
			}
		}
		return false;
	}

}