package org.gestoresmadrid.oegamDocBase.enumerados;

import org.gestoresmadrid.utilidades.listas.ListsOperator;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public enum TasaSimultaneaEnum {

	TASA_SIMULTANEA_DOBLE("SM2", "Tasa SM2"){
		public String toString() {
			return "SM2";
		}
	},
	TASA_SIMULTANEA_TRIPLE("SM3", "Tasa SM3"){
		public String toString() {
			return "SM3";
		}
	},
	TASA_SIMULTANEA_CUADRUPLE("SM4", "Tasa SM4"){
		public String toString() {
			return "SM4";
		}
	},
	TASA_SIMULTANEA_QUINTUPLE("SM5", "Tasa SM5"){
		public String toString() {
			return "SM5";
		}
	},
	TASA_SIMULTANEA_SEXTUPLE("SM6", "Tasa SM6"){
		public String toString() {
			return "SM6";
		}
	},
	TASA_SIMULTANEA_SEPTUPLE("SM7", "Tasa SM7"){
		public String toString() {
			return "SM7";
		}
	},
	TASA_SIMULTANEA_OCTUPLE("SM8", "Tasa SM8"){
		public String toString() {
			return "SM8";
		}
	},
	TASA_SIMULTANEA_NONUPLO("SM9", "Tasa SM9"){
		public String toString() {
			return "SM9";
		}
	},
	TASA_SIMULTANEA_DECUPLO("SM10", "Tasa SM10"){
		public String toString() {
			return "SM10";
		}
	};

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListsOperator.class);
	
	private String valorEnum;
	private String nombreEnum;
	
	private TasaSimultaneaEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	public String getValorEnum() {
		return valorEnum;
	}
	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}
	public String getNombreEnum() {
		return nombreEnum;
	}
	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static TasaSimultaneaEnum convertir(String valorEnum) {
		for (TasaSimultaneaEnum element : TasaSimultaneaEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TasaSimultaneaEnum element : TasaSimultaneaEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (TasaSimultaneaEnum element : TasaSimultaneaEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}

	public static Boolean esTasaSimultanea(String valorEnum) {
		for (TasaSimultaneaEnum element : TasaSimultaneaEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	
	/**
	 * Método que obtiene el tipo de tasa para simultáneas 
	 * 
	 * @param numeroExpedientes numero de expedientes
	 * @return TasaSimultaneaEnum - tipo de tasa simultánea.
	 */
	public static TasaSimultaneaEnum obtenerTipoTasaSimultanea(int numeroExpedientes) {

		if (numeroExpedientes == 2) {
			return TASA_SIMULTANEA_DOBLE;	
		
		} else if (numeroExpedientes == 3) {
			return TASA_SIMULTANEA_TRIPLE; 
		
		} else if (numeroExpedientes == 4) {
			return TASA_SIMULTANEA_CUADRUPLE; 
		
		} else if (numeroExpedientes == 5) {
			return TASA_SIMULTANEA_QUINTUPLE; 
		
		} else if (numeroExpedientes == 6) {
			return TASA_SIMULTANEA_SEXTUPLE; 
		
		} else if (numeroExpedientes == 7) {
			return TASA_SIMULTANEA_SEPTUPLE; 
		
		} else if (numeroExpedientes == 8) {
			return TASA_SIMULTANEA_OCTUPLE; 
		
		} else if (numeroExpedientes == 9) {
			return TASA_SIMULTANEA_NONUPLO; 
		
		} else if (numeroExpedientes == 10) {
			return TASA_SIMULTANEA_DECUPLO; 
		
		} else {
			log.error("Máximo de simultáneas es 10");
			return null;
		}
	}	
}