package org.gestoresmadrid.core.modelos.model.enumerados;

public enum TipoDocumentoModelo600601 {

	Publico("PUB","Público"){
		public String toString(){
			return "PUB";
		}
	},
	Privado("PRI","Privado"){
		public String toString(){
			return "PRI";
		}
	},
	Judicial("JUD","Judicial"){
		public String toString(){
			return "JUD";
		}
	},
	Administrativo("ADM","Administrativo"){
		public String toString(){
			return "ADM";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoDocumentoModelo600601(String valorEnum, String nombreEnum) {
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
	
	public static TipoDocumentoModelo600601 convertir(String valor){
		if(valor != null && !valor.isEmpty()){
			for(TipoDocumentoModelo600601 tipo : TipoDocumentoModelo600601.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static TipoDocumentoModelo600601 convertirPorNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(TipoDocumentoModelo600601 tipo : TipoDocumentoModelo600601.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(TipoDocumentoModelo600601 tipo : TipoDocumentoModelo600601.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
}
