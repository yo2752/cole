package org.gestoresmadrid.core.atex5.model.enumerados;

import java.math.BigDecimal;

public enum EstadoAtex5 {

	Iniciado("1", "Iniciado"){
		public String toString() {
	        return "1";
	    }
	},
	Pdte_Respuesta_DGT("2", "Pendiente Respuesta DGT"){
		public String toString() {
	        return "2";
	    }
	},
	Finalizado_PDF("3", "Finalizado PDF"){
		public String toString() {
	        return "3";
	    }
	},
	Finalizado_Con_Error("4", "Finalizada Con Error"){
		public String toString() {
	        return "4";
	    }
	},
	Anulado("5", "Anulado"){
		public String toString() {
	        return "5";
	    }
	},
	Finalizado_Sin_Antecedentes("6", "Finalizado Sin Antecedentes"){
		public String toString() {
	        return "6";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoAtex5(String valorEnum, String nombreEnum) {
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
	
	public static EstadoAtex5 convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoAtex5 estado : EstadoAtex5.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoAtex5 estado : EstadoAtex5.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoAtex5 estado : EstadoAtex5.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoAtex5 estadoDev){
		if(estadoDev != null){
			for(EstadoAtex5 estado : EstadoAtex5.values()){
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
