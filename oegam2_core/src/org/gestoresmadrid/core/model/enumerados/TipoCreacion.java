package org.gestoresmadrid.core.model.enumerados;

public enum TipoCreacion {
	SinClasificar("0", "Sin Clasificar") {
		public String toString() {
			return "0";
		}
	},
	OEGAM("1", "Plataforma OEGAM") {
		public String toString() {
			return "1";
		}
	},
	XML("2", "Importacion XML") {
		public String toString() {
			return "2";
		}
	},
	DGT("3", "Importacion DGT") {
		public String toString() {
			return "3";
		}
	},
	SIGA("4", "Importacion SIGA") {
		public String toString() {
			return "4";
		}
	},
	OEGAM_WS("5", "Importacion OEGAM WS") {
		public String toString() {
			return "5";
		}
	},
	JSON("6", "Importacion JSON"){
		public String toString() {
			return "6";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoCreacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoCreacion convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if (SinClasificar.valorEnum.equals(valorEnum))
			return SinClasificar;
		else if (OEGAM.valorEnum.equals(valorEnum))
			return OEGAM;
		else if (XML.valorEnum.equals(valorEnum))
			return XML;
		else if (DGT.valorEnum.equals(valorEnum))
			return DGT;
		else if (SIGA.valorEnum.equals(valorEnum))
			return SIGA;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;

		if (OEGAM.valorEnum.equals(valorEnum))
			return OEGAM.nombreEnum;
		else if (XML.valorEnum.equals(valorEnum))
			return XML.nombreEnum;
		else if (DGT.valorEnum.equals(valorEnum))
			return DGT.nombreEnum;
		else if (SIGA.valorEnum.equals(valorEnum))
			return SIGA.nombreEnum;
		else
			return null;
	}
}