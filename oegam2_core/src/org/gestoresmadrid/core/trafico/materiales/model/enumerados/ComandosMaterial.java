package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum ComandosMaterial {
	PEDIDOCREAR("1", "pedidoCrear"){
		public String toString() {
	        return "1";
	    }
	},
	PEDIDODETALLE("2", "pedidoDetalle"){
		public String toString() {
	        return "2";
	    }
	},
	INCIDENCIACREAR("5", "incidenciaCrear"){
		public String toString() {
	        return "5";
	    }
	},
	INCIDENCIAUPDATE("6", "incidenciaUpdate"){
		public String toString() {
	        return "6";
	    }
	},
	INFOPEDIDO("7", "infoPedido"){
		public String toString() {
	        return "7";
	    }
	},
	INFOSTOCK("8", "infoStock"){
		public String toString() {
	        return "8";
	    }
	},
	UPDATESTOCK("9", "stockUpdate"){
		public String toString() {
	        return "9";
	    }
	};

	private String valorEnum;
	private String nombreEnum;
	
	private ComandosMaterial(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static ComandosMaterial convertir(String valorEnum) {
		
		if(valorEnum != null && !valorEnum.isEmpty()) {
			for(ComandosMaterial estado : ComandosMaterial.values()) {
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(ComandosMaterial estado : ComandosMaterial.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(ComandosMaterial estado : ComandosMaterial.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	public static String convertirTexto(ComandosMaterial EstadoMaterial){
		if(EstadoMaterial != null){
			for(ComandosMaterial estado : EstadoMaterial.values()){
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
