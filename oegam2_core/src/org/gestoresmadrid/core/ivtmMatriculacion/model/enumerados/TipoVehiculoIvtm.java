package org.gestoresmadrid.core.ivtmMatriculacion.model.enumerados;

public enum TipoVehiculoIvtm {

	Turismo("TU") {
		public String toString() {
			return "TU";
		}
	},
	Camiones("CA") {
		public String toString() {
			return "CA";
		}
	},
	Autobuses("AU") {
		public String toString() {
			return "AU";
		}
	},
	Ciclomotores("CI") {
		public String toString() {
			return "CI";
		}
	},
	Motocicletas("MT") {
		public String toString() {
			return "MT";
		}
	},
	Remolques("RE") {
		public String toString() {
			return "RE";
		}
	},
	Tractores("TR") {
		public String toString() {
			return "TR";
		}
	},
	Otro("") {
		public String toString() {
			return "";
		}
	};

	private String valorEnum;

	private TipoVehiculoIvtm(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static TipoVehiculoIvtm obtenerTipoVehiculo(String tipo) {
		// CAMIONES/AUTOCARAVANAS
		if (tipo == null) {
			return Otro;
		}
		if ((tipo.startsWith("0") || tipo.startsWith("1")) || tipo.equals("20") || tipo.equals("21") || tipo.equals("24") || tipo.startsWith("7")) {
			return Camiones;
		}
		// TURISMOS/AUTOMOVILES 3 RUEDAS
		if (tipo.equals("22") || tipo.equals("23") || tipo.equals("25") || tipo.equals("40")) {
			return Turismo;
		}
		// AUTOBUSES/AUTOCARES
		if (tipo.startsWith("30")) {
			return Autobuses;
		}
		// MOTOCICLETAS
		if (tipo.startsWith("5")) {
			return Motocicletas;
		}
		// TRACTOR Y ESPECIALES
		if (tipo.startsWith("8")) {
			return Tractores;
		}
		// CICLOMOTORES/MOTOCICLETAS/MOTOCARRO/QUADS
		if (tipo.startsWith("9")) {
			return Ciclomotores;
		}
		// REMOLQUES Y SEMIRREMOLQUES
		if (tipo.startsWith("R") || tipo.startsWith("S")) {
			return Remolques;
		}
		return Otro;
	}

}
