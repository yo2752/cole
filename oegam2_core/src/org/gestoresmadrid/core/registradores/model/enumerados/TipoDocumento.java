package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoDocumento {
	DNI("1", "DNI") {
		public String toString() {
			return "1";
		}
	},
	CIF("2", "CIF") {
		public String toString() {
			return "2";
		}
	},
	NIF("3", "NIF") {
		public String toString() {
			return "3";
		}
	},
	PASAPORTE("4", "Pasaporte") {
		public String toString() {
			return "4";
		}
	};

	private String nombreEnum;
	private String valorEnum;

	private TipoDocumento(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public static TipoDocumento convertir(String valorEnum) {
		for (TipoDocumento element : TipoDocumento.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
}
