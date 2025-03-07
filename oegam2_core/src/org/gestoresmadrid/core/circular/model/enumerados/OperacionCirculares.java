package org.gestoresmadrid.core.circular.model.enumerados;

public enum OperacionCirculares {
	
	REVERTIR("RVT","REVERTIR"){
		public String toString() {
			return "RVT";
		}
	},
	ALTA("AL","ALTA"){
		public String toString() {
			return "AL";
		}
	},
	CAMBIO_ESTADO("CB","CAMBIAR ESTADO"){
		public String toString() {
			return "CBE";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;

	private OperacionCirculares(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static OperacionCirculares convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(OperacionCirculares tipo : OperacionCirculares.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(OperacionCirculares tipo : OperacionCirculares.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(OperacionCirculares tipo : OperacionCirculares.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
}
