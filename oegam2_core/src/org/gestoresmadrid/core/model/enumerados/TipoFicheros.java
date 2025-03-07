package org.gestoresmadrid.core.model.enumerados;

public enum TipoFicheros {

	Tasas("0", "TASAS"){
		public String toString() {
			return "TASAS";
		}
	},
	Carta_Pago("1", "Carta de Pago"){
		public String toString() {
			return "Carta de Pago";
		}
	},
	Diligencia("2", "Diligencia"){
		public String toString() {
			return "Diligencia";
		}
	},
	Escritura("3", "Escritura"){
		public String toString() {
			return "Escritura";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoFicheros(String valorEnum, String nombreEnum) {
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
	
	public static TipoFicheros convertir(String valorEnum) {
		for (TipoFicheros element : TipoFicheros.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoFicheros element : TipoFicheros.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String getValorEnumPorPorNombreEnum(String valorEnum) {
		for (TipoFicheros element : TipoFicheros.values()) {
			if (element.nombreEnum.equals(valorEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}

	public static String convertirNombre(String nombreEnum) {
		for (TipoFicheros element : TipoFicheros.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
}
