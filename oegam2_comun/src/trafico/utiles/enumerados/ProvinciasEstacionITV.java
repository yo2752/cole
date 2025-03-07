package trafico.utiles.enumerados;

public enum ProvinciasEstacionITV {

	ALAVA("VI", "Álava") {
		public String toString() {
			return "Álava";
		}
	},
	ALBACETE("AB", "Albacete") {
		public String toString() {
			return "Albacete";
		}
	},
	ALICANTE("A", "Alicante") {
		public String toString() {
			return "Alicante";
		}
	},
	ALMERIA("AL", "Almería") {
		public String toString() {
			return "Almería";
		}
	},
	AVILA("AV", "Ávila") {
		public String toString() {
			return "Ávila";
		}
	},
	BADAJOZ("BA", "Badajoz") {
		public String toString() {
			return "Badajoz";
		}
	},
	BALEARES("IB", "Baleares") {
		public String toString() {
			return "Baleares";
		}
	},
	BARCELONA("B", "Barcelona") {
		public String toString() {
			return "Barcelona";
		}
	},
	BURGOS("BU", "Burgos") {
		public String toString() {
			return "Burgos";
		}
	},
	CACERES("CC", "Cáceres") {
		public String toString() {
			return "Cáceres";
		}
	},
	CADIZ("CA", "Cádiz") {
		public String toString() {
			return "Cádiz";
		}
	},
	CASTELLON("CS", "Castellón") {
		public String toString() {
			return "Castellón";
		}
	},
	CIUDAD_REAL("CR", "Ciudad Real") {
		public String toString() {
			return "Ciudad Real";
		}
	},
	CORDOBA("CO", "Córdoba") {
		public String toString() {
			return "Córdoba";
		}
	},
	LA_CORUNYA("C", "La Coruña") {
		public String toString() {
			return "La Coruña";
		}
	},
	CUENCA("CU", "Cuenca") {
		public String toString() {
			return "Cuenca";
		}
	},
	GIRONA("GI", "Girona") {
		public String toString() {
			return "Girona";
		}
	},
	GRANADA("GR", "Granada") {
		public String toString() {
			return "Granada";
		}
	},
	GUADALAJAR("GU", "Guadalajara") {
		public String toString() {
			return "Guadalajara";
		}
	},
	GUIPUZCOA("SS", "Guipuzcoa") {
		public String toString() {
			return "Guipuzcoa";
		}
	},
	HUELVA("H", "Huelva") {
		public String toString() {
			return "Huelva";
		}
	},
	HUESCA("HU", "Huesca") {
		public String toString() {
			return "Huesca";
		}
	},
	JAEN("J", "Jaen") {
		public String toString() {
			return "Jaen";
		}
	},
	LEON("LE", "León") {
		public String toString() {
			return "León";
		}
	},
	LLEIDA("L", "Lleida") {
		public String toString() {
			return "Lleida";
		}
	},
	LA_RIOJA("LO", "La Rioja") {
		public String toString() {
			return "La Rioja";
		}
	},
	LUGO("LU", "Lugo") {
		public String toString() {
			return "Lugo";
		}
	},
	MADRID("M", "Madrid") {
		public String toString() {
			return "Madrid";
		}
	},
	MALAGA("MA", "Málaga") {
		public String toString() {
			return "Málaga";
		}
	},
	MURCIA("MU", "Murcia") {
		public String toString() {
			return "Murcia";
		}
	},
	NAVARRA("NA", "Navarra") {
		public String toString() {
			return "Navarra";
		}
	},
	ORENSE("OU", "Orense") {
		public String toString() {
			return "Orense";
		}
	},
	ASTURIAS("O", "Asturias") {
		public String toString() {
			return "Asturias";
		}
	},
	PALENCIA("P", "Palencia") {
		public String toString() {
			return "Palencia";
		}
	},
	LAS_PALMAS("GC", "Las Palmas") {
		public String toString() {
			return "Las Palmas";
		}
	},
	PONTEVEDRA("PO", "Pontevedra") {
		public String toString() {
			return "Pontevedra";
		}
	},
	SALAMANCA("SA", "Salamanca") {
		public String toString() {
			return "Salamanca";
		}
	},
	TENERIFE("TF", "Tenerife") {
		public String toString() {
			return "Tenerife";
		}
	},
	CANTABRIA("S", "Cantabria") {
		public String toString() {
			return "Cantabria";
		}
	},
	SEGOVIA("SG", "Segovia") {
		public String toString() {
			return "Segovia";
		}
	},
	SEVILLA("SE", "Sevilla") {
		public String toString() {
			return "Sevilla";
		}
	},
	SORIA("SO", "Soria") {
		public String toString() {
			return "Soria";
		}
	},
	TARRAGONA("T", "Tarragona") {
		public String toString() {
			return "Tarragona";
		}
	},
	TERUEL("TE", "Teruel") {
		public String toString() {
			return "Teruel";
		}
	},
	TOLEDO("TO", "Toledo") {
		public String toString() {
			return "Toledo";
		}
	},
	VALENCIA("V", "Valencia") {
		public String toString() {
			return "Valencia";
		}
	},
	VALLADOLID("VA", "Valladolid") {
		public String toString() {
			return "Valladolid";
		}
	},
	VIZCAYA("BI", "Vizcaya") {
		public String toString() {
			return "Vizcaya";
		}
	},
	ZAMORA("ZA", "Zamora") {
		public String toString() {
			return "Zamora";
		}
	},
	ZARAGOZA("Z", "Zaragoza") {
		public String toString() {
			return "Zaragoza";
		}
	},
	CEUTA("CE", "Ceuta") {
		public String toString() {
			return "Ceuta";
		}
	},
	MELILLA("ML", "Melilla") {
		public String toString() {
			return "Melilla";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ProvinciasEstacionITV(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String getIdProvincia(String valorEnum) {

		if (valorEnum == null)
			return null;

		if ("VI".equals(valorEnum))
			return "01";

		if ("AB".equals(valorEnum))
			return "02";

		if ("A".equals(valorEnum))
			return "03";

		if ("AL".equals(valorEnum))
			return "04";

		if ("AV".equals(valorEnum))
			return "05";

		if ("BA".equals(valorEnum))
			return "06";

		if ("IB".equals(valorEnum))
			return "07";

		if ("B".equals(valorEnum))
			return "08";

		if ("BU".equals(valorEnum))
			return "09";

		if ("CC".equals(valorEnum))
			return "10";

		if ("CA".equals(valorEnum))
			return "11";

		if ("CS".equals(valorEnum))
			return "12";

		if ("CR".equals(valorEnum))
			return "13";

		if ("CO".equals(valorEnum))
			return "14";

		if ("C".equals(valorEnum))
			return "15";

		if ("CU".equals(valorEnum))
			return "16";

		if ("GI".equals(valorEnum))
			return "17";

		if ("GR".equals(valorEnum))
			return "18";

		if ("GU".equals(valorEnum))
			return "19";

		if ("SS".equals(valorEnum))
			return "20";

		if ("H".equals(valorEnum))
			return "21";

		if ("HU".equals(valorEnum))
			return "22";

		if ("J".equals(valorEnum))
			return "23";

		if ("LE".equals(valorEnum))
			return "24";

		if ("L".equals(valorEnum))
			return "25";

		if ("LO".equals(valorEnum))
			return "26";

		if ("LU".equals(valorEnum))
			return "27";

		if ("M".equals(valorEnum))
			return "28";

		if ("MA".equals(valorEnum))
			return "29";

		if ("MU".equals(valorEnum))
			return "30";

		if ("NA".equals(valorEnum))
			return "31";

		if ("OU".equals(valorEnum))
			return "32";

		if ("O".equals(valorEnum))
			return "33";

		if ("P".equals(valorEnum))
			return "34";

		if ("GC".equals(valorEnum))
			return "35";

		if ("PO".equals(valorEnum))
			return "36";

		if ("SA".equals(valorEnum))
			return "37";

		if ("TF".equals(valorEnum))
			return "38";

		if ("S".equals(valorEnum))
			return "39";

		if ("SG".equals(valorEnum))
			return "40";

		if ("SE".equals(valorEnum))
			return "41";

		if ("SO".equals(valorEnum))
			return "42";

		if ("T".equals(valorEnum))
			return "43";

		if ("TE".equals(valorEnum))
			return "44";

		if ("TO".equals(valorEnum))
			return "45";

		if ("V".equals(valorEnum))
			return "46";

		if ("VA".equals(valorEnum))
			return "47";

		if ("BI".equals(valorEnum))
			return "48";

		if ("ZA".equals(valorEnum))
			return "49";

		if ("Z".equals(valorEnum))
			return "50";

		if ("CE".equals(valorEnum))
			return "51";

		if ("ML".equals(valorEnum))
			return "52";
		else
			return null;
	}

}