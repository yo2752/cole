package org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados;

import org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseTipoCarpetaEnum;

//el enumerado no se utiliza puesto que se va ha utilizar la tabla de base de datos
public enum TipoEstadisticas {

	MATRICULAS_PDF("1", "Matriculas con PDF") {
		public String toString() {
			return "1";
		}
	},
	TRANSFERENCIAS_A("2", "Transferencias A") {
		public String toString() {
			return "2";
		}
	},
	MATEGE("3", "Matege") {
		public String toString() {
			return "3";
		}
	},
	TRANSFERENCIAS_B("4", "Transferencias B") {
		public String toString() {
			return "4";
		}
	},
	PLACAS_VERDE_BLANCA("5", "Placas de Verdes a Blanca - Verdes") {
		public String toString() {
			return "5";
		}
	},
	TURISTICAS_HISTORICOS("6", "Turisticas/Historicos") {
		public String toString() {
			return "6";
		}
	},
	CAMBIO_DOMICILIO("7", "Cambio de Domicilio de Sociedad") {
		public String toString() {
			return "7";
		}
	},
	LIBROS_MATRICULA("8", "Libros de Matrícula") {
		public String toString() {
			return "8";
		}
	},
	RENOVACION_P_CONDUCIR("9", "Renovación P. Conducir") {
		public String toString() {
			return "9";
		}
	},
	DUPLICADOS_P_CONDUCIR("10", "Duplicados P. Conducir") {
		public String toString() {
			return "10";
		}
	},
	REFORMAS("11", "Reformas") {
		public String toString() {
			return "11";
		}
	},
	TRANSFERENCIAS("12", "Transferencias(Fusion)") {
		public String toString() {
			return "12";
		}
	},
	REHABILITACIONES("13", "Rehabilitaciones") {
		public String toString() {
			return "13";
		}
	},
	CANJES("14", "Canjes") {
		public String toString() {
			return "14";
		}
	},
	TIPO_BT_B("15", "Tipo BT/B. Exp") {
		public String toString() {
			return "15";
		}
	},
	MATRICULAS_SIN_PDF("16", "Matriculas sin Pdf") {
		public String toString() {
			return "16";
		}
	},
	COTEJOS("17", "Cotejos") {
		public String toString() {
			return "17";
		}
	},
	TIPO_C("18", "Tipo C(Altas de Bajas)") {
		public String toString() {
			return "18";
		}
	},
	TIPO_E("19", "Tipo E(Duplicados)") {
		public String toString() {
			return "19";
		}
	},
	TIPO_I("20", "Tipo I(Urgentes)") {
		public String toString() {
			return "20";
		}
	},
	TIPO_J("21", "Tipo J(Bajas Definitivas)") {
		public String toString() {
			return "21";
		}
	},
	CTTI("22", "CTTI") {
		public String toString() {
			return "22";
		}
	},
	INFORMES("23", "Informes") {
		public String toString() {
			return "23";
		}
	},
	TIPO_H("24", "Tipo H(Rematriculación)") {
		public String toString() {
			return "24";
		}
	},
	VEHICULOS_ESPECIALES("25", "Vehiculos Especiales") {
		public String toString() {
			return "25";
		}
	},
	PLACAS_ROJAS("26", "Placas Rojas") {
		public String toString() {
			return "26";
		}
	},
	ALEGACIONES("27", "Alegaciones") {
		public String toString() {
			return "27";
		}
	},
	RECURSOS("28", "Recursos") {
		public String toString() {
			return "28";
		}
	},
	CTIT_SIN_QR("29", "CTIT sin QR") {
		public String toString() {
			return "29";
		}
	},
	TIPO_A_SIN_QR("30", "Tipo A sin QR") {
		public String toString() {
			return "30";
		}
	},
	INCIDENCIA_TRANSMISION("31", "Incidencias de Transmisión") {
		public String toString() {
			return "31";
		}
	},
	INCIDENCIA_MATRICULACION("32", "Incidencias de Matriculación") {
		public String toString() {
			return "32";
		}
	},
	INCIDENCIA_CONDUCTORES("33", "Incidencias de conductores") {
		public String toString() {
			return "33";
		}
	},
	CAMBIO_DENOMINACION("34", "Cambio de denominación") {
		public String toString() {
			return "34";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private TipoEstadisticas(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private TipoEstadisticas convertir(Integer valorEnum) {
		if (valorEnum == null) {
			return null;
		} else if (valorEnum == 1) {
			return MATRICULAS_PDF;
		} else if (valorEnum == 2) {
			return TRANSFERENCIAS_A;
		} else if (valorEnum == 3) {
			return MATEGE;
		} else if (valorEnum == 4) {
			return TRANSFERENCIAS_B;
		} else if (valorEnum == 5) {
			return PLACAS_VERDE_BLANCA;
		} else if (valorEnum == 6) {
			return TURISTICAS_HISTORICOS;
		} else if (valorEnum == 7) {
			return CAMBIO_DOMICILIO;
		} else if (valorEnum == 8) {
			return LIBROS_MATRICULA;
		} else if (valorEnum == 9) {
			return RENOVACION_P_CONDUCIR;
		} else if (valorEnum == 10) {
			return DUPLICADOS_P_CONDUCIR;
		} else if (valorEnum == 11) {
			return REFORMAS;
		} else if (valorEnum == 12) {
			return TRANSFERENCIAS;
		} else if (valorEnum == 13) {
			return REHABILITACIONES;
		} else if (valorEnum == 14) {
			return CANJES;
		} else if (valorEnum == 15) {
			return TIPO_BT_B;
		} else if (valorEnum == 16) {
			return MATRICULAS_SIN_PDF;
		} else if (valorEnum == 17) {
			return COTEJOS;
		} else if (valorEnum == 18) {
			return TIPO_C;
		} else if (valorEnum == 19) {
			return TIPO_E;
		} else if (valorEnum == 20) {
			return TIPO_I;
		} else if (valorEnum == 21) {
			return TIPO_J;
		} else if (valorEnum == 22) {
			return CTTI;
		} else if (valorEnum == 23) {
			return INFORMES;
		} else if (valorEnum == 24) {
			return TIPO_H;
		} else if (valorEnum == 25) {
			return VEHICULOS_ESPECIALES;
		} else if (valorEnum == 26) {
			return PLACAS_ROJAS;
		} else if (valorEnum == 27) {
			return ALEGACIONES;
		} else if (valorEnum == 28) {
			return RECURSOS;
		}else if (valorEnum == 29) {
			return CTIT_SIN_QR;
		} else if (valorEnum == 30) {
			return TIPO_A_SIN_QR;
		} else if (valorEnum == 31) {
			return INCIDENCIA_TRANSMISION;
		} else if (valorEnum == 32) {
			return INCIDENCIA_MATRICULACION;
		} else if (valorEnum == 33) {
			return INCIDENCIA_CONDUCTORES;
		} else if (valorEnum == 34) {
			return CAMBIO_DENOMINACION;
		} 
		return null;
	}

	public static String[] convertirYerbabuena(String codigo) {
		String[] carpetas = null;
		if (codigo == null) {
			return null;
		} else if (codigo.equals("1")) {
			carpetas = new String[2];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_MATR_TIPOA.getValorEnum();
			carpetas[1] = DocumentoBaseTipoCarpetaEnum.CARPETA_MATR_PDF.getValorEnum();
			return carpetas;
		} else if (codigo.equals("2")) {
			carpetas = new String[1];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_TIPO_A.getValorEnum();
			return carpetas;
		} else if (codigo.equals("3")) {
			carpetas = new String[1];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_MATE.getValorEnum();
			return carpetas;
		} else if (codigo.equals("4")) {
			carpetas = new String[1];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_TIPO_B.getValorEnum();
			return carpetas;
		} else if (codigo.equals("12")) {
			carpetas = new String[1];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_FUSION.getValorEnum(); 
			return carpetas;
		} else if (codigo.equals("20")) {
			carpetas = new String[1];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_TIPO_I.getValorEnum();
			return carpetas;
		} else if (codigo.equals("22")) {
			carpetas = new String[1];
			carpetas[0] = DocumentoBaseTipoCarpetaEnum.CARPETA_CTIT.getValorEnum();
			return carpetas;
		}
		return null;
	}
}
