package trafico.utiles.enumerados;

public enum Procedencia {

	ALEMANIA("276", "Alemania") {
		public String toString() {
			return "Alemania";
		}
	},
	AUSTRIA("040", "Austria") {
		public String toString() {
			return "Austria";
		}
	},
	BELGICA("056", "Bélgica") {
		public String toString() {
			return "Bélgica";
		}
	},
	BULGARIA("100", "Bulgaria") {
		public String toString() {
			return "Bulgaria";
		}
	},
	CHIPRE("196", "Chipre") {
		public String toString() {
			return "Chipre";
		}
	},
	CROACIA("191", "Croacia") {
		public String toString() {
			return "Croacia";
		}
	},
	DINAMARCA("208", "Dinamarca") {
		public String toString() {
			return "Dinamarca";
		}
	},
	ESLOVAQUIA("703", "Eslovaquia") {
		public String toString() {
			return "Eslovaquia";
		}
	},
	ESLOVENIA("705", "Eslovenia") {
		public String toString() {
			return "Eslovenia";
		}
	},
	ESTONIA("233", "Estonia") {
		public String toString() {
			return "Estonia";
		}
	},
	FINLANDIA("246", "Finlandia") {
		public String toString() {
			return "Finlandia";
		}
	},
	FRANCIA("250", "Francia") {
		public String toString() {
			return "F";
		}
	},
	GRECIA("300", "Grecia") {
		public String toString() {
			return "Grecia";
		}
	},
	HUNGRIA("348", "Hungría") {
		public String toString() {
			return "Hungría";
		}
	},
	IRLANDA("372", "Irlanda") {
		public String toString() {
			return "Irlanda";
		}
	},
	ISLANDIA("352", "Islandia") {
		public String toString() {
			return "Islandia";
		}
	},
	ITALIA("380", "Italia") {
		public String toString() {
			return "Italia";
		}
	},
	LETONIA("428", "Letonia") {
		public String toString() {
			return "Letonia";
		}
	},
	LIECHTENSTEIN("438", "Liechtenstein") {
		public String toString() {
			return "Liechtenstein";
		}
	},
	LITUANIA("440", "Lituania") {
		public String toString() {
			return "Lituania";
		}
	},
	LUXEMBURGO("442", "Luxemburgo") {
		public String toString() {
			return "Luxemburgo";
		}
	},
	MALTA("470", "Malta") {
		public String toString() {
			return "Malta";
		}
	},
	NORUEGA("578", "Noruega") {
		public String toString() {
			return "Noruega";
		}
	},
	PAISES_BAJOS("528", "Países bajos") {
		public String toString() {
			return "Países bajos";
		}
	},
	POLONIA("616", "Polonia") {
		public String toString() {
			return "Polonia";
		}
	},
	PORTUGAL("620", "Portugal") {
		public String toString() {
			return "Portugal";
		}
	},
	REINO_UNIDO("826", "Reino Unido") {
		public String toString() {
			return "Reino Unido";
		}
	},
	REPUBLICA_CHECA("203", "República checa") {
		public String toString() {
			return "República checa";
		}
	},
	RUMANIA("642", "Rumanía") {
		public String toString() {
			return "Rumanía";
		}
	},
	SUECIA("752", "Suecia") {
		public String toString() {
			return "Suecia";
		}
	},
	FUERA_UE("0", "Fuera UE") {
		public String toString() {
			return "Fuera UE";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Procedencia(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	/**
	 * Este convertir debe tener en cuenta posibles antiguos valores de 'País
	 * importación' por si se recuperan trámites anteriores a la implantación de
	 * MATW
	 * 
	 * @param idProcedencia
	 * @return Objeto de la enumeración Procedencia
	 */
	public static Procedencia getProcedencia(String idProcedencia) {
		if (idProcedencia == null) {
			return null;
		}
		if (idProcedencia.equals(Procedencia.ALEMANIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.ALEMANIA.getValorEnum())) {
			return Procedencia.ALEMANIA;
		}
		if (idProcedencia.equals(Procedencia.AUSTRIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.AUSTRIA.getValorEnum())) {
			return Procedencia.AUSTRIA;
		}
		if (idProcedencia.equals(Procedencia.BELGICA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.BELGICA.getValorEnum())) {
			return Procedencia.BELGICA;
		}
		if (idProcedencia.equals(Procedencia.BULGARIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.BULGARIA.getValorEnum())) {
			return Procedencia.BULGARIA;
		}
		if (idProcedencia.equals(Procedencia.CHIPRE.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.CHIPRE.getValorEnum())) {
			return Procedencia.CHIPRE;
		}
		if (idProcedencia.equals(Procedencia.CROACIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.CROACIA.getValorEnum())) {
			return Procedencia.CROACIA;
		}
		if (idProcedencia.equals(Procedencia.DINAMARCA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.DINAMARCA.getValorEnum())) {
			return Procedencia.DINAMARCA;
		}
		if (idProcedencia.equals(Procedencia.ESLOVAQUIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.ESLOVAQUIA.getValorEnum())) {
			return Procedencia.ESLOVAQUIA;
		}
		if (idProcedencia.equals(Procedencia.ESLOVENIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.ESLOVENIA.getValorEnum())) {
			return Procedencia.ESLOVENIA;
		}
		if (idProcedencia.equals(Procedencia.ESTONIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.ESTONIA.getValorEnum())) {
			return Procedencia.ESTONIA;
		}
		if (idProcedencia.equals(Procedencia.FINLANDIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.FINLANDIA.getValorEnum())) {
			return Procedencia.FINLANDIA;
		}
		if (idProcedencia.equals(Procedencia.FRANCIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.FRANCIA.getValorEnum())) {
			return Procedencia.FRANCIA;
		}
		if (idProcedencia.equals(Procedencia.GRECIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.GRECIA.getValorEnum())) {
			return Procedencia.GRECIA;
		}
		if (idProcedencia.equals(Procedencia.HUNGRIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.HUNGRIA.getValorEnum())) {
			return Procedencia.HUNGRIA;
		}
		if (idProcedencia.equals(Procedencia.IRLANDA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.IRLANDA.getValorEnum())) {
			return Procedencia.IRLANDA;
		}
		if (idProcedencia.equals(Procedencia.ISLANDIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.ISLANDIA.getValorEnum())) {
			return Procedencia.ISLANDIA;
		}
		if (idProcedencia.equals(Procedencia.ITALIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.ITALIA.getValorEnum())) {
			return Procedencia.ITALIA;
		}
		if (idProcedencia.equals(Procedencia.LETONIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.LETONIA.getValorEnum())) {
			return Procedencia.LETONIA;
		}
		if (idProcedencia.equals(Procedencia.LIECHTENSTEIN.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.LIECHTENSTEIN.getValorEnum())) {
			return Procedencia.LIECHTENSTEIN;
		}
		if (idProcedencia.equals(Procedencia.LITUANIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.LITUANIA.getValorEnum())) {
			return Procedencia.LITUANIA;
		}
		if (idProcedencia.equals(Procedencia.LUXEMBURGO.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.LUXEMBURGO.getValorEnum())) {
			return Procedencia.LUXEMBURGO;
		}
		if (idProcedencia.equals(Procedencia.MALTA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.MALTA.getValorEnum())) {
			return Procedencia.MALTA;
		}
		if (idProcedencia.equals(Procedencia.NORUEGA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.NORUEGA.getValorEnum())) {
			return Procedencia.NORUEGA;
		}
		if (idProcedencia.equals(Procedencia.PAISES_BAJOS.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.PAISES_BAJOS.getValorEnum())) {
			return Procedencia.PAISES_BAJOS;
		}
		if (idProcedencia.equals(Procedencia.POLONIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.POLONIA.getValorEnum())) {
			return Procedencia.POLONIA;
		}
		if (idProcedencia.equals(Procedencia.PORTUGAL.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.PORTUGAL.getValorEnum())) {
			return Procedencia.PORTUGAL;
		}
		if (idProcedencia.equals(Procedencia.REINO_UNIDO.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.REINO_UNIDO.getValorEnum())) {
			return Procedencia.REINO_UNIDO;
		}
		if (idProcedencia.equals(Procedencia.REPUBLICA_CHECA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.REPUBLICA_CHECA.getValorEnum())) {
			return Procedencia.REPUBLICA_CHECA;
		}
		if (idProcedencia.equals(Procedencia.RUMANIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.RUMANIA.getValorEnum())) {
			return Procedencia.RUMANIA;
		}
		if (idProcedencia.equals(Procedencia.SUECIA.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.SUECIA.getValorEnum())) {
			return Procedencia.SUECIA;
		}
		if (idProcedencia.equals(Procedencia.FUERA_UE.getValorEnum())
				|| idProcedencia.equals(PaisImportacion.OTROS.getValorEnum())) {
			return Procedencia.FUERA_UE;
		}
		return null;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}