package org.gestoresmadrid.core.model.enumerados;

public enum TipoOperacion {

	CR("CR", "Creación") {
		public String toString() {
			return "CR";
		}
	},
	RC("RC", "Recarga") {
		public String toString() {
			return "RC";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoOperacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static TipoOperacion convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (TipoOperacion tipoOperacion : TipoOperacion.values()) {
				if (tipoOperacion.getValorEnum().equals(valorEnum)) {
					return tipoOperacion;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoOperacion tipoMaterial : TipoOperacion.values()) {
				if (tipoMaterial.getValorEnum().equals(valor)) {
					return tipoMaterial.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoOperacion tipo) {
		if (tipo != null) {
			for (TipoOperacion tipoMaterial : TipoOperacion.values()) {
				if (tipoMaterial.getValorEnum() == tipo.getValorEnum()) {
					return tipoMaterial.getNombreEnum();
				}
			}
		}
		return null;
	}

}