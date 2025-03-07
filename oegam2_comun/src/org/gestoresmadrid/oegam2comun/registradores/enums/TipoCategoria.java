package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de categorías que se establecen para describir e identificar el bien
 */
public enum TipoCategoria {

	VEHICULOS_MOTOR("01", "Vehículos a motor"){
		@Override
		public String toString() {
			return "Vehículos a motor";
		}
	},
	MAQUINARIA_INDUSTRIAL("02", "Maquinaria industrial"){
		@Override
		public String toString() {
			return "Maquinaria industrial";
		}
	},
	ESTABLECIMIENTOS_MERCANTILES("03", "Establecimientos mercantiles"){
		@Override
		public String toString() {
			return "Establecimientos mercantiles";
		}
	},
	BUQUES("04", "Buques"){
		@Override
		public String toString() {
			return "Buques";
		}
	},
	AERONAVES("05", "Aeronaves"){
		@Override
		public String toString() {
			return "Aeronaves";
		}
	},
	PROPIEDAD_INDUSTRIAL("06", "Propiedad industrial (Patentes y Marcas)"){
		@Override
		public String toString() {
			return "Propiedad industrial (Patentes y Marcas)";
		}
	},
	PROPIEDAD_INTELECTUAL("07", "Propiedad intelectual"){
		@Override
		public String toString() {
			return "Propiedad intelectual";
		}
	},
	OTROS("08", "Otros bienes muebles registrables"){
		@Override
		public String toString() {
			return "Otros bienes muebles registrables";
		}
	}
	;

	private String valorEnum;
	private String nombreEnum;

	private TipoCategoria(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getKey(){
		return getValorEnum();
	}

	@Override
	public String toString(){
		return getNombreEnum();
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoCategoria element : TipoCategoria.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

}
