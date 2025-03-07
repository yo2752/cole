package org.gestoresmadrid.core.licencias.model.enumerados;

import java.math.BigDecimal;

public enum TipoEdificacionEnum {

	Edificacion_Alta("1", "Alta") {
		public String toString() {
			return "1";
		}
	},
	Edificacion_Baja("2", "Baja") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoEdificacionEnum(String valorEnum, String nombreEnum) {
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

	public static TipoEdificacionEnum convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (TipoEdificacionEnum estado : TipoEdificacionEnum.values()) {
				if (estado.getValorEnum().equals(valorEnum)) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoEdificacionEnum estado : TipoEdificacionEnum.values()) {
				if (estado.getValorEnum().equals(valor)) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoEdificacionEnum estadoM) {
		if (estadoM != null) {
			for (TipoEdificacionEnum estado : TipoEdificacionEnum.values()) {
				if (estado.getValorEnum() == estadoM.getValorEnum()) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirEstadoBigDecimal(BigDecimal estado) {
		if (estado != null) {
			return convertirTexto(estado.toString());
		}
		return "";
	}
}
