package org.gestoresmadrid.core.modelos.model.enumerados;

public enum PeriodoRenta {

	Mensual("MENSUAL", "Mensual"){
		public String toString() {
	        return "MENSUAL";
	    }
	},
	Anual("ANUAL", "Anual"){
		public String toString() {
	        return "ANUAL";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private PeriodoRenta(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static PeriodoRenta convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(PeriodoRenta periodo : PeriodoRenta.values()){
				if(periodo.getValorEnum().equals(valorEnum)){
					return periodo;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(PeriodoRenta periodo : PeriodoRenta.values()){
				if(periodo.getValorEnum().equals(valor)){
					return periodo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(PeriodoRenta periodoRenta){
		if(periodoRenta != null){
			for(PeriodoRenta periodo : PeriodoRenta.values()){
				if(periodo.getValorEnum() == periodoRenta.getValorEnum()){
					return periodo.getNombreEnum();
				}
			}
		}
		return null;
	}
}
