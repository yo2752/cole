package org.gestoresmadrid.core.model.enumerados;

public enum EstadoCivil {

	Casado("CASADO(A)", "Casado(a)") {
		public String toString() {
			return "CASADO(A)";
		}
	},
	Soltero("SOLTERO(A)", "Soltero(a)") {
		public String toString() {
			return "SOLTERO(A)";
		}
	},
	Viudo("VIUDO(A)", "Viudo(a)") {
		public String toString() {
			return "VIUDO(A)";
		}
	},
	Divorciado("DIVORCIADO(A)", "Divorciado(a)") {
		public String toString() {
			return "DIVORCIADO(A)";
		}
	},
	Separado("SEPARADO(A)", "Separado(a)") {
		public String toString() {
			return "SEPARADO(A)";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoCivil(String valorEnum, String nombreEnum) {
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
	public static EstadoCivil convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if ("CASADO(A)".equalsIgnoreCase(valorEnum))
			return Casado;

		if ("SOLTERO(A)".equalsIgnoreCase(valorEnum))
			return Soltero;

		if ("VIUDO(A)".equalsIgnoreCase(valorEnum))
			return Viudo;

		if ("DIVORCIADO(A)".equalsIgnoreCase(valorEnum))
			return Divorciado;

		if ("SEPARADO(A)".equalsIgnoreCase(valorEnum))
			return Separado;

		return null;
	}
}
