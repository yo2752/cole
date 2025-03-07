package org.gestoresmadrid.core.trafico.materiales.model.enumerados;

public enum EstadosProceso {
	Success(new Long(0), "success"){
		public String toString() {
	        return "0";
	    }
	},
	Error(new Long(1), "error"){
		public String toString() {
	        return "1";
	    }
	};
	
	private Long valorEnum;
	private String nombreEnum;
	
	private EstadosProceso(Long valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static EstadosProceso convertir(Long valorEnum) {
		
		if(valorEnum != null) {
			for(EstadosProceso estado : EstadosProceso.values()) {
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(Long valor){
		if(valor != null){
			for(EstadosProceso estado : EstadosProceso.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static Long convertirNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(EstadosProceso estado : EstadosProceso.values()){
				if(estado.getNombreEnum().equals(nombre)){
					return estado.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadosProceso estado){
		if(estado != null){
			for(EstadosProceso item : EstadosProceso.values()){
				if(item.getValorEnum() == estado.getValorEnum()){
					return item.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	// Getters and Setters

	public String getNombreEnum() {
		return nombreEnum;
	}

	public Long getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(Long valorEnum) {
		this.valorEnum = valorEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

}
