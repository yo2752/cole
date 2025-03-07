package org.gestoresmadrid.core.trafico.interga.model.enumerados;

public enum TipoTramiteInterga {

	Permiso_Internacional("PI", "Permiso Internacional", "C") {
		public String toString() {
			return "PI";
		}
	},
	Duplicado_Permiso_Conducir("DPC", "Duplicado Permiso Conducir", "C") {
		public String toString() {
			return "DPC";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String tipo;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getTipo() {
		return tipo;
	}

	private TipoTramiteInterga(String valorEnum, String nombreEnum, String tipo) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.tipo = tipo;
	}

	public static TipoTramiteInterga convertir(String valorEnum) {
		for (TipoTramiteInterga element : TipoTramiteInterga.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoTramiteInterga element : TipoTramiteInterga.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
