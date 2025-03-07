package org.gestoresmadrid.core.model.enumerados;

public enum MotivoDuplicado {

	CambD("1", "Cambio Domicilio") {
		public String toString() {
			return "1";
		}
	},
	CambDCond("2", "Cambio Domicilio Conductor") {
		public String toString() {
			return "2";
		}
	},
	Deter("3", "Deterioro") {
		public String toString() {
			return "3";
		}
	},
	Extrv("4", "Extravio") {
		public String toString() {
			return "4";
		}
	},
	Sustr("5", "Sustracción") {
		public String toString() {
			return "5";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private MotivoDuplicado(String valorEnum, String nombreEnum) {
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
	public static MotivoDuplicado convertir(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return CambD;
		}
		if ("2".equals(valorEnum)) {
			return CambDCond;
		}
		if ("3".equals(valorEnum)) {
			return Deter;
		}
		if ("4".equals(valorEnum)) {
			return Extrv;
		}
		if ("5".equals(valorEnum)) {
			return Sustr;
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return "Cambio Domicilio";
		}
		if ("2".equals(valorEnum)) {
			return "Cambio Domicilio Conductor";
		}
		if ("3".equals(valorEnum)) {
			return "Deterioro.";
		}
		if ("4".equals(valorEnum)) {
			return "Extravío.";
		}
		if ("5".equals(valorEnum)) {
			return "Sustracción.";
		}
		return null;
	}

	public static String obtenerValorEnum(String descEnum) {
		if (MotivoDuplicado.CambD.getNombreEnum().toUpperCase().equals(descEnum.toUpperCase())) {
			return MotivoDuplicado.CambD.getValorEnum();
		} else if (MotivoDuplicado.CambDCond.getNombreEnum().toUpperCase().equals(descEnum.toUpperCase())) {
			return MotivoDuplicado.CambDCond.getValorEnum();
		} else if (MotivoDuplicado.Deter.getNombreEnum().toUpperCase().equals(descEnum.toUpperCase())) {
			return MotivoDuplicado.Deter.getValorEnum();
		} else if (MotivoDuplicado.Extrv.getNombreEnum().toUpperCase().equals(descEnum.toUpperCase())) {
			return MotivoDuplicado.Extrv.getValorEnum();
		} else if (MotivoDuplicado.Sustr.getNombreEnum().toUpperCase().equals(descEnum.toUpperCase())) {
			return MotivoDuplicado.Sustr.getValorEnum();
		}
		return null;
	}

}