package org.gestoresmadrid.core.model.enumerados;

public enum EstadoPresentacionJPT {

	No_Presentado(new Short("0"), "NO"), 
	Presentado(new Short("1"), "SI"), 
	Manual(new Short("2"), "");

	private Short valorEnum;
	private String nombreEnum;

	public Short getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private EstadoPresentacionJPT(Short valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String toString() {
		return this.valorEnum.toString();
	}

	public static String convertirValorANombre(Short valorEnum) {
		for (EstadoPresentacionJPT element : EstadoPresentacionJPT.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static EstadoPresentacionJPT convertir(Short valorEnum) {
		for (EstadoPresentacionJPT estado : EstadoPresentacionJPT.values()) {
			if (estado.valorEnum.equals(valorEnum)) {
				return estado;
			}
		}
		return null;
	}
}
