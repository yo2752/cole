package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoCargo {

	ADMINISTRADOR_MANCOMUNADO("1", "Administrador Mancomunado", "ADMINISTRADOR MANCOMUNADO") {
		public String toString() {
			return "Administrador Mancomunado";
		}
	},
	ADMINISTRADOR_SOLIDARIO("2", "Administrador Solidario", "ADMINISTRADOR SOLIDARIO") {
		public String toString() {
			return "Administrador Solidario";
		}
	},
	ADMINISTRADOR_UNICO("3", "Administrador Único", "ADMINISTRADOR UNICO") {
		public String toString() {
			return "Administrador Único";
		}
	},
	AUDITOR_CUENTAS("4", "Auditor Cuentas", "AUDITOR CUENTAS") {
		public String toString() {
			return "Administrador Único";
		}
	},
	CONSEJERO("5", "Consejero", "CONSEJERO") {
		public String toString() {
			return "Consejero";
		}
	},
	CONSEJERO_DELEGADO_DEL_CONSEJO_DE_ADMINISTRACION("6", "Consejero Delegado del Consejo de Administración", "CONSEJERO DELEGADO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Delegado del Consejo de Administración";
		}
	},
	CONSEJERO_DELEGADO_MANCOMUNADO_DEL_CONSEJO_DE_ADMINISTRACION("7", "Consejero Delegado Mancomunado del Consejo de Administración", "CONSEJERO DELEGADO MANCOMUNADO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Delegado Mancomunado del Consejo de Administración";
		}
	},
	CONSEJERO_DELEGADO_MANCOMUNADO_Y_SOLIDARIO_DEL_CONSEJO_DE_ADMINISTRACION("8", "Consejero Delegado Mancomunado y Solidario del Consejo de Administración",
			"CONSEJERO DELEGADO MANCOMUNADO Y SOLIDARIO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Delegado Mancomunado y Solidario del Consejo de Administración";
		}
	},
	CONSEJERO_DELEGADO_SOLIDARIO_DEL_CONSEJO_DE_ADMINISTRACION("9", "Consejero Delegado Solidario del Consejo de Administración", "CONSEJERO DELEGADO SOLIDARIO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Delegado Solidario del Consejo de Administración";
		}
	},
	CONSEJERO_DELEGADO_SUPLENTE_DEL_CONSEJO_DE_ADMINISTRACION("10", "Consejero Delegado Suplente del Consejo de Administración", "CONSEJERO DELEGADO SUPLENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Delegado Suplente del Consejo de Administración";
		}
	},
	CONSEJERO_INDEPENDIENTE_DEL_CONSEJO_DE_ADMINISTRACION("11", "Consejero Independiente del Consejo de Administración", "CONSEJERO INDEPENDIENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Independiente del Consejo de Administración";
		}
	},
	CONSEJERO_SUPLENTE_DEL_CONSEJO_DE_ADMINISTRACION("12", "Consejero Suplente del Consejo de Administración", "CONSEJERO SUPLENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Consejero Suplente del Consejo de Administración";
		}
	},
	PRESIDENTE_DE_LA_JUNTA("13", "Presidente de la Junta", "PRESIDENTE DE LA JUNTA") {
		public String toString() {
			return "Presidente de la Junta";
		}
	},
	PRESIDENTE_DEL_CONSEJO_DE_ADMINISTRACION("14", "Presidente del Consejo de Administración", "PRESIDENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Presidente del Consejo de Administración";
		}
	},
	PRESIDENTE_EJECUTIVO_DEL_CONSEJO_DE_ADMINISTRACION("15", "Presidente Ejecutivo del Consejo de Administración", "PRESIDENTE EJECUTIVO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Presidente Ejecutivo del Consejo de Administración";
		}
	},
	PRESIDENTE_HONORIFICO_DEL_CONSEJO_DE_ADMINISTRACION("16", "Presidente Honorífico del Consejo de Administración", "PRESIDENTE HONORIFICO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Presidente Honorífico del Consejo de Administración";
		}
	},
	PRESIDENTE_SUPLENTE_DEL_CONSEJO_DE_ADMINISTRACION("17", "Presidente Suplente del Consejo de Administración", "PRESIDENTE SUPLENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Presidente Suplente del Consejo de Administración";
		}
	},
	SECRETARIO_DE_LA_JUNTA("18", "Secretario de la Junta", "SECRETARIO DE LA JUNTA") {
		public String toString() {
			return "Secretario de la Junta";
		}
	},
	SECRETARIO_DEL_CONSEJO_DE_ADMINISTRACION("19", "Secretario del Consejo de Administración", "SECRETARIO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Secretario del Consejo de Administración";
		}
	},
	SECRETARIO_SUPLENTE_DEL_CONSEJO_DE_ADMINISTRACION("20", "Secretario Suplente del Consejo de Administración", "SECRETARIO SUPLENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Secretario Suplente del Consejo de Administración";
		}
	},
	TESORERO_DEL_CONSEJO_DE_ADMINISTRACION("21", "Tesorero del Consejo de Administración", "TESORERO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Tesorero del Consejo de Administración";
		}
	},
	VICEPRESIDENTE_DEL_CONSEJO_DE_ADMINISTRACION("22", "Vicepresidente del Consejo de Administración", "VICEPRESIDENTE DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Vicepresidente del Consejo de Administración";
		}
	},
	VICESECRETARIO_DEL_CONSEJO_DE_ADMINISTRACION("23", "Vicesecretario del Consejo de Administración", "VICESECRETARIO DEL CONSEJO DE ADMINISTRACION") {
		public String toString() {
			return "Vicesecretario del Consejo de Administración";
		}
	};

	private String codigoEnum;
	private String descripcionEnum;
	private String valorXmlEnum;

	private TipoCargo(String codigoEnum, String descripcionEnum, String valorXmlEnum) {
		this.codigoEnum = codigoEnum;
		this.descripcionEnum = descripcionEnum;
		this.valorXmlEnum = valorXmlEnum;
	}

	public String getCodigoEnum() {
		return codigoEnum;
	}

	public void setCodigoEnum(String codigoEnum) {
		this.codigoEnum = codigoEnum;
	}

	public String getDescripcionEnum() {
		return descripcionEnum;
	}

	public void setDescripcionEnum(String descripcionEnum) {
		this.descripcionEnum = descripcionEnum;
	}

	public String getValorXmlEnum() {
		return valorXmlEnum;
	}

	public void setValorXmlEnum(String valorXmlEnum) {
		this.valorXmlEnum = valorXmlEnum;
	}

	public static String convertirTexto(String codigoEnum) {
		for (TipoCargo element : TipoCargo.values()) {
			if (element.codigoEnum.equals(codigoEnum)) {
				return element.descripcionEnum;
			}
		}
		return null;
	}
	
	public static String convertirTextoXml(String codigoEnum) {
		for (TipoCargo element : TipoCargo.values()) {
			if (element.codigoEnum.equals(codigoEnum)) {
				return element.valorXmlEnum;
			}
		}
		return null;
	}
}