package org.gestoresmadrid.core.modelos.model.enumerados;

public enum TipoTramiteModelos {

	Remesa600("R600","REMESA_600"){
		public String toString(){
			return "R600";
		}
	},
	Remesa601("R601","REMESA_601"){
		public String toString(){
			return "R601";
		}
	},
	Modelo600("M600","MODELO_600"){
		public String toString(){
			return "M600";
		}
	},
	Modelo601("M601","MODELO_601"){
		public String toString(){
			return "M601";
		}
	},BIEN_URBANO("BU","BIEN_URBANO"){
		public String toString(){
			return "BU";
		}
	},BIEN_RUSTICO("BR","BIEN_RUSTICO"){
		public String toString(){
			return "BR";
		}
	},OTRO_BIEN("OB","OTRO_BIEN"){
		public String toString(){
			return "OB";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoTramiteModelos(String valorEnum, String nombreEnum) {
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
	
	public static TipoTramiteModelos convertir(String valor){
		if(valor != null && !valor.isEmpty()){
			for(TipoTramiteModelos tipo : TipoTramiteModelos.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static TipoTramiteModelos convertirPorNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(TipoTramiteModelos tipo : TipoTramiteModelos.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(TipoTramiteModelos tipo : TipoTramiteModelos.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
}
