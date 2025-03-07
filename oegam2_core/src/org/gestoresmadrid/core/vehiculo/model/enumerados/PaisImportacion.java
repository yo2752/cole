package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum PaisImportacion {

	AUSTRIA("A", "Austria") {
		public String toString() {
			return "A";
		}
	},
	BELGICA("B", "Bélgica") {
		public String toString() {
			return "B";
		}
	},
	BULGARIA("BG", "Bulgaria") {
		public String toString() {
			return "BG";
		}
	},
	CHIPRE("CY", "Chipre") {
		public String toString() {
			return "CY";
		}
	},
	CROACIA("HR", "Croacia") {
		public String toString() {
			return "HR";
		}
	},
	REPUBLICA_CHECA("CZ", "República checa") {
		public String toString() {
			return "CZ";
		}
	},
	DINAMARCA("DK", "Dinamarca") {
		public String toString() {
			return "DK";
		}
	},
	ESTONIA("EST", "Estonia") {
		public String toString() {
			return "EST";
		}
	},
	FINLANDIA("FIN", "Finlandia") {
		public String toString() {
			return "FIN";
		}
	},
	FRANCIA("F", "Francia") {
		public String toString() {
			return "F";
		}
	},
	ALEMANIA("D", "Alemania") {
		public String toString() {
			return "D";
		}
	},
	GRECIA("GR", "Grecia") {
		public String toString() {
			return "GR";
		}
	},
	HUNGRIA("H", "Hungría") {
		public String toString() {
			return "H";
		}
	},
	IRLANDA("IRL", "Irlanda") {
		public String toString() {
			return "IRL";
		}
	},
	ITALIA("I", "Italia") {
		public String toString() {
			return "I";
		}
	},
	LETONIA("LV", "Letonia") {
		public String toString() {
			return "LV";
		}
	},
	LITUANIA("LT", "Lituania") {
		public String toString() {
			return "LT";
		}
	},
	LUXEMBURGO("L", "Luxemburgo") {
		public String toString() {
			return "L";
		}
	},
	MALTA("M", "Malta") {
		public String toString() {
			return "M";
		}
	},
	PAISES_BAJOS("NL", "Países bajos") {
		public String toString() {
			return "NL";
		}
	},
	POLONIA("PL", "Polonia") {
		public String toString() {
			return "PL";
		}
	},
	PORTUGAL("P", "Portugal") {
		public String toString() {
			return "P";
		}
	},
	RUMANIA("RO", "Rumanía") {
		public String toString() {
			return "RO";
		}
	},
	ESLOVAQUIA("SVK", "Eslovaquia") {
		public String toString() {
			return "SVK";
		}
	},
	ESLOVENIA("SLO", "Eslovenia") {
		public String toString() {
			return "SLO";
		}
	},
	SUECIA("S", "Suecia") {
		public String toString() {
			return "S";
		}
	},
	REINO_UNIDO("GBR", "Reino Unido") {
		public String toString() {
			return "GBR";
		}
	},
	ISLANDIA("IS", "Islandia") {
		public String toString() {
			return "IS";
		}
	},
	LIECHTENSTEIN("FL", "Liechtenstein") {
		public String toString() {
			return "FL";
		}
	},
	NORUEGA("N", "Noruega") {
		public String toString() {
			return "N";
		}
	},
	OTROS("OT", "Otros") {
		public String toString() {
			return "OT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private PaisImportacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static PaisImportacion convertir(String idPaisImportacion) {
		if (idPaisImportacion == null) {
			return null;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ALEMANIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.ALEMANIA.getValorEnum())) {
			return PaisImportacion.ALEMANIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.AUSTRIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.AUSTRIA.getValorEnum())) {
			return PaisImportacion.AUSTRIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.BELGICA.getValorEnum()) || idPaisImportacion.equals(Procedencia.BELGICA.getValorEnum())) {
			return PaisImportacion.BELGICA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.BULGARIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.BULGARIA.getValorEnum())) {
			return PaisImportacion.BULGARIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.CHIPRE.getValorEnum()) || idPaisImportacion.equals(Procedencia.CHIPRE.getValorEnum())) {
			return PaisImportacion.CHIPRE;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.CROACIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.CROACIA.getValorEnum())) {
			return PaisImportacion.CROACIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.DINAMARCA.getValorEnum()) || idPaisImportacion.equals(Procedencia.DINAMARCA.getValorEnum())) {
			return PaisImportacion.DINAMARCA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESLOVAQUIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.ESLOVAQUIA.getValorEnum())) {
			return PaisImportacion.ESLOVAQUIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESLOVENIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.ESLOVENIA.getValorEnum())) {
			return PaisImportacion.ESLOVENIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESTONIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.ESTONIA.getValorEnum())) {
			return PaisImportacion.ESTONIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.FINLANDIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.FINLANDIA.getValorEnum())) {
			return PaisImportacion.FINLANDIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.FRANCIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.FRANCIA.getValorEnum())) {
			return PaisImportacion.FRANCIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.GRECIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.GRECIA.getValorEnum())) {
			return PaisImportacion.GRECIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.HUNGRIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.HUNGRIA.getValorEnum())) {
			return PaisImportacion.HUNGRIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.IRLANDA.getValorEnum()) || idPaisImportacion.equals(Procedencia.IRLANDA.getValorEnum())) {
			return PaisImportacion.IRLANDA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ISLANDIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.ISLANDIA.getValorEnum())) {
			return PaisImportacion.ISLANDIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ITALIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.ITALIA.getValorEnum())) {
			return PaisImportacion.ITALIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LETONIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.LETONIA.getValorEnum())) {
			return PaisImportacion.LETONIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LIECHTENSTEIN.getValorEnum()) || idPaisImportacion.equals(Procedencia.LIECHTENSTEIN.getValorEnum())) {
			return PaisImportacion.LIECHTENSTEIN;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LITUANIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.LITUANIA.getValorEnum())) {
			return PaisImportacion.LITUANIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LUXEMBURGO.getValorEnum()) || idPaisImportacion.equals(Procedencia.LUXEMBURGO.getValorEnum())) {
			return PaisImportacion.LUXEMBURGO;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.MALTA.getValorEnum()) || idPaisImportacion.equals(Procedencia.MALTA.getValorEnum())) {
			return PaisImportacion.MALTA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.NORUEGA.getValorEnum()) || idPaisImportacion.equals(Procedencia.NORUEGA.getValorEnum())) {
			return PaisImportacion.NORUEGA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.OTROS.getValorEnum()) || idPaisImportacion.equals(Procedencia.FUERA_UE.getValorEnum())) {
			return PaisImportacion.OTROS;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.PAISES_BAJOS.getValorEnum()) || idPaisImportacion.equals(Procedencia.PAISES_BAJOS.getValorEnum())) {
			return PaisImportacion.PAISES_BAJOS;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.POLONIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.POLONIA.getValorEnum())) {
			return PaisImportacion.POLONIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.PORTUGAL.getValorEnum()) || idPaisImportacion.equals(Procedencia.PORTUGAL.getValorEnum())) {
			return PaisImportacion.PORTUGAL;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.REINO_UNIDO.getValorEnum()) || idPaisImportacion.equals(Procedencia.REINO_UNIDO.getValorEnum())) {
			return PaisImportacion.REINO_UNIDO;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.REPUBLICA_CHECA.getValorEnum()) || idPaisImportacion.equals(Procedencia.REPUBLICA_CHECA.getValorEnum())) {
			return PaisImportacion.REPUBLICA_CHECA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.RUMANIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.RUMANIA.getValorEnum())) {
			return PaisImportacion.RUMANIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.SUECIA.getValorEnum()) || idPaisImportacion.equals(Procedencia.SUECIA.getValorEnum())) {
			return PaisImportacion.SUECIA;
		}
		return null;
	}

	public static PaisImportacion convertirPaisImportacion(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (PaisImportacion paisImportacion : PaisImportacion.values()) {
				if (paisImportacion.getValorEnum().equals(valorEnum)) {
					return paisImportacion;
				}
			}
			return null;
		}
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}
