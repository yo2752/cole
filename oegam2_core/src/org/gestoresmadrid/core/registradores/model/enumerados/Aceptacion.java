package org.gestoresmadrid.core.registradores.model.enumerados;

public enum Aceptacion {
	EN_LA_MISMA_JUNTA("EN LA MISMA JUNTA", "En la misma junta") {
		public String toString() {
			return "EN LA MISMA JUNTA";
		}
	},
	CON_POSTERIORIDAD("CON POSTERIORIDAD", "Con posterioridad") {
		public String toString() {
			return "CON POSTERIORIDAD";
		}
	};

	private Aceptacion(String valorEnum, String nombreEnum) {
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

	public static Aceptacion recuperar(String valorEnum) {
		if (valorEnum.equals("CON POSTERIORIDAD")) {
			return Aceptacion.CON_POSTERIORIDAD;
		} else if (valorEnum.equals("EN LA MISMA JUNTA")) {
			return Aceptacion.EN_LA_MISMA_JUNTA;
		} else {
			return null;
		}
	}
}
