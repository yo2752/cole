package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TiposMedio {

	Prensa("PRENSA", "Prensa") {
		public String toString() {
			return "PRENSA";
		}
	},
	Radio("RADIO", "Radio") {
		public String toString() {
			return "RADIO";
		}
	},
	Television("TELEVISION", "Televisión") {
		public String toString() {
			return "TELEVISION";
		}
	},
	Boletin("BOLETIN", "Boletín") {
		public String toString() {
			return "BOLETIN";
		}
	},
	Otros("OTROS", "Otros") {
		public String toString() {
			return "OTROS";
		}
	};

	private TiposMedio(String valorEnum, String nombreEnum) {
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

	// valueOf
	public static TiposMedio convertir(String valorEnum) {
		if ("PRENSA".equals(valorEnum))
			return Prensa;
		if ("RADIO".equals(valorEnum))
			return Radio;
		if ("TELEVISION".equals(valorEnum))
			return Television;
		if ("BOLETIN".equals(valorEnum))
			return Boletin;
		if ("OTROS".equals(valorEnum))
			return Otros;
		return null;
	}
}
