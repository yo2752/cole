package org.gestoresmadrid.core.impresion.model.enumerados;

public enum TipoDocumentoImpresiones {

	ListadoBastidores("ListadoBastidores", "Listado de Bastidores", "LBastidores") {
		public String toString() {
			return "ListadoBastidores";
		}
	},
	BorradorPDF417("BorradorPDF417", "Borrador PDF 417", "BorrPDF417") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	PDF417("PDF417", "PDF 417", "PDF417") {
		public String toString() {
			return "PDF417";
		}
	},
	PDFPresentacionTelematica("PDFPresentacionTelematica", "PDF Presentacion Telemática", "PDFTel") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	MandatoGenerico("MandatoGenerico", "Mandato Genérico", "MandGen") {
		public String toString() {
			return "MandatoGenerico";
		}
	},
	MandatoEspecifico("MandatoEspecifico", "Mandato Específico", "MandEsp") {
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	TransmisionModelo430("Modelo430", "Transmisión Modelo 430", "Modelo430") {
		public String toString() {
			return "Modelo430";
		}
	},
	DeclaracionJuradaExportacionTransito("DeclaracionJuradaExportacionTransito", "Declaración Jurada Exportacion Tránsito", "DeclJurExportTran") {
		public String toString() {
			return "DeclaracionJuradaExportacionTransito";
		}
	},
	DeclaracionJuradaExtravioFicha("DeclaracionJuradaExtravioFicha", "Declaración Jurada Extravio Ficha", "DeclJurExtrFic") {
		public String toString() {
			return "DeclaracionJuradaExtravioFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoFicha("DeclaracionJuradaExtravioPermisoFicha", "Declaración Jurada Extravio Permiso Ficha", "DeclJurPermFic") {
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoLicencia("DeclaracionJuradaExtravioPermisoLicencia", "Declaración Jurada Extravio Permiso Licencia", "DeclJurExtPerLic") {
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoLicencia";
		}
	},
	DeclaracionJuradaEntregaAnteriorPermiso("DeclaracionJuradaEntregaAnteriorPermiso", "Declaración Jurada Entrega Anterior Permiso", "DeclJurEntAntPerm") {
		public String toString() {
			return "DeclaracionJuradaEntregaAnteriorPermiso";
		}
	},
	DocumentoBase("DOC_BASE", "Documento Base", "documentoBase") {
		public String toString() {
			return "DOC_BASE";
		}
	},
	DeclaracionResponsabilidadColegiado("DeclaracionResponsabilidadColegiado", "Declaración Responsabilidad Colegiado", "DeclRespColegiado") {
		public String toString() {
			return "DeclaracionResponsabilidadColegiado";
		}
	},
	DeclaracionResponsabilidadColegiadoConducir("DeclaracionResponsabilidadColegiadoConducir", "Declaración Responsabilidad Colegiado DPC", "DeclRespColegiadoConducir") {
		public String toString() {
			return "DeclaracionResponsabilidadColegiadoConducir";
		}
	},
	SolicitudPermisoInter("SolicitudPermisoInter", "Solicitud Permiso Internacional", "SolicitudPI") {
		public String toString() {
			return "SolicitudPermisoInter";
		}
	},
	SolicitudDuplicadoPermisoConducir("SolicitudDuplicadoPermisoConducir", "Solicitud Duplicado Permiso Conducir", "SolicitudDPC") {
		public String toString() {
			return "SolicitudDuplicadoPermisoConducir";
		}
	},
	DeclaracionResponsabilidadTitularConducir("DeclaracionResponsabilidadTitularConducir", "Declaración Responsabilidad Titular DPC", "DeclRespTitularConducir") {
		public String toString() {
			return "DeclaracionResponsabilidadTitularConducir";
		}
	},
	CarnetConducir("CarnetConducir", "Carnet Conducir", "CarnetConducir") {
		public String toString() {
			return "CarnetConducir";
		}
	},
	CarnetIdentidad("CarnetIdentidad", "Carnet Identidad", "CarnetIdentidad") {
		public String toString() {
			return "CarnetIdentidad";
		}
	},
	PermisoInternacional("PermisoInternacional", "Permiso Internacional", "PermisoInternacional") {
		public String toString() {
			return "PermisoInternacional";
		}
	},
	OtroDocumentoPI("toString", "Otro Documento PI", "OtroDocumentoPI") {
		public String toString() {
			return "OtroDocumentoPI";
		}
	},
	OtroDocumentoDPC("OtroDocumentoDPC", "Otro Documento DPC", "OtroDocumentoDPC") {
		public String toString() {
			return "OtroDocumentoDPC";
		}
	},
	TXTNRE("TXTNRE", "TXT NRE", "TXTNRE") {
		public String toString() {
			return "TXTNRE";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String nombreDocumento;

	private TipoDocumentoImpresiones(String valorEnum, String nombreEnum, String nombreDocumento) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.nombreDocumento = nombreDocumento;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoDocumentoImpresiones tipo : TipoDocumentoImpresiones.values()) {
				if (tipo.getValorEnum().equals(valor)) {
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}
}