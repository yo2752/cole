package trafico.utiles.enumerados;

public enum TipoTramiteTrafico {

	Matriculacion("T1", "MATRICULACION") {
		public String toString() {
			return "T1";
		}
	},
	Transmision("T2", "TRANSMISION") {
		public String toString() {
			return "T2";
		}
	},
	Baja("T3", "BAJA") {
		public String toString() {
			return "T3";
		}
	},
	Solicitud("T4", "SOLICITUD") {
		public String toString() {
			return "T4";
		}
	},
	TransmisionElectronica("T7", "TRANSMISION ELECTRONICA") {
		public String toString() {
			return "T7";
		}
	},
	Duplicado("T8", "DUPLICADO") {
		public String toString() {
			return "T8";
		}
	},
	Anuntis("T11", "ANUNTIS") {
		public String toString() {
			return "T11";
		}
	},
	Legalizacion("T12", "LEGALIZACION") {
		public String toString() {
			return "T12";
		}
	},
	Solicitud_Placas("T13", "SOLICITUD PLACAS") {
		public String toString() {
			return "T13";
		}
	},
	//TODO MPC. Cambio IVTM.
	// Descomentar esto en la versión final IVTM

	Consulta_IVTM("T14","CONSULTA IVTM"){
		public String toString(){
			return "T14"; 
		}
	},
	consultaEEFF("T15", "CONSULTAEEFF") {
		public String toString() {
			return "T15";
		}
	},
	Sancion("T16", "SANCION") {
		public String toString() {
			return "T16";
		}
	},
	ConsultaEITV("T17", "CONSULTA EITV") {
		public String toString() {
			return "T17";
		}
	},
	FacturacionTasa("T18", "FACTURACION TASA") {
		public String toString() {
			return "T18";
		}
	},
	Solicitud_Inteve("T40", "SOLICITUD INTEVE") {
		public String toString() {
			return "T40";
		}
	},
	Consulta_Dev("T19", "CONSULTA DEV") {
		public String toString() {
			return "T19";
		}
	},
	CambioServicio("T27", "CAMBIO SERVICIO") {
		public String toString() {
			return "T27";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTramiteTrafico(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTramiteTrafico convertir(String valorEnum) {
		for (TipoTramiteTrafico element : TipoTramiteTrafico.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoTramiteTrafico element : TipoTramiteTrafico.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoTramiteTrafico element : TipoTramiteTrafico.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
}