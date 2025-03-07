package org.gestoresmadrid.core.trafico.eeff.model.enumerados;

import java.math.BigDecimal;

public enum EstadoConsultaEEFF {

	Iniciado("1", "Iniciado"){
		public String toString() {
	        return "1";
	    }
	},
	Pdte_Respuesta("2", "Pendiente Respuesta"){
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
	
	private EstadoConsultaEEFF(String valorEnum, String nombreEnum) {
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
	
	public static EstadoConsultaEEFF convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoConsultaEEFF estado : EstadoConsultaEEFF.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoConsultaEEFF estado : EstadoConsultaEEFF.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoConsultaEEFF estado : EstadoConsultaEEFF.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoConsultaEEFF estadoEEFF){
		if(estadoEEFF != null){
			for(EstadoConsultaEEFF estado : EstadoConsultaEEFF.values()){
				if(estado.getValorEnum() == estadoEEFF.getValorEnum()){
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
