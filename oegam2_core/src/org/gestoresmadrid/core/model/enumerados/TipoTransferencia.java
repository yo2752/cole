package org.gestoresmadrid.core.model.enumerados;

public enum TipoTransferencia {

	/*
	 * MOD CFS 24-04-2012 - Desglose de Trámites CTI
	 * 
	 * Se cambian los tipos de trámites de CTI.
	 * 
	 * tipo 1: se deslgosa en los nuevos tipos 1, 2 y 3. tipo 4: es el antiguo tipo
	 * 2. tipo 5: es el antiguo tipo 3.
	 * 
	 */

	tipo1("1", "Cambio Titularidad Completo") {
		public String toString() {
			return "1";
		}
	},
	tipo2("2", "Finalización tras una notificación") {
		public String toString() {
			return "2";
		}
	},
	tipo3("3", "Interviene Compraventa") {
		public String toString() {
			return "3";
		}
	},
	tipo4("4", "Notificación de cambio de titularidad") {
		public String toString() {
			return "4";
		}
	},
	tipo5("5", "Entrega Compraventa") {
		public String toString() {
			return "5";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTransferencia(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	// valueOf
	public static TipoTransferencia convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return tipo1;
			case 2:
				return tipo2;
			case 3:
				return tipo3;
			case 4:
				return tipo4;
			case 5:
				return tipo5;

			default:
				return null;
		}
	}

	// valueOf
	public static TipoTransferencia convertir(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return tipo1;
		}
		if ("2".equals(valorEnum)) {
			return tipo2;
		}
		if ("3".equals(valorEnum)) {
			return tipo3;
		}
		if ("4".equals(valorEnum)) {
			return tipo4;
		}
		if ("5".equals(valorEnum)) {
			return tipo5;
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null) {
			return null;
		}
		if ("1".equals(valorEnum)) {
			return "Cambio Titularidad Completo";
		}
		if ("2".equals(valorEnum)) {
			return "Finalización tras una Notificación";
		}
		if ("3".equals(valorEnum)) {
			return "Interviene Compraventa";
		}
		if ("4".equals(valorEnum)) {
			return "Notificación de cambio de titularidad";
		}
		if ("5".equals(valorEnum)) {
			return "Entrega Compraventa";
		}

		return null;
	}

	public static String mapeoTipoTransferencia(String tipoTransf) {
		if (tipoTransf != null && !tipoTransf.isEmpty()) {
			if (tipo1.getValorEnum().equalsIgnoreCase(tipoTransf) || tipo2.getValorEnum().equalsIgnoreCase(tipoTransf)
					|| tipo3.getValorEnum().equalsIgnoreCase(tipoTransf)) {
				return "1";
			} else if (tipo4.getValorEnum().equalsIgnoreCase(tipoTransf)) {
				return "2";
			} else if (tipo5.getValorEnum().equalsIgnoreCase(tipoTransf)) {
				return "3";
			}
		}
		return null;
	}

}