package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoTramiteRegistro {

	
	MODELO_1("R1", "SOCIEDADES AN�NIMAS Y LIMITADAS. CESE Y NOMBRAMIENTO","OR111") {
		public String toString() {
			return "Sociedades an�nimas y limitadas. Cese y nombramiento";
		}
	},
	MODELO_2("R2", "SOCIEDADES AN�NIMAS Y LIMITADAS. ACEPTACI�N DE NOMBRAMIENTO","OR112") {
		public String toString() {
			return "Sociedades an�nimas y limitadas. Aceptaci�n de nombramiento";
		}
	},
	MODELO_3("R3", "SOCIEDADES AN�NIMAS. CESE Y NOMBRAMIENTO","OR121") {
		public String toString() {
			return "Sociedades an�nimas. Cese y nombramiento";
		}
	},
	MODELO_4("R4", "SOCIEDADES LIMITADAS. CESE Y NOMBRAMIENTO","OR131") {
		public String toString() {
			return "Sociedades limitadas. Cese y nombramiento";
		}
	},
	MODELO_5("R5", "SOCIEDADES AN�NIMAS Y LIMITADAS. CESE Y NOMBRAMIENTO Y DELEGACI�N DE FACULTADES","OR113") {
		public String toString() {
			return "Sociedades an�nimas y limitadas. Cese y nombramiento y delegaci�n de facultades";
		}
	},
	MODELO_6("R6", "ENV�O DE ESCRITURAS","OR21") {
		public String toString() {
			return "Env�o de escrituras al registro";
		}
	},
	MODELO_7("R7", "CONTRATO DE FINANCIACI�N","OR521") {
		public String toString() {
			return "Contrato de financiaci�n";
		}
	},
	MODELO_8("R8", "CONTRATO DE LEASING","OR522") {
		public String toString() {
			return "Contrato de leasing";
		}
	},
	MODELO_9("R9", "CONTRATO DE RENTING","OR523") {
		public String toString() {
			return "Contrato de renting";
		}
	},
	MODELO_10("R10", "CONTRATO DE CANCELACI�N","OR524") {
		public String toString() {
			return "Contrato de cancelaci�n";
		}
	},
	MODELO_11("R11", "ENV�O DE CUENTA","OR141") {
		public String toString() {
			return "Env�o de cuenta";
		}
	},
	MODELO_12("R12", "ENV�O DE LIBRO","OR142") {
		public String toString() {
			return "Env�o de libro";
		}
	},

	MODELO_13("R13", "CONTRATO DE DESISTIMIENTO","OR525") {
		public String toString() {
			return "Contrato de desistimiento";
		}
	};
	
	private String valorEnum;
	private String nombreEnum;
	private String codigoFuncion;

	private TipoTramiteRegistro(String valorEnum, String nombreEnum, String codigoFuncion) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.codigoFuncion = codigoFuncion;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTramiteRegistro convertir(String valorEnum) {
		for (TipoTramiteRegistro element : TipoTramiteRegistro.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static TipoTramiteRegistro convertirPorNombre(String valorEnum) {
		for (TipoTramiteRegistro element : TipoTramiteRegistro.values()) {
			if (element.nombreEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (TipoTramiteRegistro element : TipoTramiteRegistro.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoTramiteRegistro element : TipoTramiteRegistro.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}

	public static String getIdentificadorRm(String valorEnum) {
		if (MODELO_1.getValorEnum().equals(valorEnum)) {
			return "1";
		} else if (MODELO_2.getValorEnum().equals(valorEnum)) {
			return "2";
		} else if (MODELO_3.getValorEnum().equals(valorEnum)) {
			return "3";
		} else if (MODELO_4.getValorEnum().equals(valorEnum)) {
			return "4";
		} else if (MODELO_5.getValorEnum().equals(valorEnum)) {
			return "5";
		} else if (MODELO_6.getValorEnum().equals(valorEnum)) {
			return "6";
		} else if (MODELO_7.getValorEnum().equals(valorEnum)) {
			return "7";
		} else if (MODELO_8.getValorEnum().equals(valorEnum)) {
			return "8";
		} else if (MODELO_9.getValorEnum().equals(valorEnum)) {
			return "9";
		} else if (MODELO_10.getValorEnum().equals(valorEnum)) {
			return "0";
		}else if (MODELO_11.getValorEnum().equals(valorEnum)) {
			return "11";
		}else if (MODELO_12.getValorEnum().equals(valorEnum)) {
			return "12";
		}else if (MODELO_13.getValorEnum().equals(valorEnum)) {
			return "13";
		}
		
		return null;
	}

	public static String recuperarDescripcionCorta(String tipoTramite) {
		if (tipoTramite == null)
			return null;
		if ("R1".equals(tipoTramite)) {
			return "SA/SL - CESE Y NOMBRAMIENTO";
		} else if ("R2".equals(tipoTramite)) {
			return "SA/SL - ACEPTACI�N NOMBRAMIENTO";
		} else if ("R3".equals(tipoTramite)) {
			return "SA - CESE Y NOMBRAMIENTO";
		} else if ("R4".equals(tipoTramite)) {
			return "SL - CESE Y NOMBRAMIENTO";
		} else if ("R5".equals(tipoTramite)) {
			return "SA/SL.CESE Y NOMBRAMIENTO Y DELEGACI�N DE FACULTADES";
		} else if ("R6".equals(tipoTramite)) {
			return "ENV�O DE ESCRITURAS";
		} else if ("R7".equals(tipoTramite)) {
			return "CONTRATO FINANCIACI�N";
		} else if ("R8".equals(tipoTramite)) {
			return "CONTRATO LEASING";
		} else if ("R9".equals(tipoTramite)) {
			return "CONTRATO RENTING";
		} else if ("R10".equals(tipoTramite)) {
			return "CONTRATO CANCELACI�N";
		} else if ("R11".equals(tipoTramite)) {
			return "ENV�O CUENTA";
		} else if ("R12".equals(tipoTramite)) {
			return "ENV�O LIBRO";
		} else if ("R13".equals(tipoTramite)) {
			return "CONTRATO DESISTIMIENTO";
		} else {
			return null;
		}
	}

	/**
	 * @return the codigoFuncion
	 */
	public String getCodigoFuncion() {
		return codigoFuncion;
	}
}
