package org.gestoresmadrid.core.modelos.model.enumerados;

public enum Modelo {

	Modelo600("600", "A1")
	{
		public String toString() {
	        return "600";
	    }
	},
	Modelo601("601", "A1")
	{
		public String toString() {
	        return "601";
	    }
	};

	private String valorEnum;
	private String version;
	
	private Modelo(String valorEnum, String version) {
		this.valorEnum = valorEnum;
		this.version = version;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getVersion() {
		return version;
	}

	
	public static Modelo convertirTexto(String valor){
		if(valor != null && !valor.isEmpty()){
			for(Modelo modelo : Modelo.values()){
				if(modelo.getValorEnum().equals(valor)){
					return modelo;
				}
			}
		}
		return null;
	}
}
