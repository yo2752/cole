package org.gestoresmadrid.core.registradores.model.enumerados;

public enum Inmatriculada {
	S("S", "Si") {
		public String toString() {
			return "S";
		}
	},
	N("N", "No") {
		public String toString() {
			return "N";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Inmatriculada(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static Inmatriculada convertir(String valorEnum) {
		if ("S".equals(valorEnum))
			return S;
		if ("N".equals(valorEnum))
			return N;
		return null;
	}
}