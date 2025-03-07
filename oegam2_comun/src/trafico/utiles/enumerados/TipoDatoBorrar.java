package trafico.utiles.enumerados;

public enum TipoDatoBorrar {

	Respuesta_Check("Respuesta Check Ctit", "Respuesta Check Ctit Trámite") {
		public String toString() {
			return "Respuesta Check Ctit";
		}
	},
	Respuesta_Tramitación("Respuesta Tramitación Ctit", "Respuesta Tramitación Ctit Trámite") {
		public String toString() {
			return "Respuesta Tramitación Ctit";
		}
	},
	Respuesta_Matriculacion("Respuesta Matriculación", "Respuesta Matriculación Trámite") {
		public String toString() {
			return "Respuesta Matriculación";
		}
	},
	Kilometraje("Kilometraje", "Kilometraje vehículo") {
		public String toString() {
			return "Kilometraje";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoDatoBorrar(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}