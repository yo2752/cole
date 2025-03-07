package org.gestoresmadrid.core.model.enumerados;

public enum TipoConverterSiNo {

	SI("1", "Si", 0) {
		public String toString() {
			return "1";
		}
	},
	NO("0", "No", 0) {
		public String toString() {
			return "0";
		}
	},
	S("1", "S", 1) {
		public String toString() {
			return "1";
		}
	},
	N("0", "N", 1) {
		public String toString() {
			return "0";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private int tipo;

	private TipoConverterSiNo(String valorEnum, String nombreEnum, int tipo) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.tipo = tipo;
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

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public static TipoConverterSiNo convertir(String valor, int tipo) {
		if (valor != null && !valor.isEmpty()) {
			for (TipoConverterSiNo tipoSN : TipoConverterSiNo.values()) {
				if (tipoSN.getValorEnum().equals(valor) && tipoSN.getTipo() == tipo) {
					return tipoSN;
				}
			}
		}
		return null;
	}

	public static TipoConverterSiNo convertirPorNombre(String nombre, int tipo) {
		if (nombre != null && !nombre.isEmpty()) {
			for (TipoConverterSiNo tipoSN : TipoConverterSiNo.values()) {
				if (tipoSN.getNombreEnum().equals(nombre) && tipoSN.getTipo() == tipo) {
					return tipoSN;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor, int tipo) {
		if (valor != null && !valor.isEmpty()) {
			for (TipoConverterSiNo tipoSN : TipoConverterSiNo.values()) {
				if (tipoSN.getValorEnum().equals(valor) && tipoSN.getTipo() == tipo) {
					return tipoSN.getNombreEnum();
				}
			}
		}
		return null;
	}
}