package trafico.utiles.enumerados;

import trafico.beans.PaisImportacionBean;

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

	public static PaisImportacionBean convertirABean(String idPaisImportacion) {
		if (idPaisImportacion == null) {
			return new PaisImportacionBean();
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ALEMANIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.ALEMANIA.getValorEnum(),
					PaisImportacion.ALEMANIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.AUSTRIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.AUSTRIA.getValorEnum(),
					PaisImportacion.AUSTRIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.BELGICA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.BELGICA.getValorEnum(),
					PaisImportacion.BELGICA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.BULGARIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.BULGARIA.getValorEnum(),
					PaisImportacion.BULGARIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.CHIPRE.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.CHIPRE.getValorEnum(),
					PaisImportacion.CHIPRE.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.CROACIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.CROACIA.getValorEnum(),
					PaisImportacion.CROACIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.DINAMARCA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.DINAMARCA.getValorEnum(),
					PaisImportacion.DINAMARCA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESLOVAQUIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.ESLOVAQUIA.getValorEnum(),
					PaisImportacion.ESLOVAQUIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESLOVENIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.ESLOVENIA.getValorEnum(),
					PaisImportacion.ESLOVENIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESTONIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.ESTONIA.getValorEnum(),
					PaisImportacion.ESTONIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.FINLANDIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.FINLANDIA.getValorEnum(),
					PaisImportacion.FINLANDIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.FRANCIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.FRANCIA.getValorEnum(),
					PaisImportacion.FRANCIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.GRECIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.GRECIA.getValorEnum(),
					PaisImportacion.GRECIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.HUNGRIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.HUNGRIA.getValorEnum(),
					PaisImportacion.HUNGRIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.IRLANDA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.IRLANDA.getValorEnum(),
					PaisImportacion.IRLANDA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ISLANDIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.ISLANDIA.getValorEnum(),
					PaisImportacion.ISLANDIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ITALIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.ITALIA.getValorEnum(),
					PaisImportacion.ITALIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LETONIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.LETONIA.getValorEnum(),
					PaisImportacion.LETONIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LIECHTENSTEIN.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.LIECHTENSTEIN.getValorEnum(),
					PaisImportacion.LIECHTENSTEIN.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LITUANIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.LITUANIA.getValorEnum(),
					PaisImportacion.LITUANIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LUXEMBURGO.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.LUXEMBURGO.getValorEnum(),
					PaisImportacion.LUXEMBURGO.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.MALTA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.MALTA.getValorEnum(), PaisImportacion.MALTA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.NORUEGA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.NORUEGA.getValorEnum(),
					PaisImportacion.NORUEGA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.OTROS.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.OTROS.getValorEnum(), PaisImportacion.OTROS.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.PAISES_BAJOS.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.PAISES_BAJOS.getValorEnum(),
					PaisImportacion.PAISES_BAJOS.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.POLONIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.POLONIA.getValorEnum(),
					PaisImportacion.POLONIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.PORTUGAL.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.PORTUGAL.getValorEnum(),
					PaisImportacion.PORTUGAL.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.REINO_UNIDO.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.REINO_UNIDO.getValorEnum(),
					PaisImportacion.REINO_UNIDO.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.REPUBLICA_CHECA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.REPUBLICA_CHECA.getValorEnum(),
					PaisImportacion.REPUBLICA_CHECA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.RUMANIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.RUMANIA.getValorEnum(),
					PaisImportacion.RUMANIA.getNombreEnum());
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.SUECIA.getValorEnum())) {
			return new PaisImportacionBean(PaisImportacion.SUECIA.getValorEnum(),
					PaisImportacion.SUECIA.getNombreEnum());
		}
		return null;
	}

	public static PaisImportacion convertir(String idPaisImportacion) {
		if (idPaisImportacion == null) {
			return null;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ALEMANIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.ALEMANIA.getValorEnum())) {
			return PaisImportacion.ALEMANIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.AUSTRIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.AUSTRIA.getValorEnum())) {
			return PaisImportacion.AUSTRIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.BELGICA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.BELGICA.getValorEnum())) {
			return PaisImportacion.BELGICA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.BULGARIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.BULGARIA.getValorEnum())) {
			return PaisImportacion.BULGARIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.CHIPRE.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.CHIPRE.getValorEnum())) {
			return PaisImportacion.CHIPRE;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.CROACIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.CROACIA.getValorEnum())) {
			return PaisImportacion.CROACIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.DINAMARCA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.DINAMARCA.getValorEnum())) {
			return PaisImportacion.DINAMARCA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESLOVAQUIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.ESLOVAQUIA.getValorEnum())) {
			return PaisImportacion.ESLOVAQUIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESLOVENIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.ESLOVENIA.getValorEnum())) {
			return PaisImportacion.ESLOVENIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ESTONIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.ESTONIA.getValorEnum())) {
			return PaisImportacion.ESTONIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.FINLANDIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.FINLANDIA.getValorEnum())) {
			return PaisImportacion.FINLANDIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.FRANCIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.FRANCIA.getValorEnum())) {
			return PaisImportacion.FRANCIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.GRECIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.GRECIA.getValorEnum())) {
			return PaisImportacion.GRECIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.HUNGRIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.HUNGRIA.getValorEnum())) {
			return PaisImportacion.HUNGRIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.IRLANDA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.IRLANDA.getValorEnum())) {
			return PaisImportacion.IRLANDA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ISLANDIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.ISLANDIA.getValorEnum())) {
			return PaisImportacion.ISLANDIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.ITALIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.ITALIA.getValorEnum())) {
			return PaisImportacion.ITALIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LETONIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.LETONIA.getValorEnum())) {
			return PaisImportacion.LETONIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LIECHTENSTEIN.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.LIECHTENSTEIN.getValorEnum())) {
			return PaisImportacion.LIECHTENSTEIN;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LITUANIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.LITUANIA.getValorEnum())) {
			return PaisImportacion.LITUANIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.LUXEMBURGO.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.LUXEMBURGO.getValorEnum())) {
			return PaisImportacion.LUXEMBURGO;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.MALTA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.MALTA.getValorEnum())) {
			return PaisImportacion.MALTA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.NORUEGA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.NORUEGA.getValorEnum())) {
			return PaisImportacion.NORUEGA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.OTROS.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.FUERA_UE.getValorEnum())) {
			return PaisImportacion.OTROS;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.PAISES_BAJOS.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.PAISES_BAJOS.getValorEnum())) {
			return PaisImportacion.PAISES_BAJOS;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.POLONIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.POLONIA.getValorEnum())) {
			return PaisImportacion.POLONIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.PORTUGAL.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.PORTUGAL.getValorEnum())) {
			return PaisImportacion.PORTUGAL;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.REINO_UNIDO.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.REINO_UNIDO.getValorEnum())) {
			return PaisImportacion.REINO_UNIDO;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.REPUBLICA_CHECA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.REPUBLICA_CHECA.getValorEnum())) {
			return PaisImportacion.REPUBLICA_CHECA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.RUMANIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.RUMANIA.getValorEnum())) {
			return PaisImportacion.RUMANIA;
		}
		if (idPaisImportacion.equalsIgnoreCase(PaisImportacion.SUECIA.getValorEnum())
				|| idPaisImportacion.equals(Procedencia.SUECIA.getValorEnum())) {
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