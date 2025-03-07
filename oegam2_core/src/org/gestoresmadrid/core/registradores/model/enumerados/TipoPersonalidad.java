package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoPersonalidad {
	Fisica("1", "Fisica") {
		public String toString() {
			return "1";
		}
	},
	JuridicaPublica("2", "JuridicaPublica") {
		public String toString() {
			return "2";
		}
	},
	JuridicaPrivada("3", "JuridicaPrivada") {
		public String toString() {
			return "3";
		}
	};

	private TipoPersonalidad(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static TipoPersonalidad convertir(String valorEnum) {
		for (TipoPersonalidad element : TipoPersonalidad.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
}
