package trafico.utiles.enumerados;

public class MapeoTipoTransferencia {
	/*
	 * Mapeo de los tipos nuevos de CTI en los antiguos, para generación de XML y
	 * otros procesos que lo requieran
	 */
	public static String valorEnumTipoTransferencia(String valorEnum) {
		String valorAntiguo = null;
		// DRC@01-10-2012 Incidencia: 2493
		if (valorEnum != null) {
			if (valorEnum.equalsIgnoreCase("1") || valorEnum.equalsIgnoreCase("2") || valorEnum.equalsIgnoreCase("3")) {
				valorAntiguo = "1";
			} else if (valorEnum.equalsIgnoreCase("4")) {
				valorAntiguo = "2";
			} else if (valorEnum.equalsIgnoreCase("5")) {
				valorAntiguo = "3";
			}
		}
		return valorAntiguo;
	}

	public static String valorEnumTipoTransferenciaNotEnt(String valorEnum) {
		String valorAntiguo = null;

		if (valorEnum.equalsIgnoreCase("4")) {
			valorAntiguo = "2";
		} else if (valorEnum.equalsIgnoreCase("5")) {
			valorAntiguo = "3";
		}

		if (valorAntiguo == null) {// Cuando son trámites CTI, se devuelve el valor
			return valorEnum;
		}

		return valorAntiguo;
	}

}