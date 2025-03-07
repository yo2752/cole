package org.gestoresmadrid.core.sancion.model.enumerados;

public enum Motivo {
	ALEGACION(1,"Presentar alegaciones"){
		public String toString() {
	        return "1";
	    }
	},
	RECURSO(2,"Presentar recursos"){
		public String toString() {
	        return "2";
	    }
	}/*,
	RESGUARDO(3,"Presentar el resguardo de una multa"){
		public String toString() {
	        return "3";
	}
	}*/;
	
	private Integer valorEnum;
	private String nombreEnum;
	
	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	private Motivo(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
    public static Motivo convertir(Integer valorEnum) {    
    	if(valorEnum==null)
    		return null;
    	
    	if(valorEnum.equals(1)) 
			return ALEGACION;
    	else if(valorEnum.equals(2)) 
			return RECURSO;
    	/*else if(valorEnum.equals(3)) 
			return RESGUARDO;*/
    	else
			return null;
   }
}
