package org.gestoresmadrid.core.model.enumerados;

import java.math.BigDecimal;

public enum EstadoBastidor {

	INACTIVO(0, "INACTIVO") {
		public String toString() {
			return "0";
		}
	},
	ACTIVO(1, "ACTIVO") {
		public String toString() {
			return "1";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private EstadoBastidor(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(Integer valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static EstadoBastidor convertir(int valorEnum) {
		for (EstadoBastidor element : EstadoBastidor.values()) {
			if (element.valorEnum == valorEnum) {
				return element;
			}
		}
		return null;
	}

	public static EstadoBastidor convertir(BigDecimal valorEnum) {
		return convertir(valorEnum.intValue());
	}

	public static EstadoBastidor convertir(String valorEnum) {
		if (valorEnum != null) {
			Integer valor = Integer.parseInt(valorEnum);
			for (EstadoBastidor element : EstadoBastidor.values()) {
				if (element.valorEnum == valor) {
					return element;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(int valorEnum) {
		for (EstadoBastidor element : EstadoBastidor.values()) {
			if (element.valorEnum == valorEnum) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum != null) {
			Integer valor = Integer.parseInt(valorEnum);
			for (EstadoBastidor element : EstadoBastidor.values()) {
				if (element.valorEnum == valor) {
					return element.nombreEnum;
				}
			}
		}
		return null;
	}

	public static Integer convertirNombre(String nombreEnum) {
		for (EstadoBastidor element : EstadoBastidor.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
}