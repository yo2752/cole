package trafico.utiles.enumerados;

import trafico.beans.FabricacionBean;

public enum Fabricacion {

	NACIONAL("NAC", "FABRICACI�N NACIONAL") {
		public String toString() {
			return "NACIONAL";
		}
	},
	IMPORTACION_UE("EEE", "IMPORTACI�N PAISES COMUNITARIOS") {
		public String toString() {
			return "EEE";
		}
	},
	IMPORTACION_NO_UE("IM", "IMPORTACI�N PAISES NO COMUNITARIOS") {
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

	/*
	 * Este convertir debe tener en cuenta los valores de la antigua Procedencia por
	 * si se recuperan tr�mites de antes del cambio de Mate 2.5 0 Fabricaci�n
	 * Nacional 1 Importaci�n paises fuera de la U.E. 2 Subasta 3 Importaci�n paises
	 * de la U.E. Como subasta ha desaparecido devuelve null
	 */
	public static Fabricacion convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if (Fabricacion.IMPORTACION_UE.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("3")
				|| valorEnum.equals(PaisFabricacion.IMPORTACION_UE.getValorEnum()))
			return Fabricacion.IMPORTACION_UE;
		else if (Fabricacion.IMPORTACION_NO_UE.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("1")
				|| valorEnum.equals(PaisFabricacion.IMPORTACION_NO_UE.getValorEnum()))
			return Fabricacion.IMPORTACION_NO_UE;
		else if (Fabricacion.NACIONAL.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("0")
				|| valorEnum.equals(PaisFabricacion.NACIONAL.getValorEnum()))
			return Fabricacion.NACIONAL;
		else
			return null;

	}

	/*
	 * Este convertir debe tener en cuenta los valores de la antigua Procedencia por
	 * si se recuperan tr�mites de antes del cambio de Mate 2.5 0 Fabricaci�n
	 * Nacional 1 Importaci�n paises fuera de la U.E. 2 Subasta 3 Importaci�n paises
	 * de la U.E. Como subasta ha desaparecido devuelve null
	 */
	public static FabricacionBean convertirFabricacionBean(String valorEnum) {

		if (valorEnum == null)
			return new FabricacionBean();

		if (Fabricacion.IMPORTACION_UE.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("3"))
			return new FabricacionBean(Fabricacion.IMPORTACION_UE.getValorEnum(),
					Fabricacion.IMPORTACION_UE.getNombreEnum());
		else if (Fabricacion.IMPORTACION_NO_UE.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("1"))
			return new FabricacionBean(Fabricacion.IMPORTACION_NO_UE.getValorEnum(),
					Fabricacion.IMPORTACION_NO_UE.getNombreEnum());
		else if (Fabricacion.NACIONAL.valorEnum.equals(valorEnum) || valorEnum.equalsIgnoreCase("0"))
			return new FabricacionBean(Fabricacion.NACIONAL.getValorEnum(), Fabricacion.NACIONAL.getNombreEnum());
		else
			return null;

	}

	/**
	 * Devuelve la equivalencia de fabricaci�n con lugar de adquisici�n
	 * 
	 * @param idPaisFabricacion
	 * @return LugarAdquisicion equivalente
	 */
	public static LugarAdquisicion equivalenciaLugarAdquisicion(String idFabricacion) {

		if (idFabricacion == null) {
			return null;
		}
		if (idFabricacion.equalsIgnoreCase(Fabricacion.NACIONAL.getValorEnum())) {
			return LugarAdquisicion.Espa�a;
		} else if (idFabricacion.equalsIgnoreCase(Fabricacion.IMPORTACION_NO_UE.getValorEnum())) {
			return LugarAdquisicion.EstadoNoMiembroDeLaUE;
		} else if (idFabricacion.equalsIgnoreCase(Fabricacion.IMPORTACION_UE.getValorEnum())) {
			return LugarAdquisicion.OtrosEstadosMiembrosDeLaUE;
		} else {
			return null;
		}
	}

}