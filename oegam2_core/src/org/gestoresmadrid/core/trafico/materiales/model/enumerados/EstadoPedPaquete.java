package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

import java.io.Serializable;

public enum EstadoPedPaquete implements Serializable {
	Disponible(new Long(0), "Disponible"){
		public String toString() {
	        return "0";
	    }
	},
	Utilizando(new Long(1), "Utilizando"){
		public String toString() {
	        return "1";
	    }
	},
	Agotado(new Long(2), "Agotado"){
		public String toString() {
	        return "2";
	    }
	};
	
	private Long valorEnum;
	private String nombreEnum;
	
	private EstadoPedPaquete(Long valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static EstadoPedPaquete convertir(Long valorEnum) {
		
		if(valorEnum != null) {
			for(EstadoPedPaquete estado : EstadoPedPaquete.values()) {
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(Long valor){
		if(valor != null){
			for(EstadoPedPaquete estado : EstadoPedPaquete.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static Long convertirNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(EstadoPedPaquete estado : EstadoPedPaquete.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoPedPaquete estado){
		if(estado != null){
			for(EstadoPedPaquete item : EstadoPedPaquete.values()){
				if(item.getValorEnum() == estado.getValorEnum()){
					return item.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	// Getters and Setters

	public String getNombreEnum() {
		return nombreEnum;
	}

	public Long getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(Long valorEnum) {
		this.valorEnum = valorEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

}
