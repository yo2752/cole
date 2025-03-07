package org.gestoresmadrid.oegamLegalizaciones.view.utiles;

public enum ClaseDocumento {
	CONSULAR("1","CONSULAR"){
		public String toString() {
	        return "1";
	    }
	},
	ESPANNOL("2","ESPAÑOL"){
		public String toString() {
	        return "2";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;

	
	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	

	
	private ClaseDocumento(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	
	
    public static ClaseDocumento convertir(String valorEnum) {    
        
    	if(valorEnum==null)
    		return null;
    	
    	if(valorEnum.equals("1")) 
			return CONSULAR;
    	else if(valorEnum.equals("2")) 
			return ESPANNOL;
    	else
			return null;
   }
    
}
