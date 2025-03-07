package org.gestoresmadrid.core.enumerados;

import java.util.Arrays;

public enum EstadoImpr {

	Pendiente("0", "Pendiente"){
		public String toString() {
			return "0";
		}
	},
	Tramitado_Telematicamente("1", "Tramitado Telematicamente"){
		public String toString() {
			return "1";
		}
	},
	Finalizado("2", "Finalizado"){
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoImpr(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	public String getValorEnum() {
		return valorEnum;
	}
	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}
	public String getNombreEnum() {
		return nombreEnum;
	}
	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static EstadoImpr convertir(String valorEnum) {
		for (EstadoImpr element : EstadoImpr.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (EstadoImpr element : EstadoImpr.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (EstadoImpr element : EstadoImpr.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	public static EstadoImpr[] listadoOrdenado() {
	    EstadoImpr[] estadosOrdenados = EstadoImpr.values();
	    Arrays.sort(estadosOrdenados);
		return estadosOrdenados;
	}
}
