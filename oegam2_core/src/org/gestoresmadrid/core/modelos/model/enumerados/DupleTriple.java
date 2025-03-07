package org.gestoresmadrid.core.modelos.model.enumerados;

public enum DupleTriple {
	Duple("D", "Duple"){
		public String toString() {
	        return "D";
	    }
	},
	Triple("T", "Triple"){
		public String toString() {
	        return "T";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private DupleTriple(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
    public static DupleTriple convertirValor(String valorEnum) {    
        if(valorEnum != null && !valorEnum.isEmpty()){
        	for(DupleTriple dupleTriple : DupleTriple.values()){
        		if(dupleTriple.getValorEnum().equals(valorEnum)){
        			return dupleTriple;
        		}
        	}
        }
    	return null;
   }
    
    public static DupleTriple convertirNombre(String nombreEnum) {    
        if(nombreEnum != null && !nombreEnum.isEmpty()){
        	for(DupleTriple dupleTriple : DupleTriple.values()){
        		if(dupleTriple.getNombreEnum().equals(nombreEnum)){
        			return dupleTriple;
        		}
        	}
        }
    	return null;
   }
}
