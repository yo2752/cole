package org.gestoresmadrid.core.transporte.model.enumeration;


public enum EstadosNotificacionesTransporte {

	Por_Defecto("0", "Por Defecto"){
		public String toString() {
	        return "0";
	    }
	},
	Aceptada("1", "Aceptada"){
		public String toString() {
	        return "1";
	    }
	},
	Rechazada("2", "Rechazada"){
		public String toString() {
	        return "2";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadosNotificacionesTransporte(String valorEnum, String nombreEnum) {
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
	
	public static EstadosNotificacionesTransporte convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadosNotificacionesTransporte estado : EstadosNotificacionesTransporte.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadosNotificacionesTransporte estado : EstadosNotificacionesTransporte.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadosNotificacionesTransporte estado : EstadosNotificacionesTransporte.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadosNotificacionesTransporte estadoNot){
		if(estadoNot != null){
			for(EstadosNotificacionesTransporte estado : EstadosNotificacionesTransporte.values()){
				if(estado.getValorEnum() == estadoNot.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
}