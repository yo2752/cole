package org.oegam.gestor.distintivos.model;

public enum Emisor {
	Avila("AV", "AVILA"){
		public String toString() {
	        return "AV";
	    }
	},
	Guadalajara("GU", "GUADALAJARA"){
		public String toString() {
	        return "GU";
	    }
	},
	Madrid("M", "MADRID"){
		public String toString() {
	        return "M";
	    }
	},
	Cuenca("CU", "CUENCA"){
		public String toString() {
	        return "CU";
	    }
	},
	AlcalaHenares("M2", "ALCALA DE HENARES"){
		public String toString() {
	        return "M2";
	    }
	},
	Alcorcon("M1", "ALCORCON"){
		public String toString() {
	        return "M1";
	    }
	},
	CiudadReal("CR", "CIUDAD REAL"){
		public String toString() {
	        return "CR";
	    }
	},
	Segovia("SG", "SEGOVIA"){
		public String toString() {
	        return "SG";
	    }
	},
	Colegio("CO", "COLEGIO"){
		public String toString() {
	        return "CO";
	    }
	};

	private String valorEnum;
	private String nombreEnum;
	
	private Emisor(String valorEnum, String nombreEnum) {
		this.setValorEnum(valorEnum);
		this.setNombreEnum(nombreEnum);
	}

	public static Emisor convertir(String valorEnum) {
		
		if(valorEnum != null && !valorEnum.isEmpty()) {
			for(Emisor emisor: Emisor.values()) {
				if(emisor.getValorEnum().equals(valorEnum)){
					return emisor;
				}
			}
		}
		return null;
	}

	
	public static Emisor obtenerEmisor(String valorEnum){
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(Emisor emisor: Emisor.values()){
				if(emisor.getValorEnum().equals(valorEnum)){
					return emisor;
				}
			}
		}
		return null;
	}
	
	public static Emisor obtenerJefatura(String nombreEnum){
		if(nombreEnum != null && !nombreEnum.isEmpty()){
			for(Emisor emisor: Emisor.values()){
				if(emisor.getNombreEnum().equals(nombreEnum)){
					return emisor;
				}
			}
		}
		return null;
	}
		
	public static Emisor convertirLocalityName(String value) {
		if(value != null && !value.isEmpty()){
			for(Emisor emisor: Emisor.values()){
				if(emisor.getNombreEnum().equals(value)){
					return emisor;
				}
			}
		}
		return null;
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

}
