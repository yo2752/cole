package org.icogam.sanciones.Utiles;

public enum TipoImpresion {
		
	LISTADOS(1,"Listado por motivos"){
		public String toString() {
	        return "1";
	    }
	},
	UNIFICADOS(2,"Unificados"){
		public String toString() {
	        return "2";
	    }
	};
	
	private Integer valorEnum;
	private String nombreEnum;

	
	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	
	private TipoImpresion(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	 public static TipoImpresion convertir(Integer valorEnum) {    
	    	if(valorEnum==null)
	    		return null;
	    	
	    	if(valorEnum.equals(1)) 
				return LISTADOS;
	    	else if(valorEnum.equals(2)) 
				return UNIFICADOS;
	    	else
				return null;
	   }
}
