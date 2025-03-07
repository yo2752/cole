package trafico.utiles.enumerados;

public enum TipoDocumentoImpresionesMasivas {

	ListadoBastidores("ListadoBastidores", "Listado de Bastidores") {
		public String toString() {
			return "ListadoBastidores";
		}
	},
	BorradorPDF417("BorradorPDF417", "Borrador PDF 417") {
		public String toString() {
			return "BorradorPDF417";
		}
	},
	PDF417("PDF417", "PDF 417") {
		public String toString() {
			return "PDF417";
		}
	},
	PDFPresentacionTelematica("PDFPresentacionTelematica", "PDF Presentacion Telemática") {
		public String toString() {
			return "PDFPresentacionTelematica";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoDocumentoImpresionesMasivas(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}