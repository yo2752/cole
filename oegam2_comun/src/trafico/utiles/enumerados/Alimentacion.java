package trafico.utiles.enumerados;

import trafico.beans.AlimentacionBean;

public enum Alimentacion {

	MONOFUEL("M", "Monofuel") {
		public String toString() {
			return "M";
		}
	},
	BIFUEL("B", "Bifuel") {
		public String toString() {
			return "B";
		}
	},
	FLEXIFUEL("F", "Flexifuel") {
		public String toString() {
			return "F";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Alimentacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static AlimentacionBean convertir(String idAlimentacionParam) {
		if (idAlimentacionParam == null) {
			return new AlimentacionBean();
		}
		AlimentacionBean alimentacion = new AlimentacionBean();
		if (idAlimentacionParam.equalsIgnoreCase(Alimentacion.MONOFUEL.getValorEnum())) {
			alimentacion.setDescripcion(Alimentacion.MONOFUEL.getNombreEnum());
			alimentacion.setIdAlimentacion(Alimentacion.MONOFUEL.getValorEnum());
			return alimentacion;
		}
		if (idAlimentacionParam.equalsIgnoreCase(Alimentacion.BIFUEL.getValorEnum())) {
			alimentacion.setDescripcion(Alimentacion.BIFUEL.getNombreEnum());
			alimentacion.setIdAlimentacion(Alimentacion.BIFUEL.getValorEnum());
			return alimentacion;
		}
		if (idAlimentacionParam.equalsIgnoreCase(Alimentacion.FLEXIFUEL.getValorEnum())) {
			alimentacion.setDescripcion(Alimentacion.FLEXIFUEL.getNombreEnum());
			alimentacion.setIdAlimentacion(Alimentacion.FLEXIFUEL.getValorEnum());
			return alimentacion;
		}
		return null;
	}

	public static Alimentacion convertirAlimentacion(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (Alimentacion alimentacion : Alimentacion.values()) {
				if (alimentacion.getValorEnum().equals(valorEnum)) {
					return alimentacion;
				}
			}
			return null;
		}
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}