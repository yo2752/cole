package org.gestoresmadrid.core.registradores.model.enumerados;

/*
 * 	Encapsula los tipos de entrada en el registro
 */

public enum TipoEntrada {

	PRIMERA("1","Primera entrada en el registro"){
		public String toString() {
	        return "1";
	    }
	},
	SUBSANACION("2", "Subsanación de un documento ya presentado"){
		public String toString() {
	        return "2";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoEntrada(String valorEnum, String nombreEnum){
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
    public static TipoEntrada convertir(String valorEnum) {    
        
    	if("1".equals(valorEnum))
    		return PRIMERA;
    	
    	if("2".equals(valorEnum))
    		return SUBSANACION;
    	
    	return null;
   }
}