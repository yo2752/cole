package org.gestoresmadrid.core.consultaDev.model.enumerados;

import java.math.BigDecimal;

public enum EstadoCif {

	No_Existe("0", "CIF no existente en DEV"){
		public String toString() {
	        return "0";
	    }
	},
	Alta_Dev("1", "CIF dado de alta en DEV"){
		public String toString() {
	        return "1";
	    }
	},Baja_Dev("2", "CIF dado de baja en DEV"){
		public String toString() {
	        return "2";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoCif(String valorEnum, String nombreEnum) {
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
	
	public static EstadoCif convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoCif estado : EstadoCif.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoCif estado : EstadoCif.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoCif estado : EstadoCif.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoCif estadoCif){
		if(estadoCif != null){
			for(EstadoCif estado : EstadoCif.values()){
				if(estado.getValorEnum() == estadoCif.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirEstadoBigDecimal(BigDecimal estado){
		if(estado != null){
			return convertirTexto(estado.toString());
		}
		return "";
	}

	public static BigDecimal convertirEstadoSuscripcionToEstadoCif(String codEstado) {
		if(codEstado != null && !codEstado.isEmpty()){
			if(EstadoSuscripcion.SUSCRITO.getValorEnum().equals(codEstado)){
				return new BigDecimal(EstadoCif.Alta_Dev.getValorEnum());
			} else if(EstadoSuscripcion.NO_SUSCRITO.getValorEnum().equals(codEstado)){
				return new BigDecimal(EstadoCif.Baja_Dev.getValorEnum());
			}
		}
		return null;
	}
}
