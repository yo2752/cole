package org.gestoresmadrid.core.model.enumerados;

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
	JustificantesProfesionales("T9", "JUSTIFICANTES PROFESIONALES") {
		public String toString() {
			return "T9";
		}
	},
	Inteve("T10", "Inteve") {
		public String toString() {
			return "T10";
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

	Consulta_IVTM("T14", "CONSUTA IVTM") {
		public String toString() {
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
	CompraTasas("C1", "COMPRA_TASAS") {
		public String toString() {
			return "C1";
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
	Solicitud_Fichas_Permisos("T22", "Solicitud_Fichas_Permisos") {
		public String toString() {
			return "T22";
		}
	},
	Solicitud_Distintivo("T23", "SOLICITUD_DISTINTIVO") {
		public String toString() {
			return "T23";
		}
	},
	Solicitud_Permiso("T24", "Solicitud_Permiso") {
		public String toString() {
			return "T24";
		}
	},
	Solicitud_Ficha_Tecnica("T25", "Solicitud_Ficha_Tecnica") {
		public String toString() {
			return "T25";
		}
	},
	Licencias_CAM("T26", "Licencias_CAM") {
		public String toString() {
			return "T26";
		}
	},
	CambioServicio("T27", "CAMBIO SERVICIO") {
		public String toString() {
			return "T27";
		}
	},
	PermisonInternacional("T28", "PERMISO INTERNACIONAL") {
		public String toString() {
			return "T28";
		}
	},
	IMPR_KO("T29", "IMPR KO") {
		public String toString() {
			return "T29";
		}
	},
	DuplicadoPermisoConducir("T30", "DUPLICADO PERMISO CONDUCIR") {
		public String toString() {
			return "T30";
		}
	},
	Matriculacion_AM("T1AM", "MATRICULACION_AM") {
		public String toString() {
			return "T1AM";
		}
	},
	CTIT_AM("T7AM", "CTIT_AM") {
		public String toString() {
			return "T7AM";
		}
	},
	BAJA_AM("T3AM", "BAJA_AM") {
		public String toString() {
			return "T3AM";
		}
	},
	DUPLICADO_AM("T8AM", "DUPLICADO_AM") {
		public String toString() {
			return "T8AM";
		}
	},
	IMPRESION_CANJES("T31", "Impresion canjes") {
		public String toString() {
			return "T31";
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

	public static TipoTramiteTrafico convertirPorNombre(String valorEnum) {
		for (TipoTramiteTrafico element : TipoTramiteTrafico.values()) {
			if (element.nombreEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum != null) {
			for (TipoTramiteTrafico element : TipoTramiteTrafico.values()) {
				if (element.valorEnum.equals(valorEnum)) {
					return element.nombreEnum;
				}
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
