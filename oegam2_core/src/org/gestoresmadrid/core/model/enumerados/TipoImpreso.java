package org.gestoresmadrid.core.model.enumerados;

public enum TipoImpreso {

	MatriculacionBorradorPDF417("BorradorT1PDF417", "PlantillaT1PDF.pdf") {
		public String toString() {
			return "BorradorPDF417";
		}
	},

	MatriculacionBorradorPDF417Matw("BorradorT1PDF417", "PlantillaT1PDF_MTW.pdf") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	MatriculacionBorradorPDF417_2("BorradorT1PDF417_2", "PlantillaT1PDF_RENTING.pdf") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	MatriculacionPDF417("T1PDF417", "PlantillaT1PDF.pdf") {
		public String toString() {
			return "PDF417";
		}
	},
	MatriculacionPDF417Matw("T1PDF417", "PlantillaT1PDF_MTW.pdf") {
		public String toString() {
			return "PDF417";
		}
	},
	MatriculacionPDF417_2("T1PDF417_2", "PlantillaT1PDF_RENTING.pdf") {
		public String toString() {
			return "PDF417";
		}
	},
	MatriculacionPDFPresentacionTelematica("PlantillaT1PDF", "PlantillaT1PDF.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	MatriculacionPDFPresentacionTelematica_MATEW("PlantillaT1PDF", "PlantillaT1PDF_MTW.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},

	MatriculacionPDFPresentacionTelematica_2("T1PDFPresentacionTelematica_2", "PlantillaT1PDF_RENTING.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	MatriculacionPermisoTemporalCirculacion("T1PermisoTemporalCirculacion", "PlantillaT1PermisoTemporalCirculacion.pdf") {
		public String toString() {
			return "PermisoTemporalCirculacion";
		}
	},
	MatriculacionMandatoGenerico("T1MandatoGenerico", "mandato_generico_mar_2021.pdf") {
		// Mandato_generico_2017_editable.pdf
		public String toString() {
			return "MandatoGenerico";
		}
	},
	MatriculacionMandatoEspecifico("T1MandatoEspecifico", "mandato_especifico_mar_2021.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	MatriculacionCartaPagoIVTM("T1CartaPagoIVTM", "PlantillaT1CartaPagoIVTM.pdf") {
		public String toString() {
			return "CartaPagoIVTM";
		}
	},
	MatriculacionModeloAEAT("T1ModeloAEAT", "PlantillaT1ModeloAEAT.pdf") {
		public String toString() {
			return "ModeloAEAT";
		}
	},
	MatriculacionPegatinasEtiquetaMatricula("T1PegatinasEtiquetaMatricula", "PlantillaT1PegatinasEtiquetaMatricula.pdf") {
		public String toString() {
			return "PegatinasEtiquetaMatricula";
		}
	},
	MatriculacionListadoBastidores("T1ListadoBastidores", "PlantillaT1ListadoBastidores.pdf") {
		public String toString() {
			return "ListadoBastidores";
		}
	},
	MatriculacionListadoBastidores_2("T1ListadoBastidores_2", "PlantillaT1ListadoBastidores_2.pdf") {
		public String toString() {
			return "ListadoBastidores";
		}
	},
	TransmisionBorradorPDF417("T2BorradorPDF417", "PlantillaT2PDF417.pdf") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	TransmisionPDF417("T2PDF417", "PlantillaT2PDF417.pdf") {
		public String toString() {
			return "PDF417";
		}
	},
	TransmisionPDF417Trade("T2PDF417TRADE", "PlantillaT2PDF417Trade.pdf") {
		public String toString() {
			return "PDF417TRADE";
		}
	},
	TransmisionPDFPresentacionTelematica("T2PDFPresentacionTelematica", "PlantillaT2PDFPresentacionTelematica.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujeto("T2PDFPresentacionTelematicaNoSujeto", "PlantillaT2PDFPresentacionTelematicaNoSujeto.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExento("T2PDFPresentacionTelematicaExento", "PlantillaT2PDFPresentacionTelematicaExento.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoObligado("T2PDFPresentacionTelematicaNoObligado", "PlantillaT2PDFPresentacionTelematicaNoObligado.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematica("T2TradePresentacionTelematica", "PlantillaT2TradePresentacionTelematica.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematicaNoSujeto("T2TradePresentacionTelematicaNoSujeto", "PlantillaT2TradePresentacionTelematicaNoSujeto.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematicaExento("T2TradePresentacionTelematicaExento", "PlantillaT2TradePresentacionTelematicaExento.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematicaNoObligado("T2TradePresentacionTelematicaNoObligado", "PlantillaT2TradePresentacionTelematicaNoObligado.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},
	TransmisionDocumentosTelematicos("T2DocumentosTelematicos", "PlantillaT2DocumentosTelematicos.pdf") {
		public String toString() {
			return "DocumentosTelematicos";
		}
	},
	TransmisionModelo430("T2Modelo430", "PlantillaT2Modelo430.pdf") {
		public String toString() {
			return "Modelo430";
		}
	},
	BajasBorradorPDF417("T3BorradorPDF417", "PlantillaT3PDF417_v2.pdf") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	// Cambiamos la plantilla de bajas para que aparezca el CEMA (producción 10)
	BajasPDF417("T3PDF417", "PlantillaT3PDF417_v2.pdf") {
		public String toString() {
			return "PDF417";
		}
	},
	BajasExcel("T3Excel", "ExcelBajas") {
		public String toString() {
			return "T3Excel";
		}
	},
	BajasExcel_INCD("T3ExcelIncd", "ExcelBajasIncidenciasBtv") {
		public String toString() {
			return "T3ExcelIncd";
		}
	},
	// Plantilla de bajas para bajas telematicas
	BajasTelematicas("T3PDFTEL", "PlantillaT3PDFBaja_Telematica.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	BajaMandatoEspecifico("T3MandatoEspecifico", "mandato_especifico_jul_2017.pdf") {
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	DuplicadosExcel("T8Excel", "ExcelDuplicados") {
		public String toString() {
			return "T8Excel";
		}
	},
	SolicitudBorradorPDF417("T4BorradorPDF417", "PlantillaT4BorradorPDF417.pdf") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	SolicitudPDF417("T4PDF417", "PlantillaT4PDF417.pdf") {
		public String toString() {
			return "PDF417";
		}
	},
	SolicitudAvpo("SolicitudAvpo", "Trafico-ConsultaAVPO-PDF-DGT.pdf") {
		public String toString() {
			return "SolicitudAvpo";
		}
	},
	SolicitudAvpoError("SolicitudAvpoError", "Trafico-ConsultaAVPOError-PDF-DGT.pdf") {
		public String toString() {
			return "SolicitudAvpoError";
		}
	},
	SolicitudMandatoGenerico("T4MandatoGenerico", "mandato_generico_jun_2018.pdf") {
		public String toString() {
			return "MandatoGenerico";
		}
	},
	SolicitudMandatoEspecifico("T4MandatoEspecifico", "mandato_especifico_jun_2018.pdf") {
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	ListadoMatriculas1("ListadoMatriculas1", "PlantillaListadoMatriculas.pdf") {
		public String toString() {
			return "ListadoMatriculas1";
		}
	},
	ListadoMatriculas1_BD("ListadoMatriculas1", "PlantillaListadoMatriculas_BD.pdf") {
		public String toString() {
			return "ListadoMatriculas1_BD";
		}
	},
	ListadoMatriculas2("ListadoMatriculas2", "PlantillaListadoMatriculas_2.pdf") {
		public String toString() {
			return "ListadoMatriculas2";
		}
	},
	ListadoMatriculas2_BD("ListadoMatriculas2_BD", "PlantillaListadoMatriculas_2_BD.pdf") {
		public String toString() {
			return "ListadoMatriculas2_BD";
		}
	},
	ListadoMatriculasTrans2("ListadoMatriculasTrans2", "PlantillaListadoMatriculasTrans_2.pdf") {
		public String toString() {
			return "ListadoMatriculasTrans2";
		}
	},
	JustificantePDF("JustificantePDF", "Justificante.pdf") {
		public String toString() {
			return "JustificantePDF";
		}
	},
	FichaTecnica("FichaTecnica", "FichaTecnica") {
		public String toString() {
			return "FichaTecnica";
		}
	},
	TransmisionPDFPresentacionTelematicaConAutonomo("T2PDFPresentacionTelematicaConAutonomo", "PlantillaT2PDFPresentacionTelematicaConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo("T2PDFPresentacionTelematicaExentoNoObligadoConAutonomo", "PlantillaT2PDFPresentacionTelematicaExentoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExentoNoObligado("T2PDFPresentacionTelematicaExentoNoObligado", "PlantillaT2PDFPresentacionTelematicaExentoNoObligado.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExentoConAutonomo("T2PDFPresentacionTelematicaExentoConAutonomo", "PlantillaT2PDFPresentacionTelematicaExentoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo("T2PDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujetoNoObligado("T2PDFPresentacionTelematicaNoSujetoNoObligado", "PlantillaT2PDFPresentacionTelematicaNoSujetoNoObligado.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo("T2PDFPresentacionTelematicaNoSujetoConAutonomo", "PlantillaT2PDFPresentacionTelematicaNoSujetoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo("T2PDFPresentacionTelematicaNoObligadoConAutonomo", "PlantillaT2PDFPresentacionTelematicaNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaConAutonomo("T2TradePresentacionTelematicaConAutonomo", "PlantillaT2TradePresentacionTelematicaConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoObligadoConAutonomo("T2TradePresentacionTelematicaNoObligadoConAutonomo", "PlantillaT2TradePresentacionTelematicaNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoSujetoConAutonomo("T2TradePresentacionTelematicaNoSujetoConAutonomo", "PlantillaT2TradePresentacionTelematicaNoSujetoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoSujetoNoObligado("T2TradePresentacionTelematicaNoSujetoNoObligado", "PlantillaT2TradePresentacionTelematicaNoSujetoNoObligado.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo("T2TradePresentacionTelematicaNoSujetoNoObligadoConAutonomo",
			"PlantillaT2TradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaExentoConAutonomo("T2TradePresentacionTelematicaExentoConAutonomo", "PlantillaT2TradePresentacionTelematicaExentoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaExentoNoObligado("T2TradePresentacionTelematicaExentoNoObligado", "PlantillaT2TradePresentacionTelematicaExentoNoObligado.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo("T2TradePresentacionTelematicaExentoNoObligadoConAutonomo",
			"PlantillaT2TradePresentacionTelematicaExentoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},
	PDFJustificantePresentacion576("PDFJustificantePresentacion576", "PDFJustificantePresentacion576.pdf") {
		public String toString() {
			return "PDFJustificantePresentacion576";
		}
	},
	SolicitudATEM("SolicitudATEM", "Trafico-ConsultaTEM-PDF-DGT.pdf") {
		public String toString() {
			return "SolicitudATEM";
		}
	},
	ConsultaEITV("ConsultaEITV", "ConsultaEITV") {
		public String toString() {
			return "ConsultaEITV";
		}
	},
	XMLConsultaEitv("XMLConsultaEitv", "XMLConsultaEitv") {
		public String toString() {
			return "XMLConsultaEitv";
		}
	},
	InformeBajaTelematica("InformeBajaTelematica", "Informe_Baja") {
		public String toString() {
			return "InformeBajaTelematica";
		}
	},
	SolicitudPermiso("SolicitudPermiso", "Permiso") {
		public String toString() {
			return "Permiso";
		}
	},
	SolicitudDistintivo("SolicitudDistintivo", "Distintivo") {
		public String toString() {
			return "Distintivo";
		}
	},
	SolicitudFichaTecnica("SolicitudFichaTecnica", "FichaTecnica") {
		public String toString() {
			return "FichaTecnica";
		}
	},
	JustificantesNoFinalizadosExcel("JustificantesNoFinalizados", "ExcelJustificantesNoFinalizados") {
		public String toString() {
			return "JustificantesNoFinalizados";
		}
	},
	DeclaracionJuradaExportacionTransito("DeclaracionJuradaExportacionTransito", "DeclaracionJuradaExportacionTransito.pdf") {
		public String toString() {
			return "DeclaracionJuradaExportacionTransito";
		}
	},
	DeclaracionJuradaExtravioFicha("DeclaracionJuradaExtravioFicha", "DeclaracionJuradaExtravioFicha.pdf") {
		public String toString() {
			return "DeclaracionJuradaExtravioFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoFicha("DeclaracionJuradaExtravioPermisoFicha", "DeclaracionJuradaExtravioPermisoFicha.pdf") {
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoLicencia("DeclaracionJuradaExtravioPermisoLicencia", "DeclaracionJuradaExtravioPermisoLicencia.pdf") {
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoLicencia";
		}
	},
	DeclaracionJuradaEntregaAnteriorPermiso("DeclaracionJuradaEntregaAnteriorPermiso", "DeclaracionJuradaEntregaAnteriorPermiso.pdf") {
		public String toString() {
			return "DeclaracionJuradaEntregaAnteriorPermiso";
		}
	},
	DeclaracionResponsabilidadColegiado("DeclaracionResponsabilidadColegiado", "DeclaracionResponsableColegiado.pdf") {
		public String toString() {
			return "DeclaracionResponsabilidadColegiado";
		}
	},
	DeclaracionResponsabilidadColegio("DeclaracionResponsabilidadColegio", "DeclaracionResponsableColegio.pdf") {
		public String toString() {
			return "DeclaracionResponsabilidadColegio";
		}
	},
	DeclaracionResponsabilidadColegiadoConducir("DeclaracionResponsabilidadColegiadoConducir", "DeclaracionResponsableColegiadoConducir.pdf") {
		public String toString() {
			return "DeclaracionResponsabilidadColegiadoConducir";
		}
	},
	SolicitudDuplicadoPermisoConducir("SolicitudDuplicadoPermisoConducir", "SolicitudDuplicadoPermisoConducir.pdf") {
		public String toString() {
			return "SolicitudDuplicadoPermisoConducir";
		}
	},
	MandatoEspecificoInterga("T1MandatoEspecifico", "mandato_especifico_interga_ene_2020.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	CertificadoRevisionColegial("CertificadoRevisionColegial", "CertificadoRevisionColegial.pdf") {
		public String toString() {
			return "CertificadoRevisionColegial";
		}
	},
	CertificadoRevisionColegialInicial("CertificadoRevisionColegialInicial", "CertificadoRevisionColegialInicial.pdf") {
		public String toString() {
			return "CertificadoRevisionColegialInicial";
		}
	},
	TxtNre("TXTNRE", "TXTNRE") {
		public String toString() {
			return "TXTNRE";
		}
	},
	PermisoTemporalDuplicado("PermisoTemporalDuplicado", "PermisoTemporalDuplicado") {
		public String toString() {
			return "PermisoTemporalDuplicado";
		}
	},
	JustifRegEntrada("JustifRegEntrada", "JustifRegEntrada") {
		public String toString() {
			return "JustifRegEntrada";
		}
	},
	JustificanteDuplicado("JustificanteDuplicado", "JustificanteDuplicado") {
		public String toString() {
			return "JustificanteDuplicado";
		}
	},
	JustifRegEntradaBajaSive("JustifRegEntradaBajaSive", "JustifRegEntradaBajaSive") {
		public String toString() {
			return "JustifRegEntradaBajaSive";
		}
	},
	InformeBajaSive("InformeBajaSive", "InformeBajaSive") {
		public String toString() {
			return "InformeBajaSive";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoImpreso(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoImpreso convertir(String valorEnum) {
		for (TipoImpreso element : TipoImpreso.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static TipoImpreso convertirPorTexto(String texto) {
		for (TipoImpreso element : TipoImpreso.values()) {
			if (element.toString().equals(texto)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoImpreso element : TipoImpreso.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
