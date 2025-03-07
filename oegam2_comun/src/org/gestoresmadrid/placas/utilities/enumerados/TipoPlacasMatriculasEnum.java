package org.gestoresmadrid.placas.utilities.enumerados;

public enum TipoPlacasMatriculasEnum {
	
	Turismo_Ordinaria_Larga("1", "Turismo Ordinaria Larga", "520", "100", "ambos"){
		public String toString() {
			return "1";
		}
	},
	Turismo_Ordinaria_Corta("2", "Turismo Ordinaria Corta", "340", "111", "ambos"){
		public String toString() {
			return "2";
		}
	},

	Motocicleta("3", "Motocicleta", "220", "160", "ambos"){
		public String toString() {
			return "3";
		}
	},

	Moto_Corta("4", "Moto Corta", "132", "96", "ambos"){
		public String toString() {
			return "4";
		}
	},

	Tractor("5", "Tractor", "280", "200", "ambos"){
		public String toString() {
			return "5";
		}
	},

	Ciclomotor("6", "Ciclomotor", "100", "168", "ambos"){
		public String toString() {
			return "6";
		}
	},
	TaxiVTC("8", "Taxi / VTC", "520", "110", "trasera"){
		public String toString() {
			return "8";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	private String ancho;
	private String alto;
	private String location;

	private TipoPlacasMatriculasEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	private TipoPlacasMatriculasEnum(String valorEnum, String nombreEnum, String ancho, String alto, String location) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.alto = alto;
		this.ancho = ancho;
		this.location = location;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public String getAncho() {
		return ancho;
	}

	public String getAlto() {
		return alto;
	}

	public static TipoPlacasMatriculasEnum convertir(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero= new Integer(valorEnum);
		switch (numero) {
		case 1: 
			return Turismo_Ordinaria_Larga;
		case 2: 
			return Turismo_Ordinaria_Corta;
		case 3:
			return Motocicleta;
		case 4:
			return Moto_Corta;
		case 5:
			return Tractor;
		case 6:
			return Ciclomotor;
		case 8:
			return TaxiVTC; 
		default:
			return null;
		}
	}
	
	public static String convertir(Integer valorEnum) {    

		if(valorEnum==null)
			return null;

		switch (valorEnum) {
		case 1: 
			return "Turismo Ordinaria Larga";
		case 2: 
			return "Turismo Ordinaria Corta";
		case 3:
			return "Motocicleta";
		case 4:
			return "Moto Corta";
		case 5:
			return "Tractor";
		case 6:
			return "Ciclomotor";
		case 8:
			return "Taxi / VTC";
		default:
			return null;
		}
	}
	
	public static String convertirTexto(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero= new Integer(valorEnum);
		return convertir(numero);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
