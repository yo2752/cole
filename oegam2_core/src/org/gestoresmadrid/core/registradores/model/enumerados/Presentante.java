package org.gestoresmadrid.core.registradores.model.enumerados;

public enum Presentante {

	GESTOR("1", "Gestor") {
		public String toString() {
			return "1";
		}
	},
	GESTORIA("2", "Gestoria") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Presentante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Presentante convertir(String valorEnum) {
		switch (Integer.valueOf(valorEnum).intValue()) {
			case 1:
				return GESTOR;
			case 2:
				return GESTORIA;
			default:
				return null;
		}
	}
}
