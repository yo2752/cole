package org.gestoresmadrid.core.modelos.model.enumerados;

public enum DuracionDerechoReal {

	Temporal("TEMPORAL", "Temporal"){
		public String toString() {
	        return "TEMPORAL";
	    }
	},
	Vitalicio("VITALICIO", "Vitalicio"){
		public String toString() {
	        return "VITALICIO";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private DuracionDerechoReal(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static DuracionDerechoReal convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(DuracionDerechoReal duracion : DuracionDerechoReal.values()){
				if(duracion.getValorEnum().equals(valorEnum)){
					return duracion;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(DuracionDerechoReal duracion : DuracionDerechoReal.values()){
				if(duracion.getValorEnum().equals(valor)){
					return duracion.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(DuracionDerechoReal duracionDerReal){
		if(duracionDerReal != null){
			for(DuracionDerechoReal duracion : DuracionDerechoReal.values()){
				if(duracion.getValorEnum() == duracionDerReal.getValorEnum()){
					return duracion.getNombreEnum();
				}
			}
		}
		return null;
	}
}
