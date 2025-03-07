package org.gestoresmadrid.core.consultaKo.model.enumerados;


public enum TipoTramiteKo {

	Matriculacion("T1","MATRICULACION"){
		public String toString() {
			return "T1";
		}
	},
	TransmisionElectronica("T7","TRANSMISION ELECTRONICA"){
		public String toString() {
			return "T7";
		}
	
	};
	
	private String valorEnum;
	private String nombreEnum;

	private TipoTramiteKo(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static TipoTramiteKo convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoTramiteKo tipo : TipoTramiteKo.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(TipoTramiteKo tipo : TipoTramiteKo.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoTramiteKo tipo : TipoTramiteKo.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoTramiteKo element : TipoTramiteKo.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

}
