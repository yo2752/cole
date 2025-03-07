package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum EstadoMaterial {
	Activo("A", "Activo"){
		public String toString() {
	        return "A";
	    }
	},
	Inactivo("I", "Inactivo"){
		public String toString() {
	        return "I";
	    }
	};

	private String valorEnum;
	private String nombreEnum;
	
	private EstadoMaterial(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static EstadoMaterial convertir(String valorEnum) {
		
		if(valorEnum != null && !valorEnum.isEmpty()) {
			for(EstadoMaterial estado : EstadoMaterial.values()) {
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(EstadoMaterial estado : EstadoMaterial.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoMaterial estado : EstadoMaterial.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	public static String convertirTexto(EstadoMaterial EstadoMaterial){
		if(EstadoMaterial != null){
			for(EstadoMaterial estado : EstadoMaterial.values()){
				if(estado.getValorEnum() == EstadoMaterial.getValorEnum()){
					return estado.getNombreEnum();
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
