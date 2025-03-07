package org.gestoresmadrid.placas.utilities.enumerados;

public enum TipoCreditoPlacasEnum {
	
	Credito_Ordinaria_Larga("CP01", "Crédito para placas de turismo ordinarias largas"){
		public String toString() {
			return "CP01";
		}
	},
	Credito_Ordinaria_Corta_Alfa_Romeo("CP02", "Crédito para placas de Alfa Romeo"){
		public String toString() {
			return "CP02";
		}
	},
	Credito_Motocicleta("CP03", "Crédito para placas de Motocicleta"){
		public String toString() {
			return "CP03";
		}
	},
	Credito_Motocicleta_Trial("CP04", "Crédito para placas de Motocicleta Trial"){
		public String toString() {
			return "CP04";
		}
	},
	Credito_Vehiculo_Especial("CP05", "Crédito para placas de vehículos especiales"){
		public String toString() {
			return "CP05";
		}
	},
	Credito_Ciclomotor("CP06", "Turismo Ordinaria Larga"){
		public String toString() {
			return "CP06";
		}
	};
	
	String valorEnum;
	String nombreEnum;
	
	private TipoCreditoPlacasEnum(String valorEnum, String nombreEnum){
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	public static TipoCreditoPlacasEnum convertir(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero=new Integer(valorEnum);
		switch (numero) {
		case 1: 
			return Credito_Ordinaria_Larga;
		case 2: 
			return Credito_Ordinaria_Corta_Alfa_Romeo;
		case 3:
			return Credito_Motocicleta;
		case 4:
			return Credito_Motocicleta_Trial;
		case 5:
			return Credito_Vehiculo_Especial;
		case 6:
			return Credito_Ciclomotor;
		default:
			return null;
		}
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
	
}
