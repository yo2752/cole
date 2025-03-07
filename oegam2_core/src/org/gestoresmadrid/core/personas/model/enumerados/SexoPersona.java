package org.gestoresmadrid.core.personas.model.enumerados;

public enum SexoPersona {
	
	Varon("V", "V"){
		public String toString() {
	        return "V";
	    }
	},
	Hembra("H", "H")
	{
		public String toString() {
	        return "H";
	    }
	},
	Juridica("X", "X")
	{
		public String toString() {
	        return "X";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private SexoPersona(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static SexoPersona convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(SexoPersona sexo : SexoPersona.values()){
				if(sexo.getValorEnum().equals(valorEnum)){
					return sexo;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(SexoPersona sexo : SexoPersona.values()){
				if(sexo.getValorEnum().equals(valor)){
					return sexo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(SexoPersona sexo : SexoPersona.values()){
				if(sexo.getNombreEnum().equals(nombre)){
					return sexo.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(SexoPersona sexoPersona){
		if(sexoPersona != null){
			for(SexoPersona sexo : SexoPersona.values()){
				if(sexo.getValorEnum() == sexoPersona.getValorEnum()){
					return sexo.getNombreEnum();
				}
			}
		}
		return null;
	}

}
