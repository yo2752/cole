package trafico.utiles;

public class CalculaMMAServicio {

	private static final String[] TIPOS_VEHICULO_TARA = {"50","51","53","54","91","92","R","S"};

	/**
	 * Calcula la Masa de Servicio dado el valor de la Tara y el tipo de vehículo.
	 * @param taraString
	 * @param tipoVehiculo
	 * @return
	 */

	public static String calcular(String taraString, String tipoVehiculo) {
		if (tipoVehiculo != null) {
			try {
				int tara = Integer.parseInt(taraString);
				boolean seSuma = true;
				for (String tipo : TIPOS_VEHICULO_TARA) {
					if (tipo.startsWith(tipoVehiculo)) {
						seSuma = false;
						break;
					}
				}
				if (seSuma) {
					tara+=75;
				}
				return Integer.toString(tara);
			} catch(Exception e) {
				return taraString;
			}
		} else {
			return taraString;
		}
	}

}