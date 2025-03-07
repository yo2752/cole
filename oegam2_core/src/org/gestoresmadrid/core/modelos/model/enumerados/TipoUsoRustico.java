package org.gestoresmadrid.core.modelos.model.enumerados;

public enum TipoUsoRustico {

	Ganaderia("GANADERIA"){
		public String toString() {
	        return "GANADERIA";
	    }
	},
	Cultivo("CULTIVO"){
		public String toString() {
	        return "CULTIVO";
	    }
	},
	Otros("OTROS"){
		public String toString() {
	        return "OTROS";
	    }
	};
	
	private String valorEnum;
	
	private TipoUsoRustico(String valorEnum){
		this.valorEnum = valorEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	
    public static TipoUsoRustico convertirValor(String valorEnum) {    
        if(valorEnum != null && !valorEnum.isEmpty()){
        	for(TipoUsoRustico tipoUso : TipoUsoRustico.values()){
        		if(tipoUso.getValorEnum().equals(valorEnum)){
        			return tipoUso;
        		}
        	}
        }
    	return null;
   }
    
}
