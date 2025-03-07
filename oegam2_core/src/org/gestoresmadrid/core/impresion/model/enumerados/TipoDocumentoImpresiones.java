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
	PDFPresentacionTelematica("PDFPresentacionTelematica", "PDF Presentacion Telem�tica", "PDFTel") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	MandatoGenerico("MandatoGenerico", "Mandato Gen�rico", "MandGen") {
		public String toString() {
			return "MandatoGenerico";
		}
	},
	MandatoEspecifico("MandatoEspecifico", "Mandato Espec�fico", "MandEsp") {
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	TransmisionModelo430("Modelo430", "Transmisi�n Modelo 430", "Modelo430") {
		public String toString() {
			return "Modelo430";
		}
	},
	DeclaracionJuradaExportacionTransito("DeclaracionJuradaExportacionTransito", "Declaraci�n Jurada Exportacion Tr�nsito", "DeclJurExportTran") {
		public String toString() {
			return "DeclaracionJuradaExportacionTransito";
		}
	},
	DeclaracionJuradaExtravioFicha("DeclaracionJuradaExtravioFicha", "Declaraci�n Jurada Extravio Ficha", "DeclJurExtrFic") {
		public String toString() {
			return "DeclaracionJuradaExtravioFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoFicha("DeclaracionJuradaExtravioPermisoFicha", "Declaraci�n Jurada Extravio Permiso Ficha", "DeclJurPermFic") {
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoLicencia("DeclaracionJuradaExtravioPermisoLicencia", "Declaraci�n Jurada Extravio Permiso Licencia", "DeclJurExtPerLic") {
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoLicencia";
		}
	},
	DeclaracionJuradaEntregaAnteriorPermiso("DeclaracionJuradaEntregaAnteriorPermiso", "Declaraci�n Jurada Entrega Anterior Permiso", "DeclJurEntAntPerm") {
		public String toString() {
			return "DeclaracionJuradaEntregaAnteriorPermiso";
		}
	},
	DocumentoBase("DOC_BASE", "Documento Base", "documentoBase") {
		public String toString() {
			return "DOC_BASE";
		}
	},
	DeclaracionResponsabilidadColegiado("DeclaracionResponsabilidadColegiado", "Declaraci�n Responsabilidad Colegiado", "DeclRespColegiado") {
		public String toString() {
			return "DeclaracionResponsabilidadColegiado";
		}
	},
	DeclaracionResponsabilidadColegiadoConducir("DeclaracionResponsabilidadColegiadoConducir", "Declaraci�n Responsabilidad Colegiado DPC", "DeclRespColegiadoConducir") {
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
	DeclaracionResponsabilidadTitularConducir("DeclaracionResponsabilidadTitularConducir", "Declaraci�n Responsabilidad Titular DPC", "DeclRespTitularConducir") {
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