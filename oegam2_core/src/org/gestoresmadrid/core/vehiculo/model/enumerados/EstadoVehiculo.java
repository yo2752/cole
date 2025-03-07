package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum EstadoVehiculo {

	Desactivo(new Short("0"), "DESACTIVO"), Activo(new Short("1"), "ACTIVO");

	private Short valorEnum;
	private String nombreEnum;

	public Short getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private EstadoVehiculo(Short valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String toString() {
		return this.valorEnum.toString();
	}

	public static String convertirTexto(Short valorEnum) {
		for (EstadoVehiculo element : EstadoVehiculo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return Activo.nombreEnum;
	}

	public static EstadoVehiculo convertir(Short valorEnum) {
		for (EstadoVehiculo estado : EstadoVehiculo.values()) {
			if (estado.valorEnum.equals(valorEnum)) {
				return estado;
			}
		}
		return null;
	}
}
