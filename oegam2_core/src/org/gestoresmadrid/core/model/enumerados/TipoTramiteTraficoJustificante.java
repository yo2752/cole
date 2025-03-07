package org.gestoresmadrid.core.model.enumerados;

public enum TipoTramiteTraficoJustificante {
	Transmision("T2", "TRANSMISION") {
		public String toString() {
			return "T2";
		}
	},TransmisionElectronica("T7", "TRANSMISION ELECTRONICA") {
		public String toString() {
			return "T7";
		}
	},
	Duplicado("T8", "DUPLICADO") {
		public String toString() {
			return "T8";
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
		if (valorEnum == null)
			return null;
		if (Transmision.valorEnum.equals(valorEnum))
			return Transmision;
		else if (TransmisionElectronica.valorEnum.equals(valorEnum))
			return TransmisionElectronica;
		else if (Duplicado.valorEnum.equals(valorEnum))
			return Duplicado;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (Transmision.valorEnum.equals(valorEnum))
			return Transmision.nombreEnum;
		else if (TransmisionElectronica.valorEnum.equals(valorEnum))
			return TransmisionElectronica.nombreEnum;
		else if (Duplicado.valorEnum.equals(valorEnum))
			return Duplicado.nombreEnum;
		else
			return null;
	}
}
