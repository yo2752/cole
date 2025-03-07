package trafico.inteve;

/**
 * Encapsula todos los valores posibles para el motivo de la consulta de los informes INTEVE
 */
public enum MotivoConsultaInteve {

	POSIBLE_ADQUISICION("0", "Posible adquisici�n del veh�culo") {
		public String toString() {
			return "Posible adquisici�n del veh�culo";
		}
	},
	CERTIFICADO_NO_TITULARIDAD("1", "Certificado de no titularidad del veh�culo para otras administraciones y entidades") {
		public String toString() {
			return "Certificado de no titularidad del veh�culo para otras administraciones y entidades";
		}
	},
	VERIFICACION_DATOS_TECNICOS("2", "Verificaci�n de datos t�cnicos y administrativos del veh�culo") {
		public String toString() {
			return "Verificaci�n de datos t�cnicos y administrativos del veh�culo";
		}
	},
	INVESTIGACION_SINIESTRO_COLISION("3", "Investigaci�n de veh�culos implicados en siniestro o colisi�n") {
		public String toString() {
			return "Investigaci�n de veh�culos implicados en siniestro o colisi�n";
		}
	},
	ABANDONO_VIA_PUBLICA("4", "Abandono del veh�culo en la v�a p�blica") {
		public String toString() {
			return "Abandono del veh�culo en la v�a p�blica";
		}
	};

	private String nombreEnum;
	private String valorEnum;

	private MotivoConsultaInteve(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static MotivoConsultaInteve recuperar(String valor) {
		try {
			int intValue = Integer.parseInt(valor);
			switch (intValue) {
				case 0:
					return POSIBLE_ADQUISICION;
				case 1:
					return CERTIFICADO_NO_TITULARIDAD;
				case 2:
					return VERIFICACION_DATOS_TECNICOS;
				case 3:
					return INVESTIGACION_SINIESTRO_COLISION;
				case 4:
					return ABANDONO_VIA_PUBLICA;
				default:
					return null;
			}
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	public static String convertirTexto(String valorEnum) {
		for (MotivoConsultaInteve element : MotivoConsultaInteve.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return "";
	}

	public static Boolean validarMotivo(String valor) {
		try {
			int intValue = Integer.parseInt(valor);
			switch (intValue) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
					return Boolean.TRUE;
				default:
					return Boolean.FALSE;
			}
		} catch (NumberFormatException ex) {
			return Boolean.FALSE;
		}
	}
}