package general.utiles.enumerados;

public enum EstadoReconocimientoMedico {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},

	Asistio("2", "Asistió") {
		public String toString() {
			return "2";
		}
	},

	AsistioDistinaFecha("3", "Asistió en otra fecha") {
		public String toString() {
			return "3";
		}
	},

	NoAsistio("4", "No asistió") {
		public String toString() {
			return "4";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoReconocimientoMedico(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static EstadoReconocimientoMedico convertir(String valorEnum) {

		if (valorEnum == null){
			return null;
		}
		for (EstadoReconocimientoMedico e : EstadoReconocimientoMedico.values()) {
			if (e.getValorEnum().equals(valorEnum)) {
				return e;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		EstadoReconocimientoMedico e = convertir(valorEnum);
		return e != null ? e.getNombreEnum() : null;
	}

}
