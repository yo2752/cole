package trafico.utiles.enumerados;

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
	MatriculacionPermisoTemporalCirculacion("T1PermisoTemporalCirculacion",
			"PlantillaT1PermisoTemporalCirculacion.pdf") {
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
	MatriculacionPegatinasEtiquetaMatricula("T1PegatinasEtiquetaMatricula",
			"PlantillaT1PegatinasEtiquetaMatricula.pdf") {
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
	TransmisionPDFPresentacionTelematicaNoSujeto("T2PDFPresentacionTelematicaNoSujeto",
			"PlantillaT2PDFPresentacionTelematicaNoSujeto.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExento("T2PDFPresentacionTelematicaExento",
			"PlantillaT2PDFPresentacionTelematicaExento.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoObligado("T2PDFPresentacionTelematicaNoObligado",
			"PlantillaT2PDFPresentacionTelematicaNoObligado.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematica("T2TradePresentacionTelematica",
			"PlantillaT2TradePresentacionTelematica.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematicaNoSujeto("T2TradePresentacionTelematicaNoSujeto",
			"PlantillaT2TradePresentacionTelematicaNoSujeto.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematicaExento("T2TradePresentacionTelematicaExento",
			"PlantillaT2TradePresentacionTelematicaExento.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionTradePresentacionTelematicaNoObligado("T2TradePresentacionTelematicaNoObligado",
			"PlantillaT2TradePresentacionTelematicaNoObligado.pdf") {
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
	BajasPDF417N("T3PDF417N", "PlantillaT3PDF417_v3.pdf") {
		public String toString() {
			return "PDF417N";
		}
	},
	// Plantilla de bajas para bajas telematicas
	BajasTelematicas("T3PDFTEL", "PlantillaT3PDFBaja_Telematica.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	BajasExcel("T3Excel", "ExcelBajas") {
		public String toString() {
			return "T3Excel";
		}
	},
	BajaMandatoEspecifico("T3MandatoEspecifico", "PlantillaT3MandatoEspecifico.pdf") {
		public String toString() {
			return "MandatoEspecifico";
		}
	},

	DuplicadosExcel("T8Excel", "ExcelDuplicados") {
		public String toString() {
			return "T8Excel";
		}
	},
	CambioServicioExcel("T26Excel", "ExcelCambioServicio") {
		public String toString() {
			return "T26Excel";
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
	SolicitudMandatoGenerico("T4MandatoGenerico", "generico_READER_PLATAFORMA.pdf") {
		public String toString() {
			return "MandatoGenerico";
		}
	},
	SolicitudMandatoEspecifico("T4MandatoEspecifico", "especifico_READER_PLATAFORMA.pdf") {
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
	TransmisionPDFPresentacionTelematicaConAutonomo("T2PDFPresentacionTelematicaConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo(
			"T2PDFPresentacionTelematicaExentoNoObligadoConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaExentoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExentoNoObligado("T2PDFPresentacionTelematicaExentoNoObligado",
			"PlantillaT2PDFPresentacionTelematicaExentoNoObligado.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaExentoConAutonomo("T2PDFPresentacionTelematicaExentoConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaExentoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo(
			"T2PDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujetoNoObligado("T2PDFPresentacionTelematicaNoSujetoNoObligado",
			"PlantillaT2PDFPresentacionTelematicaNoSujetoNoObligado.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo("T2PDFPresentacionTelematicaNoSujetoConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaNoSujetoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},
	TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo("T2PDFPresentacionTelematicaNoObligadoConAutonomo",
			"PlantillaT2PDFPresentacionTelematicaNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaConAutonomo("T2TradePresentacionTelematicaConAutonomo",
			"PlantillaT2TradePresentacionTelematicaConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoObligadoConAutonomo("T2TradePresentacionTelematicaNoObligadoConAutonomo",
			"PlantillaT2TradePresentacionTelematicaNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoSujetoConAutonomo("T2TradePresentacionTelematicaNoSujetoConAutonomo",
			"PlantillaT2TradePresentacionTelematicaNoSujetoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoSujetoNoObligado("T2TradePresentacionTelematicaNoSujetoNoObligado",
			"PlantillaT2TradePresentacionTelematicaNoSujetoNoObligado.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo(
			"T2TradePresentacionTelematicaNoSujetoNoObligadoConAutonomo",
			"PlantillaT2TradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaExentoConAutonomo("T2TradePresentacionTelematicaExentoConAutonomo",
			"PlantillaT2TradePresentacionTelematicaExentoConAutonomo.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaExentoNoObligado("T2TradePresentacionTelematicaExentoNoObligado",
			"PlantillaT2TradePresentacionTelematicaExentoNoObligado.pdf") {
		public String toString() {
			return "TradePresentacionTelematica";
		}
	},

	TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo(
			"T2TradePresentacionTelematicaExentoNoObligadoConAutonomo",
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
	InformeBajaTelematica("InformeBajaTelematica", "InformeBaja") {
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
	DeclaracionJuradaExportacionTransito("DeclaracionJuradaExportacionTransito",
			"DeclaracionJuradaExportacionTransito.pdf") {
		// Mandato_generico_2017_editable.pdf
		public String toString() {
			return "DeclaracionJuradaExportacionTransito";
		}
	},
	DeclaracionJuradaExtravioFicha("DeclaracionJuradaExtravioFicha", "DeclaracionJuradaExtravioFicha.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "DeclaracionJuradaExtravioFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoFicha("DeclaracionJuradaExtravioPermisoFicha",
			"DeclaracionJuradaExtravioPermisoFicha.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoFicha";
		}
	},
	DeclaracionJuradaExtravioPermisoLicencia("DeclaracionJuradaExtravioPermisoLicencia",
			"DeclaracionJuradaExtravioPermisoLicencia.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "DeclaracionJuradaExtravioPermisoLicencia";
		}
	},
	DeclaracionJuradaEntregaAnteriorPermiso("DeclaracionJuradaEntregaAnteriorPermiso",
			"DeclaracionJuradaEntregaAnteriorPermiso.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "DeclaracionJuradaEntregaAnteriorPermiso";
		}
	},
	MandatoEspecificoInterga("T1MandatoEspecifico", "mandato_especifico_interga_ene_2020.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "MandatoEspecifico";
		}
	},
	SolicitudNRE06("SolicitudNre06", "PlantillaNRE06.pdf") {
		// Mandato_especifico_2017_editable.pdf
		public String toString() {
			return "SolicitudNre06";
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
	},CertificadoRevisionColegial("CertificadoRevisionColegial", "CertificadoRevisionColegial") {
		public String toString() {
			return "CertificadoRevisionColegial";
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
		if (valorEnum == null)
			return null;
		else if (MatriculacionBorradorPDF417.valorEnum.equals(valorEnum))
			return MatriculacionBorradorPDF417;
		else if (MatriculacionBorradorPDF417_2.valorEnum.equals(valorEnum))
			return MatriculacionBorradorPDF417_2;
		else if (MatriculacionPDF417.valorEnum.equals(valorEnum))
			return MatriculacionPDF417;
		else if (MatriculacionPDF417_2.valorEnum.equals(valorEnum))
			return MatriculacionPDF417_2;
		else if (MatriculacionPDFPresentacionTelematica.valorEnum.equals(valorEnum))
			return MatriculacionPDFPresentacionTelematica;
		else if (MatriculacionPDFPresentacionTelematica_2.valorEnum.equals(valorEnum))
			return MatriculacionPDFPresentacionTelematica_2;
		else if (MatriculacionPermisoTemporalCirculacion.valorEnum.equals(valorEnum))
			return MatriculacionPermisoTemporalCirculacion;
		else if (MatriculacionMandatoGenerico.valorEnum.equals(valorEnum))
			return MatriculacionMandatoGenerico;
		else if (MatriculacionMandatoEspecifico.valorEnum.equals(valorEnum))
			return MatriculacionMandatoEspecifico;
		else if (MatriculacionCartaPagoIVTM.valorEnum.equals(valorEnum))
			return MatriculacionCartaPagoIVTM;
		else if (MatriculacionModeloAEAT.valorEnum.equals(valorEnum))
			return MatriculacionModeloAEAT;
		else if (MatriculacionPegatinasEtiquetaMatricula.valorEnum.equals(valorEnum))
			return MatriculacionPegatinasEtiquetaMatricula;
		else if (MatriculacionListadoBastidores.valorEnum.equals(valorEnum))
			return MatriculacionListadoBastidores;
		else if (MatriculacionListadoBastidores_2.valorEnum.equals(valorEnum))
			return MatriculacionListadoBastidores_2;
		else if (TransmisionBorradorPDF417.valorEnum.equals(valorEnum))
			return TransmisionBorradorPDF417;
		else if (TransmisionPDF417.valorEnum.equals(valorEnum))
			return TransmisionPDF417;
		else if (TransmisionPDF417Trade.valorEnum.equals(valorEnum))
			return TransmisionPDF417Trade;
		else if (TransmisionPDFPresentacionTelematica.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematica;
		else if (TransmisionPDFPresentacionTelematicaExento.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExento;
		else if (TransmisionPDFPresentacionTelematicaNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoObligado;
		else if (TransmisionPDFPresentacionTelematicaNoSujeto.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujeto;
		else if (TransmisionTradePresentacionTelematica.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematica;
		else if (TransmisionTradePresentacionTelematicaExento.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExento;
		else if (TransmisionTradePresentacionTelematicaNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoObligado;
		else if (TransmisionTradePresentacionTelematicaNoSujeto.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujeto;
		else if (TransmisionDocumentosTelematicos.valorEnum.equals(valorEnum))
			return TransmisionDocumentosTelematicos;
		else if (TransmisionModelo430.valorEnum.equals(valorEnum))
			return TransmisionModelo430;
		else if (BajasBorradorPDF417.valorEnum.equals(valorEnum))
			return BajasBorradorPDF417;
		else if (BajasPDF417.valorEnum.equals(valorEnum))
			return BajasPDF417;
		else if (BajasExcel.valorEnum.equals(valorEnum))
			return BajasExcel;
		else if (DuplicadosExcel.valorEnum.equals(valorEnum))
			return DuplicadosExcel;
		else if (CambioServicioExcel.valorEnum.equals(valorEnum))
			return CambioServicioExcel;
		else if (SolicitudBorradorPDF417.valorEnum.equals(valorEnum))
			return SolicitudBorradorPDF417;
		else if (SolicitudPDF417.valorEnum.equals(valorEnum))
			return SolicitudPDF417;
		else if (SolicitudAvpo.valorEnum.equals(valorEnum))
			return SolicitudAvpo;
		else if (SolicitudAvpoError.valorEnum.equals(valorEnum))
			return SolicitudAvpoError;
		else if (SolicitudMandatoGenerico.valorEnum.equals(valorEnum))
			return SolicitudMandatoGenerico;
		else if (SolicitudMandatoEspecifico.valorEnum.equals(valorEnum))
			return SolicitudMandatoEspecifico;
		else if (ListadoMatriculas1.valorEnum.equals(valorEnum))
			return ListadoMatriculas1;
		else if (ListadoMatriculas1_BD.valorEnum.equals(valorEnum))
			return ListadoMatriculas1_BD;
		else if (ListadoMatriculas2.valorEnum.equals(valorEnum))
			return ListadoMatriculas2;
		else if (ListadoMatriculas2_BD.valorEnum.equals(valorEnum))
			return ListadoMatriculas2_BD;
		else if (ListadoMatriculasTrans2.valorEnum.equals(valorEnum))
			return ListadoMatriculasTrans2;
		else if (JustificantePDF.valorEnum.equals(valorEnum))
			return JustificantePDF;
		else if (FichaTecnica.valorEnum.equals(valorEnum))
			return FichaTecnica;
		else if (TransmisionPDFPresentacionTelematicaConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaExentoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExentoNoObligado;
		else if (TransmisionPDFPresentacionTelematicaExentoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExentoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujetoNoObligado;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaConAutonomo;
		else if (TransmisionTradePresentacionTelematicaNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoObligadoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaNoSujetoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujetoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaNoSujetoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujetoNoObligado;
		else if (TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaExentoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExentoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaExentoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExentoNoObligado;
		else if (TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo;
		else if (PDFJustificantePresentacion576.valorEnum.equals(valorEnum))
			return PDFJustificantePresentacion576;
		else if (SolicitudATEM.valorEnum.equals(valorEnum))
			return SolicitudATEM;
		else if (ConsultaEITV.valorEnum.equals(valorEnum))
			return ConsultaEITV;
		else if (SolicitudPermiso.valorEnum.equals(valorEnum))
			return SolicitudPermiso;
		else if (SolicitudDistintivo.valorEnum.equals(valorEnum))
			return SolicitudDistintivo;
		else if (DeclaracionJuradaExportacionTransito.valorEnum.equals(valorEnum))
			return DeclaracionJuradaExportacionTransito;
		else if (DeclaracionJuradaExtravioFicha.valorEnum.equals(valorEnum))
			return DeclaracionJuradaExtravioFicha;
		else if (DeclaracionJuradaExtravioPermisoFicha.valorEnum.equals(valorEnum))
			return DeclaracionJuradaExtravioPermisoFicha;
		else if (DeclaracionJuradaExtravioPermisoLicencia.valorEnum.equals(valorEnum))
			return DeclaracionJuradaExtravioPermisoLicencia;
		else if (DeclaracionJuradaEntregaAnteriorPermiso.valorEnum.equals(valorEnum))
			return DeclaracionJuradaEntregaAnteriorPermiso;
		else
			return null;
	}

	public static TipoImpreso convertirPorTexto(String texto) {
		if (texto == null)
			return null;
		else if (MatriculacionBorradorPDF417.toString().equals(texto))
			return MatriculacionBorradorPDF417;
		else if (MatriculacionBorradorPDF417_2.valorEnum.equals(texto))
			return MatriculacionBorradorPDF417_2;
		else if (MatriculacionPDF417.toString().equals(texto))
			return MatriculacionPDF417;
		else if (MatriculacionPDF417_2.toString().equals(texto))
			return MatriculacionPDF417_2;
		else if (MatriculacionPDFPresentacionTelematica.toString().equals(texto))
			return MatriculacionPDFPresentacionTelematica;
		else if (MatriculacionPDFPresentacionTelematica_2.toString().equals(texto))
			return MatriculacionPDFPresentacionTelematica_2;
		else if (MatriculacionPermisoTemporalCirculacion.toString().equals(texto))
			return MatriculacionPermisoTemporalCirculacion;
		else if (MatriculacionMandatoGenerico.toString().equals(texto))
			return MatriculacionMandatoGenerico;
		else if (MatriculacionMandatoEspecifico.toString().equals(texto))
			return MatriculacionMandatoEspecifico;
		else if (MatriculacionCartaPagoIVTM.toString().equals(texto))
			return MatriculacionCartaPagoIVTM;
		else if (MatriculacionModeloAEAT.toString().equals(texto))
			return MatriculacionModeloAEAT;
		else if (MatriculacionPegatinasEtiquetaMatricula.toString().equals(texto))
			return MatriculacionPegatinasEtiquetaMatricula;
		else if (MatriculacionListadoBastidores.toString().equals(texto))
			return MatriculacionListadoBastidores;
		else if (MatriculacionListadoBastidores_2.toString().equals(texto))
			return MatriculacionListadoBastidores_2;
		else if (TransmisionBorradorPDF417.toString().equals(texto))
			return TransmisionBorradorPDF417;
		else if (TransmisionPDF417.toString().equals(texto))
			return TransmisionPDF417;
		else if (TransmisionPDF417Trade.toString().equals(texto))
			return TransmisionPDF417Trade;
		else if (TransmisionPDFPresentacionTelematica.toString().equals(texto))
			return TransmisionPDFPresentacionTelematica;
		else if (TransmisionPDFPresentacionTelematicaExento.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaExento;
		else if (TransmisionPDFPresentacionTelematicaNoObligado.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoObligado;
		else if (TransmisionPDFPresentacionTelematicaNoSujeto.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoSujeto;
		else if (TransmisionTradePresentacionTelematica.toString().equals(texto))
			return TransmisionTradePresentacionTelematica;
		else if (TransmisionTradePresentacionTelematicaExento.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaExento;
		else if (TransmisionTradePresentacionTelematicaNoObligado.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoObligado;
		else if (TransmisionTradePresentacionTelematicaNoSujeto.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaNoSujeto;
		else if (TransmisionDocumentosTelematicos.toString().equals(texto))
			return TransmisionDocumentosTelematicos;
		else if (TransmisionModelo430.toString().equals(texto))
			return TransmisionModelo430;
		else if (BajasBorradorPDF417.toString().equals(texto))
			return BajasBorradorPDF417;
		else if (BajasPDF417.toString().equals(texto))
			return BajasPDF417;
		else if (BajasExcel.toString().equals(texto))
			return BajasExcel;
		else if (DuplicadosExcel.toString().equals(texto))
			return DuplicadosExcel;
		else if (CambioServicioExcel.toString().equals(texto))
			return CambioServicioExcel;
		else if (SolicitudBorradorPDF417.toString().equals(texto))
			return SolicitudBorradorPDF417;
		else if (SolicitudPDF417.toString().equals(texto))
			return SolicitudPDF417;
		else if (SolicitudAvpo.toString().equals(texto))
			return SolicitudAvpo;
		else if (SolicitudAvpoError.toString().equals(texto))
			return SolicitudAvpoError;
		else if (SolicitudMandatoGenerico.toString().equals(texto))
			return SolicitudMandatoGenerico;
		else if (SolicitudMandatoEspecifico.toString().equals(texto))
			return SolicitudMandatoEspecifico;
		else if (ListadoMatriculas1.toString().equals(texto))
			return ListadoMatriculas1;
		else if (ListadoMatriculas1_BD.toString().equals(texto))
			return ListadoMatriculas1_BD;
		else if (ListadoMatriculas2.toString().equals(texto))
			return ListadoMatriculas2;
		else if (ListadoMatriculas2_BD.toString().equals(texto))
			return ListadoMatriculas2_BD;
		else if (ListadoMatriculasTrans2.toString().equals(texto))
			return ListadoMatriculasTrans2;
		else if (JustificantePDF.toString().equals(texto))
			return JustificantePDF;
		else if (FichaTecnica.toString().equals(texto))
			return FichaTecnica;
		else if (TransmisionPDFPresentacionTelematicaConAutonomo.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaExentoNoObligado.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaExentoNoObligado;
		else if (TransmisionPDFPresentacionTelematicaExentoConAutonomo.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaExentoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoNoObligado.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoSujetoNoObligado;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo;
		else if (TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo.toString().equals(texto))
			return TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaConAutonomo.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaConAutonomo;
		else if (TransmisionTradePresentacionTelematicaNoObligadoConAutonomo.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaNoObligadoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaNoSujetoConAutonomo.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaNoSujetoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaNoSujetoNoObligado.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaNoSujetoNoObligado;
		else if (TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaExentoConAutonomo.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaExentoConAutonomo;
		else if (TransmisionTradePresentacionTelematicaExentoNoObligado.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaExentoNoObligado;
		else if (TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo.toString().equals(texto))
			return TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo;
		else if (PDFJustificantePresentacion576.toString().equals(texto))
			return PDFJustificantePresentacion576;
		else if (SolicitudATEM.toString().equals(texto))
			return SolicitudATEM;
		else if (ConsultaEITV.toString().equals(texto))
			return ConsultaEITV;
		else if (SolicitudPermiso.toString().equals(texto))
			return SolicitudPermiso;
		else if (SolicitudDistintivo.toString().equals(texto))
			return SolicitudDistintivo;
		else if (DeclaracionJuradaExportacionTransito.toString().equals(texto))
			return DeclaracionJuradaExportacionTransito;
		else if (DeclaracionJuradaExtravioFicha.toString().equals(texto))
			return DeclaracionJuradaExtravioFicha;
		else if (DeclaracionJuradaExtravioPermisoFicha.toString().equals(texto))
			return DeclaracionJuradaExtravioPermisoFicha;
		else if (DeclaracionJuradaExtravioPermisoLicencia.toString().equals(texto))
			return DeclaracionJuradaExtravioPermisoLicencia;
		else if (DeclaracionJuradaEntregaAnteriorPermiso.toString().equals(texto))
			return DeclaracionJuradaEntregaAnteriorPermiso;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;
		else if (MatriculacionBorradorPDF417.valorEnum.equals(valorEnum))
			return MatriculacionBorradorPDF417.nombreEnum;
		else if (MatriculacionBorradorPDF417_2.valorEnum.equals(valorEnum))
			return MatriculacionBorradorPDF417_2.nombreEnum;
		else if (MatriculacionPDF417.valorEnum.equals(valorEnum))
			return MatriculacionPDF417.nombreEnum;
		else if (MatriculacionPDF417_2.valorEnum.equals(valorEnum))
			return MatriculacionPDF417_2.nombreEnum;
		else if (MatriculacionPDFPresentacionTelematica.valorEnum.equals(valorEnum))
			return MatriculacionPDFPresentacionTelematica.nombreEnum;
		else if (MatriculacionPDFPresentacionTelematica_2.valorEnum.equals(valorEnum))
			return MatriculacionPDFPresentacionTelematica_2.nombreEnum;
		else if (MatriculacionPermisoTemporalCirculacion.valorEnum.equals(valorEnum))
			return MatriculacionPermisoTemporalCirculacion.nombreEnum;
		else if (MatriculacionMandatoGenerico.valorEnum.equals(valorEnum))
			return MatriculacionMandatoGenerico.nombreEnum;
		else if (MatriculacionMandatoEspecifico.valorEnum.equals(valorEnum))
			return MatriculacionMandatoEspecifico.nombreEnum;
		else if (MatriculacionCartaPagoIVTM.valorEnum.equals(valorEnum))
			return MatriculacionCartaPagoIVTM.nombreEnum;
		else if (MatriculacionModeloAEAT.valorEnum.equals(valorEnum))
			return MatriculacionModeloAEAT.nombreEnum;
		else if (MatriculacionPegatinasEtiquetaMatricula.valorEnum.equals(valorEnum))
			return MatriculacionPegatinasEtiquetaMatricula.nombreEnum;
		else if (MatriculacionListadoBastidores.valorEnum.equals(valorEnum))
			return MatriculacionListadoBastidores.nombreEnum;
		else if (MatriculacionListadoBastidores_2.valorEnum.equals(valorEnum))
			return MatriculacionListadoBastidores_2.nombreEnum;
		else if (TransmisionBorradorPDF417.valorEnum.equals(valorEnum))
			return TransmisionBorradorPDF417.nombreEnum;
		else if (TransmisionPDF417.valorEnum.equals(valorEnum))
			return TransmisionPDF417.nombreEnum;
		else if (TransmisionPDF417Trade.valorEnum.equals(valorEnum))
			return TransmisionPDF417Trade.nombreEnum;
		else if (TransmisionPDFPresentacionTelematica.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematica.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaExento.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExento.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoObligado.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaNoSujeto.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujeto.nombreEnum;
		else if (TransmisionTradePresentacionTelematica.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematica.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaExento.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExento.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoObligado.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaNoSujeto.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujeto.nombreEnum;
		else if (TransmisionDocumentosTelematicos.valorEnum.equals(valorEnum))
			return TransmisionDocumentosTelematicos.nombreEnum;
		else if (TransmisionModelo430.valorEnum.equals(valorEnum))
			return TransmisionModelo430.nombreEnum;
		else if (BajasBorradorPDF417.valorEnum.equals(valorEnum))
			return BajasBorradorPDF417.nombreEnum;
		else if (BajasPDF417.valorEnum.equals(valorEnum))
			return BajasPDF417.nombreEnum;
		else if (BajasExcel.valorEnum.equals(valorEnum))
			return BajasExcel.nombreEnum;
		else if (DuplicadosExcel.valorEnum.equals(valorEnum))
			return DuplicadosExcel.nombreEnum;
		else if (CambioServicioExcel.valorEnum.equals(valorEnum))
			return CambioServicioExcel.nombreEnum;
		else if (SolicitudBorradorPDF417.valorEnum.equals(valorEnum))
			return SolicitudBorradorPDF417.nombreEnum;
		else if (SolicitudPDF417.valorEnum.equals(valorEnum))
			return SolicitudPDF417.nombreEnum;
		else if (SolicitudAvpo.valorEnum.equals(valorEnum))
			return SolicitudAvpo.nombreEnum;
		else if (SolicitudAvpoError.valorEnum.equals(valorEnum))
			return SolicitudAvpoError.nombreEnum;
		else if (SolicitudMandatoGenerico.valorEnum.equals(valorEnum))
			return SolicitudMandatoGenerico.nombreEnum;
		else if (SolicitudMandatoEspecifico.valorEnum.equals(valorEnum))
			return SolicitudMandatoEspecifico.nombreEnum;
		else if (ListadoMatriculas1.valorEnum.equals(valorEnum))
			return ListadoMatriculas1.nombreEnum;
		else if (ListadoMatriculas1_BD.valorEnum.equals(valorEnum))
			return ListadoMatriculas1_BD.nombreEnum;
		else if (ListadoMatriculas2.valorEnum.equals(valorEnum))
			return ListadoMatriculas2.nombreEnum;
		else if (ListadoMatriculas2_BD.valorEnum.equals(valorEnum))
			return ListadoMatriculas2_BD.nombreEnum;
		else if (ListadoMatriculasTrans2.valorEnum.equals(valorEnum))
			return ListadoMatriculasTrans2.nombreEnum;
		else if (JustificantePDF.valorEnum.equals(valorEnum))
			return JustificantePDF.nombreEnum;
		else if (FichaTecnica.valorEnum.equals(valorEnum))
			return FichaTecnica.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaConAutonomo.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaExentoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExentoNoObligado.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaExentoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaExentoConAutonomo.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujetoNoObligado.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo.nombreEnum;
		else if (TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaConAutonomo.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoObligadoConAutonomo.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaNoSujetoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujetoConAutonomo.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaNoSujetoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujetoNoObligado.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaExentoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExentoConAutonomo.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaExentoNoObligado.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExentoNoObligado.nombreEnum;
		else if (TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo.valorEnum.equals(valorEnum))
			return TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo.nombreEnum;
		else if (PDFJustificantePresentacion576.valorEnum.equals(valorEnum))
			return PDFJustificantePresentacion576.nombreEnum;
		else if (SolicitudATEM.valorEnum.equals(valorEnum))
			return SolicitudATEM.nombreEnum;
		else if (ConsultaEITV.valorEnum.equals(valorEnum))
			return ConsultaEITV.nombreEnum;
		else if (SolicitudPermiso.valorEnum.equals(valorEnum))
			return SolicitudPermiso.nombreEnum;
		else if (SolicitudDistintivo.valorEnum.equals(valorEnum)) {
			return SolicitudDistintivo.nombreEnum;
		} else if (DeclaracionJuradaExportacionTransito.valorEnum.equals(valorEnum)) {
			return DeclaracionJuradaExportacionTransito.nombreEnum;
		} else if (DeclaracionJuradaExtravioFicha.valorEnum.equals(valorEnum)) {
			return DeclaracionJuradaExtravioFicha.nombreEnum;
		} else if (DeclaracionJuradaExtravioPermisoFicha.valorEnum.equals(valorEnum)) {
			return DeclaracionJuradaExtravioPermisoFicha.nombreEnum;
		} else if (DeclaracionJuradaExtravioPermisoLicencia.valorEnum.equals(valorEnum)) {
			return DeclaracionJuradaExtravioPermisoLicencia.nombreEnum;
		} else if (DeclaracionJuradaEntregaAnteriorPermiso.valorEnum.equals(valorEnum)) {
			return DeclaracionJuradaEntregaAnteriorPermiso.nombreEnum;
		} else
			return null;
	}
}