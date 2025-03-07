package trafico.utiles.enumerados;

public enum ReduccionNoSujeccionOExencion05 {

	RE1("RE1", "RE1 - VEHICULOS FAMILIA NUMEROSA") {
		public String toString() {
			return "RE1";
		}
	},
	NS1("NS1", "NS1 - FUERZAS ARMADAS Y CUERPOS DE SEGURIDAD") {
		public String toString() {
			return "NS1";
		}
	},
	NS2("NS2", "NS2 - AMBULANCIAS") {
		public String toString() {
			return "NS2";
		}
	},

	ER1("ER1", "ER1 - AUTOTAXIS Y AUTOTURISMOS") {
		public String toString() {
			return "ER1";
		}
	},

	ER2("ER2", "ER2 - VEHICULOS DE ENSEÑANZA DE CONDUCTORES") {
		public String toString() {
			return "ER2";
		}
	},

	ER3("ER3", "ER3 - VEHICULOS DE ALQUILER") {
		public String toString() {
			return "ER3";
		}
	},

	ER4("ER4", "ER4 - VEHICULOS USO MINUSVALIDOS") {
		public String toString() {
			return "ER4";
		}
	},

	UNI("UNI", "UNI - VEHICULOS DE UNIVERSIDADES") {
		public String toString() {
			return "UNI";
		}
	},

	EC4("EC4", "EC4 - CIRCULACION: OTROS MEDIOS DE TRANSPORTE") {
		public String toString() {
			return "EC4";
		}
	};

	// valueOf
	public static ReduccionNoSujeccionOExencion05 convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if ("RE1".equals(valorEnum))
			return RE1;
		else if ("NS1".equals(valorEnum))
			return NS1;
		else if ("NS2".equals(valorEnum))
			return NS2;
		else if ("ER1".equals(valorEnum))
			return ER1;
		else if ("ER2".equals(valorEnum))
			return ER2;
		else if ("ER3".equals(valorEnum))
			return ER3;
		else if ("ER4".equals(valorEnum))
			return ER4;
		else if ("EC4".equals(valorEnum))
			return EC4;
		else if ("UNI".equals(valorEnum))
			return UNI;
		else
			return null;
	}

	private String valorEnum;
	private String nombreEnum;

	private ReduccionNoSujeccionOExencion05(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}