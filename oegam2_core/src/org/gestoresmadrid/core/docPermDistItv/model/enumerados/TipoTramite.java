package org.gestoresmadrid.core.docPermDistItv.model.enumerados;

public enum TipoTramite {

	Matriculacion("T1","MATRICULACION"){
		public String toString() {
			return "T1";
		}
	},
	Baja("T3", "BAJA") {
		public String toString() {
			return "T3";
		}
	},
	TransmisionElectronica("T7","TRANSMISION ELECTRONICA"){
		public String toString() {
			return "T7";
		}
	
	},
	Duplicado("T8", "DUPLICADO") {
		public String toString() {
			return "T8";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;

	private TipoTramite(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static TipoTramite convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoTramite tipo : TipoTramite.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(TipoTramite tipo : TipoTramite.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoTramite tipo : TipoTramite.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
}
