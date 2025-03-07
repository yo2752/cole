package escrituras.utiles.enumerados;

public enum DescripcionDerechoReal {

	Nuda_Propiedad("NUDA PROPIEDAD", "Nuda Propiedad") {
		public String toString() {
			return "NUDA PROPIEDAD";
		}
	},
	Otros("OTROS", "Otros") {
		public String toString() {
			return "OTROS";
		}
	},
	Uso_Habitacion("USO O HABITACION", "Uso ó Habitación") {
		public String toString() {
			return "USO O HABITACION";
		}
	},
	Usufructo("USUFRUCTO", "Usufructo") {
		public String toString() {
			return "USUFRUCTO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private DescripcionDerechoReal(String valorEnum, String nombreEnum) {
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
	public static DescripcionDerechoReal convertir(String valorEnum) {

		if ("NUDA PROPIEDAD".equals(valorEnum))
			return Nuda_Propiedad;

		if ("OTROS".equals(valorEnum))
			return Otros;

		if ("USO O HABITACION".equals(valorEnum))
			return Uso_Habitacion;

		if ("USUFRUCTO".equals(valorEnum))
			return Usufructo;

		return null;
	}
}