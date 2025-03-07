package org.gestoresmadrid.core.model.enumerados;

public enum SubTipoFicheros {

	TASAS_ETIQUETAS("0", "ETIQUETAS") {
		public String toString() {
			return "ETIQUETAS";
		}
	},
	TASAS_ETIQUETAS_TEMP("1", "TEMP_ETIQUETAS") {
		public String toString() {
			return "TEMP_ETIQUETAS";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private SubTipoFicheros(String valorEnum, String nombreEnum) {
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

	public static SubTipoFicheros convertir(String valorEnum) {
		for (SubTipoFicheros element : SubTipoFicheros.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (SubTipoFicheros element : SubTipoFicheros.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String getValorEnumPorPorNombreEnum(String valorEnum) {
		for (SubTipoFicheros element : SubTipoFicheros.values()) {
			if (element.nombreEnum.equals(valorEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}

	public static String convertirNombre(String nombreEnum) {
		for (SubTipoFicheros element : SubTipoFicheros.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
}