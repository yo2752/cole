package org.gestoresmadrid.core.model.enumerados;

public enum TipoCarpeta {

	CARPETA_CTIT("CTIT", "Carpeta CTIT") {
		public String toString() {
			return "CTIT";
		}
	},
	CARPETA_TIPO_A("A", "Carpeta A") {
		public String toString() {
			return "A";
		}
	},
	CARPETA_TIPO_B("B", "Carpeta B") {
		public String toString() {
			return "B";
		}
	},
	CARPETA_TIPO_I("I", "Carpeta I") {
		public String toString() {
			return "I";
		}
	},
	CARPETA_FUSION("FUSION", "Carpeta FUSION") {
		public String toString() {
			return "FUSION";
		}
	},
	CARPETA_MATE("MATE", "Carpeta MATE") {
		public String toString() {
			return "MATE";
		}
	},
	CARPETA_MATR_PDF("MATR_PDF", "Carpeta NO MATE") {
		public String toString() {
			return "MATR_PDF";
		}
	},
	CARPETA_MATR_TIPOA("MATR_TIPOA", "Carpeta VERDE TIPO A") {
		public String toString() {
			return "MATR_TIPOA";
		}
	},
	CARPETA_BTV("BTV", "Carpeta Btv") {
		public String toString() {
			return "BTV";
		}
	},
	CARPETA_DUPLICADO("DUPLICADO", "Carpeta Duplicado") {
		public String toString() {
			return "DUPLICADO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoCarpeta(String valorEnum, String nombreEnum) {
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

	public static String convertirValor(String valorEnum) {
		for (TipoCarpeta carpeta : TipoCarpeta.values()) {
			if (carpeta.getValorEnum().equals(valorEnum)) {
				return carpeta.getNombreEnum();
			}
		}
		return null;
	}

	public static TipoCarpeta convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if ("CTIT".equalsIgnoreCase(valorEnum))
			return CARPETA_CTIT;
		else if ("A".equalsIgnoreCase(valorEnum))
			return CARPETA_TIPO_A;
		else if ("B".equalsIgnoreCase(valorEnum))
			return CARPETA_TIPO_B;
		else if ("I".equalsIgnoreCase(valorEnum))
			return CARPETA_TIPO_I;
		else if ("FUSION".equalsIgnoreCase(valorEnum))
			return CARPETA_FUSION;
		else if ("MATE".equalsIgnoreCase(valorEnum))
			return CARPETA_MATE;
		else if ("MATR_PDF".equalsIgnoreCase(valorEnum))
			return CARPETA_MATR_PDF;
		else if ("MATR_TIPOA".equalsIgnoreCase(valorEnum))
			return CARPETA_MATR_TIPOA;
		else if ("DUPLICADO".equalsIgnoreCase(valorEnum))
			return CARPETA_DUPLICADO;
		else
			return null;
	}

	public static TipoCarpeta convertirTipoCarpetaBean(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			if ("0".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_CTIT;
			} else if ("-1".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_TIPO_A;
			} else if ("-2".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_TIPO_B;
			} else if ("-3".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_TIPO_I;
			} else if ("-4".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_FUSION;
			} else if ("-5".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_MATE;
			} else if ("-6".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_MATR_PDF;
			} else if ("-7".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_MATR_TIPOA;
			}else if ("-8".equalsIgnoreCase(valorEnum)) {
				return TipoCarpeta.CARPETA_DUPLICADO;
			}
		}
		return null;
	}

}