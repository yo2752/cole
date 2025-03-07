package org.gestoresmadrid.core.model.enumerados;

public enum CausaHechoImponible {

	PrimeraMatriculacion("1", "PRIMERA MATRICULACION DEFINITIVA EN ESPAÑA") {
		public String toString() {
			return "1";
		}
	},
	Circulacion("2", "CIRCULACION O UTILIZACION EN ESPAÑA SIN SOLICITAR MATRIC. DEFINITIVA") {
		public String toString() {
			return "2";
		}
	},
	ModificacionCircunstancias("3", "MODIFICACION DE CIRCUNSTANCIAS O REQUISITOS") {
		public String toString() {
			return "3";
		}
	},

	Traslado("4", "TRASLADO DESDE CANARIAS A LA PENINSULA O ISLAS BALEARES") {
		public String toString() {
			return "4";
		}
	},

	Renuncia("5", "RENUNCIA A BENEFICIOS FISCALES RECONOCIDOS POR LA ADMON TRIBUTARIA") {
		public String toString() {
			return "5";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private CausaHechoImponible(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static CausaHechoImponible convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if ("1".equals(valorEnum))
			return PrimeraMatriculacion;
		if ("2".equals(valorEnum))
			return Circulacion;
		if ("3".equals(valorEnum))
			return ModificacionCircunstancias;
		if ("4".equals(valorEnum))
			return Traslado;
		if ("5".equals(valorEnum))
			return Renuncia;
		else {
			return null;
		}
	}
}