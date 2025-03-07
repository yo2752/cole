package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum Fabricacion {

	NACIONAL("NAC", "FABRICACIÓN NACIONAL") {
		public String toString() {
			return "NACIONAL";
		}
	},
	IMPORTACION_UE("EEE", "IMPORTACIÓN PAISES COMUNITARIOS") {
		public String toString() {
			return "EEE";
		}
	},
	IMPORTACION_NO_UE("IM", "IMPORTACIÓN PAISES NO COMUNITARIOS") {
		public String toString() {
			return "IM";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Fabricacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Fabricacion convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if (Fabricacion.IMPORTACION_UE.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("3") || valorEnum.equals(PaisFabricacion.IMPORTACION_UE.getValorEnum()))
			return Fabricacion.IMPORTACION_UE;
		else if (Fabricacion.IMPORTACION_NO_UE.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("1") || valorEnum.equals(PaisFabricacion.IMPORTACION_NO_UE.getValorEnum()))
			return Fabricacion.IMPORTACION_NO_UE;
		else if (Fabricacion.NACIONAL.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("0") || valorEnum.equals(PaisFabricacion.NACIONAL.getValorEnum()))
			return Fabricacion.NACIONAL;
		else
			return null;
	}

	public static LugarAdquisicion equivalenciaLugarAdquisicion(String idFabricacion) {
		if (idFabricacion == null) {
			return null;
		}
		if (idFabricacion.equalsIgnoreCase(Fabricacion.NACIONAL.getValorEnum())) {
			return LugarAdquisicion.España;
		} else if (idFabricacion.equalsIgnoreCase(Fabricacion.IMPORTACION_NO_UE.getValorEnum())) {
			return LugarAdquisicion.EstadoNoMiembroDeLaUE;
		} else if (idFabricacion.equalsIgnoreCase(Fabricacion.IMPORTACION_UE.getValorEnum())) {
			return LugarAdquisicion.OtrosEstadosMiembrosDeLaUE;
		} else {
			return null;
		}
	}
}
