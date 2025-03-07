package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoReunionJunta {
	Ordinaria("ORDINARIA", "Ordinaria") {
		public String toString() {
			return "ORDINARIA";
		}
	},
	Extraordinaria("EXTRAORDINARIA", "Extraordinaria") {
		public String toString() {
			return "EXTRAORDINARIA";
		}
	};

	private TipoReunionJunta(String valorEnum, String nombreEnum) {
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
