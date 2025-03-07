package org.gestoresmadrid.core.model.enumerados;

public enum NoSujeccionOExencion {

	NS1("NS1", "NS1 - TRANSPORTE DE MERCANCIAS") {
		public String toString() {
			return "NS1";
		}
	},
	NS2("NS2", "NS2 - TRANSPORTE DE VIAJEROS") {
		public String toString() {
			return "NS2";
		}
	},
	NS3("NS3", "NS3 - CICLOMOTORES DE 2 O 3 RUEDAS") {
		public String toString() {
			return "NS3";
		}
	},
	NS4("NS4", "NS4 - VEHICULOS DE 2 O 3 RUEDAS DE MENOS DE 250cc") {
		public String toString() {
			return "NS4";
		}
	},
	NS5("NS5", "NS5 - COCHES DE MINUSVALIDOS") {
		public String toString() {
			return "NS5";
		}
	},
	NS6("NS6", "NS6 - VEHICULOS ESPECIALES") {
		public String toString() {
			return "NS6";
		}
	},
	NS7("NS7", "NS7 - FURGONES O FURGONETAS") {
		public String toString() {
			return "NS7";
		}
	},
	ET4("ET4", "ET4 - TRASLADO DE RESIDENCIAS A ESPAÑA") {
		public String toString() {
			return "ET4";
		}
	};

	// valueOf
	public static NoSujeccionOExencion convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		else if ("NS1".equals(valorEnum))
			return NS1;
		else if ("NS2".equals(valorEnum))
			return NS2;
		else if ("NS3".equals(valorEnum))
			return NS3;
		else if ("NS4".equals(valorEnum))
			return NS4;
		else if ("NS5".equals(valorEnum))
			return NS5;
		else if ("NS6".equals(valorEnum))
			return NS6;
		else if ("NS7".equals(valorEnum))
			return NS7;
		else if ("ET4".equals(valorEnum))
			return ET4;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return "";
		else if ("NS1".equals(valorEnum))
			return NS1.getNombreEnum();
		else if ("NS2".equals(valorEnum))
			return NS2.getNombreEnum();
		else if ("NS3".equals(valorEnum))
			return NS3.getNombreEnum();
		else if ("NS4".equals(valorEnum))
			return NS4.getNombreEnum();
		else if ("NS5".equals(valorEnum))
			return NS5.getNombreEnum();
		else if ("NS6".equals(valorEnum))
			return NS6.getNombreEnum();
		else if ("NS7".equals(valorEnum))
			return NS7.getNombreEnum();
		else if ("ET4".equals(valorEnum))
			return ET4.getNombreEnum();
		else
			return "";
	}

	private String valorEnum;
	private String nombreEnum;

	private NoSujeccionOExencion(String valorEnum, String nombreEnum) {
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
