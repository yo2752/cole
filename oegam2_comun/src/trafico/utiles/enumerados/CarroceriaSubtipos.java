package trafico.utiles.enumerados;

public enum CarroceriaSubtipos {

	PLATAFORMA("01", "Plataforma") {
		public String toString() {
			return "01";
		}
	},
	CAJA_ABIERTA("02", "Caja abierta") {
		public String toString() {
			return "02";
		}
	},
	CAJA_CERRADA("03", "Caja cerrada") {
		public String toString() {
			return "03";
		}
	},
	CAJA_ACONDICIONADA_CON_PAREDES_AISLADAS_Y_SISTEMA_DE_REFRIGERACION("04",
			"Caja acondicionada con paredes aisladas y sistema de refrigeración") {
		public String toString() {
			return "04";
		}
	},
	CAJA_ACONDICIONADA_CON_PAREDES_AISLADAS_Y_SIN_SISTEMA_DE_REFRIGERACION("05",
			"Caja acondicionada con paredes aisladas y sin sistema de refrigeración") {
		public String toString() {
			return "05";
		}
	},
	CON_LONAS_LATERALES("06", "Caja lonas laterales") {
		public String toString() {
			return "06";
		}
	},
	DE_CAJA_MOVIL("07", "De caja móvil") {
		public String toString() {
			return "07";
		}
	},
	PORTA_CONTENEDORES("08", "Porta-contenedores") {
		public String toString() {
			return "08";
		}
	},
	CON_GRUA_DE_ELEVACION("09", "Con grúa de elevación") {
		public String toString() {
			return "09";
		}
	},
	VOLQUETE("10", "Volquete") {
		public String toString() {
			return "10";
		}
	},
	CISTERNA("11", "Cisterna") {
		public String toString() {
			return "11";
		}
	},
	CISTERNA_PREPARADA_PARA_ADR("12", "Cisterna preparada para ADR") {
		public String toString() {
			return "12";
		}
	},
	TRANSPORTE_DE_GANADO("13", "Transporte de ganado") {
		public String toString() {
			return "13";
		}
	},
	PORTAVEHICULOS("14", "Portavehículos") {
		public String toString() {
			return "14";
		}
	},
	HORMIGONERA("15", "Hormigonera") {
		public String toString() {
			return "15";
		}
	},
	CON_BOMBA_DE_HORMIGONAR("16", "Con bomba de hormigonar") {
		public String toString() {
			return "16";
		}
	},
	TRANSPORTE_DE_MADERA("17", "Transporte de madera") {
		public String toString() {
			return "17";
		}
	},
	BASURERO("18", "Basurero") {
		public String toString() {
			return "18";
		}
	},
	PARA_BARRER_LIMPIAR_Y_SECAR_LA_VIA_PUBLICA("19", "Para barrer, limpiar y secar la vía pública") {
		public String toString() {
			return "19";
		}
	},
	COMPRESOR("20", "Compresor") {
		public String toString() {
			return "20";
		}
	},
	PARA_TRANSPORTE_DE_EMBARCACIONES("21", "Para transporte de embarcaciones") {
		public String toString() {
			return "21";
		}
	},
	PARA_TRANSPORTE_DE_PLANEADORES("22", "Para transporte de planeadores") {
		public String toString() {
			return "22";
		}
	},
	PARA_EL_COMERCIO_AL_POR_MENOR_O_COMO_EXPOSITOR("23", "Para el comercio al por menor o como expositor") {
		public String toString() {
			return "23";
		}
	},
	DE_ASISTENCIA("24", "De asistencia") {
		public String toString() {
			return "24";
		}
	},
	CON_ESCALERA("25", "Con escalera") {
		public String toString() {
			return "25";
		}
	},
	CAMION_GRUA("26", "Camión grúa") {
		public String toString() {
			return "26";
		}
	},
	VEHICULO_CON_PLATAFORMA_AEREA("27", "Vehículo con plataforma aérea") {
		public String toString() {
			return "27";
		}
	},
	PERFORADORA("28", "Perforadora") {
		public String toString() {
			return "28";
		}
	},
	REMOLQUE_DE_PISO_BAJO("29", "Remolque de piso bajo") {
		public String toString() {
			return "29";
		}
	},
	TRANSPORTE_DE_CRISTALES("30", "Transporte de cristales") {
		public String toString() {
			return "30";
		}
	},
	DE_EXTINCION_DE_INCENDIOS("31", "De extinción de incendios") {
		public String toString() {
			return "31";
		}
	},
	LONA_LATERAL_ABATIBLE("32", "Caja lona lateral abatible") {
		public String toString() {
			return "32";
		}
	},
	NO_INCLUIDA("99", "Carrocería no incluida en las anteriores") {
		public String toString() {
			return "99";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private CarroceriaSubtipos(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}