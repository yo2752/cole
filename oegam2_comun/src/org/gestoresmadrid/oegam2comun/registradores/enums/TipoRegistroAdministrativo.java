package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de categorías que se establecen para describir e identificar el bien
 */
public enum TipoRegistroAdministrativo {

	REGISTRO_MERCANTIL("1", "Registro Mercantil"){
		@Override
		public String toString() {
			return "Registro Mercantil";
		}
	},
	REGISTRO_DE_LA_PROPIEDAD("2", "Registro de la Propiedad"){
		@Override
		public String toString() {
			return "Registro de la Propiedad";
		}
	},
	REGISTRO_DE_BIENES_MUEBLES("3", "Registro de Bienes Muebles"){
		@Override
		public String toString() {
			return "Registro de Bienes Muebles";
		}
	},
	OFICINA_LIQUIDADORA("4", "Oficina Liquidadora"){
		@Override
		public String toString() {
			return "Oficina Liquidadora";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoRegistroAdministrativo(String valorEnum, String nombreEnum) {
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
		for (TipoRegistroAdministrativo element : TipoRegistroAdministrativo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

}
