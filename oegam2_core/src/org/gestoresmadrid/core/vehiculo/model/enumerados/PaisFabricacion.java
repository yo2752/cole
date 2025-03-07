package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum PaisFabricacion {

	NACIONAL("0", "FABRICACIÓN NACIONAL") {
		public String toString() {
			return "0";
		}
	},
	IMPORTACION_UE("3", "IMPORTACIÓN PAISES COMUNITARIOS") {
		public String toString() {
			return "3";
		}
	},
	IMPORTACION_NO_UE("1", "IMPORTACIÓN PAISES NO COMUNITARIOS") {
		public String toString() {
			return "1";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private PaisFabricacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static PaisFabricacion getPaisFabricacion(String valorEnum) {
		if (valorEnum == null)
			return null;

		if (PaisFabricacion.IMPORTACION_UE.valorEnum.equals(valorEnum) || Fabricacion.IMPORTACION_UE.getValorEnum().equals(valorEnum) || valorEnum.equalsIgnoreCase("3"))
			return PaisFabricacion.IMPORTACION_UE;
		else if (PaisFabricacion.IMPORTACION_NO_UE.valorEnum.equals(valorEnum) || Fabricacion.IMPORTACION_NO_UE.getValorEnum().equals(valorEnum) || valorEnum.equalsIgnoreCase("1"))
			return PaisFabricacion.IMPORTACION_NO_UE;
		else if (PaisFabricacion.NACIONAL.valorEnum.equals(valorEnum) || Fabricacion.NACIONAL.getValorEnum().equals(valorEnum) || valorEnum.equalsIgnoreCase("0"))
			return PaisFabricacion.NACIONAL;
		else
			return null;
	}

	public static String convertirPaisFabricacionBeanEnumerado(String valorEnum) {
		if (valorEnum.equalsIgnoreCase(PaisFabricacion.NACIONAL.getValorEnum()))
			return "NACIONAL";
		else if (valorEnum.equalsIgnoreCase(PaisFabricacion.IMPORTACION_NO_UE.getValorEnum()))
			return "IMPORTADO";
		else if (valorEnum.equalsIgnoreCase(PaisFabricacion.IMPORTACION_UE.getValorEnum()))
			return "EEE";
		else
			return "";
	}

	public static LugarAdquisicion equivalenciaLugarAdquisicion(String idPaisFabricacion) {
		if (idPaisFabricacion == null) {
			return null;
		}
		if (idPaisFabricacion.equalsIgnoreCase(PaisFabricacion.NACIONAL.getValorEnum())) {
			return LugarAdquisicion.España;
		} else if (idPaisFabricacion.equalsIgnoreCase(PaisFabricacion.IMPORTACION_NO_UE.getValorEnum())) {
			return LugarAdquisicion.EstadoNoMiembroDeLaUE;
		} else if (idPaisFabricacion.equalsIgnoreCase(PaisFabricacion.IMPORTACION_UE.getValorEnum())) {
			return LugarAdquisicion.OtrosEstadosMiembrosDeLaUE;
		} else {
			return null;
		}
	}
}
