package org.gestoresmadrid.core.paises.model.enumerados;

/*
 * 	0=Pais Exportacion
	1=Pais Transito Comunitario CEE
	
 */

public enum TipoPais {

	Pais_Exportacion("0","Pais Exportación"){
		public String toString() {
	        return "0";
	    }
	},
	Pais_Transito_CEE("1", "Pais Tránsito Comunitario CEE"){
		public String toString() {
	        return "1";
	    }
	
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoPais(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
	// valueOf
    public static TipoPais convertir(String valorEnum) {    
        
    	if("0".equals(valorEnum))
    		return Pais_Exportacion;
    	
    	if("1".equals(valorEnum))
    		return Pais_Transito_CEE;
    	
    	return null;
   }
}
