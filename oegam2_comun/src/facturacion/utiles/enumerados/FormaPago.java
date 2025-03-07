package facturacion.utiles.enumerados;

public enum FormaPago {

	TipoTransferencia("PAGO EN CUENTA", "Pago en cuenta") {
		public String toString() {
			return "Transferencia";
		}
	},
	TipoTarjeta("TARJETA", "Tarjeta") {
		public String toString() {
			return "Tarjeta";
		}
	},
	TipoMetalico("CONTADO", "Contado") {
		public String toString() {
			return "Contado";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private FormaPago(String valorEnum, String nombreEnum) {
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