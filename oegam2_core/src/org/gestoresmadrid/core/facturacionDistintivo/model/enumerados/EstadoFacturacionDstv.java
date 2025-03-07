package org.gestoresmadrid.core.facturacionDistintivo.model.enumerados;

public enum EstadoFacturacionDstv {
	Facturado("0", "Facturado"){
		public String toString() {
	        return "0";
	    }
	},
	Incidencia("1", "Incidencia"){
		public String toString() {
	        return "1";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private EstadoFacturacionDstv(String valorEnum, String nombreEnum) {
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
	
	public static EstadoFacturacionDstv convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoFacturacionDstv estado : EstadoFacturacionDstv.values()){
				if(estado.getValorEnum().equals(valorEnum)){
					return estado;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(EstadoFacturacionDstv estado : EstadoFacturacionDstv.values()){
				if(estado.getValorEnum().equals(valor)){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
}
