package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoSubsanacion {

	DESDE_INICIO("2A", "Subsanaci�n desde el inicio") {
		public String toString() {
			return "2A";
		}
	},
	DESDE_SUSPENDIDA_CALIFICACION("2B", "Subsanaci�n por suspensi�n de calificaci�n") {
		public String toString() {
			return "2B";
		}
	},
	DESDE_CALIFICACION("2C", "Subsanaci�n desde un tr�mite calificado.") {
		public String toString() {
			return "2C";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoSubsanacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoSubsanacion convertir(String valorEnum) {
		if ("2A".equals(valorEnum))
			return DESDE_INICIO;
		if ("2B".equals(valorEnum))
			return DESDE_SUSPENDIDA_CALIFICACION;
		if ("2C".equals(valorEnum))
			return DESDE_CALIFICACION;
		return null;
	}
}