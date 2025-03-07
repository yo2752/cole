package org.gestoresmadrid.core.modelos.model.enumerados;

import java.math.BigDecimal;

public enum EstadoModelos {

	Inicial("1", "Inicial"){
		public String toString() {
	        return "1";
	    }
	},
	Validado("2", "Validado"){
		public String toString() {
	        return "2";
	    }
	},
	Autoliquidable("3", "Autoliquidable"){
		public String toString() {
	        return "3";
	    }
	},
	FinalizadoKO("4", "Finalizado con Error"){
		public String toString() {
	        return "4";
	    }
	},
	Pendiente_Presentacion("5", "Pendiente Presentacion"){
		public String toString() {
	        return "5";
	    }
	},
	FinalizadoOK("6", "Finalizado"){
		public String toString() {
	        return "6";
	    }
	},Anulado("7", "Anulado"){
		public String toString() {
	        return "7";
	    }
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoModelos(String valorEnum, String nombreEnum) {
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

	public static EstadoModelos convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoModelos estado : EstadoModelos.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoModelos estado : EstadoModelos.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoModelos estadoM){
		if(estadoM != null){
			for(EstadoModelos estado : EstadoModelos.values()){
				if(estado.getValorEnum() == estadoM.getValorEnum()){
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
