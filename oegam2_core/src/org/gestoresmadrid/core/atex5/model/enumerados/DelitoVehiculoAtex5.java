package org.gestoresmadrid.core.atex5.model.enumerados;

import java.math.BigDecimal;

public enum DelitoVehiculoAtex5 {

	Alcoholemia("2", "Alcoholemia"){
		public String toString() {
	        return "2";
	    }
	},
	No_Usar_Cinturon("3", "No usar cinturón de seguridad"){
		public String toString() {
	        return "3";
	    }
	},
	No_Detenerse_Stop_O_Semaforo("4", "No detenerse ante Stop o semáforo en rojo"){
		public String toString() {
	        return "4";
	    }
	},
	Uso_Indebido_Carril_Bus("5", "Uso indebido del carril bus"){
		public String toString() {
	        return "5";
	    }
	},
	Drogas("10", "Conducir bajo la influencia de las drogas"){
		public String toString() {
	        return "10";
	    }
	},
	No_Usar_Casco("11", "No usar casco de seguridad"){
		public String toString() {
	        return "11";
	    }
	},
	Utilizacion_Dispositivos_De_Comunicacion("12", "Utilización ilegal de dispositivo de comunicación"){
		public String toString() {
	        return "12";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private DelitoVehiculoAtex5(String valorEnum, String nombreEnum) {
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
	
	public static DelitoVehiculoAtex5 convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(DelitoVehiculoAtex5 delito : DelitoVehiculoAtex5.values()){
				if(delito.getValorEnum().equals(valorEnum)){
					return delito;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(DelitoVehiculoAtex5 delito : DelitoVehiculoAtex5.values()){
				if(delito.getValorEnum().equals(valor)){
					return delito.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(DelitoVehiculoAtex5 delito : DelitoVehiculoAtex5.values()){
				if(delito.getNombreEnum().equals(nombre)){
					return delito.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(DelitoVehiculoAtex5 delitoAtex5){
		if(delitoAtex5 != null){
			for(DelitoVehiculoAtex5 delito : DelitoVehiculoAtex5.values()){
				if(delito.getValorEnum() == delitoAtex5.getValorEnum()){
					return delito.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirEstadoBigDecimal(BigDecimal delitoAtex5){
		if(delitoAtex5 != null){
			return convertirTexto(delitoAtex5.toString());
		}
		return "";
	}
}
