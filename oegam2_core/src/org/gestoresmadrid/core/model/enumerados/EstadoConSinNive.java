package org.gestoresmadrid.core.model.enumerados;

public enum EstadoConSinNive {
	TODOS("0", "Todos"),
	NO("1", "NO"), 
	SI("2", "SI"); 

	private String valorEnum;
	private String nombreEnum;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private EstadoConSinNive(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String toString() {
		return this.valorEnum.toString();
	}

	public static String convertirValorANombre(String valorEnum) {
		for (EstadoConSinNive element : EstadoConSinNive.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static EstadoConSinNive convertir(String valorEnum) {
		for (EstadoConSinNive estado : EstadoConSinNive.values()) {
			if (estado.valorEnum.equals(valorEnum)) {
				return estado;
			}
		}
		return null;
	}
}
