package org.gestoresmadrid.core.model.enumerados;

public enum TipoTarjetaITV {

	A("A", "A") {
		public String toString() {
			return "1";
		}
	},
	AL("AL", "AL") {
		public String toString() {
			return "9";
		}
	},
	AR("AR", "AR") {
		public String toString() {
			return "10";
		}
	},
	AT("AT", "AT") {
		public String toString() {
			return "8";
		}
	},
	B("B", "B") {
		public String toString() {
			return "2";
		}
	},
	BL("BL", "BL") {
		public String toString() {
			return "3";
		}
	},
	BR("BR", "BR") {
		public String toString() {
			return "6";
		}
	},
	BT("BT", "BT") {
		public String toString() {
			return "7";
		}
	},
	C("C", "C") {
		public String toString() {
			return "4";
		}
	},
	D("D", "D") {
		public String toString() {
			return "5";
		}
	},
	DL("DL", "DL") {
		public String toString() {
			return "12";
		}
	},
	DR("DR", "DR") {
		public String toString() {
			return "13";
		}
	},
	DT("DT", "DT") {
		public String toString() {
			return "11";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTarjetaITV(String valorEnum, String nombreEnum) {
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
	public static TipoTarjetaITV convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;
		switch (valorEnum) {
			case 1:
				return A;
			case 2:
				return B;
			case 3:
				return BL;
			case 4:
				return C;
			case 5:
				return D;
			case 6:
				return BR;
			case 7:
				return BT;
			case 8:
				return AT;
			case 9:
				return AL;
			case 10:
				return AR;
			case 11:
				return DT;
			case 12:
				return DL;
			case 13:
				return DR;
			default:
				return null;
		}
	}

	public static TipoTarjetaITV convertir(String valorEnum) {
		for (TipoTarjetaITV element : TipoTarjetaITV.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
}