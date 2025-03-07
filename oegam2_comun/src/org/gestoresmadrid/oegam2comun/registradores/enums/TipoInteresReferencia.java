package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de inter�s de referencia
 */
public enum TipoInteresReferencia {

	EURIBOR("1", "Referencia interbancaria a un a�o (euribor)"){
		@Override
		public String toString() {
			return "Referencia interbancaria a un a�o (euribor)";
		}
	},
	MIBOR("2", "Tipo interbancario a un a�o (mibor)"){
		@Override
		public String toString() {
			return "Tipo interbancario a un a�o (mibor)";
		}
	},
	HIPOTECARIO_BANCO("3", "Tipo de los pr�stamos hipotecarios a m�s de tres a�os concedidos por los bancos"){
		@Override
		public String toString() {
			return "Tipo de los pr�stamos hipotecarios a m�s de tres a�os concedidos por los bancos";
		}
	},
	HIPOTECARIO_ENTIDADES_CREDITO("4", "Tipo de los pr�stamos hipotecarios a m�s de tres a�os concedidos por el conjunto de las entidades de cr�dito"){
		@Override
		public String toString() {
			return "Tipo de los pr�stamos hipotecarios a m�s de tres a�os concedidos por el conjunto de las entidades de cr�dito";
		}
	},
	CECA("5", "Tipo activo de referencia de las cajas de ahorro (indicador CECA, tipo activo)"){
		@Override
		public String toString() {
			return "Tipo activo de referencia de las cajas de ahorro (indicador CECA, tipo activo)";
		}
	},
	RENDIMIENTO_INTERNO("6", "Tipo de rendimiento interno en el mercado secundario de la deuda p�blica"){
		@Override
		public String toString() {
			return "Tipo de rendimiento interno en el mercado secundario de la deuda p�blica";
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
