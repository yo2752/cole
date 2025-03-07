package org.gestoresmadrid.core.modelos.model.enumerados;

public enum GrupoConcepto {

	CONCEPTO_141("1", "1.4.1"){
		public String toString() {
	        return "1.4.1";
	    }
	},
	CONCEPTO_142("2", "1.4.2"){
		public String toString() {
	        return "1.4.2";
	    }
	},
	CONCEPTO_143("3", "1.4.3"){
		public String toString() {
	        return "1.4.3";
	    }
	},
	CONCEPTO_144("4", "1.4.4"){
		public String toString() {
	        return "1.4.4";
	    }
	},
	CONCEPTO_145("5", "1.4.5")
	{
		public String toString() {
	        return "1.4.5";
	    }
	},
	CONCEPTO_146("6", "1.4.6"){
		public String toString() {
	        return "1.4.6";
	    }
	},
	CONCEPTO_147("7", "1.4.7")	{
		public String toString() {
	        return "1.4.7";
	    }
	},
	CONCEPTO_148("8", "1.4.8")	{
		public String toString() {
	        return "1.4.8";
	    }
	},
	CONCEPTO_149("9", "1.4.9")	{
		public String toString() {
	        return "1.4.9";
	    }
	},
	CONCEPTO_1410("10", "1.4.10")	{
		public String toString() {
	        return "1.4.10";
	    }
	},
	CONCEPTO_1411("11", "1.4.11")	{
		public String toString() {
	        return "1.4.11";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private GrupoConcepto(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	public static String convertirNombre(String nombreEnum){
		if(nombreEnum != null && !nombreEnum.isEmpty()){
			for(GrupoConcepto grupoConcepto : GrupoConcepto.values()){
				if(grupoConcepto.getNombreEnum().equals(nombreEnum)){
					return grupoConcepto.getValorEnum();
				}
			}
		}
		return null;
	}
}
