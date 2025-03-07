package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoAcuerdo {
	Cese("CESE", "Cese") {
		public String toString() {
			return "CESE";
		}
	},
	Nombramiento("NOMBRAMIENTO", "Nombramiento") {
		public String toString() {
			return "NOMBRAMIENTO";
		}
	};

	private TipoAcuerdo(String valorEnum, String nombreEnum) {
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
}
