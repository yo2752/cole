package org.gestoresmadrid.core.model.enumerados;

public enum NumCola {

	Uno("1", "1") {
		public String toString() {
			return "1";
		}
	},
	Dos("2", "2") {
		public String toString() {
			return "2";
		}
	},
	Tres("3", "3") {
		public String toString() {
			return "3";
		}
	},
	Cuantro("4", "4") {
		public String toString() {
			return "4";
		}
	},
	Cinco("5", "5") {
		public String toString() {
			return "5";
		}
	},
	Seis("6", "6") {
		public String toString() {
			return "6";
		}
	},
	Siete("7", "7") {
		public String toString() {
			return "7";
		}
	},
	Ocho("8", "8") {
		public String toString() {
			return "8";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private NumCola(String valorEnum, String nombreEnum) {
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
	
	public static NumCola convertir(String valorEnum) {
		for (NumCola element : NumCola.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (NumCola element : NumCola.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String getValorEnumPorPorNombreEnum(String nombre) {
		if(nombre != null && !nombre.isEmpty()){
			for (NumCola element : NumCola.values()) {
				if (element.nombreEnum.equals(nombre)) {
					return element.valorEnum;
				}
			}
		}
		return null;
	}

	public static Boolean validarNumColaPorNombre(String nombre) {
		if(nombre != null && !nombre.isEmpty()){
			for(NumCola numCola : NumCola.values()){
				if(numCola.getNombreEnum().equals(nombre)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Boolean validarNumColaPorValor(String valor) {
		if(valor != null && !valor.isEmpty()){
			for(NumCola numCola : NumCola.values()){
				if(numCola.getValorEnum().equals(valor)){
					return true;
				}
			}
		}
		return false;
	}
}
