package org.gestoresmadrid.core.consultaDev.model.enumerados;

import java.math.BigDecimal;

public enum EstadoConsultaDev {

	Iniciado("1", "Iniciado"){
		public String toString() {
	        return "1";
	    }
	},
	Pdte_Consulta_Dev("2", "Pendiente Consulta Dev"){
		public String toString() {
	        return "2";
	    }
	},
	Finalizado("3", "Finalizado"){
		public String toString() {
	        return "3";
	    }
	},
	Finalizado_Con_Error("4", "Finalizada Con Error"){
		public String toString() {
	        return "4";
	    }
	},
	Anulada("5", "Anulada"){
		public String toString() {
	        return "5";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoConsultaDev(String valorEnum, String nombreEnum) {
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
	
	public static EstadoConsultaDev convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoConsultaDev estado : EstadoConsultaDev.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoConsultaDev estado : EstadoConsultaDev.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoConsultaDev estado : EstadoConsultaDev.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoConsultaDev estadoDev){
		if(estadoDev != null){
			for(EstadoConsultaDev estado : EstadoConsultaDev.values()){
				if(estado.getValorEnum() == estadoDev.getValorEnum()){
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
	
}
