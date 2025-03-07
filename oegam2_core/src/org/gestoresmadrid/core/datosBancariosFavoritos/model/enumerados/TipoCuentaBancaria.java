package org.gestoresmadrid.core.datosBancariosFavoritos.model.enumerados;

public enum TipoCuentaBancaria {

	NUEVA("0","Nueva"){
		public String toString(){
			return "M600";
		}
	},
	EXISTENTE("1","Existente"){
		public String toString(){
			return "M601";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoCuentaBancaria(String valorEnum, String nombreEnum) {
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
	
	public static TipoCuentaBancaria convertir(String valor){
		if(valor != null && !valor.isEmpty()){
			for(TipoCuentaBancaria tipo : TipoCuentaBancaria.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static TipoCuentaBancaria convertirPorNombre(String nombre){
		if(nombre != null && !nombre.isEmpty()){
			for(TipoCuentaBancaria tipo : TipoCuentaBancaria.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(TipoCuentaBancaria tipo : TipoCuentaBancaria.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
}
