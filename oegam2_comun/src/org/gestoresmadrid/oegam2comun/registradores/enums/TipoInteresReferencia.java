package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de interés de referencia
 */
public enum TipoInteresReferencia {

	EURIBOR("1", "Referencia interbancaria a un año (euribor)"){
		@Override
		public String toString() {
			return "Referencia interbancaria a un año (euribor)";
		}
	},
	MIBOR("2", "Tipo interbancario a un año (mibor)"){
		@Override
		public String toString() {
			return "Tipo interbancario a un año (mibor)";
		}
	},
	HIPOTECARIO_BANCO("3", "Tipo de los préstamos hipotecarios a más de tres años concedidos por los bancos"){
		@Override
		public String toString() {
			return "Tipo de los préstamos hipotecarios a más de tres años concedidos por los bancos";
		}
	},
	HIPOTECARIO_ENTIDADES_CREDITO("4", "Tipo de los préstamos hipotecarios a más de tres años concedidos por el conjunto de las entidades de crédito"){
		@Override
		public String toString() {
			return "Tipo de los préstamos hipotecarios a más de tres años concedidos por el conjunto de las entidades de crédito";
		}
	},
	CECA("5", "Tipo activo de referencia de las cajas de ahorro (indicador CECA, tipo activo)"){
		@Override
		public String toString() {
			return "Tipo activo de referencia de las cajas de ahorro (indicador CECA, tipo activo)";
		}
	},
	RENDIMIENTO_INTERNO("6", "Tipo de rendimiento interno en el mercado secundario de la deuda pública"){
		@Override
		public String toString() {
			return "Tipo de rendimiento interno en el mercado secundario de la deuda pública";
		}
	}
	;

	private String valorEnum;
	private String nombreEnum;

	private TipoInteresReferencia(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getKey(){
		return getValorEnum();
	}

	@Override
	public String toString(){
		return getNombreEnum();
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoInteresReferencia element : TipoInteresReferencia.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

}
