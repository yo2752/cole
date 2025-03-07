package trafico.utiles.enumerados;

public enum TipoDatoBorrar {

	Respuesta_Check("Respuesta Check Ctit", "Respuesta Check Ctit Tr�mite") {
		public String toString() {
			return "Respuesta Check Ctit";
		}
	},
	Respuesta_Tramitaci�n("Respuesta Tramitaci�n Ctit", "Respuesta Tramitaci�n Ctit Tr�mite") {
		public String toString() {
			return "Respuesta Tramitaci�n Ctit";
		}
	},
	Respuesta_Matriculacion("Respuesta Matriculaci�n", "Respuesta Matriculaci�n Tr�mite") {
		public String toString() {
			return "Respuesta Matriculaci�n";
		}
	},
	Kilometraje("Kilometraje", "Kilometraje veh�culo") {
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