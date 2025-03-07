package org.gestoresmadrid.core.enumerados;

/**
 * Encapsula todos los valores posibles para el motivo de la consulta de los informes INTEVE
 * 
 */
public enum EstadoTasa {

	CREACION("0", "Informe completo") {
		public String toString() {
			return "0";
		}
	},
	ASIGNAR("1", "Informe datos tecnicos") {
		public String toString() {
			return "Informe datos tecnicos";
		}
	},
	DESASIGNAR("2", "Informe cargas") {
		public String toString() {
			return "Informe cargas";
		}
	};

	private String nombreEnum;
	private String valorEnum;

	private EstadoTasa(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoTasa convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(EstadoTasa tipoInformeInteve : EstadoTasa.values()){
				if(tipoInformeInteve.getValorEnum().equals(valorEnum)){
					return tipoInformeInteve;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(EstadoTasa estadoTasa : EstadoTasa.values()){
				if(estadoTasa.getValorEnum().equals(valor)){
					return estadoTasa.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(EstadoTasa estadoTasa : EstadoTasa.values()){
				if(estadoTasa.getNombreEnum().equals(nombre)){
					return estadoTasa.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(EstadoTasa estadoTasa){
		if(estadoTasa != null){
			for(EstadoTasa estado : EstadoTasa.values()){
				if(estado.getValorEnum() == estadoTasa.getValorEnum()){
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}
	
}
