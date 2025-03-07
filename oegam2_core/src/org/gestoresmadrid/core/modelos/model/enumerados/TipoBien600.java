package org.gestoresmadrid.core.modelos.model.enumerados;

public enum TipoBien600 {

	Rustico("RUSTICO", "Bienes Rústicos"){
		public String toString() {
	        return "RUSTICO";
	    }
	},
	Urbano("URBANO", "Bienes Urbanos"){
		public String toString() {
	        return "URBANO";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoBien600(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
    public static TipoBien600 convertirValor(String valorEnum) {    
        if(valorEnum != null && !valorEnum.isEmpty()){
        	for(TipoBien600 tipoBien : TipoBien600.values()){
        		if(tipoBien.getValorEnum().equals(valorEnum)){
        			return tipoBien;
        		}
        	}
        }
    	return null;
   }
    
   public static TipoBien600 convertirNombre(String nombreEnum) {    
        if(nombreEnum != null && !nombreEnum.isEmpty()){
        	for(TipoBien600 tipoBien : TipoBien600.values()){
        		if(tipoBien.getNombreEnum().equals(nombreEnum)){
        			return tipoBien;
        		}
        	}
        }
    	return null;
   }
   
   public static String convertirTexto(String valor) {    
	   if(valor != null && !valor.isEmpty()){
       	for(TipoBien600 tipoBien : TipoBien600.values()){
       		if(tipoBien.getValorEnum().equals(valor)){
       			return tipoBien.getNombreEnum();
       		}
       	}
       }
   	return null;
  }
}
