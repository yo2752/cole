package facturacion.utiles.enumerados;

public enum TipoTramite {

	// Solicitud Datos
	// Baja
	// Notificacion Transmision
	// Transmision
	// Matriculacion
	// Otros
	// Nueva Matriculacion

	SolicitudDatos("Solicitud de Datos", "Solicitud de Datos") {
		public String toString() {
			return "SolicitudDatos";
		}
	},
	Baja("Baja", "Baja") {
		public String toString() {
			return "Baja";
		}
	},
	NotificacionTransmision("NotificacionTransmision", "NotificacionTransmision") {
		public String toString() {
			return "NotificacionTransmision";
		}
	},
	Transmision("Transmision", "Transmision") {
		public String toString() {
			return "Transmision";
		}
	},
	Matriculacion("Matriculacion", "Matriculacion") {
		public String toString() {
			return "Matriculacion";
		}
	},
	Otros("Otros", "Otros") {
		public String toString() {
			return "Otros";
		}
	},
	NuevaMatriculacion("NuevaMatriculacion", "NuevaMatriculacion") {
		public String toString() {
			return "NuevaMatriculacion";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTramite(String valorEnum, String nombreEnum) {
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