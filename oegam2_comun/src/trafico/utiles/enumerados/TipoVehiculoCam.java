package trafico.utiles.enumerados;

public enum TipoVehiculoCam {

	VEHICULO("A", "VEHÍCULO") { // Antiguo Turismo
		public String toString() {
			return "A";
		}
	},
	TODOTERRENO("B", "TODOTERRENO") {
		public String toString() {
			return "B";
		}
	},
	MOTOCICLETA("C", "MOTOCICLETA") {
		public String toString() {
			return "C";
		}
	},
	SCOOTER("D", "SCOOTER") {
		public String toString() {
			return "D";
		}
	},
	DESCONOCIDAMARCA("F", "DESCONOCIDA MARCA") {
		public String toString() {
			return "F";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoVehiculoCam(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static TipoVehiculoCam convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		else if (valorEnum.equals("A"))
			return VEHICULO;
		else if (valorEnum.equals("B"))
			return TODOTERRENO;
		else if (valorEnum.equals("C"))
			return MOTOCICLETA;
		else if (valorEnum.equals("D"))
			return SCOOTER;
		else if (valorEnum.equals("F"))
			return DESCONOCIDAMARCA;
		else
			return null;
	}
}