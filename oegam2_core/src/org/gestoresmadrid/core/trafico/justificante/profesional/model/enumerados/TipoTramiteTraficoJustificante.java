package org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados;


public enum TipoTramiteTraficoJustificante {
	
	TransmisionElectronica("T7","TRANSMISION ELECTRONICA"){
		public String toString(){
			return "T7"; 
		}
	},
	Duplicado("T8","DUPLICADO"){
		public String toString(){
			return "T8"; 
		}
	}; 
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoTramiteTraficoJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTramiteTraficoJustificante convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(TipoTramiteTraficoJustificante tipo : TipoTramiteTraficoJustificante.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(TipoTramiteTraficoJustificante tipo : TipoTramiteTraficoJustificante.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoTramiteTraficoJustificante tipo : TipoTramiteTraficoJustificante.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(TipoTramiteTraficoJustificante tipoTramite){
		if(tipoTramite != null){
			for(TipoTramiteTraficoJustificante tipo : TipoTramiteTraficoJustificante.values()){
				if(tipo.getValorEnum() == tipoTramite.getValorEnum()){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}

}
