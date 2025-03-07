package trafico.utiles.enumerados;

public enum MotivoJustificante {

	DniCaducado("DOI001", "DNI CADUCADO") {
		public String toString() {
			return "DOI001";
		}
	},
	CifCertificadoPoderes("DOI002", "CIF, CERTIFICADO DE PODERES") {
		public String toString() {
			return "DOI002";
		}
	},
	AcreditacionDomicilio("DOI003", "ACREDITACION DE DOMICILIO") {
		public String toString() {
			return "DOI003";
		}
	},
	DatosDgt("DOI004", "DATOS DGT: ERROR FECHA NACIMIENTO / NO CONSTA EN BASES DE DATOS AEAT") {
		public String toString() {
			return "DOI004";
		}
	},
	Factura("FAC001", "FACTURA") {
		public String toString() {
			return "FAC001";
		}
	},
	ContratoCompraventa("FAC002", "CONTRATO COMPRAVENTA") {
		public String toString() {
			return "FAC002";
		}
	},
	PendienteRoma("FAC003", "PENDIENTE ROMA (MAQUINARIA AGRICOLA)") {
		public String toString() {
			return "FAC003";
		}
	},
	PendienteCertificado("FAC004", "PENDIENTE CERTIFICADO TRANSPORTES") {
		public String toString() {
			return "FAC004";
		}
	},
	Itp("IMP001", "ITP") {
		public String toString() {
			return "IMP001";
		}
	},
	Sucesiones("IMP002", "SUCESIONES") {
		public String toString() {
			return "IMP002";
		}
	},
	ImpagoIvtm("IMP003", "IMPAGO IVTM") {
		public String toString() {
			return "IMP003";
		}
	},
	AnotacionDua("IMP004", "ANOTACION DUA") {
		public String toString() {
			return "IMP004";
		}
	},
	ReservaDominio("DOM001", "RESERVA DE DOMINIO NO CANCELADA EN REGISTRO MERCANTIL") {
		public String toString() {
			return "DOM001";
		}
	},
	NoTramitable("CAL001", "NO TRAMITABLE") {
		public String toString() {
			return "CAL001";
		}
	},
	TramitableJefatura("CAL002", "TRAMITABLE EN JEFATURA") {
		public String toString() {
			return "CAL002";
		}
	},
	NoTelematico("CAL003", "TRAMITE NO TELEMATICO (REFORMAS)") {
		public String toString() {
			return "CAL003";
		}
	},
	TramitableIncidencias("CAL004", "TRAMITABLE CON INCIDENCIAS") {
		public String toString() {
			return "CAL004";
		}
	},
	TramiteManual("CAL005", "TRAMITE MANUAL SIN PDF / ULTIMACIONES") {
		public String toString() {
			return "CAL005";
		}
	},
	AcreditacionActividad("VA001", "ACREDITACION ACTIVIDAD (IAE)") {
		public String toString() {
			return "VA001";
		}
	},
	ItvReforma("VA002", "TRANSFERENCIA ITV- REFORMA/CAMBIO SERVICIO") {
		public String toString() {
			return "VA002";
		}
	},
	ItvNoAnotada("VA003", "TRANSFERENCIA ITV NO ANOTADA EN DGT") {
		public String toString() {
			return "VA003";
		}
	},
	Otros("OT001", "TRANSFERENCIA Otros") {
		public String toString() {
			return "OT001";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private MotivoJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static MotivoJustificante convertir(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else if (valorEnum.equals("DOI001")) {
			return DniCaducado;
		} else if (valorEnum.equals("DOI002")) {
			return CifCertificadoPoderes;
		} else if (valorEnum.equals("DOI003")) {
			return AcreditacionDomicilio;
		} else if (valorEnum.equals("DOI004")) {
			return DatosDgt;
		} else if (valorEnum.equals("FAC001")) {
			return Factura;
		} else if (valorEnum.equals("FAC002")) {
			return ContratoCompraventa;
		} else if (valorEnum.equals("FAC003")) {
			return PendienteRoma;
		} else if (valorEnum.equals("FAC004")) {
			return PendienteCertificado;
		} else if (valorEnum.equals("IMP001")) {
			return Itp;
		} else if (valorEnum.equals("IMP002")) {
			return Sucesiones;
		} else if (valorEnum.equals("IMP003")) {
			return ImpagoIvtm;
		} else if (valorEnum.equals("IMP004")) {
			return AnotacionDua;
		} else if (valorEnum.equals("DOM001")) {
			return ReservaDominio;
		} else if (valorEnum.equals("CAL001")) {
			return NoTramitable;
		} else if (valorEnum.equals("CAL002")) {
			return TramitableJefatura;
		} else if (valorEnum.equals("CAL003")) {
			return NoTelematico;
		} else if (valorEnum.equals("CAL004")) {
			return TramitableIncidencias;
		} else if (valorEnum.equals("CAL005")) {
			return TramiteManual;
		} else if (valorEnum.equals("VA001")) {
			return AcreditacionActividad;
		} else if (valorEnum.equals("VA002")) {
			return ItvReforma;
		} else if (valorEnum.equals("VA003")) {
			return ItvNoAnotada;
		} else if (valorEnum.equals("OT001")) {
			return Otros;
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (MotivoJustificante element : MotivoJustificante.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	// valueOf
	public static MotivoJustificante convertir(Integer valorEnum) {

		if (valorEnum == null)
			return null;

		switch (valorEnum) {
		case 1:
			return DniCaducado;
		case 2:
			return CifCertificadoPoderes;
		case 3:
			return AcreditacionDomicilio;
		case 4:
			return DatosDgt;
		case 5:
			return Factura;
		case 6:
			return ContratoCompraventa;
		case 7:
			return PendienteRoma;
		case 8:
			return PendienteCertificado;
		case 9:
			return Itp;
		case 10:
			return Sucesiones;
		case 11:
			return ImpagoIvtm;
		case 12:
			return AnotacionDua;
		case 13:
			return ReservaDominio;
		case 14:
			return NoTramitable;
		case 15:
			return TramitableJefatura;
		case 16:
			return NoTelematico;
		case 17:
			return TramitableIncidencias;
		case 18:
			return TramiteManual;
		case 19:
			return AcreditacionActividad;
		case 20:
			return ItvReforma;
		case 21:
			return ItvNoAnotada;
		case 22:
			return Otros;
		default:
			return null;
		}
	}

}