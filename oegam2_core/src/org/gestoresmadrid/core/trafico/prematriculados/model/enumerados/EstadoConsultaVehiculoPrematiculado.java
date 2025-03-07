package org.gestoresmadrid.core.trafico.prematriculados.model.enumerados;

public enum EstadoConsultaVehiculoPrematiculado{
	
	NO_SOLICITADO(0, "NO SOLICITADO"){
		public String toString(){
			return "0";
		}
	},
	INICIADO(1, "INICIADO"){
		public String toString() {
	        return "1";
	    }
	},
	PENDIENTE(2, "PENDIENTE RESPUESTA"){
		public String toString() {
	        return "2";
	    }
	},
	ERROR(3, "ERROR"){
		public String toString() {
	        return "3";
	    }
	},
	FINALIZADO(4, "FINALIZADO"){
		public String toString() {
	        return "4";
	    }
	};
	
	private Integer valorEnum;
	private String nombreEnum;
	
	private EstadoConsultaVehiculoPrematiculado(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static EstadoConsultaVehiculoPrematiculado convert(Integer nombreEnum){
		for (EstadoConsultaVehiculoPrematiculado element : EstadoConsultaVehiculoPrematiculado.values()) {
			if (element.getValorEnum().equals(nombreEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static EstadoConsultaVehiculoPrematiculado[] getEstados(){
		return EstadoConsultaVehiculoPrematiculado.values();
	}
}
