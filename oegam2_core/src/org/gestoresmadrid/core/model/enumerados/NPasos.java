package org.gestoresmadrid.core.model.enumerados;

public enum NPasos {

	Uno("1", "1") {
		public String toString() {
			return "1";
		}
	},
	Dos("2", "2") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private NPasos(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static NPasos convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (valorEnum.equals("1"))
			return Uno;
		else if (valorEnum.equals("2"))
			return Dos;
		else
			return null;
	}
}