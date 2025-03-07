package org.gestoresmadrid.core.model.enumerados;

public enum TipoActualizacion {

	CRE("CRE", "CREACIÓN"), MOD("MOD", "MODIFICACIÓN"), DMT("DMT", "DESASIGNAR MICROTASA"),
	AMT("AMT", "ASIGNAR MICROTASA"), DDT("DDT", "DESASIGNAR TASA TRAMITE DUPLICADO"),
	DTI("DTI", "DESASIGNAR TASA INTEVE"), AAC("AAC", "ACTUALIZACION ALIAS COLEGIADO"), MFE("MFE", "Modificacion Fecha"),
	CFE("CFE", "CAMBIO FORMATO A ELECTRONICA"), CFP("CFP", "CAMBIO FORMATO A PEGATINA");

	private String valorEnum;
	private String nombreEnum;

	private TipoActualizacion(String valorEnum, String nombreEnum) {
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

	public static String getNombrePorValor(String valor) {
		if (valor != null) {
			for (TipoActualizacion tipo : TipoActualizacion.values()) {
				if (tipo.getValorEnum().equals(valor)) {
					return tipo.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String getValorPorNombre(String nombre) {
		if (nombre != null) {
			for (TipoActualizacion tipo : TipoActualizacion.values()) {
				if (tipo.getValorEnum().equals(nombre)) {
					return tipo.getValorEnum();
				}
			}
		}
		return null;
	}

	public static TipoActualizacion convertirTexto(String nombre) {
		if (nombre != null) {
			for (TipoActualizacion tipo : TipoActualizacion.values()) {
				if (tipo.getValorEnum().equals(nombre)) {
					return tipo;
				}
			}
		}
		return null;
	}

	public static TipoActualizacion convertirValor(String valor) {
		if (valor != null) {
			for (TipoActualizacion tipo : TipoActualizacion.values()) {
				if (tipo.getValorEnum().equals(valor)) {
					return tipo;
				}
			}
		}
		return null;
	}
}
