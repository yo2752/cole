package trafico.utiles.enumerados;

public enum TipoFactura {

	ABONO("ABON", "ABONO") {
		public String toString() {
			return "ABONO";
		}
	},

	FACTURA("FACT", "FACTURA") {
		public String toString() {
			return "FACTURA";
		}
	},

	RECTIFICATIVA("RECT", "RECTIFICATIVA") {
		public String toString() {
			return "RECTIFICATIVA";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoFactura(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
}