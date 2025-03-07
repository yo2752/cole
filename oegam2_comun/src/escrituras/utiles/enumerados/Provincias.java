package escrituras.utiles.enumerados;

public enum Provincias {

	Alava(1, "ALAVA") {
		public String toString() {
			return "1";
		}
	},
	Albacete(2, "ALBACETE") {
		public String toString() {
			return "2";
		}
	},
	Alicante(3, "ALICANTE") {
		public String toString() {
			return "3";
		}
	},
	Almeria(4, "ALMERIA") {
		public String toString() {
			return "4";
		}
	},
	Avila(5, "AVILA") {
		public String toString() {
			return "5";
		}
	},
	Badajoz(6, "BADAJOZ") {
		public String toString() {
			return "6";
		}
	},
	Baleares(7, "BALEARES") {
		public String toString() {
			return "7";
		}
	},
	Barcelona(8, "BARCELONA") {
		public String toString() {
			return "8";
		}
	},
	Burgos(9, "BURGOS") {
		public String toString() {
			return "9";
		}
	},
	Caceres(10, "CACERES") {
		public String toString() {
			return "10";
		}
	},
	Cadiz(11, "CADIZ") {
		public String toString() {
			return "11";
		}
	},
	Castellon(12, "CASTELLON") {
		public String toString() {
			return "12";
		}
	},
	Ciudad_Real(13, "CIUDAD REAL") {
		public String toString() {
			return "13";
		}
	},
	Cordoba(14, "CORDOBA") {
		public String toString() {
			return "14";
		}
	},
	La_Corunya(15, "A CORUÑA") {
		public String toString() {
			return "15";
		}
	},
	Cuenca(16, "CUENCA") {
		public String toString() {
			return "16";
		}
	},
	Girona(17, "GIRONA") {
		public String toString() {
			return "17";
		}
	},
	Granada(18, "GRANADA") {
		public String toString() {
			return "18";
		}
	},
	Guadalajara(19, "GUADALAJARA") {
		public String toString() {
			return "19";
		}
	},
	Guipuzcoa(20, "GUIPUZCOA") {
		public String toString() {
			return "20";
		}
	},
	Huelva(21, "HUELVA") {
		public String toString() {
			return "21";
		}
	},
	Huesca(22, "HUESCA") {
		public String toString() {
			return "10";
		}
	},
	Jaen(23, "JAEN") {
		public String toString() {
			return "11";
		}
	},
	Leon(24, "LEON") {
		public String toString() {
			return "24";
		}
	},
	Lleida(25, "LLEIDA") {
		public String toString() {
			return "25";
		}
	},
	La_Rioja(26, "LA RIOJA") {
		public String toString() {
			return "26";
		}
	},
	Lugo(27, "LUGO") {
		public String toString() {
			return "27";
		}
	},
	Madrid(28, "MADRID") {
		public String toString() {
			return "28";
		}
	},
	Malaga(29, "MALAGA") {
		public String toString() {
			return "29";
		}
	},
	Murcia(30, "MURCIA") {
		public String toString() {
			return "30";
		}
	},
	Navarra(31, "NAVARRA") {
		public String toString() {
			return "31";
		}
	},
	Orense(32, "ORENSE") {
		public String toString() {
			return "32";
		}
	},
	Asturias(33, "ASTURIAS") {
		public String toString() {
			return "33";
		}
	},
	Palencia(34, "PALENCIA") {
		public String toString() {
			return "34";
		}
	},
	Las_Palmas(35, "LAS PALMAS") {
		public String toString() {
			return "35";
		}
	},
	Pontevedra(36, "PONTEVEDRA") {
		public String toString() {
			return "36";
		}
	},
	Salamanca(37, "SALAMANCA") {
		public String toString() {
			return "37";
		}
	},
	Tenerife(38, "TENERIFE") {
		public String toString() {
			return "38";
		}
	},
	Cantabria(39, "CANTABRIA") {
		public String toString() {
			return "39";
		}
	},
	Segovia(40, "SEGOVIA") {
		public String toString() {
			return "40";
		}
	},
	Sevilla(41, "SEVILLA") {
		public String toString() {
			return "41";
		}
	},
	Soria(42, "SORIA") {
		public String toString() {
			return "42";
		}
	},
	Tarragona(43, "TARRAGONA") {
		public String toString() {
			return "19";
		}
	},
	Teruel(44, "TERUEL") {
		public String toString() {
			return "44";
		}
	},
	Toledo(45, "TOLEDO") {
		public String toString() {
			return "45";
		}
	},
	Valencia(46, "VALENCIA") {
		public String toString() {
			return "46";
		}
	},
	Valladolid(47, "VALLADOLID") {
		public String toString() {
			return "47";
		}
	},
	Vizcaya(48, "VIZCAYA") {
		public String toString() {
			return "48";
		}
	},
	Zamora(49, "ZAMORA") {
		public String toString() {
			return "49";
		}
	},
	Zaragoza(50, "ZARAGOZA") {
		public String toString() {
			return "50";
		}
	},
	Ceuta(51, "CEUTA") {
		public String toString() {
			return "51";
		}
	},
	Melilla(52, "MELILLA") {
		public String toString() {
			return "52";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private Provincias(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static Provincias convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return Alava;
			case 2:
				return Albacete;
			case 3:
				return Alicante;
			case 4:
				return Almeria;
			case 5:
				return Avila;
			case 6:
				return Badajoz;
			case 7:
				return Baleares;
			case 8:
				return Barcelona;
			case 9:
				return Burgos;
			case 10:
				return Caceres;
			case 11:
				return Cadiz;
			case 12:
				return Castellon;
			case 13:
				return Ciudad_Real;
			case 14:
				return Cordoba;
			case 15:
				return La_Corunya;
			case 16:
				return Cuenca;
			case 17:
				return Girona;
			case 18:
				return Granada;
			case 19:
				return Guadalajara;
			case 20:
				return Guipuzcoa;
			case 21:
				return Huelva;
			case 22:
				return Huesca;
			case 23:
				return Jaen;
			case 24:
				return Leon;
			case 25:
				return Lleida;
			case 26:
				return La_Rioja;
			case 27:
				return Lugo;
			case 28:
				return Madrid;
			case 29:
				return Malaga;
			case 30:
				return Murcia;
			case 31:
				return Navarra;
			case 32:
				return Orense;
			case 33:
				return Asturias;
			case 34:
				return Palencia;
			case 35:
				return Las_Palmas;
			case 36:
				return Pontevedra;
			case 37:
				return Salamanca;
			case 38:
				return Tenerife;
			case 39:
				return Cantabria;
			case 40:
				return Segovia;
			case 41:
				return Sevilla;
			case 42:
				return Soria;
			case 43:
				return Tarragona;
			case 44:
				return Teruel;
			case 45:
				return Toledo;
			case 46:
				return Valencia;
			case 47:
				return Valladolid;
			case 48:
				return Vizcaya;
			case 49:
				return Zamora;
			case 50:
				return Zaragoza;
			case 51:
				return Ceuta;
			case 52:
				return Melilla;
			default:
				return null;
		}
	}

	public static Provincias convertir(String valorEnum) {
		if (valorEnum == null || valorEnum.equals("")) {
			return null;
		}

		Integer valor = Integer.parseInt(valorEnum);

		switch (valor) {
			case 1:
				return Alava;
			case 2:
				return Albacete;
			case 3:
				return Alicante;
			case 4:
				return Almeria;
			case 5:
				return Avila;
			case 6:
				return Badajoz;
			case 7:
				return Baleares;
			case 8:
				return Barcelona;
			case 9:
				return Burgos;
			case 10:
				return Caceres;
			case 11:
				return Cadiz;
			case 12:
				return Castellon;
			case 13:
				return Ciudad_Real;
			case 14:
				return Cordoba;
			case 15:
				return La_Corunya;
			case 16:
				return Cuenca;
			case 17:
				return Girona;
			case 18:
				return Granada;
			case 19:
				return Guadalajara;
			case 20:
				return Guipuzcoa;
			case 21:
				return Huelva;
			case 22:
				return Huesca;
			case 23:
				return Jaen;
			case 24:
				return Leon;
			case 25:
				return Lleida;
			case 26:
				return La_Rioja;
			case 27:
				return Lugo;
			case 28:
				return Madrid;
			case 29:
				return Malaga;
			case 30:
				return Murcia;
			case 31:
				return Navarra;
			case 32:
				return Orense;
			case 33:
				return Asturias;
			case 34:
				return Palencia;
			case 35:
				return Las_Palmas;
			case 36:
				return Pontevedra;
			case 37:
				return Salamanca;
			case 38:
				return Tenerife;
			case 39:
				return Cantabria;
			case 40:
				return Segovia;
			case 41:
				return Sevilla;
			case 42:
				return Soria;
			case 43:
				return Tarragona;
			case 44:
				return Teruel;
			case 45:
				return Toledo;
			case 46:
				return Valencia;
			case 47:
				return Valladolid;
			case 48:
				return Vizcaya;
			case 49:
				return Zamora;
			case 50:
				return Zaragoza;
			case 51:
				return Ceuta;
			case 52:
				return Melilla;
			default:
				return null;
		}
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == 1) {
			return "ALAVA";
		}
		if (valorEnum == 2) {
			return "ALBACETE";
		}
		if (valorEnum == 3) {
			return "ALICANTE";
		}
		if (valorEnum == 4) {
			return "ALMERIA";
		}
		if (valorEnum == 5) {
			return "AVILA";
		}
		if (valorEnum == 6) {
			return "BADAJOZ";
		}
		if (valorEnum == 7) {
			return "BALEARES";
		}
		if (valorEnum == 8) {
			return "BARCELONA";
		}
		if (valorEnum == 9) {
			return "BURGOS";
		}
		if (valorEnum == 10) {
			return "CACERES";
		}
		if (valorEnum == 11) {
			return "CADIZ";
		}
		if (valorEnum == 12) {
			return "CASTELLON";
		}
		if (valorEnum == 13) {
			return "CIUDAD REAL";
		}
		if (valorEnum == 14) {
			return "CORDOBA";
		}
		if (valorEnum == 15) {
			return "A CORUÑA";
		}
		if (valorEnum == 16) {
			return "CUENCA";
		}
		if (valorEnum == 17) {
			return "GIRONA";
		}
		if (valorEnum == 18) {
			return "GRANADA";
		}
		if (valorEnum == 19) {
			return "GUADALAJARA";
		}
		if (valorEnum == 20) {
			return "GUIPUZCOA";
		}
		if (valorEnum == 21) {
			return "HUELVA";
		}
		if (valorEnum == 22) {
			return "HUESCA";
		}
		if (valorEnum == 23) {
			return "JAEN";
		}
		if (valorEnum == 24) {
			return "LEON";
		}
		if (valorEnum == 25) {
			return "LLEIDA";
		}
		if (valorEnum == 26) {
			return "LA RIOJA";
		}
		if (valorEnum == 27) {
			return "LUGO";
		}
		if (valorEnum == 28) {
			return "MADRID";
		}
		if (valorEnum == 29) {
			return "MALAGA";
		}
		if (valorEnum == 30) {
			return "MURCIA";
		}
		if (valorEnum == 31) {
			return "NAVARRA";
		}
		if (valorEnum == 32) {
			return "ORENSE";
		}
		if (valorEnum == 33) {
			return "ASTURIAS";
		}
		if (valorEnum == 34) {
			return "PALENCIA";
		}
		if (valorEnum == 35) {
			return "LAS PALMAS";
		}
		if (valorEnum == 36) {
			return "PONTEVEDRA";
		}
		if (valorEnum == 37) {
			return "SALAMANCA";
		}
		if (valorEnum == 38) {
			return "TENERIFE";
		}
		if (valorEnum == 39) {
			return "CANTABRIA";
		}
		if (valorEnum == 40) {
			return "SEGOVIA";
		}
		if (valorEnum == 41) {
			return "SEVILLA";
		}
		if (valorEnum == 42) {
			return "SORIA";
		}
		if (valorEnum == 43) {
			return "TARRAGONA";
		}
		if (valorEnum == 44) {
			return "TERUEL";
		}
		if (valorEnum == 45) {
			return "TOLEDO";
		}
		if (valorEnum == 46) {
			return "VALENCIA";
		}
		if (valorEnum == 47) {
			return "VALLADOLID";
		}
		if (valorEnum == 48) {
			return "VIZCAYA";
		}
		if (valorEnum == 49) {
			return "ZAMORA";
		}
		if (valorEnum == 50) {
			return "ZARAGOZA";
		}
		if (valorEnum == 51) {
			return "CEUTA";
		}
		if (valorEnum == 52) {
			return "MELILLA";
		}
		return null;
	}

	public static Integer convertirToValor(String valorEnum) {
		if (valorEnum == "ALAVA") {
			return 1;
		}
		if (valorEnum == "ALBACETE") {
			return 2;
		}
		if (valorEnum == "ALICANTE") {
			return 3;
		}
		if (valorEnum == "ALMERIA") {
			return 4;
		}
		if (valorEnum == "AVILA") {
			return 5;
		}
		if (valorEnum == "BADAJOZ") {
			return 6;
		}
		if (valorEnum == "BALEARES") {
			return 7;
		}
		if (valorEnum == "BARCELONA") {
			return 8;
		}
		if (valorEnum == "BURGOS") {
			return 9;
		}
		if (valorEnum == "CACERES") {
			return 10;
		}
		if (valorEnum == "CADIZ") {
			return 11;
		}
		if (valorEnum == "CASTELLON") {
			return 12;
		}
		if (valorEnum == "CIUDAD REAL") {
			return 13;
		}
		if (valorEnum == "CORDOBA") {
			return 14;
		}
		if (valorEnum == "A CORUÑA") {
			return 15;
		}
		if (valorEnum == "CUENCA") {
			return 16;
		}
		if (valorEnum == "GIRONA") {
			return 17;
		}
		if (valorEnum == "GRANADA") {
			return 18;
		}
		if (valorEnum == "GUADALAJARA") {
			return 19;
		}
		if (valorEnum == "GUIPUZCOA") {
			return 20;
		}
		if (valorEnum == "HUELVA") {
			return 21;
		}
		if (valorEnum == "HUESCA") {
			return 22;
		}
		if (valorEnum == "JAEN") {
			return 23;
		}
		if (valorEnum == "LEON") {
			return 24;
		}
		if (valorEnum == "LLEIDA") {
			return 25;
		}
		if (valorEnum == "LA RIOJA") {
			return 26;
		}
		if (valorEnum == "LUGO") {
			return 27;
		}
		if (valorEnum == "MADRID") {
			return 28;
		}
		if (valorEnum == "MALAGA") {
			return 29;
		}
		if (valorEnum == "MURCIA") {
			return 30;
		}
		if (valorEnum == "NAVARRA") {
			return 31;
		}
		if (valorEnum == "ORENSE") {
			return 32;
		}
		if (valorEnum == "ASTURIAS") {
			return 33;
		}
		if (valorEnum == "PALENCIA") {
			return 34;
		}
		if (valorEnum == "LAS PALMAS") {
			return 35;
		}
		if (valorEnum == "PONTEVEDRA") {
			return 36;
		}
		if (valorEnum == "SALAMANCA") {
			return 37;
		}
		if (valorEnum == "TENERIFE") {
			return 38;
		}
		if (valorEnum == "CANTABRIA") {
			return 39;
		}
		if (valorEnum == "SEGOVIA") {
			return 40;
		}
		if (valorEnum == "SEVILLA") {
			return 41;
		}
		if (valorEnum == "SORIA") {
			return 42;
		}
		if (valorEnum == "TARRAGONA") {
			return 43;
		}
		if (valorEnum == "TERUEL") {
			return 44;
		}
		if (valorEnum == "TOLEDO") {
			return 45;
		}
		if (valorEnum == "VALENCIA") {
			return 46;
		}
		if (valorEnum == "VALLADOLID") {
			return 47;
		}
		if (valorEnum == "VIZCAYA") {
			return 48;
		}
		if (valorEnum == "ZAMORA") {
			return 49;
		}
		if (valorEnum == "ZARAGOZA") {
			return 50;
		}
		if (valorEnum == "CEUTA") {
			return 51;
		}
		if (valorEnum == "MELILLA") {
			return 52;
		}
		return null;
	}

}