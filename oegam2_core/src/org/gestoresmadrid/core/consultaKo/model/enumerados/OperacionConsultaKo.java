package org.gestoresmadrid.core.consultaKo.model.enumerados;

public enum OperacionConsultaKo {
	CREACION("CR","CREACION"){
		public String toString() {
			return "CR";
		}
	},
	GENERADO("GE","GENERADO EXCEL"){
		public String toString() {
			return "GE";
		}
	},
	RECIBIDO("RE","RECIBIDO"){
		public String toString() {
			return "RE";
		}
	},
	CAMBIO_ESTADO("CB","CAMBIO ESTADO"){
		public String toString() {
			return "CB";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;

	private OperacionConsultaKo(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public static OperacionConsultaKo convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(OperacionConsultaKo tipo : OperacionConsultaKo.values()){
				if(tipo.getValorEnum().equals(valorEnum)){
					return tipo;
				}
			}
		}
		return null;
	}
	
	public static String convertirValor(String valor){
		if(valor != null){
			for(OperacionConsultaKo tipo : OperacionConsultaKo.values()){
				if(tipo.getValorEnum().equals(valor)){
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(OperacionConsultaKo tipo : OperacionConsultaKo.values()){
				if(tipo.getNombreEnum().equals(nombre)){
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}
	
}
