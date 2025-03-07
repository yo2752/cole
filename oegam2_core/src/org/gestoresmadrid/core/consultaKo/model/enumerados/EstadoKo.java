package org.gestoresmadrid.core.consultaKo.model.enumerados;

import java.math.BigDecimal;

public enum EstadoKo {

	Iniciado("1", "Iniciado"){
		public String toString() {
	        return "1";
	    }
	},
	Pdte_DGT("2", "Pendiente DGT"){
		public String toString() {
	        return "2";
	    }
	},
	Finalizado("3", "Doc. Recibida"){
		public String toString() {
	        return "3";
	    }
	},
	Pdte_Generar_KO("4", "Pendiente Generar KO"){
		public String toString() {
	        return "4";
	    }
	},
	Doc_KO_Gen("5", "Doc. KO Generada"){
		public String toString() {
	        return "5";
	    }
	},
	Finalizado_Con_Error("6", "Finalizado Con Error"){
		public String toString() {
	        return "6";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoKo(String valorEnum, String nombreEnum) {
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
	
	public static EstadoKo convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoKo estado : EstadoKo.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoKo estado : EstadoKo.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoKo estado : EstadoKo.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoKo estadoDev){
		if(estadoDev != null){
			for(EstadoKo estado : EstadoKo.values()){
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
