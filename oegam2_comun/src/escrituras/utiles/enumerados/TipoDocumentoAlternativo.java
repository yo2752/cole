package escrituras.utiles.enumerados;

public enum TipoDocumentoAlternativo {

	Pasaporte("PASAPORTE", "Pasaporte", "1") {
		public String toString() {
			return "PASAPORTE";
		}
	},
	Carnet_de_Conducir("CARNET_CONDUCIR", "Carnet de Conducir", "2") {
		public String toString() {
			return "CARNET_CONDUCIR";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String valorXML;

	private TipoDocumentoAlternativo(String valorEnum, String nombreEnum, String valorXML) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.valorXML = valorXML;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorXML() {
		return valorXML;
	}

	public void setValorXML(String valorXML) {
		this.valorXML = valorXML;
	}

	// valueOf
	public static TipoDocumentoAlternativo convertir(String valorEnum) {
		if ("PASAPORTE".equals(valorEnum))
			return Pasaporte;

		if ("CARNET_CONDUCIR".equals(valorEnum))
			return Carnet_de_Conducir;

		return null;
	}
}