package trafico.inteve;

/**
 * Encapsula todos los valores posibles para el motivo de la consulta de los informes INTEVE
 */
public enum MotivoConsultaInteve {

	POSIBLE_ADQUISICION("0", "Posible adquisición del vehículo") {
		public String toString() {
			return "Posible adquisición del vehículo";
		}
	},
	CERTIFICADO_NO_TITULARIDAD("1", "Certificado de no titularidad del vehículo para otras administraciones y entidades") {
		public String toString() {
			return "Certificado de no titularidad del vehículo para otras administraciones y entidades";
		}
	},
	VERIFICACION_DATOS_TECNICOS("2", "Verificación de datos técnicos y administrativos del vehículo") {
		public String toString() {
			return "Verificación de datos técnicos y administrativos del vehículo";
		}
	},
	INVESTIGACION_SINIESTRO_COLISION("3", "Investigación de vehículos implicados en siniestro o colisión") {
		public String toString() {
			return "Investigación de vehículos implicados en siniestro o colisión";
		}
	},
	ABANDONO_VIA_PUBLICA("4", "Abandono del vehículo en la vía pública") {
		public String toString() {
			return "Abandono del vehículo en la vía pública";
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