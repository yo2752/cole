package org.gestoresmadrid.oegamLegalizaciones.view.utiles;



public enum TipoDocumento {
	TITULO("1","TITULO"){
		public String toString() {
	        return "1";
	    }
	},
	DOCUMENTOS("2","DOCUMENTO"){
		public String toString() {
	        return "2";
	    }
	},
	INCIDENCIAS("3","INCIDENCIAS"){
		public String toString() {
	        return "3";
	    }
	},
	OTROS("4","OTROS"){
		public String toString() {
	        return "4";
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
	

	
	private TipoDocumento(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	
	
    public static TipoDocumento convertir(String valorEnum) {    
        
    	if(valorEnum==null)
    		return null;
    	
    	if(valorEnum.equals("1")) 
			return TITULO;
    	else if(valorEnum.equals("2")) 
			return DOCUMENTOS;
    	else if(valorEnum.equals("3")) 
			return INCIDENCIAS;
    	else if(valorEnum.equals("4")) 
			return OTROS;
    	else
			return null;
   }
    
}
