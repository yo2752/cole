package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum TiposInspeccionItv {

	PREVIA_MATRICULACION("M", "Previa matriculación") {
		public String toString() {
			return "Previa matriculación";
		}
	},
	EXENTO_ITV("E", "Exento ITV") {
		public String toString() {
			return "Exento ITV";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TiposInspeccionItv(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String getDescripcion(String valor) {
		if (valor == null) {
			return null;
		}
		if (valor.equals(TiposInspeccionItv.PREVIA_MATRICULACION.getValorEnum())) {
			return TiposInspeccionItv.PREVIA_MATRICULACION.getNombreEnum();
		}
		if (valor.equals(TiposInspeccionItv.EXENTO_ITV.getValorEnum())) {
			return TiposInspeccionItv.EXENTO_ITV.getNombreEnum();
		}
		return null;
	}
}
