package org.gestoresmadrid.core.atex5.model.enumerados;

public enum TipoTramiteAtex5 {

	Consulta_Persona("TA1", "Consulta Persona Atex5", "1") {
		public String toString() {
			return "TA1";
		}
	},
	Consulta_Vehiculo("TA2", "Consulta Vehiculo Atex5", "2") {
		public String toString() {
			return "TA2";
		}
	},
	Consulta_Documento("TA4", "Consulta Documento Atex5", "4") {
		public String toString() {
			return "TA4";
		}
	},
	Consulta_Eucaris("TA5", "Consulta Eucaris Atex5", "5") {
		public String toString() {
			return "TA5";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String tipo;

	private TipoTramiteAtex5(String valorEnum, String nombreEnum, String tipo) {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static TipoTramiteAtex5 convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (TipoTramiteAtex5 tipoTramite : TipoTramiteAtex5.values()) {
				if (tipoTramite.getValorEnum().equals(valorEnum)) {
					return tipoTramite;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoTramiteAtex5 tipoTramite : TipoTramiteAtex5.values()) {
				if (tipoTramite.getValorEnum().equals(valor)) {
					return tipoTramite.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombre) {
		if (nombre != null) {
			for (TipoTramiteAtex5 tipoTramite : TipoTramiteAtex5.values()) {
				if (tipoTramite.getNombreEnum().equals(nombre)) {
					return tipoTramite.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoTramiteAtex5 tipoTramiteAtex5) {
		if (tipoTramiteAtex5 != null) {
			for (TipoTramiteAtex5 tipoTramite : TipoTramiteAtex5.values()) {
				if (tipoTramite.getValorEnum() == tipoTramiteAtex5.getValorEnum()) {
					return tipoTramite.getNombreEnum();
				}
			}
		}
		return null;
	}

}
