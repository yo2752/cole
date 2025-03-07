package org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans;

public enum EstadoEmpresaTelematica {

	Activo("1", "Activo") {
		public String toString() {
			return "1";
		}
	},
	Desactivo("2", "Desactivo") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoEmpresaTelematica(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoEmpresaTelematica convertir(String valorEnum) {
		for (EstadoEmpresaTelematica element : EstadoEmpresaTelematica.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoEmpresaTelematica element : EstadoEmpresaTelematica.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static int EstadoEmpresaTelematica(String nombreEnum) {
		for (EstadoEmpresaTelematica element : EstadoEmpresaTelematica.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				try {
					return Integer.parseInt(element.valorEnum);
				} catch (NumberFormatException e){
				}
			}
		}
		return -1;
	}

}