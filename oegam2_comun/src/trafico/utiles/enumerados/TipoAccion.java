package trafico.utiles.enumerados;

public enum TipoAccion {

	Impresion417("IMPRESION417", "Impreso PDF 417") {
		public String toString() {
			return "IMPRESION417";
		}
	},
	BajaExportacionExcel("BAJAEXPORTACIONEXCEL", "Baja exportada con motivo tránsito comunitario o exportación") {
		public String toString() {
			return "BAJAEXPORTACIONEXCEL";
		}
	},
	Impresion430("IMPRESION430", "Impreso PDF 430") {
		public String toString() {
			return "IMPRESION430";
		}
	},
	ImpresionIVTM("IMPRESIONIVTM", "Impreso IVTM") {
		public String toString() {
			return "IMPRESIONIVTM";
		}
	},
	SolicitudAVPO("SOLICITUDAVPO", "Solicitud AVPO") {
		public String toString() {
			return "SOLICITUDAVPO";
		}
	},
	ReinicioAVPO("REINICIAR SOLICITUD", "Reinicio AVPO") {
		public String toString() {
			return "REINICIAR SOLICITUD";
		}
	},
	GeneracionXML620("GENERACIONXML620", "Generación XML del 620") {
		public String toString() {
			return "GENERACIONXML620";
		}
	},
	Presentacion576("PRESENTACION576", "Presentación del 576") {
		public String toString() {
			return "PRESENTACION576";
		}
	},
	ObtenerCEM("OBTENERCEM", "Obtención del CEM") {
		public String toString() {
			return "OBTENERCEM";
		}
	},
	MATEEITV("MATEEITV", "Consulta EITV") {
		public String toString() {
			return "MATEEITV";
		}
	},
	ISSUEPROPROOF("ISSUEPROPROOF", "Emisión Justificante Profesional") {
		public String toString() {
			return "ISSUEPROPROOF";
		}
	},
	GETPROPROOF("GETPROPROOF", "Obtención Justificante Profesional") {
		public String toString() {
			return "GETPROPROOF";
		}
	},
	SOLICITUDINTEVE("SOLICITUDINTEVE", "Solicitud INTEVE") {
		public String toString() {
			return "SOLICITUDINTEVE";
		}
	},
	SOLICITUDINTEVE3("SOLICITUDINTEVE3", "Solicitud INTEVE 3") {
		public String toString() {
			return "SOLICITUDINTEVE3";
		}
	},
	ReinicioINTEVE("REINICIAR INTEVE", "Reinicio INTEVE") {
		public String toString() {
			return "REINICIAR INTEVE";
		}
	},
	ATEM("ATEM", "ATEM") {
		public String toString() {
			return "ATEM";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoAccion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	// valueOf
	public static TipoAccion convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if ("GETPROPROOF".equals(valorEnum))
			return GETPROPROOF;
		else if ("ISSUEPROPROOF".equals(valorEnum))
			return ISSUEPROPROOF;
		else if ("MATEEITV".equals(valorEnum))
			return MATEEITV;
		else if ("OBTENERCEM".equals(valorEnum))
			return ObtenerCEM;
		else if ("PRESENTACION576".equals(valorEnum))
			return Presentacion576;
		else if ("GENERACIONXML620".equals(valorEnum))
			return GeneracionXML620;
		else if ("REINICIOAVPO".equals(valorEnum))
			return ReinicioAVPO;
		else if ("SOLICITUDAVPO".equals(valorEnum))
			return SolicitudAVPO;
		else if ("IMPRESIONIVTM".equals(valorEnum))
			return ImpresionIVTM;
		else if ("BAJAEXPORTACIONEXCEL".equals(valorEnum))
			return BajaExportacionExcel;
		else if ("IMPRESION430".equals(valorEnum))
			return Impresion430;
		else if ("IMPRESION417".equals(valorEnum))
			return Impresion417;
		else if ("SOLICITUDINTEVE".equals(valorEnum))
			return SOLICITUDINTEVE;
		else if ("ReinicioINTEVE".equals(valorEnum))
			return ReinicioINTEVE;
		else
			return null;
	}
}