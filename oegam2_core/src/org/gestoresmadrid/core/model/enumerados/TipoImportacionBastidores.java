package org.gestoresmadrid.core.model.enumerados;

public enum TipoImportacionBastidores {

	Alta("A", "Alta"){
		public String toString() {
			return "Alta";
		}
	},
	Baja("B", "Baja"){
		public String toString() {
			return "Baja";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoImportacionBastidores(String valorEnum, String nombreEnum) {
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
	
	public static TipoImportacionBastidores convertir(String valorEnum) {
		for (TipoImportacionBastidores element : TipoImportacionBastidores.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoImportacionBastidores element : TipoImportacionBastidores.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
