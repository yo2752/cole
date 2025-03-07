package org.gestoresmadrid.core.modelos.model.enumerados;


public enum Decision {
	Si(1, "Sí", "SI"){
		public String toString() {
	        return "1";
	    }
	},
	No(0, "No", "NO"){
		public String toString() {
	        return "0";
	    }
	};

	private Decision(Integer valorEnum, String nombreEnum, String nombreBD) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.nombreBD = nombreBD;
	}
	
	private Integer valorEnum;
	private String nombreEnum;
	private String nombreBD;
	
	public String getNombreEnum() {
		return nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreBD() {
		return nombreBD;
	}
	
    // valueOf
    public static Decision convertir(Integer valorEnum) {    
        
    	if(valorEnum==null)
    		return null;
    	
    	switch (valorEnum) {
		case 1: 
			return Si;

		case 0: 
			return No;
		
		default:
			return null;
		}
    }
    
    public static String convertirValor(Integer valor){
    	if(valor != null){
    		for(Decision decision : Decision.values()) {
    			if(decision.getValorEnum().equals(valor)){
    				return decision.getNombreEnum();
    			}
    		}
    	}
    	return null;
    }
}
