package org.icogam.legalizacion.utiles;



public enum TipoSiNo {
	SI(true,"Si"){
		public String toString() {
	        return "true";
	    }
	},
	NO(false,"No"){
		public String toString() {
	        return "false";
	    }
	};
	
	private boolean valorEnum;
	private String nombreEnum;

	
	public boolean getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	private TipoSiNo(boolean valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
    
}
