package trafico.utiles.enumerados;

public enum ModeloISD {

	SeisSesenta("660", "660") {
		public String toString() {
			return "660";
		}
	},
	SeisCincuenta("650", "650") {
		public String toString() {
			return "650";
		}
	},
	SeisCincuentaYUno("651", "651") {
		public String toString() {
			return "651";
		}
	},
	SeisCincuentaYDos("652", "652") {
		public String toString() {
			return "652";
		}
	},
	SeisSesentaYUno("661", "661") {
		public String toString() {
			return "661";
		}
	},
	OochoOchenta("880", "880") {
		public String toString() {
			return "880";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ModeloISD(String valorEnum, String nombreEnum) {
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