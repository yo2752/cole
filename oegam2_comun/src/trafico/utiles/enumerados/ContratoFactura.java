package trafico.utiles.enumerados;

public enum ContratoFactura {

	Contrato("CO", "Contrato de Compraventa") {
		public String toString() {
			return "CO";
		}
	},
	Factura("FA", "Factura") {
		public String toString() {
			return "FA";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ContratoFactura(String valorEnum, String nombreEnum) {
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
	public static ContratoFactura convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		else if (valorEnum.equals("CO"))
			return Contrato;
		else if (valorEnum.equals("FA"))
			return Factura;
		else
			return null;
	}
}
