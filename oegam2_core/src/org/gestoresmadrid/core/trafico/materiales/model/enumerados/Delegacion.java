package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum Delegacion {
	Avila("2", "AV"){
		public String toString() {
	        return "2";
	    }
	},
	Guadalajara("27", "GU"){
		public String toString() {
	        return "27";
	    }
	},
	Madrid("43", "M"){
		public String toString() {
	        return "43";
	    }
	},
	Cuenca("36", "CU"){
		public String toString() {
	        return "36";
	    }
	},
	AlcalaHenares("52", "M2"){
		public String toString() {
	        return "52";
	    }
	},
	Alcorcon("51", "M1"){
		public String toString() {
	        return "51";
	    }
	},
	CiudadReal("4", "CR"){
		public String toString() {
	        return "4";
	    }
	},
	Segovia("9", "SG"){
		public String toString() {
	        return "9";
	    }
	};

	private String valorEnum;
	private String nombreEnum;
	
	private Delegacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static Delegacion convertir(String valorEnum) {
		
		if(valorEnum != null && !valorEnum.isEmpty()) {
			for(Delegacion estado : Delegacion.values()) {
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(Delegacion estado : Delegacion.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(Delegacion estado : Delegacion.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static Long convertirNombreLong(String nombre){
		if(nombre != null){
			for(Delegacion estado : Delegacion.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return new Long(estado.getValorEnum());
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(Long valor){
		if(valor != null){
			for(Delegacion estado : Delegacion.values()){
				if(estado.getValorEnum().equals(valor.toString())){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(Delegacion delegacion){
		if(delegacion != null){
			for(Delegacion del : Delegacion.values()){
				if(del.getValorEnum() == delegacion.getValorEnum()){
					return del.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	// Getters and Setters
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
