package org.gestoresmadrid.core.modelos.model.enumerados;

public enum DerechoReal {

	Nuda_Propiedad("NUDA PROPIEDAD", "Nuda Propiedad"){
		public String toString() {
	        return "NUDA PROPIEDAD";
	    }
	},
	Uso_Habitacion("USO O HABITACION", "Uso ó Habitación")
	{
		public String toString() {
	        return "USO O HABITACION";
	    }
	},
	Usufructo("USUFRUCTO", "Usufructo"){
		public String toString() {
	        return "USUFRUCTO";
	    }
	},Otros("OTROS", "Otros"){
		public String toString() {
	        return "OTROS";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;

	private DerechoReal(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static DerechoReal convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(DerechoReal derechoReal : DerechoReal.values()){
				if(derechoReal.getValorEnum().equals(valorEnum)){
					return derechoReal;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(DerechoReal derechoReal : DerechoReal.values()){
				if(derechoReal.getValorEnum().equals(valor)){
					return derechoReal.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(DerechoReal derechoReal){
		if(derechoReal != null){
			for(DerechoReal derecho : DerechoReal.values()){
				if(derecho.getValorEnum() == derechoReal.getValorEnum()){
					return derecho.getNombreEnum();
				}
			}
		}
		return null;
	}
}
