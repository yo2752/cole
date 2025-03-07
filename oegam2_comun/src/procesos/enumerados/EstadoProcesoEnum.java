package procesos.enumerados;

public enum EstadoProcesoEnum {
	
	ACTIVO("Activo",1){
		public String toString() {
	        return "Activo";
	    }
	},
	PARADO("Parado",0){
		public String toString() {
	        return "Parado";
	    }
	};

	private EstadoProcesoEnum(String nombreEnum, int valorEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	private Integer valorEnum;
	private String nombreEnum;
	
	public String getNombreEnum() {
		return nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}
	
	public static EstadoProcesoEnum recuperar(Integer valorEnum){
		if(valorEnum == 1){
			return EstadoProcesoEnum.ACTIVO;
		}else if(valorEnum == 0){
			return EstadoProcesoEnum.PARADO;
		}else{
			return null;
		}
	}

}
