package org.gestoresmadrid.placas.utilities.enumerados;

public enum EstadoSolicitudPlacasEnum {
	
	Borrada ("0", "Borrada"){
		public String toString() {
			return "0";
		}
	},
	Iniciado("1", "Iniciado"){
		public String toString() {
			return "1";
		}
	},
	Confirmado("2", "Confirmado"){
		public String toString() {
			return "2";
		}
	},

	Tramitado("3", "Tramitado"){
		public String toString() {
			return "3";
		}
	},

	Finalizado("4", "Finalizado"){
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoSolicitudPlacasEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoSolicitudPlacasEnum convertir(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero= new Integer(valorEnum);
		switch (numero) {
		case 1: 
			return Iniciado;
		case 2: 
			return Confirmado;
		case 3:
			return Tramitado;
		case 4:
			return Finalizado;
		default:
			return null;
		}
	}

	public static String convertirTexto(String valorEnum) {    

		if(valorEnum==null)
			return null;

		Integer numero= new Integer(valorEnum);
		switch (numero) {
		case 1: 
			return "Iniciado";
		case 2: 
			return "Confirmado";
		case 3:
			return "Tramitado";
		case 4:
			return "Finalizado";
		default:
			return null;
		}
	}

}
