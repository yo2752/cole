package trafico.utiles.enumerados;

public enum TiposInspeccionITV {

	PREVIA_MATRICULACION("M", "Previa matriculación") {
		public String toString() {
			return "Previa matriculación";
		}
	},
	EXENTO_ITV("E", "Exento ITV") {
		public String toString() {
			return "Exento ITV";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TiposInspeccionITV(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String getDescripcion(String valor) {
		if (valor == null) {
			return null;
		}

		if (valor.equals(TiposInspeccionITV.PREVIA_MATRICULACION.getValorEnum())) {
			return TiposInspeccionITV.PREVIA_MATRICULACION.getNombreEnum();
		}

		if (valor.equals(TiposInspeccionITV.EXENTO_ITV.getValorEnum())) {
			return TiposInspeccionITV.EXENTO_ITV.getNombreEnum();
		}

		return null;
	}
}