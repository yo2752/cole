package org.gestoresmadrid.core.modelos.model.enumerados;

public enum EstadoRemesas {

	Inicial("1", "Inicial"){
		public String toString() {
	        return "1";
	    }
	},
	Validada("2", "Validada"){
		public String toString() {
	        return "2";
	    }
	},
	Generada("3", "Generada"){
		public String toString() {
	        return "3";
	    }
	},Anulada("4", "Anulada"){
		public String toString() {
	        return "4";
	    }
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoRemesas(String valorEnum, String nombreEnum) {
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

	public static EstadoRemesas convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoRemesas estado : EstadoRemesas.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoRemesas estado : EstadoRemesas.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoRemesas estadoR){
		if(estadoR != null){
			for(EstadoRemesas estado : EstadoRemesas.values()){
				if(estado.getValorEnum() == estadoR.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
}
