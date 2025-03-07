package org.gestoresmadrid.core.model.enumerados;

public enum EstadoImpresion {

	SIN_DESCARGAR(new Integer(0), "SIN DESCARGAR") {
		public String toString() {
			return "0";
		}
	},
	DESCARGADO(new Integer(1), "DESCARGADO") {
		public String toString() {
			return "1";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private EstadoImpresion(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static EstadoImpresion convertir(Integer valorEnum) {
		if (valorEnum == null)
			return null;

		if (valorEnum.equals(0))
			return SIN_DESCARGAR;
		else if (valorEnum.equals(1))
			return DESCARGADO;
		else
			return null;
	}
}