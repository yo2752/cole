package org.gestoresmadrid.core.personas.model.enumerados;

public enum EstadoPersonaDireccion {

	Activo(new Short("0"), "ACTIVO"), Fusionado(new Short("1"), "FUSIONADO"), Eliminado(new Short("2"), "ELIMINADO");

	private Short valorEnum;
	private String nombreEnum;

	public Short getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private EstadoPersonaDireccion(Short valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String toString() {
		return this.valorEnum.toString();
	}

	public static String convertirValorANombre(Short valorEnum) {
		for (EstadoPersonaDireccion element : EstadoPersonaDireccion.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static EstadoPersonaDireccion convertir(Short valorEnum) {
		for (EstadoPersonaDireccion estado : EstadoPersonaDireccion.values()) {
			if (estado.valorEnum.equals(valorEnum)) {
				return estado;
			}
		}
		return null;
	}
}
