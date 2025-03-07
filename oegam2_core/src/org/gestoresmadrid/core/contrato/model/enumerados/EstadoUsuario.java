package org.gestoresmadrid.core.contrato.model.enumerados;

public enum EstadoUsuario {

	Habilitado("1", "Habilitado") {
		public String toString() {
			return "1";
		}
	},
	Deshabilitado("2", "Deshabilitado") {
		public String toString() {
			return "2";
		}
	},
	Eliminado("3", "Eliminado") {
		public String toString() {
			return "3";
		}
	},
	Asociado("4", "Asociado") {
		public String toString() {
				return "4";
		}			
	},	
	SinAsociar("5", "SinAsociar") {
		public String toString() {
			return "5";
		}
	},
	CambioContrato("6", "CambioContrato") {
		public String toString() {
			return "6";
		}
		
	};

	
	private String valorEnum;
	private String nombreEnum;

	private EstadoUsuario(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoUsuario convertir(String valorEnum) {
		for (EstadoUsuario element : EstadoUsuario.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoUsuario element : EstadoUsuario.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
