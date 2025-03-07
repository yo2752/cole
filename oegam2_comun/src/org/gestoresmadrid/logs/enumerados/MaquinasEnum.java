package org.gestoresmadrid.logs.enumerados;

public enum MaquinasEnum {

	FRONTAL_31("192.168.50.31", "Frontal 31") {
		public String toString() {
			return "Frontal 31";
		}
	},
	FRONTAL_32("192.168.50.32", "Frontal 32") {
		public String toString() {
			return "Frontal 32";
		}
	},
	FRONTAL_33("192.168.50.33", "Frontal 33") {
		public String toString() {
			return "Frontal 33";
		}
	},
	FRONTAL_34("192.168.50.34", "Frontal 34") {
		public String toString() {
			return "Frontal 34";
		}
	},
	FRONTAL_16("192.168.50.16", "Frontal 16") {
		public String toString() {
			return "Frontal 16";
		}
	},
	FRONTAL_71("192.168.50.71", "Frontal 71") {
		public String toString() {
			return "Frontal 71";
		}
	},
	PROCESOS("192.168.50.46", "Procesos") {
		public String toString() {
			return "Procesos";
		}
	};

	private MaquinasEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static MaquinasEnum recuperar(String valorEnum) {
		if (valorEnum.equals("Frontal 31")) {
			return MaquinasEnum.FRONTAL_31;
		} else if (valorEnum.equals("Frontal 32")) {
			return MaquinasEnum.FRONTAL_32;
		} else if (valorEnum.equals("Frontal 33")) {
			return MaquinasEnum.FRONTAL_33;
		} else if (valorEnum.equals("Frontal 34")) {
			return MaquinasEnum.FRONTAL_34;
		} else if (valorEnum.equals("Frontal 71")) {
			return MaquinasEnum.FRONTAL_71;
		} else {
			return null;
		}
	}
}
