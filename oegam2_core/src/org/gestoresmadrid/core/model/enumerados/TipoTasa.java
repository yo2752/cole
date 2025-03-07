package org.gestoresmadrid.core.model.enumerados;

public enum TipoTasa {

	UnoUno("1.1", "1.1") {
		public String toString() {
			return "1.1";
		}
	},
	UnoDos("1.2", "1.2") {
		public String toString() {
			return "1.2";
		}
	},
	UnoCinco("1.5", "1.5") {
		public String toString() {
			return "1.5";
		}
	},
	UnoSeis("1.6", "1.6") {
		public String toString() {
			return "1.6";
		}
	},

	CuatroUno("4.1", "4.1") {
		public String toString() {
			return "4.1";
		}
	},
	CuatroCuatro("4.4", "4.4") {
		public String toString() {
			return "4.4";
		}
	},
	CuatroCinco("4.5", "4.5") {
		public String toString() {
			return "4.5";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTasa(String valorEnum, String nombreEnum) {
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