package estadisticas.utiles.enumerados;

public enum Agrupacion {

	Colegiado("1", "Número Colegiado") {
		public String toString() {
			return "1";
		}
	},
	Provincia("2", "Provincia") {
		public String toString() {
			return "2";
		}
	},

	Jefatura("3", "Jefatura") {
		public String toString() {
			return "3";
		}
	},

	Franja_Horaria("4", "Franja Horaria Presentación Trámite") {
		public String toString() {
			return "4";
		}
	},

	Combustible("5", "Combustible") {
		public String toString() {
			return "5";
		}
	},

	Tipo_Transferencia("6", "Tipo Transferencia (Transmisiones)") {
		public String toString() {
			return "6";
		}
	},

	Municipio_Titular("7", "Municipio Titular (Matriculaciones)") {
		public String toString() {
			return "7";
		}
	},

	Exenciones("8", "Exento CEM") {
		public String toString() {
			return "8";
		}
	},

	Servicio("9", "Servicio") {
		public String toString() {
			return "9";
		}
	},

	Tipo_tramite("10", "Tipo trámite") {
		public String toString() {
			return "10";
		}
	},

	Estado("11", "Estado") {
		public String toString() {
			return "11";
		}
	},

	Marca("12", "Marca Vehículo") {
		public String toString() {
			return "12";
		}
	},

	Tipo_vehiculo("13", "Tipo vehículo") {
		public String toString() {
			return "13";
		}
	},

	Tipo_Creacion("14", "Tipo Creación") {
		public String toString() {
			return "14";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Agrupacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Agrupacion convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		Integer numero = new Integer(valorEnum);
		switch (numero) {
		case 1:
			return Colegiado;
		case 2:
			return Provincia;
		case 3:
			return Jefatura;
		case 4:
			return Franja_Horaria;
		case 5:
			return Combustible;
		case 6:
			return Tipo_Transferencia;
		case 7:
			return Municipio_Titular;
		case 8:
			return Exenciones;
		case 9:
			return Servicio;
		case 10:
			return Tipo_tramite;
		case 11:
			return Estado;
		case 12:
			return Marca;
		case 13:
			return Tipo_vehiculo;
		case 14:
			return Tipo_Creacion;
		default:
			return null;
		}
	}

	public static String convertirTexto(String valorEnum) {

		if (valorEnum == null)
			return null;

		Integer numero = new Integer(valorEnum);
		switch (numero) {
			case 1:
				return "Colegiado";
			case 2:
				return "Provincia";
			case 3:
				return "Jefatura";
			case 4:
				return "Franja Horaria";
			case 5:
				return "Combustible";
			case 6:
				return "Tipo Transferencia";
			case 7:
				return "Municipio Titular";
			case 8:
				return "Exenciones";
			case 9:
				return "Servicio";
			case 10:
				return "Tipo trámite";
			case 11:
				return "Estado";
			case 12:
				return "Marca";
			case 13:
				return "Tipo vehículo";
			case 14:
				return "Tipo creación";
			default:
				return null;
		}
	}

	// Devuelve las siglas del mensaje que se debe consignar en tipo de mensaje
	// cuando se

}