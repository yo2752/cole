package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados;

public enum TipoSolicitudPlacasEnum {
	
	Solicitud_Placa_Ordinaria_Larga("T131","Solicitud Placa Ordinaria Larga"){
		public String toString(){
			return "T131"; 
		}
	},
	Solicitud_Placa_Ordinaria_Corta_Alfa_Romeo("T132","Solicitud Placa Ordinaria Corta Alfa Romeo"){
		public String toString(){
			return "T132"; 
		}
	},
	Solicitud_Placa_Motocicleta("T133","Solicitud Placa Motocicleta"){
		public String toString(){
			return "T133";
		}
	},
	Solicitud_Placa_Motocicleta_Trial("T134","Solicitud Placa Motocicleta Trial"){
		public String toString(){
			return "T134";
		}
	},
	Solicitud_Placa_Vehiculo_Especial("T135","Solicitud Placa Vehiculo Especial"){
		public String toString(){
			return "T135";
		}
	},
	Solicitud_Placa_Ciclomotor("T136","Solicitud Placa Ciclomotor"){
		public String toString(){
			return "T136";
		}
	},
	Solicitud_Placa_Ordinaria_Alta("T137","Solicitud Placa Ordinaria Alta"){
		public String toString(){
			return "T137";
		}
	},
	Solicitud_Placa_TaxiVTC("T138","Solicitud Placa Taxi/VTC"){
		public String toString(){
			return "T138";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private TipoSolicitudPlacasEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoSolicitudPlacasEnum convertir(String valorEnum) {
		for (TipoSolicitudPlacasEnum element : TipoSolicitudPlacasEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;	
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoSolicitudPlacasEnum element : TipoSolicitudPlacasEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoSolicitudPlacasEnum element : TipoSolicitudPlacasEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	 
	 	
}
