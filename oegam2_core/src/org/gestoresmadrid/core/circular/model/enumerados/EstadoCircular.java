package org.gestoresmadrid.core.circular.model.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum EstadoCircular {
	Iniciado("0", "Iniciado"){
		public String toString() {
	        return "0";
	    }
	},
	Finalizado("1", "Finalizado"){
		public String toString() {
	        return "1";
	    }
	
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoCircular(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	public static EstadoCircular convertir(String valorEnum) {
		for (EstadoCircular element : EstadoCircular.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoCircular element : EstadoCircular.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static int EstadoCircular(String nombreEnum) {
		for (EstadoCircular element : EstadoCircular.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				try {
					return Integer.parseInt(element.valorEnum);
				} catch (NumberFormatException e){
				}
			}
		}
		return -1;
	}

	public static List<EstadoCircular> getEstadoCircular() {
	List<EstadoCircular> listaEstado = new ArrayList<EstadoCircular>();
	listaEstado.add(EstadoCircular.Iniciado);
	listaEstado.add(EstadoCircular.Finalizado);
	return listaEstado;
	}
}
