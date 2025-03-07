package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum Combustible {

	Gasolina("G", "GASOLINA") {
		public String toString() {
			return "G";
		}
	},
	Diesel("D", "DIESEL") {
		public String toString() {
			return "D";
		}
	},
	Electrico("E", "ELÉCTRICO") {
		public String toString() {
			return "E";
		}
	},
	GasLicuadoPetroleo("GLP", "GAS LICUADO DE PETRÓLEO") {
		public String toString() {
			return "GLP";
		}
	},
	GasNaturalComprimido("GNC", "GAS NATURAL COMPRIMIDO") {
		public String toString() {
			return "GNC";
		}
	},
	GasNaturalLicuado("GNL", "GAS NATURAL LICUADO") {
		public String toString() {
			return "GNL";
		}
	},
	Hidrogeno("H", "HIDRÓGENO") {
		public String toString() {
			return "H";
		}
	},
	BioMetano("BM", "BIOMETANO") {
		public String toString() {
			return "BM";
		}
	},
	Etanol("ET", "ETANOL") {
		public String toString() {
			return "ET";
		}
	},
	BioDiesel("BD", "BIODIESEL") {
		public String toString() {
			return "BD";
		}
	},
	Gasolina_Electrico("G/E", "GASOLINA/ELÉCTRICO") {
		public String toString() {
			return "G/E";
		}
	},
	Gasolina_GasNaturalPetroleo("G/GLP", "GASOLINA/GAS LICUADO DE PETRÓLEO") {
		public String toString() {
			return "G/GLP";
		}
	},
	GasNaturalLicuado_Gasolina("GLP/G", "GAS LICUADO DE PETRÓLEO/GASOLINA") {
		public String toString() {
			return "GLP/G";
		}
	},
	Gasolina_GasNaturalComprimido("G/GNC", "GASOLINA/GAS NATURAL COMPRIMIDO") {
		public String toString() {
			return "G/GNC";
		}
	},
	Biodiesel_Diesel("BD/D", "BIODIESEL/DIESEL") {
		public String toString() {
			return "BD/D";
		}
	},
	Diesel_Biodiesel("D/BD", "DIESEL/BIODIESEL") {
		public String toString() {
			return "D/BD";
		}
	},
	Otros("OT", "OTROS") {
		public String toString() {
			return "OT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Combustible(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static Combustible convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return Gasolina;
			case 2:
				return Diesel;
			case 3:
				return Electrico;
			case 4:
				return GasLicuadoPetroleo;
			case 5:
				return GasNaturalLicuado;
			case 6:
				return GasNaturalComprimido;
			case 7:
				return Hidrogeno;
			case 8:
				return BioMetano;
			case 9:
				return Etanol;
			case 10:
				return BioDiesel;
			case 11:
				return Otros;
			default:
				return null;
		}
	}

	public static Combustible convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if ("G".equalsIgnoreCase(valorEnum) || "GA".equalsIgnoreCase(valorEnum))
			return Gasolina;
		else if ("D".equalsIgnoreCase(valorEnum) || "GO".equalsIgnoreCase(valorEnum))
			return Diesel;
		else if ("E".equalsIgnoreCase(valorEnum) || "EL".equalsIgnoreCase(valorEnum))
			return Electrico;
		else if ("GLP".equalsIgnoreCase(valorEnum))
			return GasLicuadoPetroleo;
		else if ("GNC".equalsIgnoreCase(valorEnum))
			return GasNaturalComprimido;
		else if ("GNL".equalsIgnoreCase(valorEnum))
			return GasNaturalLicuado;
		else if ("H".equalsIgnoreCase(valorEnum))
			return Hidrogeno;
		else if ("BM".equalsIgnoreCase(valorEnum))
			return BioMetano;
		else if ("ET".equalsIgnoreCase(valorEnum))
			return Etanol;
		else if ("BD".equalsIgnoreCase(valorEnum))
			return BioDiesel;
		else if ("G/E".equalsIgnoreCase(valorEnum))
			return Gasolina_Electrico;
		else if ("G/GLP".equalsIgnoreCase(valorEnum))
			return Combustible.Gasolina_GasNaturalPetroleo;
		else if ("GLP/G".equalsIgnoreCase(valorEnum))
			return Combustible.GasNaturalLicuado_Gasolina;
		else if ("G/GNC".equalsIgnoreCase(valorEnum))
			return Combustible.Gasolina_GasNaturalComprimido;
		else if ("BD/D".equalsIgnoreCase(valorEnum))
			return Combustible.Biodiesel_Diesel;
		else if ("D/BD".equalsIgnoreCase(valorEnum))
			return Combustible.Diesel_Biodiesel;
		else if ("OT".equalsIgnoreCase(valorEnum) || "BU".equalsIgnoreCase(valorEnum) || "SO".equalsIgnoreCase(valorEnum) || "O".equalsIgnoreCase(valorEnum) || "ST".equalsIgnoreCase(valorEnum))
			return Otros;
		else
			return null;
	}

	public static String convertirTexto(Integer valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if (valorEnum == 1) {
			return "GASOLINA";
		}
		if (valorEnum == 2) {
			return "DIESEL";
		}
		if (valorEnum == 3) {
			return "ELECTRICO";
		}
		if (valorEnum == 4) {
			return "GAS LICUADO PETROLEO";
		}
		if (valorEnum == 5) {
			return "GAS NATURAL COMPRIMIDO";
		}
		if (valorEnum == 6) {
			return "GAS NATURAL LICUADO";
		}
		if (valorEnum == 7) {
			return "HIDROGENO";
		}
		if (valorEnum == 8) {
			return "BIOMETANO";
		}
		if (valorEnum == 9) {
			return "ETANOL";
		}
		if (valorEnum == 10) {
			return "BIODIESEL";
		}
		if (valorEnum == 11) {
			return "OTROS";
		}
		return null;
	}

	public static String convertirAString(String codigoCombustible) {
		if (codigoCombustible == null)
			return null;
		if ("G".equalsIgnoreCase(codigoCombustible))
			return "GASOLINA";
		else if ("GA".equalsIgnoreCase(codigoCombustible))
			return "GASOLINA (Antigua Gasolina)";
		else if ("D".equalsIgnoreCase(codigoCombustible))
			return "DIESEL";
		else if ("GO".equalsIgnoreCase(codigoCombustible))
			return "DIESEL (Antiguo Gasoil)";
		else if ("E".equalsIgnoreCase(codigoCombustible))
			return "ELÉCTRICO";
		else if ("EL".equalsIgnoreCase(codigoCombustible))
			return "ELECTRICO (Antiguo Electrico)";
		else if ("GLP".equalsIgnoreCase(codigoCombustible))
			return "GAS LICUADO PETROLEO";
		else if ("GNC".equalsIgnoreCase(codigoCombustible))
			return "GAS NATURAL COMPRIMIDO";
		else if ("GNL".equalsIgnoreCase(codigoCombustible))
			return "GAS NATURAL LICUADO";
		else if ("H".equalsIgnoreCase(codigoCombustible))
			return "HIDROGENO";
		else if ("BM".equalsIgnoreCase(codigoCombustible))
			return "BIOMETANO";
		else if ("ET".equalsIgnoreCase(codigoCombustible))
			return "ETANOL";
		else if ("BD".equalsIgnoreCase(codigoCombustible))
			return "BIODIESEL";
		else if ("G/E".equalsIgnoreCase(codigoCombustible))
			return "GASOLINA/ELÉCTRICO";
		else if ("G/GLP".equalsIgnoreCase(codigoCombustible))
			return "GASOLINA/GAS LICUADO DE PETRÓLEO";
		else if ("GLP/G".equalsIgnoreCase(codigoCombustible))
			return "GAS LICUADO DE PETRÓLEO/GASOLINA";
		else if ("G/GNC".equalsIgnoreCase(codigoCombustible))
			return "GASOLINA / GAS NATURAL COMPRIMIDO";
		else if ("BD/D".equalsIgnoreCase(codigoCombustible))
			return "BIODIESEL / DIESEL";
		else if ("D/BD".equalsIgnoreCase(codigoCombustible))
			return "DIESEL / BIODIESEL";
		else if ("SIN ESPECIFICAR".equals(codigoCombustible))
			return "SIN ESPECIFICAR";
		else if ("O".equalsIgnoreCase(codigoCombustible) || "BU".equalsIgnoreCase(codigoCombustible) || "SO".equalsIgnoreCase(codigoCombustible) || "OT".equalsIgnoreCase(codigoCombustible) || "ST"
				.equalsIgnoreCase(codigoCombustible))
			return "OTROS";
		else
			return null;
	}
}
