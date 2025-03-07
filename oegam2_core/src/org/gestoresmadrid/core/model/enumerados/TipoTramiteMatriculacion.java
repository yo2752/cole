package org.gestoresmadrid.core.model.enumerados;

public enum TipoTramiteMatriculacion {

	MATRICULAR_TIPO_DEFINITIVA("1", "Matricular tipo definitiva") {
		public String toString() {
			return "Matricular tipo definitiva";
		}
	},
	MATRICULAR_DEFINITIVAMENTE_DESDE_ORIGEN_TEMPORAL("2", "Matricular definitivamente desde origen temporal") {
		public String toString() {
			return "Matricular definitivamente desde origen temporal";
		}
	},
	MATRICULAR_DEFINITIVAMENTE_DESDE_ORIGEN_TURISTICO("3", "Matricular definitivamente desde origen turístico") {
		public String toString() {
			return "Matricular definitivamente desde origen turístico";
		}
	},
	MATRICULAR_TIPO_TURISTICO("4", "Matricular tipo turístico") {
		public String toString() {
			return "Matricular tipo turístico";
		}
	},
	MATRICULAR_TIPO_HISTORICO("5", "Matricular tipo histórico") {
		public String toString() {
			return "Matricular tipo histórico";
		}
	},
	MATRICULAR_TIPO_DIPLOMATICO("6", "Matricular tipo diplomático") {
		public String toString() {
			return "Matricular tipo diplomático";
		}
	},
	MATRICULAR_TIPO_RESERVADA("7", "Matricular tipo reservada") {
		public String toString() {
			return "Matricular tipo reservada";
		}
	},
	REMATRICULAR("8", "Rematricular") {
		public String toString() {
			return "Rematricular";
		}
	},
	MATRICULAR_TIPO_TEMPORAL_PARTICULARES("9", "Matricular tipo temporal particulares") {
		public String toString() {
			return "Matricular tipo temporal particulares";
		}
	},
	MATRICULAR_TIPO_TEMPORAL_PROFESIONALES("10", "Matricular tipo temporal profesionales") {
		public String toString() {
			return "Matricular tipo temporal profesionales";
		}
	},
	PRORROGAR_MATRICULA_TEMPORAL("11", "Prorrogar matrícula temporal") {
		public String toString() {
			return "Prorrogar matrícula temporal";
		}
	},
	PRORROGAR_MATRICULA_TURISTICA("12", "Prorrogar matrícula turística") {
		public String toString() {
			return "Prorrogar matrícula turística";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTramiteMatriculacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTramiteMatriculacion convertir(String valorEnum) {

		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return MATRICULAR_TIPO_DEFINITIVA;
		}
		if ("2".equals(valorEnum)) {
			return MATRICULAR_DEFINITIVAMENTE_DESDE_ORIGEN_TEMPORAL;
		}
		if ("3".equals(valorEnum)) {
			return MATRICULAR_DEFINITIVAMENTE_DESDE_ORIGEN_TURISTICO;
		}
		if ("4".equals(valorEnum)) {
			return MATRICULAR_TIPO_TURISTICO;
		}
		if ("5".equals(valorEnum)) {
			return MATRICULAR_TIPO_HISTORICO;
		}
		if ("6".equals(valorEnum)) {
			return MATRICULAR_TIPO_DIPLOMATICO;
		}
		if ("7".equals(valorEnum)) {
			return MATRICULAR_TIPO_RESERVADA;
		}
		if ("8".equals(valorEnum)) {
			return REMATRICULAR;
		}
		if ("9".equals(valorEnum)) {
			return MATRICULAR_TIPO_TEMPORAL_PARTICULARES;
		}
		if ("10".equals(valorEnum)) {
			return MATRICULAR_TIPO_TEMPORAL_PROFESIONALES;
		}
		if ("11".equals(valorEnum)) {
			return PRORROGAR_MATRICULA_TEMPORAL;
		}
		if ("12".equals(valorEnum)) {
			return PRORROGAR_MATRICULA_TURISTICA;
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else if ("1".equals(valorEnum)) {
			return MATRICULAR_TIPO_DEFINITIVA.getNombreEnum();
		} else if ("2".equals(valorEnum)) {
			return MATRICULAR_DEFINITIVAMENTE_DESDE_ORIGEN_TEMPORAL.getNombreEnum();
		} else if ("3".equals(valorEnum)) {
			return MATRICULAR_DEFINITIVAMENTE_DESDE_ORIGEN_TURISTICO.getNombreEnum();
		} else if ("4".equals(valorEnum)) {
			return MATRICULAR_TIPO_TURISTICO.getNombreEnum();
		} else if ("5".equals(valorEnum)) {
			return MATRICULAR_TIPO_HISTORICO.getNombreEnum();
		} else if ("6".equals(valorEnum)) {
			return MATRICULAR_TIPO_DIPLOMATICO.getNombreEnum();
		} else if ("7".equals(valorEnum)) {
			return MATRICULAR_TIPO_RESERVADA.getNombreEnum();
		} else if ("8".equals(valorEnum)) {
			return REMATRICULAR.getNombreEnum();
		} else if ("9".equals(valorEnum)) {
			return MATRICULAR_TIPO_TEMPORAL_PARTICULARES.getNombreEnum();
		} else if ("10".equals(valorEnum)) {
			return MATRICULAR_TIPO_TEMPORAL_PROFESIONALES.getNombreEnum();
		} else if ("11".equals(valorEnum)) {
			return PRORROGAR_MATRICULA_TEMPORAL.getNombreEnum();
		} else if ("12".equals(valorEnum)) {
			return PRORROGAR_MATRICULA_TURISTICA.getNombreEnum();
		}
		return null;
	}
}
