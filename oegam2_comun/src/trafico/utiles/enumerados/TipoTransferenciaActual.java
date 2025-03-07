package trafico.utiles.enumerados;

public enum TipoTransferenciaActual {

	tipo1("1", "Cambio Titularidad Completo o finalización tras una notificación") {
		public String toString() {
			return "1";
		}
	},
	tipo2("2", "Notificación de cambio de titularidad") {
		public String toString() {
			return "2";
		}
	},
	tipo3("3", "Interviene Compraventa") {
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTransferenciaActual(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	// valueOf
	public static TipoTransferenciaActual convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
		case 1:
			return tipo1;
		case 2:
			return tipo2;
		case 3:
			return tipo3;
		default:
			return null;
		}
	}

	// valueOf
	public static TipoTransferenciaActual convertir(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return tipo1;
		}
		if ("2".equals(valorEnum)) {
			return tipo2;
		}
		if ("3".equals(valorEnum)) {
			return tipo3;
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return "Cambio Titularidad Completo o finalización tras una notificación";
		}
		if ("2".equals(valorEnum)) {
			return "Notificación de cambio de titularidad";
		}
		if ("3".equals(valorEnum)) {
			return "Interviene Compraventa";
		}

		return null;
	}

}