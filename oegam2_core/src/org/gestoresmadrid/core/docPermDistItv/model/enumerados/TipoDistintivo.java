package org.gestoresmadrid.core.docPermDistItv.model.enumerados;

public enum TipoDistintivo {

	CERO("0","Cero","Ambiental 0"){
		public String toString() {
			return "0";
		}
	},
	ECO("E","Eco","Ambiental Eco"){
		public String toString() {
			return "E";
		}
	},
	B("B","B","Ambiental B"){
		public String toString() {
			return "B";
		}
	},
	C("C","C","Ambiental C"){
		public String toString() {
			return "C";
		}
	},
	CEROMT("LO","Cero Moto","Ambiental 0 Motos"){
		public String toString() {
			return "0";
		}
		
	},
	ECOMT("LE","Eco Moto","Ambiental Eco Motos"){
		public String toString() {
			return "E";
		}
		
	},
	BMT("LB","B Moto","Ambiental B Motos"){
		public String toString() {
			return "B";
		}
		
	},
	CMT("LC","C Moto", "Ambiental C Motos"){
		public String toString() {
			return "C";
		}
		
	},
	CARSHARING("CS","Car Sharing", "Ambiental Car Sharing"){
		public String toString() {
			return "CS";
		}
		
	},
	MOTOSHARING("MS","MOTO Sharing", "Ambiental Moto Sharing"){
		public String toString() {
			return "MS";
		}
		
	};
	
	private String valorEnum;
	private String nombreEnum;
	private String descripcion;
	
	private TipoDistintivo(String valorEnum, String nombreEnum, String descripcion) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.descripcion = descripcion;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static TipoDistintivo convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoDistintivo tipo : TipoDistintivo.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(TipoDistintivo tipo : TipoDistintivo.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoDistintivo tipo : TipoDistintivo.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}

	public static Boolean esDistintivoMoto(String tipoDistintivo) {
		if(TipoDistintivo.BMT.getValorEnum().equals(tipoDistintivo)){
			return Boolean.TRUE;
		} else if(TipoDistintivo.CEROMT.getValorEnum().equals(tipoDistintivo)){
			return Boolean.TRUE;
		}else if(TipoDistintivo.CMT.getValorEnum().equals(tipoDistintivo)){
			return Boolean.TRUE;
		}else if(TipoDistintivo.ECOMT.getValorEnum().equals(tipoDistintivo)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static Boolean esCorrectoTipo(String tipoDistintivo) {
		if(TipoDistintivo.convertir(tipoDistintivo) != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
