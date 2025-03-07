package org.gestoresmadrid.oegamDocBase.enumerados;

public enum DocumentoBaseTipoCarpetaEnum {

	CARPETA_CTIT("CTIT", "Carpeta CTIT"){
		public String toString() {
			return "CTIT";
		}
	},
	CARPETA_TIPO_A("A", "Carpeta A"){
		public String toString() {
			return "A";
		}
	},
	CARPETA_TIPO_B("B", "Carpeta B"){
		public String toString() {
			return "B";
		}
	},
	CARPETA_TIPO_I("I", "Carpeta I"){
		public String toString() {
			return "I";
		}
	},
	CARPETA_FUSION("FUSION", "Carpeta FUSION"){
		public String toString() {
			return "FUSION";
		}
	},
	CARPETA_MATE("MATE", "Carpeta MATE"){
		public String toString() {
			return "MATE";
		}
	},
	CARPETA_MATR_PDF("MATR_PDF", "Carpeta NO MATE"){
		public String toString() {
			return "MATR_PDF";
		}
	},
	CARPETA_MATR_TIPOA("MATR_TIPOA", "Carpeta VERDE TIPO A"){
		public String toString() {
			return "MATR_TIPOA";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private DocumentoBaseTipoCarpetaEnum(String valorEnum, String nombreEnum) {
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

	public static DocumentoBaseTipoCarpetaEnum convertir(String valorEnum) {
		for (DocumentoBaseTipoCarpetaEnum element : DocumentoBaseTipoCarpetaEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (DocumentoBaseTipoCarpetaEnum element : DocumentoBaseTipoCarpetaEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (DocumentoBaseTipoCarpetaEnum element : DocumentoBaseTipoCarpetaEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}

}