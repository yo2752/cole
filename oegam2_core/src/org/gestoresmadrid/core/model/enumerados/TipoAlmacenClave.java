package org.gestoresmadrid.core.model.enumerados;

public enum TipoAlmacenClave {

	PUBLICAS("CLAVES_PUBLICAS", "Claves públicas") {
		public String toString() {
			return "Claves públicas";
		}
	},
	PRIVADAS("CLAVE_PRIVADA", "Clave privadas") {
		public String toString() {
			return "Clave privadas";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoAlmacenClave(String valorEnum, String nombreEnum) {
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

	public static TipoAlmacenClave convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (TipoAlmacenClave tipoAlmacen : TipoAlmacenClave.values()) {
				if (tipoAlmacen.getValorEnum().equals(valorEnum)) {
					return tipoAlmacen;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoAlmacenClave tipoAlmacen : TipoAlmacenClave.values()) {
				if (tipoAlmacen.getValorEnum().equals(valor)) {
					return tipoAlmacen.getNombreEnum();
				}
			}
		}
		return null;
	}

}
