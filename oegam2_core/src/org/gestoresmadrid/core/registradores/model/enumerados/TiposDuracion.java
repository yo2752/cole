package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TiposDuracion {
	NUMERO_MESES(1, "Número de meses") {
		public String toString() {
			return "1";
		}
	},
	NUMERO_ANIOS(2, "Número de años") {
		public String toString() {
			return "2";
		}
	},
	INTERVALO_FECHAS(3, "Intervalo de fechas") {
		public String toString() {
			return "3";
		}
	};

	private TiposDuracion(int valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	private int valorEnum;
	private String nombreEnum;

	public String getNombreEnum() {
		return nombreEnum;
	}

	public int getValorEnum() {
		return valorEnum;
	}

	public static TiposDuracion convertir(int valorEnum) {
		switch (valorEnum) {
			case 1:
				return NUMERO_MESES;
			case 2:
				return NUMERO_ANIOS;
			case 3:
				return INTERVALO_FECHAS;
			default:
				return null;
		}
	}
}
