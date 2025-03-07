package org.gestoresmadrid.core.model.enumerados;

public enum TipoMaterial {

	PC("PC", "Permiso de Circulacion"){
		public String toString() {
	        return "PC";
	    }
	},DSTV_ECO("E", "Distintivo ECO"){
		public String toString() {
	        return "E";
	    }
	},DSTV_0("0", "Distintivo Cero"){
		public String toString() {
	        return "0";
	    }
	},DSTV_B("B", "Distintivo B"){
		public String toString() {
	        return "B";
	    }
	},DSTV_C("C", "Distintivo C"){
		public String toString() {
	        return "C";
	    }
	},DSTV_LE("LE", "Distintivo Moto ECO"){
		public String toString() {
	        return "LE";
	    }
	},DSTV_L0("L0", "Distintivo Moto Cero"){
		public String toString() {
	        return "L0";
	    }
	},DSTV_LB("LB", "Distintivo Moto B"){
		public String toString() {
	        return "LB";
	    }
	},DSTV_LC("LC", "Distintivo Moto C"){
		public String toString() {
	        return "LC";
	    }
	};

	private String valorEnum;
	private String nombreEnum;
	
	private TipoMaterial(String valorEnum, String nombreEnum) {
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
	
	public static TipoMaterial convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoMaterial tipoMaterial : TipoMaterial.values()){
				if(tipoMaterial.getValorEnum().equals(valorEnum)){
					return tipoMaterial;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(TipoMaterial tipoMaterial : TipoMaterial.values()){
				if(tipoMaterial.getValorEnum().equals(valor)){
					return tipoMaterial.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(TipoMaterial tipo){
		if(tipo != null){
			for(TipoMaterial tipoMaterial : TipoMaterial.values()){
				if(tipoMaterial.getValorEnum() == tipo.getValorEnum()){
					return tipoMaterial.getNombreEnum();
				}
			}
		}
		return null;
	}
	
}
