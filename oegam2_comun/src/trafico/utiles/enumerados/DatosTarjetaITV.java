package trafico.utiles.enumerados;

public enum DatosTarjetaITV {

	PC("1", "Permiso de circulación.") {
		public String toString() {
			return "1";
		}
	},

	TIT("2", "Tarjeta de inspección técnica.") {
		public String toString() {
			return "2";
		}
	},

	BajaIMVTM("3", "Baja del Impuesto Municipal sobre Vehículos de Tracción Mecánica.") {
		public String toString() {
			return "3";
		}
	},

	JustDS("4", "Justificante de denuncia de sustracción.") {
		public String toString() {
			return "4";
		}
	},

	DocPV("5", "Documento acreditativo de la propiedad del vehículo si se ha adquirido para el desguace.") {
		public String toString() {
			return "5";
		}
	},

	JustPagoIMVTM("6", "Justificante del pago del Impuesto Municipal sobre Vehículos de Tracción Mecánica.") {
		public String toString() {
			return "6";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private DatosTarjetaITV(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static DatosTarjetaITV convertir(String valorEnum) {

		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return PC;
		}
		if ("1".equals(valorEnum)) {
			return TIT;
		}
		if ("1".equals(valorEnum)) {
			return BajaIMVTM;
		}
		if ("1".equals(valorEnum)) {
			return JustDS;
		}
		if ("1".equals(valorEnum)) {
			return DocPV;
		}
		if ("1".equals(valorEnum)) {
			return JustPagoIMVTM;
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {

		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return "Permiso de circulación.";
		}
		if ("1".equals(valorEnum)) {
			return "Tarjeta de inspección técnica.";
		}
		if ("1".equals(valorEnum)) {
			return "Baja del Impuesto Municipal sobre Vehículos de Tracción Mecánica.";
		}
		if ("1".equals(valorEnum)) {
			return "Justificante de denuncia por sustracción.";
		}
		if ("1".equals(valorEnum)) {
			return "Documento acreditativo de la propiedad del vehículo, si se ha adquirido para el desguace.";
		}
		if ("1".equals(valorEnum)) {
			return "Justificante del pago del Impuesto Municipal sobre Vehículos de Tracción Mecánica.";
		}
		return null;
	}
}
