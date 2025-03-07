package org.gestoresmadrid.core.model.enumerados;

public enum TipoMotivoExencion {

	Alquiler("ALQU", "ALQU - ALQUILER") {
		public String toString() {
			return "ALQU";
		}
	},
	Familia("FAMN", "FAMN - FAMILIA NUMEROSA") {
		public String toString() {
			return "FAMN";
		}
	},
	Taxi("TAXI", "TAXI - AUTOTAXI") {
		public String toString() {
			return "TAXI";
		}
	},
	Minusvalidos("MINU", "MINU - MINUSVALIDOS") {
		public String toString() {
			return "MINU";
		}
	},
	Autoescuela("AESC", "AESC - AUTOESCUELA") {
		public String toString() {
			return "AESC";
		}
	},
	Residencia("RESI", "RESI - CAMBIO RESIDENCIA") {
		public String toString() {
			return "RESI";
		}
	},
	Seguridad("5NS1", "5NS1 - Cuerpos y fuerzas de seguridad del estado, aduanas") {
		public String toString() {
			return "5NS1";
		}
	},
	Ambulancias("5NS2", "5NS2 - Ambulancias") {
		public String toString() {
			return "5NS2";
		}
	},
	Legislacion("6NS1", "6NS1 - Aprox. de la legislación de los estados miembros sobre homologación") {
		public String toString() {
			return "6NS1";
		}
	},
	Mixtos("6NS7", "6NS7 - Vehículos mixtos adaptables afectos a una actividad económica ") {
		public String toString() {
			return "6NS7";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoMotivoExencion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoMotivoExencion convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (valorEnum.equals("ALQU"))
			return Alquiler;
		if (valorEnum.equals("FAMN"))
			return Familia;
		if (valorEnum.equals("TAXI"))
			return Taxi;
		if (valorEnum.equals("MINU"))
			return Minusvalidos;
		if (valorEnum.equals("AESC"))
			return Autoescuela;
		if (valorEnum.equals("RESI"))
			return Residencia;
		if (valorEnum.equals("5NS1"))
			return Seguridad;
		if (valorEnum.equals("5NS2"))
			return Ambulancias;
		if (valorEnum.equals("6NS1"))
			return Legislacion;
		if (valorEnum.equals("6NS7"))
			return Mixtos;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (valorEnum.equals("ALQU"))
			return Alquiler.getNombreEnum();
		if (valorEnum.equals("FAMN"))
			return Familia.getNombreEnum();
		if (valorEnum.equals("TAXI"))
			return Taxi.getNombreEnum();
		if (valorEnum.equals("MINU"))
			return Minusvalidos.getNombreEnum();
		if (valorEnum.equals("AESC"))
			return Autoescuela.getNombreEnum();
		if (valorEnum.equals("RESI"))
			return Residencia.getNombreEnum();
		if (valorEnum.equals("5NS1"))
			return Seguridad.getNombreEnum();
		if (valorEnum.equals("5NS2"))
			return Ambulancias.getNombreEnum();
		if (valorEnum.equals("6NS1"))
			return Legislacion.getNombreEnum();
		if (valorEnum.equals("6NS7"))
			return Mixtos.getNombreEnum();
		else
			return null;
	}
}
