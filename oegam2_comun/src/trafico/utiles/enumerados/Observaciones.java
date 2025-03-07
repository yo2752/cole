package trafico.utiles.enumerados;

public enum Observaciones {

	VehiculoTipoQuad("01", "Vehículo tipo quad") {
		public String toString() {
			return "01";
		}
	},

	VehiculoTodoTerreno("02", "Vehículo tipo todo terreno") {
		public String toString() {
			return "02";
		}
	},

	VehiculoDestinadoAVivienda("03", "Vehículo destinado a vivienda") {
		public String toString() {
			return "03";
		}
	},

	MotocicletasPotenciaMayor("04",
			"Motocicletas con potencia CEE mayor o igual a 74 KW (100 CV) y relación Potencia/Masa menor a 0.66") {
		public String toString() {
			return "04";
		}
	},

	MotocicletasPotenciaMenor("05", "Motocicletas con potencia CEE menor a 74 KW (100 CV)") {
		public String toString() {
			return "05";
		}
	},

	MotocicletasPotenciaMayor2("06",
			"Motocicletas con potencia CEE mayor o igual a 74 KW (100 CV) y relación Potencia/Masa mayor o igual a 0.66") {
		public String toString() {
			return "06";
		}
	},
	// 0026551
	Vehiculo3RuedasL5e("07", "Vehículo de 3 ruedas con clasificación europea L5E") {
		public String toString() {
			return "07";
		}
	},

	RestoVehiculos("00", "Resto de vehículos") {
		public String toString() {
			return "00";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Observaciones(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Observaciones convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if ("00".equals(valorEnum) || "0".equals(valorEnum))
			return RestoVehiculos;
		if ("01".equals(valorEnum) || "1".equals(valorEnum))
			return VehiculoTipoQuad;
		if ("02".equals(valorEnum) || "2".equals(valorEnum))
			return VehiculoTodoTerreno;
		if ("03".equals(valorEnum) || "3".equals(valorEnum))
			return VehiculoDestinadoAVivienda;
		if ("04".equals(valorEnum) || "4".equals(valorEnum))
			return MotocicletasPotenciaMayor;
		if ("05".equals(valorEnum) || "5".equals(valorEnum))
			return MotocicletasPotenciaMenor;
		if ("06".equals(valorEnum) || "6".equals(valorEnum))
			return MotocicletasPotenciaMayor2;
		if ("07".equals(valorEnum) || "7".equals(valorEnum))
			return Vehiculo3RuedasL5e;
		else {
			return null;
		}
	}

	public static String valorExportacion(Observaciones observaciones) {
		if (observaciones == null) {
			return null;
		}
		if (observaciones.getValorEnum().equals(Observaciones.RestoVehiculos.getValorEnum())) {
			return "0";
		}
		if (observaciones.getValorEnum().equals(Observaciones.VehiculoTipoQuad.getValorEnum())) {
			return "1";
		}
		if (observaciones.getValorEnum().equals(Observaciones.VehiculoTodoTerreno.getValorEnum())) {
			return "2";
		}
		if (observaciones.getValorEnum().equals(Observaciones.VehiculoDestinadoAVivienda.getValorEnum())) {
			return "3";
		}
		if (observaciones.getValorEnum().equals(Observaciones.MotocicletasPotenciaMayor.getValorEnum())) {
			return "4";
		}
		if (observaciones.getValorEnum().equals(Observaciones.MotocicletasPotenciaMenor.getValorEnum())) {
			return "5";
		}
		if (observaciones.getValorEnum().equals(Observaciones.MotocicletasPotenciaMayor2.getValorEnum())) {
			return "6";
		}
		if (observaciones.getValorEnum().equals(Observaciones.Vehiculo3RuedasL5e.getValorEnum())) {
			return "7";
		}
		return null;
	}

}
