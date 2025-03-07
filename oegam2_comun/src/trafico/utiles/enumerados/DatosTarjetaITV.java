package trafico.utiles.enumerados;

public enum DatosTarjetaITV {

	PC("1", "Permiso de circulaci�n.") {
		public String toString() {
			return "1";
		}
	},

	TIT("2", "Tarjeta de inspecci�n t�cnica.") {
		public String toString() {
			return "2";
		}
	},

	BajaIMVTM("3", "Baja del Impuesto Municipal sobre Veh�culos de Tracci�n Mec�nica.") {
		public String toString() {
			return "3";
		}
	},

	JustDS("4", "Justificante de denuncia de sustracci�n.") {
		public String toString() {
			return "4";
		}
	},

	DocPV("5", "Documento acreditativo de la propiedad del veh�culo si se ha adquirido para el desguace.") {
		public String toString() {
			return "5";
		}
	},

	JustPagoIMVTM("6", "Justificante del pago del Impuesto Municipal sobre Veh�culos de Tracci�n Mec�nica.") {
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
			return "Permiso de circulaci�n.";
		}
		if ("1".equals(valorEnum)) {
			return "Tarjeta de inspecci�n t�cnica.";
		}
		if ("1".equals(valorEnum)) {
			return "Baja del Impuesto Municipal sobre Veh�culos de Tracci�n Mec�nica.";
		}
		if ("1".equals(valorEnum)) {
			return "Justificante de denuncia por sustracci�n.";
		}
		if ("1".equals(valorEnum)) {
			return "Documento acreditativo de la propiedad del veh�culo, si se ha adquirido para el desguace.";
		}
		if ("1".equals(valorEnum)) {
			return "Justificante del pago del Impuesto Municipal sobre Veh�culos de Tracci�n Mec�nica.";
		}
		return null;
	}
}
