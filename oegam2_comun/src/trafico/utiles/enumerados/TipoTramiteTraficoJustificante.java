package trafico.utiles.enumerados;

public enum TipoTramiteTraficoJustificante {

	TRANSFERENCIA("TR", "TRANSFERENCIA") {
		public String toString() {
			return "TR";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTramiteTraficoJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTramiteTraficoJustificante convertir(String valorEnum) {
		if (valorEnum != null) {
			for (TipoTramiteTraficoJustificante tipoTramite : TipoTramiteTraficoJustificante.values()) {
				if (tipoTramite.getValorEnum().equals(valorEnum)) {
					return tipoTramite;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum != null) {
			for (TipoTramiteTraficoJustificante tipoTramite : TipoTramiteTraficoJustificante.values()) {
				if (tipoTramite.getValorEnum().equals(valorEnum)) {
					return tipoTramite.getNombreEnum();
				}
			}
		}
		return null;
	}

}