package org.gestoresmadrid.core.licencias.model.enumerados;

import java.math.BigDecimal;

public enum TipoResumenEdificacionEnum {

	Vivienda("1", "Vivienda") {
		public String toString() {
			return "1";
		}
	},
	Trastero("2", "Trastero") {
		public String toString() {
			return "2";
		}
	},
	Garaje("3", "Garaje") {
		public String toString() {
			return "3";
		}
	},
	Local("4", "Local") {
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoResumenEdificacionEnum(String valorEnum, String nombreEnum) {
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

	public static TipoResumenEdificacionEnum convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (TipoResumenEdificacionEnum estado : TipoResumenEdificacionEnum.values()) {
				if (estado.getValorEnum().equals(valorEnum)) {
					return estado;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoResumenEdificacionEnum estado : TipoResumenEdificacionEnum.values()) {
				if (estado.getValorEnum().equals(valor)) {
					return estado.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoResumenEdificacionEnum estadoM) {
		if (estadoM != null) {
			for (TipoResumenEdificacionEnum estado : TipoResumenEdificacionEnum.values()) {
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
