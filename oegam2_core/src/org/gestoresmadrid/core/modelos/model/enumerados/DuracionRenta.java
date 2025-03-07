package org.gestoresmadrid.core.modelos.model.enumerados;



public enum DuracionRenta{

	Global("GLOBAL", "Global"){
		public String toString() {
	        return "GLOBAL";
	    }
	},
	Periodica("PERIODICA", "Periódica"){
		public String toString() {
	        return "PERIODICA";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;

	private DuracionRenta(String valorEnum, String nombreEnum) {
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

	public static DuracionRenta convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(DuracionRenta duracion : DuracionRenta.values()){
				if(duracion.getValorEnum().equals(valorEnum)){
					return duracion;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(DuracionRenta duracion : DuracionRenta.values()){
				if(duracion.getValorEnum().equals(valor)){
					return duracion.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(DuracionRenta duracionRenta){
		if(duracionRenta != null){
			for(DuracionRenta duracion : DuracionRenta.values()){
				if(duracion.getValorEnum() == duracionRenta.getValorEnum()){
					return duracion.getNombreEnum();
				}
			}
		}
		return null;
	}
}
