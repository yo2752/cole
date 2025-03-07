package org.gestoresmadrid.logs.enumerados;

public enum TipoLogFrontalEnum {

	LOGING_LOG("loging", "loging") {
		public String toString() {
			return "loging";
		}
	},
	TRAFICO_LOG("trafico", "trafico") {
		public String toString() {
			return "trafico";
		}
		
	};

	private TipoLogFrontalEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private String valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public static TipoLogFrontalEnum recuperar(String valorEnum) {
		if (valorEnum.equals("loging")) {
			return TipoLogFrontalEnum.LOGING_LOG;
		} else if (valorEnum.equals("trafico")) {
			return TipoLogFrontalEnum.TRAFICO_LOG;
		} else {
			return null;
		}
	}
}