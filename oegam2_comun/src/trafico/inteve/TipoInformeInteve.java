package trafico.inteve;

import java.math.BigDecimal;

/**
 * Encapsula todos los valores posibles para el motivo de la consulta de los informes INTEVE
 */
public enum TipoInformeInteve {

	COMPLETO("0", "Informe completo") {
		public String toString() {
			return "Informe completo";
		}
	},
	DATOS_TECNICOS("1", "Informe datos tecnicos") {
		public String toString() {
			return "Informe datos tecnicos";
		}
	},
	CARGAS("2", "Informe cargas") {
		public String toString() {
			return "Informe cargas";
		}
	},
	VEHICULOS_A_MI_NOMBRE("5", "Informe vehiculos a mi nombre") {
		public String toString() {
			return "Informe vehiculos a mi nombre";
		}

	},
	VEHICULOS_SIN_MATRICULA("6", "Informe vehiculos sin matricular") {
		public String toString() {
			return "Informe vehiculos sin matricular";
		}
	};

	private String nombreEnum;
	private String valorEnum;

	private TipoInformeInteve(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoInformeInteve convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (TipoInformeInteve tipoInformeInteve : TipoInformeInteve.values()) {
				if (tipoInformeInteve.getValorEnum().equals(valorEnum)) {
					return tipoInformeInteve;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valor) {
		if (valor != null) {
			for (TipoInformeInteve tipoInformeInteve : TipoInformeInteve.values()) {
				if (tipoInformeInteve.getValorEnum().equals(valor)) {
					return tipoInformeInteve.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(TipoInformeInteve tipoInformeInteve : TipoInformeInteve.values()){
				if(tipoInformeInteve.getNombreEnum().equals(nombre)){
					return tipoInformeInteve.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(TipoInformeInteve tipoInforme){
		if (tipoInforme != null) {
			for (TipoInformeInteve tipoInformeInteve : TipoInformeInteve.values()) {
				if (tipoInformeInteve.getValorEnum() == tipoInforme.getValorEnum()) {
					return tipoInformeInteve.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirEstadoBigDecimal(BigDecimal estado) {
		if (estado != null) {
			return convertirTexto(estado.toString());
		}
		return "";
	}

	public static Boolean validarTipoInforme(String tipoInforme) {
		if (tipoInforme != null && !tipoInforme.isEmpty()) {
			if (TipoInformeInteve.COMPLETO.getValorEnum().equals(tipoInforme)) {
				return Boolean.TRUE;
			} else if (TipoInformeInteve.CARGAS.getValorEnum().equals(tipoInforme)) {
				return Boolean.TRUE;
			} else if (TipoInformeInteve.DATOS_TECNICOS.getValorEnum().equals(tipoInforme)) {
				return Boolean.TRUE;
			} else if (TipoInformeInteve.VEHICULOS_A_MI_NOMBRE.getValorEnum().equals(tipoInforme)) {
				return Boolean.TRUE;
			} else if (TipoInformeInteve.VEHICULOS_SIN_MATRICULA.getValorEnum().equals(tipoInforme)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}